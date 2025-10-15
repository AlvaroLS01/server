package com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones;

import com.comerzzia.core.basketcalculator.model.pasarelas.tipos.TipoPasarelaBean;


public class ConfiguracionPasarelaBean extends ConfiguracionPasarelaKey {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4442910427349677164L;

	private String idTipoPasarela;

    private String desconfpasarela;

    private byte[] configuracion;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    private TipoPasarelaBean tipoPasarela;
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------

    public String getIdTipoPasarela() {
        return idTipoPasarela;
    }

	public void setIdTipoPasarela(String idTipoPasarela) {
        this.idTipoPasarela = idTipoPasarela == null ? null : idTipoPasarela.trim();
    }

    public String getDesconfpasarela() {
        return desconfpasarela;
    }

    public void setDesconfpasarela(String desconfpasarela) {
        this.desconfpasarela = desconfpasarela == null ? null : desconfpasarela.trim();
    }

    public byte[] getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(byte[] configuracion) {
        this.configuracion = configuracion;
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
	public TipoPasarelaBean getTipoPasarela() {
		return tipoPasarela;
	}
	
	public void setTipoPasarela(TipoPasarelaBean tipoPasarela) {
		this.tipoPasarela = tipoPasarela;
	}
	
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------
}