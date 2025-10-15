package com.comerzzia.omnichannel.domain.dto.basket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.comerzzia.omnichannel.domain.dto.basket.items.exception.ItemExceptionDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(callSuper=true)
public class LoyaltyCardExceptionDTO extends ItemExceptionDTO {
   private static final long serialVersionUID = 2506440572417798986L;
      
	protected String accountNumber;
	protected String entryMethod;
	protected String cardType;	
	protected String status;
	
	public LoyaltyCardExceptionDTO() {
	   this.itemType = "LoyaltyCard";
	}
}
