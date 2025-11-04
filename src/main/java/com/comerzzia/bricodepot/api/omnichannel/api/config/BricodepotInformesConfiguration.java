package com.comerzzia.bricodepot.api.omnichannel.api.config;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import com.comerzzia.core.util.config.AppInfo;

@Configuration
public class BricodepotInformesConfiguration {

	private static Logger log = Logger.getLogger(BricodepotInformesConfiguration.class);

	private static final String INFORMES_CLASSPATH_DIRECTORY = "informes";

	@PostConstruct
	public void configureReportsBasePath() {
		if (StringUtils.isNotBlank(AppInfo.getInformesInfo().getRutaBase())) {
			log.debug("La ruta base de informes ya está configurada en: " + AppInfo.getInformesInfo().getRutaBase());
			return;
		}

		URL resource = Thread.currentThread().getContextClassLoader().getResource(INFORMES_CLASSPATH_DIRECTORY);
		if (resource == null) {
			log.warn("No se encuentra el directorio de informes '" + INFORMES_CLASSPATH_DIRECTORY + "' en el classpath");
			return;
		}

		try {
			File informesDirectory = new File(resource.toURI());
			if (!informesDirectory.exists()) {
				log.warn("El directorio de informes '" + informesDirectory + "' no existe");
				return;
			}

			String absolutePath = informesDirectory.getAbsolutePath();
			if (!absolutePath.endsWith(File.separator)) {
				absolutePath = absolutePath + File.separator;
			}

			AppInfo.getInformesInfo().setRutaBase(absolutePath, AppInfo.getRutaTrabajo());
			log.info("Ruta base de informes configurada en '" + absolutePath + "'");
		}
		catch (URISyntaxException exception) {
			log.warn("No se pudo configurar la ruta base de informes", exception);
		}
	}
}
