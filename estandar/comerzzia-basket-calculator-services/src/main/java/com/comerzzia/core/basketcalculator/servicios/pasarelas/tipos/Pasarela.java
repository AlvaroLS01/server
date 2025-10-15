package com.comerzzia.core.basketcalculator.servicios.pasarelas.tipos;

import java.math.BigDecimal;

import javax.xml.bind.JAXBException;

import com.comerzzia.core.basketcalculator.model.pasarelas.PasarelaConfig;
import com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones.ConfiguracionPasarelaBean;
import com.comerzzia.core.basketcalculator.servicios.sesion.DatosSesionBean;

public interface Pasarela {
	/**
	 * 
	 * @param configPasarela
	 * @throws JAXBException
	 */
	void setPasarela(ConfiguracionPasarelaBean configPasarela) throws JAXBException;
	
	/**
	 * 
	 * @return
	 */
	PasarelaConfig getPasarela();

	/**
	 * 
	 * @return
	 * @throws JAXBException
	 */
	String getXmlConfiguracion() throws JAXBException;

	/**
	 * 
	 * @return
	 */
	ConfiguracionPasarelaBean getConfiguracionPasarelaBean();

	/**
	 * 
	 * @return
	 * @throws JAXBException 
	 */
	String getVistaControl(DatosSesionBean datosSesion) throws JAXBException;

	/**
	 * 
	 * @return
	 */
	Boolean isMostrarXmlConfiguracion();

	/**
	 * 
	 * @param mostrarXmlConfiguracion
	 */
	void setMostrarXmlConfiguracion(Boolean mostrarXmlConfiguracion);
	
	String returnAmount(BigDecimal amount, String transactionResponse);
	
	String confirmPayment(BigDecimal amount, String transactionResponse);
}