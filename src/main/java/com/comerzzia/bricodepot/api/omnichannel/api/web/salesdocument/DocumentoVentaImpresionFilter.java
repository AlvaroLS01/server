package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentoVentaImpresionFilter implements ContainerRequestFilter {

        private static final Logger log = LoggerFactory.getLogger(DocumentoVentaImpresionFilter.class);

        private final DocumentoVentaImpresionServicio servicioImpresion;

        public DocumentoVentaImpresionFilter(DocumentoVentaImpresionServicio servicioImpresion) {
                this.servicioImpresion = servicioImpresion;
        }

        @Override
        public void filter(ContainerRequestContext requestContext) throws IOException {
                if (!isDocumentoVentaPrint(requestContext)) {
                        return;
                }

                String documentUid = extractDocumentUid(requestContext);

                if (StringUtils.isBlank(documentUid)) {
                        return;
                }

                OpcionesImpresionDocumentoVenta opciones = buildOpciones(requestContext.getUriInfo().getQueryParameters());

                log.debug("filter() - Interceptando impresión de documento {}", documentUid);

                try {
                        Optional<DocumentoVentaImpresionRespuesta> respuesta = servicioImpresion.imprimir(documentUid, opciones);
                        Response response = Response.ok(respuesta.orElse(null), MediaType.APPLICATION_JSON_TYPE).build();
                        requestContext.abortWith(response);
                }
                catch (Exception e) {
                        log.error("filter() - Error generando la impresión del documento {}", documentUid, e);
                        requestContext.abortWith(Response.serverError().entity(null).build());
                }
        }

        protected boolean isDocumentoVentaPrint(ContainerRequestContext requestContext) {
                if (!"GET".equalsIgnoreCase(requestContext.getMethod())) {
                        return false;
                }

                List<PathSegment> segments = requestContext.getUriInfo().getPathSegments(false);

                if (segments.size() < 3) {
                        return false;
                }

                String first = segments.get(0).getPath();
                String last = segments.get(segments.size() - 1).getPath();

                return "salesdocument".equalsIgnoreCase(first) && "print".equalsIgnoreCase(last);
        }

        protected String extractDocumentUid(ContainerRequestContext requestContext) {
                List<PathSegment> segments = requestContext.getUriInfo().getPathSegments(false);

                if (segments.size() >= 3) {
                        return segments.get(1).getPath();
                }

                return null;
        }

        protected OpcionesImpresionDocumentoVenta buildOpciones(MultivaluedMap<String, String> parametros) {
                String mimeType = parametros.getFirst("mimeType");
                boolean copy = Boolean.parseBoolean(parametros.getFirst("copy"));
                boolean inline = Boolean.parseBoolean(parametros.getFirst("inline"));
                String outputName = parametros.getFirst("outputDocumentName");
                String plantilla = parametros.getFirst("printTemplate");

                Map<String, String> personalizados = new HashMap<>();
                for (Map.Entry<String, List<String>> entry : parametros.entrySet()) {
                        if (isParametroReservado(entry.getKey())) {
                                continue;
                        }
                        if (!entry.getValue().isEmpty()) {
                                personalizados.put(entry.getKey(), entry.getValue().get(0));
                        }
                }

                return new OpcionesImpresionDocumentoVenta(mimeType, copy, inline, outputName, plantilla, personalizados);
        }

        private boolean isParametroReservado(String parametro) {
                return "mimeType".equalsIgnoreCase(parametro) || "copy".equalsIgnoreCase(parametro)
                                || "inline".equalsIgnoreCase(parametro) || "outputDocumentName".equalsIgnoreCase(parametro)
                                || "printTemplate".equalsIgnoreCase(parametro);
        }
}
