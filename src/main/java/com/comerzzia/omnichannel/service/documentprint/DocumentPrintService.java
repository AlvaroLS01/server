package com.comerzzia.omnichannel.service.documentprint;

import java.io.OutputStream;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;

public interface DocumentPrintService {

    String PARAM_SESSION_DATA = "sessionData";
    String PARAM_DEFAULT_TEMPLATE = "DEFAULT_TEMPLATE";
    String PARAM_COUNTRY_ID = "countryId";
    String PARAM_LOCALE_ID = "localeId";
    String PARAM_LOCALE = "locale";
    String PARAM_CURRENCY_CODE = "currencyCode";
    String PARAM_LOGO = "LOGO";
    String PARAM_COMPANY_CODE = "companyCode";
    String TEMPLATE_FILE = "templateFile";

    void printToStream(OutputStream outputStream, IDatosSesion datosSesion, PrintDocumentDTO printRequest)
            throws ApiException;
}
