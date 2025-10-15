/**
 * ComerZZia 3.0
 *
 * Copyright (c) 2008-2015 Comerzzia, S.L.  All Rights Reserved.
 *
 * THIS WORK IS  SUBJECT  TO  SPAIN  AND  INTERNATIONAL  COPYRIGHT  LAWS  AND
 * TREATIES.   NO  PART  OF  THIS  WORK MAY BE  USED,  PRACTICED,  PERFORMED
 * COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED,  ABRIDGED, CONDENSED,
 * EXPANDED,  COLLECTED,  COMPILED,  LINKED,  RECAST, TRANSFORMED OR ADAPTED
 * WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION
 * OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO
 * CRIMINAL AND CIVIL LIABILITY.
 *
 * CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL
 * RESTRICTIONS.
 */

package com.comerzzia.pos.services.ticket.pagos;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.ticket.pagos.tarjeta.DatosRespuestaPagoTarjeta;
import com.comerzzia.pos.util.format.FormatUtil;


public class EntregaCuentaTicket {
	protected String uidTransaccionDet;
    protected MedioPagoBean medioPago;
    protected BigDecimal importe;
    protected String formaPago;
    protected DatosRespuestaPagoTarjeta datosRespuestaPagoTarjeta;
    
    @Autowired
    Sesion sesion;

    public EntregaCuentaTicket() {
        importe = BigDecimal.ZERO;
        medioPago = new MedioPagoBean();
    }

    public EntregaCuentaTicket(MedioPagoBean medioPago) {
        importe = BigDecimal.ZERO;
        this.medioPago = medioPago;
        formaPago = medioPago.getDesMedioPago();
    }

	public String getUidTransaccionDet() {
		return uidTransaccionDet;
	}

	public void setUidTransaccionDet(String uidTransaccionDet) {
		this.uidTransaccionDet = uidTransaccionDet;
	}

	public String getFormaPago() {
        return formaPago;
    }
    
	public MedioPagoBean getMedioPago() {
        return medioPago;
    }

	public void setMedioPago(MedioPagoBean medioPago) {
        this.medioPago = medioPago;
        if(medioPago != null){
        	formaPago = medioPago.getDesMedioPago();
        }
    }

	public void sumaImporte(BigDecimal importe) {
        if (this.importe == null) {
            this.importe = BigDecimal.ZERO;
        }
        this.importe = this.importe.add(importe);
    }

    public String getCodMedioPago() {
        return medioPago.getCodMedioPago();
    }

	public void setCodMedioPago(String codMedioPago) {
        medioPago.setCodMedioPago(codMedioPago);
    }

    public String getDesMedioPago() {
        return medioPago.getDesMedioPago();
    }

	public void setDesMedioPago(String desMedioPago) {
        medioPago.setDesMedioPago(desMedioPago);
    }
    
    public BigDecimal getImporte() {
        return importe;
    }

	public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getImporteAsString() {
        return FormatUtil.getInstance().formateaImporte(getImporte());
    }

	public boolean isMedioPagoDefecto(){
        return StringUtils.equals(sesion.getSesionCaja().getMedioPagoDefecto().getCodMedioPago(), medioPago.getCodMedioPago());
    }
    
	public boolean isMedioPagoTarjeta(){
        return medioPago.getTarjetaCredito();
    }
    
	public DatosRespuestaPagoTarjeta getDatosRespuestaPagoTarjeta() {
		return datosRespuestaPagoTarjeta;
	}

	public void setDatosRespuestaPagoTarjeta(DatosRespuestaPagoTarjeta datosRespuestaPagoTarjeta) {
		this.datosRespuestaPagoTarjeta = datosRespuestaPagoTarjeta;
	}
	
	public boolean tieneDatosRespuestaPagoTarjeta(){
		return datosRespuestaPagoTarjeta != null;
	}
       
}
