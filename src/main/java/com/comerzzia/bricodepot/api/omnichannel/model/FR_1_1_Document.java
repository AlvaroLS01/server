package com.comerzzia.bricodepot.api.omnichannel.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
 
import com.comerzzia.omnichannel.model.documents.sales.ticket.TicketVentaAbono;
 
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class FR_1_1_Document extends TicketVentaAbono {

	private static final long serialVersionUID = 5387769517902279947L;
}