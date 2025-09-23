package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentificationCards {

	private List<IdentificationCard> identificationCard;

	public List<IdentificationCard> getIdentificationCard() {
		return identificationCard;
	}

	public void setIdentificationCard(List<IdentificationCard> identificationCard) {
		this.identificationCard = identificationCard;
	}

}
