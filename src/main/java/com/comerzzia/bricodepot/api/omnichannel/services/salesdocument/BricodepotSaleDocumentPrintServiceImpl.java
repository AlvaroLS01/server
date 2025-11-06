package com.comerzzia.bricodepot.api.omnichannel.services.salesdocument;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.bricodepot.api.omnichannel.domain.salesdocument.BricodepotPrintableDocument;
import com.comerzzia.bricodepot.api.omnichannel.services.documentprint.BricodepotDocumentPrintService;
import com.comerzzia.core.model.empresas.EmpresaBean;
import com.comerzzia.core.model.tiposdocumentos.TipoDocumentoBean;
import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.servicios.empresas.EmpresaException;
import com.comerzzia.core.servicios.empresas.EmpresaNotFoundException;
import com.comerzzia.core.servicios.empresas.EmpresasService;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.servicios.tipodocumento.TiposDocumentosService;
import com.comerzzia.core.servicios.ventas.tickets.TicketService;
import com.comerzzia.core.util.config.AppInfo;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.LineaTicket;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.SubtotalIvaTicket;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.domain.entity.document.DocumentEntity;
import com.comerzzia.omnichannel.service.document.DocumentService;
import com.comerzzia.omnichannel.service.documentprint.DocumentPrintService;
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

	private static final Logger log = Logger.getLogger(BricodepotSaleDocumentPrintServiceImpl.class);

	private static final String PARAM_FISCAL_DATA_ATCUD = "fiscalData_ACTUD";
	private static final String PARAM_FISCAL_DATA_QR = "fiscalData_QR";
	private static final String PARAM_QR_PORTUGAL = "QR_PORTUGAL";
	private static final String PARAM_DUPLICATE_FLAG = "esDuplicado";
	private static final String PARAM_TICKET = "ticket";
	private static final String PARAM_SUBREPORT_DIR = "SUBREPORT_DIR";
	private static final String PARAM_LOGO = "LOGO";
	private static final String PARAM_DEVOLUCION = "DEVOLUCION";
	private static final String PARAM_LINEAS_AGRUPADAS = "lineasAgrupadas";

	private static final String FACTURA_REPORT_DIRECTORY = "ventas" + File.separator + "facturas" + File.separator;

	private static final String FACTURA_TEMPLATE_BASE = "ventas"+File.separator+"facturas"+File.separator+"facturaA4";
	private static final String FACTURA_TEMPLATE_ORIGINAL = FACTURA_TEMPLATE_BASE+"_Original";
	private static final String FACTURA_TEMPLATE_PORTUGAL = FACTURA_TEMPLATE_BASE+"_PT";
	private static final String FACTURA_TEMPLATE_CATALUNYA = FACTURA_TEMPLATE_BASE+"_CA";
	private static final Set<String> FACTURA_DOCUMENT_TYPES = new HashSet<>(Arrays.asList("FT", "FS", "NC", "VC", "FR"));

	private static final String TAG_FISCAL_DATA = "fiscal_data";
	private static final String TAG_PROPERTY = "property";
	private static final String TAG_NAME = "name";
	private static final String TAG_VALUE = "value";
	private static final String ATCUD = "ATCUD";
	private static final String QR = "QR";
	private static final int QR_IMAGE_SIZE = 200;

	private static final JAXBContext TICKET_JAXB_CONTEXT = createTicketContext();

	private final SaleDocumentService saleDocumentService;
	private final DocumentService documentService;
	private final BricodepotDocumentPrintService documentPrintService;

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private TiposDocumentosService tiposDocumentosService;
	
	@Autowired
	private EmpresasService empresasService;

	@Autowired
	public BricodepotSaleDocumentPrintServiceImpl(SaleDocumentService saleDocumentService, DocumentService documentService,
			BricodepotDocumentPrintService documentPrintService) {
		this.saleDocumentService = saleDocumentService;
		this.documentService = documentService;
		this.documentPrintService = documentPrintService;
	}

	@Override
	public BricodepotPrintableDocument printDocument(IDatosSesion datosSesion, String documentUid, PrintDocumentDTO printRequest) throws ApiException {
		if (printRequest == null)
			throw new ApiException("Solicitud de impresión nula");
		log.debug("printDocument() - Generando documento de venta '" + documentUid + "' con tipo MIME '" + printRequest.getMimeType() + "'");

		Map<String, Object> params = ensureParamsMap(printRequest);

		TicketContext ticketCtx = loadTicketContext(datosSesion, documentUid, params);

		applyInvoiceTemplateByCountry(printRequest, ticketCtx, datosSesion);

		ensureCompanyCodeFromTicket(ticketCtx, params);
		ensureCompanyLogo(datosSesion, params);
		ensureSubreportDirectory(params);
		ensureDevolucionParameter(params, ticketCtx, datosSesion);
		applyDuplicateFlag(printRequest);
		populateFiscalData(datosSesion, documentUid, printRequest);

		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			saleDocumentService.printDocument(out, datosSesion, documentUid, printRequest);
			String outputName = StringUtils.hasText(printRequest.getOutputDocumentName()) ? printRequest.getOutputDocumentName() : documentUid;
			return new BricodepotPrintableDocument(documentUid, outputName, printRequest.getMimeType(), out.toByteArray());
		}
		catch (Exception e) {
			log.error("printDocument() - Error al generar el documento '" + documentUid + "'", e);
			if (e instanceof ApiException)
				throw (ApiException) e;
			throw new ApiException(e.getMessage(), e);
		}
	}

	private Map<String, Object> ensureParamsMap(PrintDocumentDTO dto) {
		Map<String, Object> current = dto.getCustomParams();
		if (current == null)
			current = new HashMap<>();
		else
			current = new HashMap<>(current);
		TicketPreservingMap wrapped = new TicketPreservingMap(current);
		try {
			dto.setCustomParams(wrapped);
		}
		catch (Throwable ignore) {
		}
		return wrapped;
	}

																				 
	private TicketContext loadTicketContext(IDatosSesion datosSesion, String documentUid, Map<String, Object> params) throws ApiException {
		Object ticketParam = params.get(PARAM_TICKET);
		if (ticketParam instanceof TicketVentaAbono) {
			TicketVentaAbono tv = (TicketVentaAbono) ticketParam;
			prepareTicketForPrinting(params, tv);
			return new TicketContext(null, tv);
		}
		TicketBean tb;
		TicketVentaAbono tv;
		try {
			tb = ticketService.consultarTicketUid(documentUid, datosSesion.getUidActividad());
			if (tb == null)
				throw new ApiException("No se encontró ticket para documentUid=" + documentUid);
			tv = parseTicketVenta(tb.getTicket());
		}
		catch (Exception ex) {
			throw new ApiException(ex.getMessage(), ex);
		}
		params.put(PARAM_TICKET, tv);
		prepareTicketForPrinting(params, tv);
		return new TicketContext(tb, tv);
	}

	private void ensureCompanyLogo(IDatosSesion datosSesion, Map<String, Object> params) {
		if (params == null)
			return;
		Object existingLogo = params.get(PARAM_LOGO);
		if (existingLogo instanceof InputStream)
			return;
		if (existingLogo == null)
			params.remove(PARAM_LOGO);

		Object cc = params.get(DocumentPrintService.PARAM_COMPANY_CODE);
		if (!(cc instanceof String) || !StringUtils.hasText((String) cc))
			return;

		String companyCode = ((String) cc).trim();
		try {
			EmpresaBean empresa = empresasService.consultar(datosSesion, companyCode);
			if (empresa != null && empresa.getLogotipo() != null && empresa.getLogotipo().length > 0)
				params.put(PARAM_LOGO, new ByteArrayInputStream(empresa.getLogotipo()));
			else
				params.remove(PARAM_LOGO);
		}
		catch (EmpresaNotFoundException | EmpresaException ex) {
			params.remove(PARAM_LOGO);
		}
	}

																				  
	private void ensureCompanyCodeFromTicket(TicketContext ctx, Map<String, Object> params) {
		if (params == null)
			return;
		final String KEY = DocumentPrintService.PARAM_COMPANY_CODE;
		Object cc = params.get(KEY);
		if (cc instanceof String && StringUtils.hasText((String) cc))
			return;
		if (ctx == null)
			return;
		TicketVentaAbono tv = ctx.getTicketVenta();
		if (tv == null || tv.getCabecera() == null || tv.getCabecera().getEmpresa() == null)
			return;
		String code = tv.getCabecera().getEmpresa().getCodEmpresa();
		if (StringUtils.hasText(code))
			params.put(KEY, code.trim());
	}

	private void ensureSubreportDirectory(Map<String, Object> params) {
		if (params.containsKey(PARAM_SUBREPORT_DIR) && params.get(PARAM_SUBREPORT_DIR) != null)
			return;
		String basePath = AppInfo.getInformesInfo().getRutaBase();
		if (!StringUtils.hasText(basePath))
			return;
		String normalized = basePath.replace('\\', File.separatorChar).replace('/', File.separatorChar);
		if (!normalized.endsWith(File.separator))
			normalized = normalized + File.separator;
		params.put(PARAM_SUBREPORT_DIR, normalized + FACTURA_REPORT_DIRECTORY);
	}

	private void prepareTicketForPrinting(Map<String, Object> params, TicketVentaAbono ticketVenta) {
		if (params == null || ticketVenta == null)
			return;
		List<LineaTicket> agregadas = aggregateTicketLines(ticketVenta);
		params.put(PARAM_LINEAS_AGRUPADAS, new ArrayList<>(agregadas));
	}

	private void applyInvoiceTemplateByCountry(PrintDocumentDTO printRequest, TicketContext ctx, IDatosSesion datosSesion) {
		if (printRequest == null || ctx == null || datosSesion == null)
			return;

		TicketVentaAbono ticketVenta = ctx.getTicketVenta();
		if (ticketVenta == null || ticketVenta.getCabecera() == null)
			return;

		String docType = normalizeDocType(ticketVenta.getCabecera().getCodTipoDocumento());
		if (!FACTURA_DOCUMENT_TYPES.contains(docType))
			return;

		Long tipoDocumentoId = ticketVenta.getCabecera().getTipoDocumento();
		if (tipoDocumentoId == null)
			return;

		try {
			TipoDocumentoBean tipo = tiposDocumentosService.consultar(datosSesion, tipoDocumentoId);
			String customTemplate = resolveCustomInvoiceTemplate(printRequest.getPrintTemplate());
			if (StringUtils.hasText(customTemplate)) {
				printRequest.setPrintTemplate(customTemplate);
				return;
			}

			String template = selectTemplateForCountry(tipo != null ? tipo.getCodPais() : null, normalizeTemplate(printRequest.getPrintTemplate()));
			if (StringUtils.hasText(template))
				printRequest.setPrintTemplate(template);
		}
		catch (Exception e) {
			log.debug("applyInvoiceTemplateByCountry() - No se pudo determinar la plantilla por país", e);
		}
	}

	private String selectTemplateForCountry(String countryCode, String currentTemplate) {
		String normalizedCountry = normalizeCountry(countryCode);
		if ("PT".equals(normalizedCountry))
			return FACTURA_TEMPLATE_PORTUGAL;
		if ("CA".equals(normalizedCountry))
			return FACTURA_TEMPLATE_CATALUNYA;
		if (!StringUtils.hasText(normalizedCountry) || "ES".equals(normalizedCountry))
			return FACTURA_TEMPLATE_ORIGINAL;

		if (FACTURA_TEMPLATE_PORTUGAL.equals(currentTemplate) || FACTURA_TEMPLATE_CATALUNYA.equals(currentTemplate))
			return currentTemplate;
		return FACTURA_TEMPLATE_ORIGINAL;
	}

	private String normalizeDocType(String code) {
		if (!StringUtils.hasText(code))
			return "";
		String trimmed = code.trim();
		int slash = trimmed.indexOf('/');
		if (slash >= 0)
			trimmed = trimmed.substring(0, slash);
		int space = trimmed.indexOf(' ');
		if (space >= 0)
			trimmed = trimmed.substring(0, space);
		return trimmed.toUpperCase(Locale.ROOT);
	}

	private String normalizeTemplate(String template) {
		if (!StringUtils.hasText(template))
			return null;
		String normalized = template.replace('\\', '/').trim();
		if (FACTURA_TEMPLATE_BASE.equals(normalized))
			return FACTURA_TEMPLATE_ORIGINAL;
		if (!normalized.contains("/"))
			return FACTURA_TEMPLATE_BASE;
		return normalized;
	}

	private String resolveCustomInvoiceTemplate(String template) {
		if (!StringUtils.hasText(template) || documentPrintService == null)
			return null;

		String existingTemplate = documentPrintService.findExistingTemplate(template);
		if (!StringUtils.hasText(existingTemplate))
			return null;

		return existingTemplate.replace('\\', '/').trim();
	}

	private String normalizeCountry(String countryCode) {
		if (!StringUtils.hasText(countryCode))
			return null;
		return countryCode.trim().toUpperCase(Locale.ROOT);
	}

	private List<LineaTicket> aggregateTicketLines(TicketVentaAbono ticketVenta) {
		List<LineaTicket> original = ticketVenta != null ? ticketVenta.getLineas() : null;
		if (original == null || original.isEmpty())
			return Collections.emptyList();

		List<LineaTicket> working = new ArrayList<>();
		for (LineaTicket l : original)
			if (l != null)
				working.add(l);

		List<LineaTicket> out = new ArrayList<>(working.size());
		while (!working.isEmpty()) {
			LineaTicket base = working.remove(0);
			if (base == null)
				continue;

			BigDecimal q = safe(base.getCantidad());
			BigDecimal promo = safe(base.getImporteTotalPromociones());
			BigDecimal totalConDto = safe(base.getImporteTotalConDto());
			BigDecimal importe = safe(base.getImporteConDto());
			List<LineaTicket> merged = new ArrayList<>();

			Iterator<LineaTicket> it = working.iterator();
			while (it.hasNext()) {
				LineaTicket cand = it.next();
				if (cand == null) {
					it.remove();
					continue;
				}
				if (haveSameSaleConditions(base, cand)) {
					q = q.add(safe(cand.getCantidad()));
					promo = promo.add(safe(cand.getImporteTotalPromociones()));
					totalConDto = totalConDto.add(safe(cand.getImporteTotalConDto()));
					importe = importe.add(safe(cand.getImporteConDto()));
					merged.add(cand);
					it.remove();
				}
			}

			if (isZero(q))
				continue;

			base.setCantidad(q);
			base.setImporteTotalPromociones(promo);
			base.setImporteTotalConDto(totalConDto);
			base.setImporteConDto(importe);

			applyTaxBreakdown(base, ticketVenta, q);
			if (!merged.isEmpty()) {
				String desg2 = base.getDesglose2();
				String codImpFmt = base.getCodImp();
				for (LineaTicket m : merged) {
					m.setDesglose2(desg2);
					m.setCodImp(codImpFmt);
				}
			}
			out.add(base);
		}

		List<LineaTicket> ticketLines = ticketVenta.getLineas();
		if (ticketLines != null) {
			try {
				ticketLines.clear();
				ticketLines.addAll(out);
			}
			catch (UnsupportedOperationException ignored) {
			}
		}
		return out;
	}

	private boolean haveSameSaleConditions(LineaTicket a, LineaTicket b) {
		if (a == null || b == null)
			return false;
		if (!Objects.equals(a.getCodArticulo(), b.getCodArticulo()))
			return false;
		if (safe(a.getPrecioTotalConDto()).compareTo(safe(b.getPrecioTotalConDto())) != 0)
			return false;
		BigDecimal qa = safe(a.getCantidad());
		BigDecimal qb = safe(b.getCantidad());
		return qa.signum() == qb.signum();
	}

	private void applyTaxBreakdown(LineaTicket l, TicketVentaAbono ticketVenta, BigDecimal qty) {
		if (l == null || ticketVenta == null || ticketVenta.getCabecera() == null)
			return;

		List<SubtotalIvaTicket> subs = ticketVenta.getCabecera().getSubtotalesIva();
		if (subs == null || subs.isEmpty())
			return;

		String code = normalize(l.getCodImp());
		for (SubtotalIvaTicket s : subs) {
			if (s == null)
				continue;
			if (!normalize(s.getCodImp()).equals(code))
				continue;

			l.setCodImp(formatPercentage(s.getPorcentaje()));
			BigDecimal priceWithDto = safe(l.getPrecioConDto());
			BigDecimal pct = safe(s.getPorcentaje());
			BigDecimal tax = priceWithDto.multiply(qty).multiply(pct).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
			l.setDesglose2(formatDecimal(tax, 4));
			return;
		}
	}

	private void ensureDevolucionParameter(Map<String, Object> params, TicketContext ctx, IDatosSesion datosSesion) {
		if (Boolean.TRUE.equals(params.get(PARAM_DEVOLUCION)))
			return;
		TicketVentaAbono tv = ctx != null ? ctx.getTicketVenta() : null;
		if (tv == null || tv.getCabecera() == null)
			return;

		boolean devolucion = false;
		try {
			String codTipoDocumento = tv.getCabecera().getCodTipoDocumento();
			if ("FR".equalsIgnoreCase(codTipoDocumento)) {
				devolucion = true;
			}
			else if ("NC".equalsIgnoreCase(codTipoDocumento)) {
				TipoDocumentoBean tipo = tiposDocumentosService.consultar(datosSesion, tv.getCabecera().getTipoDocumento());
				if (tipo != null && !"ES".equalsIgnoreCase(tipo.getCodPais()))
					devolucion = true;
			}
		}
		catch (Exception ignored) {
		}
		params.put(PARAM_DEVOLUCION, devolucion);
	}

	private void applyDuplicateFlag(PrintDocumentDTO printRequest) {
		if (printRequest == null || !Boolean.TRUE.equals(printRequest.getCopy()))
			return;
		Map<String, Object> params = printRequest.getCustomParams();
		if (params != null && !params.containsKey(PARAM_DUPLICATE_FLAG))
			params.put(PARAM_DUPLICATE_FLAG, Boolean.TRUE);
	}

	private void populateFiscalData(IDatosSesion datosSesion, String documentUid, PrintDocumentDTO printRequest) {
		if (printRequest == null)
			return;
		try {
			DocumentEntity entity = documentService.findById(datosSesion, documentUid);
			if (entity == null)
				return;
			byte[] content = entity.getDocumentContent();
			if (content == null || content.length == 0)
				return;

			Map<String, Object> params = printRequest.getCustomParams();
			FiscalDocumentData fd = extractFiscalData(content);

			if (!params.containsKey(PARAM_FISCAL_DATA_ATCUD) && StringUtils.hasText(fd.getAtcud()))
				params.put(PARAM_FISCAL_DATA_ATCUD, fd.getAtcud());

			if (!params.containsKey(PARAM_FISCAL_DATA_QR) && StringUtils.hasText(fd.getQr())) {
				params.put(PARAM_FISCAL_DATA_QR, fd.getQr());
				InputStream qr = createQrImage(fd.getQr());
				if (qr != null && !params.containsKey(PARAM_QR_PORTUGAL))
					params.put(PARAM_QR_PORTUGAL, qr);
			}
		}
		catch (Exception ignored) {
		}
	}

	private FiscalDocumentData extractFiscalData(byte[] content) {
		if (content == null || content.length == 0)
			return FiscalDocumentData.empty();
		try {
			if (isLikelyJson(content))
				return parseFiscalDataFromJson(content);
			return parseFiscalDataFromXml(content);
		}
		catch (Exception e) {
			return FiscalDocumentData.empty();
		}
	}

	private boolean isLikelyJson(byte[] content) {
		for (byte b : content) {
			if (b <= ' ')
				continue;
			return b == '{' || b == '[';
		}
		return false;
	}

	private FiscalDocumentData parseFiscalDataFromXml(byte[] xml) throws Exception {
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		f.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		f.setFeature("http://xml.org/sax/features/external-general-entities", false);
		f.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		DocumentBuilder b = f.newDocumentBuilder();
		try (ByteArrayInputStream in = new ByteArrayInputStream(xml)) {
			Document doc = b.parse(in);
			Element root = doc.getDocumentElement();
			if (root == null)
				return FiscalDocumentData.empty();
			NodeList fiscalNodes = root.getElementsByTagName(TAG_FISCAL_DATA);
			if (fiscalNodes == null || fiscalNodes.getLength() == 0)
				return FiscalDocumentData.empty();
			FiscalDocumentData res = new FiscalDocumentData();
			for (int i = 0; i < fiscalNodes.getLength(); i++) {
				Node n = fiscalNodes.item(i);
				if (n instanceof Element)
					populateFromFiscalElement((Element) n, res);
			}
			return res;
		}
	}

	private void populateFromFiscalElement(Element el, FiscalDocumentData res) {
		NodeList props = el.getElementsByTagName(TAG_PROPERTY);
		for (int i = 0; i < props.getLength(); i++) {
			Node n = props.item(i);
			if (n instanceof Element) {
				Element p = (Element) n;
				String name = getChildTextContent(p, TAG_NAME);
				String value = getChildTextContent(p, TAG_VALUE);
				applyFiscalProperty(res, name, value);
			}
		}
	}

	private String getChildTextContent(Element parent, String tagName) {
		NodeList nodes = parent.getElementsByTagName(tagName);
		if (nodes == null || nodes.getLength() == 0)
			return null;
		Node n = nodes.item(0);
		String txt = n != null ? n.getTextContent() : null;
		return StringUtils.hasText(txt) ? txt.trim() : null;
	}

	private FiscalDocumentData parseFiscalDataFromJson(byte[] json) throws IOException {
		ObjectMapper om = new ObjectMapper();
		JsonNode root = om.readTree(json);
		FiscalDocumentData res = new FiscalDocumentData();
		traverseJson(root, res);
		return res;
	}

	private void traverseJson(JsonNode node, FiscalDocumentData res) {
		if (node == null)
			return;
		if (node.isObject()) {
			Iterator<Map.Entry<String, JsonNode>> it = node.fields();
			while (it.hasNext()) {
				Map.Entry<String, JsonNode> e = it.next();
				String key = e.getKey();
				JsonNode val = e.getValue();
				if (key != null && (TAG_FISCAL_DATA.equalsIgnoreCase(key) || "fiscalData".equalsIgnoreCase(key)))
					parseFiscalJsonNode(val, res);
				traverseJson(val, res);
			}
		}
		else if (node.isArray()) {
			for (JsonNode c : node)
				traverseJson(c, res);
		}
	}

	private void parseFiscalJsonNode(JsonNode fiscalNode, FiscalDocumentData res) {
		if (fiscalNode == null)
			return;
		if (fiscalNode.isObject()) {
			if (fiscalNode.has(ATCUD))
				applyFiscalProperty(res, ATCUD, getJsonText(fiscalNode.get(ATCUD)));
			if (fiscalNode.has(QR))
				applyFiscalProperty(res, QR, getJsonText(fiscalNode.get(QR)));
			if (fiscalNode.has("properties"))
				parseFiscalJsonProperties(fiscalNode.get("properties"), res);
			if (fiscalNode.has("property"))
				parseFiscalJsonProperties(fiscalNode.get("property"), res);
		}
		else if (fiscalNode.isArray()) {
			parseFiscalJsonProperties(fiscalNode, res);
		}
	}

	private void parseFiscalJsonProperties(JsonNode props, FiscalDocumentData res) {
		if (props == null)
			return;
		for (JsonNode p : props) {
			if (p == null)
				continue;
			if (p.isObject()) {
				String name = getJsonText(p.get(TAG_NAME));
				String value = getJsonText(p.get(TAG_VALUE));
				if (!StringUtils.hasText(value))
					value = getJsonText(p.get("valor"));
				applyFiscalProperty(res, name, value);
			}
			else {
				parseFiscalJsonNode(p, res);
			}
		}
	}

	private String getJsonText(JsonNode n) {
		if (n == null)
			return null;
		if (n.isValueNode()) {
			String t = n.asText();
			return StringUtils.hasText(t) ? t.trim() : null;
		}
		return null;
	}

	private void applyFiscalProperty(FiscalDocumentData res, String name, String value) {
		if (!StringUtils.hasText(name) || !StringUtils.hasText(value) || res == null)
			return;
		if (ATCUD.equalsIgnoreCase(name) && !StringUtils.hasText(res.getAtcud()))
			res.setAtcud(value.trim());
		else if (QR.equalsIgnoreCase(name) && !StringUtils.hasText(res.getQr()))
			res.setQr(value.trim());
	}

	private InputStream createQrImage(String base64Value) {
		if (!StringUtils.hasText(base64Value))
			return null;
		try {
			byte[] decoded = Base64.getMimeDecoder().decode(base64Value.trim());
			String qrText = new String(decoded, StandardCharsets.UTF_8);
			if (!StringUtils.hasText(qrText))
				return null;

			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix matrix = writer.encode(qrText, BarcodeFormat.QR_CODE, QR_IMAGE_SIZE, QR_IMAGE_SIZE);
			BufferedImage img = MatrixToImageWriter.toBufferedImage(matrix);

			try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				ImageIO.write(img, "jpeg", out);
				return new ByteArrayInputStream(out.toByteArray());
			}
		}
		catch (IllegalArgumentException | IOException | WriterException e) {
			log.warn("createQrImage() - No se pudo generar la imagen QR", e);
		}
		return null;
	}

	private BigDecimal safe(BigDecimal v) {
		return v != null ? v : BigDecimal.ZERO;
	}

	private boolean isZero(BigDecimal v) {
		return safe(v).compareTo(BigDecimal.ZERO) == 0;
	}

	private String normalize(String v) {
		return v != null ? v.trim() : "";
	}

	private String formatPercentage(BigDecimal v) {
		return v == null ? "" : v.stripTrailingZeros().toPlainString();
	}

	private String formatDecimal(BigDecimal v, int scale) {
		return v == null ? null : v.setScale(scale, RoundingMode.HALF_UP).toPlainString();
	}

	private TicketVentaAbono parseTicketVenta(byte[] ticketData) throws ApiException {
		if (ticketData == null || ticketData.length == 0)
			throw new ApiException("Datos de ticket vacíos");
		try (ByteArrayInputStream in = new ByteArrayInputStream(ticketData)) {
			Unmarshaller u = TICKET_JAXB_CONTEXT.createUnmarshaller();
			Object parsed = u.unmarshal(in);
			if (parsed instanceof TicketVentaAbono)
				return (TicketVentaAbono) parsed;
			throw new ApiException("No se pudo parsear el XML de ticket a TicketVentaAbono");
		}
		catch (JAXBException | IOException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	private static JAXBContext createTicketContext() {
		try {
			return JAXBContext.newInstance(TicketVentaAbono.class);
		}
		catch (JAXBException e) {
			throw new IllegalStateException("No se pudo crear JAXBContext para TicketVentaAbono", e);
		}
	}

	private static final class TicketPreservingMap extends HashMap<String, Object> {

		private static final long serialVersionUID = 1L;

		TicketPreservingMap(Map<String, Object> existing) {
			super(existing != null ? Math.max(existing.size() * 2, 16) : 16);
			if (existing != null)
				super.putAll(existing);
		}

		@Override
		public Object put(String key, Object value) {
			if (PARAM_TICKET.equals(key) && !(value instanceof TicketVentaAbono))
				return super.get(key);
			return super.put(key, value);
		}

		@Override
		public void putAll(Map<? extends String, ?> m) {
			if (m == null)
				return;
			for (Map.Entry<? extends String, ?> e : m.entrySet())
				put(e.getKey(), e.getValue());
		}
	}

	private static final class TicketContext {

		private final TicketBean ticketBean;
		private final TicketVentaAbono ticketVenta;

		TicketContext(TicketBean ticketBean, TicketVentaAbono ticketVenta) {
			this.ticketBean = ticketBean;
			this.ticketVenta = ticketVenta;
		}

		TicketBean getTicketBean() {
			return ticketBean;
		}

		TicketVentaAbono getTicketVenta() {
			return ticketVenta;
		}
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
