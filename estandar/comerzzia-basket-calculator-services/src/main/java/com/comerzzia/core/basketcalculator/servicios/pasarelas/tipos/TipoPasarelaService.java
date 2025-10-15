package com.comerzzia.core.basketcalculator.servicios.pasarelas.tipos;

import java.sql.Connection;
import java.util.List;

import com.comerzzia.core.basketcalculator.model.pasarelas.tipos.TipoPasarelaBean;
import com.comerzzia.core.basketcalculator.model.pasarelas.tipos.TipoPasarelaExample;
import com.comerzzia.core.basketcalculator.persistencia.pasarelas.tipos.ParametrosBuscarTipoPasarelaBean;
import com.comerzzia.core.basketcalculator.servicios.sesion.DatosSesionBean;
import com.comerzzia.core.basketcalculator.servicios.sesion.IDatosSesion;
import com.comerzzia.core.basketcalculator.util.paginacion.PaginaResultados;

public interface TipoPasarelaService {

	List<TipoPasarelaBean> consultar(TipoPasarelaExample tipoPasarelaExample)
			throws TipoPasarelaException;

	TipoPasarelaBean consultar(String idTipoPasarela, IDatosSesion datosSesion,
			Connection conn) throws TipoPasarelaNotFoundException;

	TipoPasarelaBean consultar(String idTipoPasarela, IDatosSesion datosSesion)
			throws TipoPasarelaNotFoundException;

	PaginaResultados consultar(ParametrosBuscarTipoPasarelaBean param,
			IDatosSesion datosSesion);

	void salvar(TipoPasarelaBean tipoPasarela, DatosSesionBean datosSesion);

	void eliminar(String idTipoPasarela, DatosSesionBean datosSesion)
			throws TipoPasarelaConstraintViolationException;

}