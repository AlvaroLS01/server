package com.comerzzia.core.basketcalculator.servicios.pasarelas.tipos;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comerzzia.core.basketcalculator.model.pasarelas.tipos.TipoPasarelaBean;
import com.comerzzia.core.basketcalculator.model.pasarelas.tipos.TipoPasarelaExample;
import com.comerzzia.core.basketcalculator.model.pasarelas.tipos.TipoPasarelaExample.Criteria;
import com.comerzzia.core.basketcalculator.model.pasarelas.tipos.TipoPasarelaKey;
import com.comerzzia.core.basketcalculator.persistencia.pasarelas.tipos.ParametrosBuscarTipoPasarelaBean;
import com.comerzzia.core.basketcalculator.persistencia.pasarelas.tipos.BasketCalculatorTipoPasarelaMapper;
import com.comerzzia.core.basketcalculator.servicios.sesion.DatosSesionBean;
import com.comerzzia.core.basketcalculator.servicios.sesion.IDatosSesion;
import com.comerzzia.core.basketcalculator.util.base.Estado;
import com.comerzzia.core.basketcalculator.util.paginacion.PaginaResultados;
import com.comerzzia.pos.services.ContextHolder;

@Service
public class BasketCalculatorServicioTipoPasarela implements TipoPasarelaService {

	protected static Logger log = Logger.getLogger(BasketCalculatorServicioTipoPasarela.class);

	protected static TipoPasarelaService instance;
	
	@Autowired
	BasketCalculatorTipoPasarelaMapper tipoPasarelaMapper;
	
	@Deprecated
	public static TipoPasarelaService get(){
		if (instance != null) {
		   return instance;
		} else { 
   		   return (TipoPasarelaService) ContextHolder.get().getBean(TipoPasarelaService.class);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.pasarelas.tipos.TipoPasarelaService#consultar(com.comerzzia.core.model.pasarelas.tipos.TipoPasarelaExample)
	 */
	@Override
	public List<TipoPasarelaBean> consultar(TipoPasarelaExample tipoPasarelaExample) throws TipoPasarelaException {
		try {
			log.debug("consultar() - Consultando acciones");
						
			List<TipoPasarelaBean> tipoPasarelas = tipoPasarelaMapper.selectByExample(tipoPasarelaExample);
			
			return tipoPasarelas;
		}
		catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar tipos de pasarelas: " + e.getMessage();

			throw new TipoPasarelaException(mensaje, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.pasarelas.tipos.TipoPasarelaService#consultar(java.lang.String, com.comerzzia.core.servicios.sesion.IDatosSesion, java.sql.Connection)
	 */
	@Override
	@Deprecated
	public TipoPasarelaBean consultar(String idTipoPasarela, IDatosSesion datosSesion, Connection conn) throws TipoPasarelaNotFoundException {
		log.debug("consultar() - Consultando datos del tipo de pasarela: " + idTipoPasarela);

		TipoPasarelaKey tipPasarelakey = new TipoPasarelaKey();
		tipPasarelakey.setUidActividad(datosSesion.getUidActividad());
		tipPasarelakey.setIdTipoPasarela(idTipoPasarela);

		TipoPasarelaBean tipoPasarelaBean = tipoPasarelaMapper.selectByPrimaryKey(tipPasarelakey);
		if (tipoPasarelaBean == null) {
			String msg = "No se ha encontrado los datos del tipo de pasarela: " + idTipoPasarela;
			log.info("consultar() - " + msg);
			throw new TipoPasarelaNotFoundException(msg);
		}

		return tipoPasarelaBean;
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.pasarelas.tipos.TipoPasarelaService#consultar(java.lang.String, com.comerzzia.core.servicios.sesion.IDatosSesion)
	 */
	@Override
	public TipoPasarelaBean consultar(String idTipoPasarela, IDatosSesion datosSesion) throws TipoPasarelaNotFoundException {
		log.debug("consultar() - Consultando datos del tipo de pasarela: " + idTipoPasarela);
		try {
			TipoPasarelaKey tipPasarelakey = new TipoPasarelaKey();
			tipPasarelakey.setUidActividad(datosSesion.getUidActividad());
			tipPasarelakey.setIdTipoPasarela(idTipoPasarela);

			TipoPasarelaBean tipoPasarelaBean = tipoPasarelaMapper.selectByPrimaryKey(tipPasarelakey);
			if (tipoPasarelaBean == null) {
				String msg = "No se ha encontrado los datos del tipo de pasarela: " + idTipoPasarela;
				log.info("consultar() - " + msg);
				throw new TipoPasarelaNotFoundException(msg);
			}
			
			return tipoPasarelaBean;
		} 
		catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar tipos de pasarelas: " + e.getMessage();

			throw new TipoPasarelaException(mensaje, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.pasarelas.tipos.TipoPasarelaService#consultar(com.comerzzia.core.persistencia.pasarelas.tipos.ParametrosBuscarTipoPasarelaBean, com.comerzzia.core.servicios.sesion.IDatosSesion)
	 */
	@Override
	public PaginaResultados consultar(ParametrosBuscarTipoPasarelaBean param, IDatosSesion datosSesion) {
		log.debug("consultar() - Consultando los tipos de las pasarelas");
		try {
			TipoPasarelaExample example = new TipoPasarelaExample();
			Criteria criteria = example.or().andUidActividadEqualTo(datosSesion.getUidActividad());

			// ID_TIPO_PASARELA
			if (!param.getIdTipoPasarela().isEmpty()) {
				criteria.andIdTipoPasarelaLikeInsensitive("%" + param.getIdTipoPasarela() + "%");
			}
			// DESCONFPASARELA
			if (!param.getDesTipoPasarela().isEmpty()) {
				criteria.andDestipopasarelaLikeInsensitive("%" + param.getDesTipoPasarela() + "%");
			}

			example.setOrderByClause(param.getOrden());

			List<TipoPasarelaBean> resultados = new ArrayList<TipoPasarelaBean>(param.getTamañoPagina());
			PaginaResultados paginaResultados = new PaginaResultados(param, resultados);

			List<TipoPasarelaBean> tiposPasarelas = tipoPasarelaMapper.selectByExample(example);

			int fromIndex = (paginaResultados.getInicio() - 1);
			int toIndex = (paginaResultados.getInicio() + paginaResultados.getTamañoPagina() - 1);
			if (toIndex > tiposPasarelas.size()) {
				toIndex = tiposPasarelas.size();
			}

			resultados.addAll(tiposPasarelas.subList(fromIndex, toIndex));
			paginaResultados.setTotalResultados(tiposPasarelas.size());

			return paginaResultados;
		} catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar los tipos de las pasarelas: " + e.getMessage();

			throw new TipoPasarelaException(mensaje, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.pasarelas.tipos.TipoPasarelaService#salvar(com.comerzzia.core.model.pasarelas.tipos.TipoPasarelaBean, com.comerzzia.core.servicios.sesion.DatosSesionBean)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void salvar(TipoPasarelaBean tipoPasarela, DatosSesionBean datosSesion) {
		switch (tipoPasarela.getEstadoBean()) {
		case Estado.NUEVO:
			crear(tipoPasarela, datosSesion);
			break;

		case Estado.MODIFICADO:
			modificar(tipoPasarela, datosSesion);
			break;

		case Estado.BORRADO:
			eliminar(tipoPasarela.getIdTipoPasarela(), datosSesion);
		}
		
	}

	protected void crear(TipoPasarelaBean tipoPasarela, DatosSesionBean datosSesion) throws TipoPasarelaConstraintViolationException {
		log.debug("crear() - Creando una nuevo tipo de pasarela");
		try {
			tipoPasarela.setUidActividad(datosSesion.getUidActividad());
			tipoPasarelaMapper.insert(tipoPasarela);

		} catch (PersistenceException e) {
			log.error("crear() - No se ha podido crear un tipo de pasarela: " + e.getMessage(), e);
			throw e;
		}		
	}

	protected void modificar(TipoPasarelaBean tipoPasarela, DatosSesionBean datosSesion) {
		log.debug("modificar() - Modificando un tipo de pasarela: " + tipoPasarela.getIdTipoPasarela());
		try {
			tipoPasarelaMapper.updateByPrimaryKey(tipoPasarela);
		} catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar los tipos de las pasarelas: " + e.getMessage();

			throw new TipoPasarelaException(mensaje, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.pasarelas.tipos.TipoPasarelaService#eliminar(java.lang.String, com.comerzzia.core.servicios.sesion.DatosSesionBean)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(String idTipoPasarela, DatosSesionBean datosSesion) throws TipoPasarelaConstraintViolationException{
		log.debug("eliminar() - Eliminando un tipo de pasarela: " + idTipoPasarela);
		try {
			TipoPasarelaKey key = new TipoPasarelaKey();
			key.setUidActividad(datosSesion.getUidActividad());
			key.setIdTipoPasarela(idTipoPasarela);
			tipoPasarelaMapper.deleteByPrimaryKey(key);
		} catch (PersistenceException e) {			
			log.error("eliminar() - No se ha podido eliminar la tipo de la pasarela: " + e.getMessage(), e);
			throw e;
		}		
	}
}