package com.comerzzia.bricodepot.api.omnichannel.api.services.documentprint;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.comerzzia.core.util.config.AppInfo;
import com.comerzzia.omnichannel.service.documentprint.jasper.JasperPrintServiceImpl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

@Service
@Primary
public class BricodepotDocumentPrintService extends JasperPrintServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(BricodepotDocumentPrintService.class);
	private static final Set<String> COMPILED = ConcurrentHashMap.newKeySet();

	private static final String DEFAULT_TEMPLATE_PATH = "ventas/facturas/facturaA4_Original";

	private static final Pattern PROPERTY_PATTERN = Pattern.compile("\\$P\\{ticket\\}\\.getCabecera\\(\\)\\.getFiscalData\\(\\)\\.getProperty\\(\\\"ATCUD\\\"\\)");
	private static final Pattern PROPERTY_VALUE_PATTERN = Pattern
	        .compile("\\$P\\{ticket\\}\\.getCabecera\\(\\)\\.getFiscalData\\(\\)\\.getProperty(?:Value)?\\(\\\"ATCUD\\\"\\)(?:\\.getValue\\(\\))?");

	protected File getTemplate(String template, String localeId) {
		String decided = decideTemplate(template);
		File jasper = super.getTemplateLocaleFile(decided, localeId);
		compileIfNeeded(jasper);

		if (!jasper.exists()) {
			File alt = resolveFromReportsBase(decided, localeId);
			compileIfNeeded(alt);
			if (alt.exists()) {
				jasper = alt;
			}
		}

		if (jasper != null) {
			LOGGER.debug("getTemplate() - Using template: {}", jasper.getAbsolutePath());
		}
		return jasper;
	}

	@Override
	protected File getTemplateLocaleFile(String template, String localeId) {
		String decided = decideTemplate(template);
		File jasper = super.getTemplateLocaleFile(decided, localeId);
		compileIfNeeded(jasper);

		if (!jasper.exists()) {
			File alt = resolveFromReportsBase(decided, localeId);
			compileIfNeeded(alt);
			if (alt.exists()) {
				jasper = alt;
			}
		}

		if (jasper != null) {
			LOGGER.debug("getTemplate() - Using template: {}", jasper.getAbsolutePath());
		}
		return jasper;
	}

	private String decideTemplate(String template) {
		if (StringUtils.isBlank(template)) {
			return DEFAULT_TEMPLATE_PATH;
		}
		String t = template.replace('\\', '/').trim();

		if (!t.contains("/")) {
			LOGGER.debug("Ignoring short alias '{}' and forcing default template '{}'", t, DEFAULT_TEMPLATE_PATH);
			return DEFAULT_TEMPLATE_PATH;
		}

		if (t.equals("ventas/facturas/facturaA4") || t.endsWith("/facturaA4")) {
			return "ventas/facturas/facturaA4_Original";
		}
		return t;
	}

	private void compileIfNeeded(File jasperFile) {
		if (jasperFile == null)
			return;

		String jasperPath = jasperFile.getAbsolutePath();
		if (COMPILED.contains(jasperPath))
			return;

		File jrxml = toJrxml(jasperFile);
		if (jrxml == null || !jrxml.exists()) {
			COMPILED.add(jasperPath);
			return;
		}

		ensurePortugueseCompatibility(jrxml);

		ensureParentDir(jasperFile);
		try {
			JasperCompileManager.compileReportToFile(jrxml.getAbsolutePath(), jasperPath);
			COMPILED.add(jasperPath);
		}
		catch (JRException e) {
			LOGGER.warn("Failed to compile Jasper template '{}' from '{}'", jasperPath, jrxml.getAbsolutePath(), e);
		}
	}

	private void ensurePortugueseCompatibility(File jrxml) {
		String name = jrxml.getName();
		if (!"facturaA4_PT.jrxml".equalsIgnoreCase(name) && !"facturaDevolucionA4_PT.jrxml".equalsIgnoreCase(name)) {
			return;
		}
		try {
			String content = new String(Files.readAllBytes(jrxml.toPath()), StandardCharsets.UTF_8);
			String updated = patchAtcud(content);
			if (!updated.equals(content)) {
				Files.write(jrxml.toPath(), updated.getBytes(StandardCharsets.UTF_8));
				LOGGER.debug("ensurePortugueseCompatibility() - Patched fiscal data in '{}'", jrxml.getAbsolutePath());
			}
		}
		catch (IOException e) {
			LOGGER.warn("ensurePortugueseCompatibility() - Unable to adjust '{}'", jrxml.getAbsolutePath(), e);
		}
	}

	private String patchAtcud(String xml) {
		if (xml == null)
			return "";

		if (!xml.contains("name=\"fiscalData_ACTUD\"")) {
			xml = injectFiscalParams(xml);
		}
		else if (!xml.contains("name=\"fiscalData_QR\"")) {
			xml = xml.replace("name=\"fiscalData_ACTUD\" class=\"java.lang.String\"/>",
			        "name=\"fiscalData_ACTUD\" class=\"java.lang.String\"/>\n        <parameter name=\"fiscalData_QR\" class=\"java.lang.String\"/>");
		}

		String repl = Matcher.quoteReplacement("$P{fiscalData_ACTUD}");
		xml = PROPERTY_VALUE_PATTERN.matcher(xml).replaceAll(repl);
		xml = PROPERTY_PATTERN.matcher(xml).replaceAll(repl);

		return xml;
	}

	private String injectFiscalParams(String xml) {
		String marker = "<parameter name=\"ticket\"";
		int idx = xml.indexOf(marker);
		if (idx < 0)
			return xml;

		int insert = xml.indexOf('\n', idx);
		if (insert < 0)
			insert = idx + marker.length();

		StringBuilder sb = new StringBuilder(xml.length() + 160);
		sb.append(xml, 0, insert + 1);
		sb.append("        <parameter name=\"fiscalData_ACTUD\" class=\"java.lang.String\"/>\n");
		sb.append("        <parameter name=\"fiscalData_QR\" class=\"java.lang.String\"/>\n");
		sb.append(xml.substring(insert + 1));
		return sb.toString();
	}

	private File resolveFromReportsBase(String template, String localeId) {
		String base = AppInfo.getInformesInfo() != null ? AppInfo.getInformesInfo().getRutaBase() : null;
		if (StringUtils.isBlank(base))
			return new File(template + getTemplateExtension());

		if (!base.endsWith(File.separator))
			base = base + File.separator;

		String t = template.replace('\\', '/');
		String locale = localeId != null ? localeId.toLowerCase(Locale.ROOT) : null;
		String ext = getTemplateExtension();

		if (StringUtils.isNotBlank(locale)) {
			File f = new File(base + t + "_" + locale + ext);
			if (f.exists())
				return f;
			if (locale.length() >= 2) {
				f = new File(base + t + "_" + locale.substring(0, 2) + ext);
				if (f.exists())
					return f;
			}
		}
		return new File(base + t + ext);
	}

	private File toJrxml(File jasperFile) {
		if (jasperFile == null)
			return null;
		String p = jasperFile.getAbsolutePath();
		if (StringUtils.isBlank(p))
			return null;
		return new File(StringUtils.substringBeforeLast(p, ".") + ".jrxml");
	}

	private void ensureParentDir(File f) {
		File parent = f.getParentFile();
		if (parent != null && !parent.exists())
			parent.mkdirs();
	}
}
