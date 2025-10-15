package com.comerzzia.pos.persistence.mediosPagos;

import javax.xml.bind.annotation.XmlElement;

import com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones.ConfiguracionPasarelaBean;
import com.comerzzia.core.basketcalculator.model.pasarelas.tipos.TipoPasarelaBean;

public class MedioPagoBean extends MedioPagoKey {

	private String desMedioPago;

	private Boolean contado;

	private Boolean efectivo;

	private Boolean tarjetaCredito;

	private String codTipoEfecto;

	private Boolean visibleVenta;

	private Boolean visibleTiendaVirtual;

	private Boolean visibleCompra;

	private Boolean activo;

	private Boolean manual;

	private Boolean recuentoAutomaticoCaja;

	private String claseControl;

	private Boolean visibleDevolucion;

	// INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------

	private TipoPasarelaBean tipoPasarela;

	private ConfiguracionPasarelaBean configPasarela;

	// FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------

	public String getDesMedioPago() {
		return desMedioPago;
	}

	public void setDesMedioPago(String desMedioPago) {
		this.desMedioPago = desMedioPago == null ? null : desMedioPago.trim();
	}

	public Boolean getContado() {
		return contado;
	}

	public void setContado(Boolean contado) {
		this.contado = contado;
	}

	public Boolean getEfectivo() {
		return efectivo;
	}

	public void setEfectivo(Boolean efectivo) {
		this.efectivo = efectivo;
	}

	public Boolean getTarjetaCredito() {
		return tarjetaCredito;
	}

	public void setTarjetaCredito(Boolean tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}

	public String getCodTipoEfecto() {
		return codTipoEfecto;
	}

	public void setCodTipoEfecto(String codTipoEfecto) {
		this.codTipoEfecto = codTipoEfecto == null ? null : codTipoEfecto.trim();
	}

	public Boolean getVisibleVenta() {
		return visibleVenta;
	}

	public void setVisibleVenta(Boolean visibleVenta) {
		this.visibleVenta = visibleVenta;
	}

	public Boolean getVisibleTiendaVirtual() {
		return visibleTiendaVirtual;
	}

	public void setVisibleTiendaVirtual(Boolean visibleTiendaVirtual) {
		this.visibleTiendaVirtual = visibleTiendaVirtual;
	}

	public Boolean getVisibleCompra() {
		return visibleCompra;
	}

	public void setVisibleCompra(Boolean visibleCompra) {
		this.visibleCompra = visibleCompra;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getManual() {
		return manual;
	}

	public void setManual(Boolean manual) {
		this.manual = manual;
	}

	public Boolean getRecuentoAutomaticoCaja() {
		return recuentoAutomaticoCaja;
	}

	public void setRecuentoAutomaticoCaja(Boolean recuentoAutomaticoCaja) {
		this.recuentoAutomaticoCaja = recuentoAutomaticoCaja;
	}

	public String getClaseControl() {
		return claseControl;
	}

	public void setClaseControl(String claseControl) {
		this.claseControl = claseControl;
	}

	public Boolean getVisibleDevolucion() {
		return visibleDevolucion;
	}

	public void setVisibleDevolucion(Boolean visibleDevolucion) {
		this.visibleDevolucion = visibleDevolucion;
	}

	// INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
	@Override
	public String toString() {
		return "(" + getCodMedioPago() + ") " + getDesMedioPago();
	}

	@XmlElement(name = "codigomediopago")
	public String getCodMedioPagoXML() {
		return getCodMedioPago();
	}

	public void setCodMedioPagoXML(String codigomediopago) {
		setCodMedioPago(codigomediopago);
	}

	public TipoPasarelaBean getTipoPasarela() {
		return tipoPasarela;
	}

	public void setTipoPasarela(TipoPasarelaBean tipoPasarela) {
		this.tipoPasarela = tipoPasarela;
	}

	public ConfiguracionPasarelaBean getConfigPasarela() {
		return configPasarela;
	}

	public void setConfigPasarela(ConfiguracionPasarelaBean configPasarela) {
		this.configPasarela = configPasarela;
	}

	// FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}