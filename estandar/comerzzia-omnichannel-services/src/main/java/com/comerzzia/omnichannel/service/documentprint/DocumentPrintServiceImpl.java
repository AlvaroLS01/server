package com.comerzzia.omnichannel.service.documentprint;

import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.service.documentprint.jasper.JasperPrintService;
import com.comerzzia.omnichannel.service.documentprint.raw.RawPrintService;

@Service
public class DocumentPrintServiceImpl implements DocumentPrintService {
	protected final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	RawPrintService rawPrintService;
	
	@Autowired
	JasperPrintService jasperPrintService;
	
	@Override
	public void printToStream(OutputStream outputStream, IDatosSesion datosSesion, PrintDocumentDTO printRequest) throws ApiException {
		// Print request format
        if (StringUtils.equals(printRequest.getMimeType(), MimeTypeUtils.APPLICATION_XML_VALUE)) {
        	rawPrintService.getRawXML(datosSesion, outputStream, printRequest);	
        } else if (StringUtils.equals(printRequest.getMimeType(), MimeTypeUtils.TEXT_HTML_VALUE)) {
        	rawPrintService.getRawXMLAsHTML(datosSesion, outputStream, printRequest);
        } else if (StringUtils.equals(printRequest.getMimeType(), "application/pdf")) {
        	jasperPrintService.getPdf(datosSesion, outputStream, printRequest);
        } else if (StringUtils.equals(printRequest.getMimeType(), "application/jasperprint")) {
        	jasperPrintService.getJasperPrint(datosSesion, outputStream, printRequest);
        }
        
		
	}
}
