package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.NotFoundException;
import com.comerzzia.api.core.service.util.ComerzziaDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.service.salesdocument.SaleDocumentService;

@Service
public class DocumentoVentaImpresionServicio {

        private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoVentaImpresionServicio.class);
        private static final String MIMETYPE_POR_DEFECTO = "application/pdf";

    private final SaleDocumentService saleDocumentService;
    private final ComerzziaDatosSesion datosSesionRequest;

    private volatile Path directorioFacturas;

    public DocumentoVentaImpresionServicio(
                    SaleDocumentService saleDocumentService,
                        @Qualifier("datosSesionRequest") ComerzziaDatosSesion datosSesionRequest) {
                this.saleDocumentService = saleDocumentService;
                this.datosSesionRequest = datosSesionRequest;
        }

        public Optional<DocumentoVentaImpresionRespuesta> imprimir(String uidDocumento, OpcionesImpresionDocumentoVenta opciones) {

                if (StringUtils.isBlank(uidDocumento)) {
                        throw new DocumentoVentaImpresionException("El identificador del documento es obligatorio");
                }

                PrintDocumentDTO configuracionImpresion = construirPeticionImpresion(uidDocumento, opciones);

                try (ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
                        saleDocumentService.printDocument(salida, datosSesionRequest.getDatosSesionBean(), uidDocumento, configuracionImpresion);
                        byte[] contenido = salida.toByteArray();
                        if (contenido.length == 0) {
                                LOGGER.debug("imprimir() - La impresión del documento '{}' no generó contenido", uidDocumento);
                                return Optional.empty();
                        }
                        guardarDocumentoEnSistema(configuracionImpresion, contenido);
                        DocumentoVentaImpresionRespuesta respuesta = construirRespuesta(uidDocumento, opciones, configuracionImpresion, contenido);
                        return Optional.of(respuesta);
                }
                catch (NotFoundException excepcion) {
                        LOGGER.debug("imprimir() - No se encontró el documento de venta con uid '{}'", uidDocumento, excepcion);
                        return Optional.empty();
                }
                catch (ApiException excepcion) {
                        LOGGER.error("imprimir() - Error generando el documento de venta '{}'", uidDocumento, excepcion);
                        throw new DocumentoVentaImpresionException("No fue posible generar el documento de venta", excepcion);
                }
                catch (DocumentoVentaImpresionException excepcion) {
                        throw excepcion;
                }
                catch (Exception excepcion) {
                        LOGGER.error("imprimir() - Error inesperado generando el documento de venta '{}'", uidDocumento, excepcion);
                        throw new DocumentoVentaImpresionException("No fue posible generar el documento de venta", excepcion);
                }
        }

        private PrintDocumentDTO construirPeticionImpresion(String uidDocumento, OpcionesImpresionDocumentoVenta opciones) {
                PrintDocumentDTO printRequest = new PrintDocumentDTO();
                printRequest.setMimeType(resolverMimeType(opciones.getTipoMime()));
                printRequest.setCopy(opciones.esCopia());
                printRequest.setInline(opciones.esEnLinea());
                printRequest.setScreenOutput(opciones.esEnLinea());

                String nombreSalida = opciones.getNombreDocumentoSalida();
                if (StringUtils.isBlank(nombreSalida)) {
                        nombreSalida = uidDocumento;
                }
                printRequest.setOutputDocumentName(nombreSalida);

                if (StringUtils.isNotBlank(opciones.getPlantillaImpresion())) {
                        printRequest.setPrintTemplate(opciones.getPlantillaImpresion().trim());
                }

                Map<String, Object> parametros = printRequest.getCustomParams();
                opciones.getParametrosPersonalizados().forEach(parametros::put);

                return printRequest;
        }

        private DocumentoVentaImpresionRespuesta construirRespuesta(String uidDocumento, OpcionesImpresionDocumentoVenta opciones,
                        PrintDocumentDTO configuracionImpresion, byte[] contenido) {
                DocumentoVentaImpresionRespuesta respuesta = new DocumentoVentaImpresionRespuesta();
                respuesta.setUidDocumento(uidDocumento);
                respuesta.setCopia(opciones.esCopia());
                respuesta.setEnLinea(opciones.esEnLinea());
                respuesta.setTipoMime(configuracionImpresion.getMimeType());
                respuesta.setNombreArchivo(normalizarNombreArchivo(configuracionImpresion.getOutputDocumentName(), configuracionImpresion.getMimeType()));
                respuesta.setDocumento(Base64.getEncoder().encodeToString(contenido));
                return respuesta;
        }

        private String normalizarNombreArchivo(String nombreSolicitado, String mimeType) {
                String nombre = StringUtils.defaultIfBlank(nombreSolicitado, "documento");
                if (StringUtils.isBlank(mimeType) || !mimeType.equalsIgnoreCase(MIMETYPE_POR_DEFECTO)) {
                        return nombre;
                }
                if (nombre.toLowerCase().endsWith(".pdf")) {
                        return nombre;
                }
                return nombre + ".pdf";
        }

        private String resolverMimeType(String tipoSolicitado) {
                if (tipoSolicitado == null) {
                        return MIMETYPE_POR_DEFECTO;
                }
                String normalizado = tipoSolicitado.trim();
                if (normalizado.isEmpty()) {
                        return MIMETYPE_POR_DEFECTO;
                }
                return normalizado;
        }

        private void guardarDocumentoEnSistema(PrintDocumentDTO configuracionImpresion, byte[] contenido) {
                if (configuracionImpresion == null || contenido == null || contenido.length == 0) {
                        return;
                }

                if (!MIMETYPE_POR_DEFECTO.equalsIgnoreCase(configuracionImpresion.getMimeType())) {
                        return;
                }

                Path directorioDestino = obtenerDirectorioFacturas();
                if (directorioDestino == null) {
                        LOGGER.warn("guardarDocumentoEnSistema() - No se pudo determinar la ruta de destino para almacenar el PDF generado");
                        return;
                }

                String nombreArchivo = normalizarNombreArchivo(configuracionImpresion.getOutputDocumentName(), configuracionImpresion.getMimeType());
                nombreArchivo = sanitizarNombreArchivo(nombreArchivo);

                Path destino = directorioDestino.resolve(nombreArchivo);
                try {
                        Files.write(destino, contenido, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                        LOGGER.info("guardarDocumentoEnSistema() - Documento '{}' guardado en {}", nombreArchivo, destino.toAbsolutePath());
                }
                catch (IOException excepcion) {
                        throw new DocumentoVentaImpresionException("No se pudo guardar el documento generado en la ruta configurada", excepcion);
                }
        }

        private String sanitizarNombreArchivo(String nombreArchivo) {
                String nombre = StringUtils.defaultIfBlank(nombreArchivo, "documento.pdf").trim();
                String normalizado = nombre.replaceAll("[\\\\/:*?\"<>|]", "_");
                if (!normalizado.toLowerCase(Locale.ROOT).endsWith(".pdf")) {
                        normalizado = normalizado + ".pdf";
                }
                return normalizado;
        }

        private Path obtenerDirectorioFacturas() {
                Path directorioActual = directorioFacturas;
                if (directorioActual != null && Files.exists(directorioActual)) {
                        return directorioActual;
                }

                synchronized (this) {
                        if (directorioFacturas != null && Files.exists(directorioFacturas)) {
                                return directorioFacturas;
                        }

                        String rutaBase = localizarRutaInformes();
                        if (StringUtils.isBlank(rutaBase)) {
                                return null;
                        }

                        Path directorio = Paths.get(rutaBase, "ventas", "facturas");
                        try {
                                Files.createDirectories(directorio);
                                directorioFacturas = directorio;
                                return directorioFacturas;
                        }
                        catch (IOException excepcion) {
                                throw new DocumentoVentaImpresionException("No se pudo preparar el directorio configurado para informes", excepcion);
                        }
                }
        }

        private String localizarRutaInformes() {
                try {
                        Class<?> claseAppInfo = Class.forName("com.comerzzia.core.util.config.AppInfo");
                        Method metodoInformes = claseAppInfo.getMethod("getInformesInfo");
                        Object informacion = metodoInformes.invoke(null);
                        if (informacion != null) {
                                Method metodoRuta = informacion.getClass().getMethod("getRutaBase");
                                Object ruta = metodoRuta.invoke(informacion);
                                if (ruta != null) {
                                        String textoRuta = ruta.toString().trim();
                                        if (!textoRuta.isEmpty()) {
                                                return textoRuta;
                                        }
                                }
                        }
                }
                catch (Exception excepcion) {
                        LOGGER.debug("localizarRutaInformes() - No se pudo obtener la ruta base de informes desde AppInfo", excepcion);
                }
                Path rutaPorDefecto = localizarRutaInformesPorDefecto();
                if (rutaPorDefecto != null) {
                        LOGGER.info("localizarRutaInformes() - Utilizando ruta por defecto '{}' para almacenar informes", rutaPorDefecto);
                        return rutaPorDefecto.toString();
                }
                return null;
        }

        private Path localizarRutaInformesPorDefecto() {
                Path rutaConfigurada = localizarRutaConfiguradaManualmente();
                if (rutaConfigurada != null) {
                        return rutaConfigurada;
                }

                String[] posiblesHomes = new String[] {
                                System.getProperty("comerzzia.home"),
                                System.getProperty("COMERZZIA_HOME"),
                                System.getenv("COMERZZIA_HOME")
                };
                for (String home : posiblesHomes) {
                        if (StringUtils.isBlank(home)) {
                                continue;
                        }
                        Path candidato = Paths.get(home, "informes");
                        if (home.contains("${")) {
                                continue;
                        }
                        return candidato;
                }

                String homeUsuario = System.getProperty("user.home");
                if (StringUtils.isNotBlank(homeUsuario)) {
                        return Paths.get(homeUsuario, ".comerzzia", "informes");
                }

                return null;
        }

        private Path localizarRutaConfiguradaManualmente() {
                String[] propiedades = new String[] {
                                System.getProperty("bricodepot.facturas.dir"),
                                System.getProperty("bricodepot.informes.dir"),
                                System.getenv("BRICODEPOT_FACTURAS_DIR"),
                                System.getenv("BRICODEPOT_INFORMES_DIR")
                };

                for (String valor : propiedades) {
                        if (StringUtils.isBlank(valor)) {
                                continue;
                        }
                        String rutaNormalizada = valor.trim();
                        if (rutaNormalizada.isEmpty()) {
                                continue;
                        }
                        try {
                                Path ruta = Paths.get(rutaNormalizada);
                                if (ruta.toFile().exists() || ruta.getParent() != null) {
                                        return ruta;
                                }
                        }
                        catch (Exception excepcion) {
                                LOGGER.warn("localizarRutaConfiguradaManualmente() - Ruta manual '{}' inválida", valor, excepcion);
                        }
                }

                String userDir = System.getProperty("user.dir");
                if (StringUtils.isNotBlank(userDir)) {
                        Path rutaProyecto = Paths.get(userDir, "src", "main", "resources");
                        if (Files.exists(rutaProyecto)) {
                                return rutaProyecto;
                        }
                }

                return null;
        }
}