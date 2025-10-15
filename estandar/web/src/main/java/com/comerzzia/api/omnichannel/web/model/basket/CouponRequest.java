package com.comerzzia.api.omnichannel.web.model.basket;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.QueryParam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CouponRequest {
	@QueryParam("upc")
	@NotNull
	@Size(max = 50)
	protected String upc;
	@QueryParam("name")
	protected String name;
	@QueryParam("description")
	protected String description;
	@QueryParam("amount")
	protected BigDecimal amount;
	@QueryParam("startDate")
	protected Date startDate;
	@QueryParam("endDate")
	protected Date endDate;	
	@QueryParam("promotionId")
	@NotNull	
	protected Long promotionId;	
}
