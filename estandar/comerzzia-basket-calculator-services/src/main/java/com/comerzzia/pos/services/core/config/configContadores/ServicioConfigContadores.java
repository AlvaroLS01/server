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
package com.comerzzia.pos.services.core.config.configContadores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.core.basketcalculator.util.paginacion.PaginaResultados;
import com.comerzzia.pos.persistence.core.config.configcontadores.ConfigContadorBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.ConfigContadorExample;
import com.comerzzia.pos.persistence.core.config.configcontadores.POSConfigContadorMapper;
import com.comerzzia.pos.persistence.core.config.configcontadores.ParametrosBuscarConfigContadoresBean;

@Service
public class ServicioConfigContadores {

	protected static Logger log = Logger.getLogger(ServicioConfigContadores.class);

	protected static Map<String, ConfigContadorBean> cacheConfigContadores = new HashMap<String, ConfigContadorBean>();
	
	@Autowired
	protected POSConfigContadorMapper configContadorMapper;
	
	public PaginaResultados consultar(ParametrosBuscarConfigContadoresBean param) throws ContadoresConfigException {
		PaginaResultados paginaResultados = null;
		try {
			log.debug("consultar() - Consultando variables");
			
			List<ConfigContadorBean> resultados = new ArrayList<ConfigContadorBean>(param.getTamañoPagina());
			paginaResultados = new PaginaResultados(param, resultados);
			
			ConfigContadorExample filtro = obtenerFiltro(param);
			paginaResultados.setTotalResultados(configContadorMapper.countByExample(filtro));
			resultados.addAll(configContadorMapper.selectByExampleWithRowbounds(filtro, new RowBounds(paginaResultados.getInicio()-1, paginaResultados.getTamañoPagina())));
			
			return paginaResultados;
		}
		catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "consultar() - Error al consultar contadores: " + e.getMessage();
			throw new ContadoresConfigException(mensaje, e);
		}
	}
	
	protected ConfigContadorExample obtenerFiltro(ParametrosBuscarConfigContadoresBean param) {
		ConfigContadorExample filtro = new ConfigContadorExample();
		
		ConfigContadorExample.Criteria cirteria = filtro.or();
		
		// ID_CONTADOR
		if (!param.getIdContador().isEmpty()) {
			cirteria.andIdContadorEqualTo(param.getIdContador());
		}
	
		// DESCRIPCION
		if (!param.getDescripcion().isEmpty()) {
			cirteria.andDescripcionLikeInsensitive("%"+param.getDescripcion()+"%");
			
		}
		return filtro;
	}
	
	public ConfigContadorBean consultar(String idContador) throws ContadoresConfigException, ContadoresConfigNotFoundException {
		try {
			log.debug("consultar() - Consultando datos del contador: " + idContador);
			ConfigContadorBean configContador = configContadorMapper.selectByPrimaryKey(idContador);

			if (configContador == null) {
				String msg = "No se ha encontrado el contador con identificador: " + idContador;
				log.info("consultar() - " + msg);
				throw new ContadoresConfigNotFoundException(msg);
			}

			return configContador;
		}
		catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar datos de un contador: " + e.getMessage();
			if(e instanceof ContadoresConfigNotFoundException) {
				throw new ContadoresConfigNotFoundException(e.getMessage());
			}
			else
			{
				throw new ContadoresConfigException(mensaje, e);
			}
		}
	}
	

	
    public Map<String, ConfigContadorBean> getCacheConfigContadores() {
    	return cacheConfigContadores;
    }
}
