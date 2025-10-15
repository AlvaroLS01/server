package com.comerzzia.pos.services.articulos.tarifas;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.articulos.tarifas.TarifaDetalleBean;
import com.comerzzia.pos.persistence.tarifas.TarifaBean;

@Component
@Scope("prototype")
public class TarifaArticuloDto {

	private TarifaBean cabecera;
	private TarifaDetalleBean detalle;

	public TarifaBean getCabecera() {
		return cabecera;
	}

	public void setCabecera(TarifaBean cabecera) {
		this.cabecera = cabecera;
	}

	public TarifaDetalleBean getDetalle() {
		return detalle;
	}

	public void setDetalle(TarifaDetalleBean detalle) {
		this.detalle = detalle;
	}

}
