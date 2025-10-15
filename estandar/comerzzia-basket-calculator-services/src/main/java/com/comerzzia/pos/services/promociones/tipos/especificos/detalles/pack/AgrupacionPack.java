package com.comerzzia.pos.services.promociones.tipos.especificos.detalles.pack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;
import com.comerzzia.pos.services.promociones.tipos.componente.IComponentePromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.ItemComponentePromoBean;

/**
 * Objeto que agrupa las líneas aplicables para una determinada regla de la promoción de pack.
 */
public class AgrupacionPack {

	private BigDecimal cantidad;

	private List<LineaDocumentoPromocionable> lineasAplicables;

	private List<IComponentePromoBean> reglas;

	public AgrupacionPack() {
		super();
		this.reglas = new ArrayList<IComponentePromoBean>();
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public List<LineaDocumentoPromocionable> getLineasAplicables() {
		return lineasAplicables;
	}

	public void setLineasAplicables(List<LineaDocumentoPromocionable> lineasAplicables) {
		this.lineasAplicables = lineasAplicables;
	}

	public List<IComponentePromoBean> getReglas() {
		return reglas;
	}

	public void setReglas(List<IComponentePromoBean> reglas) {
		this.reglas = reglas;
	}

	public void addRegla(ItemComponentePromoBean regla) {
		this.reglas.add(regla);
    }

}
