package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.comerzzia.core.model.tiposdocumentos.TipoDocumentoBean;
import com.comerzzia.core.servicios.ContextHolder;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoException;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoNotFoundException;
import com.comerzzia.core.servicios.tipodocumento.TiposDocumentosService;
import com.comerzzia.omnichannel.domain.entity.document.DocumentEntity;
import com.comerzzia.omnichannel.model.documents.util.FormatUtil;
import com.comerzzia.omnichannel.service.salesdocument.converters.SaleDocumentConverter;
import com.comerzzia.omnichannel.service.salesdocument.metadata.DocumentMetadata;
import com.comerzzia.omnichannel.service.salesdocument.metadata.DocumentMetadataParser;

/**
 * Utility component responsible for converting the raw omnichannel document
 * content into the corresponding {@code TicketVentaAbono} model using the same
 * converter infrastructure as the standard omnichannel services. This ensures
 * that the API printing endpoint always relies on the up-to-date ticket
 * representation regardless of the schema version stored in the database.
 */
@Component
public class DocumentoVentaConvertidor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoVentaConvertidor.class);

    private final TiposDocumentosService tiposDocumentosService;
    private final DocumentMetadataParser metadataParser;

    public DocumentoVentaConvertidor(TiposDocumentosService tiposDocumentosService,
            DocumentMetadataParser metadataParser) {
        this.tiposDocumentosService = tiposDocumentosService;
        this.metadataParser = metadataParser;
    }

    public Object convertir(IDatosSesion datosSesion, DocumentEntity documento) {
        if (documento == null) {
            return null;
        }

        DocumentMetadata metadata = obtenerMetadata(datosSesion, documento);
        SaleDocumentConverter<?> convertidor = obtenerConvertidor(datosSesion, documento, metadata);

        Object ticket = convertidor.convert(documento.getDocumentContent());
        aplicarFormatUtil(ticket, metadata);
        return ticket;
    }

    private DocumentMetadata obtenerMetadata(IDatosSesion datosSesion, DocumentEntity documento) {
        try {
            return metadataParser.getMetadata(datosSesion, documento);
        } catch (RuntimeException excepcion) {
            throw new DocumentoVentaImpresionException(
                    "No se pudo interpretar los metadatos del documento " + documento.getDocumentUid(), excepcion);
        }
    }

    private SaleDocumentConverter<?> obtenerConvertidor(IDatosSesion datosSesion, DocumentEntity documento,
            DocumentMetadata metadata) {
        TipoDocumentoBean tipoDocumento = consultarTipoDocumento(datosSesion, documento.getDocumentTypeId(),
                documento.getDocumentUid());

        String codigoAplicacion = tipoDocumento.getCodAplicacion();
        if (codigoAplicacion == null
                || (!"VENT".equalsIgnoreCase(codigoAplicacion) && !"SALE".equalsIgnoreCase(codigoAplicacion))) {
            throw new DocumentoVentaImpresionException("El documento " + documento.getDocumentUid()
                    + " no corresponde a un documento de venta imprimible");
        }

        String versionEsquema = metadata != null ? metadata.getSchemaVersion() : null;
        if (!StringUtils.hasText(versionEsquema)) {
            throw new DocumentoVentaImpresionException(
                    "No se pudo determinar la versión de esquema del documento " + documento.getDocumentUid());
        }

        String nombreBean = tipoDocumento.getCodTipoDocumento() + "_" + versionEsquema.replace('.', '_')
                + "_DocumentConverter";
        try {
            return (SaleDocumentConverter<?>) ContextHolder.getBean(nombreBean);
        } catch (RuntimeException excepcion) {
            throw new DocumentoVentaImpresionException(
                    "No se encontró el convertidor para el tipo de documento " + tipoDocumento.getCodTipoDocumento()
                            + " y la versión de esquema " + versionEsquema,
                    excepcion);
        }
    }

    private TipoDocumentoBean consultarTipoDocumento(IDatosSesion datosSesion, Long idTipoDocumento, String uidDocumento) {
        if (idTipoDocumento == null) {
            throw new DocumentoVentaImpresionException(
                    "El documento " + uidDocumento + " no tiene un tipo de documento asociado");
        }
        try {
            return tiposDocumentosService.consultar(datosSesion, idTipoDocumento);
        } catch (TipoDocumentoNotFoundException | TipoDocumentoException excepcion) {
            throw new DocumentoVentaImpresionException(
                    "No se pudo recuperar la información del tipo de documento " + idTipoDocumento, excepcion);
        }
    }

    private void aplicarFormatUtil(Object ticket, DocumentMetadata metadata) {
        if (ticket == null || metadata == null || metadata.getLocale() == null) {
            return;
        }
        try {
            Method metodo = ticket.getClass().getMethod("setFormatUtil", FormatUtil.class);
            metodo.invoke(ticket, new FormatUtil(metadata.getLocale()));
        } catch (NoSuchMethodException excepcion) {
            LOGGER.debug("El ticket {} no expone setFormatUtil. Se continuará sin formateador", ticket.getClass().getName());
        } catch (IllegalAccessException | InvocationTargetException excepcion) {
            throw new DocumentoVentaImpresionException("No se pudo configurar el formateador del ticket", excepcion);
        }
    }
}
