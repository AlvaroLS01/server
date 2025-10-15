package com.comerzzia.core.basketcalculator.model.mediospago.configuracion;

import com.comerzzia.core.basketcalculator.util.base.MantenimientoBean;

public class ConfiguracionMedioPagoKey extends MantenimientoBean {

	private static final long serialVersionUID = 1884079514087869045L;
	
	public static final String METHOD_PAYMENT_CLASS = "D_MEDIOS_PAGO_TBL.CODMEDPAG";
	public static final String STORE_CLASS = "D_TIENDAS_TBL.CODALM";
	public static final String TILL_CLASS = "D_TIENDAS_CAJAS_TBL.UID_CAJA";

	private String uidActividad;

    private String idClase;

    private String idObjeto;

    private String codmedpag;

    public String getUidActividad() {
        return uidActividad;
    }

    public void setUidActividad(String uidActividad) {
        this.uidActividad = uidActividad == null ? null : uidActividad.trim();
    }

    public String getIdClase() {
        return idClase;
    }

    public void setIdClase(String idClase) {
        this.idClase = idClase == null ? null : idClase.trim();
    }

    public String getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(String idObjeto) {
        this.idObjeto = idObjeto == null ? null : idObjeto.trim();
    }

    public String getCodmedpag() {
        return codmedpag;
    }

    public void setCodmedpag(String codmedpag) {
        this.codmedpag = codmedpag == null ? null : codmedpag.trim();
    }

	@Override
	protected void initNuevoBean() {}
}