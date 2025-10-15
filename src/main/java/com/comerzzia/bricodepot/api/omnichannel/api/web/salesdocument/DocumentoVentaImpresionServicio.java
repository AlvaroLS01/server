package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
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
                        DocumentoVentaImpresionRespuesta respuesta = construirRespuesta(uidDocumento, opciones, configuracionImpresion, contenido);
                        return Optional.of(respuesta);
                }
                catch (NotFoundException excepcion) {
                        LOGGER.debug("imprimir() - No se encontró el documento de venta con uid '{}'", uidDocumento, excepcion);
                        return Optional.empty();
                }
                catch (ApiException excepcion) {
                        throw new DocumentoVentaImpresionException("No fue posible generar el documento de venta", excepcion);
                }
                catch (DocumentoVentaImpresionException excepcion) {
                        throw excepcion;
                }
                catch (Exception excepcion) {
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
}
