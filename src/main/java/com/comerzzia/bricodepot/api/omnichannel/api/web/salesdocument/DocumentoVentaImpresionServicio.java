package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.util.Base64;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DocumentoVentaImpresionServicio {

        private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoVentaImpresionServicio.class);
        private static final String MIMETYPE_POR_DEFECTO = "application/pdf";

        private final GeneradorFacturaA4 generadorFacturaA4;

        public DocumentoVentaImpresionServicio(GeneradorFacturaA4 generadorFacturaA4) {
                this.generadorFacturaA4 = generadorFacturaA4;
        }

        public Optional<DocumentoVentaImpresionRespuesta> imprimir(String uidDocumento, OpcionesImpresionDocumentoVenta opciones) {
                String uidNormalizado = normalizarUid(uidDocumento);
                if (uidNormalizado == null) {
                        throw new DocumentoVentaImpresionException("El identificador del documento es obligatorio");
                }

                String mimeType = normalizarMimeType(opciones != null ? opciones.getTipoMime() : null);
                if (!MIMETYPE_POR_DEFECTO.equalsIgnoreCase(mimeType)) {
                        throw new DocumentoVentaImpresionException("Solo se admite la generación en formato PDF");
                }

                OpcionesImpresionDocumentoVenta opcionesSeguras = opciones != null ? opciones :
                        new OpcionesImpresionDocumentoVenta(MIMETYPE_POR_DEFECTO, false, false, null, null, null);

                try {
                        Optional<GeneradorFacturaA4.FacturaGenerada> facturaGenerada = generadorFacturaA4.generarFactura(uidNormalizado, opcionesSeguras);
                        if (!facturaGenerada.isPresent()) {
                                LOGGER.debug("imprimir() - No se encontró el documento de venta con uid '{}'", uidNormalizado);
                                return Optional.empty();
                        }

                        GeneradorFacturaA4.FacturaGenerada resultado = facturaGenerada.get();
                        DocumentoVentaImpresionRespuesta respuesta = new DocumentoVentaImpresionRespuesta();
                        respuesta.setUidDocumento(uidNormalizado);
                        respuesta.setTipoMime(MIMETYPE_POR_DEFECTO);
                        respuesta.setCopia(opcionesSeguras.esCopia());
                        respuesta.setEnLinea(opcionesSeguras.esEnLinea());
                        respuesta.setNombreArchivo(normalizarNombreArchivo(resultado.getNombreFichero()));
                        respuesta.setDocumento(Base64.getEncoder().encodeToString(resultado.getContenidoPdf()));
                        return Optional.of(respuesta);
                }
                catch (DocumentoVentaImpresionException excepcion) {
                        throw excepcion;
                }
                catch (Exception excepcion) {
                        LOGGER.error("imprimir() - Error inesperado generando el documento de venta '{}'", uidNormalizado, excepcion);
                        throw new DocumentoVentaImpresionException("No fue posible generar el documento de venta", excepcion);
                }
        }

        private String normalizarUid(String uidDocumento) {
                if (uidDocumento == null) {
                        return null;
                }
                String texto = uidDocumento.trim();
                return texto.isEmpty() ? null : texto;
        }

        private String normalizarMimeType(String mimeTypeSolicitado) {
                if (mimeTypeSolicitado == null) {
                        return MIMETYPE_POR_DEFECTO;
                }
                String normalizado = mimeTypeSolicitado.trim();
                if (normalizado.isEmpty()) {
                        return MIMETYPE_POR_DEFECTO;
                }
                return normalizado.toLowerCase(Locale.ROOT);
        }

        private String normalizarNombreArchivo(String nombreArchivo) {
                String nombreNormalizado = nombreArchivo;
                if (nombreNormalizado == null || nombreNormalizado.trim().isEmpty()) {
                        nombreNormalizado = "documento.pdf";
                }
                else if (!nombreNormalizado.toLowerCase(Locale.ROOT).endsWith(".pdf")) {
                        nombreNormalizado = nombreNormalizado + ".pdf";
                }
                return nombreNormalizado;
        }
}
