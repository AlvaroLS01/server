package com.comerzzia.omnichannel.domain.dto.basket.items.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ItemExceptionDTO extends Exception {
	private static final long serialVersionUID = -7419522425655112736L;

	protected String upc;
	protected String itemType = "Item";
	protected Boolean notFound = false;
	protected Boolean inactive = false;

	protected String message;
}
