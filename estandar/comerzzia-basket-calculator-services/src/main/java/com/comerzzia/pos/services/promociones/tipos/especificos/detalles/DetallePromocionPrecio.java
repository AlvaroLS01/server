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


package com.comerzzia.pos.services.promociones.tipos.especificos.detalles;

import org.apache.commons.lang.StringUtils;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.pos.persistence.promociones.detalle.PromocionDetalleBean;
import com.comerzzia.pos.services.promociones.Promocion;


public class DetallePromocionPrecio extends DetallePromocion{

    public Integer puntos;
    
    public DetallePromocionPrecio(Promocion promocion, PromocionDetalleBean detalle) throws XMLDocumentException {
        super(promocion, detalle);
        puntos = detalle.getPuntos();
        XMLDocument xml = new XMLDocument(detalle.getDatosPromocion());
        String storeLanguageCode = promocion.getStoreLanguageCode();
        for(XMLDocumentNode textPromoNode : xml.getNodos("textoPromocion")) {
        	String languageCode = textPromoNode.getAtributoValue("lang", true);
        	if(StringUtils.isNotBlank(languageCode) && languageCode.equals(storeLanguageCode)) {
        		this.detalle.setTextoPromocion(textPromoNode.getValue());
        		break;
        	}
        }
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
    
    
}
