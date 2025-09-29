package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.LineaAgrupada;
import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.TicketVentaAbono;
import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.TicketVentaAbono.FiscalData;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.InvoiceDataPreparer;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.ReportTemplateResolver;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.ReportTemplateResolver.TemplateResolution;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.executor.ReportRenderer;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model.SalesDocumentPrintRequest;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model.SalesDocumentPrintResult;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model.TrabajoInformeBean;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model.TrabajoInformeBean.ParametrosTrabajo;

@Service
public class SalesDocumentPrintServiceImpl implements SalesDocumentPrintService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesDocumentPrintServiceImpl.class);

    private final SalesDocumentRepository repository;
    private final ReportRenderer reportRenderer;
    private final ReportTemplateResolver templateResolver;
    private final InvoiceDataPreparer dataPreparer;
    private final ReportProperties reportProperties;

    public SalesDocumentPrintServiceImpl(SalesDocumentRepository repository, ReportRenderer reportRenderer,
            ReportProperties reportProperties) {
        this.repository = repository;
        this.reportRenderer = reportRenderer;
        this.templateResolver = new ReportTemplateResolver(reportProperties);
        this.dataPreparer = new InvoiceDataPreparer();
        this.reportProperties = reportProperties;
    }

    @Override
    public Optional<SalesDocumentPrintResult> print(String documentUid, SalesDocumentPrintRequest request) {
        Optional<TicketVentaAbono> maybeTicket = repository.findByDocumentUid(documentUid);

        if (!maybeTicket.isPresent()) {
            LOGGER.info("No sales document found for uid {}", documentUid);
            return Optional.empty();
        }

        TicketVentaAbono ticket = maybeTicket.get();

        TemplateResolution resolution = templateResolver.resolve(ticket, request.getTemplateOverride());
        TrabajoInformeBean trabajo = buildTrabajo(ticket, resolution, request);

        byte[] pdf = reportRenderer.render(trabajo);

        String filename = resolveFileName(ticket, request.getOutputDocumentName());

        return Optional.of(new SalesDocumentPrintResult(pdf, filename, "application/pdf"));
    }

    private TrabajoInformeBean buildTrabajo(TicketVentaAbono ticket, TemplateResolution resolution,
            SalesDocumentPrintRequest request) {
        ParametrosTrabajo parametros = new ParametrosTrabajo()
                .fechaTicket(ticket.getFechaTicket())
                .locatorId(ticket.getLocatorId())
                .uidInstancia(ticket.getUidInstancia())
                .logo(ticket.getLogo())
                .codigoTicket(ticket.getCodigoTicket())
                .pais(ticket.getPais().name())
                .subreportDir(resolution.getSubreportDirectory())
                .duplicado(request.isCopy());

        Map<String, Object> jasperParams = new HashMap<>();
        jasperParams.put("FECHA_TICKET", ticket.getFechaTicket());
        jasperParams.put("LOCATOR_ID", ticket.getLocatorId());
        jasperParams.put("UID_INSTANCIA", ticket.getUidInstancia());
        jasperParams.put("LOGO", ticket.getLogo());
        jasperParams.put("ticket", ticket);
        jasperParams.put("ticketVentaAbono", ticket);
        jasperParams.put("fecha_origen", ticket.getFechaOrigen());
        jasperParams.put("SUBREPORT_DIR", resolution.getSubreportDirectory());
        jasperParams.put("numPedido", ticket.getNumeroPedido());
        jasperParams.put("esDuplicado", request.isCopy());

        if (ticket.getAtributosAdicionales() != null) {
            jasperParams.putAll(ticket.getAtributosAdicionales());
        }

        java.util.List<LineaAgrupada> lineasAgrupadas = dataPreparer.agruparLineas(ticket.getLineas());
        jasperParams.put("lineasAgrupadas", lineasAgrupadas);
        jasperParams.put("listaPromociones", dataPreparer.promociones(ticket.getPromociones()));
        jasperParams.put("listaPagosTarjetaDatosPeticion", dataPreparer.pagosTarjeta(ticket.getPagos()));
        jasperParams.put("listaPagosTarjeta", dataPreparer.pagosTarjeta(ticket.getPagos()));
        jasperParams.put("pagoGiftcard", dataPreparer.resumenGiftCard(ticket.getPagos()));
        jasperParams.put("totalSaldoGiftCard", dataPreparer.totalGiftCards(ticket.getPagos()));

        if (ticket.isDevolucion() || ticket.getPais() == TicketVentaAbono.Pais.FR) {
            jasperParams.put("DEVOLUCION", Boolean.TRUE);
        }

        FiscalData fiscalData = ticket.getFiscalData();
        if (fiscalData != null) {
            jasperParams.put("fiscalData_ATCUD", fiscalData.getAtcud());
            jasperParams.put("fiscalData_QR", fiscalData.getQrData());
            byte[] qr = dataPreparer.generarQr(fiscalData.getQrData());
            if (qr != null) {
                jasperParams.put("QR_PORTUGAL", qr);
            }
        }

        request.getCustomParams().forEach(jasperParams::put);

        jasperParams.forEach(parametros::parametro);

        return TrabajoInformeBean.builder()
                .informe(resolution.getTemplateName())
                .reportVersion(resolution.getReportVersion())
                .parametros(parametros)
                .build();
    }

    private String resolveFileName(TicketVentaAbono ticket, String override) {
        String filename = override;
        if (filename == null || filename.trim().isEmpty()) {
            filename = reportProperties.getDefaultDocumentName() + "_" + ticket.getCodigoTicket() + ".pdf";
        }

        if (!filename.toLowerCase().endsWith(".pdf")) {
            filename = filename + ".pdf";
        }
        return filename;
    }
}

