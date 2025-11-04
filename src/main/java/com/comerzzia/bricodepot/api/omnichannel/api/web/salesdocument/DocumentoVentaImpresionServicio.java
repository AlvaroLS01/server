package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DocumentoVentaImpresionServicio {

	private static Logger log = Logger.getLogger(DocumentoVentaImpresionServicio.class);

	public void registrarInicioImpresion(String documentUid) {
            if (log.isDebugEnabled()) {
                    log.debug("Inicio de generación de impresión para el documento '" + documentUid + "'");
            }
    }

    public void registrarFinImpresion(String documentUid, int statusCode, Instant startInstant) {
            if (!log.isDebugEnabled()) {
                    return;
            }

            long elapsedMillis = -1L;
            if (startInstant != null) {
                    elapsedMillis = Duration.between(startInstant, Instant.now()).toMillis();
            }

            String durationPart = elapsedMillis >= 0 ? " en " + elapsedMillis + " ms" : StringUtils.EMPTY;
            log.debug("Finalizada la generación de impresión del documento '" + documentUid + "' con estado " + statusCode + durationPart);
    }
}
