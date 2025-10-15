package com.comerzzia.pos.services.ticket.promociones;

import com.comerzzia.pos.util.format.FormatUtil;

public class PromocionLineaTicket extends PromocionLineaTicketAbstract {
    public PromocionLineaTicket() {
    	super();
    }
    
    public PromocionLineaTicket(IPromocionTicket promocionTicket) {
    	super(promocionTicket);
    }


    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionLineaTicket#getIdPromocionAsString()
	 */
    @Override
	public String getIdPromocionAsString(){
        return String.valueOf(idPromocion);
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.promociones.IPromocionLineaTicket#getImporteTotalDtoMenosMargenAsStringNegate()
	 */
    @Override
	public String getImporteTotalDtoMenosMargenAsStringNegate() {
    	return "-" + FormatUtil.getInstance().formateaNumero(importeTotalDtoMenosMargen, 2);
    }
}
