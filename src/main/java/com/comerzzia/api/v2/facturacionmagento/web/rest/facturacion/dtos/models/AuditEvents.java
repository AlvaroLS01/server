package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditEvents {

	@JsonProperty("event")
	List<Event> event;

	public List<Event> getEvent() {
		return event;
	}

	public void setEvent(List<Event> event) {
		this.event = event;
	}

}
