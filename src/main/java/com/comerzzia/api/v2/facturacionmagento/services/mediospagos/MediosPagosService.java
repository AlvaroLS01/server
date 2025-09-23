package com.comerzzia.api.v2.facturacionmagento.services.mediospagos;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.mediospago.MediosPagoMapper;
import com.comerzzia.core.servicios.sesion.DatosSesionBean;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;

@Component
public class MediosPagosService {

	protected static final Logger log = Logger.getLogger(MediosPagosService.class);

	protected static MediosPagosService instance;

	public static MediosPagosService get() {
		if (instance == null) {
			instance = new MediosPagosService();
		}
		return instance;
	}

	@SuppressWarnings("deprecation")
	public MedioPagoBean getMedioPago(DatosSesionBean datosSesion, String codMedioPago) throws Exception {
		log.debug("getMedioPago() - Consultando medio pago con codigo = " + codMedioPago);

		MedioPagoBean medioPago = new MedioPagoBean();
		SqlSession sqlSession =  datosSesion.getSqlSessionFactory().openSession();

		MediosPagoMapper mapper = sqlSession.getMapper(MediosPagoMapper.class);
		medioPago = mapper.selectByCod(datosSesion.getUidActividad(), codMedioPago);
		if (medioPago == null) {
			String msg = "Error consultando medio de pago con codigo = " + codMedioPago;
			log.error("getMedioPago() - " + msg);
			throw new Exception(msg);
		}
		sqlSession.close();

		return medioPago;
	}

}
