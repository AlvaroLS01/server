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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.pos.persistence.promociones.detalle.PromocionDetalleBean;
import com.comerzzia.pos.services.promociones.Promocion;


public class DetallePromocionNxM extends DetallePromocion {
	
    protected static final String TIPO_DTO_PORCENTAJE = "Descuento";
    protected static final String TIPO_DTO_IMPORTE    = "Importe";

    protected final BigDecimal descuento;
    protected final Integer cantidadN;
    protected final Integer cantidadM;
    protected final List<LineaDetallePromocionNxM> lineasAgrupacion;
    protected final String tipoDto;

    public DetallePromocionNxM(Promocion promocion, PromocionDetalleBean detalle) throws XMLDocumentException {
        super(promocion, detalle);
        XMLDocument xml = new XMLDocument(detalle.getDatosPromocion());
        descuento = xml.getRoot().getNodo("Descuento").getValueAsBigDecimal();
        cantidadN = xml.getRoot().getNodo("N").getValueAsInteger();
        cantidadM = xml.getRoot().getNodo("M").getValueAsInteger();
        lineasAgrupacion = new ArrayList<>();
        lineasAgrupacion.add(new LineaDetallePromocionNxM(getCodArticulo(), getDesglose1(), getDesglose2(), getDescuento(), getCantidadN(), getCantidadM(), detalle.getFechaInicio(), detalle.getFechaFin()));
        
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

    public Integer getCantidadN() {
        return cantidadN;
    }

    public Integer getCantidadM() {
        return cantidadM;
    }

    public List<LineaDetallePromocionNxM> getLineasAgrupacion() {
        return lineasAgrupacion;
    }


    public LineaDetallePromocionNxM getLineaAgrupacion(Calendar cal, String codArt) {
    	for (LineaDetallePromocionNxM lineaDetallePromocionNxM : getLineasAgrupacion(cal)) {
			if (lineaDetallePromocionNxM.getCodArticulo().equals(codArt)) {
				return lineaDetallePromocionNxM;
			}
		}
    	return null;
    }

	public List<LineaDetallePromocionNxM> getLineasAgrupacion(Calendar cal) {
		List<LineaDetallePromocionNxM> list = new LinkedList<>();
		Calendar calInicio = Calendar.getInstance();
		Calendar calFin = Calendar.getInstance();
		for (LineaDetallePromocionNxM lineaDetallePromocionNxM : getLineasAgrupacion()) {
			calInicio.setTime(lineaDetallePromocionNxM.getFechaInicio());
			calFin.setTime(lineaDetallePromocionNxM.getFechaFin());
			if ((cal.compareTo(calInicio) >= 0) && (cal.compareTo(calFin) <= 0)) {
				list.add(lineaDetallePromocionNxM);
			}
		}
		return list;
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
