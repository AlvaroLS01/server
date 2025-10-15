package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.omnichannel.web.model.document.PrintDocumentRequest;
import com.comerzzia.api.omnichannel.web.rest.salesdoc.SalesDocumentResource;
import com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument.DocumentoVentaImpresionException;

@Controller("salesDocumentResource")
@Path("/salesdocument")
public class DocumentoVentaImpresionController extends SalesDocumentResource {

        private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoVentaImpresionController.class);

        private final DocumentoVentaImpresionServicio servicioImpresion;

        @Autowired
        public DocumentoVentaImpresionController(DocumentoVentaImpresionServicio servicioImpresion) {
                this.servicioImpresion = servicioImpresion;
        }

        @Override
        @GET
        @Path("/{documentUid}/print")
        @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML, "application/pdf", "application/jasperprint" })
        public Response printSaleDocumentByUid(@PathParam("documentUid") String uidDocumento,
                @Context HttpServletRequest request,
                @Context HttpServletResponse response,
                @Valid @BeanParam PrintDocumentRequest peticionImpresion) throws ApiException {

                PrintDocumentRequest parametrosPeticion = peticionImpresion != null ? peticionImpresion : new PrintDocumentRequest();
                String mimeSolicitado = normalizarMime(parametrosPeticion.getMimeType(), request != null ? request.getContentType() : null);
                boolean esCopia = Boolean.TRUE.equals(parametrosPeticion.getCopy());
                boolean enLinea = Boolean.TRUE.equals(parametrosPeticion.getInline());
                String nombreSalida = normalizarNombreSalida(parametrosPeticion.getOutputDocumentName(), uidDocumento);
                String plantilla = parametrosPeticion.getPrintTemplate();
                Map<String, String> parametrosPersonalizados = obtenerParametrosPersonalizados(parametrosPeticion);

                OpcionesImpresionDocumentoVenta opciones = new OpcionesImpresionDocumentoVenta(mimeSolicitado, esCopia, enLinea,
                                nombreSalida, plantilla, parametrosPersonalizados);

                Optional<DocumentoVentaImpresionRespuesta> posibleDocumento;
                try {
                        posibleDocumento = servicioImpresion.imprimir(uidDocumento, opciones);
                }
                catch (DocumentoVentaImpresionException excepcion) {
                        throw excepcion;
                }
                catch (RuntimeException excepcion) {
                        throw new WebApplicationException("Error creando el PDF del documento" +
                                        (excepcion.getMessage() != null ? ": " + excepcion.getMessage() : ""), excepcion);
                }
                if (!posibleDocumento.isPresent()) {
                        return Response.ok().entity(null).build();
                }

                DocumentoVentaImpresionRespuesta documento = posibleDocumento.get();
                byte[] contenido = decodificar(documento.getDocumento());

                StreamingOutput flujo = output -> escribirPdf(output, contenido);

                String tipoRespuesta = normalizarMime(documento.getTipoMime(), mimeSolicitado);
                String nombreRespuesta = normalizarNombreSalida(documento.getNombreArchivo(), nombreSalida);
                String disposicion = documento.isEnLinea() ? "inline" : "attachment";

                return Response.ok(flujo, tipoRespuesta)
                                .header("Content-disposition", disposicion + "; filename = " + nombreRespuesta)
                                .build();
        }

        private void escribirPdf(java.io.OutputStream output, byte[] contenido) {
                try {
                        output.write(contenido);
                        output.flush();
                }
                catch (IOException excepcion) {
                        LOGGER.error("Error escribiendo el PDF generado", excepcion);
                        throw new WebApplicationException("Error escribiendo el PDF generado", excepcion);
                }
        }

        private String normalizarMime(String mimeSolicitado, String valorAlternativo) {
                if (StringUtils.isNotBlank(mimeSolicitado)) {
                        return mimeSolicitado;
                }
                if (StringUtils.isNotBlank(valorAlternativo)) {
                        return valorAlternativo;
                }
                return "application/pdf";
        }

        private String normalizarNombreSalida(String nombreSolicitado, String valorAlternativo) {
                String candidato = StringUtils.isNotBlank(nombreSolicitado) ? nombreSolicitado : valorAlternativo;
                if (StringUtils.isBlank(candidato)) {
                        return "documento.pdf";
                }
                String nombreNormalizado = candidato.trim();
                if (!nombreNormalizado.toLowerCase(Locale.ROOT).endsWith(".pdf")) {
                        nombreNormalizado = nombreNormalizado + ".pdf";
                }
                return nombreNormalizado;
        }

        private Map<String, String> obtenerParametrosPersonalizados(PrintDocumentRequest peticionImpresion) {
                Map<String, String> parametros = peticionImpresion.getCustomParams();
                if (parametros == null || parametros.isEmpty()) {
                        return Collections.emptyMap();
                }
                return Collections.unmodifiableMap(new HashMap<>(parametros));
        }

        private byte[] decodificar(String documentoBase64) {
                if (StringUtils.isBlank(documentoBase64)) {
                        return new byte[0];
                }
                return Base64.getDecoder().decode(documentoBase64);
        }
}
