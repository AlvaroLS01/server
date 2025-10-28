package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DocumentoVentaImpresionServicio {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoVentaImpresionServicio.class);

	public void registrarInicioImpresion(String documentUid) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("registrarInicioImpresion() - Starting print generation for document '{}'", documentUid);
		}
	}

	public void registrarFinImpresion(String documentUid, int statusCode, Instant startInstant) {
		if (!LOGGER.isDebugEnabled()) {
			return;
		}

		long elapsedMillis = -1L;
		if (startInstant != null) {
			elapsedMillis = Duration.between(startInstant, Instant.now()).toMillis();
		}

		String durationPart = elapsedMillis >= 0 ? " in " + elapsedMillis + " ms" : StringUtils.EMPTY;
		LOGGER.debug("registrarFinImpresion() - Completed print generation for document '{}' with status {}{}", documentUid, statusCode, durationPart);
	}
}
