package com.comerzzia.pos.services.ticket.lineas;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewLineaTicketRequest {
	protected String upc;
	protected String combination1Code;
	protected String combination2Code;
	protected String scanCodeType;
	protected Boolean scanned;	
	protected String attendantId;	
	
	protected BigDecimal quantity;
	protected BigDecimal referenceRatePrice;
	protected BigDecimal ratePrice;
	protected BigDecimal price;
	
	protected BigDecimal weight;
	protected BigDecimal discountPercentage;
	
	protected LineaTicketItemData itemData; 
}
