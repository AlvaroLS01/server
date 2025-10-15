package com.comerzzia.omnichannel.service.documentprint.raw;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.springframework.stereotype.Service;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.util.config.AppInfo;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.service.documentprint.AbstractDocumentPrintService;
import com.comerzzia.omnichannel.service.documentprint.DocumentPrintService;

@Service
public class RawPrintServiceImpl extends AbstractDocumentPrintService implements RawPrintService {
	protected final Logger log = Logger.getLogger(this.getClass());

	protected Map<String, Template> templateCache = new HashMap<>();
	protected VelocityEngine velocityEngine = null;
	
	@Override
	public void getRawXML(IDatosSesion datosSesion, OutputStream outputStream, PrintDocumentDTO printRequest)
			throws ApiException {

		// String documento a imprimir
		String rawXML = getRawXML(datosSesion, printRequest);

		try {
			outputStream.write(rawXML.getBytes());
		} catch (IOException e) {
			throw new ApiException("Error writing XML: " + e.getMessage(), e);
		}
	}

	@Override
	public void getRawXMLAsHTML(IDatosSesion datosSesion, OutputStream outputStream, PrintDocumentDTO printRequest) throws ApiException {
		RawHTMLPrinter printer = new RawHTMLPrinter();

		printer.inicializar();

		try {
			RawPrintSaxParser parser = new RawPrintSaxParser();

			parser.setParametros(printRequest.getCustomParams());

			// get final xml String document to parse
			parser.print(getRawXML(datosSesion, printRequest), printer);
		} catch (PrintParserException e) {
			log.error("getRawXMLAsHTML() - Error imprimiendo documento: " + e.getMessage(), e);
			throw new RuntimeException(e);
		}
		
		try {
		    outputStream.write(printer.getPrevisualizacion().getBytes());
		} catch (IOException e) {
			throw new ApiException("Error writing XML as HTML: " + e.getMessage(), e);
		}		    
	}

	protected String getRawXML(IDatosSesion datosSesion, PrintDocumentDTO printRequest) throws ApiException {
		log.debug("getRawXML() - Generando documento con parametros: " + printRequest.toString());

		Map<String, Object> docParameters = generateDocParameters(datosSesion, printRequest);		
		
		File templateFile = (File)docParameters.get(DocumentPrintService.TEMPLATE_FILE);
		File basePath = new File(AppInfo.getInformesInfo().getRutaBase());
		
		String templateRelativePath = templateFile.getAbsolutePath().replace(basePath.getAbsolutePath(), "");
				
		// Pasamos a la plantilla los parámetros que la alimentan
		VelocityEngine ve = getVelocityEngine();
				
		Template template = ve.getTemplate(templateRelativePath, "UTF-8");
				
		// put report parameters to velocity context
		Context context = new VelocityContext();
		
		for (String key : docParameters.keySet()) {
			context.put(key, docParameters.get(key));
		}
		
		// Aplicamos a la plantilla las variables
		try (StringWriter writer = new StringWriter()) {
			template.merge(context, writer);
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected VelocityEngine getVelocityEngine() {
		if (velocityEngine != null) {
			return velocityEngine;
		}

		velocityEngine = new VelocityEngine();

		// Establecemos propiedades
		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");

		velocityEngine.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");		
		velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, AppInfo.getInformesInfo().getRutaBase());
		

		velocityEngine.setProperty("runtime.log.logsystem.log4j.logger", "root");
		velocityEngine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
		velocityEngine.setProperty("velocimacro.library", TEMPLATES_PATH + "velocimacros.vm");

		// engine init
		velocityEngine.init();

		return velocityEngine;
	}

	@Override
	protected String getTemplateExtension() {
		return ".xml";
	}

}
