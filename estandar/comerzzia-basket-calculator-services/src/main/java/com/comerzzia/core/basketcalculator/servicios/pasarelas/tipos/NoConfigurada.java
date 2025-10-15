package com.comerzzia.core.basketcalculator.servicios.pasarelas.tipos;

import java.math.BigDecimal;

import javax.xml.bind.JAXBException;

import com.comerzzia.core.basketcalculator.model.pasarelas.PasarelaConfig;
import com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones.ConfiguracionPasarelaBean;
import com.comerzzia.core.basketcalculator.servicios.sesion.DatosSesionBean;

public class NoConfigurada implements Pasarela {

	@Override
	public void setPasarela(ConfiguracionPasarelaBean configPasarela) throws JAXBException {		
	}

	@Override
	public PasarelaConfig getPasarela() {
		return null;
	}

	@Override
	public String getXmlConfiguracion() throws JAXBException {
		return null;
	}

	@Override
	public ConfiguracionPasarelaBean getConfiguracionPasarelaBean() {
		return null;
	}

	@Override
	public String getVistaControl(DatosSesionBean datosSesion) throws JAXBException {
		return null;
	}

	@Override
	public Boolean isMostrarXmlConfiguracion() {
		return null;
	}

	@Override
	public void setMostrarXmlConfiguracion(Boolean mostrarXmlConfiguracion) {
	}

	@Override
	public String returnAmount(BigDecimal amount, String transactionResponse) {
		return null;
	}

	@Override
	public String confirmPayment(BigDecimal amount, String transactionResponse) {
		return null;
	}

}
