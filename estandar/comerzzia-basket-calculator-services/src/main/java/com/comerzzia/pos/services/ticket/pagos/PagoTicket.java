/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND
 * INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED,
 * DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST,
 * TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK
 * WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE
 * AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */

package com.comerzzia.pos.services.ticket.pagos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.giftcard.GiftCardBean;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.ticket.pagos.tarjeta.DatosRespuestaPagoTarjeta;
import com.comerzzia.pos.util.format.FormatUtil;

@Component
@Scope("prototype")
public class PagoTicket implements IPagoTicket {

	protected MedioPagoBean medioPago;
	protected BigDecimal importe;
	protected String codigoPagoCredito;
	protected String codigoPagoBanco;
	protected boolean eliminable;
	protected boolean pagoTarjetaRegalo;
	protected String formaPago;
	protected DatosRespuestaPagoTarjeta datosRespuestaPagoTarjeta;

	protected String uidTransaccionDet;

	protected List<GiftCardBean> giftcards;

	protected Integer paymentId;

	protected Map<String, Object> extendedData;

	protected boolean introducidoPorCajero;

	protected boolean movimientoCajaInsertado;
	
    @Autowired
    Sesion sesion;
    
	public PagoTicket() {
		importe = BigDecimal.ZERO;
		medioPago = new MedioPagoBean();
		codigoPagoCredito = "";
		giftcards = new ArrayList<GiftCardBean>();
		extendedData = new HashMap<String, Object>();
	}

	public PagoTicket(MedioPagoBean medioPago) {
		importe = BigDecimal.ZERO;
		this.medioPago = medioPago;
		formaPago = medioPago.getDesMedioPago();
		codigoPagoCredito = "";
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#getFormaPago()
	 */
	@Override
	public String getFormaPago() {
		return formaPago;
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#getMedioPago()
	 */
	@Override
	public MedioPagoBean getMedioPago() {
		return medioPago;
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#setMedioPago(com.comerzzia.pos.persistence.mediosPagos.
	 * MedioPagoBean)
	 */
	@Override
	public void setMedioPago(MedioPagoBean medioPago) {
		this.medioPago = medioPago;
		if (medioPago != null) {
			formaPago = medioPago.getDesMedioPago();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#getCodMedioPago()
	 */
	@Override
	@XmlElement(name = "codmedpag")
	public String getCodMedioPago() {
		return medioPago.getCodMedioPago();
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#setCodMedioPago(java.lang.String)
	 */
	@Override
	public void setCodMedioPago(String codMedioPago) {
		medioPago.setCodMedioPago(codMedioPago);
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#getDesMedioPago()
	 */
	@Override
	@XmlElement(name = "desmedpag")
	public String getDesMedioPago() {
		return medioPago.getDesMedioPago();
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#setDesMedioPago(java.lang.String)
	 */
	@Override
	public void setDesMedioPago(String desMedioPago) {
		medioPago.setDesMedioPago(desMedioPago);
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#getImporte()
	 */
	@Override
	@XmlElement
	public BigDecimal getImporte() {
		return importe;
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#setImporte(java.math.BigDecimal)
	 */
	@Override
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	// Métodos para impresión por pantalla o papel
	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#getImporteAsString()
	 */
	@Override
	public String getImporteAsString() {
		return FormatUtil.getInstance().formateaImporte(getImporte());
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#getCodigoPagoCredito()
	 */
	@Override
	public String getCodigoPagoCredito() {
		return codigoPagoCredito;
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#setCodigoPagoCredito(java.lang.String)
	 */
	@Override
	public void setCodigoPagoCredito(String codigoPagoCredito) {
		this.codigoPagoCredito = codigoPagoCredito;
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#getCodigoPagoBanco()
	 */
	@Override
	public String getCodigoPagoBanco() {
		return codigoPagoBanco;
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#setCodigoPagoBanco(java.lang.String)
	 */
	@Override
	public void setCodigoPagoBanco(String codigoPagoBanco) {
		this.codigoPagoBanco = codigoPagoBanco;
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#isMedioPagoDefecto()
	 */
	@Override
	public boolean isMedioPagoDefecto() {
		return StringUtils.equals(sesion.getSesionCaja().getMedioPagoDefecto().getCodMedioPago(), medioPago.getCodMedioPago());
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#isMedioPagoTarjeta()
	 */
	@Override
	public boolean isMedioPagoTarjeta() {
		return medioPago.getTarjetaCredito();
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#isPagoTarjetaRegalo()
	 */
	@Override
	public boolean isPagoTarjetaRegalo() {
		return giftcards != null && !giftcards.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#isEliminable()
	 */
	@XmlElement
	@Override
	public boolean isEliminable() {
		return eliminable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.pagos.IPagoTicket#setEliminable(boolean)
	 */
	@Override
	public void setEliminable(boolean eliminable) {
		this.eliminable = eliminable;
	}

	@XmlElement
	public DatosRespuestaPagoTarjeta getDatosRespuestaPagoTarjeta() {
		return datosRespuestaPagoTarjeta;
	}

	public void setDatosRespuestaPagoTarjeta(DatosRespuestaPagoTarjeta datosRespuestaPagoTarjeta) {
		this.datosRespuestaPagoTarjeta = datosRespuestaPagoTarjeta;
	}

	public boolean tieneDatosRespuestaPagoTarjeta() {
		return datosRespuestaPagoTarjeta != null;
	}

	public List<GiftCardBean> getGiftcards() {
		return giftcards;
	}

	public void setGiftcards(List<GiftCardBean> giftcards) {
		this.giftcards = giftcards;
	}

	public void addGiftcardBean(BigDecimal importe, GiftCardBean giftcard) {
		if (giftcard != null) {
			boolean encontrado = false;
			if (giftcards != null) {
				for (GiftCardBean tarjetaPago : giftcards) {
					if (tarjetaPago.getNumTarjetaRegalo().equals(giftcard.getNumTarjetaRegalo())) {
						encontrado = true;
						tarjetaPago.setImportePago(tarjetaPago.getImportePago().add(importe));
					}
				}
			}

			if (!encontrado) {
				giftcard.setImportePago(importe);
				addGiftcard(giftcard);
			}
		}
	}

	protected void addGiftcard(GiftCardBean giftcard) {
		if (giftcards == null) {
			giftcards = new ArrayList<GiftCardBean>();
		}
		giftcards.add(giftcard);
	}

	@XmlElement(name = "payment_id")
	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Map<String, Object> getExtendedData() {
		return extendedData;
	}

	public void setExtendedData(Map<String, Object> extendedData) {
		this.extendedData = extendedData;
	}

	public void addExtendedData(String key, Object value) {
		if (this.extendedData == null) {
			this.extendedData = new HashMap<String, Object>();
		}

		this.extendedData.put(key, value);
	}

	public Object getExtendedData(String key) {
		if (this.extendedData == null) {
			this.extendedData = new HashMap<String, Object>();
		}

		return this.extendedData.get(key);
	}

	public boolean isIntroducidoPorCajero() {
		return introducidoPorCajero;
	}

	public void setIntroducidoPorCajero(boolean introducidoPorCajero) {
		this.introducidoPorCajero = introducidoPorCajero;
	}

	public boolean isMovimientoCajaInsertado() {
		return movimientoCajaInsertado;
	}

	public void setMovimientoCajaInsertado(boolean movimientoCajaInsertado) {
		this.movimientoCajaInsertado = movimientoCajaInsertado;
	}

	public String getUidTransaccionDet() {
		return uidTransaccionDet;
	}

	public void setUidTransaccionDet(String uidTransaccionDet) {
		this.uidTransaccionDet = uidTransaccionDet;
	}

	@Override
	public String toString() {
		return "ID: " + paymentId + ". Medio de pago: " + medioPago + ". Importe: " + importe;
	}

}
