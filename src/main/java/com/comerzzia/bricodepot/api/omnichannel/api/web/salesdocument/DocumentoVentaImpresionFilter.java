package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

/**
 * Jersey request filter that intercepts the standard omnichannel sales document
 * printing endpoint so that the Brico custom implementation is used instead of
 * the default one delivered with the product.
 */
@Provider
@PreMatching
public class DocumentoVentaImpresionFilter implements ContainerRequestFilter {

    private static final String PARAM_MIME_TYPE = "mimeType";
    private static final String PARAM_COPY = "copy";
    private static final String PARAM_INLINE = "inline";
    private static final String PARAM_OUTPUT_NAME = "outputDocumentName";
    private static final String PARAM_PRINT_TEMPLATE = "printTemplate";
    private static final String DEFAULT_MIME_TYPE = "application/pdf";

    private final DocumentoVentaImpresionServicio servicioImpresion;

    public DocumentoVentaImpresionFilter(DocumentoVentaImpresionServicio servicioImpresion) {
        this.servicioImpresion = servicioImpresion;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!HttpMethod.GET.equals(requestContext.getMethod())) {
            return;
        }

        List<PathSegment> segments = requestContext.getUriInfo().getPathSegments();
        if (segments.size() != 3) {
            return;
        }

        if (!"salesdocument".equalsIgnoreCase(segments.get(0).getPath())) {
            return;
        }

        if (!"print".equalsIgnoreCase(segments.get(2).getPath())) {
            return;
        }

        String documentUid = segments.get(1).getPath();
        if (documentUid == null || documentUid.trim().isEmpty()) {
            throw new WebApplicationException("Document uid is required", Response.Status.BAD_REQUEST);
        }

        Map<String, String> rawParams = toSingleValueMap(requestContext.getUriInfo().getQueryParameters());

        String mimeType = valueOrDefault(rawParams.remove(PARAM_MIME_TYPE), DEFAULT_MIME_TYPE);
        boolean copy = parseBoolean(rawParams.remove(PARAM_COPY));
        boolean inline = parseBoolean(rawParams.remove(PARAM_INLINE));
        String outputDocumentName = emptyToNull(rawParams.remove(PARAM_OUTPUT_NAME));
        String printTemplate = emptyToNull(rawParams.remove(PARAM_PRINT_TEMPLATE));

        Map<String, String> customParams = rawParams.isEmpty() ? Collections.emptyMap() : new HashMap<>(rawParams);

        OpcionesImpresionDocumentoVenta opciones = new OpcionesImpresionDocumentoVenta(
                mimeType,
                copy,
                inline,
                outputDocumentName,
                printTemplate,
                customParams);

        Optional<DocumentoVentaImpresionResultado> respuesta = servicioImpresion.imprimir(documentUid, opciones);

        if (!respuesta.isPresent()) {
            requestContext.abortWith(Response.status(Response.Status.NO_CONTENT).build());
            return;
        }

        DocumentoVentaImpresionResultado documento = respuesta.get();
        byte[] contenido = documento.getContenido();
        ResponseBuilder responseBuilder = Response.ok(contenido);
        responseBuilder.type(valueOrDefault(documento.getTipoMime(), MediaType.APPLICATION_OCTET_STREAM));
        if (contenido != null) {
            responseBuilder.header("Content-Length", contenido.length);
        }
        String contentDisposition = buildContentDisposition(documento);
        if (contentDisposition != null) {
            responseBuilder.header("Content-Disposition", contentDisposition);
        }

        requestContext.abortWith(responseBuilder.build());
    }

    private String buildContentDisposition(DocumentoVentaImpresionResultado documento) {
        String nombre = emptyToNull(documento.getNombreArchivo());
        String tipo = documento.isEnLinea() ? "inline" : "attachment";
        if (nombre == null) {
            return tipo;
        }
        String sanitized = nombre.replace("\"", "").replace("\n", "").replace("\r", "");
        StringBuilder builder = new StringBuilder(tipo).append("; filename=\"").append(sanitized).append("\"");
        String encoded = encodeUtf8(sanitized);
        builder.append("; filename*=UTF-8''").append(encoded);
        return builder.toString();
    }

    private String encodeUtf8(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not supported", e);
        }
    }

    private Map<String, String> toSingleValueMap(javax.ws.rs.core.MultivaluedMap<String, String> parameters) {
        Map<String, String> singleValueMap = new HashMap<>();
        parameters.forEach((key, values) -> {
            if (!values.isEmpty()) {
                singleValueMap.put(key, values.get(0));
            } else {
                singleValueMap.put(key, null);
            }
        });
        return singleValueMap;
    }

    private String valueOrDefault(String value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? defaultValue : trimmed;
    }

    private boolean parseBoolean(String value) {
        return value != null && Boolean.parseBoolean(value.trim());
    }

    private String emptyToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
