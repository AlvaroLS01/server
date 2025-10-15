package com.comerzzia.core.basketcalculator.model.pasarelas.tipos;

import com.comerzzia.core.basketcalculator.util.base.MantenimientoBean;

public class TipoPasarelaKey extends MantenimientoBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2153602563770577581L;

	private String uidActividad;

    private String idTipoPasarela;

    public String getUidActividad() {
        return uidActividad;
    }

    public void setUidActividad(String uidActividad) {
        this.uidActividad = uidActividad == null ? null : uidActividad.trim();
    }

    public String getIdTipoPasarela() {
        return idTipoPasarela;
    }

    public void setIdTipoPasarela(String idTipoPasarela) {
        this.idTipoPasarela = idTipoPasarela == null ? null : idTipoPasarela.trim();
    }

	@Override
	protected void initNuevoBean() {}
}