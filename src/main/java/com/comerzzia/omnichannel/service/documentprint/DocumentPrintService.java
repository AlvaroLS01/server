package com.comerzzia.omnichannel.service.documentprint;

import java.io.OutputStream;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;

public interface DocumentPrintService {
	public static final String PARAM_SESSION_DATA = "sessionData";
	public static final String PARAM_DEFAULT_TEMPLATE = "DEFAULT_TEMPLATE";
	public static final String PARAM_COUNTRY_ID = "countryId";
	public static final String PARAM_LOCALE_ID = "localeId";
	public static final String PARAM_LOCALE = "locale";
	public static final String PARAM_CURRENCY_CODE = "currencyCode";
	public static final String PARAM_LOGO = "LOGO";
	public static final String PARAM_COMPANY_CODE = "companyCode";
	public static final String TEMPLATE_FILE = "templateFile";
	
	void printToStream(OutputStream outputStream, IDatosSesion datosSesion, PrintDocumentDTO printRequest)
			throws ApiException;

}