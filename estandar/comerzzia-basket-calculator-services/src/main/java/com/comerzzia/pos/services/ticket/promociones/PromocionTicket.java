package com.comerzzia.pos.services.ticket.promociones;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.promociones.Promocion;
import com.comerzzia.pos.util.format.FormatUtil;


@Component
@Scope("prototype")
public class PromocionTicket extends PromocionTicketAbstract {
    
    public PromocionTicket() {
    }
    
    public PromocionTicket(Promocion promocion) {
    	super(promocion);  
    }    
    
    @Override
	public String getImporteTotalAhorroAsString(){
        return FormatUtil.getInstance().formateaImporte(importeTotalAhorro);
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionTicket#getIdPromocionAsString()
	 */
    @Override
	public String getIdPromocionAsString(){
        return String.valueOf(idPromocion);
    }    
}
