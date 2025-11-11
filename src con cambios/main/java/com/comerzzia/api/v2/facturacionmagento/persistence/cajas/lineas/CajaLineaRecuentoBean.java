package com.comerzzia.api.v2.facturacionmagento.persistence.cajas.lineas;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.comerzzia.pos.util.format.FormatUtil;

@XmlRootElement(name = "recuento")
public class CajaLineaRecuentoBean extends CajaLineaRecuentoKey {

	private String codMedioPago;

	private Integer cantidad;

	private BigDecimal valor;

	// INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------

	// FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------

	public String getCodMedioPago() {
		return codMedioPago;
	}

	public void setCodMedioPago(String codMedioPago) {
		this.codMedioPago = codMedioPago == null ? null : codMedioPago.trim();
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getValorAsString() {
		return FormatUtil.getInstance().formateaNumero(valor, 2);
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	// INICIO MÃ‰TODOS PERSONALIZADOS--------------------------------------------
	public BigDecimal getTotal() {
		return new BigDecimal(cantidad).multiply(valor);
	}

	public String getTotalAsString() {
		return FormatUtil.getInstance().formateaNumero(new BigDecimal(cantidad).multiply(valor), 2);
	}
	// FIN MÃ‰TODOS PERSONALIZADOS-----------------------------------------------

}
