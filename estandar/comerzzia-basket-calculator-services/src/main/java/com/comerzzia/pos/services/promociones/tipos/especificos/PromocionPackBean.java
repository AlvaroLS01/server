package com.comerzzia.pos.services.promociones.tipos.especificos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;
import com.comerzzia.pos.services.promociones.filtro.FiltroLineasPackPromocion;
import com.comerzzia.pos.services.promociones.filtro.LineasAplicablesPromoBean;
import com.comerzzia.pos.services.promociones.tipos.PromocionLinea;
import com.comerzzia.pos.services.promociones.tipos.componente.CondicionPrincipalPromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.GrupoComponentePromoBean;
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.pack.AgrupacionPack;
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.pack.CombinacionAgrupacionPack;
import com.comerzzia.pos.services.ticket.lineas.LineaTicket;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaCandidataTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.config.SpringContext;
import com.comerzzia.pos.util.math.Combinaciones;

@Component
@Scope("prototype")
public class PromocionPackBean extends PromocionLinea {

	protected static final Logger log = Logger.getLogger(PromocionPackBean.class);

	protected CondicionPrincipalPromoBean condiciones;
	protected GrupoComponentePromoBean aplicacion;

	protected BigDecimal cantidadMaximaAplicacion;
	protected BigDecimal precioPack;

	/**
	 * Lista de agrupaciones resultantes por regla. Se guardan como atributo de la clase para que se pueda utilizar en
	 * el aplicarPromocion, ya que la estructura del motor de promociones no permite calcular de nuevo las agrupaciones
	 * en ese método.
	 */
	protected List<AgrupacionPack> agrupacionesPack;

	@Override
	public void analizarLineasAplicables(DocumentoPromocionable<IPromocionTicket> documento) {
		log.trace("analizarLineasAplicables() - " + this);

		FiltroLineasPackPromocion filtroLineas = createFiltroLineasPromocionPack(documento);
		LineasAplicablesPromoBean lineasCondicion = filtroLineas.getNumCombosCondicion(condiciones);

		// Obtenemos las líneas de condición según el filtro configurado
		if (lineasCondicion.isEmpty() || lineasCondicion.getNumCombos() == 0) {
			log.trace(this + " analizarLineasAplicables() - La promoción no se puede aplicar por no existir las líneas que cumplan las condiciones en el documento .");
			return;
		}

		// Obtenemos las líneas aplicables según el filtro configurado
		filtroLineas.setFiltrarPromoExclusivas(true);

		agrupacionesPack = filtroLineas.getLineasAplicablesPack(getAplicacion());
		if (agrupacionesPack.isEmpty() || agrupacionesPack.size() < getAplicacion().getGrupos().size()) {
			return;
		}

		// Marcamos líneas con la promoción candidata
		LineasAplicablesPromoBean aplicables = SpringContext.getBean(LineasAplicablesPromoBean.class);
		List<LineaDocumentoPromocionable> lineasAplicablesAgrup = new ArrayList<LineaDocumentoPromocionable>();

		// Controlamos que solo metamos una vez cada línea en las aplicables de la promoción para que al meter las
		// promociones en la línea
		// se meta esta promoción varias veces y salga como conflicto de si misma.
		List<Integer> lineasIncluidas = new ArrayList<Integer>();
		for (AgrupacionPack lineasRegla : agrupacionesPack) {
			for (LineaDocumentoPromocionable linea : lineasRegla.getLineasAplicables()) {
				if (!lineasIncluidas.contains(linea.getIdLinea())) {
					lineasIncluidas.add(linea.getIdLinea());
					lineasAplicablesAgrup.add(linea);
				}
			}
		}
		aplicables.setLineasAplicables(lineasAplicablesAgrup);

		PromocionLineaCandidataTicket candidata = new PromocionLineaCandidataTicket(aplicables, lineasCondicion, this);
		for (LineaDocumentoPromocionable linea : aplicables.getLineasAplicables()) {
			linea.addPromocionAplicable(candidata);
		}
	}
	
	@Override
	public BigDecimal calcularPromocion(LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables) {
		log.trace("calcularPromocion() - " + this);
		
		if (agrupacionesPack.isEmpty() || agrupacionesPack.size() < getAplicacion().getGrupos().size()) {
			return BigDecimal.ZERO;
		}
		
		int numCombos = getNumCombos(agrupacionesPack);

		List<List<CombinacionAgrupacionPack>> combinacionesAgrupaciones = getCombinacionesAgrupaciones(agrupacionesPack);
		Set<List<CombinacionAgrupacionPack>> combinaciones = Combinaciones.getCombinaciones(combinacionesAgrupaciones);
		
		eliminarCombinacionesConRepeticion(combinaciones);
		eliminarCombinacionesConPromocionCandidata(combinaciones);
		
		BigDecimal importeTotalAhorro = BigDecimal.ZERO;
		
		for(int i = 0 ; i < numCombos ; i++) {
			List<CombinacionAgrupacionPack> mejorCombinacion = obtenerMejorCombinacion(combinaciones, true);
			
			if(mejorCombinacion != null) {
				BigDecimal descuento = getDescuentoCombinacion(mejorCombinacion);
				importeTotalAhorro = importeTotalAhorro.add(descuento);
				
				// Añadimos la cantidad de promoción de aplicación para que funcionen correctamenten las decisiones en los conflictos
				for(CombinacionAgrupacionPack combinacion : mejorCombinacion) {
					BigDecimal cantidadPromocionDisponible = combinacion.getAgrupacion().getCantidad();
					for(LineaDocumentoPromocionable linea : combinacion.getLineas()) {
						if (BigDecimalUtil.isMayorACero(cantidadPromocionDisponible)) {
							BigDecimal cantidadAplicacionLinea = cantidadPromocionDisponible.min(linea.getCantidad().subtract(linea.getCantidadPromocionCandidata()));
							linea.addCantidadPromocionCandidata(cantidadAplicacionLinea);
							cantidadPromocionDisponible = cantidadPromocionDisponible.subtract(cantidadAplicacionLinea);
						}
					}
				}
			}
		}
		
		return importeTotalAhorro;
	}

	@Override
	public void aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento, LineasAplicablesPromoBean lineasCondicion, LineasAplicablesPromoBean lineasAplicables) {
		log.trace("aplicarPromocion() - " + this);

		List<AgrupacionPack> agrupacionesPack = getAgrupacionesAplicables(documento);

		if (agrupacionesPack.isEmpty() || agrupacionesPack.size() < getAplicacion().getGrupos().size()) {
			return;
		}

		int numCombos = getNumCombos(agrupacionesPack);

		List<List<CombinacionAgrupacionPack>> combinacionesAgrupaciones = getCombinacionesAgrupaciones(agrupacionesPack);
		Set<List<CombinacionAgrupacionPack>> combinaciones = Combinaciones.getCombinaciones(combinacionesAgrupaciones);

		eliminarCombinacionesConRepeticion(combinaciones);

		BigDecimal importeTotalAhorro = BigDecimal.ZERO;

		for (int i = 0; i < numCombos; i++) {
			List<CombinacionAgrupacionPack> mejorCombinacion = obtenerMejorCombinacion(combinaciones, false);

			if(mejorCombinacion != null) {
				BigDecimal descuento = getDescuentoCombinacion(mejorCombinacion);
				importeTotalAhorro = importeTotalAhorro.add(descuento);
				IPromocionTicket promocionTicket = getPromocionTicket(documento);
	
				aplicarPromocion(mejorCombinacion, promocionTicket);
			}
		}

		if(BigDecimalUtil.isMayorACero(importeTotalAhorro)) {
			IPromocionTicket promocionTicket = getPromocionTicket(documento);
			promocionTicket.setImporteTotalAhorro(importeTotalAhorro);
			documento.addPromocion(promocionTicket);
		}
	}

	protected BigDecimal getDescuentoCombinacion(List<CombinacionAgrupacionPack> mejorCombinacion) {
		BigDecimal importeTotalOriginalLineas = getImporteTotalOriginalLineas(mejorCombinacion);
		BigDecimal descuento = importeTotalOriginalLineas.subtract(precioPack);
		return descuento;
	}

	protected BigDecimal getImporteTotalOriginalLineas(List<CombinacionAgrupacionPack> mejorCombinacion) {
		BigDecimal importeTotalOriginalLineas = BigDecimal.ZERO;
		for (CombinacionAgrupacionPack combinacion : mejorCombinacion) {
			BigDecimal cantidadPromocionDisponible = combinacion.getAgrupacion().getCantidad();
			for (LineaDocumentoPromocionable linea : combinacion.getLineas()) {
				if (BigDecimalUtil.isMayorACero(cantidadPromocionDisponible)) {
					BigDecimal cantidadAplicacionLinea = cantidadPromocionDisponible.min(linea.getCantidad().subtract(linea.getCantidadPromocion()));
					importeTotalOriginalLineas = importeTotalOriginalLineas.add(linea.getPrecioAplicacionPromocion().multiply(cantidadAplicacionLinea));

					cantidadPromocionDisponible = cantidadPromocionDisponible.subtract(cantidadAplicacionLinea);
				}
			}
		}
		return importeTotalOriginalLineas;
	}

	/**
	 * Elimina aquellas combinaciones en las que una línea aparezca varias veces. Esto se puede dar en el caso de que
	 * una línea cumpla varias reglas de la promoción.
	 * 
	 * @param combinaciones
	 */
	protected void eliminarCombinacionesConRepeticion(Set<List<CombinacionAgrupacionPack>> listaCombinaciones) {
		Iterator<List<CombinacionAgrupacionPack>> it = listaCombinaciones.iterator();
		while (it.hasNext()) {
			List<CombinacionAgrupacionPack> combinaciones = it.next();
			List<Integer> lineasRevisadas = new ArrayList<Integer>();
			boolean hayLineaRepetida = false;
			for (CombinacionAgrupacionPack combinacion : combinaciones) {
				for (LineaDocumentoPromocionable linea : combinacion.getLineas()) {
					if (!lineasRevisadas.contains(linea.getIdLinea())) {
						lineasRevisadas.add(linea.getIdLinea());
					}
					else {
						hayLineaRepetida = true;
						break;
					}
				}

				if (hayLineaRepetida) {
					it.remove();
				}
			}
		}
	}

	/**
	 * Elimina aquellas combinaciones a las que no se le pueda aplicar la promoción porque ya tienen una promocion candidata aplicada
	 * 
	 * @param combinaciones
	 */
	private void eliminarCombinacionesConPromocionCandidata(Set<List<CombinacionAgrupacionPack>> listaCombinaciones) {
		Iterator<List<CombinacionAgrupacionPack>> it = listaCombinaciones.iterator();
		while (it.hasNext()) {
			List<CombinacionAgrupacionPack> combinaciones = it.next();
			outLoop:
			for (CombinacionAgrupacionPack combinacion : combinaciones) {
				for (LineaDocumentoPromocionable linea : combinacion.getLineas()) {
					if (BigDecimalUtil.isMenorOrIgual(linea.getCantidad(), linea.getCantidadPromocionCandidata())) {
						it.remove();
						break outLoop;
					}
				}
			}
		}
    }

	/**
	 * <p>
	 * Calcula el número de combos disponibles según las agrupaciones pasadas como párametro. Este número de combos será
	 * el mínimo del tamaño de las listas de líneas aplicables de cada regla divididas por la cantidad de la regla.
	 * </p>
	 * <p>
	 * Si el número de combos es mayor que el número de veces de aplicación se cogerá este número.
	 * </p>
	 * 
	 * @param agrupacionesPack
	 * @return
	 */
	protected int getNumCombos(List<AgrupacionPack> agrupacionesPack) {
		List<Integer> sizes = new ArrayList<Integer>();
		for (AgrupacionPack agrupacion : agrupacionesPack) {

			BigDecimal numeroArticulos = BigDecimal.ZERO;
			for (LineaDocumentoPromocionable linea : agrupacion.getLineasAplicables()) {
				numeroArticulos = numeroArticulos.add(linea.getCantidad());
			}

			BigDecimal ratio = numeroArticulos.divide(agrupacion.getCantidad(), 0, RoundingMode.FLOOR);
			sizes.add(ratio.intValue());
		}

		int numCombos = Collections.min(sizes);

		if (cantidadMaximaAplicacion != null && BigDecimalUtil.isMayor(new BigDecimal(numCombos).setScale(0, RoundingMode.FLOOR), cantidadMaximaAplicacion)) {
			numCombos = cantidadMaximaAplicacion.intValue();
		}

		return numCombos;
	}

	protected IPromocionTicket getPromocionTicket(DocumentoPromocionable<IPromocionTicket> documento) {
		IPromocionTicket promocionTicket = documento.getPromocion(getIdPromocion());
		if (promocionTicket == null) {
			promocionTicket = createPromocionTicket(customerCoupon);
		}
		return promocionTicket;
	}

	/**
	 * Devuelve las posibles agrupaciones para un determinado ticket.
	 * 
	 * @param documento
	 * @return
	 */
	protected List<AgrupacionPack> getAgrupacionesAplicables(DocumentoPromocionable<IPromocionTicket> documento) {
		FiltroLineasPackPromocion filtroLineas = createFiltroLineasPromocionPack(documento);
		List<AgrupacionPack> lineasAplicablesPack = filtroLineas.getLineasAplicablesPack(getAplicacion());
		return lineasAplicablesPack;
	}

	/**
	 * <p>
	 * Aplica la promoción a un conjunto de líneas determinadas.
	 * </p>
	 * <p>
	 * Calcula para cada línea de la lista su importe de ahorro con la siguiente fórmula:
	 * </p>
	 * <p>
	 * Descuento de la línea = (PVP de la línea / PVP total de las líneas) * (PVP total de las líneas - PVP del pack)
	 * </p>
	 * 
	 * @param mejorCombinacion
	 * @param promocionTicket
	 */
	protected void aplicarPromocion(List<CombinacionAgrupacionPack> mejorCombinacion, IPromocionTicket promocionTicket) {
		BigDecimal importeTotalOriginalLineas = getImporteTotalOriginalLineas(mejorCombinacion);
		BigDecimal descuento = importeTotalOriginalLineas.subtract(precioPack);

		BigDecimal importeAhorroCalculado = BigDecimal.ZERO;

		for (CombinacionAgrupacionPack combinacion : mejorCombinacion) {
			BigDecimal cantidadPromocionDisponible = combinacion.getAgrupacion().getCantidad();
			for (LineaDocumentoPromocionable linea : combinacion.getLineas()) {
				if (BigDecimalUtil.isMayorACero(cantidadPromocionDisponible)) {
					BigDecimal cantidadAplicacionLinea = cantidadPromocionDisponible.min(linea.getCantidad().subtract(linea.getCantidadPromocion()));
					BigDecimal importeAplicacionPromocionLinea = linea.getPrecioAplicacionPromocion().multiply(cantidadAplicacionLinea);

					BigDecimal importeTotalAhorroLinea = BigDecimalUtil.redondear(importeAplicacionPromocionLinea.divide(importeTotalOriginalLineas, 6, RoundingMode.HALF_UP).multiply(descuento));

					PromocionLineaTicket promocionLinea = linea.getPromocion(promocionTicket.getIdPromocion());
					if (promocionLinea == null) {
						promocionLinea = new PromocionLineaTicket(promocionTicket);
						linea.addPromocion(promocionLinea);
					}

					importeAhorroCalculado = importeAhorroCalculado.add(importeTotalAhorroLinea);

					promocionLinea.setImporteTotalDto(promocionLinea.getImporteTotalDtoMenosMargen().add(importeTotalAhorroLinea));
					promocionLinea.addCantidadPromocion(promocionLinea.getCantidadPromocionAplicada().add(cantidadAplicacionLinea));
					linea.addCantidadPromocion(cantidadAplicacionLinea);
					linea.recalcularImporteFinal();

					cantidadPromocionDisponible = cantidadPromocionDisponible.subtract(cantidadAplicacionLinea);
				}
			}
		}

		ajustarCentimosDescuento(mejorCombinacion, importeTotalOriginalLineas, importeAhorroCalculado);
	}
	
	/**
	 * Ajusta los céntimos de diferencia al final de la aplicación de la promoción
	 * 
	 * @param mejorCombinacion
	 *            Líneas sobre las que se podrá ajustar la diferencia de céntimos.
	 * @param importeTotalOriginalLineas
	 *            Importe total de las líneas antes de aplicar la promoción.
	 * @param importeAhorroCalculado
	 *            Importe del ahorro total al aplicar la promoción.
	 */
	protected void ajustarCentimosDescuento(List<CombinacionAgrupacionPack> mejorCombinacion, BigDecimal importeTotalOriginalLineas, BigDecimal importeAhorroCalculado) {
		BigDecimal totalTrasDto = importeTotalOriginalLineas.subtract(importeAhorroCalculado);
		if (!BigDecimalUtil.isIgual(totalTrasDto, precioPack)) {
			BigDecimal diferencia = totalTrasDto.subtract(precioPack);
			for (CombinacionAgrupacionPack combinacion : mejorCombinacion) {
				boolean diferenciaAjustada = false;
				for (LineaDocumentoPromocionable linea : combinacion.getLineas()) {
					PromocionLineaTicket promocion = null;
					for (PromocionLineaTicket promocionLinea : linea.getPromociones()) {
						if (promocionLinea.getIdPromocion().equals(this.getIdPromocion())) {
							promocion = promocionLinea;
						}
					}

					if (promocion != null && BigDecimalUtil.isMayorACero(((LineaTicket) linea).getImporteTotalConDto().add(diferencia))) {
						promocion.setImporteTotalDto(promocion.getImporteTotalDtoMenosMargen().add(diferencia));
						linea.recalcularImporteFinal();
						diferenciaAjustada = true;
						break;
					}
				}

				if (diferenciaAjustada) {
					break;
				}
			}
		}
	}

	/**
	 * Devuelve cuál es la combinación que más ahorro supone a la venta.
	 * 
	 * @param agrupacionesPack
	 * @param combinaciones
	 * @return
	 */
	protected List<CombinacionAgrupacionPack> obtenerMejorCombinacion(Set<List<CombinacionAgrupacionPack>> combinaciones, boolean calcularPromocion) {
		List<CombinacionAgrupacionPack> mejorCombinacion = null;
		BigDecimal mejorDescuento = BigDecimal.ZERO;

		for (List<CombinacionAgrupacionPack> combinacionesAgrupacion : combinaciones) {
			if(!esAplicable(combinacionesAgrupacion, calcularPromocion)) {
				continue;
			}
			BigDecimal descuento = getDescuentoCombinacion(combinacionesAgrupacion);
			if (BigDecimalUtil.isMayor(descuento, mejorDescuento)) {
				mejorCombinacion = combinacionesAgrupacion;
				mejorDescuento = descuento;
			}
		}
		return mejorCombinacion;
	}

	protected boolean esAplicable(List<CombinacionAgrupacionPack> combinacionesAgrupacion, boolean calcularPromocion) {
		for(CombinacionAgrupacionPack combinacionAgrupacionPack : combinacionesAgrupacion) {
			BigDecimal cantidadAgrupacion = combinacionAgrupacionPack.getAgrupacion().getCantidad();
			BigDecimal cantidadDisponible = BigDecimal.ZERO;
			for(LineaDocumentoPromocionable linea : combinacionAgrupacionPack.getLineas()) {
				BigDecimal cantidadPromocion = linea.getCantidadPromocion();
				if(calcularPromocion) {
					cantidadPromocion = linea.getCantidadPromocionCandidata();
				}
				
				cantidadDisponible = cantidadDisponible.add(linea.getCantidad().subtract(cantidadPromocion));
			}
			if(!BigDecimalUtil.isMayorOrIgual(cantidadDisponible, cantidadAgrupacion)) {
				return false;
			}
		}
	    return true;
    }

	/**
	 * Convierte la lista de agrupaciones en una lista de combinaciones de las líneas de una agrupación según la
	 * cantidad de esa agrupación. Se usa para sacar las posibles combinaciones de cada regla o agrupación de cara a
	 * combinarlas con las otras combinaciones de las otras reglas.
	 * 
	 * @param agrupacionesPack
	 * @return
	 */
	protected List<List<CombinacionAgrupacionPack>> getCombinacionesAgrupaciones(List<AgrupacionPack> agrupacionesPack) {
		List<List<CombinacionAgrupacionPack>> listasCombinaciones = new ArrayList<List<CombinacionAgrupacionPack>>();

		for (AgrupacionPack agrupacionPack : agrupacionesPack) {
			List<CombinacionAgrupacionPack> combinacionesAgrupacion = new ArrayList<CombinacionAgrupacionPack>();
			int tamanoCombinacion = agrupacionPack.getCantidad().intValue();
			if(tamanoCombinacion == 0) {
				tamanoCombinacion = 1;
			}
			
			while (tamanoCombinacion > 0) {
				List<int[]> combinaciones = Combinaciones.generarPosiciones(agrupacionPack.getLineasAplicables().size(), tamanoCombinacion);

				for (int[] combinacion : combinaciones) {
					List<LineaDocumentoPromocionable> lineasCombinacion = new ArrayList<LineaDocumentoPromocionable>();

					for (int i = 0; i < combinacion.length; i++) {
						LineaDocumentoPromocionable linea = agrupacionPack.getLineasAplicables().get(combinacion[i]);
						lineasCombinacion.add(linea);
					}

					BigDecimal cantidadArticulos = BigDecimal.ZERO;
					for (LineaDocumentoPromocionable linea : lineasCombinacion) {
						cantidadArticulos = cantidadArticulos.add(linea.getCantidad());
					}

					if (BigDecimalUtil.isMayorOrIgual(cantidadArticulos, agrupacionPack.getCantidad())) {
						CombinacionAgrupacionPack combinacionAgrupacion = new CombinacionAgrupacionPack();
						combinacionAgrupacion.setAgrupacion(agrupacionPack);
						combinacionAgrupacion.setLineas(lineasCombinacion);
						combinacionesAgrupacion.add(combinacionAgrupacion);
					}
				}
				tamanoCombinacion--;
			}

			if (!combinacionesAgrupacion.isEmpty()) {
				listasCombinaciones.add(combinacionesAgrupacion);
			}
		}

		return listasCombinaciones;
	}

	@Override
	public void leerDatosPromocion(byte[] datosPromocion) {
		try {
			XMLDocument xmlPromocion = new XMLDocument(datosPromocion);

			condiciones = (new CondicionPrincipalPromoBean(xmlPromocion.getNodo("condicionLineas")));
			aplicacion = new GrupoComponentePromoBean(xmlPromocion.getNodo("aplicacion"));

			String precioPack = xmlPromocion.getNodo("precioPack").getValue();
			setPrecioPack(new BigDecimal(precioPack.replace(",", ".")));

			String cantidadMaximaAplicacion = xmlPromocion.getNodo("cantidadMaximaAplicacion").getValue();
			if (StringUtils.isNotBlank(cantidadMaximaAplicacion)) {
				setCantidadMaximaAplicacion(new BigDecimal(cantidadMaximaAplicacion.replace(",", ".")));
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
			log.error("leerDatosPromocion() - Error al leer los datos de la promoción de tipo pack: " + e.getMessage(), e);
		}
	}

	public CondicionPrincipalPromoBean getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(CondicionPrincipalPromoBean condiciones) {
		this.condiciones = condiciones;
	}

	public GrupoComponentePromoBean getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(GrupoComponentePromoBean aplicacion) {
		this.aplicacion = aplicacion;
	}

	public BigDecimal getCantidadMaximaAplicacion() {
		return cantidadMaximaAplicacion;
	}

	public void setCantidadMaximaAplicacion(BigDecimal cantidadMaximaAplicacion) {
		this.cantidadMaximaAplicacion = cantidadMaximaAplicacion;
	}

	public BigDecimal getPrecioPack() {
		return precioPack;
	}

	public void setPrecioPack(BigDecimal precioPack) {
		this.precioPack = precioPack;
	}

	protected FiltroLineasPackPromocion createFiltroLineasPromocionPack(DocumentoPromocionable<IPromocionTicket> documento) {
		FiltroLineasPackPromocion filtro = SpringContext.getBean(FiltroLineasPackPromocion.class);
		filtro.setDocumento(documento);
		filtro.setFiltrarPromoExclusivas(false);
		return filtro;
	}

}
