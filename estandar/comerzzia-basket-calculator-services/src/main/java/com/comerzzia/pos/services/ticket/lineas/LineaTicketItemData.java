package com.comerzzia.pos.services.ticket.lineas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LineaTicketItemData {
	protected String itemCode;
	protected String itemDes;
	protected String taxTypeCode;
	protected Boolean combination1Active;
	protected Boolean combination2Active;
	protected String categoryCode;	
	protected String familyCode;
	protected String sectionCode;
	protected String supplierCode;
	protected String brandCode;	
	protected Boolean genericItem;
	protected Boolean weightRequired;
	protected Integer ageRequired;
    protected Boolean serialNumbersActive;
	
	protected String combination1Code;
	protected String combination2Code;
	
	protected List<String> tags;
	protected Map<String, String> properties;
	
	@Deprecated
	public String getCodArticulo() {
		return itemCode;
	}
	
	@Deprecated
	public String getDesArticulo() {
		return itemDes;
	}
	
	@Deprecated
	public String getCodProveedor() {
		return supplierCode;
	}
	
	@Deprecated
	public String getCodMarca() {
		return brandCode;
	}
	
	@Deprecated
	public String getCodFamilia() {
		return familyCode;
	}
	
	@Deprecated
	public String getCodseccion() {
		return sectionCode;	
	}
	
	@Deprecated
	public String getCodCategorizacion() {
		return categoryCode;
	}
	
	@Deprecated
	public List<String> getEtiquetas() {
		List<String> result = new ArrayList<String>();
		if (tags != null) {
		   result.addAll(tags);
		}
		return result;
	}
	
	
	@Deprecated
	public String getCodImpuesto() {
		return taxTypeCode;
	}
		
}
