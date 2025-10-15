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

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.pos.persistence.promociones.detalle.PromocionDetalleBean;
import com.comerzzia.pos.services.promociones.Promocion;


public class DetallePromocionDescuento extends DetallePromocion{

	protected static final String TIPO_DTO_PORCENTAJE = "Porcentaje";
    protected static final String TIPO_DTO_IMPORTE    = "Importe";
	
    protected final BigDecimal descuento;
    protected final String tipoDto;
    
    public DetallePromocionDescuento(Promocion promocion, PromocionDetalleBean detalle) throws XMLDocumentException {
        super(promocion, detalle);
        XMLDocument xml = new XMLDocument(detalle.getDatosPromocion());
        descuento = xml.getRoot().getNodo("Descuento").getValueAsBigDecimal();
        
        XMLDocumentNode tipoFiltro = xml.getNodo("tipoFiltro",true);
        if(tipoFiltro != null){
        	tipoDto = tipoFiltro.getValue();        	
        }else{
        	tipoDto = TIPO_DTO_PORCENTAJE;
        }
        String storeLanguageCode = promocion.getStoreLanguageCode();
        for(XMLDocumentNode textPromoNode : xml.getNodos("textoPromocion")) {
        	String languageCode = textPromoNode.getAtributoValue("lang", true);
        	if(StringUtils.isNotBlank(languageCode) && languageCode.equals(storeLanguageCode)) {
        		this.detalle.setTextoPromocion(textPromoNode.getValue());
        		break;
        	}
        }
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public String getTipoDto() {
    	return tipoDto;
    }
    
    public boolean isTipoDtoPorcentaje(){
        return tipoDto.equals(TIPO_DTO_PORCENTAJE);
    }
    public boolean isTipoDtoImporte(){
        return tipoDto.equals(TIPO_DTO_IMPORTE);
    }
    
}
