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
package com.comerzzia.pos.services.promociones.tipos.especificos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.filtro.FiltroLineasPromocion;
import com.comerzzia.pos.services.promociones.filtro.LineasAplicablesPromoBean;
import com.comerzzia.pos.services.promociones.tipos.PromocionCabecera;
import com.comerzzia.pos.services.promociones.tipos.componente.CondicionPrincipalPromoBean;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionTicket;

@Component
@Scope("prototype")
public class PromocionPuntosBean extends PromocionCabecera {
    
    protected static final Logger log = Logger.getLogger(PromocionPuntosBean.class);  
    protected CondicionPrincipalPromoBean condiciones;

    protected BigDecimal puntosEuros;

	public BigDecimal getPuntosEuros() {
        return puntosEuros;
    }

    public void setPuntosEuros(BigDecimal puntosEuros) {
        this.puntosEuros = puntosEuros;
    }

    @Override
    public boolean aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento) {
    	log.trace("aplicarPromocion() - " + this);
    	if (puntosEuros == null || puntosEuros.compareTo(BigDecimal.ZERO) == 0){
            log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar porque los puntos por euros configurados no lo permiten: " + puntosEuros);
    		return false;
    	}
        // Obtenemos las líneas aplicables según el filtro configurado
        FiltroLineasPromocion filtroLineas = createFiltroLineasPromocion(documento);
        filtroLineas.setFiltrarPromoExclusivas(false); // Da igual que las líneas tengan una promoción exclusiva
        LineasAplicablesPromoBean lineasAplicables = filtroLineas.getNumCombosCondicion(condiciones);
        if (lineasAplicables.isEmpty()) {
            log.debug(this + " aplicarPromocion() - La promoción no se puede aplicar por no existir líneas aplicables en el documento .");
            return false;
        }

        // Obtenemos el importe de descuento 
        BigDecimal importeAplicable = lineasAplicables.getImporteLineasConDto();
    	if (importeAplicable.compareTo(BigDecimal.ZERO) == 0){
            log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar porque el importe aplicable es cero. ");
    		return false;
    	}
    	
    	Integer puntos = importeAplicable.divide(puntosEuros, 1, RoundingMode.DOWN).intValue();
    	if (puntos == 0){
            log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar porque los puntos obtenidos son cero. ");
    		return false;
    	}

        // Aplicamos la promoción sobre el documento
        PromocionTicket promocionTicket = createPromocionTicket(customerCoupon);
        promocionTicket.setPuntos(puntos);
        documento.addPromocion(promocionTicket);
        
        lineasAplicables.setLineasAplicables(documento.getLineasDocumentoPromocionable());   
        lineasAplicables.aplicaPromocionPuntos(promocionTicket, puntosEuros);
        documento.addPuntos(puntos);
        

        return true;
    }

    @Override
    public void leerDatosPromocion(byte[] datosPromocion) {
        try {
            XMLDocument xmlPromocion = new XMLDocument(datosPromocion);            
            condiciones = new CondicionPrincipalPromoBean(xmlPromocion.getNodo("condicionLineas"));
            
            setPuntosEuros(xmlPromocion.getNodo("puntosEuros").getValueAsBigDecimal());
            
            String storeLanguageCode = getStoreLanguageCode();
            
            String textPromo = null;
            String textPromoDefault = null;
        	List<XMLDocumentNode> textPromoNodes = xmlPromocion.getNodos("textoPromocion");
        	for(XMLDocumentNode textPromoNode : textPromoNodes) {
        		String textPromoLanguageCode = textPromoNode.getAtributoValue("lang", true);
        		if(StringUtils.isNotBlank(textPromoLanguageCode)){
        			if(textPromoLanguageCode.equals(storeLanguageCode)) {
        				textPromo = textPromoNode.getValue();
        				break;
        			}
        		}else {
        			textPromoDefault = textPromoNode.getValue();
        		}
        	}

        	if(StringUtils.isBlank(textPromo)) {
        		textPromo = textPromoDefault;
        	}

            setTextoPromocion(textPromo);
        }
        catch (XMLDocumentException e) {
            log.error("Error al leer los datos de la promoción de tipo puntos: "+e.getMessage(), e);
        }  
    }

	@Override
	public boolean isAplicacionFinal() {
		return true;
	}
	
	@Override
	public boolean isAplicacionCabecera() {
		return false;
	}
    
}
