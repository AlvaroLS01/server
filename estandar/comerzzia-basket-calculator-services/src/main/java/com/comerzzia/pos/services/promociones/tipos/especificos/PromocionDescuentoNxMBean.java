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
import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;
import com.comerzzia.pos.services.promociones.filtro.FiltroLineasPromocion;
import com.comerzzia.pos.services.promociones.filtro.LineasAplicablesPromoBean;
import com.comerzzia.pos.services.promociones.tipos.PromocionLinea;
import com.comerzzia.pos.services.promociones.tipos.componente.GrupoComponentePromoBean;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaCandidataTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;

@Component
@Scope("prototype")
public class PromocionDescuentoNxMBean extends PromocionLinea {

    protected static final Logger log = Logger.getLogger(PromocionDescuentoNxMBean.class);

    protected GrupoComponentePromoBean filtroAplicacion;
    
    // Los tres valores son enteros, pero por comodidad de cálculo los declaramos como BigDecimal
    protected BigDecimal condicionN;
    protected BigDecimal condicionM;
    protected BigDecimal porcentajeDto;
    protected BigDecimal precioUnidadRegalada; //Precio correspondiente a la unidad M (en el caso de tratarse de una promoción 3x2 la tercera unidad tendría dicho precio)
    protected BigDecimal cantidadDto; // N - M

    @Override
    public void analizarLineasAplicables(DocumentoPromocionable<IPromocionTicket> documento) {
    	log.trace("analizarLineasAplicables() - " + this);
    	
        // Obtenemos las líneas de condición según el filtro configurado
        FiltroLineasPromocion filtroLineas = createFiltroLineasPromocion(documento);
        filtroLineas.setFiltrarPromoExclusivas(true);
        LineasAplicablesPromoBean lineasAplicables = filtroLineas.getLineasAplicablesGrupo(filtroAplicacion, true);
        if (lineasAplicables.isEmpty()) {
            log.trace(this + " analizarLineasAplicables() - La promoción no se puede aplicar por no existir las líneas que cumplan las condiciones en el documento .");
            return;
        }

        if (BigDecimalUtil.isMenor(lineasAplicables.getCantidadArticulos(), condicionN)){
            log.trace(this + " analizarLineasAplicables() - La promoción no se puede aplicar porque las líneas no suman la cantidad N configurada: " + lineasAplicables.getCantidadArticulos());
            return;
        }
        
        // Ordenamos las líneas aplicables por precio descendente
        lineasAplicables.ordenarLineasPrecioDesc();
        
        // Registramos promoción candidata en todas las líneas
        PromocionLineaCandidataTicket candidata = new PromocionLineaCandidataTicket(lineasAplicables, lineasAplicables, this);
        for (LineaDocumentoPromocionable linea : lineasAplicables.getLineasAplicables()) {
            linea.addPromocionAplicable(candidata);
        }
    }
    
    @Override
    public BigDecimal calcularPromocion(LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables) {
        // Calculamos el número de combos que podremos aplicar
        Integer numCombos = lineasAplicables.getCantidadArticulos().divide(condicionN, 1, RoundingMode.DOWN).intValue();
        
        BigDecimal importeTotalAhorro = BigDecimal.ZERO;
        for (int i = 1; i <= numCombos; i++) {
            // Instanciamos la promoción del documento (cada combo tendrá una)
            PromocionTicket promocionTicket = createPromocionTicket(customerCoupon);
            
            // Intentamos aplicar la promoción sobre las líneas aplicables
            BigDecimal importeTotalAhorroCombo = lineasAplicables.aplicaDescuentoCandidato(promocionTicket, porcentajeDto, precioUnidadRegalada, precioUnidadRegalada == null, false, condicionM, cantidadDto);
            if (importeTotalAhorroCombo == null){
                return importeTotalAhorro;
            }
            // Si conseguimos aplicar la promoción, añadimos el combo aplicado al total
            importeTotalAhorro = importeTotalAhorro.add(importeTotalAhorroCombo);
        }
        return importeTotalAhorro;
    }    

    
    @Override
    public void aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento, LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables) {
        // Calculamos el número de combos que podremos aplicar
        Integer numCombos = lineasAplicables.getCantidadArticulos().divide(condicionN, 1, RoundingMode.DOWN).intValue();
        
        for (int i = 1; i <= numCombos; i++) {
            // Instanciamos la promoción del documento (cada combo tendrá una)
            PromocionTicket promocionTicket = createPromocionTicket(customerCoupon);
            
            // Intentamos aplicar la promoción sobre las líneas aplicables
            BigDecimal importeTotalAhorro = lineasAplicables.aplicaDescuento(promocionTicket, porcentajeDto, precioUnidadRegalada, precioUnidadRegalada == null, false, condicionM, cantidadDto);
            if (importeTotalAhorro == null){
                return;
            }
            // Si conseguimos aplicar la promoción, añadimos el combo aplicado a las promociones del documento
            promocionTicket.setImporteTotalAhorro(importeTotalAhorro);
            documento.addPromocion(promocionTicket);
        }
    }

    
    
    @Override
    public void leerDatosPromocion(byte[] datosPromocion) {
        try {
            XMLDocument xmlPromocion = new XMLDocument(datosPromocion);
            filtroAplicacion = new GrupoComponentePromoBean(xmlPromocion.getNodo("condicionLineas"));
            setCondicionN(xmlPromocion.getNodo("condicionN").getValueAsBigDecimal()); 
            setCondicionM(xmlPromocion.getNodo("condicionM").getValueAsBigDecimal());
            
            if (BigDecimalUtil.isMenorOrIgual(getCondicionN(), getCondicionM())) {
            	throw new IllegalArgumentException("La condición N debe ser mayor que M. Actualmente, N:" + getCondicionN() + ", M:"+getCondicionM());
            }
            if (BigDecimalUtil.isMenorOrIgualACero(getCondicionN())) {
            	throw new IllegalArgumentException("La condición N debe ser distinta de cero y positiva.");
            }
            if (BigDecimalUtil.isMenorOrIgualACero(getCondicionM())) {
            	throw new IllegalArgumentException("La condición M debe ser distinta de cero y positiva.");
            }
            
            try{
            	setPorcentajeDto(xmlPromocion.getNodo("descuentoPorcentaje").getValueAsBigDecimal());
            }
            catch(NumberFormatException e){
            	log.error("No se ha podido obtener el porcentaje de descuento como BigDecimal.");
            }
            
            try{
            	setPrecioUnidadRegalada(xmlPromocion.getNodo("precioUnidadRegalada").getValueAsBigDecimal());
            }
            catch(Exception e){
            	log.error("No se ha podido obtener el precio de la unidad regalada como BigDecimal.");
            }
            
            
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
            
            cantidadDto = getCondicionN().subtract(getCondicionM());
        }
        catch (XMLDocumentException e) {
            log.error("Error al leer los datos de la promoción de tipo descuento  NxM: " + e.getMessage(), e);
        }
    }

    
    public BigDecimal getCondicionN() {
        return condicionN;
    }

    public void setCondicionN(BigDecimal condicionN) {
        this.condicionN = condicionN;
    }

    public BigDecimal getCondicionM() {
        return condicionM;
    }

    public void setCondicionM(BigDecimal condicionM) {
        this.condicionM = condicionM;
    }

    public BigDecimal getPorcentajeDto() {
        return porcentajeDto;
    }

    public void setPorcentajeDto(BigDecimal porcentajeDto) {
        this.porcentajeDto = porcentajeDto;
    }

	public BigDecimal getPrecioUnidadRegalada() {
		return precioUnidadRegalada;
	}

	public void setPrecioUnidadRegalada(BigDecimal precioUnidadRegalada) {
		this.precioUnidadRegalada = precioUnidadRegalada;
	}

}
