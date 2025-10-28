package com.comerzzia.bricodepot.api.omnichannel.api.services.documentprint;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
	private static final Set<String> COMPILED_TEMPLATES = ConcurrentHashMap.newKeySet();
	private static final ConcurrentMap<String, File> TEMPLATE_RESOLUTION_CACHE = new ConcurrentHashMap<>();

	@Override
	protected File getTemplateLocaleFile(String template, String localeId) {
		File templateFile = super.getTemplateLocaleFile(template, localeId);
		compileTemplateIfNecessary(templateFile);
		if (templateFile.exists()) {
			return templateFile;
		}

		File alternative = resolveFromReportsDirectory(template, localeId);
		compileTemplateIfNecessary(alternative);
		if (alternative.exists()) {
			return alternative;
		}

		return templateFile;
	}

	private void compileTemplateIfNecessary(File jasperFile) {
		if (jasperFile == null) {
			return;
		}

		String absolutePath = jasperFile.getAbsolutePath();
		if (COMPILED_TEMPLATES.contains(absolutePath)) {
			return;
		}

		File jrxmlFile = toJrxmlFile(jasperFile);
		if (jrxmlFile == null || !jrxmlFile.exists()) {
			COMPILED_TEMPLATES.add(absolutePath);
			return;
		}

		ensurePortugueseTemplateCompatibility(jrxmlFile);
		ensureParentDirectory(jasperFile);

		try {
			JasperCompileManager.compileReportToFile(jrxmlFile.getAbsolutePath(), jasperFile.getAbsolutePath());
			COMPILED_TEMPLATES.add(absolutePath);
			compileSiblingTemplates(jrxmlFile.getParentFile(), jrxmlFile);
		}
		catch (JRException exception) {
			LOGGER.warn("Failed to compile Jasper template '{}' from '{}'", absolutePath, jrxmlFile.getAbsolutePath(), exception);
		}
	}

	private void ensurePortugueseTemplateCompatibility(File jrxmlFile) {
		if (jrxmlFile == null) {
			return;
		}

		String name = jrxmlFile.getName();
		if (!"facturaA4_PT.jrxml".equalsIgnoreCase(name) && !"facturaDevolucionA4_PT.jrxml".equalsIgnoreCase(name)) {
			return;
		}

		try {
			String content = new String(Files.readAllBytes(jrxmlFile.toPath()), StandardCharsets.UTF_8);
			String updatedContent = patchAtcudExpressions(content);

			if (!updatedContent.equals(content)) {
				Files.write(jrxmlFile.toPath(), updatedContent.getBytes(StandardCharsets.UTF_8));
				LOGGER.debug("ensurePortugueseTemplateCompatibility() - Patched fiscal data expressions in '{}'", jrxmlFile.getAbsolutePath());
			}
		}
		catch (IOException exception) {
			LOGGER.warn("ensurePortugueseTemplateCompatibility() - Unable to adjust template '{}'", jrxmlFile.getAbsolutePath(), exception);
		}
	}

	private String patchAtcudExpressions(String content) {
		if (content == null) {
			return "";
		}

		String result = content;
		if (!result.contains("name=\"fiscalData_ACTUD\"")) {
			result = injectFiscalParameters(result);
		}
		else if (!result.contains("name=\"fiscalData_QR\"")) {
			result = result.replace("name=\"fiscalData_ACTUD\" class=\"java.lang.String\"/>",
			        "name=\"fiscalData_ACTUD\" class=\"java.lang.String\"/>\n        <parameter name=\"fiscalData_QR\" class=\"java.lang.String\"/>");
		}

		result = PROPERTY_VALUE_PATTERN.matcher(result).replaceAll("$P{fiscalData_ACTUD}");
		result = PROPERTY_PATTERN.matcher(result).replaceAll("$P{fiscalData_ACTUD}");

		return result;
	}

	private String injectFiscalParameters(String content) {
		String marker = "<parameter name=\"ticket\"";
		int markerIndex = content.indexOf(marker);
		if (markerIndex < 0) {
			return content;
		}

		int insertIndex = content.indexOf('\n', markerIndex);
		if (insertIndex < 0) {
			insertIndex = markerIndex + marker.length();
		}

		StringBuilder builder = new StringBuilder(content.length() + 160);
		builder.append(content, 0, insertIndex + 1);
		builder.append("        <parameter name=\"fiscalData_ACTUD\" class=\"java.lang.String\"/>\n");
		builder.append("        <parameter name=\"fiscalData_QR\" class=\"java.lang.String\"/>\n");
		builder.append(content.substring(insertIndex + 1));
		return builder.toString();
	}

	private void compileSiblingTemplates(File directory, File primaryJrxml) {
		if (directory == null || !directory.isDirectory()) {
			return;
		}

		File[] jrxmlFiles = directory.listFiles(new JrxmlFilter());
		if (jrxmlFiles == null) {
			return;
		}

		for (File jrxmlFile : jrxmlFiles) {
			if (primaryJrxml != null && primaryJrxml.equals(jrxmlFile)) {
				continue;
			}
			File jasperFile = toJasperFile(jrxmlFile);
			if (jasperFile == null) {
				continue;
			}
			ensureParentDirectory(jasperFile);

			try {
				if (!jasperFile.exists() || jrxmlFile.lastModified() >= jasperFile.lastModified()) {
					JasperCompileManager.compileReportToFile(jrxmlFile.getAbsolutePath(), jasperFile.getAbsolutePath());
				}
				COMPILED_TEMPLATES.add(jasperFile.getAbsolutePath());
			}
			catch (JRException exception) {
				LOGGER.warn("Failed to compile Jasper subreport '{}'", jrxmlFile.getAbsolutePath(), exception);
			}
		}
	}

	private void ensureParentDirectory(File jasperFile) {
		File parent = jasperFile.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
	}

	private File toJrxmlFile(File jasperFile) {
		String absolutePath = jasperFile != null ? jasperFile.getAbsolutePath() : null;
		if (StringUtils.isBlank(absolutePath)) {
			return null;
		}

		return new File(StringUtils.substringBeforeLast(absolutePath, ".") + ".jrxml");
	}

	private File toJasperFile(File jrxmlFile) {
		String absolutePath = jrxmlFile != null ? jrxmlFile.getAbsolutePath() : null;
		if (StringUtils.isBlank(absolutePath)) {
			return null;
		}

		String jasperPath;
		if (StringUtils.endsWithIgnoreCase(absolutePath, ".jrxml")) {
			jasperPath = StringUtils.substringBeforeLast(absolutePath, ".") + getTemplateExtension();
		}
		else {
			jasperPath = absolutePath + getTemplateExtension();
		}

		return new File(jasperPath);
	}

	private File resolveFromReportsDirectory(String template, String localeId) {
		String basePath = AppInfo.getInformesInfo().getRutaBase();
		if (StringUtils.isBlank(basePath)) {
			basePath = "";
		}

		if (!basePath.endsWith(File.separator)) {
			basePath = basePath + File.separator;
		}

		File candidate = buildTemplateCandidate(basePath + template, localeId);
		if (candidate.exists()) {
			return candidate;
		}

		if (template.contains("/")) {
			String normalizedTemplate = template.replace('/', File.separatorChar);
			File normalizedCandidate = buildTemplateCandidate(basePath + normalizedTemplate, localeId);
			if (normalizedCandidate.exists()) {
				return normalizedCandidate;
			}
		}
		else {
			File resolved = locateTemplateInReportsDirectory(basePath, template, localeId);
			if (resolved != null) {
				return resolved;
			}
		}

		return candidate;
	}

	private File buildTemplateCandidate(String partialFileName, String localeId) {
		String locale = localeId != null ? localeId.toLowerCase(Locale.ROOT) : null;
		String extension = getTemplateExtension();
		File result = null;

		if (StringUtils.isNotBlank(locale)) {
			result = new File(partialFileName + "_" + locale + extension);
			if (!result.exists() && locale.length() >= 2) {
				result = new File(partialFileName + "_" + StringUtils.left(locale, 2) + extension);
			}
		}

		if (result == null || !result.exists()) {
			result = new File(partialFileName + extension);
		}

		return result;
	}

	private File locateTemplateInReportsDirectory(String basePath, String template, String localeId) {
		if (StringUtils.isBlank(basePath) || StringUtils.isBlank(template)) {
			return null;
		}

		File baseDirectory = new File(basePath);
		if (!baseDirectory.isDirectory()) {
			return null;
		}

		for (String candidateName : buildCandidateNames(template, localeId)) {
			File cached = TEMPLATE_RESOLUTION_CACHE.get(candidateName);
			if (cached != null) {
				if (cached.exists()) {
					return cached;
				}
				TEMPLATE_RESOLUTION_CACHE.remove(candidateName);
			}

			File located = searchTemplateRecursively(baseDirectory, candidateName);
			if (located != null) {
				TEMPLATE_RESOLUTION_CACHE.put(candidateName, located);
				return located;
			}
		}

		return null;
	}

	private List<String> buildCandidateNames(String template, String localeId) {
		String extension = getTemplateExtension();
		List<String> candidates = new ArrayList<>(3);
		String locale = localeId != null ? localeId.toLowerCase(Locale.ROOT) : null;

		if (StringUtils.isNotBlank(locale)) {
			candidates.add(template + "_" + locale + extension);
			if (locale.length() >= 2) {
				candidates.add(template + "_" + StringUtils.left(locale, 2) + extension);
			}
		}

		candidates.add(template + extension);
		return candidates;
	}

	private File searchTemplateRecursively(File baseDirectory, String candidateName) {
		try (Stream<Path> paths = Files.walk(baseDirectory.toPath())) {
			return paths.filter(Files::isRegularFile).filter(path -> candidateName.equalsIgnoreCase(path.getFileName().toString())).findFirst().map(Path::toFile).orElse(null);
		}
		catch (IOException exception) {
			LOGGER.warn("locateTemplateInReportsDirectory() - Unable to resolve template '{}' under '{}'", candidateName, baseDirectory.getAbsolutePath(), exception);
			return null;
		}
	}

	private static final class JrxmlFilter implements FileFilter {

		@Override
		public boolean accept(File pathname) {
			return pathname.isFile() && StringUtils.endsWithIgnoreCase(pathname.getName(), ".jrxml");
		}
	}

	private static final Pattern PROPERTY_PATTERN = Pattern.compile("\\$P\\{ticket\\}\\.getCabecera\\(\\)\\.getFiscalData\\(\\)\\.getProperty\\(\\\"ATCUD\\\"\\)");
	private static final Pattern PROPERTY_VALUE_PATTERN = Pattern
	        .compile("\\$P\\{ticket\\}\\.getCabecera\\(\\)\\.getFiscalData\\(\\)\\.getProperty(?:Value)?\\(\\\"ATCUD\\\"\\)(?:\\.getValue\\(\\))?");
}