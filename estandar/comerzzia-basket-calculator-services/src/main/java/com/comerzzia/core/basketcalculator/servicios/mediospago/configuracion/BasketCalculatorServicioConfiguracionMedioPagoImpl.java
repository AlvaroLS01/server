package com.comerzzia.core.basketcalculator.servicios.mediospago.configuracion;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.core.basketcalculator.model.mediospago.configuracion.ConfiguracionMedioPagoBean;
import com.comerzzia.core.basketcalculator.model.mediospago.configuracion.ConfiguracionMedioPagoExample;
import com.comerzzia.core.basketcalculator.model.mediospago.configuracion.ConfiguracionMedioPagoKey;
import com.comerzzia.core.basketcalculator.model.pasarelas.PasarelaConfig;
import com.comerzzia.core.basketcalculator.persistencia.mediospago.configuracion.BasketCalculatorConfiguracionMedioPagoMapper;
import com.comerzzia.core.basketcalculator.servicios.pasarelas.configuraciones.ConfiguracionPasarelaService;
import com.comerzzia.core.basketcalculator.servicios.pasarelas.tipos.Pasarela;
import com.comerzzia.core.basketcalculator.servicios.sesion.IDatosSesion;
import com.comerzzia.core.basketcalculator.util.base.Estado;
import com.comerzzia.pos.services.ContextHolder;

@Service
public class BasketCalculatorServicioConfiguracionMedioPagoImpl implements ConfiguracionMedioPagoService {

	protected static Logger log = Logger.getLogger(BasketCalculatorServicioConfiguracionMedioPagoImpl.class);
	
	protected static ConfiguracionMedioPagoService instance;
	
	@Autowired
	BasketCalculatorConfiguracionMedioPagoMapper mapper;
	
	@Autowired
	ConfiguracionPasarelaService configuracionPasarelaService;
	
	@Deprecated
	public static ConfiguracionMedioPagoService get(){
		if (instance != null) {
		   return instance;
		} else { 
   		   return (ConfiguracionMedioPagoService) ContextHolder.get().getBean(ConfiguracionMedioPagoService.class);
		}
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.mediospago.configuracion.ConfiguracionMedioPagoService#consultar(java.lang.String, java.lang.String, java.lang.String, com.comerzzia.core.servicios.sesion.DatosSesionBean)
	 */
	@Override
	public ConfiguracionMedioPagoBean consultar(String codMedPag, String idClase, String idObjeto, IDatosSesion datosSesion) {
			
		log.debug("consultar() - Consultando datos de la configuración del medio de pago: " + codMedPag + ", la clase: "+ idClase +", idObjeto"+ idObjeto);
		
		ConfiguracionMedioPagoKey key = new ConfiguracionMedioPagoKey();
		key.setUidActividad(datosSesion.getUidActividad());
		key.setCodmedpag(codMedPag);
		key.setIdClase(idClase);
		key.setIdObjeto(idObjeto);
		
		return mapper.selectFromViewByPrimaryKey(key);
		
	}
		
	
	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.mediospago.configuracion.ConfiguracionMedioPagoService#consultarConfiguracion(java.lang.String, java.lang.String, java.lang.String, com.comerzzia.core.servicios.sesion.DatosSesionBean)
	 */
	@Override
	public PasarelaConfig consultarConfiguracion(String codMedPag, String idClase, String idObjeto, IDatosSesion datosSesion) {
		PasarelaConfig pasarelaConfig = null;
		try {
			log.debug("consultarConfiguracion() - Consultando la configuración del medio de pago: " + codMedPag + ", la clase: "+ idClase +", idObjeto"+ idObjeto);
	 
			ConfiguracionMedioPagoKey key = new ConfiguracionMedioPagoKey();
			key.setUidActividad(datosSesion.getUidActividad());
			key.setCodmedpag(codMedPag);
			key.setIdClase(idClase);
			key.setIdObjeto(idObjeto);
			ConfiguracionMedioPagoBean configMedioPago = mapper.selectByPrimaryKey(key);
				
			if(configMedioPago != null){
				Pasarela pasarela = configuracionPasarelaService.consultar(configMedioPago.getIdConfPasarela(), datosSesion);
				pasarelaConfig = pasarela.getPasarela(); 
			}
			return pasarelaConfig; 
		}catch(Exception e) {
			String msg = "Ha ocurrido un error consultando la configuración de medio de pago";
			log.error(msg, e);
			throw new ConfiguracionMedioPagoConstraintViolationException(msg, e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.core.servicios.mediospago.configuracion.ConfiguracionMedioPagoService#salvar(com.comerzzia.core.model.mediospago.configuracion.ConfiguracionMedioPagoBean, com.comerzzia.core.util.db.Connection, com.comerzzia.core.servicios.sesion.DatosSesionBean)
	 */
	@Override
	public void salvar(ConfiguracionMedioPagoBean configuracionMedioPago, IDatosSesion datosSesion) throws ConfiguracionMedioPagoConstraintViolationException, ConfiguracionMedioPagoException{

		switch (configuracionMedioPago.getEstadoBean()) {
			case Estado.NUEVO:
				crear(datosSesion, configuracionMedioPago);
				break;
			case Estado.MODIFICADO:
				modificar(datosSesion, configuracionMedioPago);
				break;
			case Estado.BORRADO:
				eliminar(datosSesion, configuracionMedioPago.getCodmedpag(), configuracionMedioPago.getIdClase(), configuracionMedioPago.getIdObjeto());
				break;
		}
	}

	protected void crear(IDatosSesion datosSesion, ConfiguracionMedioPagoBean configuracionMedioPago) throws ConfiguracionMedioPagoConstraintViolationException, ConfiguracionMedioPagoException{
		
		try {
			
			configuracionMedioPago.setUidActividad(datosSesion.getUidActividad());
			
			mapper.insert(configuracionMedioPago);
		}
		catch (PersistenceException e) {
			String msg = "Se ha producido un error añadiendo la configuración del medio de pago  " + configuracionMedioPago.getCodmedpag() + ", para la tienda " +configuracionMedioPago.getIdObjeto();
			log.error("crear() - " + msg);
			throw e;
			
		}catch (Exception e) {
			String msg = "Se ha producido un error añadiendo la configuración del medio de pago  " + configuracionMedioPago.getCodmedpag() + ", para la tienda " +configuracionMedioPago.getIdObjeto();
			log.error("crear() - " + msg);
			throw new ConfiguracionMedioPagoException(msg, e);
		}
		
	}

	protected void eliminar(IDatosSesion datosSesion, String codmedpag, String idClase, String idObjeto) throws ConfiguracionMedioPagoConstraintViolationException, ConfiguracionMedioPagoException{
		
		try {
			log.debug("eliminar() - Eliminando configuración del medio de pago  " + codmedpag + " para la tienda " +idObjeto);
			
			ConfiguracionMedioPagoExample example = new ConfiguracionMedioPagoExample();
			example.or().andUidActividadEqualTo(datosSesion.getUidActividad())
						.andCodmedpagEqualTo(codmedpag)
						.andIdClaseEqualTo(idClase)
						.andIdObjetoEqualTo(idObjeto);
			
			mapper.deleteByExample(example);
		}
		catch(PersistenceException e){
			log.error("eliminar() - Error eliminando la configuración del medio de pago  " + codmedpag + " para la tienda " +idObjeto +" - "+ e.getMessage());
			throw e;
			
		}catch(Exception e){
			String msg = "eliminar() - Error eliminando la configuración del medio de pago  " + codmedpag + " para la tienda " +idObjeto;
			log.error("eliminar() - " + msg);
			throw new ConfiguracionMedioPagoException(msg, e);
		}
	}
	
	protected void modificar(IDatosSesion datosSesion, ConfiguracionMedioPagoBean configuracionMedioPago) throws ConfiguracionMedioPagoConstraintViolationException, ConfiguracionMedioPagoException{
		
		try {
			log.debug("modificar() - Modificando configuración del medio de pago  " + configuracionMedioPago.getCodmedpag() + ", para la tienda " +configuracionMedioPago.getIdObjeto());			
			
			mapper.updateByPrimaryKey(configuracionMedioPago);
		}
		catch (PersistenceException e) {
			String msg = "Se ha producido un error modificando la configuración del medio de pago  " + configuracionMedioPago.getCodmedpag() + ", para la tienda " +configuracionMedioPago.getIdObjeto();
			log.error("modificar() - " + msg);
			throw e;
			
		}catch (Exception e) {
			String msg = "Se ha producido un error modificando la configuración del medio de pago  " + configuracionMedioPago.getCodmedpag() + ", para la tienda " +configuracionMedioPago.getIdObjeto();
			log.error("modificar() - " + msg);
			throw new ConfiguracionMedioPagoException(msg, e);
		}
		
	}

	@Override
	public ConfiguracionMedioPagoBean selectByPrimaryKey(String codMedPag, String idClase, String idObjeto,
			IDatosSesion datosSesion) {
		
		log.debug("consultar() - Consultando datos de la configuración del medio de pago: " + codMedPag + ", la clase: "+ idClase +", idObjeto"+ idObjeto);
		
		ConfiguracionMedioPagoKey key = new ConfiguracionMedioPagoKey();
		key.setUidActividad(datosSesion.getUidActividad());
		key.setCodmedpag(codMedPag);
		key.setIdClase(idClase);
		key.setIdObjeto(idObjeto);
		
		return mapper.selectByPrimaryKey(key);
	}
}