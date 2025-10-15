/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST, TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */

package com.comerzzia.pos.services.promociones.tipos.especificos;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.pos.persistence.promociones.detalle.PromocionDetalleBean;
import com.comerzzia.pos.persistence.promociones.detalle.PromocionDetalleKey;
import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;
import com.comerzzia.pos.services.promociones.filtro.LineasAplicablesPromoBean;
import com.comerzzia.pos.services.promociones.tipos.PromocionLineaDetalles;
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.DetallePromocion;
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.DetallePromocionDescuento;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaCandidataTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.config.SpringContext;

@Component
@Scope("prototype")
public class PromocionDescuentoDetalles extends PromocionLineaDetalles {

	@Override
	public DetallePromocion createDetallePromocion(PromocionDetalleBean detalleBean) throws XMLDocumentException {
		return new DetallePromocionDescuento(this, detalleBean);
	}

	public DetallePromocionDescuento getDetallePromocionDescuento(String codArticulo, String desglose1, String desglose2) {
		PromocionDetalleKey key = new PromocionDetalleKey();
		key.setCodArticulo(codArticulo);
		key.setDesglose1(desglose1);
		key.setDesglose2(desglose2);
		return (DetallePromocionDescuento) getDetalle(getDetalles(), key);
	}

	@Override
	public void analizarLineasAplicables(DocumentoPromocionable<IPromocionTicket> documento) {
		LineasAplicablesPromoBean lineasAplicables = SpringContext.getBean(LineasAplicablesPromoBean.class);
		lineasAplicables.setFiltroPromoExclusiva(true);

		LinkedList<LineaDocumentoPromocionable> lineas = new LinkedList<LineaDocumentoPromocionable>();
		for (LineaDocumentoPromocionable linea : documento.getLineasDocumentoPromocionable()) {
			DetallePromocionDescuento detallePromocion = getDetallePromocionDescuento(linea.getCodArticulo(), linea.getDesglose1(), linea.getDesglose2());
			if (detallePromocion != null && detallePromocion.isAplicable()) {
				lineas.add(linea);
			}
		}
		lineasAplicables.setLineasAplicables(lineas);

		PromocionLineaCandidataTicket candidata = new PromocionLineaCandidataTicket(lineasAplicables, lineasAplicables, this);
		for (LineaDocumentoPromocionable linea : lineasAplicables.getLineasAplicables()) {
			linea.addPromocionAplicable(candidata);
		}
	}

	@Override
	public BigDecimal calcularPromocion(LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables) {
		BigDecimal importeTotalAhorro = BigDecimal.ZERO;
		BigDecimal cantidadAplicada = BigDecimal.ZERO;
		BigDecimal quantityToApply = lineasAplicables.getCandidateQuantityRemaining();
		
		for (int i = 0; i < lineasAplicables.getLineasAplicables().size();) {
			LineaDocumentoPromocionable linea = lineasAplicables.getLineasAplicables().get(i);
			
			//Comprobamos si hay cantidad de artículos suficientes
            if (BigDecimalUtil.isMenorOrIgualACero(quantityToApply.subtract(cantidadAplicada))) {
                break;
            }
            
			// Obtenemos detalle aplicable para este artículo (sólo puede haber uno en cada promoción unitaria)
			DetallePromocionDescuento detalle = getDetallePromocionDescuento(linea.getCodArticulo(), linea.getDesglose1(), linea.getDesglose2());
			setTextoPromocion(detalle.getTextoPromocion());

			// Comprobamos si la promoción puede seguir aplicándose en esta línea
			if (BigDecimalUtil.isMayorOrIgual(linea.getCantidadPromocionCandidata(), linea.getCantidad())) {
				linea.recalcularImporteFinal();
				i++;
				continue; // nueva iteración del bucle
			}

			BigDecimal aumento = BigDecimal.ONE;
			BigDecimal cantidadRestante = linea.getCantidad().subtract(linea.getCantidadPromocionCandidata());
			if (BigDecimalUtil.isMenor(cantidadRestante, BigDecimal.ONE)) {
				aumento = cantidadRestante;
			}

			BigDecimal ahorro = BigDecimal.ZERO;
			if(detalle.isTipoDtoPorcentaje()) {
				ahorro = BigDecimalUtil.porcentaje(linea.getPrecioAplicacionPromocion(), detalle.getDescuento());
			}else if(detalle.isTipoDtoImporte()) {
				ahorro = detalle.getDescuento();
			}
			
			ahorro = ahorro.multiply(aumento);
			importeTotalAhorro = importeTotalAhorro.add(ahorro);

			linea.addCantidadPromocionCandidata(aumento);
			cantidadAplicada = cantidadAplicada.add(aumento);
		}
		return importeTotalAhorro;
	}

	@Override
	public void aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento, LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables) {
		IPromocionTicket promocionTicket = documento.getPromocion(getIdPromocion());
		if (promocionTicket == null) {
			promocionTicket = createPromocionTicket(customerCoupon);
		}

		BigDecimal cantidadAplicada = BigDecimal.ZERO;
		BigDecimal importeTotalAhorro = BigDecimal.ZERO;
		for (int i = 0; i < lineasAplicables.getLineasAplicables().size();) {
			LineaDocumentoPromocionable linea = lineasAplicables.getLineasAplicables().get(i);
			// Obtenemos detalle aplicable para este artículo (sólo puede haber uno en cada promoción unitaria)
			DetallePromocionDescuento detalle = getDetallePromocionDescuento(linea.getCodArticulo(), linea.getDesglose1(), linea.getDesglose2());

			// Comprobamos si hay cantidad de artículos suficientes
			if (BigDecimalUtil.isMenorOrIgualACero(lineasAplicables.getCantidadArticulos().subtract(cantidadAplicada))) {
				linea.recalcularImporteFinal();
				break;
			}

			// Comprobamos si la promoción puede seguir aplicándose en esta línea según la cantidad
			if (BigDecimalUtil.isMayorOrIgual(linea.getCantidadPromocion(), linea.getCantidad())) {
				linea.recalcularImporteFinal();
				i++;
				continue; // nueva iteración del bucle
			}

			BigDecimal aumento = BigDecimal.ONE;
			BigDecimal cantidadRestante = linea.getCantidad().subtract(linea.getCantidadPromocion());
			if (BigDecimalUtil.isMenor(cantidadRestante, BigDecimal.ONE)) {
				aumento = cantidadRestante;
			}

			BigDecimal ahorro = BigDecimal.ZERO;
			if(detalle.isTipoDtoPorcentaje()) {
				ahorro = BigDecimalUtil.porcentaje(linea.getPrecioAplicacionPromocion(), detalle.getDescuento());
			}else if(detalle.isTipoDtoImporte()) {
				ahorro = detalle.getDescuento();
			}
			ahorro = ahorro.multiply(aumento);
			importeTotalAhorro = importeTotalAhorro.add(BigDecimalUtil.redondear(ahorro));

			PromocionLineaTicket promocionLinea = linea.getPromocion(promocionTicket.getIdPromocion());
			if (promocionLinea == null) {
				promocionLinea = new PromocionLineaTicket(promocionTicket);
				linea.addPromocion(promocionLinea);
			}

			promocionLinea.addImporteTotalDto(ahorro);

			promocionLinea.addCantidadPromocion(aumento);
			linea.addCantidadPromocion(aumento);
			cantidadAplicada = cantidadAplicada.add(aumento);
		}

		if (BigDecimalUtil.isMayorACero(cantidadAplicada)) {
			promocionTicket.setImporteTotalAhorro(importeTotalAhorro);
			documento.addPromocion(promocionTicket);
		}
	}

	@Override
	public void leerDatosPromocion(byte[] datosPromocion) {
	}

	public BigDecimal getDescuento(String codArt, String desglose1, String desglose2) {
		return getDetallePromocionDescuento(codArt, desglose1, desglose2).getDescuento();
	}

}
