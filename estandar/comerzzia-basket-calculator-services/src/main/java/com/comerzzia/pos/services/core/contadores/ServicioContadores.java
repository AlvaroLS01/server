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
package com.comerzzia.pos.services.core.contadores;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.core.basketcalculator.util.base.Estado;
import com.comerzzia.pos.persistence.core.config.configcontadores.ConfigContadorBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ConfigContadorParametroBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoBean;
import com.comerzzia.pos.persistence.core.contadores.ContadorBean;
import com.comerzzia.pos.persistence.core.contadores.ContadorKey;
import com.comerzzia.pos.persistence.core.contadores.POSContadorMapper;
import com.comerzzia.pos.services.cajas.conceptos.CajaConceptosServices;
import com.comerzzia.pos.services.core.config.configContadores.ContadoresConfigInvalidException;
import com.comerzzia.pos.services.core.config.configContadores.ServicioConfigContadores;
import com.comerzzia.pos.services.core.config.configContadores.parametros.ConfigContadoresParametrosException;
import com.comerzzia.pos.services.core.config.configContadores.parametros.ConfigContadoresParametrosInvalidException;
import com.comerzzia.pos.services.core.config.configContadores.parametros.ServicioConfigContadoresParametros;
import com.comerzzia.pos.services.core.config.configContadores.rangos.ServicioConfigContadoresRangos;
import com.comerzzia.pos.services.core.variables.VariablesServices;

@Service
public class ServicioContadores {
	
	protected static Logger log = Logger.getLogger(ServicioContadores.class);
	
	@Autowired
	protected CajaConceptosServices cajaConceptosServices;
	
	@Autowired
	protected VariablesServices variablesServices;
	
	@Autowired
	protected ServicioConfigContadores servicioConfigContadores;
	
	@Autowired
	protected ServicioConfigContadoresParametros servicioConfigContadoresParametros;
	
	@Autowired
	protected ServicioConfigContadoresRangos servicioConfigContadoresRangos;
	
	@Autowired
	protected POSContadorMapper contadorMapper;
	
	/**
	 * Obtiene toda la información del contador.
	 * 
	 * @param idContador
     * @param uidActividad
	 * @return
     * @throws com.comerzzia.pos.services.core.contadores.ContadorServiceException
	 */
	public ContadorBean obtenerContador(String idContador, String uidActividad) throws ContadorServiceException {
		return obtenerContador(idContador, null, uidActividad);
	}
	
	/**
	 * Obtiene toda la información del contador.
	 * 
	 * @param idContador
	 * @param parametrosContador
     * @param uidActividad
	 * @return
	 * @throws ContadorNotFoundException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public ContadorBean obtenerContador(String idContador, Map<String, String> parametrosContador, String uidActividad) throws ContadorServiceException, ContadorNotFoundException {
		ContadorBean contador = null;
		ConfigContadorBean configContador = null;
		ContadorKey contadorPrimaryKey = new ContadorKey();
		contadorPrimaryKey.setUidActividad(uidActividad);
		contadorPrimaryKey.setIdContador(idContador);
		try {
			log.debug("obtenerContador() - Obteniendo el valor para el contador " + idContador);

			if(parametrosContador != null) {
				configContador = servicioConfigContadores.getCacheConfigContadores().get(idContador);
				
				if(configContador == null){
					// Obtenemos la configuración del contador
					configContador = servicioConfigContadores.consultar(idContador);
				}
				else{
					log.debug("obtenerContador() - La configuración del contador " + idContador + " se ha obtenido desde la caché");
				}
				
				if(configContador == null) {
					String mensaje = "No se ha encontrado la configuración para el contador " + idContador;
					log.error("obtenerContador() - " + mensaje);
					throw new ContadorNotFoundException(mensaje);
				}
				
				servicioConfigContadores.getCacheConfigContadores().put(idContador, configContador);
				
				// Obtenemos el listado de parámetros efectivos del contador. Se entiende por parámetro efectivo aquel que perteneciendo
				// a los parámetros de la configuración del contador, también se encuentra como clave en el Map de parámetros que se pasa.
				Map<String, ConfigContadorParametroBean> mapParametrosEfectivos = obtenerParametrosEfectivos(idContador, parametrosContador);
				
				// Aplicamos los parámetros efectivos a las máscaras de la configuración
				obtenerValoresParaMascaras(configContador, mapParametrosEfectivos);
				
				// Comprobamos que todas las máscaras de se han evaluado correctamente, es decir, no queda ningún carácter # en los valores de los divisores
				if(!validarValoresDivisores(configContador)) {
					String mensaje = "No se han establecido todos los parámetros para la configuración del contador " + idContador;
					log.error("obtenerContador() - " + mensaje);
					throw new ContadorServiceException(mensaje);
				}
				
				// Obtenemos el contador
				contadorPrimaryKey.setDivisor1(configContador.getValorDivisor1());
				contadorPrimaryKey.setDivisor2(configContador.getValorDivisor2());
				contadorPrimaryKey.setDivisor3(configContador.getValorDivisor3());
				
				configContador.setParametros(new ArrayList(mapParametrosEfectivos.values()));
			}
			contador = contadorMapper.selectByPrimaryKey(contadorPrimaryKey);
			
			// Si el contador aún no existe, se crea con valor inicial 0.
			if(contador == null) {
				contador = new ContadorBean();
				contador.setUidActividad(contadorPrimaryKey.getUidActividad());
				contador.setIdContador(contadorPrimaryKey.getIdContador());
				contador.setDivisor1(contadorPrimaryKey.getDivisor1());
				contador.setDivisor2(contadorPrimaryKey.getDivisor2());
				contador.setDivisor3(contadorPrimaryKey.getDivisor3());
				contador.setValor(Long.valueOf(0));
				
				contadorMapper.insert(contador);
			}
			contador.setConfigContador(configContador);
			
			Date fechaActual = new Date();
			//Sólo se actualizará el contador si es la primera consulta o si la fecha actual es igual o mayor que la ultima petición
			if(contador.getUltimaPeticion() == null || (fechaActual.equals(contador.getUltimaPeticion()) || fechaActual.after(contador.getUltimaPeticion()))) {
				//Incrementamos en 1 el valor del contador
				if(contador.getConfigContador() != null && contador.getConfigContador().getLongitudMaxima() != null && contador.getConfigContador().getLongitudMaxima() < String.valueOf(contador.getValor() + 1).length()){
					throw new ContadoresConfigInvalidException("El valor del contador supera la longitud máxima.");
				}
				contador.setValor(contador.getValor() + 1);
				contador.setUltimaPeticion(new Date());
				contadorMapper.updateByPrimaryKey(contador);
			}
			else{
				String mensaje = "Se está intentando acceder a un contador cuya fecha de última petición es posterior a la fecha actual";
				log.error("obtenerContador() - "+mensaje);
				throw new ContadorServiceException(mensaje);
			}
			
			return contador;
		}
		catch (Exception e) {
                        log.error("obtenerContador() - Error obteniendo contador para actividad "+ uidActividad +" y contador " + idContador);
			if(e instanceof ContadorNotFoundException) {
				throw new ContadorNotFoundException(e.getMessage());
			}
			else if(e instanceof ContadorServiceException ) {
				throw new ContadorServiceException(e);
			}
			else{
				log.error("obtenerContador() - " + e.getMessage());
				String mensaje = "Error obteniendo el contador " + idContador + ": " + e.getMessage();
				throw new ContadorServiceException(mensaje);
			}
		}
	}
	

	protected boolean validarValoresDivisores(ConfigContadorBean configContador) {
		if(!"*".equals(configContador.getValorDivisor1())) {
			if(configContador.getValorDivisor1().contains("#")) {
				return false;
			}
		}
		
		if(!"*".equals(configContador.getValorDivisor2())) {
			if(configContador.getValorDivisor2().contains("#")) {
				return false;
			}
		}
		
		if(!"*".equals(configContador.getValorDivisor3())) {
			if(configContador.getValorDivisor3().contains("#")) {
				return false;
			}
		}
		
		
		return true;
	}
	
	/**
	 * Obtiene los valores de las máscaras de los divisores.
	 * 
	 * @param configContador
	 * @param parametrosEfectivos
	 */
	protected void obtenerValoresParaMascaras(ConfigContadorBean configContador, Map<String, ConfigContadorParametroBean> mapParametrosEfectivos) {
		Pattern pattern = Pattern.compile("#(.*?)#");
		Matcher matcher;
		
		// Búscamos el valor para la MascaraDivisor1
		if(!"*".equals(configContador.getMascaraDivisor1())) {
			matcher = pattern.matcher(configContador.getMascaraDivisor1());
			
			String[] valoresMascara1 = obtenerValorMascara(mapParametrosEfectivos, configContador, configContador.getMascaraDivisor1(), matcher);
			configContador.setValorDivisor1(valoresMascara1[0]);
			configContador.setValorDivisor1Formateado(valoresMascara1[1]);
		}
		
		// Búscamos el valor para la MascaraDivisor2
		
		if(!"*".equals(configContador.getMascaraDivisor2())) {
			matcher = pattern.matcher(configContador.getMascaraDivisor2());
			
			String[] valoresMascara2 = obtenerValorMascara(mapParametrosEfectivos, configContador, configContador.getMascaraDivisor2(), matcher);
			configContador.setValorDivisor2(valoresMascara2[0]);
			configContador.setValorDivisor2Formateado(valoresMascara2[1]);
		}
		
		// Búscamos el valor para la MascaraDivisor3
		if(!"*".equals(configContador.getMascaraDivisor3())) {
			matcher = pattern.matcher(configContador.getMascaraDivisor3());
			
			String[] valoresMascara3 = obtenerValorMascara(mapParametrosEfectivos, configContador, configContador.getMascaraDivisor3(), matcher);
			configContador.setValorDivisor3(valoresMascara3[0]);
			configContador.setValorDivisor3Formateado(valoresMascara3[1]);
		}
	}
	
	/**
	 * Obtene el valor de la máscara de divisor.
	 * 
	 * @param parametrosEfectivos
	 * @param configContador
	 * @param mascara
	 * @param matcher
	 * @return array de dos valores. [0] = valor de la máscara; [1] = valor de la máscara aplicado el posible formateo.
	 */
	protected String[] obtenerValorMascara(Map<String, ConfigContadorParametroBean> mapParametrosEfectivos, ConfigContadorBean configContador, String mascara, Matcher matcher) {
		String mascaraFormateada = mascara;
		while(matcher.find()) {
			String param = matcher.group(1);
			if(mapParametrosEfectivos.containsKey(param)) {
				String valorParam = mapParametrosEfectivos.get(param).getValorParametro();
				mascara = mascara.replace("#"+param+"#", valorParam);
				
				String valorParamFormateado = mapParametrosEfectivos.get(param).formatearValorMascara(valorParam);
				mascaraFormateada = mascaraFormateada.replace("#"+param+"#", valorParamFormateado);
			}
		}
		
		return new String[]{mascara, mascaraFormateada};
	}
	
	/**
	 * Obtiene todos los parámetros efectivos para la configuración del contador y el Map de valores que se pasan como parámetro.
	 * 
	 * @param configContadorParametroMapper
	 * @param idContador
	 * @param parametrosContador
	 * @return
	 * @throws ConfigContadoresParametrosException 
	 * @throws ConfigContadoresParametrosInvalidException 
	 */
	protected Map<String, ConfigContadorParametroBean> obtenerParametrosEfectivos(String idContador, Map<String, String> parametrosContador) throws ConfigContadoresParametrosException, ConfigContadoresParametrosInvalidException {
		
		Map<String, ConfigContadorParametroBean> mapConfigParametrosEfectivos = new HashMap<>();
		
		Map<String, ConfigContadorParametroBean> mapConfigParametrosPermitidos = servicioConfigContadoresParametros.consultarMap(idContador);
		
		for(Entry<String, String> entry: parametrosContador.entrySet()) {
			if(mapConfigParametrosPermitidos.containsKey(entry.getKey())) {
				ConfigContadorParametroBean parametro = mapConfigParametrosPermitidos.get(entry.getKey());
				if(parametro.getLongitudMaxima() != null && parametro.getLongitudMaxima() < entry.getValue().length()){
					log.error("Error en la configuración del parámetro "+entry.getKey()+" del contador "+idContador);
					throw new ConfigContadoresParametrosInvalidException();
				}
				parametro.setValorParametro(entry.getValue());
				mapConfigParametrosEfectivos.put(entry.getKey(), parametro);
			}
		}
		
		return mapConfigParametrosEfectivos;
	}

	/**
	 * Obtiene el valor numérico del contador.
	 * 
	 * @param idContador
     * @param uidActividad
	 * @return
     * @throws com.comerzzia.pos.services.core.contadores.ContadorServiceException
	 */
	public Long obtenerValorContador(String idContador, String uidActividad) throws ContadorServiceException {
		ContadorBean contador = obtenerContador(idContador, uidActividad);
		return contador.getValor();
	}
	
	/**
	 * Obtiene el valor numérico del contador.
	 * 
	 * @param idContador
	 * @param parametrosContador
     * @param uidActividad
	 * @return
     * @throws com.comerzzia.pos.services.core.contadores.ContadorServiceException
	 */
	public Long obtenerValorContador(String idContador, Map<String, String> parametrosContador, String uidActividad) throws ContadorServiceException  {
		ContadorBean contador = obtenerContador(idContador, parametrosContador, uidActividad);
		return contador.getValor();
	} 
	
	public String obtenerValorTotalConSeparador(String divisor, String valorContadorFormateado){
		String separador = variablesServices.getVariableAsString(VariablesServices.CONTADORES_CARACTER_SEPARADOR);
		if("".equals(separador)){
			separador = "/";
		}
		return divisor+separador+valorContadorFormateado;
	}
	
	public ContadorBean consultarContador(ConfigContadorBean configContador, ConfigContadorRangoBean rango, Map<String,String> parametrosContador, String uidActividad) throws ContadorServiceException, ContadoresConfigInvalidException, ConfigContadoresParametrosInvalidException{
		ContadorBean contador = null;
		ContadorKey contadorPrimaryKey = new ContadorKey();
		contadorPrimaryKey.setUidActividad(uidActividad);
		contadorPrimaryKey.setIdContador(configContador.getIdContador());
		
		
		try {
			log.debug("consultarContador() - Obteniendo el valor para el contador " + configContador.getIdContador());
			if(parametrosContador != null) {
				// Obtenemos el listado de parámetros efectivos del contador. Se entiende por parámetro efectivo aquel que perteneciendo
				// a los parámetros de la configuración del contador, también se encuentra como clave en el Map de parámetros que se pasa.
				Map<String, ConfigContadorParametroBean> mapParametrosEfectivos = obtenerParametrosEfectivos(configContador.getIdContador(), parametrosContador);
				
				// Aplicamos los parámetros efectivos a las máscaras de la configuración
				obtenerValoresParaMascaras(configContador, mapParametrosEfectivos);
				
				// Comprobamos que todas las máscaras de se han evaluado correctamente, es decir, no queda ningún carácter # en los valores de los divisores
				if(!validarValoresDivisores(configContador)) {
					String mensaje = "No se han establecido todos los parámetros para la configuración del contador " + configContador.getIdContador();
					log.error("obtenerContador() - " + mensaje);
					throw new ContadorServiceException(mensaje);
				}
				
				// Obtenemos el contador
				contadorPrimaryKey.setDivisor1(configContador.getValorDivisor1());
				contadorPrimaryKey.setDivisor2(configContador.getValorDivisor2());
				contadorPrimaryKey.setDivisor3(configContador.getValorDivisor3());
				
				configContador.setParametros(new ArrayList(mapParametrosEfectivos.values()));
			}
			contador = contadorMapper.selectByPrimaryKey(contadorPrimaryKey);
			
			if(!configContador.isRangosCargados()){
				configContador.setRangos(servicioConfigContadoresRangos.consultar(contador.getIdContador()));
				configContador.setRangosCargados(true);
			}
			
			// Si el contador aún no existe se crea con valor inicial 0 o el mínimo del rango activo
			if(contador == null) {
				contador = new ContadorBean();
				contador.setUidActividad(contadorPrimaryKey.getUidActividad());
				contador.setIdContador(contadorPrimaryKey.getIdContador());
				contador.setDivisor1(contadorPrimaryKey.getDivisor1());
				contador.setDivisor2(contadorPrimaryKey.getDivisor2());
				contador.setDivisor3(contadorPrimaryKey.getDivisor3());
				contador.setEstadoBean(Estado.NUEVO);
				
				if(rango!=null){
					if(contador.getConfigContador() != null && contador.getConfigContador().getLongitudMaxima() != null && contador.getConfigContador().getLongitudMaxima() < String.valueOf(rango.getRangoInicio()-1).length()){
						throw new ContadoresConfigInvalidException("El valor del contador supera la longitud máxima.");
					}
					contador.setValor(rango.getRangoInicio()-1);
				}else{
					contador.setValor(Long.valueOf(0));
				}
				
			}
			contador.setConfigContador(configContador);
			
			return contador;
			
		}catch (Exception e) {
			String msg = "consultarContador() - Error obteniendo contador para actividad "+ uidActividad +" y contador " + configContador.getIdContador()+". ";
			
			if(e instanceof ContadorNotFoundException) {
				log.error(msg+"Contador no encontrado. ");
				throw new ContadorNotFoundException(e.getMessage());
			}
			else if (e instanceof ContadoresConfigInvalidException){ 
				log.error(msg+"No se ha podido obtener la lista de rangos del contador. ");
				throw new ContadoresConfigInvalidException(e);
			}
			else if (e instanceof ConfigContadoresParametrosInvalidException){
				log.error(msg+"Configuración de parametros inválida. ");
				throw new ConfigContadoresParametrosInvalidException(e);
			}
			else{
				log.error("consultarContador() - " + e.getMessage());
				String mensaje = "Error obteniendo el contador " + configContador.getIdContador() + ": " + e.getMessage();
				throw new ContadorServiceException(mensaje);
			}
		}
		
	}
	
	public ContadorBean incrementarContador(ContadorBean contador) throws ContadorServiceException, ContadoresConfigInvalidException{
		Date fechaActual = new Date();
		ConfigContadorRangoBean rango = contador.getConfigContadorRango();
		//Si el contador tiene una vigencia activa
		if(rango != null){
			if(contador.getValor()+1>rango.getRangoFin()){
				String mensaje = "Se está intentando incrementar un contador por encima de su rango numérico máximo";
				log.warn("consultarContador() - "+mensaje);
				return contador;
			}
		}
		
		//Sólo se actualizará el contador si es la primera consulta o si la fecha actual es igual o mayor que la ultima petición
		if(contador.getUltimaPeticion() == null || (fechaActual.equals(contador.getUltimaPeticion()) || fechaActual.after(contador.getUltimaPeticion()))) {
			
			if(rango==null || (rango.getRangoFechaInicio().before(new Date()) && rango.getRangoFechaFin().after(new Date())) ){
				//Incrementamos en 1 el valor del contador
				if(contador.getConfigContador() != null && contador.getConfigContador().getLongitudMaxima() != null && contador.getConfigContador().getLongitudMaxima() < String.valueOf(contador.getValor() + 1).length()){
					throw new ContadoresConfigInvalidException("El valor del contador supera la longitud máxima.");
				}
				contador.setValor(contador.getValor() + 1);
				contador.setUltimaPeticion(new Date());
				if(rango!=null){
					contador.setIdRangoUltimaPeticion(rango.getIdRango());
				}
				contadorMapper.updateByPrimaryKey(contador);
			}
		}
		else{
			String mensaje = "Se está intentando acceder a un contador cuya fecha de última petición es posterior a la fecha actual";
			log.error("consultarContador() - "+mensaje);
			throw new ContadorServiceException(mensaje);
		}
		
		return contador;
		
	}
	
	/*
	 * Devuleve el primer contador que puede ser incrementado según los rangos disponibles
	 */
	public ContadorBean consultarContadorActivo(ConfigContadorBean configContador, Map<String, String> parametrosContador, Map<String, String> condicionesVigencia, String uidActividad, boolean incrementar) throws ConfigContadoresParametrosException, ContadorServiceException{
		ContadorBean res = new ContadorBean();
		ContadorBean contador = null;
		ContadorBean mayorConsultado = null;
		try {
			if(configContador.isRangosCargados()){
				if(!configContador.getRangos().isEmpty()){
					//Si se encuentra un contador estos errores desaparecerán
					if(configContador.getRangoFechasActivas().isEmpty()){
						res.setError(ContadorBean.ERROR_FECHAS);
					}else{
						res.setError(ContadorBean.ERROR_RANGOS);
					}
					
					//Evaluamos todos los rangos para encontrar el que corresponda
					for(ConfigContadorRangoBean rango:configContador.getRangos()){
						parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_RANGO, rango.getIdRango());
						contador = consultarContador(configContador,rango, parametrosContador, uidActividad);
						contador.setConfigContadorRango(rango);
						
						//Evaluamos si el contador obtenido corresponde al conjunto actual de empresa-almacén-caja
						if(evaluaUsoEnCaja(contador,condicionesVigencia)){
							
							//Evaluamos si el contador tiene numeración disponible
							if((contador.getValor()<rango.getRangoFin() || (incrementar && contador.getValor().equals(rango.getRangoFin())))
									&&(mayorConsultado==null || mayorConsultado.getValor() < rango.getRangoFin())){
								if(contador.disponible()){
									res=contador;
									
									// Comprobamos si el contador anterior caducó por fecha y el actual se ha creado con intención de continuarlo numericamente
									if(evaluaContinuidadContadores(mayorConsultado,res)){	
										res.setValor(mayorConsultado.getValor());
									}
									if(res.getEstadoBean()==Estado.NUEVO){
										//Al crear un contador que continua en una vigencia que no es la primera, no debe haber un salto de numeración respecto al anterior
										if(mayorConsultado!=null && res.getValor() - mayorConsultado.getValor() > 0){
											throw new ContadorNumeracionException("No se puede crear el nuevo contador porque hay un salto en la numeración de las vigencias.");
										}
										contadorMapper.insert(contador);
									}
									if(incrementar){
										incrementarContador(contador);
									}
									break;
								}
							}
							
							//Almacenamos el valor del contador más alto registrado para asegurarnos que se continúa por el mismo
							if(mayorConsultado == null || mayorConsultado.getValor() < contador.getValor() ){
								mayorConsultado = contador;									
							}
						}
					}
				}else{
					res = consultarContador(configContador,null, parametrosContador, uidActividad);
					if(res.getEstadoBean()==Estado.NUEVO){
						contadorMapper.insert(res);
					}
					if(incrementar){
						incrementarContador(res);
					}
				}
				
			}
		}
		catch(ContadoresConfigInvalidException e){
			res.setError(ContadorBean.ERROR_CONTADOR_INVALIDO);
		}
		catch(ConfigContadoresParametrosInvalidException e){
			res.setError(ContadorBean.ERROR_PARAMETRO_INVALIDO);
		}
		catch(ContadorNumeracionException e){
			res.setError(ContadorBean.ERROR_SALTO_NUMERACION);
		}
		return res;
	}

	protected boolean evaluaContinuidadContadores(ContadorBean mayorConsultado, ContadorBean contadorContinuo) {
		return mayorConsultado!=null && mayorConsultado.getConfigContadorRango() != null && 
				mayorConsultado.getConfigContadorRango().getRangoInicio().equals(contadorContinuo.getConfigContadorRango().getRangoInicio()) &&
				mayorConsultado.getConfigContadorRango().getRangoFin().equals(contadorContinuo.getConfigContadorRango().getRangoFin()) &&
				contadorContinuo.getValor() < mayorConsultado.getValor();
    }

	protected boolean evaluaUsoEnCaja(ContadorBean contador, Map<String, String> condicionesVigencia) {
	    return contador.getConfigContadorRango().getCodemp().equals(condicionesVigencia.get(ConfigContadorRangoBean.VIGENCIA_CODEMP)) 
				&& (contador.getConfigContadorRango().getCodalm() == null || contador.getConfigContadorRango().getCodalm().equals(condicionesVigencia.get(ConfigContadorRangoBean.VIGENCIA_CODALM)))
				&& (contador.getConfigContadorRango().getCodcaja() == null || contador.getConfigContadorRango().getCodcaja().equals(condicionesVigencia.get(ConfigContadorRangoBean.VIGENCIA_CODCAJA)));
    }
	
}
