package com.comerzzia.omnichannel.domain.dto.basket;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CouponIssueDTO {
	protected String name;
	protected String description;  
	protected BigDecimal amount;
	protected Date startDate;
	protected Date endDate;
	
	protected Long maxUses;
	
	protected Long priority;
	
	protected String couponType;
	
	protected Long promotionId;
	
	protected Boolean ManualSelection;
	protected String imageUrl;
}
