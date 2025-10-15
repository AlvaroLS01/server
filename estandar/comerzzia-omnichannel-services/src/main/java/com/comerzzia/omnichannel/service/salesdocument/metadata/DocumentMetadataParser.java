package com.comerzzia.omnichannel.service.salesdocument.metadata;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.comerzzia.core.model.tiposdocumentos.TipoDocumentoBean;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoException;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoNotFoundException;
import com.comerzzia.core.servicios.tipodocumento.TiposDocumentosService;
import com.comerzzia.omnichannel.domain.entity.document.DocumentEntity;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DocumentMetadataParser {
	private static final String SCHEMA_VERSION_FIELD = "schemaVersion";
	private static final String LOCALE_FIELD = "locale";
	
	final List<String> formatFields = Arrays.asList("/cabecera/formato_impresion",
			                                        "/header/printTemplate");
	final List<String> companyFields = Arrays.asList("/cabecera/empresa/codigo",
			                                         "/header/company/companyCode");
	final List<String> docTypeIdFields = Arrays.asList("/cabecera/tipo_documento",
			                                           "/header/company/docTypeId");	
	final List<String> docTypeCodeFields = Arrays.asList("/cabecera/cod_tipo_documento",
			                                             "/header/company/docTypeCode");		
	final List<String> documentCodeFields = Arrays.asList("/cabecera/cod_ticket",
			                                              "/header/company/documentCode");
	
	
	@Autowired
	TiposDocumentosService tiposDocumentosService;

	public DocumentMetadata getMetadata(IDatosSesion datosSesion, DocumentEntity document) {
		DocumentMetadata result = getMetadata(datosSesion, document.getDocumentContent());
		
		// get properties from DocumentEntity if no metadata found
		if (result.getDocTypeId() == null) {
			result.setDocTypeId(document.getDocumentTypeId());	
		}
		
		if (StringUtils.isBlank(result.getDocTypeCode())) {
			try {
				TipoDocumentoBean tipoDocumentoBean = tiposDocumentosService.consultar(datosSesion,
						document.getDocumentTypeId());
			    
				result.setDocTypeCode(tipoDocumentoBean.getCodTipoDocumento());
			} catch (TipoDocumentoNotFoundException | TipoDocumentoException e) {
				throw new RuntimeException(e);
			}		
		}
		
		if (StringUtils.isBlank(result.getDocTypeCode())) {
			result.setDocumentCode(document.getDocumentCode());
	    }

		return result;
	}
	
	public DocumentMetadata getMetadata(IDatosSesion datosSesion, byte[] documentContent) {
		if (documentContent == null|| documentContent.length == 0) {
			throw new RuntimeException("Document content is empty");
		}

		DocumentMetadata result = new DocumentMetadata();

		if (documentContent[0] == '{') {
			result.setMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE);

			parseJsonMetadata(documentContent, result);
		} else {
			result.setMimeType(MimeTypeUtils.APPLICATION_XML_VALUE);

			parseXmlMetadata(documentContent, result);
		}

		if (StringUtils.isEmpty(result.getSchemaVersion())) {
			result.setSchemaVersion("1.0");
		}
		
		if (result.getLocale() == null) {
			result.setLocale(datosSesion.getLocale());
		}
		
		if (result.getDocTypeId() != null && StringUtils.isBlank(result.getDocTypeCode())) {
			try {
				TipoDocumentoBean tipoDocumentoBean = tiposDocumentosService.consultar(datosSesion,
						result.getDocTypeId());
			    
				result.setDocTypeCode(tipoDocumentoBean.getCodTipoDocumento());
			} catch (TipoDocumentoNotFoundException | TipoDocumentoException e) {
				throw new RuntimeException(e);
			}		
		}

		return result;
	}
	
	protected String getJsonFieldValue(JsonNode rootNode, String path) {
		String[] paths = path.split("/");
		String property = paths[paths.length-1];
		
		JsonNode currentNode = rootNode;
		
		int x = 1; 
		while (currentNode != null && x < paths.length-2) {
			String field = paths[x];
			
			currentNode = currentNode.get(field);
			
			x++;
		}
		
		// find property in object
		if (currentNode != null) {
			currentNode = currentNode.get(property);
			
			if (currentNode != null && currentNode.isValueNode()) {
				return currentNode.asText();
			}
		}
		
		return "";
	}
	
	protected String getJsonFieldValue(JsonNode rootNode, List<String> fields) {
		for (String field : fields) {
			String value = getJsonFieldValue(rootNode, field);
			
			if (StringUtils.isNotEmpty(value)) {
				return value;
			}
		}

		return "";
	}

	protected void parseJsonMetadata(final byte[] content, DocumentMetadata documentMetadata) {
		JsonFactory factory = new JsonFactory();

		ObjectMapper mapper = new ObjectMapper(factory);
		JsonNode rootNode;
		try {
			rootNode = mapper.readTree(content);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		if (rootNode.get(SCHEMA_VERSION_FIELD) != null) {
		   documentMetadata.setSchemaVersion(rootNode.get(SCHEMA_VERSION_FIELD).asText());
		}
		if (rootNode.get(LOCALE_FIELD) != null) {
		   documentMetadata.setLocale(rootNode.get(LOCALE_FIELD).asText());
		}

		documentMetadata.setPrintTemplate(getJsonFieldValue(rootNode, formatFields));
		documentMetadata.setCompanyCode(getJsonFieldValue(rootNode, companyFields));
		
		documentMetadata.setDocTypeId(Long.valueOf(getJsonFieldValue(rootNode, docTypeIdFields)));
		documentMetadata.setDocTypeCode(getJsonFieldValue(rootNode, docTypeCodeFields));
		
		documentMetadata.setDocumentCode(getJsonFieldValue(rootNode, documentCodeFields));
	}

	protected String getXmlFieldValue(Document doc, String xPathSearch) {
		XPath xPath = XPathFactory.newInstance().newXPath();

		try {
			NodeList nodeList = (NodeList) xPath.compile(xPathSearch).evaluate(doc, XPathConstants.NODESET);

			if (nodeList.getLength() == 1) {
				return nodeList.item(0).getTextContent();
			}
		} catch (XPathExpressionException ignore) {
		}
		
		return "";
	}
	
	protected String getXmlFieldValue(Document doc, String prefix, List<String> fields) {
		for (String field : fields) {
			String value = getXmlFieldValue(doc, prefix + field);
			
			if (StringUtils.isNotEmpty(value)) {
				return value;
			}
		}

		return "";
	}

	protected void parseXmlMetadata(final byte[] content, DocumentMetadata documentMetadata) {
		// Instantiate the Factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			// parse XML file
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(new ByteArrayInputStream(content));
		
			documentMetadata.setSchemaVersion(getXmlFieldValue(doc, "/ticket/@" + SCHEMA_VERSION_FIELD));
			documentMetadata.setLocale(getXmlFieldValue(doc, "/ticket/@" + LOCALE_FIELD));

			documentMetadata.setPrintTemplate(getXmlFieldValue(doc, "/ticket", formatFields));
			documentMetadata.setCompanyCode(getXmlFieldValue(doc, "/ticket", companyFields));
			
			String docTypeId = getXmlFieldValue(doc, "/ticket", docTypeIdFields);
			if (StringUtils.isNotBlank(docTypeId)) {
			   documentMetadata.setDocTypeId(Long.valueOf(docTypeId));
			}
			documentMetadata.setDocTypeCode(getXmlFieldValue(doc, "/ticket", docTypeCodeFields));
			
			documentMetadata.setDocumentCode(getXmlFieldValue(doc, "/ticket", documentCodeFields));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
