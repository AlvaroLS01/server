package com.comerzzia.api.v2.facturacionmagento.services.atcud;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.bricodepot.backoffice.persistence.general.tiendas.atcud.AtcudMagento;
import com.comerzzia.core.model.config.configContadores.ConfigContadorBean;
import com.comerzzia.core.model.contadores.ContadorBean;
import com.comerzzia.core.model.contadores.ContadorKey;
import com.comerzzia.core.persistencia.contadores.ContadorMapper;
import com.comerzzia.core.servicios.config.configContadores.ConfigContadoresService;
import com.comerzzia.core.servicios.config.configContadores.ContadoresConfigException;
import com.comerzzia.core.servicios.config.configContadores.ContadoresConfigNotFoundException;
import com.comerzzia.core.servicios.config.configContadores.parametros.ConfigContadoresParametrosException;
import com.comerzzia.core.servicios.config.configContadores.parametros.ConfigContadoresParametrosService;
import com.comerzzia.core.servicios.contadores.ContadorException;
import com.comerzzia.core.servicios.contadores.ContadorNotFoundException;

@Service
public class BricodepotServicioContadoresAtcud {
	
	protected static Logger log = Logger.getLogger(BricodepotServicioContadoresAtcud.class);
	
	@Autowired
	protected ConfigContadoresService configContadoresService;
	
	@Autowired
	protected ConfigContadoresParametrosService configContadoresParametrosService;

	public ContadorBean obtenerContador(SqlSession sqlSession, String uidActividad, String idContador,AtcudMagento atcudAlmacen)
	        throws ContadoresConfigException, ContadoresConfigNotFoundException, ConfigContadoresParametrosException, ContadorException {
		ContadorBean contador = null;
		ConfigContadorBean configContador = null;
		ContadorKey contadorPrimaryKey = new ContadorKey();
		contadorPrimaryKey.setUidActividad(uidActividad);
		contadorPrimaryKey.setIdContador(idContador);

		ContadorMapper contadorMapper = sqlSession.getMapper(ContadorMapper.class);

		log.debug("obtenerContador() - Obteniendo el valor para el contador " + idContador);

		configContador = configContadoresService.getCacheConfigContadores().get(idContador);

		if (configContador == null) {
			// Obtenemos la configuración del contador
			configContador = configContadoresService.consultar(sqlSession, idContador);
		}
		else {
			log.debug("obtenerContador() - La configuración del contador " + idContador + " se ha obtenido desde la caché");
		}

		if (configContador == null) {
			String mensaje = "No se ha encontrado la configuración para el contador " + idContador;
			log.error("obtenerContador() - " + mensaje);
			throw new ContadorNotFoundException(mensaje);
		}

		configContadoresService.getCacheConfigContadores().put(idContador, configContador);

		contadorPrimaryKey.setDivisor1(atcudAlmacen.getMascaraDivisor1());
		contadorPrimaryKey.setDivisor2(atcudAlmacen.getMascaraDivisor2());
		contadorPrimaryKey.setDivisor3(atcudAlmacen.getMascaraDivisor3());
		// Comprobamos que todas las máscaras de se han evaluado correctamente, es decir, no queda ningún carácter #
		// en los valores de los divisores
		if (!validarValoresDivisores(configContador)) {
			String mensaje = "No se han establecido todos los parámetros para la configuración del contador " + idContador;
			log.error("obtenerContador() - " + mensaje);
			throw new ContadorException(mensaje);
		}

		contador = contadorMapper.selectByPrimaryKey(contadorPrimaryKey);

		contador.setConfigContador(configContador);

		return contador;
	}

	private boolean validarValoresDivisores(ConfigContadorBean configContador) {
		log.debug("validarValoresDivisores() ");
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
}
