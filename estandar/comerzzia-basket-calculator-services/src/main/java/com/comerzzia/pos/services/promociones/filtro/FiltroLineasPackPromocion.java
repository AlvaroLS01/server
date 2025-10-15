package com.comerzzia.pos.services.promociones.filtro;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;
import com.comerzzia.pos.services.promociones.tipos.componente.GrupoComponentePromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.IComponentePromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.ItemComponentePromoBean;
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.pack.AgrupacionPack;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;

@Component
@Scope("prototype")
public class FiltroLineasPackPromocion extends FiltroLineasPromocion {

	public List<AgrupacionPack> getLineasAplicablesPack(GrupoComponentePromoBean grupo) {
		List<AgrupacionPack> lineasAplicables = new ArrayList<AgrupacionPack>();
		
		AgrupacionPack ultimaAgrupacion = null;
		for(IComponentePromoBean componente : grupo.getComponentes()) {
			if(componente.isTipoItemRegla()) {
				ItemComponentePromoBean regla = (ItemComponentePromoBean) componente;
				if(regla.isReglaCantidadMultiple() && ultimaAgrupacion != null) {
					ultimaAgrupacion.setCantidad(regla.getValorAsBigDecimal());
					ultimaAgrupacion.addRegla(regla);
				}
				else {
					AgrupacionPack agrupacionPack = procesarRegla(regla);
	
					if (agrupacionPack != null) {
						lineasAplicables.add(agrupacionPack);
					}
				}
			}
			else if(componente.isTipoGrupoReglas()) {
				AgrupacionPack agrupacionPack = procesarGrupo((GrupoComponentePromoBean) componente);

				if (agrupacionPack != null) {
					ultimaAgrupacion = agrupacionPack;
					lineasAplicables.add(agrupacionPack);
				}
			}
		}

		// Si no se cumplen todas las reglas, no hay líneas aplicables
		if (lineasAplicables.size() < grupo.getReglas().size()) {
			return new ArrayList<>();
		}
		
		// Eliminamos aquellas agrupaciones en las que no se cumpla la cantidad necesaria
		Iterator<AgrupacionPack> it = lineasAplicables.iterator();
		while(it.hasNext()) {
			AgrupacionPack agrupacion = it.next();
			
			if(agrupacion.getCantidad() == null) {
				it.remove();
				continue;
			}
			
			BigDecimal cantidadLineas = BigDecimal.ZERO;
			for(LineaDocumentoPromocionable linea : agrupacion.getLineasAplicables()) {
				cantidadLineas = cantidadLineas.add(linea.getCantidad());
			}
			
			if(BigDecimalUtil.isMenor(cantidadLineas, agrupacion.getCantidad())) {
				it.remove();
			}
		}

		return lineasAplicables;
	}

	protected AgrupacionPack procesarRegla(ItemComponentePromoBean regla) {
		AgrupacionPack agrupacionPack = null;

		List<LineaDocumentoPromocionable> lineasAplicablesRegla = getLineasAplicablesRegla(regla, documento.getLineasDocumentoPromocionable());
		if (lineasAplicablesRegla.isEmpty()) {
			return null; // No continuamos procesando, porque hay una condición del pack que no se cumple
		}

		BigDecimal cantidadRegla = regla.getValorCantidadAsBigDecimal();
		BigDecimal cantidadLineas = calcularCantidadLineas(lineasAplicablesRegla);

		if (BigDecimalUtil.isMayorOrIgual(cantidadLineas, cantidadRegla)) {
			agrupacionPack = crearAgrupacion(lineasAplicablesRegla, cantidadRegla, regla);
		}

		return agrupacionPack;
	}

	protected AgrupacionPack procesarGrupo(GrupoComponentePromoBean grupo) {
		List<LineaDocumentoPromocionable> lineasAplicablesRegla = getLineasAplicablesGrupo(grupo, documento.getLineasDocumentoPromocionable());
		if (lineasAplicablesRegla.isEmpty()) {
			return null; // No continuamos procesando, porque hay una condición del pack que no se cumple
		}
		
		AgrupacionPack agrupacionPack = crearAgrupacion(lineasAplicablesRegla, null, null);

		return agrupacionPack;
	}

	protected AgrupacionPack crearAgrupacion(List<LineaDocumentoPromocionable> lineasAplicablesRegla, BigDecimal cantidadRegla, ItemComponentePromoBean regla) {
		AgrupacionPack agrupacionPack = new AgrupacionPack();
		agrupacionPack.setCantidad(cantidadRegla);
		agrupacionPack.setLineasAplicables(lineasAplicablesRegla);
		agrupacionPack.addRegla(regla);
		return agrupacionPack;
	}

	protected BigDecimal calcularCantidadLineas(List<LineaDocumentoPromocionable> lineasAplicablesRegla) {
		BigDecimal cantidadLineas = BigDecimal.ZERO;
		for (LineaDocumentoPromocionable linea : lineasAplicablesRegla) {
			cantidadLineas = cantidadLineas.add(linea.getCantidad());
		}
		return cantidadLineas;
	}

}
