package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.util.Collections;
import java.util.Map;

import org.springframework.util.StringUtils;

public class OpcionesImpresionDocumentoVenta {

        private final String mimeType;
        private final boolean copia;
        private final boolean enLinea;
        private final String nombreDocumentoSalida;
        private final String plantillaImpresion;
        private final Map<String, String> parametrosPersonalizados;

        public OpcionesImpresionDocumentoVenta(String mimeType, boolean copia, boolean enLinea,
                        String nombreDocumentoSalida, String plantillaImpresion, Map<String, String> parametrosPersonalizados) {
                this.mimeType = (StringUtils.hasText(mimeType) ? mimeType : "application/pdf");
                this.copia = copia;
                this.enLinea = enLinea;
                this.nombreDocumentoSalida = nombreDocumentoSalida;
                this.plantillaImpresion = plantillaImpresion;
                this.parametrosPersonalizados = (parametrosPersonalizados != null ? Collections.unmodifiableMap(parametrosPersonalizados)
                                : Collections.emptyMap());
        }

        public String getMimeType() {
                return mimeType;
        }

        public boolean isCopia() {
                return copia;
        }

        public boolean isEnLinea() {
                return enLinea;
        }

        public String getNombreDocumentoSalida() {
                return nombreDocumentoSalida;
        }

        public String getPlantillaImpresion() {
                return plantillaImpresion;
        }

        public Map<String, String> getParametrosPersonalizados() {
                return parametrosPersonalizados;
        }
}
