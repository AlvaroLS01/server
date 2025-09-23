package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FiscalProperty {

	private String name;
	private String value;

	public FiscalProperty(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public FiscalProperty() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
