package com.comerzzia.api.v2.facturacionmagento.services.impuestos.tratamientos;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.core.servicios.sesion.DatosSesionBean;
import com.comerzzia.core.util.db.Database;
import com.comerzzia.pos.persistence.core.impuestos.tratamientos.POSTratamientoImpuestoMapper;
import com.comerzzia.pos.persistence.core.impuestos.tratamientos.TratamientoImpuestoBean;
import com.comerzzia.pos.persistence.core.impuestos.tratamientos.TratamientoImpuestoKey;


@Service
public class TratamientoImpuestoService {

	public static final Logger log = Logger.getLogger(TratamientoImpuestoService.class);
	
	@Autowired
	protected POSTratamientoImpuestoMapper tratamientoImpuestoMapper;
	
	public TratamientoImpuestoBean consultarTratamientoImpuesto(DatosSesionBean datosSesion, Long idTratamiento) {

		SqlSession sqlSession = Database.getSqlSession();
		try {
			TratamientoImpuestoKey key = new TratamientoImpuestoKey();
			key.setUidActividad(datosSesion.getUidActividad());
			key.setIdTratImpuestos(idTratamiento);

			return tratamientoImpuestoMapper.selectByPrimaryKey(key);
		}
		catch (Exception e) {
			String msg = "Se ha producido un error consultando el grupo de impuestos con vigencia actual. " + e.getMessage();
			log.error("consultarGrupoImpuestosActual() - " + msg, e);
			throw e;
		}
		finally {
			sqlSession.close();
		}
	}
}
