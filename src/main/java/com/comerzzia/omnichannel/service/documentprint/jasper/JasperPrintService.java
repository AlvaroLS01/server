package com.comerzzia.omnichannel.service.documentprint.jasper;

import java.io.OutputStream;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;

public interface JasperPrintService {

    void getPdf(IDatosSesion datosSesion, OutputStream outputStream, PrintDocumentDTO printRequest) throws ApiException;

    void getJasperPrint(IDatosSesion datosSesion, OutputStream outputStream, PrintDocumentDTO printRequest)
            throws ApiException;
}
