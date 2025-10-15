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

import com.comerzzia.pos.persistence.core.impuestos.porcentajes.PorcentajeImpuestoBean;
import com.comerzzia.pos.services.ticket.lineas.LineaTicketAbstract;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.format.FormatUtil;

public class SubtotalIvaTicket implements ISubtotalIvaTicket{
    protected PorcentajeImpuestoBean porcentaje;
    protected BigDecimal base;
    protected BigDecimal impuestos;
    protected BigDecimal total;
    protected BigDecimal cuota;
    protected BigDecimal cuotaRecargo;    

    public SubtotalIvaTicket(){        
        base = BigDecimal.ZERO;
        impuestos = BigDecimal.ZERO;
        total = BigDecimal.ZERO;
        cuota = BigDecimal.ZERO;
        cuotaRecargo = BigDecimal.ZERO;
        porcentaje = new PorcentajeImpuestoBean(); // Para el unmarshall
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#addLinea(com.comerzzia.pos.services.ticket.lineas.LineaTicket)
	 */
    @Override
	public void addLinea(LineaTicketAbstract linea){
        total = total.add(linea.getImporteTotalConDto());
        total = BigDecimalUtil.redondear(total);
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#recalcular()
	 */
    @Override
	public void recalcular(){
        base = BigDecimalUtil.getAntesDePorcentaje(total, porcentaje.getPorcentajeMasRecargo(),15);  
        impuestos = total.subtract(base);
        cuota = BigDecimalUtil.porcentajeR(base, porcentaje.getPorcentaje());
        cuotaRecargo = BigDecimalUtil.porcentajeR(base, porcentaje.getPorcentajeRecargo());
        base = BigDecimalUtil.redondear(base);
        impuestos = BigDecimalUtil.redondear(impuestos);
        
        // Si existe diferencia entre el total y la suma de base más impuestos, se la restamos a la cuota para cuadrar los números
        cuota = cuota.add(total.subtract(base.add(impuestos)));
        
        impuestos = cuota.add(cuotaRecargo);
    }
    
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getCodImpuesto()
	 */
    @Override
    public String getCodImpuesto() {
        return porcentaje.getCodImpuesto();
    }
    
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#setCodImpuesto(java.lang.String)
	 */
     @Override
	public void setCodImpuesto(String codImpuesto) {
       porcentaje.setCodImpuesto(codImpuesto);       
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getBase()
	 */
    @Override
    public BigDecimal getBase() {
        return base;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#setBase(java.math.BigDecimal)
	 */
    @Override
	public void setBase(BigDecimal base) {
        this.base = base;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getImpuestos()
	 */
    @Override
    public BigDecimal getImpuestos() {
        return impuestos;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#setImpuestos(java.math.BigDecimal)
	 */
    @Override
	public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getTotal()
	 */
    @Override
    public BigDecimal getTotal() {
        return total;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#setTotal(java.math.BigDecimal)
	 */
    @Override
	public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getPorcentaje()
	 */
    @Override
    public BigDecimal getPorcentaje() {
        return porcentaje.getPorcentaje();
    }
    
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#setPorcentaje(java.math.BigDecimal)
	 */
    @Override
	public void setPorcentaje(BigDecimal porcentajeBD) {
       porcentaje.setPorcentaje(porcentajeBD);       
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#setPorcentajeImpuestoBean(com.comerzzia.pos.persistence.core.impuestos.porcentajes.PorcentajeImpuestoBean)
	 */
    @Override
	public void setPorcentajeImpuestoBean(PorcentajeImpuestoBean porcentaje) {
        this.porcentaje = porcentaje;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getCuota()
	 */
    @Override
    public BigDecimal getCuota() {
        return cuota;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#setCuota(java.math.BigDecimal)
	 */
    @Override
	public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getPorcentajeRecargo()
	 */
    @Override
    public BigDecimal getPorcentajeRecargo() {
        return porcentaje.getPorcentajeRecargo();
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#setPorcentajeRecargo(java.math.BigDecimal)
	 */
     @Override
	public void setPorcentajeRecargo(BigDecimal porcentajeRecargo) {
       porcentaje.setPorcentajeRecargo(porcentajeRecargo);
       
    }
    

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getCuotaRecargo()
	 */
    @Override
    public BigDecimal getCuotaRecargo() {
        return cuotaRecargo;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#setCuotaRecargo(java.math.BigDecimal)
	 */
    @Override
	public void setCuotaRecargo(BigDecimal cuotaRecargo) {
        this.cuotaRecargo = cuotaRecargo;
    }
    
    
    // funciones para representación por pantalla o impresión
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getBaseAsString()
	 */
    @Override
    public String getBaseAsString() {
        return FormatUtil.getInstance().formateaImporte(getBase());
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getTotalAsString()
	 */
    @Override
    public String getTotalAsString() {
        return FormatUtil.getInstance().formateaImporte(getTotal());
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getPorcentajeAsString()
	 */
    @Override
    public String getPorcentajeAsString() {
        return FormatUtil.getInstance().formateaImporte(getPorcentaje());
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getImpuestosAsString()
	 */
    @Override
    public String getImpuestosAsString() {
        return FormatUtil.getInstance().formateaImporte(getImpuestos());
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getCuotaAsString()
	 */
    @Override
    public String getCuotaAsString() {
        return FormatUtil.getInstance().formateaImporte(getCuota());
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getPorcentajeRecargoAsString()
	 */
    @Override
    public String getPorcentajeRecargoAsString() {
        return FormatUtil.getInstance().formateaImporte(getPorcentajeRecargo());
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket#getCuotaRecargoAsString()
	 */
    @Override
    public String getCuotaRecargoAsString() {
        return FormatUtil.getInstance().formateaImporte(getCuotaRecargo());
    }

}
