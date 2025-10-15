/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND
 * INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED,
 * DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST,
 * TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK
 * WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE
 * AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */

package com.comerzzia.pos.services.promociones.filtro;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;
import com.comerzzia.pos.services.promociones.tipos.componente.CondicionPrincipalPromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.GrupoComponentePromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.IComponentePromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.ItemComponentePromoBean;
import com.comerzzia.pos.services.ticket.lineas.LineaTicketItemData;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.config.SpringContext;

@Component
@Scope("prototype")
public class FiltroLineasPromocion {

	protected static final Logger log = Logger.getLogger(FiltroLineasPromocion.class.getName());

	protected DocumentoPromocionable<IPromocionTicket> documento;
	protected boolean filtrarPromoExclusivas;

	public FiltroLineasPromocion() {
		super();
	}

	public FiltroLineasPromocion(DocumentoPromocionable<IPromocionTicket> documento) {
		this.documento = documento;
		filtrarPromoExclusivas = false;
	}

	public void setDocumento(DocumentoPromocionable<IPromocionTicket> documento) {
		this.documento = documento;
	}

	public void setFiltrarPromoExclusivas(boolean filtrarPromoExclusivas) {
		this.filtrarPromoExclusivas = filtrarPromoExclusivas;
	}

	public LineasAplicablesPromoBean getLineasAplicablesGrupo(GrupoComponentePromoBean filtro) {
		return getLineasAplicablesGrupo(filtro, false);
	}

	public LineasAplicablesPromoBean getLineasAplicablesGrupo(GrupoComponentePromoBean filtro, boolean filtroLineasCantidadDecimales) {
		LineasAplicablesPromoBean aplicables = createLineasAplicables();
		aplicables.setFiltroLineasCantidadDecimales(filtroLineasCantidadDecimales);

		// Si el filtro no define reglas ni grupos, asumimos que la promoción puede aplicarse sobre todas las líneas del
		// documento
		if (filtro.isVacio()) {
			aplicables.setLineasAplicables(documento.getLineasDocumentoPromocionable());
		}
		else { // Si tenemos reglas y grupos, obtenemos las líneas aplicables
			List<LineaDocumentoPromocionable> lineasAplicables = getLineasAplicablesGrupo(filtro, documento.getLineasDocumentoPromocionable());
			aplicables.setLineasAplicables(lineasAplicables);
		}

		return aplicables;
	}

	public LineasAplicablesPromoBean getLineasAplicablesRegla(ItemComponentePromoBean filtro) {
		LineasAplicablesPromoBean aplicables = createLineasAplicables();
		if (filtro == null) { // Regla vacía
			aplicables.setLineasAplicables(documento.getLineasDocumentoPromocionable());
		}
		else {
			List<LineaDocumentoPromocionable> lineasAplicables = getLineasAplicablesRegla(filtro, documento.getLineasDocumentoPromocionable());
			aplicables.setLineasAplicables(lineasAplicables);
		}
		return aplicables;
	}

	protected LineasAplicablesPromoBean createLineasAplicables() {
		LineasAplicablesPromoBean aplicables = SpringContext.getBean(LineasAplicablesPromoBean.class);
		aplicables.setFiltroPromoExclusiva(filtrarPromoExclusivas);
		return aplicables;
	}

	protected List<LineaDocumentoPromocionable> getLineasAplicablesGrupo(GrupoComponentePromoBean grupo, List<LineaDocumentoPromocionable> lineasCandidatas) {
		Set<LineaDocumentoPromocionable> totalLineasAplicables = new HashSet<>();

		List<LineaDocumentoPromocionable> lineasAplicables = new ArrayList<>();
		for (ItemComponentePromoBean regla : grupo.getReglas()) {
			lineasAplicables = getLineasAplicablesRegla(regla, lineasCandidatas);
			if (grupo.isAndNexo() && lineasAplicables.isEmpty()) {
				return lineasAplicables; // no continuamos procesando, porque en un AND ya hay una condición que no
										 // cumple ninguna línea
			}
			else if (grupo.isAndNexo()) {
				lineasCandidatas = lineasAplicables;
			}
			else { // Si es un OR, añadimos todas las líneas al conjunto general
				totalLineasAplicables.addAll(lineasAplicables);
			}
		}
		for (GrupoComponentePromoBean subgrupo : grupo.getGrupos()) {
			lineasAplicables = getLineasAplicablesGrupo(subgrupo, lineasCandidatas);
			if (grupo.isAndNexo() && lineasAplicables.isEmpty()) {
				return lineasAplicables; // no continuamos procesando, porque en un AND ya hay una condición que no
										 // cumple ninguna línea
			}
			else if (grupo.isAndNexo()) {
				lineasCandidatas = lineasAplicables;
			}
			else { // Si es un OR, añadimos todas las líneas al conjunto general
				totalLineasAplicables.addAll(lineasAplicables);
			}
		}
		// Si el filtro es AND devolvemos todas las líneas que hayan quedado como aplicables tras pasar todos los
		// filtros condición
		if (grupo.isAndNexo()) {
			return lineasAplicables;
		}
		else { // Si es un OR, devolvemos todas las líneas del conjunto total
			lineasAplicables.clear();
			lineasAplicables.addAll(totalLineasAplicables);
			return lineasAplicables;
		}

	}

	protected List<LineaDocumentoPromocionable> getLineasAplicablesRegla(ItemComponentePromoBean regla, List<LineaDocumentoPromocionable> lineasTicket) {
		List<LineaDocumentoPromocionable> aplicables = new ArrayList<>();
		for (LineaDocumentoPromocionable linea : lineasTicket) {
			if (filtrarPromoExclusivas && linea.tienePromocionLineaExclusiva()) {
				continue; // Si la línea ya tiene una promoción exclusiva y tenemos configurado que las filtremos, la
						  // ignoramos
			}
			if (aplicaRegla(regla, linea)) {
				aplicables.add(linea);
			}
		}
		return aplicables;
	}

	protected boolean aplicaRegla(ItemComponentePromoBean regla, LineaDocumentoPromocionable linea) {
		LineaTicketItemData articuloLinea = linea.getArticulo();
		switch (regla.getItem()) {
			case ItemComponentePromoBean.ITEM_ARTICULOS:
				return (regla.isOperacionEqual() && Objects.equals(articuloLinea.getCodArticulo(), regla.getValor()) || regla.isOperacionNoEqual()
				        && !Objects.equals(articuloLinea.getCodArticulo(), regla.getValor()));
			case ItemComponentePromoBean.ITEM_PROVEEDOR:
				return (regla.isOperacionEqual() && Objects.equals(articuloLinea.getCodProveedor(), regla.getValor()) || regla.isOperacionNoEqual()
				        && !Objects.equals(articuloLinea.getCodProveedor(), regla.getValor()));
			case ItemComponentePromoBean.ITEM_MARCA:
				return (regla.isOperacionEqual() && Objects.equals(articuloLinea.getCodMarca(), regla.getValor()) || regla.isOperacionNoEqual()
				        && !Objects.equals(articuloLinea.getCodMarca(), regla.getValor()));
			case ItemComponentePromoBean.ITEM_SECCION:
				return (regla.isOperacionEqual() && Objects.equals(articuloLinea.getCodseccion(), regla.getValor()) || regla.isOperacionNoEqual()
				        && !Objects.equals(articuloLinea.getCodseccion(), regla.getValor()));
			case ItemComponentePromoBean.ITEM_FAMILIAS:
				return (regla.isOperacionEqual() && Objects.equals(articuloLinea.getCodFamilia(), regla.getValor()) || regla.isOperacionNoEqual()
				        && !Objects.equals(articuloLinea.getCodFamilia(), regla.getValor()));
			case ItemComponentePromoBean.ITEM_CATEGORIZACIONES:
				return (regla.isOperacionEqual() && Objects.equals(articuloLinea.getCodCategorizacion(), regla.getValor()) || regla.isOperacionNoEqual()
				        && !Objects.equals(articuloLinea.getCodCategorizacion(), regla.getValor()) || regla.isOperacionBeginsWith() && articuloLinea.getCodCategorizacion() != null
				        && articuloLinea.getCodCategorizacion().startsWith(regla.getValor()) || regla.isOperacionNotBeginWith() && articuloLinea.getCodCategorizacion() != null
				        && !articuloLinea.getCodCategorizacion().startsWith(regla.getValor()));
			case ItemComponentePromoBean.ITEM_PRECIO:
				BigDecimal precioDesde = regla.getValorAsBigDecimal();
				BigDecimal precioHasta = regla.getValorAdicionalAsBigDecimal();
				BigDecimal precioArticulo = linea.getPrecioAplicacionPromocion();
				return BigDecimalUtil.isMayorOrIgual(precioArticulo, precioDesde) && BigDecimalUtil.isMenorOrIgual(precioArticulo, precioHasta);
			case ItemComponentePromoBean.ITEM_ETIQUETAS:
				return (regla.isOperacionEqual() && articuloLinea.getEtiquetas().contains(regla.getValor()) || regla.isOperacionNoEqual()
				        && !articuloLinea.getEtiquetas().contains(regla.getValor()));
			case ItemComponentePromoBean.ITEM_CANTIDAD_MULT:
				return false;
			case ItemComponentePromoBean.ITEM_IMPORTE_MULT:
				return false;
			case ItemComponentePromoBean.ITEM_CANTIDAD:
				return false;
			case ItemComponentePromoBean.ITEM_IMPORTE:
				return false;
			default:
				log.warn("aplicaRegla() - Se está intentando aplicar una regla no definida: " + regla.getItem());
				return false;
		}
	}

//	protected boolean containsEtiqueta(List<EtiquetaArticuloBean> etiquetas, String uidEtiqueta) {
//		for (EtiquetaArticuloBean etiquetaArticuloBean : etiquetas) {
//			if (etiquetaArticuloBean.getUidEtiqueta().equals(uidEtiqueta)) {
//				return true;
//			}
//		}
//		return false;
//	}

	public LineasAplicablesPromoBean getNumCombosCondicion(CondicionPrincipalPromoBean condiciones) {

		LineasAplicablesPromoBean lineasAplicables = createLineasAplicables();
		LineasAplicablesPromoBean lineasUltimaRegla = null;
		HashSet<LineaDocumentoPromocionable> lineasAcumuladas = new HashSet<>();
		Integer numCombos = -1;
		Integer numCombosUltimaRegla = 0;

		if (condiciones.isVacio()) {
			lineasAplicables.setLineasAplicables(documento.getLineasDocumentoPromocionable());
			lineasAplicables.setNumCombos(1);
			return lineasAplicables;
		}

		/*
		 * Será true cuando el numCombos calculado se deba a una regla de cantidad/importe de aplicación única.
		 */
		boolean numCombosCalculadosPorReglaAplicacionUnica = false;
		for (IComponentePromoBean condicion : condiciones.getReglas()) {
			if (condicion.isTipoGrupoReglas()) {
				GrupoComponentePromoBean grupo = (GrupoComponentePromoBean) condicion;
				lineasUltimaRegla = getLineasAplicablesGrupo(grupo);
				numCombosUltimaRegla = 1;
				/* Es una regla */
			}
			else {
				/* Miramos si es regla de cantidad o importe sobre última condición. */
				ItemComponentePromoBean regla = (ItemComponentePromoBean) condicion;
				if ((regla.isReglaCantidad() || regla.isReglaImporte() || regla.isReglaCantidadMultiple() || regla.isReglaImporteMultiple())) {

					if (lineasUltimaRegla == null) {
						/*
						 * Se quiere hacer un filtro de cantidad o importe sobre todas las líneas del documento.
						 */
						lineasUltimaRegla = getLineasAplicablesRegla(null);
						lineasAcumuladas.addAll(lineasUltimaRegla.getLineasAplicables());
						numCombosUltimaRegla = 1;
					}
					if (regla.isReglaCantidad()) {
						/* Si la regla es única, se comprueba que se cumple y si es así se marca 1 combo. */
						/* ========== MENOR ========== */
						if (regla.getOperacion().equals("less")) {
							if (BigDecimalUtil.isMenor(lineasUltimaRegla.getCantidadArticulos(), regla.getValorAsBigDecimal())) {
								numCombosUltimaRegla = 1;
							}
							else {
								numCombosUltimaRegla = 0;
							}
							/* ========== MENOR O IGUAL ========== */
						}
						else if (regla.getOperacion().equals("less_or_equal")) {
							if (BigDecimalUtil.isMenorOrIgual(lineasUltimaRegla.getCantidadArticulos(), regla.getValorAsBigDecimal())) {
								numCombosUltimaRegla = 1;
							}
							else {
								numCombosUltimaRegla = 0;
							}
							/* ========== IGUAL ========== */
						}
						else if (regla.getOperacion().equals("equal")) {
							if (BigDecimalUtil.isIgual(lineasUltimaRegla.getCantidadArticulos(), regla.getValorAsBigDecimal())) {
								numCombosUltimaRegla = 1;
							}
							else {
								numCombosUltimaRegla = 0;
							}
							/* ========== MAYOR O IGUAL ========== */
						}
						else if (regla.getOperacion().equals("greater_or_equal")) {
							if (BigDecimalUtil.isMayorOrIgual(lineasUltimaRegla.getCantidadArticulos(), regla.getValorAsBigDecimal())) {
								numCombosUltimaRegla = 1;
							}
							else {
								numCombosUltimaRegla = 0;
							}
							/* ========== MAYOR ========== */
						}
						else if (regla.getOperacion().equals("greater")) {
							if (BigDecimalUtil.isMayor(lineasUltimaRegla.getCantidadArticulos(), regla.getValorAsBigDecimal())) {
								numCombosUltimaRegla = 1;
							}
							else {
								numCombosUltimaRegla = 0;
							}
						}
						else {
							numCombosUltimaRegla = 0;
						}
					}
					else if (regla.isReglaImporte()) {
						/* Si la regla es única, se comprueba que se cumple y si es así se marca 1 combo. */
						if (compruebaImporte(lineasUltimaRegla.getImporteLineasConDto(), regla)) {
							numCombosUltimaRegla = 1;
						}
						else {
							numCombosUltimaRegla = 0;
						}
					}
					else if (regla.isReglaCantidadMultiple()) {
						/* Si la regla es múltiple se calculan la cantidad de combos. */
						/* ========== MENOR ========== */
						if (regla.getOperacion().equals("less")) {
							if (BigDecimalUtil.isMenor(lineasUltimaRegla.getCantidadArticulos(), regla.getValorAsBigDecimal())) {
								numCombosUltimaRegla = 1;
							}
							else {
								numCombosUltimaRegla = 0;
							}
							/* ========== MENOR O IGUAL ========== */
						}
						else if (regla.getOperacion().equals("less_or_equal")) {
							if (BigDecimalUtil.isMenorOrIgual(lineasUltimaRegla.getCantidadArticulos(), regla.getValorAsBigDecimal())) {
								numCombosUltimaRegla = 1;
							}
							else {
								numCombosUltimaRegla = 0;
							}
							/* ========== IGUAL ========== */
						}
						else if (regla.getOperacion().equals("equal")) {
							if (BigDecimalUtil.isIgual(lineasUltimaRegla.getCantidadArticulos(), regla.getValorAsBigDecimal())) {
								numCombosUltimaRegla = 1;
							}
							else {
								numCombosUltimaRegla = 0;
							}
							/* ========== MAYOR ========== */
						}
						else if (regla.getOperacion().equals("greater")) {
							if (BigDecimalUtil.isMayor(lineasUltimaRegla.getCantidadArticulos(), regla.getValorAsBigDecimal())) {
								if(BigDecimalUtil.isIgualACero(regla.getValorAsBigDecimal())) {
									numCombosUltimaRegla = lineasUltimaRegla.getCantidadArticulos().intValue();
								}
								else {
									numCombosUltimaRegla = lineasUltimaRegla.getCantidadArticulos().divide(regla.getValorAsBigDecimal(), 1, RoundingMode.DOWN).intValue();
								}
							}
							else {
								numCombosUltimaRegla = 0;
							}
							/* ========== MAYOR O IGUAL ========== */
						}
						else if (regla.getOperacion().equals("greater_or_equal")) {
							if (BigDecimalUtil.isMayorOrIgual(lineasUltimaRegla.getCantidadArticulos(), regla.getValorAsBigDecimal())) {
								if(BigDecimalUtil.isIgualACero(regla.getValorAsBigDecimal())) {
									numCombosUltimaRegla = Math.max(1, lineasUltimaRegla.getCantidadArticulos().intValue());
								}
								else {
									numCombosUltimaRegla = lineasUltimaRegla.getCantidadArticulos().divide(regla.getValorAsBigDecimal(), 1, RoundingMode.DOWN).intValue();
								}
							}
							else {
								numCombosUltimaRegla = 0;
							}
						}
						else {
							numCombosUltimaRegla = 0;
						}
					}
					else if (regla.isReglaImporteMultiple()) {
						/* Si la regla es única, se comprueba que se cumple y si es así se marca 1 combo. */
						if (compruebaImporte(lineasUltimaRegla.getImporteLineasConDto(), regla)) {
							if (regla.getOperacion().equals(ItemComponentePromoBean.OP_GREATER) || regla.getOperacion().equals(ItemComponentePromoBean.OP_GREATER_EQ)) {
								if(BigDecimalUtil.isIgualACero(regla.getValorAsBigDecimal())) {
									numCombosUltimaRegla = Math.max(1, lineasUltimaRegla.getImporteLineasConDto().intValue());
								}
								else {
									numCombosUltimaRegla = lineasUltimaRegla.getImporteLineasConDto().divide(regla.getValorAsBigDecimal(), 1, RoundingMode.DOWN).intValue();
								}
							}
							else {
								numCombosUltimaRegla = 1;
							}

						}
						else {
							numCombosUltimaRegla = 0;
						}
					}

					/* Primer combo */
					if (numCombos < 0) {
						numCombos = numCombosUltimaRegla;
						if (!regla.isReglaMultiple()) {
							numCombosCalculadosPorReglaAplicacionUnica = true;
						}
					}
					else if (condiciones.isAndNexo() && numCombosUltimaRegla == 0) {
						numCombos = numCombosUltimaRegla;
					}
					else if (condiciones.isAndNexo() && numCombosUltimaRegla < numCombos && regla.isReglaMultiple()) {
						numCombos = numCombosUltimaRegla;
						numCombosCalculadosPorReglaAplicacionUnica = false;
						/*
						 * Cuando los numCombos calculados anteriormente vienen por una regla de aplicación única deben
						 * ser recalculados si la regla actual es múltiple aunque se trate de un AND y la nueva regla
						 * tenga un numCombos mayor. Esto se hace para permitir condiciones con un mínimo en regla única
						 * pero que se cumpla por cada regla múltiple.
						 */
					}
					else if (condiciones.isAndNexo() && numCombosCalculadosPorReglaAplicacionUnica && regla.isReglaMultiple()) {
						numCombos = numCombosUltimaRegla;
						numCombosCalculadosPorReglaAplicacionUnica = false;
					}
					else if (condiciones.isOrNexo() && numCombosUltimaRegla > numCombos) {
						numCombos = numCombosUltimaRegla;
					}
					lineasUltimaRegla = null;
				}
				else {
					lineasUltimaRegla = getLineasAplicablesRegla(regla);
					/* Condición AND y regla que no devuelve resultados. */
					if (lineasUltimaRegla.isEmpty() && condiciones.isAndNexo()) {
						return lineasAplicables;
					}
					numCombosUltimaRegla = 1;
				}
			}
			
			/*
			 * Se ha evaluado un grupo o una regla que no es regla de cantidad o importe sobre última condición y no hay lineas que lo cumplan
			 */
			if(lineasUltimaRegla!=null && lineasUltimaRegla.isEmpty() && condiciones.isAndNexo()) {
				numCombos = 0;
			}
			
			/* Será porque tengo condición AND en raíz. No merece la pena seguir analizando */
			if (numCombos == 0 && condiciones.isAndNexo()) {
				return lineasAplicables;
			}
			/*
			 * Será null después de una regla de cantidad o importe sobre la anterior. En este caso no es necesario
			 * añadir nuevas líneas acumuladas.
			 */
			if (lineasUltimaRegla != null) {
				lineasAcumuladas.addAll(lineasUltimaRegla.getLineasAplicables());
			}
		}
		/* Será porque es un OR y no ha conseguido llegar a aplicar ninguna regla. */
		if (numCombos == 0) {
			return lineasAplicables;
		}

		List<LineaDocumentoPromocionable> lineas = new ArrayList<>();
		lineas.addAll(lineasAcumuladas);
		lineasAplicables.setLineasAplicables(lineas);
		/* No se habrá llegado a calcular combos. Se aplicará por tanto sólo 1. */
		if (numCombos < 1) {
			numCombos = 1;
		}
		lineasAplicables.setNumCombos(numCombos);

		return lineasAplicables;

	}

	protected boolean compruebaImporte(BigDecimal cantidad, ItemComponentePromoBean itemCondicion) {

		switch (itemCondicion.getOperacion()) {
			case ItemComponentePromoBean.OP_EQUAL:
				return cantidad.compareTo(itemCondicion.getValorAsBigDecimal()) == 0;
			case ItemComponentePromoBean.OP_GREATER:
				return cantidad.compareTo(itemCondicion.getValorAsBigDecimal()) > 0;
			case ItemComponentePromoBean.OP_GREATER_EQ:
				return cantidad.compareTo(itemCondicion.getValorAsBigDecimal()) >= 0;
			case ItemComponentePromoBean.OP_LESS:
				return cantidad.compareTo(itemCondicion.getValorAsBigDecimal()) < 0;
			case ItemComponentePromoBean.OP_LESS_EQ:
				return cantidad.compareTo(itemCondicion.getValorAsBigDecimal()) <= 0;
			default:
				log.warn("compruebaImporte() - Se está intentando comparar una regla " + "con un operador no definido: " + itemCondicion.getOperacion());
				return false;
		}

	}

}
