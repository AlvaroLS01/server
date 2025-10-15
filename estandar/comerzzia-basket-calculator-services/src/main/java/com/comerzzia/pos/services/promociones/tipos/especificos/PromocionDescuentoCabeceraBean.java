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
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNodeNotFoundException;
import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;
import com.comerzzia.pos.services.promociones.filtro.FiltroLineasPromocion;
import com.comerzzia.pos.services.promociones.filtro.LineasAplicablesPromoBean;
import com.comerzzia.pos.services.promociones.tipos.PromocionCabecera;
import com.comerzzia.pos.services.promociones.tipos.componente.CondicionPrincipalPromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.GrupoComponentePromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.ItemComponentePromoBean;
import com.comerzzia.pos.services.ticket.cupones.CuponAplicadoTicket;
import com.comerzzia.pos.services.ticket.lineas.LineaTicket;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;

@Component
@Scope("prototype")
public class PromocionDescuentoCabeceraBean extends PromocionCabecera {

    protected static final Logger log = Logger.getLogger(PromocionDescuentoCabeceraBean.class);

    protected CondicionPrincipalPromoBean condiciones;
    protected GrupoComponentePromoBean aplicacion;
    protected GrupoComponentePromoBean lineasAplicacion;
    protected String tipoFiltro;

	@Override
    public boolean aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento) {
    	log.trace("aplicarPromocion() - " + this);
        // Obtenemos las líneas aplicables según el filtro configurado
        FiltroLineasPromocion filtroLineas = createFiltroLineasPromocion(documento);
        filtroLineas.setFiltrarPromoExclusivas(false); // Da igual que las líneas tengan una promoción exclusiva
        LineasAplicablesPromoBean lineasAplicables = filtroLineas.getNumCombosCondicion(condiciones);
        if (lineasAplicables.isEmpty()) {
            log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar por no existir líneas aplicables en el documento .");
            return false;
        }
        
        if(lineasAplicacion != null) {
	        lineasAplicables = filtroLineas.getLineasAplicablesGrupo(lineasAplicacion);
	        if (lineasAplicables.isEmpty()) {
	        	log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar por no existir líneas aplicables en el documento .");
	        	return false;
	        }
        }

        // Obtenemos el importe de descuento 
        BigDecimal valorConfigurado = getImporteDescuento(lineasAplicables);

        if (valorConfigurado == null) {
            log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar porque no se ha definido un intervalo de importe aplicable compatible. Importe: " + lineasAplicables.getImporteLineasConDto());
            return false;
        }
        BigDecimal importeLineasConDto = lineasAplicables.getImporteLineasConDto();
        BigDecimal totalesPromocionesCabecera = BigDecimal.ZERO;
        for(LineaDocumentoPromocionable linea : lineasAplicables.getLineasAplicables()){
        	totalesPromocionesCabecera = totalesPromocionesCabecera.add(((LineaTicket) linea).getImporteTotalPromocionesMenosIngreso());
        }


        // Aplicamos la promoción sobre el ticket
        PromocionTicket promocionTicket = createPromocionTicket(customerCoupon);
        BigDecimal importePromocion = BigDecimal.ZERO;
        
        // Si el tipo de filtro es importe 
        if(isImporte()){
        		BigDecimal importeDespuesPromociones = importeLineasConDto.subtract(totalesPromocionesCabecera);
        		if(BigDecimalUtil.isMayorOrIgual(valorConfigurado, importeDespuesPromociones)){
            		importePromocion = importeDespuesPromociones;
            	}else{
            		importePromocion = valorConfigurado;
            	}

        	
        }
        // Si el tipo de filtro es porcentaje
        else if(isPorcentaje()){      	
        	BigDecimal importeDespuesPromociones = importeLineasConDto.subtract(totalesPromocionesCabecera);
        	importePromocion = BigDecimalUtil.porcentaje(importeDespuesPromociones, valorConfigurado);
        }

        promocionTicket.setImporteTotalAhorro(importePromocion);
        documento.addPromocion(promocionTicket);
        
        if(isImporte()){
        	BigDecimal porcentajeDescuento = BigDecimal.ZERO;
        		BigDecimal importeDespuesPromociones = importeLineasConDto.subtract(totalesPromocionesCabecera);
        		if(!BigDecimalUtil.isIgualACero(importePromocion)){
        			porcentajeDescuento = BigDecimalUtil.getTantoPorCiento(importeDespuesPromociones, importePromocion);
        		}
           
            lineasAplicables.aplicaDescuentoPorcentajeGeneral(promocionTicket, porcentajeDescuento);
        }
        else if(isPorcentaje()){
            lineasAplicables.aplicaDescuentoPorcentajeGeneral(promocionTicket, valorConfigurado);
        }
        
        if(customerCoupon != null) {
	        CuponAplicadoTicket cupon = documento.getCuponAplicado(customerCoupon.getCouponCode());
	        if (cupon != null){
	        	cupon.setTextoPromocion(promocionTicket.getTextoPromocion());
	        }
        }

        return true;
    }

    protected BigDecimal getImporteDescuento(LineasAplicablesPromoBean lineasAplicables) {
        boolean intervaloAplicable;
        // Recorremos los intervalos para obtener el que corresponde (los intervalos ya están ordenados de menor a mayor)
        for (ItemComponentePromoBean intervalo : aplicacion.getReglas()) {
            if (intervalo.getOperacion().equals(ItemComponentePromoBean.OP_MAYOR)) {
                intervaloAplicable = BigDecimalUtil.isMayor(lineasAplicables.getImporteLineasConDto(), intervalo.getValorAsBigDecimal());
            }
            else { // Mayor o igual
                intervaloAplicable = BigDecimalUtil.isMayorOrIgual(lineasAplicables.getImporteLineasConDto(), intervalo.getValorAsBigDecimal());
            }
            if (intervaloAplicable) {
                return intervalo.getValorAdicionalAsBigDecimal();
            }
        }
        return null;
    }

    @Override
    public void leerDatosPromocion(byte[] datosPromocion) {
        try {
            XMLDocument xmlPromocion = new XMLDocument(datosPromocion);
            condiciones = new CondicionPrincipalPromoBean(xmlPromocion.getNodo("condicionLineas"));
            aplicacion = new GrupoComponentePromoBean(xmlPromocion.getNodo("aplicacion"));
            XMLDocumentNode nodoLineasAplicacion = xmlPromocion.getNodo("lineasAplicacion", true);
            if(nodoLineasAplicacion != null) {
            	lineasAplicacion = new GrupoComponentePromoBean(nodoLineasAplicacion);
            }
            
            Collections.sort(aplicacion.getReglas());
            
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
            try{
            	tipoFiltro = xmlPromocion.getNodo("tipoFiltro").getValue();
            }
            catch(XMLDocumentNodeNotFoundException ignore){
            }
            if (tipoFiltro == null){
            	tipoFiltro = "Importe";
            }
        }
        catch (XMLDocumentException e) {
            log.error("Error al leer los datos de la promoción de tipo descuento  cabecera: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean isImporte(){
        return tipoFiltro.equals("Importe");
    }
    
    public boolean isPorcentaje(){
        return tipoFiltro.equals("Porcentaje");
    }
    
    public GrupoComponentePromoBean getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(GrupoComponentePromoBean aplicacion) {
        this.aplicacion = aplicacion;
    }

}
