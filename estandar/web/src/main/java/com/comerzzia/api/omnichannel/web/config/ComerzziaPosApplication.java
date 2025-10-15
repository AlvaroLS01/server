package com.comerzzia.api.omnichannel.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import com.comerzzia.api.core.service.util.ComerzziaDatosSesion;
import com.comerzzia.api.omnichannel.web.cache.TerminalSessionCache;
import com.comerzzia.omnichannel.service.basket.BasketItemsManager;
import com.comerzzia.omnichannel.service.basket.BasketManager;
import com.comerzzia.omnichannel.service.basket.BasketPaymentsManager;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.core.variables.VariablesServices;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.log4j.Log4j;

@EnableCaching
@Configuration
@Log4j
public class ComerzziaPosApplication {
	@Autowired
	VariablesServices variablesServices;
	
	@Bean
	MeterRegistryCustomizer<MeterRegistry> configurer(
	    @Value("${spring.application.name}") String applicationName) {
	    return (registry) -> registry.config().commonTags("application", applicationName);
	}
	
	@Bean
	public TerminalSessionCache getSessionCache() {
		return new TerminalSessionCache();
	}

	@Bean({"getSesion", "sesion"})
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Sesion sesion(ComerzziaDatosSesion datosSesionRequest) throws Exception {
		// get session from cache
		return getSessionCache().getTerminalSession(datosSesionRequest.getDatosSesionBean());
	}
	
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public BasketManager basketManager(Sesion sesion, BasketItemsManager basketItemsManager, BasketPaymentsManager basketPaymentsManager) throws Exception {
		return new BasketManager(sesion, basketItemsManager, basketPaymentsManager);
	}
	
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public BasketItemsManager basketItemsManager() throws Exception {
		return new BasketItemsManager();
	}
	
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public BasketPaymentsManager basketPaymentsManager() throws Exception {
		return new BasketPaymentsManager();
	}	
	
//	@Bean
//	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
//	protected Dispositivos dispositivos(Sesion sesion) throws Exception {
//		// Cargamos dispositivos
//		Dispositivos dispositivos = new Dispositivos();
//
//		try {
//			// Leemos los dispositivos disponibles
//			dispositivos.leerDispositivosDisponibles();
//		} catch (com.comerzzia.pos.core.dispositivos.ConfigDispositivosLoadException e) {
//			log.error(e.getMessage(), e);
//		}
//		
//		if (sesion.getAplicacion().getTiendaCaja().getConfiguracion() == null) {
//			log.error(I18N.getTexto("Aún no se han configurado los dispositivos"));
//		} else {		
//			try {
//				// Cargamos la configuración de los dispositivos
//				dispositivos.cargarConfiguracionDispositivos(sesion.getAplicacion().getTiendaCaja().getConfiguracion());
//			} catch (Exception e) {
//				if (sesion.getAplicacion().getTiendaCaja().getConfiguracion() == null) {
//					log.error(I18N.getTexto("Aún no se han configurado los dispositivos"));
//				}
//			}
//		}
//		
//		dispositivos.getFidelizacion().setApikey(variablesServices.getVariableAsString(VariablesServices.WEBSERVICES_APIKEY));
//		
//		return dispositivos;
//	}
}
