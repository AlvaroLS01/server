package com.comerzzia.core.basketcalculator.servicios.mediospago.configuracion;

import com.comerzzia.core.basketcalculator.model.mediospago.configuracion.ConfiguracionMedioPagoBean;
import com.comerzzia.core.basketcalculator.model.pasarelas.PasarelaConfig;
import com.comerzzia.core.basketcalculator.servicios.sesion.IDatosSesion;

public interface ConfiguracionMedioPagoService {

	ConfiguracionMedioPagoBean consultar(String codMedPag, String idClase, String idObjeto,
			IDatosSesion datosSesion);

	PasarelaConfig consultarConfiguracion(String codMedPag, String idClase, String idObjeto,
			IDatosSesion datosSesion);
	
	ConfiguracionMedioPagoBean selectByPrimaryKey(String codMedPag, String idClase, String idObjeto,
			IDatosSesion datosSesion);

	void salvar(ConfiguracionMedioPagoBean configuracionMedioPago, IDatosSesion datosSesion)
			throws ConfiguracionMedioPagoConstraintViolationException, ConfiguracionMedioPagoException;

}