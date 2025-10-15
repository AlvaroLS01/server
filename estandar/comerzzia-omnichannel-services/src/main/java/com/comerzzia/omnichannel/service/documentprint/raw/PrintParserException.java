package com.comerzzia.omnichannel.service.documentprint.raw;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.BadRequestException;

public class PrintParserException extends BadRequestException {
	private static final long serialVersionUID = -5144408934604734279L;

	public PrintParserException(String msg) {
		super(STATUS_RESPONSE_ERROR_BAD_REQUEST, msg);
		this.code = ApiException.STATUS_RESPONSE_ERROR_BAD_REQUEST;
	}

	public PrintParserException(Throwable e) {
		this(e.getMessage());
	}

}
