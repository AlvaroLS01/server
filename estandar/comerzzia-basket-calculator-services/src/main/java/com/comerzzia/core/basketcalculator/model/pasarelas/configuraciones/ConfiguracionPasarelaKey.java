package com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones;

import com.comerzzia.core.basketcalculator.util.base.MantenimientoBean;

public class ConfiguracionPasarelaKey extends MantenimientoBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3377082179697097043L;

	private String uidActividad;

    private String idConfPasarela;

    public String getUidActividad() {
        return uidActividad;
    }

    public void setUidActividad(String uidActividad) {
        this.uidActividad = uidActividad == null ? null : uidActividad.trim();
    }

    public String getIdConfPasarela() {
        return idConfPasarela;
    }

    public void setIdConfPasarela(String idConfPasarela) {
        this.idConfPasarela = idConfPasarela == null ? null : idConfPasarela.trim();
    }

	@Override
	protected void initNuevoBean() {}
}