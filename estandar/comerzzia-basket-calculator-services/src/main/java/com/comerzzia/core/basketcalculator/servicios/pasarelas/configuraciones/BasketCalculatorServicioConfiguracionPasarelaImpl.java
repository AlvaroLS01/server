package com.comerzzia.core.basketcalculator.servicios.pasarelas.configuraciones;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones.ConfiguracionPasarelaBean;
import com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones.ConfiguracionPasarelaExample;
import com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones.ConfiguracionPasarelaExample.Criteria;
import com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones.ConfiguracionPasarelaKey;
import com.comerzzia.core.basketcalculator.model.pasarelas.tipos.TipoPasarelaBean;
import com.comerzzia.core.basketcalculator.persistencia.pasarelas.configuraciones.BasketCalculatorConfiguracionPasarelaMapper;
import com.comerzzia.core.basketcalculator.persistencia.pasarelas.configuraciones.ParametrosBuscarConfiguracionPasarelaBean;
import com.comerzzia.core.basketcalculator.servicios.pasarelas.tipos.Pasarela;
import com.comerzzia.core.basketcalculator.servicios.pasarelas.tipos.TipoPasarelaService;
import com.comerzzia.core.basketcalculator.servicios.sesion.IDatosSesion;
import com.comerzzia.core.basketcalculator.util.base.Estado;
import com.comerzzia.core.basketcalculator.util.paginacion.PaginaResultados;
import com.comerzzia.pos.services.ContextHolder;

@Service
public class BasketCalculatorServicioConfiguracionPasarelaImpl implements ConfiguracionPasarelaService {

	protected static Logger log = Logger.getLogger(BasketCalculatorServicioConfiguracionPasarelaImpl.class);

	protected static ConfiguracionPasarelaService instance;
	
	@Autowired
	BasketCalculatorConfiguracionPasarelaMapper mapper;
	
	@Autowired
	TipoPasarelaService tipoPasarelaService;

	@Deprecated
	public static ConfiguracionPasarelaService get() {
		if (instance != null) {
			   return instance;
			} else { 
	   		   return (ConfiguracionPasarelaService) ContextHolder.get().getBean(ConfiguracionPasarelaService.class);
			}
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.pasarelas.configuraciones.ConfiguracionPasarelaService#consultar(com.comerzzia.core.persistencia.pasarelas.configuraciones.ParametrosBuscarConfiguracionPasarelaBean, com.comerzzia.core.servicios.sesion.IDatosSesion)
	 */
	@Override
	public PaginaResultados consultar(ParametrosBuscarConfiguracionPasarelaBean param, IDatosSesion datosSesion) {

		try {
			log.debug("consultar() - Consultando la configuración de los tipos de pasarela");

			ConfiguracionPasarelaExample example = new ConfiguracionPasarelaExample();
			Criteria criteria = example.or().andUidActividadEqualTo(datosSesion.getUidActividad());

			// ID_CONF_PASARELA
			if (!param.getIdConfPasarela().isEmpty()) {
				criteria.andIdConfPasarelaLikeInsensitive("%" + param.getIdConfPasarela() + "%");
			}
			// ID_TIPO_PASARELA
			if (!param.getIdTipoPasarela().isEmpty()) {
				criteria.andIdTipoPasarelaEqualTo(param.getIdTipoPasarela());
			}
			// DESCONFPASARELA
			if (!param.getDesConfPasarela().isEmpty()) {
				criteria.andDesconfpasarelaLikeInsensitive("%" + param.getDesConfPasarela() + "%");
			}
			// ACTIVO
			if (!param.getActivo().isEmpty()) {
				criteria.andActivoEqualTo(param.isActivo());
			}

			example.setOrderByClause(param.getOrden());

			List<ConfiguracionPasarelaBean> resultados = new ArrayList<ConfiguracionPasarelaBean>(param.getTamañoPagina());
			PaginaResultados paginaResultados = new PaginaResultados(param, resultados);

			List<ConfiguracionPasarelaBean> configuracionesPasarelas = mapper.selectByExample(example);

			int fromIndex = (paginaResultados.getInicio() - 1);
			int toIndex = (paginaResultados.getInicio() + paginaResultados.getTamañoPagina() - 1);
			if (toIndex > configuracionesPasarelas.size()) {
				toIndex = configuracionesPasarelas.size();
			}

			resultados.addAll(configuracionesPasarelas.subList(fromIndex, toIndex));
			paginaResultados.setTotalResultados(configuracionesPasarelas.size());

			return paginaResultados;
		} catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar la configuración de los tipos de pasarela: " + e.getMessage();

			throw new ConfiguracionPasarelaException(mensaje, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.pasarelas.configuraciones.ConfiguracionPasarelaService#consultar(java.lang.String, com.comerzzia.core.servicios.sesion.IDatosSesion, java.sql.Connection)
	 */
	@Override
	public Pasarela consultar(String idConfPasarela, IDatosSesion datosSesion) {
		try {
			log.debug("consultar() - Consultando datos de la configuración de los tipos de pasarela: " + idConfPasarela);
			
			ConfiguracionPasarelaExample example = new ConfiguracionPasarelaExample();
			example.or().andUidActividadEqualTo(datosSesion.getUidActividad()).andIdConfPasarelaEqualTo(idConfPasarela);
			
			List<ConfiguracionPasarelaBean> lstconfigPasarelaBean = mapper.selectByExampleWithBLOBs(example);
			if (lstconfigPasarelaBean.isEmpty()) {
				String msg = "No se ha encontrado la configuración de los tipos de pasarela: " + idConfPasarela;
				log.info("consultar() - " + msg);
				throw new ConfiguracionPasarelaNotFoundException(msg);
			}
			
			return setConfiguracion(datosSesion, lstconfigPasarelaBean.get(0));
		} catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar la configuración de los tipos de pasarela: " + e.getMessage();

			throw new ConfiguracionPasarelaException(mensaje, e);
		}
	}


	@SuppressWarnings("unchecked")
	protected <D extends Pasarela> Pasarela setConfiguracion(IDatosSesion datosSesion, ConfiguracionPasarelaBean configPasarelaBean) throws ConfiguracionPasarelaException {
		TipoPasarelaBean tipoPasarelaBean = tipoPasarelaService.consultar(configPasarelaBean.getIdTipoPasarela(), datosSesion);
		configPasarelaBean.setTipoPasarela(tipoPasarelaBean);
		
		try {
			Class<D> clazzDoc = null;
			Class<?> clazz = Class.forName(tipoPasarelaBean.getClaseControl());
			if (Pasarela.class.isAssignableFrom(clazz)) {
				clazzDoc = (Class<D>) clazz;
			} else {
				throw new IllegalStateException(String.format("La clase '%s' no implementa de '%s'", tipoPasarelaBean.getClaseControl(), Pasarela.class.getName()));
			}
			Pasarela pasarela = (D) clazzDoc.newInstance();
			pasarela.setPasarela(configPasarelaBean);
			return pasarela;

		} catch (InstantiationException e) {
			String msg = "setConfiguracion() - Error al instanciar la clase " + tipoPasarelaBean.getClaseControl();
			log.error(msg + e.getMessage());
			throw new ConfiguracionPasarelaException(msg);
		} catch (ClassNotFoundException e) {
			String msg = String.format("setConfiguracion() - La clase %s no se encuentra en el classpath de la aplicación	", tipoPasarelaBean.getClaseControl());
			log.error(msg + e.getMessage());
			throw new ConfiguracionPasarelaException(msg);
		} catch (Exception e) {
			String msg = "setConfiguracion() - Error al parsear la configuración de la pasarela de pago" + tipoPasarelaBean.getClaseControl();
			log.error(msg + e.getMessage());
			throw new ConfiguracionPasarelaException(msg, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.pasarelas.configuraciones.ConfiguracionPasarelaService#salvar(com.comerzzia.core.model.pasarelas.configuraciones.ConfiguracionPasarelaBean, com.comerzzia.core.servicios.sesion.IDatosSesion)
	 */
	@Override
	public void salvar(ConfiguracionPasarelaBean configPasarela, IDatosSesion datosSesion) {
		switch (configPasarela.getEstadoBean()) {
		case Estado.NUEVO:
			crear(configPasarela, datosSesion);
			break;

		case Estado.MODIFICADO:
			modificar(configPasarela, datosSesion);
			break;

		case Estado.BORRADO:
			eliminar(configPasarela.getIdConfPasarela(), datosSesion);
		}

	}

	protected void crear(ConfiguracionPasarelaBean configPasarela, IDatosSesion datosSesion) throws ConfiguracionPasarelaConstraintViolationException {

		try {
			log.debug("crear() - Creando una nueva configuración de un tipo de pasarela");

			configPasarela.setUidActividad(datosSesion.getUidActividad());
			mapper.insert(configPasarela);

		} catch (PersistenceException e) {
			log.error("crear() - No se ha podido crear la configuración de un tipo de pasarela: " + e.getMessage(), e);
			throw e;
		}

	}

	protected void modificar(ConfiguracionPasarelaBean configPasarela, IDatosSesion datosSesion) {

			log.debug("modificar() - Modificando una configuración de un tipo de pasarela: " + configPasarela.getIdConfPasarela());

			mapper.updateByPrimaryKeyWithBLOBs(configPasarela);

		
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.pasarelas.configuraciones.ConfiguracionPasarelaService#eliminar(java.lang.String, com.comerzzia.core.servicios.sesion.IDatosSesion)
	 */
	@Override
	public void eliminar(String idConfPasarela, IDatosSesion datosSesion) throws ConfiguracionPasarelaConstraintViolationException {

		try {
			log.debug("eliminar() - Eliminando una configuración de medios de pago: " + idConfPasarela);

			ConfiguracionPasarelaKey key = new ConfiguracionPasarelaKey();
			key.setUidActividad(datosSesion.getUidActividad());
			key.setIdConfPasarela(idConfPasarela);
			mapper.deleteByPrimaryKey(key);

		} catch (PersistenceException e) {
			log.error("eliminar() - No se ha podido eliminar la configuracion de un tipo de pasarela: " + e.getMessage(), e);
			throw e;
		} 
	}
	
	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.pasarelas.configuraciones.ConfiguracionPasarelaService#consultarConfiguracion(java.lang.String, com.comerzzia.core.servicios.sesion.IDatosSesion)
	 */
	@Override
	public ConfiguracionPasarelaBean consultarConfiguracion(String idConfPasarela, IDatosSesion datosSesion) {

		log.debug("consultar() - Consultando configuración del tipo de pasarela: " + idConfPasarela);
		
		ConfiguracionPasarelaKey key = new ConfiguracionPasarelaKey();
		key.setUidActividad(datosSesion.getUidActividad());
		key.setIdConfPasarela(idConfPasarela);
		
		ConfiguracionPasarelaBean configuracionPasarela = mapper.selectByPrimaryKey(key);

		return configuracionPasarela;
		
	}
}