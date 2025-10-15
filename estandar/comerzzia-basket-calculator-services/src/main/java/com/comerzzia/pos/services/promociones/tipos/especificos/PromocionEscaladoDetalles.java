package com.comerzzia.pos.services.promociones.tipos.especificos;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
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
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.escalado.DetallePromocionEscalado;
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.escalado.TramoEscalado;
import com.comerzzia.pos.services.ticket.lineas.LineaTicket;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaCandidataTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.config.SpringContext;

@Component
@Scope("prototype")
public class PromocionEscaladoDetalles extends PromocionLineaDetalles {

	private Logger log = Logger.getLogger(PromocionLineaDetalles.class);

	@Override
	public DetallePromocion createDetallePromocion(PromocionDetalleBean detalleBean) throws XMLDocumentException {
		return new DetallePromocionEscalado(this, detalleBean);
	}

	public DetallePromocionEscalado getDetallePromocionEscalado(String codArticulo, String desglose1, String desglose2) {
		PromocionDetalleKey key = new PromocionDetalleKey();
		key.setCodArticulo(codArticulo);
		key.setDesglose1(desglose1);
		key.setDesglose2(desglose2);
		
		DetallePromocionEscalado detalle = (DetallePromocionEscalado) getDetalles().get(key);
		if (detalle != null && !detalle.isXmlLeido()) {
			try {
				detalle.leerXml();
			}
			catch (XMLDocumentException e) {
				log.error("createDetallePromocion() - Ha habido un error al leer el XML del detalle de la promoción: " + e.getMessage(), e);
				detalle = null;
			}
		}
		return detalle;
	}

	@Override
	public void analizarLineasAplicables(DocumentoPromocionable<IPromocionTicket> documento) {
		LineasAplicablesPromoBean lineasAplicables = new LineasAplicablesPromoBean();
		lineasAplicables.setFiltroPromoExclusiva(true);

		LinkedList<LineaDocumentoPromocionable> lineas = new LinkedList<LineaDocumentoPromocionable>();
		for (LineaDocumentoPromocionable linea : documento.getLineasDocumentoPromocionable()) {
			DetallePromocionEscalado detallePromocion = getDetallePromocionEscalado(linea.getCodArticulo(), linea.getDesglose1(), linea.getDesglose2());
			BigDecimal cantidadArticuloTicket = getCantidadArticuloTicket(documento, linea.getCodArticulo());
			
			if(detallePromocion != null) {
				if(detallePromocion.getTramos() != null) {
					for(TramoEscalado tramoEscalado : detallePromocion.getTramos()) {
						if(comprobarTramo(cantidadArticuloTicket, tramoEscalado)) {
							lineas.add(linea);
							break;
						}
					}
				}
			}
		}
		lineasAplicables.setLineasAplicables(lineas);

		PromocionLineaCandidataTicket candidata = new PromocionLineaCandidataTicket(lineasAplicables, lineasAplicables, this);
		for (LineaDocumentoPromocionable linea : lineasAplicables.getLineasAplicables()) {
			linea.addPromocionAplicable(candidata);
		}
	}

	protected boolean comprobarTramo(BigDecimal cantidadArticuloTicket, TramoEscalado tramoEscalado) {
	    return BigDecimalUtil.isMayorOrIgual(cantidadArticuloTicket, tramoEscalado.getCantidadDesde()) && BigDecimalUtil.isMenorOrIgual(cantidadArticuloTicket, tramoEscalado.getCantidadHasta());
    }

	protected BigDecimal getCantidadArticuloTicket(DocumentoPromocionable<IPromocionTicket> documento, String codArticulo) {
		BigDecimal cantidad = BigDecimal.ZERO;

		for (LineaDocumentoPromocionable linea : documento.getLineasDocumentoPromocionable()) {
			if (linea.getCodArticulo().equals(codArticulo)) {
				cantidad = cantidad.add(linea.getCantidad());
			}
		}

		return cantidad;
	}

	protected BigDecimal getCantidadArticuloTicket(LineasAplicablesPromoBean lineasAplicables, String codArticulo) {
		BigDecimal cantidad = BigDecimal.ZERO;

		for (LineaDocumentoPromocionable linea : lineasAplicables.getLineasAplicables()) {
			if (linea.getCodArticulo().equals(codArticulo)) {
				cantidad = cantidad.add(linea.getCantidad());
			}
		}

		return cantidad;
    }

	@Override
	public BigDecimal calcularPromocion(LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables) {
		BigDecimal importeTotalAhorro = BigDecimal.ZERO;

		for (int i = 0; i < lineasAplicables.getLineasAplicables().size();) {
			LineaDocumentoPromocionable linea = lineasAplicables.getLineasAplicables().get(i);

			// Obtenemos detalle aplicable para este artículo (sólo puede haber uno en cada promoción unitaria)
			DetallePromocionEscalado detallePromocion = getDetallePromocionEscalado(linea.getCodArticulo(), linea.getDesglose1(), linea.getDesglose2());
			
			if(detallePromocion != null) {
				setTextoPromocion(detallePromocion.getTextoPromocion());
	
				// Comprobamos si la promoción puede seguir aplicándose en esta línea
				if (BigDecimalUtil.isMayorOrIgual(linea.getCantidadPromocionCandidata(), linea.getCantidad())) {
					i++;
					continue;
				}
	
				BigDecimal aumento = BigDecimal.ONE;
				BigDecimal cantidadRestante = linea.getCantidad().subtract(linea.getCantidadPromocionCandidata());
				if (BigDecimalUtil.isMenor(cantidadRestante, BigDecimal.ONE)) {
					aumento = cantidadRestante;
				}
				LineaTicket lineaConPromocion = SpringContext.getBean(LineaTicket.class);
				try {
					BeanUtils.copyProperties(lineaConPromocion, linea);
				}
				catch (Exception e) {
					log.error("calcularPromocion() - Ha habido un error al copiar los detalles de la línea: " + e.getMessage(), e);
				}
				
				BigDecimal cantidadArticuloTicket = getCantidadArticuloTicket(lineasAplicables, linea.getCodArticulo());
				
				BigDecimal valor = null;
				for(TramoEscalado tramoEscalado : detallePromocion.getTramos()) {
					if(comprobarTramo(cantidadArticuloTicket, tramoEscalado)) {
						valor = tramoEscalado.getValor();
						break;
					}
				}
				
				if(valor != null) {
					if(detallePromocion.isTipoDtoNuevoPrecio() || detallePromocion.isTipoDtoDescuento()) {
						if(detallePromocion.isTipoDtoNuevoPrecio()) {
							lineaConPromocion.setPrecioTotalSinDto(valor);
						}
						else {
							lineaConPromocion.setDescuentoManual(valor);
						}
						lineaConPromocion.recalcularImporteFinal();
						importeTotalAhorro = importeTotalAhorro.add(((LineaTicket) linea).getPrecioTotalSinDto().subtract(lineaConPromocion.getPrecioTotalSinDto()));
					}
					else if(detallePromocion.isTipoDtoImporteDto()) {
						importeTotalAhorro = importeTotalAhorro.add(valor);
					}
		
					linea.addCantidadPromocionCandidata(aumento);
				}
			}
		}
		return importeTotalAhorro;
	}

	@Override
	public void aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento, LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables) {
		IPromocionTicket promocionTicket = documento.getPromocion(getIdPromocion());
		if (promocionTicket == null) {
			promocionTicket = createPromocionTicket(customerCoupon);
		}

		int cantidadAplicada = 0;
		BigDecimal importeTotalAhorro = BigDecimal.ZERO;
		for (int i = 0; i < lineasAplicables.getLineasAplicables().size();) {
			LineaDocumentoPromocionable linea = lineasAplicables.getLineasAplicables().get(i);

			// Obtenemos detalle aplicable para este artículo (sólo puede haber uno en cada promoción unitaria)
			DetallePromocionEscalado detallePromocion = getDetallePromocionEscalado(linea.getCodArticulo(), linea.getDesglose1(), linea.getDesglose2());
			
			if(detallePromocion != null) {
				BigDecimal cantidadArticuloTicket = getCantidadArticuloTicket(lineasAplicables, linea.getCodArticulo());
				
				BigDecimal valor = null;
				for(TramoEscalado tramoEscalado : detallePromocion.getTramos()) {
					if(comprobarTramo(cantidadArticuloTicket, tramoEscalado)) {
						valor = tramoEscalado.getValor();
						break;
					}
				}
	
				// Comprobamos si la línea ya tiene una promoción y si el precio de la promoción es menor que el de la de
				// escalado
				if(detallePromocion.isTipoDtoNuevoPrecio()) {
					BigDecimal precioPromocionTotalSinDto = ((LineaTicket) linea).getPrecioPromocionTotalSinDto();
					if (precioPromocionTotalSinDto != null && BigDecimalUtil.isMenor(precioPromocionTotalSinDto, valor)) {
						i++;
						continue;
					}
				}
	
				// Comprobamos si la promoción puede seguir aplicándose en esta línea según la cantidad
				if (BigDecimalUtil.isMayorOrIgual(linea.getCantidadPromocion(), linea.getCantidad())) {
					linea.recalcularImporteFinal();
					i++;
					continue;
				}
	
				BigDecimal importeTotalAhorroLinea = calcularImporteTotalAhorroLinea(linea, detallePromocion, valor);
				importeTotalAhorro = importeTotalAhorro.add(importeTotalAhorroLinea);
	
				PromocionLineaTicket promocionLinea = linea.getPromocion(promocionTicket.getIdPromocion());
				if (promocionLinea == null) {
					promocionLinea = new PromocionLineaTicket(promocionTicket);
					linea.addPromocion(promocionLinea);
				}
	
				promocionLinea.setImporteTotalDto(importeTotalAhorroLinea);
				promocionLinea.addCantidadPromocion(linea.getCantidad());
				linea.addCantidadPromocion(linea.getCantidad());
				linea.recalcularImporteFinal();
				cantidadAplicada++;
			}
		}

		if (cantidadAplicada > 0) {
			promocionTicket.setImporteTotalAhorro(importeTotalAhorro);
			documento.addPromocion(promocionTicket);
		}
	}

	protected BigDecimal calcularImporteTotalAhorroLinea(LineaDocumentoPromocionable linea, DetallePromocionEscalado detallePromocion, BigDecimal valor) {
		LineaTicket lineaTicket = (LineaTicket) linea;
		
		BigDecimal importeTotalAhorro = BigDecimal.ZERO;
			
		if(detallePromocion.isTipoDtoDescuento()) {
			importeTotalAhorro = BigDecimalUtil.porcentaje(lineaTicket.getImporteTotalSinDto(), valor);
		}
		else if(detallePromocion.isTipoDtoImporteDto()) {
			importeTotalAhorro = valor;
		}
		else if(detallePromocion.isTipoDtoNuevoPrecio()) {
			importeTotalAhorro = (lineaTicket.getPrecioTotalSinDto().subtract(valor)).multiply(lineaTicket.getCantidad());
		}
			
	    return importeTotalAhorro;
    }

}
