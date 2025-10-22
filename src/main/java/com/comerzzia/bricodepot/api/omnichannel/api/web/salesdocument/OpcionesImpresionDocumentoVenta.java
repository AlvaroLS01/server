package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OpcionesImpresionDocumentoVenta {

	private final String tipoMime;
	private final boolean esCopia;
	private final boolean enLinea;
        private final String nombreDocumentoSalida;
        private final String plantillaImpresion;
        private final String uidActividad;
        private final Map<String, String> parametrosPersonalizados;

        public OpcionesImpresionDocumentoVenta(String tipoMime, boolean esCopia, boolean enLinea,
                        String nombreDocumentoSalida, String plantillaImpresion, String uidActividad,
                        Map<String, String> parametrosPersonalizados) {
                this.tipoMime = tipoMime;
                this.esCopia = esCopia;
                this.enLinea = enLinea;
                this.nombreDocumentoSalida = nombreDocumentoSalida;
                this.plantillaImpresion = plantillaImpresion;
                this.uidActividad = uidActividad;
                this.parametrosPersonalizados = parametrosPersonalizados == null ? Collections.emptyMap() : Collections.unmodifiableMap(new HashMap<>(parametrosPersonalizados));
        }

	public String getTipoMime() {
		return tipoMime;
	}

	public boolean esCopia() {
		return esCopia;
	}

	public boolean esEnLinea() {
		return enLinea;
	}

	public String getNombreDocumentoSalida() {
		return nombreDocumentoSalida;
	}

        public String getPlantillaImpresion() {
                return plantillaImpresion;
        }

        public String getUidActividad() {
                return uidActividad;
        }

        public Map<String, String> getParametrosPersonalizados() {
                return parametrosPersonalizados;
        }
}
