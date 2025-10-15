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
package com.comerzzia.pos.services.promociones.tipos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.pos.persistence.promociones.PromocionBean;
import com.comerzzia.pos.persistence.promociones.detalle.PromocionDetalleBean;
import com.comerzzia.pos.persistence.promociones.detalle.PromocionDetalleKey;
import com.comerzzia.pos.services.promociones.PromocionesService;
import com.comerzzia.pos.services.promociones.PromocionesServiceException;
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.DetallePromocion;

@Component
@Scope("prototype")
public abstract class PromocionLineaDetalles extends PromocionLinea {

	protected Map<PromocionDetalleKey, DetallePromocion> detalles;
	@Autowired
	protected PromocionesService promocionesService;
	
	public PromocionLineaDetalles() {
		detalles = new HashMap<>();
	}
	
    @Override
	public void init(PromocionBean promocionBean, String storeLanguageCode) throws XMLDocumentException {
    	super.init(promocionBean, storeLanguageCode);
    	try {
			List<PromocionDetalleBean> detallesPromocion = promocionesService.consultarDetallesPromocion(promocionBean.getUidActividad(), getIdPromocion());
			setDetalles(detallesPromocion);
		} catch (PromocionesServiceException e) {
			throw new RuntimeException(e);
		}
	}

	public DetallePromocion getDetalle(Map<PromocionDetalleKey, DetallePromocion> detalles, PromocionDetalleKey key) {
		// Buscamos la promoción con desgloses más restrictivos, siendo la promoción con desgloses "*" la menos restrictiva
		// Se itera por cada caso puesto que las promociones no vienen ordenadas por desgloses
		for (PromocionDetalleKey detalleKey : detalles.keySet()) {
			if (detalleKey.getCodArticulo().equals(key.getCodArticulo()) && key.getDesglose1().equals(detalleKey.getDesglose1()) && key.getDesglose2().equals(detalleKey.getDesglose2())) {
				return detalles.get(detalleKey);
			}
		}
		for (PromocionDetalleKey detalleKey : detalles.keySet()) {
			if (detalleKey.getCodArticulo().equals(key.getCodArticulo()) && "*".equals(detalleKey.getDesglose1()) && key.getDesglose2().equals(detalleKey.getDesglose2())) {
				return detalles.get(detalleKey);
			}
		}
		for (PromocionDetalleKey detalleKey : detalles.keySet()) {
			if (detalleKey.getCodArticulo().equals(key.getCodArticulo()) && key.getDesglose1().equals(detalleKey.getDesglose1()) && "*".equals(detalleKey.getDesglose2())) {
				return detalles.get(detalleKey);
			}
		}
		for (PromocionDetalleKey detalleKey : detalles.keySet()) {
			if (detalleKey.getCodArticulo().equals(key.getCodArticulo()) && "*".equals(detalleKey.getDesglose1()) && "*".equals(detalleKey.getDesglose2())) {
				return detalles.get(detalleKey);
			}
		}
		return null;
	}

	public void setDetalles(List<PromocionDetalleBean> detallesBean) throws XMLDocumentException {
        for (PromocionDetalleBean promocionDetalleBean : detallesBean) {
            // Instanciamos el detalle y lo añadimos a los detalles de la promoción
            DetallePromocion detalle = createDetallePromocion(promocionDetalleBean);
            PromocionDetalleKey key = new PromocionDetalleKey();
            key.setCodArticulo(detalle.getCodArticulo());
            key.setDesglose1(detalle.getDesglose1());
            key.setDesglose2(detalle.getDesglose2());
            detalles.put(key, detalle);
        }
    }
    
    public abstract DetallePromocion createDetallePromocion(PromocionDetalleBean detalleBean) throws XMLDocumentException;
    
	@Override
	public void leerDatosPromocion(byte[] datosPromocion) {
	}

	public Map<PromocionDetalleKey, DetallePromocion> getDetalles() {
		return detalles;
	}
}
