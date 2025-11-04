package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.lang.reflect.Field;

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
import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.BricodepotPrintableDocument;
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

import com.comerzzia.core.model.tiposdocumentos.TipoDocumentoBean;
import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.servicios.tipodocumento.ServicioTiposDocumentosImpl;
import com.comerzzia.core.servicios.ventas.tickets.ServicioTicketsImpl;
import com.comerzzia.core.util.config.AppInfo;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.LineaTicket;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.SubtotalIvaTicket;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

// === para cargar LOGO como el estándar ===
import com.comerzzia.omnichannel.service.documentprint.DocumentPrintService;
import com.comerzzia.core.servicios.empresas.EmpresasService;
import com.comerzzia.core.model.empresas.EmpresaBean;
import com.comerzzia.core.servicios.empresas.EmpresaException;
import com.comerzzia.core.servicios.empresas.EmpresaNotFoundException;

@Service
public class BricodepotSaleDocumentPrintServiceImpl implements BricodepotSaleDocumentPrintService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BricodepotSaleDocumentPrintServiceImpl.class);
    private static final String PARAM_FISCAL_DATA_ATCUD = "fiscalData_ACTUD";
    private static final String PARAM_FISCAL_DATA_QR = "fiscalData_QR";
    private static final String PARAM_QR_PORTUGAL = "QR_PORTUGAL";
    private static final String PARAM_DUPLICATE_FLAG = "esDuplicado";
    private static final String PARAM_TICKET = "ticket";
    private static final String PARAM_SUBREPORT_DIR = "SUBREPORT_DIR";
    private static final String PARAM_LOGO = "LOGO";
    private static final String PARAM_DEVOLUTION = "DEVOLUCION";
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

    private static volatile Field printDocumentCustomParamsField;

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
        LOGGER.debug("printDocument() - Generating sales document '{}' with mime type '{}'", documentUid, printRequest.getMimeType());

        populateFiscalData(datosSesion, documentUid, printRequest);
        applyDuplicateFlag(printRequest);
        ensureFacturaTemplateParameters(datosSesion, documentUid, printRequest);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            saleDocumentService.printDocument(outputStream, datosSesion, documentUid, printRequest);

            String outputDocumentName = printRequest.getOutputDocumentName();
            if (!StringUtils.hasText(outputDocumentName)) {
                outputDocumentName = documentUid;
            }

            return new BricodepotPrintableDocument(documentUid, outputDocumentName, printRequest.getMimeType(), outputStream.toByteArray());
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

    // === FIX LOGO & PARAMS ===
    private void ensureFacturaTemplateParameters(IDatosSesion datosSesion, String documentUid, PrintDocumentDTO printRequest) throws ApiException {
        if (printRequest == null) return;

        Map<String, Object> customParams = ensureCustomParamsWrapper(printRequest);
        if (customParams == null) return;

        customParams = printRequest.getCustomParams();
        if (customParams == null) return;

        // No permitir que LOGO=null pise al estándar
        if (customParams.containsKey(PARAM_LOGO) && customParams.get(PARAM_LOGO) == null) {
            customParams.remove(PARAM_LOGO);
        }

        // Garantiza companyCode para que el estándar pueda cargar logo/divisa
        ensureCompanyCodeFromTicketIfMissing(datosSesion, documentUid, customParams);

        // Carga nuestro logo solo si no existe todavía (siempre InputStream)
        ensureCompanyLogo(datosSesion, customParams);

        // DEVOLUCIÓN por defecto
        customParams.putIfAbsent(PARAM_DEVOLUTION, Boolean.FALSE);

        ensureSubreportDirectory(customParams);

        TicketContext ticketContext = ensureTicketParameter(datosSesion, documentUid, customParams);
        if (ticketContext != null) {
            ensureDevolutionParameter(customParams, ticketContext, datosSesion);
        }
    }

    private void ensureCompanyLogo(IDatosSesion datosSesion, Map<String, Object> customParams) {
        if (customParams == null) return;

        Object existingLogo = customParams.get(PARAM_LOGO);
        if (existingLogo instanceof InputStream) return;
        if (existingLogo == null) customParams.remove(PARAM_LOGO);

        Object cc = customParams.get(DocumentPrintService.PARAM_COMPANY_CODE);
        if (!(cc instanceof String) || !StringUtils.hasText((String) cc)) return;

        String companyCode = ((String) cc).trim();
        try {
            EmpresaBean empresa = empresasService.consultar(datosSesion, companyCode);
            if (empresa != null && empresa.getLogotipo() != null && empresa.getLogotipo().length > 0) {
                customParams.put(PARAM_LOGO, new ByteArrayInputStream(empresa.getLogotipo()));
                LOGGER.debug("ensureCompanyLogo() - Loaded logo from DB for companyCode '{}'", companyCode);
            } else {
                customParams.remove(PARAM_LOGO);
                LOGGER.debug("ensureCompanyLogo() - No logo in DB for companyCode '{}'", companyCode);
            }
        } catch (EmpresaNotFoundException | EmpresaException ex) {
            customParams.remove(PARAM_LOGO);
            LOGGER.warn("ensureCompanyLogo() - Unable to load company logo for companyCode '{}'", companyCode, ex);
        }
    }

    private void ensureCompanyCodeFromTicketIfMissing(IDatosSesion datosSesion, String documentUid, Map<String, Object> customParams) {
        final String KEY = DocumentPrintService.PARAM_COMPANY_CODE;
        Object cc = customParams.get(KEY);
        if (cc instanceof String && StringUtils.hasText((String) cc)) return;

        try {
            Object t = customParams.get(PARAM_TICKET);
            if (t instanceof TicketVentaAbono) {
                TicketVentaAbono tv = (TicketVentaAbono) t;
                if (tv.getCabecera() != null && StringUtils.hasText(tv.getCabecera().getEmpresa().getCodEmpresa())) {
                    customParams.put(KEY, tv.getCabecera().getEmpresa().getCodEmpresa().trim());
                    return;
                }
            }
            TicketBean tb = ServicioTicketsImpl.get().consultarTicketUid(documentUid, datosSesion.getUidActividad());
            if (tb != null && tb.getTicket() != null && tb.getTicket().length > 0) {
                TicketVentaAbono tv = parseTicketVenta(tb.getTicket());
                if (tv != null && tv.getCabecera() != null && StringUtils.hasText(tv.getCabecera().getEmpresa().getCodEmpresa())) {
                    customParams.put(KEY, tv.getCabecera().getEmpresa().getCodEmpresa().trim());
                }
            }
        } catch (Exception e) {
            LOGGER.debug("ensureCompanyCodeFromTicketIfMissing() - Unable to derive company code from ticket '{}': {}", documentUid, e.getMessage());
        }
    }
    // === FIN FIX LOGO & PARAMS ===

    private void ensureSubreportDirectory(Map<String, Object> customParams) {
        if (customParams.containsKey(PARAM_SUBREPORT_DIR) && customParams.get(PARAM_SUBREPORT_DIR) != null) {
            return;
        }

        String basePath = AppInfo.getInformesInfo().getRutaBase();
        if (!StringUtils.hasText(basePath)) {
            LOGGER.debug("ensureSubreportDirectory() - Report base path not configured, skipping subreport directory parameter");
            return;
        }

        String normalizedBasePath = basePath.replace('\\', File.separatorChar).replace('/', File.separatorChar);
        if (!normalizedBasePath.endsWith(File.separator)) {
            normalizedBasePath = normalizedBasePath + File.separator;
        }

        String directory = normalizedBasePath + FACTURA_REPORT_DIRECTORY;
        customParams.put(PARAM_SUBREPORT_DIR, directory);
    }

    private TicketContext ensureTicketParameter(IDatosSesion datosSesion, String documentUid, Map<String, Object> customParams) throws ApiException {
        Object ticketParam = customParams.get(PARAM_TICKET);
        if (ticketParam instanceof TicketVentaAbono) {
            TicketVentaAbono ticketVenta = (TicketVentaAbono) ticketParam;
            prepareTicketForPrinting(customParams, ticketVenta);
            return new TicketContext(null, ticketVenta);
        }

        if (ticketParam != null) {
            LOGGER.debug("ensureTicketParameter() - Ignoring ticket parameter of unsupported type: {}", ticketParam.getClass());
            return null;
        }

        TicketBean ticketBean;
        TicketVentaAbono ticketVenta;
        try {
            ticketBean = ServicioTicketsImpl.get().consultarTicketUid(documentUid, datosSesion.getUidActividad());
            if (ticketBean == null) {
                throw new ApiException("Ticket not found for documentUid=" + documentUid);
            }

            ticketVenta = parseTicketVenta(ticketBean.getTicket());
        }
        catch (ApiException exception) {
            throw exception;
        }
        catch (Exception exception) {
            LOGGER.error("ensureTicketParameter() - Error retrieving ticket '{}'", documentUid, exception);
            throw new ApiException(exception.getMessage(), exception);
        }

        prepareTicketForPrinting(customParams, ticketVenta);
        customParams.put(PARAM_TICKET, ticketVenta);
        LOGGER.debug("ensureTicketParameter() - Loaded ticket '{}' for printing", documentUid);
        return new TicketContext(ticketBean, ticketVenta);
    }

    private void prepareTicketForPrinting(Map<String, Object> customParams, TicketVentaAbono ticketVenta) {
        if (customParams == null || ticketVenta == null) {
            return;
        }

        List<LineaTicket> aggregatedLines = aggregateTicketLines(ticketVenta);
        customParams.put(PARAM_LINEAS_AGRUPADAS, new ArrayList<>(aggregatedLines));
    }

    private List<LineaTicket> aggregateTicketLines(TicketVentaAbono ticketVenta) {
        List<LineaTicket> originalLines = ticketVenta != null ? ticketVenta.getLineas() : null;
        if (originalLines == null || originalLines.isEmpty()) {
            return Collections.emptyList();
        }

        List<LineaTicket> working = new ArrayList<>();
        for (LineaTicket line : originalLines) {
            if (line != null) {
                working.add(line);
            }
        }

        List<LineaTicket> aggregated = new ArrayList<>(working.size());
        while (!working.isEmpty()) {
            LineaTicket line = working.remove(0);
            if (line == null) {
                continue;
            }

            BigDecimal totalQuantity = safe(line.getCantidad());
            BigDecimal totalPromotions = safe(line.getImporteTotalPromociones());
            BigDecimal totalWithDiscount = safe(line.getImporteTotalConDto());
            BigDecimal totalAmount = safe(line.getImporteConDto());
            List<LineaTicket> mergedLines = new ArrayList<>();

            Iterator<LineaTicket> iterator = working.iterator();
            while (iterator.hasNext()) {
                LineaTicket candidate = iterator.next();
                if (candidate == null) {
                    iterator.remove();
                    continue;
                }

                if (haveSameSaleConditions(line, candidate)) {
                    totalQuantity = totalQuantity.add(safe(candidate.getCantidad()));
                    totalPromotions = totalPromotions.add(safe(candidate.getImporteTotalPromociones()));
                    totalWithDiscount = totalWithDiscount.add(safe(candidate.getImporteTotalConDto()));
                    totalAmount = totalAmount.add(safe(candidate.getImporteConDto()));
                    mergedLines.add(candidate);
                    iterator.remove();
                }
            }

            if (isZero(totalQuantity)) {
                continue;
            }

            line.setCantidad(totalQuantity);
            line.setImporteTotalPromociones(totalPromotions);
            line.setImporteTotalConDto(totalWithDiscount);
            line.setImporteConDto(totalAmount);

            applyTaxBreakdown(line, ticketVenta, totalQuantity);
            if (!mergedLines.isEmpty()) {
                String formattedTax = line.getDesglose2();
                String formattedCode = line.getCodImp();
                for (LineaTicket merged : mergedLines) {
                    merged.setDesglose2(formattedTax);
                    merged.setCodImp(formattedCode);
                }
            }
            aggregated.add(line);
        }

        List<LineaTicket> ticketLines = ticketVenta.getLineas();
        if (ticketLines != null) {
            try {
                ticketLines.clear();
                ticketLines.addAll(aggregated);
            }
            catch (UnsupportedOperationException exception) {
                LOGGER.debug("aggregateTicketLines() - Unable to replace ticket line collection: {}", exception.getMessage());
            }
        }

        return aggregated;
    }

    private boolean haveSameSaleConditions(LineaTicket primary, LineaTicket candidate) {
        if (primary == null || candidate == null) {
            return false;
        }

        if (!Objects.equals(primary.getCodArticulo(), candidate.getCodArticulo())) {
            return false;
        }

        if (safe(primary.getPrecioTotalConDto()).compareTo(safe(candidate.getPrecioTotalConDto())) != 0) {
            return false;
        }

        BigDecimal primaryQty = safe(primary.getCantidad());
        BigDecimal candidateQty = safe(candidate.getCantidad());
        return primaryQty.signum() == candidateQty.signum();
    }

    private void applyTaxBreakdown(LineaTicket line, TicketVentaAbono ticketVenta, BigDecimal quantity) {
        if (line == null || ticketVenta == null || ticketVenta.getCabecera() == null) {
            return;
        }

        List<SubtotalIvaTicket> subtotals = ticketVenta.getCabecera().getSubtotalesIva();
        if (subtotals == null || subtotals.isEmpty()) {
            return;
        }

        String lineTaxCode = normalize(line.getCodImp());
        for (SubtotalIvaTicket subtotal : subtotals) {
            if (subtotal == null) {
                continue;
            }

            if (!normalize(subtotal.getCodImp()).equals(lineTaxCode)) {
                continue;
            }

            line.setCodImp(formatPercentage(subtotal.getPorcentaje()));

            BigDecimal priceWithDiscount = safe(line.getPrecioConDto());
            BigDecimal percentage = safe(subtotal.getPorcentaje());
            BigDecimal taxAmount = priceWithDiscount.multiply(quantity).multiply(percentage).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
            line.setDesglose2(formatDecimal(taxAmount, 4));
            return;
        }
    }

    private BigDecimal safe(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    private boolean isZero(BigDecimal value) {
        return safe(value).compareTo(BigDecimal.ZERO) == 0;
    }

    private String normalize(String value) {
        return value != null ? value.trim() : "";
    }

    private String formatPercentage(BigDecimal value) {
        if (value == null) {
            return "";
        }
        return value.stripTrailingZeros().toPlainString();
    }

    private String formatDecimal(BigDecimal value, int scale) {
        if (value == null) {
            return null;
        }
        return value.setScale(scale, RoundingMode.HALF_UP).toPlainString();
    }

    private void ensureDevolutionParameter(Map<String, Object> customParams, TicketContext ticketContext, IDatosSesion datosSesion) {
        if (Boolean.TRUE.equals(customParams.get(PARAM_DEVOLUTION))) {
            return;
        }

        TicketVentaAbono ticketVenta = ticketContext != null ? ticketContext.getTicketVenta() : null;
        if (ticketVenta == null || ticketVenta.getCabecera() == null) {
            return;
        }

        boolean devolucion = false;
        try {
            String codTipoDocumento = ticketVenta.getCabecera().getCodTipoDocumento();
            if (StringUtils.hasText(codTipoDocumento)) {
                if ("FR".equalsIgnoreCase(codTipoDocumento)) {
                    devolucion = true;
                }
                else if ("NC".equalsIgnoreCase(codTipoDocumento)) {
                    TipoDocumentoBean tipoDocumento = ServicioTiposDocumentosImpl.get().consultar(datosSesion, ticketVenta.getCabecera().getTipoDocumento());
                    if (tipoDocumento != null && !"ES".equalsIgnoreCase(tipoDocumento.getCodPais())) {
                        devolucion = true;
                    }
                }
            }
        }
        catch (Exception exception) {
            LOGGER.debug("ensureDevolutionParameter() - Unable to determine document type for ticket '{}': {}", documentUidSafe(ticketContext), exception.getMessage());
        }

        customParams.put(PARAM_DEVOLUTION, devolucion);
    }

    private String documentUidSafe(TicketContext ticketContext) {
        if (ticketContext == null) {
            return "";
        }
        TicketBean ticketBean = ticketContext.getTicketBean();
        if (ticketBean != null && StringUtils.hasText(ticketBean.getUidTicket())) {
            return ticketBean.getUidTicket();
        }
        TicketVentaAbono ticketVenta = ticketContext.getTicketVenta();
        if (ticketVenta != null && ticketVenta.getCabecera() != null && StringUtils.hasText(ticketVenta.getCabecera().getUidTicket())) {
            return ticketVenta.getCabecera().getUidTicket();
        }
        return "";
    }

    private Map<String, Object> ensureCustomParamsWrapper(PrintDocumentDTO printRequest) {
        if (printRequest == null) {
            return null;
        }

        Map<String, Object> current = printRequest.getCustomParams();
        if (current instanceof TicketPreservingMap) {
            return current;
        }

        TicketPreservingMap wrapper = new TicketPreservingMap(current);
        if (assignCustomParamsMap(printRequest, wrapper)) {
            return wrapper;
        }

        if (current == null) {
            Map<String, Object> fallback = new HashMap<>();
            if (assignCustomParamsMap(printRequest, fallback)) {
                return fallback;
            }
            return null;
        }

        LOGGER.debug("ensureCustomParamsWrapper() - Unable to replace custom params map, continuing without wrapper");
        return current;
    }

    private boolean assignCustomParamsMap(PrintDocumentDTO printRequest, Map<String, Object> replacement) {
        if (printRequest == null || replacement == null) {
            return false;
        }

        Field field = resolveCustomParamsField(printRequest.getClass());
        if (field == null) {
            return false;
        }

        try {
            field.set(printRequest, replacement);
            return true;
        }
        catch (IllegalAccessException exception) {
            LOGGER.debug("assignCustomParamsMap() - Unable to access custom params field: {}", exception.getMessage());
            return false;
        }
    }

    private Field resolveCustomParamsField(Class<?> type) {
        if (type == null) {
            return null;
        }

        Field cached = printDocumentCustomParamsField;
        if (cached != null) {
            return cached;
        }

        synchronized (BricodepotSaleDocumentPrintServiceImpl.class) {
            if (printDocumentCustomParamsField != null) {
                return printDocumentCustomParamsField;
            }

            Class<?> current = type;
            while (current != null) {
                for (Field field : current.getDeclaredFields()) {
                    if (Map.class.isAssignableFrom(field.getType())) {
                        field.setAccessible(true);
                        printDocumentCustomParamsField = field;
                        return field;
                    }
                }
                current = current.getSuperclass();
            }
        }

        LOGGER.debug("resolveCustomParamsField() - Unable to locate custom params field in {}", type.getName());
        return null;
    }

    private TicketVentaAbono parseTicketVenta(byte[] ticketData) throws ApiException {
        if (ticketData == null || ticketData.length == 0) {
            throw new ApiException("Ticket data is empty");
        }

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(ticketData)) {
            Unmarshaller unmarshaller = TICKET_JAXB_CONTEXT.createUnmarshaller();
            Object parsed = unmarshaller.unmarshal(inputStream);
            if (parsed instanceof TicketVentaAbono) {
                return (TicketVentaAbono) parsed;
            }
            throw new ApiException("Unable to parse ticket XML into TicketVentaAbono");
        }
        catch (JAXBException | IOException exception) {
            throw new ApiException(exception.getMessage(), exception);
        }
    }

    private static JAXBContext createTicketContext() {
        try {
            return JAXBContext.newInstance(TicketVentaAbono.class);
        }
        catch (JAXBException exception) {
            throw new IllegalStateException("Unable to create JAXB context for TicketVentaAbono", exception);
        }
    }

    private static final class TicketPreservingMap extends HashMap<String, Object> {

        private static final long serialVersionUID = 1L;

        TicketPreservingMap(Map<String, Object> existing) {
            super(existing != null ? Math.max(existing.size() * 2, 16) : 16);
            if (existing != null) {
                super.putAll(existing);
            }
        }

        @Override
        public Object put(String key, Object value) {
            if (PARAM_TICKET.equals(key) && !(value instanceof TicketVentaAbono)) {
                LOGGER.debug("TicketPreservingMap.put() - Ignoring non ticket value '{}' for parameter '{}'", value != null ? value.getClass() : null, key);
                return super.get(key);
            }
            return super.put(key, value);
        }

        @Override
        public void putAll(Map<? extends String, ?> m) {
            if (m == null) {
                return;
            }
            for (Map.Entry<? extends String, ?> entry : m.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
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
