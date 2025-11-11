package com.comerzzia.api.v2.facturacionmagento.services.contadores;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.contadores.ContadoresMapper;
import com.comerzzia.api.v2.facturacionmagento.services.contadores.config.ConfigContadoresService;
import com.comerzzia.api.v2.facturacionmagento.services.contadores.config.parametros.ConfigContadoresParametroService;
import com.comerzzia.api.v2.facturacionmagento.services.contadores.rangos.ConfigContadorRangosService;
import com.comerzzia.core.basketcalculator.util.base.Estado;
import com.comerzzia.core.servicios.config.configContadores.ContadoresConfigNotFoundException;
import com.comerzzia.core.servicios.sesion.DatosSesionBean;
import com.comerzzia.core.servicios.variables.VariableException;
import com.comerzzia.core.servicios.variables.VariableNotFoundException;
import com.comerzzia.core.servicios.variables.VariablesService;
import com.comerzzia.pos.persistence.core.config.configcontadores.ConfigContadorBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ConfigContadorParametroBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoBean;
import com.comerzzia.pos.persistence.core.contadores.ContadorBean;
import com.comerzzia.pos.persistence.core.contadores.ContadorExample;
import com.comerzzia.pos.persistence.core.contadores.ContadorKey;
import com.comerzzia.pos.services.core.config.configContadores.ContadoresConfigInvalidException;
import com.comerzzia.pos.services.core.config.configContadores.parametros.ConfigContadoresParametrosException;
import com.comerzzia.pos.services.core.config.configContadores.parametros.ConfigContadoresParametrosInvalidException;
import com.comerzzia.pos.services.core.contadores.ContadorNotFoundException;
import com.comerzzia.pos.services.core.contadores.ContadorServiceException;
import com.comerzzia.pos.services.core.variables.VariablesServices;

@Component
public class ContadorService {

	protected static final Logger log = Logger.getLogger(ContadorService.class);

	@Autowired
	protected ContadoresMapper contadoresMapper;
	@Autowired
	protected ConfigContadorRangosService configContadorRangosService;
	@Autowired
	protected ConfigContadoresParametroService configContadoresParametroService;
	@Autowired
	protected ConfigContadoresService configContadoresService;
	@Autowired
	protected VariablesService variablesService;

	public ContadorBean consultarContadorActivo(ConfigContadorBean configContador, Map<String, String> parametrosContador, Map<String, String> condicionesVigencia, String uidActividad,
	        boolean incrementar, boolean simulacion) throws ContadoresConfigInvalidException, ConfigContadoresParametrosInvalidException, ContadorServiceException {
		ContadorBean res = new ContadorBean();
		ContadorBean contador = null;

		if (configContador.isRangosCargados()) {
			if (!configContador.getRangos().isEmpty()) {
				// Si se encuentra un contador estos errores desaparecerÃ¡n
				if (configContador.getRangoFechasActivas().isEmpty()) {
					res.setError(ContadorBean.ERROR_FECHAS);
				}
				else {
					res.setError(ContadorBean.ERROR_RANGOS);
				}

				// Evaluamos todos los rangos para encontrar el que corresponda
				for (ConfigContadorRangoBean rango : configContador.getRangos()) {
					parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_RANGO, rango.getIdRango());
					contador = consultarContador(configContador, rango, parametrosContador, uidActividad);
					contador.setConfigContadorRango(rango);

					// Evaluamos si el contador obtenido corresponde al conjunto actual de empresa-almacén-caja
					if (evaluaUsoEnCaja(contador, condicionesVigencia)) {

						// Evaluamos si el contador tiene numeraciÃ³n disponible
						if ((contador.getValor() < rango.getRangoFin() || (incrementar && contador.getValor().equals(rango.getRangoFin())))) {
							if (contador.disponible()) {
								res = contador;

								if (!simulacion) {
									if (res.getEstadoBean() == Estado.NUEVO) {
										contadoresMapper.insert(contador);
									}
									if (incrementar) {
										res = incrementarContador(contador);
									}
								}
								break;
							}
						}
					}
				}
			}
			else {
				res = consultarContador(configContador, null, parametrosContador, uidActividad);
				if (!simulacion) {
					if (res.getEstadoBean() == Estado.NUEVO) {
						contadoresMapper.insert(res);
					}
					if (incrementar) {
						res = incrementarContador(res);
					}
				}
			}

		}
		return res;
	}

	private ContadorBean consultarContador(ConfigContadorBean configContador, ConfigContadorRangoBean rango, Map<String, String> parametrosContador, String uidActividad)
	        throws ContadoresConfigInvalidException, ConfigContadoresParametrosInvalidException, ContadorServiceException {
		ContadorBean contador = null;
		ContadorKey contadorPrimaryKey = new ContadorKey();
		contadorPrimaryKey.setUidActividad(uidActividad);
		contadorPrimaryKey.setIdContador(configContador.getIdContador());

		try {
			log.debug("consultarContador() - Obteniendo el valor para el contador " + configContador.getIdContador());
			if (parametrosContador != null) {
				// Obtenemos el listado de parÃ¡metros efectivos del contador. Se entiende por parÃ¡metro efectivo aquel
				// que perteneciendo
				// a los parÃ¡metros de la configuraciÃ³n del contador, tambiÃ©n se encuentra como clave en el Map de
				// parÃ¡metros que se pasa.
				Map<String, ConfigContadorParametroBean> mapParametrosEfectivos = obtenerParametrosEfectivos(configContador.getIdContador(), parametrosContador);

				// Aplicamos los parÃ¡metros efectivos a las mÃ¡scaras de la configuraciÃ³n
				obtenerValoresParaMascaras(configContador, mapParametrosEfectivos);

				// Comprobamos que todas las mÃ¡scaras de se han evaluado correctamente, es decir, no queda ningÃºn
				// carÃ¡cter # en los valores de los divisores
				if (!validarValoresDivisores(configContador)) {
					String mensaje = "No se han establecido todos los parÃ¡metros para la configuraciÃ³n del contador " + configContador.getIdContador();
					log.error("obtenerContador() - " + mensaje);
					throw new ContadorServiceException(mensaje);
				}

				// Obtenemos el contador
				contadorPrimaryKey.setDivisor1(configContador.getValorDivisor1());
				contadorPrimaryKey.setDivisor2(configContador.getValorDivisor2());
				contadorPrimaryKey.setDivisor3(configContador.getValorDivisor3());

				configContador.setParametros(new ArrayList<ConfigContadorParametroBean>(mapParametrosEfectivos.values()));
			}
			contador = contadoresMapper.selectByPrimaryKey(contadorPrimaryKey);

			if (!configContador.isRangosCargados()) {
				configContador.setRangos(configContadorRangosService.consultar(contador.getIdContador()));
				configContador.setRangosCargados(true);
			}

			// Si el contador aun no existe se crea con valor inicial 0 o el mínimo del rango activo
			if (contador == null) {
				contador = new ContadorBean();
				contador.setUidActividad(contadorPrimaryKey.getUidActividad());
				contador.setIdContador(contadorPrimaryKey.getIdContador());
				contador.setDivisor1(contadorPrimaryKey.getDivisor1());
				contador.setDivisor2(contadorPrimaryKey.getDivisor2());
				contador.setDivisor3(contadorPrimaryKey.getDivisor3());
				contador.setEstadoBean(Estado.NUEVO);

				if (rango != null) {
					if (contador.getConfigContador() != null && contador.getConfigContador().getLongitudMaxima() != null
					        && contador.getConfigContador().getLongitudMaxima() < String.valueOf(rango.getRangoInicio() - 1).length()) {
						throw new ContadoresConfigInvalidException("El valor del contador supera la longitud máxima.");
					}
					contador.setValor(rango.getRangoInicio() - 1);
				}
				else {
					contador.setValor(Long.valueOf(0));
				}

			}
			contador.setConfigContador(configContador);

			return contador;

		}
		catch (Exception e) {
			String msg = "consultarContador() - Error obteniendo contador para actividad " + uidActividad + " y contador " + configContador.getIdContador() + ". ";

			if (e instanceof ContadorNotFoundException) {
				log.error(msg + "Contador no encontrado. ");
				throw new ContadorNotFoundException(e.getMessage());
			}
			else if (e instanceof ContadoresConfigInvalidException) {
				log.error(msg + "No se ha podido obtener la lista de rangos del contador. ");
				throw new ContadoresConfigInvalidException(e);
			}
			else if (e instanceof ConfigContadoresParametrosInvalidException) {
				log.error(msg + "Configuración de parametros invÃ¡lida. ");
				throw new ConfigContadoresParametrosInvalidException(e);
			}
			else {
				log.error("consultarContador() - " + e.getMessage());
				String mensaje = "Error obteniendo el contador " + configContador.getIdContador() + ": " + e.getMessage();
				throw new ContadorServiceException(mensaje);
			}
		}

	}

	private boolean evaluaUsoEnCaja(ContadorBean contador, Map<String, String> condicionesVigencia) {
		return contador.getConfigContadorRango().getCodemp().equals(condicionesVigencia.get(ConfigContadorRangoBean.VIGENCIA_CODEMP))
		        && (contador.getConfigContadorRango().getCodalm() == null || contador.getConfigContadorRango().getCodalm().equals(condicionesVigencia.get(ConfigContadorRangoBean.VIGENCIA_CODALM)))
		        && (contador.getConfigContadorRango().getCodcaja() == null || contador.getConfigContadorRango().getCodcaja().equals(condicionesVigencia.get(ConfigContadorRangoBean.VIGENCIA_CODCAJA)));
	}

	private ContadorBean incrementarContador(ContadorBean contador) throws ContadorServiceException, ContadoresConfigInvalidException {
		Date fechaActual = new Date();
		ConfigContadorRangoBean rango = contador.getConfigContadorRango();
		ConfigContadorBean configContador = contador.getConfigContador();

		ContadorKey contadorPrimaryKey = new ContadorKey();
		contadorPrimaryKey.setUidActividad(contador.getUidActividad());
		contadorPrimaryKey.setIdContador(contador.getIdContador());
		contadorPrimaryKey.setDivisor1(configContador.getValorDivisor1());
		contadorPrimaryKey.setDivisor2(configContador.getValorDivisor2());
		contadorPrimaryKey.setDivisor3(configContador.getValorDivisor3());
		// SÃ³lo se actualizarÃ¡ el contador si es la primera consulta o si la fecha actual es igual o mayor que la
		// ultima peticiÃ³n
		if (contador.getUltimaPeticion() == null || (fechaActual.equals(contador.getUltimaPeticion()) || DateUtils.addHours(fechaActual, 1).after(contador.getUltimaPeticion()))) {
			boolean lock = true;
			int numIntentos = 100;

			while (lock && numIntentos > 0) {
				contador = contadoresMapper.selectByPrimaryKey(contadorPrimaryKey);
				contador.setConfigContador(configContador);
				contador.setConfigContadorRango(rango);

				// Si el contador tiene una vigencia activa
				if (rango != null) {
					if (contador.getValor() + 1 > rango.getRangoFin()) {
						String mensaje = "Se está intentando incrementar un contador por encima de su rango numÃ©rico mÃ¡ximo";
						log.warn("incrementarContador() - " + mensaje);
						return contador;
					}
				}

				if (rango == null || (rango.getRangoFechaInicio().before(new Date()) && rango.getRangoFechaFin().after(new Date()))) {
					if (contador.getConfigContador() != null && contador.getConfigContador().getLongitudMaxima() != null
					        && contador.getConfigContador().getLongitudMaxima() < String.valueOf(contador.getValor() + 1).length()) {
						throw new ContadoresConfigInvalidException("El valor del contador supera la longitud mÃ¡xima.");
					}

					// Incrementamos en 1 el valor del contador
					Long valorActual = contador.getValor();
					contador.setValor(contador.getValor() + 1);

					// Esto elimina la diferencia entre distintas bases de datos a la hora de redondear los
					// milisegundos.
					Calendar calFechaActual = Calendar.getInstance();
					calFechaActual.setTime(fechaActual);
					calFechaActual.set(Calendar.MILLISECOND, 0);

					contador.setUltimaPeticion(calFechaActual.getTime());
					if (rango != null) {
						contador.setIdRangoUltimaPeticion(rango.getIdRango());
					}

					ContadorExample filtroUpdate = new ContadorExample();
					filtroUpdate.or().andUidActividadEqualTo(contador.getUidActividad()).andIdContadorEqualTo(contador.getIdContador()).andDivisor1EqualTo(contador.getDivisor1())
					        .andDivisor2EqualTo(contador.getDivisor2()).andDivisor3EqualTo(contador.getDivisor3()).andValorEqualTo(valorActual);

					// Si no se ha actualizado ningÃºn registro, el lock seguirÃ¡ a verdadero y se consultarÃ¡ de nuevo
					// el valor del contador.
					lock = contadoresMapper.updateByExample(contador, filtroUpdate) != 1;
					if (lock) {
						numIntentos--;
						if (numIntentos < 80) {
							log.warn(String.format(
							        "incrementarContador() - No se ha actualizado correctamente el contador [%s] (divisor1: %s, divisor2: %s, divisor3: %s) cuyo valor actual es [%s] con el nuevo valor [%s] ; se reintenta de nuevo (intentos restantes: %s)",
							        contador.getIdContador(), contador.getDivisor1(), contador.getDivisor2(), contador.getDivisor3(), valorActual, contador.getValor(), numIntentos));
						}
					}
				}
			}

			if (numIntentos == 0) {
				String mensaje = String.format("No se ha podido actualizar el contador [%s] (divisor1: %s, divisor2: %s, divisor3: %s); se ha superado el nÃºmero de intentos para obtener un valor ",
				        contador.getIdContador(), contador.getDivisor1(), contador.getDivisor2(), contador.getDivisor3());
				log.error("incrementarContador() - " + mensaje);
				throw new ContadorServiceException(mensaje);
			}
		}
		else {
			String mensaje = "Se estÃ¡ intentando acceder a un contador cuya fecha de Ãºltima peticiÃ³n es posterior a la fecha actual";
			log.error("incrementarContador() - " + mensaje);
			throw new ContadorServiceException(mensaje);
		}

		return contador;

	}

	private Map<String, ConfigContadorParametroBean> obtenerParametrosEfectivos(String idContador, Map<String, String> parametrosContador)
	        throws ConfigContadoresParametrosInvalidException, ConfigContadoresParametrosException {

		Map<String, ConfigContadorParametroBean> mapConfigParametrosEfectivos = new HashMap<>();

		Map<String, ConfigContadorParametroBean> mapConfigParametrosPermitidos = configContadoresParametroService.consultarMap(idContador);

		for (Entry<String, String> entry : parametrosContador.entrySet()) {
			if (mapConfigParametrosPermitidos.containsKey(entry.getKey())) {
				ConfigContadorParametroBean parametro = mapConfigParametrosPermitidos.get(entry.getKey());
				if (parametro.getLongitudMaxima() != null && parametro.getLongitudMaxima() < entry.getValue().length()) {
					log.error("Error en la configuraciÃ³n del parÃ¡metro " + entry.getKey() + " del contador " + idContador);
					throw new ConfigContadoresParametrosInvalidException();
				}
				parametro.setValorParametro(entry.getValue());
				mapConfigParametrosEfectivos.put(entry.getKey(), parametro);
			}
		}

		return mapConfigParametrosEfectivos;
	}

	private void obtenerValoresParaMascaras(ConfigContadorBean configContador, Map<String, ConfigContadorParametroBean> mapParametrosEfectivos) {
		Pattern pattern = Pattern.compile("#(.*?)#");
		Matcher matcher;

		// BÃºscamos el valor para la MascaraDivisor1
		if (!"*".equals(configContador.getMascaraDivisor1())) {
			matcher = pattern.matcher(configContador.getMascaraDivisor1());

			String[] valoresMascara1 = obtenerValorMascara(mapParametrosEfectivos, configContador, configContador.getMascaraDivisor1(), matcher);
			configContador.setValorDivisor1(valoresMascara1[0]);
			configContador.setValorDivisor1Formateado(valoresMascara1[1]);
		}

		// BÃºscamos el valor para la MascaraDivisor2

		if (!"*".equals(configContador.getMascaraDivisor2())) {
			matcher = pattern.matcher(configContador.getMascaraDivisor2());

			String[] valoresMascara2 = obtenerValorMascara(mapParametrosEfectivos, configContador, configContador.getMascaraDivisor2(), matcher);
			configContador.setValorDivisor2(valoresMascara2[0]);
			configContador.setValorDivisor2Formateado(valoresMascara2[1]);
		}

		// BÃºscamos el valor para la MascaraDivisor3
		if (!"*".equals(configContador.getMascaraDivisor3())) {
			matcher = pattern.matcher(configContador.getMascaraDivisor3());

			String[] valoresMascara3 = obtenerValorMascara(mapParametrosEfectivos, configContador, configContador.getMascaraDivisor3(), matcher);
			configContador.setValorDivisor3(valoresMascara3[0]);
			configContador.setValorDivisor3Formateado(valoresMascara3[1]);
		}
	}

	private boolean validarValoresDivisores(ConfigContadorBean configContador) {
		if (!"*".equals(configContador.getValorDivisor1())) {
			if (configContador.getValorDivisor1().contains("#")) {
				return false;
			}
		}

		if (!"*".equals(configContador.getValorDivisor2())) {
			if (configContador.getValorDivisor2().contains("#")) {
				return false;
			}
		}

		if (!"*".equals(configContador.getValorDivisor3())) {
			if (configContador.getValorDivisor3().contains("#")) {
				return false;
			}
		}

		return true;
	}

	private String[] obtenerValorMascara(Map<String, ConfigContadorParametroBean> mapParametrosEfectivos, ConfigContadorBean configContador, String mascara, Matcher matcher) {
		String mascaraFormateada = mascara;
		while (matcher.find()) {
			String param = matcher.group(1);
			if (mapParametrosEfectivos.containsKey(param)) {
				String valorParam = mapParametrosEfectivos.get(param).getValorParametro();
				mascara = mascara.replace("#" + param + "#", valorParam);

				String valorParamFormateado = mapParametrosEfectivos.get(param).formatearValorMascara(valorParam);
				mascaraFormateada = mascaraFormateada.replace("#" + param + "#", valorParamFormateado);
			}
		}

		return new String[] { mascara, mascaraFormateada };
	}

	public String obtenerValorTotalConSeparador(DatosSesionBean datosSesion, String divisor, String valorContadorFormateado) {
		String separador = "";
		try {
			separador = variablesService.consultarValor(datosSesion, VariablesServices.CONTADORES_CARACTER_SEPARADOR);
			if ("".equals(separador)) {
				separador = "/";
			}
		}
		catch (VariableException e) {
			log.error("obtenerValorTotalConSeparador() Error obteniendo la variable CONTADORES_CARACTER_SEPARADOR. " + e.getMessage() + e);
		}
		catch (VariableNotFoundException e) {
			log.error("obtenerValorTotalConSeparador() No se ha encontrado la variable CONTADORES_CARACTER_SEPARADOR. " + e.getMessage() + e);
		}
		return divisor + separador + valorContadorFormateado;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ContadorBean obtenerContador(String idContador, Map<String, String> parametrosContador, String uidActividad) throws ContadorServiceException, ContadoresConfigInvalidException, ConfigContadoresParametrosInvalidException, ConfigContadoresParametrosException {

		ContadorBean contador = null;
		ConfigContadorBean configContador = null;
		ContadorKey contadorPrimaryKey = new ContadorKey();
		contadorPrimaryKey.setUidActividad(uidActividad);
		contadorPrimaryKey.setIdContador(idContador);
		log.debug("obtenerContador() - Obteniendo el valor para el contador " + idContador);

		try {
			configContador = configContadoresService.consultar(idContador);

			if (configContador == null) {
				String mensaje = "No se ha encontrado la configuración para el contador " + idContador;
				log.error("obtenerContador() - " + mensaje);
				throw new ContadorNotFoundException(mensaje);
			}

			Map<String, ConfigContadorParametroBean> mapParametrosEfectivos = obtenerParametrosEfectivos(idContador, parametrosContador);
			obtenerValoresParaMascaras(configContador, mapParametrosEfectivos);

			if (!validarValoresDivisores(configContador)) {
				String mensaje = "No se han establecido todos los parámetros para la configuración del contador " + idContador;
				log.error("obtenerContador() - " + mensaje);
				throw new ContadorServiceException(mensaje);
			}

			// Obtenemos el contador
			contadorPrimaryKey.setDivisor1(configContador.getValorDivisor1());
			contadorPrimaryKey.setDivisor2(configContador.getValorDivisor2());
			contadorPrimaryKey.setDivisor3(configContador.getValorDivisor3());

			configContador.setParametros(new ArrayList(mapParametrosEfectivos.values()));
			contador = contadoresMapper.selectByPrimaryKey(contadorPrimaryKey);

			// Si el contador aÃºn no existe, se crea con valor inicial 0.
			if (contador == null) {
				contador = new ContadorBean();
				contador.setUidActividad(contadorPrimaryKey.getUidActividad());
				contador.setIdContador(contadorPrimaryKey.getIdContador());
				contador.setDivisor1(contadorPrimaryKey.getDivisor1());
				contador.setDivisor2(contadorPrimaryKey.getDivisor2());
				contador.setDivisor3(contadorPrimaryKey.getDivisor3());
				contador.setValor(Long.valueOf(0));

				contadoresMapper.insert(contador);
			}
			contador.setConfigContador(configContador);
			
			Date fechaActual = new Date();
			//Solo se actualizará el contador si es la primera consulta o si la fecha actual es igual o mayor que la ultima petición
			if(contador.getUltimaPeticion() == null || (fechaActual.equals(contador.getUltimaPeticion()) || fechaActual.after(contador.getUltimaPeticion()))) {
				//Incrementamos en 1 el valor del contador
				if(contador.getConfigContador() != null && contador.getConfigContador().getLongitudMaxima() != null && contador.getConfigContador().getLongitudMaxima() < String.valueOf(contador.getValor() + 1).length()){
					throw new ContadoresConfigInvalidException("El valor del contador supera la longitud mÃ¡xima.");
				}
				contador.setValor(contador.getValor() + 1);
				contador.setUltimaPeticion(new Date());
				contadoresMapper.updateByPrimaryKey(contador);
			}
			else{
				String mensaje = "Se estÃ¡ intentando acceder a un contador cuya fecha de Ãºltima peticiÃ³n es posterior a la fecha actual";
				log.error("obtenerContador() - "+mensaje);
				throw new ContadorServiceException(mensaje);
			}
		}
		catch (ContadoresConfigNotFoundException e) {
			throw new ContadorNotFoundException(e.getMessage());
		}
		catch (ConfigContadoresParametrosInvalidException e) {
			throw new ConfigContadoresParametrosInvalidException(e.getMessage());
		}
		catch (ConfigContadoresParametrosException e) {
			throw new ConfigContadoresParametrosException(e.getMessage());
		}
		return contador;
	}

}
