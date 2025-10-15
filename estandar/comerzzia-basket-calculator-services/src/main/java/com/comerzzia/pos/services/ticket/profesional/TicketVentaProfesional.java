package com.comerzzia.pos.services.ticket.profesional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.ticket.TicketVentaAbono;
import com.comerzzia.pos.services.ticket.cabecera.SubtotalIvaTicket;
import com.comerzzia.pos.util.config.SpringContext;

@Component
@Scope("prototype")
public class TicketVentaProfesional extends TicketVentaAbono {
    
    public TicketVentaProfesional(){
        super();
    }

    @Override
	public void inicializarTotales() {
		getCabecera().setTotales(SpringContext.getBean(TotalesTicketProfesional.class, this));
	}
	
	@Override
	public Long getIdTratImpuestos() {
	    return getCabecera().getCliente().getIdTratImpuestos();
	}
	
	@Override
	protected SubtotalIvaTicket crearSubTotalIvaTicket() {
		return new SubtotalIvaTicketProfesional();
	}
	
}
