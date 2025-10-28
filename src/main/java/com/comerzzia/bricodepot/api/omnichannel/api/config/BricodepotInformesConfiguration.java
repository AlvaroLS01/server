package com.comerzzia.bricodepot.api.omnichannel.api.config;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.comerzzia.core.util.config.AppInfo;

@Configuration
public class BricodepotInformesConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(BricodepotInformesConfiguration.class);

	private static final String INFORMES_CLASSPATH_DIRECTORY = "informes";

	@PostConstruct
	public void configureReportsBasePath() {
		if (StringUtils.isNotBlank(AppInfo.getInformesInfo().getRutaBase())) {
			LOGGER.debug("configureReportsBasePath() - Report base path already configured: {}", AppInfo.getInformesInfo().getRutaBase());
			return;
		}

		URL resource = Thread.currentThread().getContextClassLoader().getResource(INFORMES_CLASSPATH_DIRECTORY);
		if (resource == null) {
			LOGGER.warn("configureReportsBasePath() - Unable to locate reports directory '{}' on the classpath", INFORMES_CLASSPATH_DIRECTORY);
			return;
		}

		try {
			File informesDirectory = new File(resource.toURI());
			if (!informesDirectory.exists()) {
				LOGGER.warn("configureReportsBasePath() - Reports directory '{}' does not exist", informesDirectory);
				return;
			}

			String absolutePath = informesDirectory.getAbsolutePath();
			if (!absolutePath.endsWith(File.separator)) {
				absolutePath = absolutePath + File.separator;
			}

			AppInfo.getInformesInfo().setRutaBase(absolutePath, AppInfo.getRutaTrabajo());
			LOGGER.info("configureReportsBasePath() - Configured reports base path to '{}'", absolutePath);
		}
		catch (URISyntaxException exception) {
			LOGGER.warn("configureReportsBasePath() - Unable to configure reports base path", exception);
		}
	}
}
