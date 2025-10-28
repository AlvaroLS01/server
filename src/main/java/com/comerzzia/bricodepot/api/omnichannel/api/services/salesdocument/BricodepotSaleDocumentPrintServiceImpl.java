package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.BricodepotPrintedDocument;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.domain.entity.document.DocumentEntity;
import com.comerzzia.omnichannel.service.document.DocumentService;
import com.comerzzia.omnichannel.service.salesdocument.SaleDocumentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class BricodepotSaleDocumentPrintServiceImpl implements BricodepotSaleDocumentPrintService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BricodepotSaleDocumentPrintServiceImpl.class);
	private static final String PARAM_FISCAL_DATA_ATCUD = "fiscalData_ACTUD";
	private static final String PARAM_FISCAL_DATA_QR = "fiscalData_QR";
	private static final String PARAM_QR_PORTUGAL = "QR_PORTUGAL";
	private static final String PARAM_DUPLICATE_FLAG = "esDuplicado";
	private static final String TAG_FISCAL_DATA = "fiscal_data";
	private static final String TAG_PROPERTY = "property";
	private static final String TAG_NAME = "name";
	private static final String TAG_VALUE = "value";
	private static final String ATCUD = "ATCUD";
	private static final String QR = "QR";
	private static final int QR_IMAGE_SIZE = 200;

	private final SaleDocumentService saleDocumentService;
	private final DocumentService documentService;

	@Autowired
	public BricodepotSaleDocumentPrintServiceImpl(SaleDocumentService saleDocumentService, DocumentService documentService) {
		this.saleDocumentService = saleDocumentService;
		this.documentService = documentService;
	}

	@Override
	public BricodepotPrintedDocument printDocument(IDatosSesion datosSesion, String documentUid, PrintDocumentDTO printRequest) throws ApiException {
		LOGGER.debug("printDocument() - Generating sales document '{}' with mime type '{}'", documentUid, printRequest.getMimeType());

		populateFiscalData(datosSesion, documentUid, printRequest);
		applyDuplicateFlag(printRequest);

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			saleDocumentService.printDocument(outputStream, datosSesion, documentUid, printRequest);

			String outputDocumentName = printRequest.getOutputDocumentName();
			if (!StringUtils.hasText(outputDocumentName)) {
				outputDocumentName = documentUid;
			}

			return new BricodepotPrintedDocument(documentUid, outputDocumentName, printRequest.getMimeType(), outputStream.toByteArray());
		}
		catch (Exception exception) {
			LOGGER.error("printDocument() - Error generating sales document '{}'", documentUid, exception);
			if (exception instanceof ApiException) {
				throw (ApiException) exception;
			}
			throw new ApiException(exception.getMessage(), exception);
		}
	}

	private void populateFiscalData(IDatosSesion datosSesion, String documentUid, PrintDocumentDTO printRequest) {
		if (printRequest == null) {
			return;
		}

		try {
			DocumentEntity documentEntity = documentService.findById(datosSesion, documentUid);
			if (documentEntity == null) {
				LOGGER.debug("populateFiscalData() - Document '{}' not found, skipping fiscal data extraction", documentUid);
				return;
			}

			byte[] content = documentEntity.getDocumentContent();
			if (content == null || content.length == 0) {
				LOGGER.debug("populateFiscalData() - Document '{}' has no content, skipping fiscal data extraction", documentUid);
				return;
			}

			Map<String, Object> customParams = printRequest.getCustomParams();
			FiscalDocumentData fiscalData = extractFiscalData(content);

			if (!customParams.containsKey(PARAM_FISCAL_DATA_ATCUD) && StringUtils.hasText(fiscalData.getAtcud())) {
				customParams.put(PARAM_FISCAL_DATA_ATCUD, fiscalData.getAtcud());
			}

			if (!customParams.containsKey(PARAM_FISCAL_DATA_QR) && StringUtils.hasText(fiscalData.getQr())) {
				customParams.put(PARAM_FISCAL_DATA_QR, fiscalData.getQr());

				InputStream qrStream = createQrImage(fiscalData.getQr());
				if (qrStream != null && !customParams.containsKey(PARAM_QR_PORTUGAL)) {
					customParams.put(PARAM_QR_PORTUGAL, qrStream);
				}
			}
		}
		catch (Exception exception) {
			LOGGER.warn("populateFiscalData() - Unable to extract fiscal data for document '{}'", documentUid, exception);
		}
	}

	private void applyDuplicateFlag(PrintDocumentDTO printRequest) {
		if (printRequest == null || !Boolean.TRUE.equals(printRequest.getCopy())) {
			return;
		}

		Map<String, Object> customParams = printRequest.getCustomParams();
		if (!customParams.containsKey(PARAM_DUPLICATE_FLAG)) {
			customParams.put(PARAM_DUPLICATE_FLAG, Boolean.TRUE);
			LOGGER.debug("applyDuplicateFlag() - Flagging document copy request with parameter '{}'", PARAM_DUPLICATE_FLAG);
		}
	}

	private FiscalDocumentData extractFiscalData(byte[] content) {
		if (content == null || content.length == 0) {
			return FiscalDocumentData.empty();
		}

		try {
			if (isLikelyJson(content)) {
				return parseFiscalDataFromJson(content);
			}

			return parseFiscalDataFromXml(content);
		}
		catch (Exception exception) {
			LOGGER.debug("extractFiscalData() - Unable to parse fiscal data as structured content", exception);
			return FiscalDocumentData.empty();
		}
	}

	private boolean isLikelyJson(byte[] content) {
		for (byte candidate : content) {
			if (candidate <= ' ') {
				continue;
			}
			return candidate == '{' || candidate == '[';
		}
		return false;
	}

	private FiscalDocumentData parseFiscalDataFromXml(byte[] xmlContent) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		factory.setXIncludeAware(false);
		factory.setExpandEntityReferences(false);

		DocumentBuilder builder = factory.newDocumentBuilder();

		try (ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlContent)) {
			Document document = builder.parse(inputStream);
			Element root = document.getDocumentElement();
			if (root == null) {
				return FiscalDocumentData.empty();
			}

			NodeList fiscalNodes = root.getElementsByTagName(TAG_FISCAL_DATA);
			if (fiscalNodes == null || fiscalNodes.getLength() == 0) {
				return FiscalDocumentData.empty();
			}

			FiscalDocumentData result = new FiscalDocumentData();
			for (int index = 0; index < fiscalNodes.getLength(); index++) {
				Node node = fiscalNodes.item(index);
				if (node instanceof Element) {
					populateFromFiscalElement((Element) node, result);
				}
			}
			return result;
		}
	}

	private void populateFromFiscalElement(Element fiscalDataElement, FiscalDocumentData result) {
		if (fiscalDataElement == null) {
			return;
		}

		NodeList propertyNodes = fiscalDataElement.getElementsByTagName(TAG_PROPERTY);
		if (propertyNodes == null || propertyNodes.getLength() == 0) {
			return;
		}

		for (int i = 0; i < propertyNodes.getLength(); i++) {
			Node node = propertyNodes.item(i);
			if (!(node instanceof Element)) {
				continue;
			}

			Element propertyElement = (Element) node;
			String name = getChildTextContent(propertyElement, TAG_NAME);
			String value = getChildTextContent(propertyElement, TAG_VALUE);
			applyFiscalProperty(result, name, value);
		}
	}

	private String getChildTextContent(Element parent, String tagName) {
		if (parent == null) {
			return null;
		}

		NodeList nodes = parent.getElementsByTagName(tagName);
		if (nodes == null || nodes.getLength() == 0) {
			return null;
		}

		Node node = nodes.item(0);
		if (node == null) {
			return null;
		}

		String text = node.getTextContent();
		return StringUtils.hasText(text) ? text.trim() : null;
	}

	private FiscalDocumentData parseFiscalDataFromJson(byte[] jsonContent) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonContent);

		FiscalDocumentData result = new FiscalDocumentData();
		traverseJson(rootNode, result);
		return result;
	}

	private void traverseJson(JsonNode node, FiscalDocumentData result) {
		if (node == null) {
			return;
		}

		if (node.isObject()) {
			Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
			while (fields.hasNext()) {
				Map.Entry<String, JsonNode> entry = fields.next();
				String key = entry.getKey();
				JsonNode value = entry.getValue();

				if (key != null && (TAG_FISCAL_DATA.equalsIgnoreCase(key) || "fiscalData".equalsIgnoreCase(key))) {
					parseFiscalJsonNode(value, result);
				}

				traverseJson(value, result);
			}
		}
		else if (node.isArray()) {
			for (JsonNode child : node) {
				traverseJson(child, result);
			}
		}
	}

	private void parseFiscalJsonNode(JsonNode fiscalNode, FiscalDocumentData result) {
		if (fiscalNode == null) {
			return;
		}

		if (fiscalNode.isObject()) {
			if (fiscalNode.has(ATCUD)) {
				applyFiscalProperty(result, ATCUD, getJsonText(fiscalNode.get(ATCUD)));
			}
			if (fiscalNode.has(QR)) {
				applyFiscalProperty(result, QR, getJsonText(fiscalNode.get(QR)));
			}

			if (fiscalNode.has("properties")) {
				parseFiscalJsonProperties(fiscalNode.get("properties"), result);
			}
			if (fiscalNode.has("property")) {
				parseFiscalJsonProperties(fiscalNode.get("property"), result);
			}
		}
		else if (fiscalNode.isArray()) {
			parseFiscalJsonProperties(fiscalNode, result);
		}
	}

	private void parseFiscalJsonProperties(JsonNode propertiesNode, FiscalDocumentData result) {
		if (propertiesNode == null) {
			return;
		}

		for (JsonNode propertyNode : propertiesNode) {
			if (propertyNode == null) {
				continue;
			}

			if (propertyNode.isObject()) {
				String name = getJsonText(propertyNode.get(TAG_NAME));
				String value = getJsonText(propertyNode.get(TAG_VALUE));
				if (!StringUtils.hasText(value)) {
					value = getJsonText(propertyNode.get("valor"));
				}
				applyFiscalProperty(result, name, value);
			}
			else {
				parseFiscalJsonNode(propertyNode, result);
			}
		}
	}

	private String getJsonText(JsonNode node) {
		if (node == null) {
			return null;
		}

		if (node.isValueNode()) {
			String text = node.asText();
			return StringUtils.hasText(text) ? text.trim() : null;
		}

		return null;
	}

	private void applyFiscalProperty(FiscalDocumentData result, String name, String value) {
		if (!StringUtils.hasText(name) || !StringUtils.hasText(value) || result == null) {
			return;
		}

		if (ATCUD.equalsIgnoreCase(name) && !StringUtils.hasText(result.getAtcud())) {
			result.setAtcud(value.trim());
		}
		else if (QR.equalsIgnoreCase(name) && !StringUtils.hasText(result.getQr())) {
			result.setQr(value.trim());
		}
	}

	private InputStream createQrImage(String base64Value) {
		if (!StringUtils.hasText(base64Value)) {
			return null;
		}

		try {
			byte[] decoded = Base64.getDecoder().decode(base64Value.trim());
			String qrText = new String(decoded, StandardCharsets.UTF_8);
			if (!StringUtils.hasText(qrText)) {
				return null;
			}

			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix bitMatrix = writer.encode(qrText, BarcodeFormat.QR_CODE, QR_IMAGE_SIZE, QR_IMAGE_SIZE);
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				ImageIO.write(qrImage, "jpeg", outputStream);
				return new ByteArrayInputStream(outputStream.toByteArray());
			}
		}
		catch (IllegalArgumentException | IOException | WriterException exception) {
			LOGGER.warn("createQrImage() - Unable to generate QR image from fiscal data", exception);
		}

		return null;
	}

	private static final class FiscalDocumentData {

		private String atcud;
		private String qr;

		static FiscalDocumentData empty() {
			return new FiscalDocumentData();
		}

		String getAtcud() {
			return atcud;
		}

		void setAtcud(String atcud) {
			this.atcud = atcud;
		}

		String getQr() {
			return qr;
		}

		void setQr(String qr) {
			this.qr = qr;
		}
	}
}