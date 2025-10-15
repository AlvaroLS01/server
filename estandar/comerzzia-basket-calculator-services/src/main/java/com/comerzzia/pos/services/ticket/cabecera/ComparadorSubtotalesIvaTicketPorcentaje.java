package com.comerzzia.pos.services.ticket.cabecera;

import java.util.Comparator;


public class ComparadorSubtotalesIvaTicketPorcentaje implements Comparator<SubtotalIvaTicket> {

	@Override
    public int compare(SubtotalIvaTicket o1, SubtotalIvaTicket o2) {
		return o1.getPorcentaje().compareTo(o2.getPorcentaje());
    }

}
