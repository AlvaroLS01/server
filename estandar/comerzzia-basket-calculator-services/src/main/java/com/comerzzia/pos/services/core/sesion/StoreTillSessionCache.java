package com.comerzzia.pos.services.core.sesion;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.core.sesion.paymentMethods.PaymentMethodsData;
import com.comerzzia.pos.util.config.SpringContext;

@Component
public class StoreTillSessionCache {
   private static Logger log = Logger.getLogger(StoreTillSessionCache.class);

   @Cacheable(cacheNames = "paymentMethodsSessions") 
   public PaymentMethodsData getPaymentMethods(StoreTillSessionKey storeTillSessionKey) throws Exception {
      log.info("getPaymentMethods() - Creating new payments methods session for key '" + storeTillSessionKey + "'...");

      return new PaymentMethodsData();
   }
   
   @Cacheable(cacheNames = "taxesSessions", key = "new org.springframework.cache.interceptor.SimpleKey(#storeTillSessionKey.uidActividad)") 
   public SesionImpuestos getTaxes(StoreTillSessionKey storeTillSessionKey) throws Exception {
      log.info("getTaxes() - Creating new taxes session for key '" + storeTillSessionKey + "'...");

      SesionImpuestos sesion = SpringContext.getBean(SesionImpuestos.class);
      sesion.init(storeTillSessionKey.getUidActividad());
      return sesion;
   }
   
   @Cacheable(cacheNames = "storeTillApplicationSessions") 
   public SesionAplicacion getStoreTillApplicationSession(StoreTillSessionKey storeTillSessionKey) throws Exception {
      log.info("getStoreTillApplicationSession() - Creating Store-Till session for key '" + storeTillSessionKey + "'...");

      SesionAplicacion sesion = SpringContext.getBean(SesionAplicacion.class);
      sesion.init(storeTillSessionKey);
      
      return sesion;
   }
   
   @Cacheable(cacheNames = "storeTillSessions") 
   public SesionCaja getStoreTillSession(StoreTillSessionKey storeTillSessionKey) throws Exception {
      log.info("getStoreTillSession() - Creating Store-Till session for key '" + storeTillSessionKey + "'...");

      SesionCaja sesion = SpringContext.getBean(SesionCaja.class);
      sesion.init(storeTillSessionKey);
      
      return sesion;
   }

}
