package com.comerzzia.pos.services.core.config.configContadores.rangos;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comerzzia.core.basketcalculator.util.base.Estado;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoExample;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoKey;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.POSConfigContadorRangoMapper;
import com.comerzzia.pos.services.core.config.configContadores.ContadoresConfigException;

@Service
public class ServicioConfigContadoresRangos {

	protected static Logger log = Logger.getLogger(ServicioConfigContadoresRangos.class);

	@Autowired
	POSConfigContadorRangoMapper configContadorRangoMapper;

	public List<ConfigContadorRangoBean> consultar(String idContador) throws ContadoresConfigException {
		log.debug("consultar() - Consultando rangos del contador " + idContador);
		try {
			ConfigContadorRangoExample configContadorRangoExample = new ConfigContadorRangoExample();
			configContadorRangoExample.or().andIdContadorEqualTo(idContador);

			return configContadorRangoMapper.selectByExample(configContadorRangoExample);
		}
		catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar los rangos de un contador: " + e.getMessage();
			throw new ContadoresConfigException(mensaje, e);
		}
	}
	
	public List<ConfigContadorRangoBean> consultar(ConfigContadorRangoExample configContadorRangoExample)
			throws ContadoresConfigException {
		log.debug("consultar() - Consultando rangos ");
		try {
			return configContadorRangoMapper
					.selectByExample(configContadorRangoExample);
		} catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar los rangos de un contador: "
					+ e.getMessage();
			throw new ContadoresConfigException(mensaje, e);
		}
	}

	public List<ConfigContadorRangoBean> consultar(String idContador, String codEmp, String codAlm, String codCaj) throws ContadoresConfigException {
		log.debug("consultar() - Consultando rangos del contador " + idContador);
		try {
			ConfigContadorRangoExample filtro = new ConfigContadorRangoExample();

			ConfigContadorRangoExample.Criteria cirteria1 = filtro.or();
			ConfigContadorRangoExample.Criteria cirteria2 = filtro.or();
			ConfigContadorRangoExample.Criteria cirteria3 = filtro.or();

			cirteria1.andIdContadorEqualTo(idContador).andCodempEqualTo(codEmp).andCodalmEqualTo(codAlm).andCodcajaEqualTo(codCaj);
			cirteria2.andIdContadorEqualTo(idContador).andCodempEqualTo(codEmp).andCodalmEqualTo(codAlm).andCodcajaIsNull();
			cirteria3.andIdContadorEqualTo(idContador).andCodempEqualTo(codEmp).andCodalmIsNull();

			return configContadorRangoMapper.selectByExample(filtro);
		}
		catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar los rangos de un contador: " + e.getMessage();
			throw new ContadoresConfigException(mensaje, e);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void salvar(ConfigContadorRangoBean rangoContador) throws ConfigContadoresRangosConstraintViolationException, ConfigContadoresRangosException {
		switch (rangoContador.getEstadoBean()) {
			case Estado.NUEVO:
				crear(rangoContador);
				break;

			case Estado.MODIFICADO:
				modificar(rangoContador);
				break;

			case Estado.BORRADO:
				eliminar(rangoContador);
		}

	}

	protected void eliminar(ConfigContadorRangoBean rangoContador) throws ConfigContadoresRangosConstraintViolationException, ConfigContadoresRangosException {
		log.debug("eliminar() - Eliminando rango " + rangoContador.getIdRango() + " de configuración de contador " + rangoContador.getIdContador());

		try {
			configContadorRangoMapper.deleteByPrimaryKey(rangoContador);
		}
		catch (Exception e) {
			String msg = "Error eliminando rango de configuración de contador: " + e.getMessage();
			log.error("eliminar() - " + msg);

			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ConfigContadoresRangosConstraintViolationException("Error eliminando rango de configuración de contador: Se han violado restricciones de integridad entre registros");
			}
			else {
				throw new ConfigContadoresRangosException(msg, e);
			}
		}

	}

	protected void crear(ConfigContadorRangoBean rangoContador) throws ConfigContadoresRangosConstraintViolationException, ConfigContadoresRangosException {
		log.debug("crear() - Creando nuevo rango de configuración de contador");

		try {
			configContadorRangoMapper.insert(rangoContador);
		}
		catch (Exception e) {
			String msg = "Error creando nuevo rango de configuración de contador: " + e.getMessage();
			log.error("crear() - " + msg);
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				if (e.getCause().toString().contains("ORA-00001")) {
					throw new ConfigContadoresRangosConstraintViolationException("Error creando rango de configuración de contador: Ya existe un registro con el mismo código en el sistema");
				}
				else {
					throw new ConfigContadoresRangosException(msg, e);
				}
			}
			else {
				throw new ConfigContadoresRangosException(msg, e);
			}
		}

	}

	protected void modificar(ConfigContadorRangoBean rangoContador) throws ConfigContadoresRangosException {
		log.debug("modificar() - Modificando rango " + rangoContador.getIdRango() + " de configuración de contador " + rangoContador.getIdContador());

		try {
			configContadorRangoMapper.updateByPrimaryKey(rangoContador);
		}
		catch (Exception e) {
			String msg = "Error modificando rango de configuración de contador: " + e.getMessage();
			log.error("modificar() - " + msg);

			throw new ConfigContadoresRangosException(msg, e);
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminar(String idContador) throws ConfigContadoresRangosConstraintViolationException, ConfigContadoresRangosException {
		try {
			log.debug("eliminar() - Eliminando todos los rangos de configuración de contador " + idContador);

			ConfigContadorRangoExample filtro = new ConfigContadorRangoExample();
			filtro.or().andIdContadorEqualTo(idContador);

			configContadorRangoMapper.deleteByExample(filtro);
		}
		catch (Exception e) {
			String msg = "Error eliminando todos los rangos de configuración de contador: " + e.getMessage();
			log.error("eliminar() - " + msg);

			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ConfigContadoresRangosConstraintViolationException("Error eliminando rangos de configuración de contador: Se han violado restricciones de integridad entre registros");
			}
			else {
				throw new ConfigContadoresRangosException(msg, e);
			}
		}

	}

	public ConfigContadorRangoBean consultar(String idContador, String idRangoUltimaPeticion) {
		log.debug("consultar() - Consultando rangos del contador " + idContador);
		ConfigContadorRangoKey rangoKey = new ConfigContadorRangoKey();
		rangoKey.setIdContador(idContador);
		rangoKey.setIdRango(idRangoUltimaPeticion);

		return configContadorRangoMapper.selectByPrimaryKey(rangoKey);

	}

}
