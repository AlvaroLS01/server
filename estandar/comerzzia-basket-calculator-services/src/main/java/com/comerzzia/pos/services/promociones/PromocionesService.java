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


package com.comerzzia.pos.services.promociones;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.persistence.promociones.PromocionBean;
import com.comerzzia.pos.persistence.promociones.PromocionKey;
import com.comerzzia.pos.persistence.promociones.PromocionMapper;
import com.comerzzia.pos.persistence.promociones.detalle.PromocionDetalleBean;
import com.comerzzia.pos.persistence.promociones.detalle.PromocionDetalleMapper;
import com.comerzzia.pos.services.core.sesion.promotions.PromotionsSessionKey;

@Service
public class PromocionesService {
    protected static final Logger log = Logger.getLogger(PromocionesService.class);
    
    @Autowired
    protected PromocionMapper promocionMapper;
    
    @Autowired
    protected PromocionDetalleMapper promocionDetalleMapper;

    public List<PromocionBean> consultarPromocionesActivas(PromotionsSessionKey promotionsSessionKey) throws PromocionesServiceException{
        try{
            log.debug("consultarPromocionesActivas() - Consultando promociones activas...");

            PromocionBean example = new PromocionBean();
            example.setCodAlmacen(promotionsSessionKey.getStoreId());
            example.setUidActividad(promotionsSessionKey.getUidActividad());
            example.setFechaInicio(promotionsSessionKey.getVigence());
            
            return promocionMapper.selectByCodAlmacen(example);
        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando promociones activas: " + e.getMessage();
            log.error("consultarPromocionesActivas() - " + msg, e);
            throw new PromocionesServiceException(e);
        }
    }

    public List<PromocionDetalleBean> consultarDetallesPromocion(String uidActividad, Long idPromocion) throws PromocionesServiceException{
        try{
            log.debug("consultarDetallesPromocion() - Consultando detalles promoción: " + idPromocion);
                        
            return promocionDetalleMapper.selectByPromotionId(uidActividad, idPromocion);
        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando detalles de la promoción con id: " + idPromocion + " - " + e.getMessage();
            log.error("consultarDetallesPromocion() - " + msg, e);
            throw new PromocionesServiceException(e);
        }
    }
    
    public Integer checkPromotionsChange(PromotionsSessionKey promotionsSessionKey) throws PromocionesServiceException{
        try{
            log.debug("checkPromotionsChange() - Consultando cambio en promociones activas...");

            PromocionBean example = new PromocionBean();
            example.setCodAlmacen(promotionsSessionKey.getStoreId());
            example.setUidActividad(promotionsSessionKey.getUidActividad());
            example.setFechaInicio(promotionsSessionKey.getVigence());
            
            return promocionMapper.checkPromotionsChange(example);
        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando cambios en promociones activas: " + e.getMessage();
            log.error("checkPromotionsChange() - " + msg, e);
            throw new PromocionesServiceException(e);
        }
    }

    public PromocionBean getPromotion(String uidActividad, Long promotionId) throws PromocionesServiceException {
        log.debug("getPromotion() - Consultando promoción: " + promotionId);
        
        PromocionKey key = new PromocionKey();
        key.setUidActividad(uidActividad);
        key.setIdPromocion(promotionId);
        return promocionMapper.selectByPrimaryKey(key);
    }
}
