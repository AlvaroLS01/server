package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.NotFoundException;
import com.comerzzia.api.core.service.util.ComerzziaDatosSesion;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.service.salesdocument.SaleDocumentService;

/**
 * Implementation that delegates the rendering of the sale document to the
 * standard {@link SaleDocumentService}.
 */
@Service
public class DocumentoVentaImpresionServicioImpl implements DocumentoVentaImpresionServicio {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoVentaImpresionServicioImpl.class);
    private static final String MIME_PDF = "application/pdf";
    private static final String DIRECTORIO_DOCTEMPLATES = "doctemplates";
    private static final String CLASSPATH_PLANTILLAS_FACTURAS = "classpath*:informes/ventas/facturas/*.jasper";
    private static final Map<String, String> ALIAS_PLANTILLAS;
    private static final Map<String, String> ARCHIVOS_ALIAS;

    static {
        Map<String, String> alias = new HashMap<>();
        alias.put("facturaa4", "facturaA4");
        alias.put("facturaa4_original", "facturaA4_Original");
        alias.put("facturaa4_pt", "facturaA4_PT");
        alias.put("facturaa4_ca", "facturaA4_CA");
        alias.put("facturadevoluciona4_pt", "facturaDevolucionA4_PT");
        alias.put("facturadevoluciona4_pt_old", "facturaDevolucionA4_PT");
        alias.put("factura", "facturaA4");
        alias.put("fs", "facturaA4");
        alias.put("ft", "facturaA4");
        alias.put("fr", "facturaA4");
        alias.put("nc", "facturaA4");
        ALIAS_PLANTILLAS = Collections.unmodifiableMap(alias);

        Map<String, String> archivosAlias = new HashMap<>();
        archivosAlias.put("FT.jasper", "facturaA4.jasper");
        archivosAlias.put("FS.jasper", "facturaA4.jasper");
        archivosAlias.put("FR.jasper", "facturaA4.jasper");
        archivosAlias.put("NC.jasper", "facturaA4.jasper");
        ARCHIVOS_ALIAS = Collections.unmodifiableMap(archivosAlias);
    }

    private final SaleDocumentService saleDocumentService;
    private final ComerzziaDatosSesion datosSesionRequest;
    private final PathMatchingResourcePatternResolver resourceResolver;

    private final Object plantillasLock = new Object();
    private volatile boolean plantillasPreparadas;
    private volatile Path directorioInformes;

    public DocumentoVentaImpresionServicioImpl(SaleDocumentService saleDocumentService,
            @Qualifier("datosSesionRequest") ComerzziaDatosSesion datosSesionRequest) {
        this.saleDocumentService = saleDocumentService;
        this.datosSesionRequest = datosSesionRequest;
        this.resourceResolver = new PathMatchingResourcePatternResolver();
    }

    @Override
    public Optional<DocumentoVentaImpresionRespuesta> imprimir(String uidDocumento,
            OpcionesImpresionDocumentoVenta opciones) {
        if (StringUtils.isBlank(uidDocumento)) {
            throw new DocumentoVentaImpresionException("El identificador del documento es obligatorio");
        }

        OpcionesImpresionDocumentoVenta parametros = normalizarOpciones(opciones);
        IDatosSesion datosSesion = obtenerDatosSesion();

        String uidActividadAnterior = datosSesion.getUidActividad();
        String uidActividadImpresion = resolverUidActividad(uidActividadAnterior, parametros.getUidActividad(), uidDocumento);
        boolean requiereActualizarSesion = !StringUtils.equals(uidActividadAnterior, uidActividadImpresion);

        if (requiereActualizarSesion) {
            aplicarUidActividad(datosSesion, uidActividadImpresion, false);
        }

        prepararPlantillasDeImpresion();

        try (ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
            PrintDocumentDTO configuracionImpresion = construirPeticionImpresion(uidDocumento, parametros);
            saleDocumentService.printDocument(salida, datosSesion, uidDocumento, configuracionImpresion);

            byte[] contenido = salida.toByteArray();
            if (contenido.length == 0) {
                LOGGER.debug("imprimir() - La impresión del documento '{}' no generó contenido", uidDocumento);
                return Optional.empty();
            }

            DocumentoVentaImpresionRespuesta respuesta = construirRespuesta(uidDocumento, parametros,
                    configuracionImpresion, contenido);
            return Optional.of(respuesta);
        } catch (NotFoundException excepcion) {
            LOGGER.debug("imprimir() - No se encontró el documento de venta con uid '{}'", uidDocumento, excepcion);
            return Optional.empty();
        } catch (ApiException excepcion) {
            LOGGER.error("imprimir() - Error generando el documento de venta '{}'", uidDocumento, excepcion);
            throw new DocumentoVentaImpresionException("No fue posible generar el documento de venta", excepcion);
        } catch (DocumentoVentaImpresionException excepcion) {
            throw excepcion;
        } catch (Exception excepcion) {
            LOGGER.error("imprimir() - Error inesperado generando el documento de venta '{}'", uidDocumento, excepcion);
            throw new DocumentoVentaImpresionException("No fue posible generar el documento de venta", excepcion);
        } finally {
            if (requiereActualizarSesion) {
                try {
                    aplicarUidActividad(datosSesion, uidActividadAnterior, true);
                } catch (DocumentoVentaImpresionException excepcion) {
                    LOGGER.debug("imprimir() - No se pudo restaurar el uid de actividad original tras imprimir '{}'",
                            uidDocumento, excepcion);
                }
            }
        }
    }

    private OpcionesImpresionDocumentoVenta normalizarOpciones(OpcionesImpresionDocumentoVenta opciones) {
        if (opciones != null) {
            return opciones;
        }
        return new OpcionesImpresionDocumentoVenta(null, false, false, null, null, null, Collections.emptyMap());
    }

    private IDatosSesion obtenerDatosSesion() {
        if (datosSesionRequest == null) {
            throw new DocumentoVentaImpresionException(
                    "No hay datos de sesión disponibles para ejecutar la impresión del documento");
        }
        IDatosSesion datosSesion = datosSesionRequest.getDatosSesionBean();
        if (datosSesion == null) {
            throw new DocumentoVentaImpresionException(
                    "No fue posible obtener los datos de sesión asociados a la petición");
        }
        return datosSesion;
    }

    private String resolverUidActividad(String uidActividadActual, String uidActividadSolicitada, String uidDocumento) {
        String solicitado = StringUtils.trimToNull(uidActividadSolicitada);
        if (solicitado != null) {
            if (!StringUtils.equals(StringUtils.trimToNull(uidActividadActual), solicitado)) {
                LOGGER.debug("imprimir() - Se utilizará el uid de actividad {} indicado en la petición para {}", solicitado,
                        uidDocumento);
            }
            return solicitado;
        }

        String actual = StringUtils.trimToNull(uidActividadActual);
        if (actual != null) {
            LOGGER.debug("imprimir() - Usando el uid de actividad {} obtenido de la sesión para {}", actual, uidDocumento);
            return actual;
        }

        throw new DocumentoVentaImpresionException(
                "Es obligatorio indicar el uid de actividad para imprimir el documento " + uidDocumento);
    }

    private void aplicarUidActividad(IDatosSesion datosSesion, String uidActividad, boolean restaurando) {
        try {
            Method metodo = datosSesion.getClass().getMethod("setUidActividad", String.class);
            metodo.invoke(datosSesion, uidActividad);
        } catch (NoSuchMethodException excepcion) {
            String accion = restaurando ? "restaurar" : "establecer";
            throw new DocumentoVentaImpresionException(
                    "La sesión no permite " + accion + " el uid de actividad necesario para la impresión", excepcion);
        } catch (IllegalAccessException | InvocationTargetException excepcion) {
            String accion = restaurando ? "restaurar" : "actualizar";
            throw new DocumentoVentaImpresionException(
                    "Error al " + accion + " el uid de actividad en los datos de sesión", excepcion);
        }
    }

    private PrintDocumentDTO construirPeticionImpresion(String uidDocumento, OpcionesImpresionDocumentoVenta opciones) {
        PrintDocumentDTO printRequest = new PrintDocumentDTO();
        printRequest.setMimeType(resolverMimeType(opciones.getTipoMime()));
        printRequest.setCopy(opciones.esCopia());
        printRequest.setInline(opciones.esEnLinea());
        printRequest.setScreenOutput(opciones.esEnLinea());

        String nombreSalida = determinarNombreSalida(uidDocumento, opciones.getNombreDocumentoSalida());
        printRequest.setOutputDocumentName(nombreSalida);

        if (StringUtils.isNotBlank(opciones.getPlantillaImpresion())) {
            printRequest.setPrintTemplate(normalizarPlantilla(opciones.getPlantillaImpresion()));
        }

        Map<String, Object> parametros = printRequest.getCustomParams();
        opciones.getParametrosPersonalizados().forEach(parametros::put);

        return printRequest;
    }

    private String determinarNombreSalida(String uidDocumento, String nombreSolicitado) {
        return StringUtils.isNotBlank(nombreSolicitado) ? nombreSolicitado.trim() : uidDocumento;
    }

    private String resolverMimeType(String tipoSolicitado) {
        if (StringUtils.isBlank(tipoSolicitado)) {
            return MIME_PDF;
        }
        return tipoSolicitado.trim();
    }

    private DocumentoVentaImpresionRespuesta construirRespuesta(String uidDocumento,
            OpcionesImpresionDocumentoVenta opciones, PrintDocumentDTO configuracionImpresion, byte[] contenido) {
        DocumentoVentaImpresionRespuesta respuesta = new DocumentoVentaImpresionRespuesta();
        respuesta.setUidDocumento(uidDocumento);
        respuesta.setCopia(opciones.esCopia());
        respuesta.setEnLinea(opciones.esEnLinea());
        respuesta.setTipoMime(configuracionImpresion.getMimeType());
        respuesta.setNombreArchivo(normalizarNombreArchivo(uidDocumento, configuracionImpresion.getOutputDocumentName(),
                configuracionImpresion.getMimeType()));
        respuesta.setDocumento(Base64.getEncoder().encodeToString(contenido));
        return respuesta;
    }

    private String normalizarNombreArchivo(String uidDocumento, String nombreSolicitado, String mimeType) {
        String nombre = StringUtils.isNotBlank(nombreSolicitado) ? nombreSolicitado.trim() : uidDocumento;
        if (StringUtils.isBlank(mimeType)) {
            return nombre;
        }
        if (MIME_PDF.equalsIgnoreCase(mimeType) && !StringUtils.endsWithIgnoreCase(nombre, ".pdf")) {
            return nombre + ".pdf";
        }
        return nombre;
    }

    private String normalizarPlantilla(String plantillaSolicitada) {
        String valor = StringUtils.trimToNull(plantillaSolicitada);
        if (valor == null) {
            return null;
        }
        String alias = ALIAS_PLANTILLAS.get(valor.toLowerCase(Locale.ROOT));
        return alias != null ? alias : valor;
    }

    private void prepararPlantillasDeImpresion() {
        if (plantillasPreparadas) {
            return;
        }
        synchronized (plantillasLock) {
            if (plantillasPreparadas) {
                return;
            }
            try {
                Path baseInformes = inicializarDirectorioInformes();
                Path directorioPlantillas = baseInformes.resolve(DIRECTORIO_DOCTEMPLATES);
                Files.createDirectories(directorioPlantillas);
                copiarPlantillasDesdeClasspath(directorioPlantillas);
                crearAliasDePlantillas(directorioPlantillas);
                plantillasPreparadas = true;
                LOGGER.debug("prepararPlantillasDeImpresion() - Plantillas disponibles en {}", directorioPlantillas);
            } catch (IOException excepcion) {
                throw new DocumentoVentaImpresionException("No se pudieron preparar las plantillas de impresión", excepcion);
            }
        }
    }

    private Path inicializarDirectorioInformes() throws IOException {
        if (directorioInformes != null) {
            return directorioInformes;
        }

        Object informacionInformes = obtenerInformesInfo();
        Path rutaExistente = obtenerRutaInformesConfigurada(informacionInformes);

        if (rutaExistente == null) {
            rutaExistente = crearRutaInformesPredeterminada();
            establecerRutaInformes(informacionInformes, rutaExistente);
        }

        directorioInformes = rutaExistente;
        return directorioInformes;
    }

    private Object obtenerInformesInfo() {
        try {
            Class<?> claseAppInfo = Class.forName("com.comerzzia.core.util.config.AppInfo");
            Method metodoInformes = claseAppInfo.getMethod("getInformesInfo");
            return metodoInformes.invoke(null);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException excepcion) {
            LOGGER.debug("obtenerInformesInfo() - No se pudo acceder a AppInfo", excepcion);
            return null;
        }
    }

    private Path obtenerRutaInformesConfigurada(Object informacionInformes) {
        if (informacionInformes == null) {
            return null;
        }
        try {
            Method metodoRuta = informacionInformes.getClass().getMethod("getRutaBase");
            Object ruta = metodoRuta.invoke(informacionInformes);
            String textoRuta = ruta != null ? ruta.toString().trim() : null;
            if (StringUtils.isBlank(textoRuta) || "null".equalsIgnoreCase(textoRuta)) {
                return null;
            }
            try {
                Path rutaNormalizada = Paths.get(textoRuta);
                Files.createDirectories(rutaNormalizada);
                return rutaNormalizada;
            } catch (InvalidPathException excepcion) {
                LOGGER.warn("obtenerRutaInformesConfigurada() - Ruta de informes inválida {}", textoRuta, excepcion);
                return null;
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException excepcion) {
            LOGGER.debug("obtenerRutaInformesConfigurada() - No se pudo recuperar la ruta configurada", excepcion);
            return null;
        }
    }

    private Path crearRutaInformesPredeterminada() throws IOException {
        Path rutaBase = null;
        String comerzziaHome = System.getProperty("comerzzia.home");
        if (StringUtils.isNotBlank(comerzziaHome)) {
            rutaBase = Paths.get(comerzziaHome, "informes");
        } else {
            String rutaUsuario = System.getProperty("user.home");
            if (StringUtils.isNotBlank(rutaUsuario)) {
                rutaBase = Paths.get(rutaUsuario, ".comerzzia", "informes");
            }
        }
        if (rutaBase == null) {
            rutaBase = Files.createTempDirectory("comerzzia-informes");
        }
        Files.createDirectories(rutaBase);
        return rutaBase;
    }

    private void establecerRutaInformes(Object informacionInformes, Path rutaBase) {
        if (informacionInformes == null || rutaBase == null) {
            return;
        }
        try {
            Method metodo = informacionInformes.getClass().getMethod("setRutaBase", String.class);
            metodo.invoke(informacionInformes, rutaBase.toString());
        } catch (NoSuchMethodException excepcion) {
            LOGGER.trace("establecerRutaInformes() - La configuración de informes no expone setRutaBase", excepcion);
        } catch (IllegalAccessException | InvocationTargetException excepcion) {
            LOGGER.warn("establecerRutaInformes() - No se pudo actualizar la ruta de informes", excepcion);
        }
    }

    private void copiarPlantillasDesdeClasspath(Path directorioPlantillas) throws IOException {
        Resource[] recursos = resourceResolver.getResources(CLASSPATH_PLANTILLAS_FACTURAS);
        for (Resource recurso : recursos) {
            if (!recurso.isReadable()) {
                continue;
            }
            String nombreFichero = recurso.getFilename();
            if (StringUtils.isBlank(nombreFichero)) {
                continue;
            }
            Path destino = directorioPlantillas.resolve(nombreFichero);
            if (Files.exists(destino)) {
                continue;
            }
            try (InputStream entrada = recurso.getInputStream()) {
                Files.copy(entrada, destino, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private void crearAliasDePlantillas(Path directorioPlantillas) throws IOException {
        for (Map.Entry<String, String> alias : ARCHIVOS_ALIAS.entrySet()) {
            Path origen = directorioPlantillas.resolve(alias.getValue());
            Path destino = directorioPlantillas.resolve(alias.getKey());
            if (!Files.exists(origen) || Files.exists(destino)) {
                continue;
            }
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
