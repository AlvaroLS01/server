package com.comerzzia.pos.services.core.sesion.promotions;

import java.util.Calendar;
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.core.sesion.SesionPromociones;
import com.comerzzia.pos.services.promociones.PromocionesServiceException;

@Component
public class PromotionsSessionCache {
   private static Logger log = Logger.getLogger(PromotionsSessionCache.class);
   
   @Autowired
   private ApplicationContext context;
   
   @Autowired
   CacheManager cacheManager;

    //, key = "new org.springframework.cache.interceptor.SimpleKey(#datosSesion.user, #terminalId)")
   @Cacheable(cacheNames = "promotionsSessions")
   public SesionPromociones get(PromotionsSessionKey promotionsSessionKey) throws Exception {
      log.info("get() - Creating new promotions session for key '" + promotionsSessionKey + "'...");
      
      SesionPromociones promotionsSession = context.getBean(SesionPromociones.class, promotionsSessionKey);
      
      promotionsSession.actualizarPromocionesActivas();
      
      return promotionsSession;
   }
   
   public void delete(PromotionsSessionKey promotionsSessionKey) {
      log.info("updatePromotionsSessions() - delete promotions session cache for key " + promotionsSessionKey);
	   
	  cacheManager.getCache("promotionsSessions").evict(promotionsSessionKey);
   }

   @Scheduled(cron = "0 0 0 1/1 * ?")   
   @SuppressWarnings("unchecked")
   public void clearExpiredSessions() {
	   log.info("clearExpiredSessions() - Cleaning expired promotions session cache");
	   
	   ConcurrentHashMap<PromotionsSessionKey, SesionPromociones> nativeCache = (ConcurrentHashMap<PromotionsSessionKey, SesionPromociones>) cacheManager.getCache("promotionsSessions").getNativeCache();
	   
	   for (Entry<PromotionsSessionKey, SesionPromociones> entry : nativeCache.entrySet()) {
		   PromotionsSessionKey promotionsSessionKey = entry.getKey();
		   
		   if (DateUtils.truncatedCompareTo(new Date(), promotionsSessionKey.getVigence(), Calendar.DAY_OF_MONTH) < 0) {
			   delete(promotionsSessionKey);
		   }
	   }
   }
   
   @Scheduled(fixedRate = 60000)
   @SuppressWarnings("unchecked")
   public void updatePromotionsSessions() {	   
	   ConcurrentHashMap<PromotionsSessionKey, SesionPromociones> nativeCache = (ConcurrentHashMap<PromotionsSessionKey, SesionPromociones>) cacheManager.getCache("promotionsSessions").getNativeCache();
	   
	   for (Entry<PromotionsSessionKey, SesionPromociones> entry : nativeCache.entrySet()) {
		   log.debug("updatePromotionsSessions() - Update promotions session cache for key " + entry.getKey());
		   
		   try {
				entry.getValue().actualizarPromocionesActivas();
			} catch (PromocionesServiceException e) {
				log.error("updatePromotionsSessions() - Error updating active promotions: " + e.getMessage(), e);
				delete(entry.getKey());
			}
	   }
   }
}
