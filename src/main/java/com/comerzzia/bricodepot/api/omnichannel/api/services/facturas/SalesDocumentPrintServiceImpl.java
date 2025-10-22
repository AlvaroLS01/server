package com.comerzzia.bricodepot.api.omnichannel.api.services.facturas;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.omnichannel.web.model.document.PrintDocumentRequest;
import com.comerzzia.api.omnichannel.web.model.document.PrintDocumentResponse;
import com.comerzzia.bricodepot.backoffice.services.ventas.facturas.CargarFacturaA4Servicio;
import com.comerzzia.core.model.actividades.ActividadBean;
import com.comerzzia.core.model.informes.TrabajoInformeBean;
import com.comerzzia.core.model.tiposdocumentos.TipoDocumentoBean;
import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.servicios.informes.InformeException;
import com.comerzzia.core.servicios.instancias.ServicioInstanciasImpl;
import com.comerzzia.core.servicios.sesion.DatosSesionBean;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoException;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoNotFoundException;
import com.comerzzia.core.servicios.tipodocumento.TiposDocumentosService;
import com.comerzzia.core.servicios.ventas.tickets.TicketService;
import com.comerzzia.core.util.config.AppInfo;
import com.comerzzia.core.util.xml.XMLDocument;
import com.comerzzia.core.util.xml.XMLDocumentNode;
import com.comerzzia.core.util.xml.XMLDocumentNodeNotFoundException;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.LineaTicket;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.service.documentprint.DocumentPrintService;
import com.comerzzia.omnichannel.service.salesdocument.metadata.DocumentMetadata;
import com.comerzzia.omnichannel.service.salesdocument.metadata.DocumentMetadataParser;
import com.comerzzia.pos.util.xml.MarshallUtil;

@Service
public class SalesDocumentPrintServiceImpl implements SalesDocumentPrintService {

        private static final String TEMPLATE_NAME = "ventas.facturas.facturaA4";
        private static final String PORTUGAL = "PT";
        private static final String ESPANYA = "ES";
        private static final String CATALUNYA = "CA";
        private static final String ATCUD = "ATCUD";
        private static final String QR = "QR";

        private final Logger log = LoggerFactory.getLogger(SalesDocumentPrintServiceImpl.class);

        @Autowired
        private TicketService ticketService;

        @Autowired
        private CargarFacturaA4Servicio cargarFacturaA4Servicio;

        @Autowired
        private TiposDocumentosService tiposDocumentosService;

        @Autowired
        private DocumentPrintService documentPrintService;

        @Autowired
        private DocumentMetadataParser metadataParser;

        @Override
        public Optional<PrintDocumentResponse> print(String documentUid, DatosSesionBean datosSesion,
                        PrintDocumentRequest request, PrintDocumentDTO printDocumentDTO) throws ApiException {

                TicketBean ticket;
                try {
                        ticket = ticketService.consultarTicketUid(datosSesion, documentUid);
                } catch (InformeException e) {
                        throw new ApiException("Error consulting ticket: " + e.getMessage(), e);
                }

                if (ticket == null) {
                        return Optional.empty();
                }

                TicketVentaAbono ticketVenta = unmarshallTicket(ticket);
                if (ticketVenta == null) {
                        throw new ApiException("Unable to parse ticket XML for document " + documentUid);
                }

                TrabajoInformeBean trabajoInforme = new TrabajoInformeBean();
                trabajoInforme.addParametro("ticket", ticketVenta);
                trabajoInforme.addParametro("document", ticketVenta);

                boolean isCopy = Boolean.TRUE.equals(request.getCopy());
                trabajoInforme.addParametro("esDuplicado", isCopy);
                if (ticket.getFecha() != null) {
                        trabajoInforme.addParametro("fecha", ticket.getFecha());
                }
                if (StringUtils.isNotBlank(ticket.getLocatorId())) {
                        trabajoInforme.addParametro("LOCATOR_ID", ticket.getLocatorId());
                }

                Document ticketXml = ticket.getXml();
                if (ticketXml != null) {
                        String fechaOrigen = obtenerFechaOrigen(ticketXml);
                        if (fechaOrigen != null) {
                                trabajoInforme.addParametro("fecha_origen", fechaOrigen);
                        }
                        trabajoInforme.addParametro("numPedido", getNumPedido(ticketXml));
                }

                try {
                        ActividadBean actividad = ServicioInstanciasImpl.get().consultarActividad(ticket.getUidActividad());
                        if (actividad != null) {
                                trabajoInforme.addParametro("UID_INSTANCIA", actividad.getUidInstancia());
                        }
                } catch (Exception e) {
                        log.debug("Unable to resolve actividad for ticket {}: {}", documentUid, e.getMessage());
                }

                cargarFacturaA4Servicio.getPagoGiftCard(ticketVenta, trabajoInforme);
                try {
                        cargarFacturaA4Servicio.generarMediosPago(ticketVenta, datosSesion);
                } catch (Exception e) {
                        log.warn("Failed to load medios de pago: {}", e.getMessage());
                }
                try {
                        cargarFacturaA4Servicio.cargarPromociones(ticket, trabajoInforme);
                } catch (Exception e) {
                        log.warn("Failed to load promotions: {}", e.getMessage());
                }
                cargarFacturaA4Servicio.cargarDatosPagoTarjeta(ticket, ticketVenta, trabajoInforme);
                List<LineaTicket> lineasAgrupadas = cargarFacturaA4Servicio.getLineasAgrupadas(ticketVenta, trabajoInforme);
                trabajoInforme.addParametro("lineasAgrupadas", lineasAgrupadas);
                trabajoInforme.addParametro("DEVOLUCION", Boolean.FALSE);

                String ruta = AppInfo.getInformesInfo().getRutaBase() + "ventas/facturas/";
                trabajoInforme.addParametro("SUBREPORT_DIR", cargarFacturaA4Servicio.getSubReportDir(ruta));

                try {
                        cargarFacturaA4Servicio.addFiscalData(ticket, trabajoInforme);
                } catch (Exception e) {
                        log.debug("No fiscal data present: {}", e.getMessage());
                }

                TipoDocumentoBean tipoDocumento;
                try {
                        tipoDocumento = tiposDocumentosService.consultar(datosSesion, ticketVenta.getCabecera().getTipoDocumento());
                } catch (TipoDocumentoNotFoundException | TipoDocumentoException e) {
                        throw new ApiException("Error retrieving document type: " + e.getMessage(), e);
                }

                String resolvedTemplate = resolveTemplate(tipoDocumento, ticket, trabajoInforme, ticketVenta);

                DocumentMetadata metadata = metadataParser.getMetadata(datosSesion, ticket.getTicket());
                if (metadata.getLocale() == null) {
                        metadata.setLocale(datosSesion.getLocale());
                }
                if (StringUtils.isBlank(metadata.getCompanyCode()) && ticketVenta.getCabecera() != null
                                && ticketVenta.getCabecera().getEmpresa() != null) {
                        metadata.setCompanyCode(ticketVenta.getCabecera().getEmpresa().getCodEmpresa());
                }

                Locale locale = metadata.getLocale() != null ? metadata.getLocale() : datosSesion.getLocale();
                printDocumentDTO.setMimeType("application/pdf");
                printDocumentDTO.setPrintTemplate(resolvedTemplate);
                printDocumentDTO.setOutputDocumentName(resolveDocumentName(request, documentUid));
                printDocumentDTO.setCopy(isCopy);
                printDocumentDTO.setInline(Boolean.TRUE.equals(request.getInline()));
                printDocumentDTO.getCustomParams().putAll(trabajoInforme.getParametros());
                String companyCode = metadata.getCompanyCode();
                if (StringUtils.isBlank(companyCode) && ticketVenta.getCabecera() != null
                                && ticketVenta.getCabecera().getEmpresa() != null) {
                        companyCode = ticketVenta.getCabecera().getEmpresa().getCodEmpresa();
                }
                printDocumentDTO.getCustomParams().put(DocumentPrintService.PARAM_COMPANY_CODE, companyCode);
                printDocumentDTO.getCustomParams().put(DocumentPrintService.PARAM_LOCALE, locale);
                printDocumentDTO.getCustomParams().put(DocumentPrintService.PARAM_DEFAULT_TEMPLATE, TEMPLATE_NAME);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                documentPrintService.printToStream(baos, datosSesion, printDocumentDTO);

                String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());

                PrintDocumentResponse response = PrintDocumentResponse.builder()
                                .documentUid(documentUid)
                                .documentName(printDocumentDTO.getOutputDocumentName())
                                .mimeType(printDocumentDTO.getMimeType())
                                .content(base64)
                                .copy(isCopy)
                                .build();

                return Optional.of(response);
        }

        protected TicketVentaAbono unmarshallTicket(TicketBean ticket) throws ApiException {
                try {
                        byte[] content = ticket.getTicket();
                        if (content == null || content.length == 0) {
                                return null;
                        }
                        Object parsed = MarshallUtil.leerXML(content, TicketVentaAbono.class);
                        return (TicketVentaAbono) parsed;
                } catch (Exception e) {
                        throw new ApiException("Error parsing ticket xml", e);
                }
        }

        protected String resolveDocumentName(PrintDocumentRequest request, String documentUid) {
                if (StringUtils.isNotBlank(request.getOutputDocumentName())) {
                        return request.getOutputDocumentName();
                }
                return documentUid;
        }

        protected String resolveTemplate(TipoDocumentoBean tipoDocumento, TicketBean ticket, TrabajoInformeBean trabajoInforme,
                        TicketVentaAbono ticketVenta) {
                String template = TEMPLATE_NAME;
                String pais = tipoDocumento.getCodPais();

                Document xml = ticket.getXml();
                if (StringUtils.equals(pais, PORTUGAL) && xml != null) {
                        String qrData = obtenerValorTagXml(xml, QR);
                        if (StringUtils.isNotBlank(qrData)) {
                                trabajoInforme.addParametro("fiscalData_QR", qrData);
                        }
                        String atcud = obtenerValorTagXml(xml, ATCUD);
                        if (StringUtils.isNotBlank(atcud)) {
                                trabajoInforme.addParametro("fiscalData_ACTUD", atcud);
                        }
                }

                if (StringUtils.equals(ticketVenta.getCabecera().getCodTipoDocumento(), "FR")) {
                        trabajoInforme.addParametro("DEVOLUCION", Boolean.TRUE);
                }

                if (StringUtils.equals(pais, CATALUNYA)) {
                        trabajoInforme.addParametro("reportVersion", 3L);
                } else if (StringUtils.equals(pais, PORTUGAL)) {
                        trabajoInforme.addParametro("reportVersion", 2L);
                } else if (StringUtils.equals(pais, ESPANYA)) {
                        trabajoInforme.addParametro("reportVersion", 1L);
                }

                return template;
        }

        protected String obtenerFechaOrigen(Document document) {
                NodeList nodes = document.getElementsByTagName("fechaTicketOrigen");
                if (nodes != null && nodes.getLength() > 0) {
                        Node node = nodes.item(0);
                        return node != null ? node.getTextContent() : null;
                }
                return null;
        }

        protected String getNumPedido(Document document) {
                try {
                        XMLDocument xmlDoc = new XMLDocument(document);
                        XMLDocumentNode pagosNode = xmlDoc.getNodo("pagos", true);
                        if (pagosNode != null) {
                                for (XMLDocumentNode pagoNode : pagosNode.getHijos("pago")) {
                                        XMLDocumentNode extendedDataNode = pagoNode.getNodo("extendedData", true);
                                        if (extendedDataNode != null) {
                                                XMLDocumentNode documentoNode = extendedDataNode.getNodo("documento", true);
                                                if (documentoNode != null) {
                                                        return documentoNode.getValue();
                                                }
                                        }
                                }
                        }
                } catch (XMLDocumentNodeNotFoundException e) {
                        log.debug("getNumPedido() - Nodo no encontrado: {}", e.getMessage());
                }
                return null;
        }

        protected String obtenerValorTagXml(Document document, String nodo) {
                NodeList fiscalDataNodes = document.getElementsByTagName("fiscal_data");
                if (fiscalDataNodes.getLength() > 0) {
                        Node fiscalDataNode = fiscalDataNodes.item(0);
                        if (fiscalDataNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element fiscalDataElement = (Element) fiscalDataNode;
                                NodeList propertyNodes = fiscalDataElement.getElementsByTagName("property");

                                for (int i = 0; i < propertyNodes.getLength(); i++) {
                                        Element propertyElement = (Element) propertyNodes.item(i);
                                        String name = propertyElement.getElementsByTagName("name").item(0).getTextContent();
                                        if (nodo.equals(name)) {
                                                return propertyElement.getElementsByTagName("value").item(0).getTextContent();
                                        }
                                }
                        }
                }
                return null;
        }
}
