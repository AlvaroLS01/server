package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.metadata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.entity.document.DocumentEntity;
import com.comerzzia.omnichannel.service.salesdocument.metadata.DocumentMetadata;
import com.comerzzia.omnichannel.service.salesdocument.metadata.DocumentMetadataParser;

@Component
@Primary
public class BricodepotDocumentMetadataParser extends DocumentMetadataParser {

	private static final String DEFAULT_FACTURA_TEMPLATE = "ventas/facturas/facturaA4";

	private static final Map<String, String> TEMPLATE_ALIASES;

	static {
		Map<String, String> aliases = new HashMap<>();
		registerAlias(aliases, "FT", DEFAULT_FACTURA_TEMPLATE);
		registerAlias(aliases, "FS", DEFAULT_FACTURA_TEMPLATE);
		registerAlias(aliases, "NC", DEFAULT_FACTURA_TEMPLATE);
		registerAlias(aliases, "VC", DEFAULT_FACTURA_TEMPLATE);
		registerAlias(aliases, "FR", DEFAULT_FACTURA_TEMPLATE);
		registerAlias(aliases, "facturaA4", DEFAULT_FACTURA_TEMPLATE);
		registerAlias(aliases, "factura", DEFAULT_FACTURA_TEMPLATE);
		TEMPLATE_ALIASES = Collections.unmodifiableMap(aliases);
	}

	@Override
	public DocumentMetadata getMetadata(IDatosSesion datosSesion, DocumentEntity document) {
		DocumentMetadata metadata = super.getMetadata(datosSesion, document);
		applyTemplateOverride(metadata);
		return metadata;
	}

	@Override
	public DocumentMetadata getMetadata(IDatosSesion datosSesion, byte[] documentContent) {
		DocumentMetadata metadata = super.getMetadata(datosSesion, documentContent);
		applyTemplateOverride(metadata);
		return metadata;
	}

	private void applyTemplateOverride(DocumentMetadata metadata) {
		if (metadata == null) {
			return;
		}

		String currentTemplate = metadata.getPrintTemplate();
		if (StringUtils.isNotBlank(currentTemplate) && !isGenericTemplateName(currentTemplate)) {
			return;
		}

		String template = resolveAlias(currentTemplate);
		if (StringUtils.isBlank(template)) {
			template = resolveAlias(metadata.getDocTypeCode());
		}

		if (StringUtils.isBlank(template)) {
			String normalizedDocType = normalizeKey(metadata.getDocTypeCode());
			if (StringUtils.isNotBlank(normalizedDocType) && normalizedDocType.startsWith("FT")) {
				template = DEFAULT_FACTURA_TEMPLATE;
			}
		}

		if (StringUtils.isNotBlank(template)) {
			metadata.setPrintTemplate(template);
		}
	}

	private static void registerAlias(Map<String, String> target, String key, String value) {
		String normalized = normalizeKey(key);
		if (normalized != null) {
			target.put(normalized, value);
		}
	}

	private static String normalizeKey(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}

		String trimmed = key.trim();
		trimmed = StringUtils.substringBefore(trimmed, "/");
		trimmed = StringUtils.substringBefore(trimmed, " ");

		return trimmed.toUpperCase(Locale.ROOT);
	}

	private String resolveAlias(String key) {
		String normalized = normalizeKey(key);
		if (normalized == null) {
			return null;
		}
		return TEMPLATE_ALIASES.get(normalized);
	}

	private boolean isGenericTemplateName(String template) {
		return StringUtils.isNotBlank(template) && !StringUtils.containsAny(template, '/', '\\');
	}
}
