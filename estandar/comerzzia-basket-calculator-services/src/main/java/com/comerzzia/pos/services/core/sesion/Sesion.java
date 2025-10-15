package com.comerzzia.pos.services.core.sesion;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.comerzzia.core.basketcalculator.servicios.sesion.BasicSessionBean;
import com.comerzzia.core.basketcalculator.servicios.sesion.IDatosSesion;
import com.comerzzia.pos.services.core.sesion.promotions.PromotionsSessionCache;
import com.comerzzia.pos.services.core.sesion.promotions.PromotionsSessionKey;
import com.comerzzia.pos.util.config.SpringContext;
import com.comerzzia.pos.util.format.FormatUtil;

public class Sesion extends BasicSessionBean {
	protected static final Logger log = Logger.getLogger(Sesion.class.getName());
	
	@Autowired
	PromotionsSessionCache promotionsSessionCache;
	
	@Autowired
	StoreTillSessionCache storeTillSessionCache;

	protected SesionImpuestos sesionImpuestos;
	protected SesionCaja sesionCaja;
	protected SesionAplicacion sesionAplicacion;
	protected SesionUsuario sesionUsuario;

	protected SesionPromociones promotionsSession;

	protected boolean initialized = false;

	public Sesion(IDatosSesion datosSesion) throws Exception {
		this.setUidActividad(datosSesion.getUidActividad());
		this.setUidInstancia(datosSesion.getUidInstancia());
		this.setLocale(datosSesion.getLocale());
		this.setUser(datosSesion.getUser());
		this.setUserId(datosSesion.getUserId());
	}

	public Sesion(String uidActividad) throws Exception {
		this.setUidActividad(uidActividad);
	}

	public SesionImpuestos getImpuestos() {
		if (sesionImpuestos == null) {
			throw new RuntimeException("No se ha creado sesion de tienda/caja");
		}
		return sesionImpuestos;
	}

	public SesionAplicacion getAplicacion() {
		if (sesionAplicacion == null) {
			throw new RuntimeException("No se ha creado sesion de tienda/caja");
		}
		
		return sesionAplicacion;
	}

	public SesionCaja getSesionCaja() {
		if (sesionCaja == null) {
			throw new RuntimeException("No se ha creado sesion de tienda/caja");
		}
		
		return sesionCaja;
	}

	public SesionUsuario getSesionUsuario() {
		return sesionUsuario;
	}

	public SesionPromociones getSesionPromociones() {
		try {
			promotionsSession = promotionsSessionCache.get(new PromotionsSessionKey(getUidInstancia(),
					getUidActividad(), getAplicacion().getCodAlmacen(),
					DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH), getAplicacion().getStoreLanguageCode()));
		} catch (Exception e) {
			throw new RuntimeException("Error creando sesion de promociones", e);
		}
		return promotionsSession;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@PostConstruct
	public void initSesion() throws Exception {
		if (!initialized) {
			log.info("initSesion() - Initializing application session...");

			//sesionImpuestos = SpringContext.getBean(SesionImpuestos.class);

			// sesionCaja = SpringContext.getBean(SesionCaja.class);

			//sesionAplicacion = SpringContext.getBean(SesionAplicacion.class);

			sesionUsuario = SpringContext.getBean(SesionUsuario.class);

//			if (getUidInstancia() == null) {
//				// Consultamos uidActividad para obtener la instancia
//				ActividadBean actividad = actividadesService.consultarActividad(this.getUidActividad());
//				this.setUidInstancia(actividad.getUidInstancia());
//			}

			//sesionImpuestos.init(this.getUidActividad());
			//sesionAplicacion.init(this, sesionImpuestos);
			//sesionCaja.init(this, sesionAplicacion);
			sesionUsuario.init(this);

			// TODO: Cambiar esta basura
			FormatUtil formatUtil = FormatUtil.getInstance();
			formatUtil.init(getLocale());

			log.info("initSesion() - Application session initialized successfully.");
			initialized = true;
		}
	}
	
	public void setTillSession(String storeId, String tillId) {
		StoreTillSessionKey key = new StoreTillSessionKey();
		key.setUidInstancia(getUidInstancia());
		key.setUidActividad(getUidActividad());
		key.setStoreId(storeId);
		key.setTillId(tillId);
		
		try {
			sesionImpuestos = storeTillSessionCache.getTaxes(key);
			sesionAplicacion = storeTillSessionCache.getStoreTillApplicationSession(key);
			sesionCaja = storeTillSessionCache.getStoreTillSession(key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
