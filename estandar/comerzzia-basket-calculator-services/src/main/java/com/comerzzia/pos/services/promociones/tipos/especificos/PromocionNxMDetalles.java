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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.pos.persistence.promociones.detalle.PromocionDetalleBean;
import com.comerzzia.pos.persistence.promociones.detalle.PromocionDetalleKey;
import com.comerzzia.pos.services.core.variables.VariablesServices;
import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;
import com.comerzzia.pos.services.promociones.filtro.LineasAplicablesPromoBean;
import com.comerzzia.pos.services.promociones.tipos.PromocionLineaDetalles;
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.DetallePromocion;
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.DetallePromocionNxM;
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.LineaDetallePromocionNxM;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaCandidataTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.config.SpringContext;

@Component
@Scope("prototype")
public class PromocionNxMDetalles extends PromocionLineaDetalles {

	protected static Logger log = Logger.getLogger(PromocionNxMDetalles.class);
	protected List<DetallePromocionNxM> detalles;
	protected Map<PromocionDetalleKey, DetallePromocion> detallesArticulos;
	
	@Autowired
	private VariablesServices variablesServices;
    
	public void setDetalles(List<PromocionDetalleBean> detallesBean) throws XMLDocumentException {
		Map<Long, DetallePromocionNxM> mapaDetalles = new HashMap<>(); // Map de IdAgrupacion -> Agrupacion (N, M, descuento, lista de codArts)
		detallesArticulos = new HashMap<>(); // Map de codArt -> Agrupación
		for (PromocionDetalleBean promocionDetalleBean : detallesBean) {
			DetallePromocionNxM detalleNxM = mapaDetalles.get(promocionDetalleBean.getIdAgrupacion());
			if (detalleNxM == null) {
				detalleNxM = new DetallePromocionNxM(this, promocionDetalleBean);
				validar(detalleNxM);
				mapaDetalles.put(promocionDetalleBean.getIdAgrupacion(), detalleNxM);
			} else {
				detalleNxM.getLineasAgrupacion().add(createLineaPromocion(promocionDetalleBean));
			}
			validar(detalleNxM);
			PromocionDetalleKey key = new PromocionDetalleKey();
			key.setCodArticulo(promocionDetalleBean.getCodArticulo());
			key.setDesglose1(promocionDetalleBean.getDesglose1());
			key.setDesglose2(promocionDetalleBean.getDesglose2());
			detallesArticulos.put(key, detalleNxM);
		}
		detalles = new ArrayList<>(mapaDetalles.values()); // Lista de agrupaciones
    }
	
	@Override
	public DetallePromocion createDetallePromocion(PromocionDetalleBean detalleBean) throws XMLDocumentException {
		return new DetallePromocionNxM(this, detalleBean);
	}

	protected LineaDetallePromocionNxM createLineaPromocion(PromocionDetalleBean promocionDetalleBean) throws XMLDocumentException {
		String codArticulo = promocionDetalleBean.getCodArticulo();
		String desglose1 = promocionDetalleBean.getDesglose1();
		String desglose2 = promocionDetalleBean.getDesglose2();
		XMLDocument xml = new XMLDocument(promocionDetalleBean.getDatosPromocion());
		BigDecimal descuento = xml.getRoot().getNodo("Descuento").getValueAsBigDecimal();
		Integer cantidadN = xml.getRoot().getNodo("N").getValueAsInteger();
		Integer cantidadM = xml.getRoot().getNodo("M").getValueAsInteger();
		String storeLanguageCode = getStoreLanguageCode();
        for(XMLDocumentNode textPromoNode : xml.getNodos("textoPromocion")) {
        	String languageCode = textPromoNode.getAtributoValue("lang", true);
        	if(StringUtils.isNotBlank(languageCode) && languageCode.equals(storeLanguageCode)) {
        		promocionDetalleBean.setTextoPromocion(textPromoNode.getValue());
        		break;
        	}
        }
		return new LineaDetallePromocionNxM(codArticulo, desglose1, desglose2, descuento, cantidadN, cantidadM, promocionDetalleBean.getFechaInicio(), promocionDetalleBean.getFechaFin());
	}
	
	@Override
	public void analizarLineasAplicables(DocumentoPromocionable<IPromocionTicket> documento) {
		Calendar calendarHoy = Calendar.getInstance();
        calendarHoy.set(Calendar.HOUR_OF_DAY, 0);
        calendarHoy.set(Calendar.MINUTE, 0);
        calendarHoy.set(Calendar.SECOND, 0);
        calendarHoy.set(Calendar.MILLISECOND, 0);
		
		// Creamos un PromocionLineaCandidataTicket por cada Agrupación
		for (DetallePromocionNxM detalle : detalles) {
			LineasAplicablesPromoBean lineasAplicables = SpringContext.getBean(LineasAplicablesPromoBean.class);
			lineasAplicables.setFiltroPromoExclusiva(true);
			lineasAplicables.setFiltroLineasCantidadDecimales(true);
			LinkedList<LineaDocumentoPromocionable> lineas = new LinkedList<LineaDocumentoPromocionable>();
			for (LineaDocumentoPromocionable linea : documento.getLineasDocumentoPromocionable()) {
				if (containsCodArticulo(detalle.getLineasAgrupacion(calendarHoy), linea.getArticulo().getCodArticulo(), linea.getDesglose1(), linea.getDesglose2())){
					lineas.add(linea);
				}
			}
			lineasAplicables.setLineasAplicables(lineas);

			if (BigDecimalUtil.isMenor(lineasAplicables.getCantidadArticulos(), new BigDecimal(detalle.getCantidadN()))){
	            log.trace(this + " analizarLineasAplicables() - El detalle de promoción NxM no se puede aplicar porque las líneas no suman la cantidad N configurada: " + detalle.getCantidadN() + " Aplicables: " + lineasAplicables.getCantidadArticulos());
	            continue;
	        }
			
	        // Ordenamos las líneas aplicables por precio descendente
	        lineasAplicables.ordenarLineasPrecioDesc();
			
			PromocionLineaCandidataTicket candidata = new PromocionLineaCandidataTicket(lineasAplicables, lineasAplicables, this);
			for (LineaDocumentoPromocionable linea : lineasAplicables.getLineasAplicables()) {
	        	linea.addPromocionAplicable(candidata);
	        }
		}
		
	}
	
	protected boolean containsCodArticulo(List<LineaDetallePromocionNxM> lineas, String codArticulo, String desglose1, String desglose2) {
		String variableDesglose1 = variablesServices.getVariableAsString(VariablesServices.ARTICULO_DESGLOSE1_TITULO);
		String variableDesglose2 = variablesServices.getVariableAsString(VariablesServices.ARTICULO_DESGLOSE2_TITULO);
		
		for (LineaDetallePromocionNxM lineaDetallePromocionNxM : lineas) {
			if (lineaDetallePromocionNxM.getCodArticulo().equals(codArticulo) &&
				(StringUtils.isBlank(variableDesglose1) || (StringUtils.isBlank(desglose1) || lineaDetallePromocionNxM.getDesglose1().equals("*")) || lineaDetallePromocionNxM.getDesglose1().equals(desglose1)) &&
				(StringUtils.isBlank(variableDesglose2) || (StringUtils.isBlank(desglose2) || lineaDetallePromocionNxM.getDesglose2().equals("*")) || lineaDetallePromocionNxM.getDesglose2().equals(desglose2))	)  {
				return true;
			}
		}
		return false;
	}
	
    @Override
	public BigDecimal calcularPromocion(LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables) {
		// Obtenemos a qué agrupación pertenece los codArticulos de este LineasAplicables
		String codArticulo = lineasAplicables.getLineasAplicables().get(0).getArticulo().getCodArticulo();
    	PromocionDetalleKey key = new PromocionDetalleKey();
		key.setCodArticulo(codArticulo);
		key.setDesglose1(lineasAplicables.getLineasAplicables().get(0).getDesglose1());
		key.setDesglose2(lineasAplicables.getLineasAplicables().get(0).getDesglose2());
		DetallePromocionNxM detalleNxM = (DetallePromocionNxM) getDetalle(detallesArticulos, key);
		if(detalleNxM != null){
			setTextoPromocion(detalleNxM.getTextoPromocion());
			// Calculamos el número de combos que podremos aplicar
			Integer cantidadN = detalleNxM.getCantidadN();
			Integer cantidadM = detalleNxM.getCantidadM();
			BigDecimal descuento = detalleNxM.getDescuento();
			boolean isPorcentaje = detalleNxM.isTipoDtoPorcentaje();

			Integer numCombos = lineasAplicables.getCantidadArticulos().divide(new BigDecimal(cantidadN), 1, RoundingMode.DOWN).intValue();
			Integer cantConDescuento = cantidadN - cantidadM;
			Integer cantSinDescuento = cantidadM;
			BigDecimal importeTotalAhorro = BigDecimal.ZERO;
			for (int i = 1; i <= numCombos; i++) {
				// Instanciamos la promoción del documento (cada combo tendrá una)
				PromocionTicket promocionTicket = createPromocionTicket(customerCoupon);

				// Intentamos aplicar la promoción sobre las líneas aplicables
				BigDecimal importeTotalAhorroCombo = lineasAplicables.aplicaDescuentoCandidato(promocionTicket, descuento, null, isPorcentaje, !isPorcentaje, new BigDecimal(cantSinDescuento), new BigDecimal(cantConDescuento));
				if (importeTotalAhorroCombo == null) {
					return importeTotalAhorro;
				}
				// Si conseguimos aplicar la promoción, añadimos el combo aplicado al total
				importeTotalAhorro = importeTotalAhorro.add(importeTotalAhorroCombo);
			}
			return importeTotalAhorro;
		}else{
			return BigDecimal.ZERO;
		}
		
	}   
	
    @Override
    public void aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento, LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables) {
    	//Obtenemos a qué agrupación pertenece los codArticulos de este LineasAplicables
    	String codArticulo = lineasAplicables.getLineasAplicables().get(0).getArticulo().getCodArticulo();
    	PromocionDetalleKey key = new PromocionDetalleKey();
		key.setCodArticulo(codArticulo);
		key.setDesglose1(lineasAplicables.getLineasAplicables().get(0).getDesglose1());
		key.setDesglose2(lineasAplicables.getLineasAplicables().get(0).getDesglose2());
		DetallePromocionNxM detalleNxM = (DetallePromocionNxM) getDetalle(detallesArticulos, key);
		if(detalleNxM != null){
			setTextoPromocion(detalleNxM.getTextoPromocion());
	    	// Calculamos el número de combos que podremos aplicar
	        Integer cantidadN = detalleNxM.getCantidadN();
	        Integer cantidadM = detalleNxM.getCantidadM();
	        BigDecimal descuento = detalleNxM.getDescuento();
	        boolean isPorcentaje = detalleNxM.isTipoDtoPorcentaje();

			Integer numCombos = lineasAplicables.getCantidadArticulos().divide(new BigDecimal(cantidadN), 1, RoundingMode.DOWN).intValue();
			Integer cantConDescuento = cantidadN - cantidadM;
			Integer cantSinDescuento = cantidadM;
	        for (int i = 1; i <= numCombos; i++) {
	            // Instanciamos la promoción del documento (cada combo tendrá una)
	            PromocionTicket promocionTicket = createPromocionTicket(customerCoupon);
	            
	            // Intentamos aplicar la promoción sobre las líneas aplicables
				BigDecimal importeTotalAhorro = lineasAplicables.aplicaDescuento(promocionTicket, descuento, null, isPorcentaje, !isPorcentaje, new BigDecimal(cantSinDescuento), new BigDecimal(cantConDescuento));
	            if (importeTotalAhorro == null){
	                return;
	            }
	            // Si conseguimos aplicar la promoción, añadimos el combo aplicado a las promociones del documento
	            promocionTicket.setImporteTotalAhorro(importeTotalAhorro);
	            documento.addPromocion(promocionTicket);
	        }
		}
        
    }    
    
	@Override
	public void leerDatosPromocion(byte[] datosPromocion) {
	}
	
	public void validar(DetallePromocionNxM detallePromocionNxM) {
		
		BigDecimal n = new BigDecimal(detallePromocionNxM.getCantidadN()); 
		BigDecimal m = new BigDecimal(detallePromocionNxM.getCantidadM());
		
		if (BigDecimalUtil.isMenorOrIgual(n, m)) {
			throw new IllegalArgumentException("La condición N debe ser mayor que M. Actualmente, N: " + n + ", M: "+ m);
		}
		if (BigDecimalUtil.isMenorOrIgualACero(n)) {
			throw new IllegalArgumentException("La condición N debe ser distinta de cero y positiva.");
		}
		if (BigDecimalUtil.isMenorOrIgualACero(m)) {
			throw new IllegalArgumentException("La condición M debe ser distinta de cero y positiva.");
		}
	}

}