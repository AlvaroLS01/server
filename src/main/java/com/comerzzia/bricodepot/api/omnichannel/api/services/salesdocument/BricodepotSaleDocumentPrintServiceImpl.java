package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument;

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
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.BricodepotPrintableDocument;
import com.comerzzia.core.model.empresas.EmpresaBean;
import com.comerzzia.core.model.tiposdocumentos.TipoDocumentoBean;
import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.servicios.empresas.EmpresaException;
import com.comerzzia.core.servicios.empresas.EmpresaNotFoundException;
import com.comerzzia.core.servicios.empresas.EmpresasService;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.servicios.tipodocumento.ServicioTiposDocumentosImpl;
import com.comerzzia.core.servicios.ventas.tickets.ServicioTicketsImpl;
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

    private static Logger log = Logger.getLogger(BricodepotSaleDocumentPrintServiceImpl.class);

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

	@Autowired
	private EmpresasService empresasService;

	@Autowired
	public BricodepotSaleDocumentPrintServiceImpl(SaleDocumentService saleDocumentService, DocumentService documentService) {
		this.saleDocumentService = saleDocumentService;
		this.documentService = documentService;
	}

	@Override
	public BricodepotPrintableDocument printDocument(IDatosSesion datosSesion, String documentUid, PrintDocumentDTO printRequest) throws ApiException {
                if (log.isDebugEnabled()) {
                        String mimeType = printRequest != null ? printRequest.getMimeType() : null;
                        log.debug("Generando documento de venta '" + documentUid + "' con tipo MIME '" + mimeType + "'");
                }

		if (printRequest == null) {
			throw new ApiException("Solicitud de impresión nula");
		}

		// Asegura un mapa de parámetros controlado (sin reflexión)
		Map<String, Object> customParams = ensureParamsMap(printRequest);

		// Cargar ticket primero para que el resto dependa de él
		TicketContext ticketCtx = ensureTicketParameter(datosSesion, documentUid, customParams);

		// Derivados
		ensureCompanyCodeFromTicketIfMissing(datosSesion, documentUid, customParams);
		ensureCompanyLogo(datosSesion, customParams);
		ensureSubreportDirectory(customParams);
		ensureDevolucionParameter(customParams, ticketCtx, datosSesion);
		applyDuplicateFlag(printRequest);
		populateFiscalData(datosSesion, documentUid, printRequest); // añade ATCUD/QR si existen

		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			saleDocumentService.printDocument(out, datosSesion, documentUid, printRequest);

			String outputName = printRequest.getOutputDocumentName();
			if (!StringUtils.hasText(outputName)) {
				outputName = documentUid;
			}
			return new BricodepotPrintableDocument(documentUid, outputName, printRequest.getMimeType(), out.toByteArray());
		}
                catch (Exception e) {
                        log.error("Error al generar el documento '" + documentUid + "'", e);
			if (e instanceof ApiException)
				throw (ApiException) e;
			throw new ApiException(e.getMessage(), e);
		}
	}

	/* ===================== Soporte parámetros y logo ===================== */

	/**
	 * Crea un HashMap si es nulo y sustituye por un TicketPreservingMap para evitar que terceros pisen el parámetro
	 * 'ticket' con objetos no válidos.
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> ensureParamsMap(PrintDocumentDTO dto) {
		Map<String, Object> current = dto.getCustomParams();
		if (current == null) {
			current = new HashMap<>();
		}
		else if (!(current instanceof Map)) {
			current = new HashMap<>(current);
		}
		else {
			current = new HashMap<>(current); // copiamos para envolver limpio
		}

		TicketPreservingMap wrapped = new TicketPreservingMap(current);

		try {
			// Si existe setter, úsalo. (La mayoría de DTOs lo tienen)
			dto.setCustomParams(wrapped);
		}
		catch (Throwable ignore) {
			// Si no existiera setter, seguimos trabajando sobre 'wrapped'
			// pero al menos devolvemos el mapa para usarlo en esta clase.
		}
		return wrapped;
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
                        if (empresa != null && empresa.getLogotipo() != null && empresa.getLogotipo().length > 0) {
                                params.put(PARAM_LOGO, new ByteArrayInputStream(empresa.getLogotipo()));
                                log.debug("Logo cargado desde base de datos para la empresa '" + companyCode + "'");
                        }
                        else {
                                params.remove(PARAM_LOGO);
                                log.debug("Sin logo disponible en base de datos para la empresa '" + companyCode + "'");
                        }
                }
                catch (EmpresaNotFoundException | EmpresaException ex) {
                        params.remove(PARAM_LOGO);
                        log.warn("No se pudo cargar el logo para la empresa '" + companyCode + "'", ex);
                }
        }

	private void ensureCompanyCodeFromTicketIfMissing(IDatosSesion datosSesion, String documentUid, Map<String, Object> params) {
		final String KEY = DocumentPrintService.PARAM_COMPANY_CODE;
		Object cc = params.get(KEY);
		if (cc instanceof String && StringUtils.hasText((String) cc))
			return;

		try {
			Object t = params.get(PARAM_TICKET);
			if (t instanceof TicketVentaAbono) {
				TicketVentaAbono tv = (TicketVentaAbono) t;
				if (tv.getCabecera() != null && tv.getCabecera().getEmpresa() != null && StringUtils.hasText(tv.getCabecera().getEmpresa().getCodEmpresa())) {
					params.put(KEY, tv.getCabecera().getEmpresa().getCodEmpresa().trim());
					return;
				}
			}
			TicketBean tb = ServicioTicketsImpl.get().consultarTicketUid(documentUid, datosSesion.getUidActividad());
			if (tb != null && tb.getTicket() != null && tb.getTicket().length > 0) {
				TicketVentaAbono tv = parseTicketVenta(tb.getTicket());
				if (tv != null && tv.getCabecera() != null && tv.getCabecera().getEmpresa() != null && StringUtils.hasText(tv.getCabecera().getEmpresa().getCodEmpresa())) {
					params.put(KEY, tv.getCabecera().getEmpresa().getCodEmpresa().trim());
				}
			}
		}
                catch (Exception e) {
                        log.debug("No fue posible obtener el código de empresa del ticket '" + documentUid + "': " + e.getMessage());
                }
        }

	private void ensureSubreportDirectory(Map<String, Object> params) {
		if (params.containsKey(PARAM_SUBREPORT_DIR) && params.get(PARAM_SUBREPORT_DIR) != null)
			return;

		String basePath = AppInfo.getInformesInfo().getRutaBase();
                if (!StringUtils.hasText(basePath)) {
                        log.debug("La ruta base de informes no está configurada");
                        return;
                }
		String normalized = basePath.replace('\\', File.separatorChar).replace('/', File.separatorChar);
		if (!normalized.endsWith(File.separator))
			normalized = normalized + File.separator;

		params.put(PARAM_SUBREPORT_DIR, normalized + FACTURA_REPORT_DIRECTORY);
	}

	/* ===================== Ticket y líneas ===================== */

	private TicketContext ensureTicketParameter(IDatosSesion datosSesion, String documentUid, Map<String, Object> params) throws ApiException {
		Object ticketParam = params.get(PARAM_TICKET);
		TicketVentaAbono ticketVenta = null;
		TicketBean ticketBean = null;

		if (ticketParam instanceof TicketVentaAbono) {
			ticketVenta = (TicketVentaAbono) ticketParam;
		}
                else if (ticketParam != null) {
                        log.debug("Se ignora el parámetro 'ticket' con un tipo no soportado para la impresión");
                }

		if (ticketVenta == null) {
			try {
				ticketBean = ServicioTicketsImpl.get().consultarTicketUid(documentUid, datosSesion.getUidActividad());
				if (ticketBean == null) {
					throw new ApiException("No se encontró ticket para documentUid=" + documentUid);
				}
				ticketVenta = parseTicketVenta(ticketBean.getTicket());
			}
			catch (ApiException ex) {
				throw ex;
			}
                        catch (Exception ex) {
                                log.error("Error al recuperar el ticket '" + documentUid + "'", ex);
                                throw new ApiException(ex.getMessage(), ex);
                        }
                        params.put(PARAM_TICKET, ticketVenta);
                        log.debug("Ticket '" + documentUid + "' preparado para la impresión");
		}

		prepareTicketForPrinting(params, ticketVenta);
		return new TicketContext(ticketBean, ticketVenta);
	}

	private void prepareTicketForPrinting(Map<String, Object> params, TicketVentaAbono ticketVenta) {
		if (params == null || ticketVenta == null)
			return;
		List<LineaTicket> agregadas = aggregateTicketLines(ticketVenta);
		params.put(PARAM_LINEAS_AGRUPADAS, new ArrayList<>(agregadas));
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
                        catch (UnsupportedOperationException ex) {
                                log.debug("No se pudo reemplazar la colección de líneas: " + ex.getMessage());
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

	/* ===================== Devolución / flags ===================== */

	private void ensureDevolucionParameter(Map<String, Object> params, TicketContext ctx, IDatosSesion datosSesion) {
		if (Boolean.TRUE.equals(params.get(PARAM_DEVOLUCION)))
			return;

		TicketVentaAbono tv = ctx != null ? ctx.getTicketVenta() : null;
		if (tv == null || tv.getCabecera() == null)
			return;

		boolean devolucion = false;
		try {
			String codTipoDocumento = tv.getCabecera().getCodTipoDocumento();
			if (StringUtils.hasText(codTipoDocumento)) {
				if ("FR".equalsIgnoreCase(codTipoDocumento)) {
					devolucion = true;
				}
				else if ("NC".equalsIgnoreCase(codTipoDocumento)) {
					TipoDocumentoBean tipo = ServicioTiposDocumentosImpl.get().consultar(datosSesion, tv.getCabecera().getTipoDocumento());
					if (tipo != null && !"ES".equalsIgnoreCase(tipo.getCodPais())) {
						devolucion = true;
					}
				}
			}
		}
                catch (Exception e) {
                        log.debug("No se pudo determinar el tipo de documento para el ticket '" + documentUidSafe(ctx) + "': " + e.getMessage());
                }
		params.put(PARAM_DEVOLUCION, devolucion);
	}

	private void applyDuplicateFlag(PrintDocumentDTO printRequest) {
		if (printRequest == null || !Boolean.TRUE.equals(printRequest.getCopy()))
			return;

		Map<String, Object> params = printRequest.getCustomParams();
		if (params != null && !params.containsKey(PARAM_DUPLICATE_FLAG)) {
                        params.put(PARAM_DUPLICATE_FLAG, Boolean.TRUE);
                        log.debug("Marcando la copia con el parámetro '" + PARAM_DUPLICATE_FLAG + "'");
                }
	}

	private String documentUidSafe(TicketContext ctx) {
		if (ctx == null)
			return "";
		TicketBean tb = ctx.getTicketBean();
		if (tb != null && StringUtils.hasText(tb.getUidTicket()))
			return tb.getUidTicket();
		TicketVentaAbono tv = ctx.getTicketVenta();
		if (tv != null && tv.getCabecera() != null && StringUtils.hasText(tv.getCabecera().getUidTicket())) {
			return tv.getCabecera().getUidTicket();
		}
		return "";
	}

	/* ===================== Fiscal data (ATCUD / QR) ===================== */

	private void populateFiscalData(IDatosSesion datosSesion, String documentUid, PrintDocumentDTO printRequest) {
		if (printRequest == null)
			return;

		try {
			DocumentEntity entity = documentService.findById(datosSesion, documentUid);
                        if (entity == null) {
                                log.debug("Documento '" + documentUid + "' no encontrado al obtener datos fiscales");
                                return;
                        }
                        byte[] content = entity.getDocumentContent();
                        if (content == null || content.length == 0) {
                                log.debug("Documento '" + documentUid + "' sin contenido para datos fiscales");
                                return;
                        }

			Map<String, Object> params = printRequest.getCustomParams();
			FiscalDocumentData fd = extractFiscalData(content);

			if (!params.containsKey(PARAM_FISCAL_DATA_ATCUD) && StringUtils.hasText(fd.getAtcud())) {
				params.put(PARAM_FISCAL_DATA_ATCUD, fd.getAtcud());
			}

			if (!params.containsKey(PARAM_FISCAL_DATA_QR) && StringUtils.hasText(fd.getQr())) {
				params.put(PARAM_FISCAL_DATA_QR, fd.getQr());
				InputStream qr = createQrImage(fd.getQr());
				if (qr != null && !params.containsKey(PARAM_QR_PORTUGAL)) {
					params.put(PARAM_QR_PORTUGAL, qr);
				}
			}
		}
                catch (Exception e) {
                        log.warn("No se pudieron extraer los datos fiscales de '" + documentUid + "'", e);
                }
        }

	private FiscalDocumentData extractFiscalData(byte[] content) {
		if (content == null || content.length == 0)
			return FiscalDocumentData.empty();
		try {
			if (isLikelyJson(content)) {
				return parseFiscalDataFromJson(content);
			}
			return parseFiscalDataFromXml(content);
		}
                catch (Exception e) {
                        log.debug("No se pudo analizar la información fiscal", e);
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
		f.setXIncludeAware(false);
		f.setExpandEntityReferences(false);

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
		if (el == null)
			return;
		NodeList props = el.getElementsByTagName(TAG_PROPERTY);
		if (props == null || props.getLength() == 0)
			return;

		for (int i = 0; i < props.getLength(); i++) {
			Node n = props.item(i);
			if (!(n instanceof Element))
				continue;

			Element p = (Element) n;
			String name = getChildTextContent(p, TAG_NAME);
			String value = getChildTextContent(p, TAG_VALUE);
			applyFiscalProperty(res, name, value);
		}
	}

	private String getChildTextContent(Element parent, String tagName) {
		if (parent == null)
			return null;
		NodeList nodes = parent.getElementsByTagName(tagName);
		if (nodes == null || nodes.getLength() == 0)
			return null;
		Node n = nodes.item(0);
		if (n == null)
			return null;
		String txt = n.getTextContent();
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

				if (key != null && (TAG_FISCAL_DATA.equalsIgnoreCase(key) || "fiscalData".equalsIgnoreCase(key))) {
					parseFiscalJsonNode(val, res);
				}
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

		if (ATCUD.equalsIgnoreCase(name) && !StringUtils.hasText(res.getAtcud())) {
			res.setAtcud(value.trim());
		}
		else if (QR.equalsIgnoreCase(name) && !StringUtils.hasText(res.getQr())) {
			res.setQr(value.trim());
		}
	}

	private InputStream createQrImage(String base64Value) {
		if (!StringUtils.hasText(base64Value))
			return null;
		try {
			byte[] decoded = Base64.getDecoder().decode(base64Value.trim());
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
                        log.warn("No se pudo generar la imagen QR", e);
                }
		return null;
	}

	/* ===================== Utilidades ===================== */

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
		if (v == null)
			return null;
		return v.setScale(scale, RoundingMode.HALF_UP).toPlainString();
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

	/* ===================== Tipos internos ===================== */

	/**
	 * Mapa que evita que se sobreescriba 'ticket' con un tipo incorrecto.
	 */
	private static final class TicketPreservingMap extends HashMap<String, Object> {

		private static final long serialVersionUID = 1L;

		TicketPreservingMap(Map<String, Object> existing) {
			super(existing != null ? Math.max(existing.size() * 2, 16) : 16);
			if (existing != null)
				super.putAll(existing);
		}

		@Override
		public Object put(String key, Object value) {
			if (PARAM_TICKET.equals(key) && !(value instanceof TicketVentaAbono)) {
				// Mantenemos el ticket válido que ya estuviera
				return super.get(key);
			}
			return super.put(key, value);
		}

		@Override
		public void putAll(Map<? extends String, ?> m) {
			if (m == null)
				return;
			for (Map.Entry<? extends String, ?> e : m.entrySet()) {
				put(e.getKey(), e.getValue());
			}
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
