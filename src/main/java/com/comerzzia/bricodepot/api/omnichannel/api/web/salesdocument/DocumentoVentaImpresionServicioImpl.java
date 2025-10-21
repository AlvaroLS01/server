package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import com.comerzzia.aena.util.xml.MarshallUtil;
import com.comerzzia.api.core.service.util.ComerzziaDatosSesion;
import com.comerzzia.core.model.actividades.ActividadBean;
import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.servicios.instancias.ServicioInstanciasImpl;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.servicios.tipodocumento.ServicioTiposDocumentosImpl;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoBean;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoException;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoNotFoundException;
import com.comerzzia.core.servicios.ventas.tickets.TicketService;
import com.comerzzia.core.util.xml.XMLDocument;
import com.comerzzia.core.util.xml.XMLDocumentNode;
import com.comerzzia.core.util.xml.XMLDocumentNodeNotFoundException;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.LineaTicket;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono;
import com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.CargarFacturaA4Servicio;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class DocumentoVentaImpresionServicioImpl implements DocumentoVentaImpresionServicio {

        private static final Logger log = LoggerFactory.getLogger(DocumentoVentaImpresionServicioImpl.class);
        private static final String RECURSOS_FACTURAS = "classpath:informes/ventas/facturas/";

        private final TicketService ticketService;
        private final CargarFacturaA4Servicio cargarFacturaA4Servicio;
        private final PathMatchingResourcePatternResolver resourceResolver;

        @Resource(name = "datosSesionRequest")
        private ComerzziaDatosSesion datosSesionRequest;

        @Autowired
        public DocumentoVentaImpresionServicioImpl(TicketService ticketService,
                        CargarFacturaA4Servicio cargarFacturaA4Servicio) {
                this.ticketService = ticketService;
                this.cargarFacturaA4Servicio = cargarFacturaA4Servicio;
                this.resourceResolver = new PathMatchingResourcePatternResolver();
        }

        @Override
        public Optional<DocumentoVentaImpresionRespuesta> imprimir(String uidDocumento,
                        OpcionesImpresionDocumentoVenta opciones) {
                try {
                        IDatosSesion datosSesion = datosSesionRequest.getDatosSesionBean();
                        TicketBean ticketBean = ticketService.consultarTicketUid(datosSesion, uidDocumento);

                        if (ticketBean == null) {
                                log.warn("imprimir() - Ticket no encontrado: {}", uidDocumento);
                                return Optional.empty();
                        }

                        TicketVentaAbono ticketVenta = MarshallUtil.leerXML(ticketBean.getTicket(), TicketVentaAbono.class);

                        if (ticketVenta == null) {
                                log.warn("imprimir() - No se ha podido convertir el ticket {}", uidDocumento);
                                return Optional.empty();
                        }

                        Map<String, Object> parametros = prepararParametros(ticketBean, ticketVenta, datosSesion, opciones);
                        String plantilla = determinarPlantilla(ticketBean, ticketVenta, datosSesion, opciones, parametros);
                        byte[] pdf = generarPdf(plantilla, parametros, datosSesion.getLocale());

                        if (pdf == null || pdf.length == 0) {
                                log.warn("imprimir() - No se ha generado contenido para el ticket {}", uidDocumento);
                                return Optional.empty();
                        }

                        String contenido = Base64.getEncoder().encodeToString(pdf);
                        String nombreDocumento = StringUtils.defaultIfBlank(opciones.getNombreDocumentoSalida(), uidDocumento);
                        DocumentoVentaImpresionRespuesta respuesta = new DocumentoVentaImpresionRespuesta(
                                        opciones.getMimeType(), nombreDocumento, opciones.isEnLinea(), opciones.isCopia(),
                                        contenido);

                        return Optional.of(respuesta);
                }
                catch (Exception e) {
                        log.error("imprimir() - Error generando factura {}", uidDocumento, e);
                        return Optional.empty();
                }
        }

        protected Map<String, Object> prepararParametros(TicketBean ticketBean, TicketVentaAbono ticketVenta,
                        IDatosSesion datosSesion, OpcionesImpresionDocumentoVenta opciones) throws Exception {
                Map<String, Object> parametros = new HashMap<>(opciones.getParametrosPersonalizados());
                parametros.put("ticket", ticketVenta);
                parametros.put("esDuplicado", opciones.isCopia());
                parametros.put("DEVOLUCION", Boolean.FALSE);

                if (ticketBean.getFecha() != null) {
                        parametros.put("fecha", ticketBean.getFecha());
                }

                if (StringUtils.isNotBlank(ticketBean.getLocatorId())) {
                        parametros.put("LOCATOR_ID", ticketBean.getLocatorId());
                }

                parametros.put(JRParameter.REPORT_LOCALE, datosSesion.getLocale());
                parametros.put("UID_ACTIVIDAD", datosSesion.getUidActividad());

                org.w3c.dom.Document xml = ticketBean.getXml();
                if (xml != null) {
                        String fechaOrigen = obtenerFechaOrigen(xml);
                        if (fechaOrigen != null) {
                                parametros.put("fecha_origen", fechaOrigen);
                        }

                        try {
                                String documento = getNumPedido(xml);
                                if (documento != null) {
                                        parametros.put("numPedido", documento);
                                }
                        }
                        catch (XMLDocumentNodeNotFoundException e) {
                                log.debug("prepararParametros() - No se encontró numPedido en el ticket: {}", e.getMessage());
                        }
                }

                try {
                        ActividadBean actividad = ServicioInstanciasImpl.get().consultarActividad(ticketBean.getUidActividad());
                        if (actividad != null) {
                                parametros.put("UID_INSTANCIA", actividad.getUidInstancia());
                        }
                }
                catch (Exception e) {
                        log.warn("prepararParametros() - No se pudo obtener la actividad {}", ticketBean.getUidActividad(), e);
                }

                cargarFacturaA4Servicio.generarMediosPago(ticketVenta, datosSesion);
                cargarFacturaA4Servicio.cargarDatosPagoTarjeta(ticketBean, ticketVenta, parametros);
                cargarFacturaA4Servicio.cargarPromociones(ticketBean, parametros);
                cargarFacturaA4Servicio.getPagoGiftCard(ticketVenta, parametros);

                List<LineaTicket> lineasAgrupadas = cargarFacturaA4Servicio.getLineasAgrupadas(ticketVenta);
                parametros.put("lineasAgrupadas", lineasAgrupadas);

                return parametros;
        }

        protected String determinarPlantilla(TicketBean ticketBean, TicketVentaAbono ticketVenta, IDatosSesion datosSesion,
                        OpcionesImpresionDocumentoVenta opciones, Map<String, Object> parametros)
                        throws TipoDocumentoException, TipoDocumentoNotFoundException, Exception {
                if (StringUtils.isNotBlank(opciones.getPlantillaImpresion())) {
                        return opciones.getPlantillaImpresion();
                }

                TipoDocumentoBean tipoDocumento = ServicioTiposDocumentosImpl.get()
                                .consultar(datosSesion, ticketVenta.getCabecera().getTipoDocumento());

                String codPais = tipoDocumento != null ? tipoDocumento.getCodPais() : null;
                boolean devolucion = "FR".equals(ticketVenta.getCabecera().getCodTipoDocumento());
                parametros.put("DEVOLUCION", devolucion);

                if ("PT".equalsIgnoreCase(codPais)) {
                        cargarFacturaA4Servicio.addFiscalData(ticketBean, parametros);
                        return devolucion ? "facturaDevolucionA4_PT.jasper" : "facturaA4_PT.jasper";
                }
                if ("CA".equalsIgnoreCase(codPais)) {
                        return "facturaA4_CA.jasper";
                }

                return "facturaA4.jasper";
        }

        protected byte[] generarPdf(String plantilla, Map<String, Object> parametros, Locale locale)
                        throws IOException, JRException {
                parametros.put(JRParameter.REPORT_LOCALE, locale);
                Path tempDir = Files.createTempDirectory("facturaA4");

                try {
                        copiarSubinformes(tempDir);
                        parametros.put("SUBREPORT_DIR", tempDir.toAbsolutePath().toString() + java.io.File.separator);

                        org.springframework.core.io.Resource mainTemplate = resourceResolver
                                        .getResource(RECURSOS_FACTURAS + plantilla);

                        if (!mainTemplate.exists()) {
                                throw new IOException("No se encontró la plantilla de impresión: " + plantilla);
                        }

                        try (InputStream in = mainTemplate.getInputStream()) {
                                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(in);
                                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros,
                                                new JREmptyDataSource());
                                ByteArrayOutputStream out = new ByteArrayOutputStream();
                                JRPdfExporter exporter = new JRPdfExporter();
                                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
                                exporter.exportReport();
                                return out.toByteArray();
                        }
                }
                finally {
                        FileSystemUtils.deleteRecursively(tempDir);
                }
        }

        protected void copiarSubinformes(Path tempDir) throws IOException {
                org.springframework.core.io.Resource[] recursos = resourceResolver
                                .getResources("classpath*:informes/ventas/facturas/*.jasper");

                for (org.springframework.core.io.Resource recurso : recursos) {
                        try (InputStream inputStream = recurso.getInputStream()) {
                                Files.copy(inputStream, tempDir.resolve(recurso.getFilename()),
                                                StandardCopyOption.REPLACE_EXISTING);
                        }
                }
        }

        protected String obtenerFechaOrigen(org.w3c.dom.Document document) {
                if (document != null && document.getElementsByTagName("fechaTicketOrigen") != null
                                && document.getElementsByTagName("fechaTicketOrigen").item(0) != null) {
                        return document.getElementsByTagName("fechaTicketOrigen").item(0).getTextContent();
                }
                return null;
        }

        protected String getNumPedido(org.w3c.dom.Document document) throws XMLDocumentNodeNotFoundException {
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
                return null;
        }
}
