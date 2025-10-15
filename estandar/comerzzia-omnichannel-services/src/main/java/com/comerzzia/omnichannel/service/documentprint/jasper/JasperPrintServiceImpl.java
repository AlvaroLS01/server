package com.comerzzia.omnichannel.service.documentprint.jasper;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.service.documentprint.AbstractDocumentPrintService;
import com.comerzzia.omnichannel.service.documentprint.DocumentPrintService;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class JasperPrintServiceImpl extends AbstractDocumentPrintService implements JasperPrintService {
	protected final Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void getPdf(IDatosSesion datosSesion, OutputStream outputStream, PrintDocumentDTO printRequest) throws ApiException {
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(getJasperPrint(datosSesion, printRequest)));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		try {
			exporter.exportReport();
		} catch (JRException e) {
			throw new ApiException("Error generating PDF: " + e.getMessage(), e);
		}
	}

	@Override
	public void getJasperPrint(IDatosSesion datosSesion, OutputStream outputStream, PrintDocumentDTO printRequest) throws ApiException {
        JasperPrint jasperPrint = getJasperPrint(datosSesion, printRequest);

        try {
			JRSaver.saveObject(jasperPrint, outputStream);
		} catch (JRException e) {
			throw new ApiException("Error generating JasperPrint: " + e.getMessage(), e);
		}
	}

	protected JasperPrint getJasperPrint(IDatosSesion datosSesion, PrintDocumentDTO printRequest) throws ApiException {
		log.debug("getJasperPrint() - Generando documento con parametros: " + printRequest.toString());
		
		Map<String, Object> docParameters = generateDocParameters(datosSesion, printRequest);
				
		// main report
		File templateFile = (File)docParameters.get(DocumentPrintService.TEMPLATE_FILE);
						
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(templateFile);
			return JasperFillManager.fillReport(jasperReport, docParameters, new JREmptyDataSource());
		} catch (JRException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected String getTemplateExtension() {
		return ".jasper";
	}

}
