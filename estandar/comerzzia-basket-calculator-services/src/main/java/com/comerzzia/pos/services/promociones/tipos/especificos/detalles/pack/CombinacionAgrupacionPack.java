package com.comerzzia.pos.services.promociones.tipos.especificos.detalles.pack;

import java.util.List;

import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;

public class CombinacionAgrupacionPack {

	private AgrupacionPack agrupacion;

	private List<LineaDocumentoPromocionable> lineas;

	public AgrupacionPack getAgrupacion() {
		return agrupacion;
	}

	public void setAgrupacion(AgrupacionPack agrupacion) {
		this.agrupacion = agrupacion;
	}

	public List<LineaDocumentoPromocionable> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaDocumentoPromocionable> lineas) {
		this.lineas = lineas;
	}

}
