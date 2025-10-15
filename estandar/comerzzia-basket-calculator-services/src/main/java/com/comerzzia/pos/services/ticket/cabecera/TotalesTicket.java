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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.ticket.ITicket;
import com.comerzzia.pos.services.ticket.pagos.IPagoTicket;
import com.comerzzia.pos.util.format.FormatUtil;


@Component
@Scope("prototype")
public class TotalesTicket extends TotalesTicketAbstract<IPagoTicket> implements ITotalesTicket<IPagoTicket> {
	public TotalesTicket(){
		super();
	}
	
	public TotalesTicket(ITicket<?, IPagoTicket, ?, ?, ?> ticket){
		super(ticket);
	}
	    	
    // Métodos para representación en pantalla e impresión    
	/* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getPendienteAsString()
	 */
	@Override
	public String getPendienteAsString() {
        return FormatUtil.getInstance().formateaImporte(getPendiente());
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getCambioAsString()
	 */
    @Override
	public String getCambioAsString() {
        return FormatUtil.getInstance().formateaImporte(getCambio().getImporte());
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getTotalAsString()
	 */
    @Override
	public String getTotalAsString() {
        return FormatUtil.getInstance().formateaImporte(getTotal());
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getEntregadoAsString()
	 */
    @Override
	public String getEntregadoAsString() {
        return FormatUtil.getInstance().formateaImporte(getEntregado());
    }
    
    @Override
	public String getBaseAsString() {
        return FormatUtil.getInstance().formateaImporte(getBase());
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getTotalPromocionesAsString()
	 */
    @Override
	public String getTotalPromocionesAsString() {
    	return FormatUtil.getInstance().formateaImporte(totalPromociones);
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getTotalPromocionesCabeceraAsString()
	 */
    @Override
	public String getTotalPromocionesCabeceraAsString(){
        return FormatUtil.getInstance().formateaImporte(totalPromocionesCabecera);
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getTotalAPagarAsString()
	 */
    @Override
	public String getTotalAPagarAsString(){
        return FormatUtil.getInstance().formateaImporte(totalAPagar);
    }
    	        
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getEntregadoACuentaAsString()
	 */
    @Override
	public String getEntregadoACuentaAsString(){
        return FormatUtil.getInstance().formateaImporte(entregadoACuenta);
    }
    
}
