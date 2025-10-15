/**
 * ComerZZia 3.0
 *
 * Copyright (c) 2008-2015 Comerzzia, S.L.  All Rights Reserved.
 *
 * THIS WORK IS  SUBJECT  TO  SPAIN  AND  INTERNATIONAL  COPYRIGHT  LAWS  AND
 * TREATIES.   NO  PART  OF  THIS  WORK MAY BE  USED,  PRACTICED,  PERFORMED
 * COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED,  ABRIDGED, CONDENSED,
 * EXPANDED,  COLLECTED,  COMPILED,  LINKED,  RECAST, TRANSFORMED OR ADAPTED
 * WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION
 * OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO
 * CRIMINAL AND CIVIL LIABILITY.
 *
 * CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL
 * RESTRICTIONS.
 */
package com.comerzzia.pos.services.core.config.configContadores.parametros;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.core.basketcalculator.util.paginacion.PaginaResultados;
import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ConfigContadorParametroBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ConfigContadorParametroExample;
import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ConfigContadorParametroKey;
import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.POSConfigContadorParametroMapper;
import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ParametrosBuscarConfigContadoresParametrosBean;
import com.comerzzia.pos.services.core.config.configContadores.ContadoresConfigException;

@Service
public class ServicioConfigContadoresParametros {

	protected static Logger log = Logger.getLogger(ServicioConfigContadoresParametros.class);

	@Autowired
	protected POSConfigContadorParametroMapper configContadorParametroMapper;
	
	public PaginaResultados consultar(ParametrosBuscarConfigContadoresParametrosBean param) throws ContadoresConfigException {
		PaginaResultados paginaResultados = null;
		try {
			log.debug("consultar() - Consultando parámetros de configuración de contador");
			
			List<ConfigContadorParametroBean> resultados = new ArrayList<ConfigContadorParametroBean>(param.getTamañoPagina());
			paginaResultados = new PaginaResultados(param, resultados);
			
			ConfigContadorParametroExample filtro = obtenerFiltro(param);
			
			paginaResultados.setTotalResultados(configContadorParametroMapper.countByExample(filtro));
			resultados.addAll(configContadorParametroMapper.selectByExample(filtro));
			
			return paginaResultados;
		}
		catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar los parámetros de configuración de contador: " + e.getMessage();
			throw new ContadoresConfigException(mensaje, e);
		}
	}
	
	protected ConfigContadorParametroExample obtenerFiltro(ParametrosBuscarConfigContadoresParametrosBean param) {
		ConfigContadorParametroExample filtro = new ConfigContadorParametroExample();
		
		ConfigContadorParametroExample.Criteria cirteria = filtro.or();
		
		// ID_CONTADOR
		if (!param.getIdContador().isEmpty()) {
			cirteria.andIdContadorEqualTo(param.getIdContador());
		}
	
		return filtro;
	}
	
	
	public Map<String, ConfigContadorParametroBean> consultarMap(String idContador) throws ConfigContadoresParametrosException {

		try {
			log.debug("consultar() - Consultando parámetro del contador " + idContador);

			ConfigContadorParametroExample filtro = new ConfigContadorParametroExample();
			filtro.or().andIdContadorEqualTo(idContador);

			Map<String, ConfigContadorParametroBean> mapParametros = new HashMap<String, ConfigContadorParametroBean>();
			List<ConfigContadorParametroBean> listConfigContadorParametros = configContadorParametroMapper.selectByExample(filtro);
			if(listConfigContadorParametros != null) {
				for(ConfigContadorParametroBean configParametro: listConfigContadorParametros) {
					mapParametros.put(configParametro.getParametro(), configParametro);
				}
			}
			
			return mapParametros;
		}
		catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar los parámetros del contador " + idContador + ": " + e.getMessage();

			throw new ConfigContadoresParametrosException(mensaje, e);
		}
	}
	
	public List<ConfigContadorParametroBean> consultar(String idContador) throws ConfigContadoresParametrosException {
		try {
			log.debug("consultar() - Consultando parámetro del contador " + idContador);

			ConfigContadorParametroExample filtro = new ConfigContadorParametroExample();
			filtro.or().andIdContadorEqualTo(idContador);

			return configContadorParametroMapper.selectByExample(filtro);
		}
		catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar los parámetros del contador " + idContador + ": " + e.getMessage();

			throw new ConfigContadoresParametrosException(mensaje, e);
		}
	}

	public ConfigContadorParametroBean consultar(String idContador, String parametro) throws ConfigContadoresParametrosException,
	        ConfigContadoresParametrosNotFoundException {

		try {
			log.debug("consultar() - Consultando configuración de contadores");

			ConfigContadorParametroKey primaryKey = new ConfigContadorParametroKey();
			primaryKey.setIdContador(idContador);
			primaryKey.setParametro(parametro);

			ConfigContadorParametroBean configContadorParametro = configContadorParametroMapper.selectByPrimaryKey(primaryKey);

			if (configContadorParametro == null) {
				String msg = "No se ha encontrado el parámetro " + parametro + " para el contador con identificador: " + idContador;
				log.info("consultar() - " + msg);
				throw new ConfigContadoresParametrosNotFoundException(msg);
			}

			return configContadorParametro;
		}
		catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar la información del parámetro " + parametro + " del contador " + idContador + ": " + e.getMessage();
			if (e instanceof ConfigContadoresParametrosNotFoundException) {
				throw new ConfigContadoresParametrosNotFoundException(e.getMessage());
			}
			else {
				throw new ConfigContadoresParametrosException(mensaje, e);
			}

		}
	}
	
}
