package com.comerzzia.api.v2.facturacionmagento.services.ticket.lineas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.motivos.Motivo;
import com.comerzzia.omnichannel.model.documents.sales.ticket.lineas.LineaTicket;

@XmlAccessorType(XmlAccessType.FIELD)
@Component
@Primary
@Scope("prototype")
public class BricodepotLineaTicket extends LineaTicket{

	
	@XmlElement(name = "motivo")
	private Motivo motivo;

	public BricodepotLineaTicket() {
		super();
	}

	public Motivo getMotivo() {
		return motivo;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}
}
