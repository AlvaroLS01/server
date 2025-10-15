package com.comerzzia.core.basketcalculator.model.mediospago.configuracion;

public class ConfiguracionMedioPagoBean extends ConfiguracionMedioPagoKey {

	private static final long serialVersionUID = 8719945566730171719L;

	private String idConfPasarela;

    private String desmedpag;

    private String desclase;

    private String idTipoPasarela;

    private String destipopasarela;

    private String desconfpasarela;

    private String confActivo;
    
    private String claseControl;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public String getIdConfPasarela() {
        return idConfPasarela;
    }

    public void setIdConfPasarela(String idConfPasarela) {
        this.idConfPasarela = idConfPasarela == null ? null : idConfPasarela.trim();
    }

    public String getDesmedpag() {
        return desmedpag;
    }

    public void setDesmedpag(String desmedpag) {
        this.desmedpag = desmedpag == null ? null : desmedpag.trim();
    }

    public String getDesclase() {
        return desclase;
    }

    public void setDesclase(String desclase) {
        this.desclase = desclase == null ? null : desclase.trim();
    }

    public String getIdTipoPasarela() {
        return idTipoPasarela;
    }

    public void setIdTipoPasarela(String idTipoPasarela) {
        this.idTipoPasarela = idTipoPasarela == null ? null : idTipoPasarela.trim();
    }

    public String getDestipopasarela() {
        return destipopasarela;
    }

    public void setDestipopasarela(String destipopasarela) {
        this.destipopasarela = destipopasarela == null ? null : destipopasarela.trim();
    }

    public String getDesconfpasarela() {
        return desconfpasarela;
    }

    public void setDesconfpasarela(String desconfpasarela) {
        this.desconfpasarela = desconfpasarela == null ? null : desconfpasarela.trim();
    }

    public String getConfActivo() {
        return confActivo;
    }

    public void setConfActivo(String confActivo) {
        this.confActivo = confActivo == null ? null : confActivo.trim();
    }

	public String getClaseControl() {
		return claseControl;
	}

	public void setClaseControl(String claseControl) {
		this.claseControl = claseControl;
	}
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}