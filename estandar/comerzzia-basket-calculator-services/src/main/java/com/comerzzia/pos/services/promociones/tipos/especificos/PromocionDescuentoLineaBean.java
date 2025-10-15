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
import java.util.List;
import java.util.Map;

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
import com.comerzzia.pos.services.promociones.tipos.componente.CondicionPrincipalPromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.GrupoComponentePromoBean;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaCandidataTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;

@Component
@Scope("prototype")
public class PromocionDescuentoLineaBean extends PromocionLinea{
    
    protected static final Logger log = Logger.getLogger(PromocionDescuentoLineaBean.class);  

    protected CondicionPrincipalPromoBean condiciones;

    protected static final String TIPO_DTO_PORCENTAJE = "Descuento";
    protected static final String TIPO_DTO_PRECIO    = "Precio";
    protected static final String TIPO_DTO_IMPORTE    = "Importe";

    protected GrupoComponentePromoBean aplicacion;
    protected String tipoDescuento;
    protected BigDecimal precioPorcentaje;
    protected BigDecimal cantidad;

    @Override
    public void analizarLineasAplicables(DocumentoPromocionable<IPromocionTicket> documento) {
    	log.trace("analizarLineasAplicables() - " + this);
    	
    	FiltroLineasPromocion filtroLineas = createFiltroLineasPromocion(documento);
        LineasAplicablesPromoBean lineasCondicion = filtroLineas.getNumCombosCondicion(condiciones);

        // Obtenemos las líneas de condición según el filtro configurado
        if (lineasCondicion.isEmpty() || lineasCondicion.getNumCombos() == 0) {
            log.trace(this + " analizarLineasAplicables() - La promoción no se puede aplicar por no existir las líneas que cumplan las condiciones en el documento .");
            return;
        }

        // Obtenemos las líneas aplicables según el filtro configurado
        filtroLineas.setFiltrarPromoExclusivas(true);
        boolean cantidadAplicacionLimitada = !BigDecimalUtil.isIgualACero(this.cantidad);
        boolean filtrarLineasCantidadDecimales =  cantidadAplicacionLimitada;
        
        LineasAplicablesPromoBean lineasAplicables = filtroLineas.getLineasAplicablesGrupo(getAplicacion(), filtrarLineasCantidadDecimales);
        if (lineasAplicables.isEmpty()) {
            return;
        }

        // Ordenamos líneas
        lineasAplicables.ordenarLineasPrecioDesc();
        
        // Marcamos líneas con la promoción candidata
        PromocionLineaCandidataTicket candidata = new PromocionLineaCandidataTicket(lineasAplicables, lineasCondicion, this);
        for (LineaDocumentoPromocionable linea : lineasAplicables.getLineasAplicables()) {
        	linea.addPromocionAplicable(candidata);
        }
    }    
    
    
	@Override
    public BigDecimal calcularPromocion(LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables) {
        // Tenemos en cuenta la cantidad de artículos a los que podemos dar descuento
		BigDecimal cantidadDescuento = cantidad;
        if (BigDecimalUtil.isIgualACero(cantidadDescuento)){ // Se aplica a todos los artículos
            cantidadDescuento = lineasAplicables.getCantidadArticulos();
        }
        
        Map<Integer, String> tarifasOriginales = getTarifasOriginalesLinea(lineasAplicables);
        
        BigDecimal totalTarifaOrigen = lineasAplicables.getImporteTotalLineasSinPromociones();
        cambiarTarifaLineasAplicables(lineasAplicables, promocionBean.getCodtarPrecios());
        BigDecimal totalTarifaPromocion = lineasAplicables.getImporteTotalLineasConPromociones();
        
        BigDecimal importeTotalAhorro = BigDecimal.ZERO;
        for (int i = 1; i <= lineasCondicion.getNumCombos(); i++) {
            // Instanciamos la promoción del documento (cada combo tendrá una)
            PromocionTicket promocionTicket = createPromocionTicket(customerCoupon);
            
            // Intentamos aplicar la promoción sobre las líneas aplicables
            BigDecimal importeTotalAhorroCombo = lineasAplicables.aplicaDescuentoCandidato(promocionTicket, precioPorcentaje, null, isTipoDtoPorcentaje(), isTipoDtoImporte(), BigDecimal.ZERO, cantidadDescuento);
            if (importeTotalAhorroCombo == null) {
            	cambiarTarifaOriginalLineasAplicables(lineasAplicables, tarifasOriginales);
                return importeTotalAhorro;
            }
            // Si conseguimos aplicar la promoción, añadimos el combo aplicado al total
            importeTotalAhorro = importeTotalAhorro.add(importeTotalAhorroCombo).add(totalTarifaOrigen).subtract(totalTarifaPromocion);
        }

        cambiarTarifaOriginalLineasAplicables(lineasAplicables, tarifasOriginales);

        return importeTotalAhorro;
    }

	@Override
    public void aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento, LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables) {
        // Tenemos en cuenta la cantidad de artículos a los que podemos dar descuento
    	BigDecimal cantidadDescuento = cantidad;
        if (BigDecimalUtil.isIgualACero(cantidadDescuento)){ // Se aplica a todos los artículos
            cantidadDescuento = lineasAplicables.getCantidadArticulos();
        }
        cantidadDescuento = cantidadDescuento.subtract(getAppliedQuantity(lineasAplicables));
        if(BigDecimalUtil.isMenorOrIgualACero(cantidadDescuento)) {
        	return;
        }
        
        Map<Integer, String> tarifasOriginales = getTarifasOriginalesLinea(lineasAplicables);
        BigDecimal totalTarifaOrigen = lineasAplicables.getImporteTotalLineasSinPromociones();
        cambiarTarifaLineasAplicables(lineasAplicables, promocionBean.getCodtarPrecios());
        
        for (int i = 1; i <= lineasCondicion.getNumCombos(); i++) {
            // Instanciamos la promoción del documento (cada combo tendrá una)
            PromocionTicket promocionTicket = createPromocionTicket(customerCoupon);
            
            // Intentamos aplicar la promoción sobre las líneas aplicables
            BigDecimal importeTotalAhorro = lineasAplicables.aplicaDescuentoCandidato(promocionTicket, precioPorcentaje, null, isTipoDtoPorcentaje(), isTipoDtoImporte(), BigDecimal.ZERO, cantidadDescuento);
            // Calculamos si la promocion mejora el precio original para acabar aplicandola
            if (importeTotalAhorro == null || BigDecimalUtil.isMayorOrIgual(lineasAplicables.getImporteTotalLineasSinPromociones().subtract(importeTotalAhorro), totalTarifaOrigen)) {
            	cambiarTarifaOriginalLineasAplicables(lineasAplicables, tarifasOriginales);
                return;
            }
            
            // Reseteamos promociones candidatas
            for (LineaDocumentoPromocionable linea : documento.getLineasDocumentoPromocionable()){
                linea.resetPromocionesCandidatas();
            }

            importeTotalAhorro = lineasAplicables.aplicaDescuento(promocionTicket, precioPorcentaje, null, isTipoDtoPorcentaje(), isTipoDtoImporte(), BigDecimal.ZERO, cantidadDescuento);

            
            // Si conseguimos aplicar la promoción, añadimos el combo aplicado a las promociones del documento
            promocionTicket.setImporteTotalAhorro(importeTotalAhorro);
            documento.addPromocion(promocionTicket);
        }
    }


	protected BigDecimal getAppliedQuantity(LineasAplicablesPromoBean lineasAplicables) {
		BigDecimal appliedQuantity = BigDecimal.ZERO;
        for(LineaDocumentoPromocionable line: lineasAplicables.getLineasAplicables()) {
        	PromocionLineaTicket promo = line.getPromocion(getIdPromocion());
        	if(promo!=null) {
        		appliedQuantity = appliedQuantity.add(promo.getCantidadPromocion());
        	}
        }
		return appliedQuantity;
	}

    protected boolean isPromocionRegalo(){
    	return isTipoDtoPorcentaje() && BigDecimalUtil.isIgual(precioPorcentaje, BigDecimalUtil.CIEN);
    }
    
    @Override
    public void leerDatosPromocion(byte[] datosPromocion) {
        try {
            XMLDocument xmlPromocion = new XMLDocument(datosPromocion);         
            
            condiciones = (new CondicionPrincipalPromoBean(xmlPromocion.getNodo("condicionLineas")));
            aplicacion = new GrupoComponentePromoBean(xmlPromocion.getNodo("aplicacion"));
            
            setTipoDescuento(xmlPromocion.getNodo("tipoFiltro").getValue());
            BigDecimal precioDescuentoFormat = BigDecimal.ZERO;
            try {
            	
            	precioDescuentoFormat = xmlPromocion.getNodo("precioDescuento").getValueAsBigDecimal();
            }catch(NumberFormatException e) {
            	String strPrecioDescuentoFormat = xmlPromocion.getNodo("precioDescuento").getValue();
            	precioDescuentoFormat = new BigDecimal(strPrecioDescuentoFormat.replace(",", "."));
            }
            setPrecioPorcentaje(precioDescuentoFormat);
            
            String cantidadTipo = xmlPromocion.getNodo("cantidadTipo").getValue();
            if(cantidadTipo == null || cantidadTipo.isEmpty() ) {
            	   setCantidad(BigDecimal.ZERO);
            } else {
            	   setCantidad(new BigDecimal(cantidadTipo));
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
        }
        catch (XMLDocumentException e) {
            log.error("Error al leer los datos de la promoción de tipo descuento  línea: "+e.getMessage(), e);
        }
    }


    public GrupoComponentePromoBean getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(GrupoComponentePromoBean aplicacion) {
        this.aplicacion = aplicacion;
    }

    public String getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(String tipo) {
        this.tipoDescuento = tipo;
    }

    public BigDecimal getPrecioPorcentaje() {
        return precioPorcentaje;
    }

    public void setPrecioPorcentaje(BigDecimal precioPorcentaje) {
        this.precioPorcentaje = precioPorcentaje;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isTipoDtoPorcentaje(){
        return tipoDescuento.equals(TIPO_DTO_PORCENTAJE);
    }
    public boolean isTipoDtoPrecio(){
        return tipoDescuento.equals(TIPO_DTO_PRECIO);
    }
    public boolean isTipoDtoImporte(){
        return tipoDescuento.equals(TIPO_DTO_IMPORTE);
    }

}
