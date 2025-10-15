package com.comerzzia.core.basketcalculator.model.pasarelas.tipos;

import com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones.ConfiguracionPasarelaBean;

public class TipoPasarelaBean extends TipoPasarelaKey {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3552289802549735015L;

	private String destipopasarela;

    private String claseControl;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    protected ConfiguracionPasarelaBean configuracionPasarela;
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public String getDestipopasarela() {
        return destipopasarela;
    }

    public void setDestipopasarela(String destipopasarela) {
        this.destipopasarela = destipopasarela == null ? null : destipopasarela.trim();
    }

    public String getClaseControl() {
        return claseControl;
    }

    public void setClaseControl(String claseControl) {
        this.claseControl = claseControl == null ? null : claseControl.trim();
    }
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    public ConfiguracionPasarelaBean getConfiguracionPasarela() {
    	return configuracionPasarela;
    }
    
    public void setConfiguracionPasarela(
    		ConfiguracionPasarelaBean configuracionPasarela) {
    	this.configuracionPasarela = configuracionPasarela;
    }
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------
}