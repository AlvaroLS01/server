/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND
 * INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED,
 * DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST,
 * TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK
 * WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE
 * AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */

package com.comerzzia.pos.services.promociones.tipos.especificos;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.ListIterator;

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
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.DetallePromocionPrecio;
import com.comerzzia.pos.services.ticket.promociones.IPromocionLineaTicket;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaCandidataTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.config.SpringContext;

@Component
@Scope("prototype")
public class PromocionPrecioDetalles extends PromocionLineaDetalles {

	@Override
	public DetallePromocion createDetallePromocion(PromocionDetalleBean detalleBean) throws XMLDocumentException {
    	return new DetallePromocionPrecio(this, detalleBean);
	}

    public DetallePromocionPrecio getDetallePromocionPrecio(String codArticulo, String desglose1, String desglose2) {
		PromocionDetalleKey key = new PromocionDetalleKey();
		key.setCodArticulo(codArticulo);
		key.setDesglose1(desglose1);
		key.setDesglose2(desglose2);
		return (DetallePromocionPrecio) getDetalle(getDetalles(), key);
    }
	
	public DetallePromocion getDetalleAplicable(String codArticulo, String desglose1, String desglose2) {
		return getDetallePromocionPrecio(codArticulo, desglose1, desglose2);
	}

	@Override
	public void analizarLineasAplicables(DocumentoPromocionable<IPromocionTicket> documento) {
		LineasAplicablesPromoBean lineasAplicables = SpringContext.getBean(LineasAplicablesPromoBean.class);
		lineasAplicables.setFiltroPromoExclusiva(true);

		LinkedList<LineaDocumentoPromocionable> lineas = new LinkedList<LineaDocumentoPromocionable>();
		for (LineaDocumentoPromocionable linea : documento.getLineasDocumentoPromocionable()) {
			DetallePromocionPrecio detallePromocionPrecio = getDetallePromocionPrecio(linea.getCodArticulo(), linea.getDesglose1(), linea.getDesglose2());
			if (detallePromocionPrecio != null && detallePromocionPrecio.isAplicable()) {
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
            
            // lineas negativas no ahorran
            if (BigDecimalUtil.isMenorOrIgualACero(linea.getCantidad())) {
                i++;
                continue; // nueva iteración del bucle
            }   
			
			// Obtenemos detalle aplicable para este artículo (sólo puede haber uno en cada promoción unitaria)
			DetallePromocionPrecio detalle = getDetallePromocionPrecio(linea.getCodArticulo(), linea.getDesglose1(), linea.getDesglose2());
			
			// Comprobamos si la promoción puede seguir aplicándose en esta línea según la cantidad
			if (BigDecimalUtil.isMayorOrIgual(linea.getCantidadPromocionCandidata(), linea.getCantidad())) {
				i++;
				continue; // nueva iteración del bucle
			}

			BigDecimal aumento = BigDecimal.ONE;
            BigDecimal cantidadRestante = linea.getCantidad().subtract(linea.getCantidadPromocionCandidata());
            if (BigDecimalUtil.isMenor(cantidadRestante, BigDecimal.ONE)) {
            	aumento = cantidadRestante;
            }
			
			BigDecimal ahorro = BigDecimal.ZERO;
			BigDecimal precioDetalle = linea.isPrecioIncluyeImpuestos()? detalle.getPrecioTotal() : detalle.getPrecioVenta();
			ahorro = linea.getPrecioAplicacionPromocion().subtract(precioDetalle);
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
		BigDecimal quantityToApply = lineasAplicables.getQuantityRemaining();
		
		for (int i = 0; i < lineasAplicables.getLineasAplicables().size();) {
			LineaDocumentoPromocionable linea = lineasAplicables.getLineasAplicables().get(i);
			// Obtenemos detalle aplicable para este artículo (sólo puede haber uno en cada promoción unitaria)
			DetallePromocionPrecio detalle = getDetallePromocionPrecio(linea.getCodArticulo(), linea.getDesglose1(), linea.getDesglose2());
			setTextoPromocion(detalle.getTextoPromocion());
			promocionTicket.setTextoPromocion(getTextoPromocion());

			//Comprobamos si hay cantidad de artículos suficientes
            if (BigDecimalUtil.isMenorOrIgualACero(quantityToApply.subtract(cantidadAplicada))) {
                break;
            }
			
			// Comprobamos si la promoción puede seguir aplicándose en esta línea según la cantidad
			if (BigDecimalUtil.isMayorOrIgual(linea.getCantidadPromocion(), linea.getCantidad())) {
				i++;
				continue; // nueva iteración del bucle
			}
			
			BigDecimal aumento = BigDecimal.ONE;
            BigDecimal cantidadRestante = linea.getCantidad().subtract(linea.getCantidadPromocion());
            if (BigDecimalUtil.isMenor(cantidadRestante, BigDecimal.ONE)) {
            	aumento = cantidadRestante;
            }

			BigDecimal ahorro = BigDecimal.ZERO;
			BigDecimal precioDetalle = linea.isPrecioIncluyeImpuestos()? detalle.getPrecioTotal() : detalle.getPrecioVenta();
			ahorro = linea.getPrecioAplicacionPromocion().multiply(aumento).subtract(precioDetalle.multiply(aumento));
			importeTotalAhorro = importeTotalAhorro.add(BigDecimalUtil.redondear(ahorro));
			
			// Calculamos si la promocion mejora el precio original para acabar aplicandola
			if (BigDecimalUtil.isMenor(ahorro, BigDecimal.ZERO)) {
				i++;
				continue;
			}

			PromocionLineaTicket promocionLinea = linea.getPromocion(promocionTicket.getIdPromocion());
			if (promocionLinea == null) {
				promocionLinea = new PromocionLineaTicket(promocionTicket);
				linea.addPromocion(promocionLinea);
			}

			promocionLinea.addImporteTotalDto(ahorro);
			promocionLinea.addCantidadPromocion(aumento);
			linea.addCantidadPromocion(aumento);
			linea.recalcularImporteFinal();
			cantidadAplicada = cantidadAplicada.add(aumento);
		}

		if (BigDecimalUtil.isMayorACero(cantidadAplicada)) {
			promocionTicket.setImporteTotalAhorro(importeTotalAhorro);
			documento.addPromocion(promocionTicket);
		}
	}

	/** Utilizado cuando se aplican promociones de precio antes que el resto de promociones, si variable está configurada. */
	public void aplicaPromocionPrecioLinea(DocumentoPromocionable<IPromocionTicket> documento, LineaDocumentoPromocionable linea) {
		// Obtenemos detalle aplicable para este artículo (sólo puede haber uno en cada promoción unitaria)
		DetallePromocionPrecio detalle = getDetallePromocionPrecio(linea.getCodArticulo(), linea.getDesglose1(), linea.getDesglose2());
		if (detalle == null){
			return;
		}

		BigDecimal precioDetalle = linea.isPrecioIncluyeImpuestos()? detalle.getPrecioTotal() : detalle.getPrecioVenta();
		BigDecimal unitSaving = BigDecimalUtil.redondear(linea.getPrecioAplicacionPromocion().subtract(precioDetalle));
		BigDecimal ahorro = BigDecimalUtil.redondear(unitSaving.multiply(linea.getCantidad()));
		
		// Calculamos si la promocion mejora el precio original para acabar aplicandola
		if (BigDecimalUtil.isMenor(unitSaving, BigDecimal.ZERO)) {
			return;
		}
		
		setTextoPromocion(detalle.getTextoPromocion());

		IPromocionTicket promocionTicket = documento.getPromocion(getIdPromocion());
		if (promocionTicket == null) {
			promocionTicket = createPromocionTicket(customerCoupon);
			documento.addPromocion(promocionTicket);
		}
		PromocionLineaTicket promocionLinea = linea.getPromocion(promocionTicket.getIdPromocion());
		if (promocionLinea == null) {
			promocionLinea = new PromocionLineaTicket(promocionTicket);
			linea.addPromocion(promocionLinea);
		}
					
		promocionLinea.addImporteTotalDto(ahorro);
		promocionLinea.setCantidadPromocion(linea.getCantidad());
		linea.setPrecioPromocionSinDto(detalle.getPrecioVenta());
		linea.setPrecioPromocionTotalSinDto(detalle.getPrecioTotal());
		linea.recalcularImporteFinal();
		promocionTicket.setImporteTotalAhorro(BigDecimalUtil.redondear(ahorro).add(promocionTicket.getImporteTotalAhorro()));
	}

	@Override
	public boolean aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento) {
		// Usamos este método para aplicar puntos independientemente de si se aplica precio

		LineasAplicablesPromoBean lineasAplicables = SpringContext.getBean(LineasAplicablesPromoBean.class);
		lineasAplicables.setFiltroPromoExclusiva(false); // Haremos tratamiento especial

		LinkedList<LineaDocumentoPromocionable> lineas = new LinkedList<LineaDocumentoPromocionable>();
		for (LineaDocumentoPromocionable linea :  documento.getLineasDocumentoPromocionable()) {
			DetallePromocionPrecio detallePromocionPrecio = getDetallePromocionPrecio(linea.getCodArticulo(), linea.getDesglose1(), linea.getDesglose2());
			if (detallePromocionPrecio != null && detallePromocionPrecio.isAplicable()) {
				lineas.add(linea);
			}
		}
		lineasAplicables.setLineasAplicables(lineas);
		filtrarPromoExclusivaPromoPrecioPuntos(lineasAplicables, this);

		if (lineasAplicables.isEmpty()) {
			log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar por no existir líneas aplicables en el documento .");
			return false;
		}

		log.trace("aplicarPromocion() - " + this);

		IPromocionTicket promocionTicket = documento.getPromocion(getIdPromocion()); // Puede ya existir porque el precio ya se haya aplicado
		
		boolean existePromocion = promocionTicket != null;
		if (!existePromocion) {
			promocionTicket = createPromocionTicket(customerCoupon);
		}

		Integer puntosTotales = 0;
		for (LineaDocumentoPromocionable lineaTicket : lineasAplicables.getLineasAplicables()) {
			DetallePromocionPrecio detallePromocionPrecio = getDetallePromocionPrecio(lineaTicket.getCodArticulo(), lineaTicket.getDesglose1(), lineaTicket.getDesglose2());
			Integer puntos = detallePromocionPrecio.getPuntos();
			if (puntos == null || puntos.equals(0)) {
				log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar porque los puntos por euros configurados no lo permiten. Puntos: " + puntos);
				continue;
			}

			puntos = BigDecimalUtil.redondear(lineaTicket.getCantidad().multiply(new BigDecimal(puntos)), 1, BigDecimal.ROUND_DOWN).intValue();

			PromocionLineaTicket promocionLineaTicket = lineaTicket.getPromocion(getIdPromocion());
			if (promocionLineaTicket == null) {
				promocionLineaTicket = new PromocionLineaTicket(promocionTicket);
				lineaTicket.addPromocion(promocionLineaTicket);
			}
			promocionLineaTicket.setPuntos(puntos);
			puntosTotales += puntos;
		}

		// Aplicamos la promoción sobre el documento
		promocionTicket.setPuntos(puntosTotales);
		documento.addPuntos(puntosTotales);
		if (!existePromocion && puntosTotales > 0) {
			documento.addPromocion(promocionTicket);
		}

		return true;
	}

	/**
	 * Haremos un tratamiento especial de exclusivas en este tipo de promociones ya que no se debe filtrar cuando se
	 * aplica puntos y existe una promoción exclusiva que es la propia promo de precio que ya ha sido aplicada antes.
	 */
	protected void filtrarPromoExclusivaPromoPrecioPuntos(LineasAplicablesPromoBean lineasAplicables, PromocionPrecioDetalles promocionPrecioDetalles) {
		ListIterator<LineaDocumentoPromocionable> listIterator = lineasAplicables.getLineasAplicables().listIterator();
		while (listIterator.hasNext()) {
			LineaDocumentoPromocionable lineaAplicable = listIterator.next();
			for (IPromocionLineaTicket promocionLineaDocumentoPromocionable : lineaAplicable.getPromociones()) {
				if (promocionLineaDocumentoPromocionable.isExclusiva()) {
					if (!promocionLineaDocumentoPromocionable.getIdPromocion().equals(promocionPrecioDetalles.getIdPromocion())) {
						listIterator.remove();
						break;
					}
				}
			}
		}
	}

	@Override
	public boolean isAplicacionFinal() {
		return true; //Para aplicar los puntos
	}

	@Override
	public boolean isAplicacionPrecio() {
		return true;
	}
	
}
