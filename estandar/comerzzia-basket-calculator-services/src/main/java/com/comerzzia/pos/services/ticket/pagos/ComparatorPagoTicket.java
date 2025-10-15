package com.comerzzia.pos.services.ticket.pagos;

import java.util.Comparator;

public class ComparatorPagoTicket implements Comparator<PagoTicket> {

	@Override
	public int compare(PagoTicket o1, PagoTicket o2) {
		if(o1.getPaymentId() == null) {
			return -1;
		}
		else if(o2.getPaymentId() == null) {
			return 1;
		}
		else {
			return o1.getPaymentId().compareTo(o2.getPaymentId());
		}
	}

}
