package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.FiscalData;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.FiscalDataProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonSerialize(using = JsonSerializerFiscalProperties.class)
public class FiscalDataItems {

	private List<FiscalProperty> fiscalDataItem;

	public FiscalDataItems(FiscalData fiscalData) {
		fiscalDataItem = new ArrayList<>();
		for (FiscalDataProperty property : fiscalData.getProperties()) {
			fiscalDataItem.add(new FiscalProperty(property.getName(), property.getValue()));
		}
	}

	public FiscalDataItems() {
		fiscalDataItem = new ArrayList<>();
	}

	public List<FiscalProperty> getFiscalDataItem() {
		return fiscalDataItem;
	}
	
}
