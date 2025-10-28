package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
@Provider
public class DocumentoVentaImpresionFilter implements ContainerRequestFilter, ContainerResponseFilter {

	private static final String DOCUMENT_UID_PARAM = "documentUid";
	private static final String START_TIME_ATTRIBUTE = DocumentoVentaImpresionFilter.class.getName() + ".start";

	private final DocumentoVentaImpresionServicio servicioImpresion;

	public DocumentoVentaImpresionFilter(DocumentoVentaImpresionServicio servicioImpresion) {
		this.servicioImpresion = servicioImpresion;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (!isSalesDocumentPrintRequest(requestContext)) {
			return;
		}

		requestContext.setProperty(START_TIME_ATTRIBUTE, Instant.now());
		servicioImpresion.registrarInicioImpresion(resolveDocumentUid(requestContext));
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		if (!isSalesDocumentPrintRequest(requestContext)) {
			return;
		}

		Object value = requestContext.getProperty(START_TIME_ATTRIBUTE);
		Instant startInstant = value instanceof Instant ? (Instant) value : null;
		servicioImpresion.registrarFinImpresion(resolveDocumentUid(requestContext), responseContext.getStatus(), startInstant);
	}

	private boolean isSalesDocumentPrintRequest(ContainerRequestContext requestContext) {
		if (requestContext == null || requestContext.getUriInfo() == null) {
			return false;
		}
		String path = requestContext.getUriInfo().getPath();
		return path != null && path.startsWith("salesdocument/") && path.endsWith("/print");
	}

	private String resolveDocumentUid(ContainerRequestContext requestContext) {
		if (requestContext == null || requestContext.getUriInfo() == null) {
			return null;
		}

		MultivaluedMap<String, String> pathParameters = requestContext.getUriInfo().getPathParameters();
		if (pathParameters == null) {
			return null;
		}

		List<String> values = pathParameters.get(DOCUMENT_UID_PARAM);
		if (!CollectionUtils.isEmpty(values)) {
			return values.get(0);
		}

		// Fallback in case the parameter name is not exposed for some reason
		for (Map.Entry<String, List<String>> entry : pathParameters.entrySet()) {
			if (!CollectionUtils.isEmpty(entry.getValue())) {
				String candidate = entry.getValue().get(0);
				if (StringUtils.hasText(candidate)) {
					return candidate;
				}
			}
		}

		return null;
	}
}
