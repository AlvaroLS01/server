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


package com.comerzzia.pos.services.ticket.cabecera;

import java.math.BigDecimal;

import com.comerzzia.pos.util.format.FormatUtil;


public class TarjetaRegaloTicket {
    protected BigDecimal saldo;
    protected BigDecimal saldoProvisional;
    protected String uidTransaccion;
    protected String numTarjetaRegalo;
    protected BigDecimal importeRecarga;

    public BigDecimal getSaldo() {
        return saldo;
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

    public String getNumTarjetaRegalo() {
        return numTarjetaRegalo;
    }

    public void setNumTarjetaRegalo(String numTarjetaRegalo) {
        this.numTarjetaRegalo = numTarjetaRegalo;
    }

    public BigDecimal getImporteRecarga() {
        return importeRecarga;
    }

    public void setImporteRecarga(BigDecimal importeRecarga) {
        this.importeRecarga = importeRecarga;
    }

    public BigDecimal getSaldoProvisional() {
        return saldoProvisional;
    }

    public void setSaldoProvisional(BigDecimal saldoProvisional) {
        this.saldoProvisional = saldoProvisional;
    }
    
    public String getSaldoTotalAsString(){
        return FormatUtil.getInstance().formateaNumero(getSaldoProvisional().add(getSaldo()), 2);
    }
    
    public String getSaldoAnteriorAsString(){
        return FormatUtil.getInstance().formateaNumero(getSaldoProvisional().add(getSaldo()).subtract(getImporteRecarga()), 2);
    }
    
    public String getImporteRecargaAsString(){
        return FormatUtil.getInstance().formateaNumero(getImporteRecarga(), 2);
    }
    
}
