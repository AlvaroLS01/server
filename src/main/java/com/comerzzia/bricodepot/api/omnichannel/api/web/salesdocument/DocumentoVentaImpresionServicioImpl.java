package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.api.core.service.util.ComerzziaDatosSesion;
import com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion.FacturaDocumento;
import com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion.FacturaDocumentoLoader;
import com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion.FacturaReportParametersBuilder;
import com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion.FacturaTemplateResolver;
import com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion.JasperReportRenderer;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.servicios.tipodocumento.ServicioTiposDocumentosImpl;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoBean;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoException;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoNotFoundException;

import net.sf.jasperreports.engine.JRException;

@Service
public class DocumentoVentaImpresionServicioImpl implements DocumentoVentaImpresionServicio {

    private static final Logger log = LoggerFactory.getLogger(DocumentoVentaImpresionServicioImpl.class);

    private final FacturaDocumentoLoader documentoLoader;
    private final FacturaReportParametersBuilder parametersBuilder;
    private final FacturaTemplateResolver templateResolver;
    private final JasperReportRenderer jasperRenderer;

    @Resource(name = "datosSesionRequest")
    private ComerzziaDatosSesion datosSesionRequest;

    @Autowired
    public DocumentoVentaImpresionServicioImpl(FacturaDocumentoLoader documentoLoader,
            FacturaReportParametersBuilder parametersBuilder, FacturaTemplateResolver templateResolver,
            JasperReportRenderer jasperRenderer) {
        this.documentoLoader = documentoLoader;
        this.parametersBuilder = parametersBuilder;
        this.templateResolver = templateResolver;
        this.jasperRenderer = jasperRenderer;
    }

    @Override
    public Optional<DocumentoVentaImpresionRespuesta> imprimir(String uidDocumento,
            OpcionesImpresionDocumentoVenta opciones) {
        try {
            IDatosSesion datosSesion = datosSesionRequest.getDatosSesionBean();
            Optional<FacturaDocumento> documentoOpt = documentoLoader.load(uidDocumento, datosSesion);

            if (!documentoOpt.isPresent()) {
                return Optional.empty();
            }

            FacturaDocumento documento = documentoOpt.get();
            Map<String, Object> parametros = parametersBuilder.build(documento, datosSesion, opciones);

            String plantilla = opciones.getPlantillaImpresion();
            if (!StringUtils.hasText(plantilla)) {
                TipoDocumentoBean tipoDocumento = obtenerTipoDocumento(datosSesion, documento);
                plantilla = templateResolver.resolve(documento.getTicketVenta(), tipoDocumento, parametros);
            }

            byte[] pdf = jasperRenderer.render(plantilla, parametros, datosSesion.getLocale());

            if (pdf == null || pdf.length == 0) {
                log.warn("imprimir() - No se generó contenido para el ticket {}", uidDocumento);
                return Optional.empty();
            }

            String contenido = Base64.getEncoder().encodeToString(pdf);
            String nombreDocumento = StringUtils.defaultIfBlank(opciones.getNombreDocumentoSalida(), uidDocumento);

            DocumentoVentaImpresionRespuesta respuesta = new DocumentoVentaImpresionRespuesta(opciones.getMimeType(),
                    nombreDocumento, opciones.isEnLinea(), opciones.isCopia(), contenido);

            return Optional.of(respuesta);
        }
        catch (TipoDocumentoNotFoundException | TipoDocumentoException e) {
            log.error("imprimir() - No se pudo obtener el tipo de documento {}", uidDocumento, e);
            return Optional.empty();
        }
        catch (JRException e) {
            log.error("imprimir() - Error Jasper generando factura {}", uidDocumento, e);
            return Optional.empty();
        }
        catch (Exception e) {
            log.error("imprimir() - Error generando factura {}", uidDocumento, e);
            return Optional.empty();
        }
    }

    private TipoDocumentoBean obtenerTipoDocumento(IDatosSesion datosSesion, FacturaDocumento documento)
            throws TipoDocumentoNotFoundException, TipoDocumentoException {
        if (documento.getTicketVenta() == null || documento.getTicketVenta().getCabecera() == null) {
            return null;
        }
        return ServicioTiposDocumentosImpl.get().consultar(datosSesion,
                documento.getTicketVenta().getCabecera().getTipoDocumento());
    }
}
