package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Collections;
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

    private final SaleDocumentService saleDocumentService;
    private final ComerzziaDatosSesion datosSesionRequest;

    public DocumentoVentaImpresionServicioImpl(SaleDocumentService saleDocumentService,
            @Qualifier("datosSesionRequest") ComerzziaDatosSesion datosSesionRequest) {
        this.saleDocumentService = saleDocumentService;
        this.datosSesionRequest = datosSesionRequest;
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
            printRequest.setPrintTemplate(opciones.getPlantillaImpresion().trim());
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
}
