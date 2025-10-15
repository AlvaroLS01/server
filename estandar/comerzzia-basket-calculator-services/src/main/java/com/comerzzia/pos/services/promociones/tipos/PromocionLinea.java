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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.articulos.tarifas.TarifaDetalleBean;
import com.comerzzia.pos.services.articulos.tarifas.ArticulosTarifaService;
import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;
import com.comerzzia.pos.services.promociones.Promocion;
import com.comerzzia.pos.services.promociones.filtro.LineasAplicablesPromoBean;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;

@Component
@Scope("prototype")
public abstract class PromocionLinea extends Promocion {
	
	@Autowired
	private ArticulosTarifaService articulosTarifaService;

	/** Realiza un análisis de aplicación sobre todas las líneas del documento. A cada línea, le asocia sus promociones
     * candidatas juntos con las líneas de condición y las líneas aplicables calculadas.
     * @param documento
     */
    public abstract void analizarLineasAplicables(DocumentoPromocionable<IPromocionTicket> documento); 

    /** Calcula el importe de ahorro de aplicar la promoción teniendo en cuenta las líneas de condición y las líneas 
     * aplicables proporcionadas.
     * @param lineasCondicion
     * @param lineasAplicables
     * @return
     */
    public abstract BigDecimal calcularPromocion(LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables);
    
    /** Aplica la promoción en el documento teniendo en cuenta las líneas de condición y las líneas
     * aplicables indicadas.
     * @param documento
     * @param lineasCondicion
     * @param lineasAplicables
     */
    public abstract void aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento, LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables);
    
    public boolean aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento){
    	return false;
    }

	@Override
	public boolean isAplicacionLinea() {
		return true;
	}
	
	public void cambiarTarifa(LineaDocumentoPromocionable linea, String codtar) {
		try {
			if(linea.containsTarifaDisponible(codtar)) {
				linea.cambiarTarifaCalculos(codtar);
			}
			else {
				TarifaDetalleBean tarifa = articulosTarifaService.consultarArticuloTarifa(linea.getCodArticulo(), codtar, linea.getDesglose1(), linea.getDesglose2(), new Date());
				if(tarifa != null) {
					linea.addTarifaDisponible(codtar, tarifa);
					linea.cambiarTarifaCalculos(codtar);
				}
				else {
					log.warn("cambiarTarifa() - No existe la tarifa " + codtar + " para el artículo " + linea.getCodArticulo() + ". Se usará la tarifa del ticket.");
					linea.cambiarTarifaOriginal();
				}
			}
	        
        }
        catch (Exception e) {
        	log.error("cambiarTarifa() - Ha habido un error al cambiar la tarifa: " + e.getMessage(), e);
        }
	}
    
	public Map<Integer, String> getTarifasOriginalesLinea(LineasAplicablesPromoBean lineasAplicables) {
		Map<Integer, String> tarifasOriginales = new HashMap<Integer, String>();
		for(LineaDocumentoPromocionable linea : lineasAplicables.getLineasAplicables()) {
			tarifasOriginales.put(linea.getIdLinea(), linea.getTarifa().getCodTarifa());
    	}
	    return tarifasOriginales;
	}

	public void cambiarTarifaLineasAplicables(LineasAplicablesPromoBean lineasAplicables, String codtarNuevo) {
	    if(StringUtils.isNotBlank(promocionBean.getCodtarPrecios())) {
        	for(LineaDocumentoPromocionable linea : lineasAplicables.getLineasAplicables()) {
        		cambiarTarifa(linea, codtarNuevo);
        		linea.setHayCambioTarifaReferencia(true);
        	}
        }
    }

	public void cambiarTarifaOriginalLineasAplicables(LineasAplicablesPromoBean lineasAplicables, Map<Integer, String> tarifasOriginales) {
    	for(LineaDocumentoPromocionable linea : lineasAplicables.getLineasAplicables()) {
    		String codtar = tarifasOriginales.get(linea.getIdLinea());
    		if(StringUtils.isNotBlank(codtar)) {
    			linea.cambiarTarifaCalculos(codtar);
    		}
    		else {
    			linea.cambiarTarifaOriginal();
    		}
    	}
    }
}
