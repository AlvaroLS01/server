/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND
 * INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED,
 * DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST,
 * TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK
 * WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE
 * AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */

package com.comerzzia.pos.persistence.giftcard;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.format.FormatUtil;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "giftcard")
@XmlType(propOrder = { "numTarjetaRegalo", "saldo", "saldoProvisional", "uidTransaccion", "importePago" })
@Component
@Scope("prototype")
public class GiftCardBean {

	@XmlElement(name = "numero_tarjeta")
	private String numTarjetaRegalo;

	private boolean activa;

	private boolean baja;

	@XmlElement
	private BigDecimal saldo;

	@XmlElement(name = "saldo_provisional")
	private BigDecimal saldoProvisional;

	@XmlElement(name = "uid_transaccion")
	private String uidTransaccion;

	private BigDecimal importeRecarga;

	@XmlElement(name = "importe_pago")
	private BigDecimal importePago;

	private String codTipoTarjeta;

	public String getNumTarjetaRegalo() {
		return numTarjetaRegalo;
	}

	public void setNumTarjetaRegalo(String numTarjetaRegalo) {
		this.numTarjetaRegalo = numTarjetaRegalo;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public BigDecimal getSaldoTotal() {
		return BigDecimalUtil.redondear(getSaldo().add(getSaldoProvisional()));
	}

	public String getSaldoTotalAsString() {
		return FormatUtil.getInstance().formateaImporte(getSaldoTotal());
	}

	public BigDecimal getSaldoProvisional() {
		return saldoProvisional;
	}

	public void setSaldoProvisional(BigDecimal saldo) {
		this.saldoProvisional = saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getUidTransaccion() {
		return uidTransaccion;
	}

	public void setUidTransaccion(String uidTransaccion) {
		this.uidTransaccion = uidTransaccion;
	}

	public BigDecimal getImporteRecarga() {
		return importeRecarga;
	}

	public void setImporteRecarga(BigDecimal importeRecarga) {
		this.importeRecarga = importeRecarga;
	}

	public String getCodTipoTarjeta() {
		return codTipoTarjeta;
	}

	public void setCodTipoTarjeta(String codTipoTarjeta) {
		this.codTipoTarjeta = codTipoTarjeta;
	}

	public BigDecimal getImportePago() {
		return importePago;
	}

	public void setImportePago(BigDecimal importePago) {
		this.importePago = importePago;
	}

	public String getSaldoDisponibleAsString() {
		return FormatUtil.getInstance().formateaImporte(getSaldoTotal().subtract(importePago));
	}

}
