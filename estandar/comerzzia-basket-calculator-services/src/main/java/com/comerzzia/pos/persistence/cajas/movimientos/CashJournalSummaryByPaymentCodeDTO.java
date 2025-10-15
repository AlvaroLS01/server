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


package com.comerzzia.pos.persistence.cajas.movimientos;

import java.math.BigDecimal;

import com.comerzzia.pos.persistence.cajas.recuentos.CashJournalCountLine;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;
import com.comerzzia.pos.util.format.FormatUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashJournalSummaryByPaymentCodeDTO {
    private MedioPagoBean medioPago;
    private BigDecimal entrada;
    private BigDecimal salida;
    private BigDecimal recuento;

    public CashJournalSummaryByPaymentCodeDTO(MedioPagoBean medioPago){
        entrada = BigDecimal.ZERO;
        recuento = BigDecimal.ZERO;
        salida = BigDecimal.ZERO;
        this.medioPago = medioPago;
    }

    public void addMovimiento(CashJournalLine movimiento){
        entrada = entrada.add(movimiento.getOutput());
        salida = salida.add(movimiento.getInput());
    }
    
    public void addLineaRecuento(CashJournalCountLine lineaRecuento){
        recuento = recuento.add(lineaRecuento.getTotal());
    }
    
    public MedioPagoBean getMedioPago() {
        return medioPago;
    }

    public BigDecimal getEntrada() {
        return entrada;
    }

    public BigDecimal getSalida() {
        return salida;
    }

    public BigDecimal getRecuento() {
        return recuento;
    }
    
    public void limpiarRecuento(){
        recuento = BigDecimal.ZERO;
    }
    
    public BigDecimal getTotal(){
        return getEntrada().subtract(getSalida());
    }
    
    public BigDecimal getDescuadre(){
        return getRecuento().subtract(getTotal());
    }
    
    public String getRecuentoAsString(){
    	return FormatUtil.getInstance().formateaImporte(getRecuento());
    }
    
    public String getTotalAsString(){
    	return FormatUtil.getInstance().formateaImporte(getTotal());
    }
    
    public String getDescuadreAsString(){
    	return FormatUtil.getInstance().formateaImporte(getDescuadre());
    }

	@Override
	public String toString() {
		return "CajaLineaAcumuladoBean [medioPago=" + medioPago + ", entrada=" + entrada + ", salida=" + salida + ", recuento=" + recuento + "]";
	}

    
    
}
