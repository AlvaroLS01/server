package com.comerzzia.core.basketcalculator.servicios.pasarelas.configuraciones;

import com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones.ConfiguracionPasarelaBean;
import com.comerzzia.core.basketcalculator.persistencia.pasarelas.configuraciones.ParametrosBuscarConfiguracionPasarelaBean;
import com.comerzzia.core.basketcalculator.servicios.pasarelas.tipos.Pasarela;
import com.comerzzia.core.basketcalculator.servicios.sesion.IDatosSesion;
import com.comerzzia.core.basketcalculator.util.paginacion.PaginaResultados;

public interface ConfiguracionPasarelaService {

	PaginaResultados consultar(ParametrosBuscarConfiguracionPasarelaBean param, IDatosSesion datosSesion);

	Pasarela consultar(String idConfPasarela, IDatosSesion datosSesion);

	void salvar(ConfiguracionPasarelaBean configPasarela, IDatosSesion datosSesion);

	void eliminar(String idConfPasarela, IDatosSesion datosSesion)
			throws ConfiguracionPasarelaConstraintViolationException;

	ConfiguracionPasarelaBean consultarConfiguracion(String idConfPasarela, IDatosSesion datosSesion);

}