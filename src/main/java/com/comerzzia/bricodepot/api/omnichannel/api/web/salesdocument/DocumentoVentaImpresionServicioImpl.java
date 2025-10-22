package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.comerzzia.api.core.service.util.ComerzziaDatosSesion;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.entity.document.DocumentEntity;
import com.comerzzia.omnichannel.service.document.DocumentService;

/**
 * Default implementation of {@link DocumentoVentaImpresionServicio} that reads
 * the ticket information of a sales document and renders it using the custom
 * Jasper templates shipped with this project.
 */
@Service
public class DocumentoVentaImpresionServicioImpl implements DocumentoVentaImpresionServicio {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoVentaImpresionServicioImpl.class);

    private static final String CONTEXTO_JAXB_TICKET =
            "com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket";
    private static final String MIME_PDF = "application/pdf";

    private final DocumentService documentService;
    private final GeneradorFacturaA4 generadorFacturaA4;
    private final ComerzziaDatosSesion datosSesionRequest;
    private volatile JAXBContext contextoTicket;
    private final Object contextoTicketLock = new Object();
    private volatile boolean contextoTicketInicializacionFallida;

    public DocumentoVentaImpresionServicioImpl(DocumentService documentService,
            GeneradorFacturaA4 generadorFacturaA4,
            @Qualifier("datosSesionRequest") ComerzziaDatosSesion datosSesionRequest) {
        this.documentService = documentService;
        this.generadorFacturaA4 = generadorFacturaA4;
        this.datosSesionRequest = datosSesionRequest;
    }

    @Override
    public Optional<DocumentoVentaImpresionRespuesta> imprimir(String uidDocumento,
            OpcionesImpresionDocumentoVenta opciones) {
        if (!StringUtils.hasText(uidDocumento)) {
            throw new DocumentoVentaImpresionException("Es obligatorio indicar el identificador del documento a imprimir");
        }
        if (opciones == null) {
            throw new DocumentoVentaImpresionException("Las opciones de impresión no pueden ser nulas");
        }
        String uidActividad = opciones.getUidActividad();
        if (!StringUtils.hasText(uidActividad)) {
            throw new DocumentoVentaImpresionException(
                    "Es obligatorio indicar el uid de actividad para imprimir el documento " + uidDocumento);
        }

        IDatosSesion datosSesion = prepararDatosSesion();
        String uidActividadOriginal = obtenerUidActividad(datosSesion);

        try {
            establecerUidActividad(datosSesion, uidActividad);
            DocumentEntity documento = obtenerDocumento(datosSesion, uidDocumento);
            Optional<DocumentoVentaImpresionRespuesta> resultado = generarRespuestaDesdeDocumento(uidDocumento, uidActividad,
                    opciones, documento);
            if (resultado.isPresent()) {
                return resultado;
            }

            LOGGER.debug("Intentando generar la factura {} localizando el ticket mediante servicios internos", uidDocumento);
            return generarRespuestaDesdeServicios(uidDocumento, opciones);
        } finally {
            try {
                establecerUidActividad(datosSesion, uidActividadOriginal);
            } catch (DocumentoVentaImpresionException excepcion) {
                LOGGER.debug("No se pudo restaurar el uid de actividad original tras la impresión", excepcion);
            }
        }
    }

    private IDatosSesion prepararDatosSesion() {
        if (datosSesionRequest == null) {
            throw new DocumentoVentaImpresionException(
                    "No hay datos de sesión disponibles para ejecutar la impresión del documento");
        }
        IDatosSesion datosSesion = datosSesionRequest.getDatosSesionBean();
        if (datosSesion == null) {
            throw new DocumentoVentaImpresionException(
                    "No fue posible obtener los datos de sesión asociados a la petición");
        }
        return datosSesion;
    }

    private DocumentEntity obtenerDocumento(IDatosSesion datosSesion, String uidDocumento) {
        try {
            return documentService.findById(datosSesion, uidDocumento);
        } catch (Exception excepcion) {
            throw new DocumentoVentaImpresionException(
                    "Error al recuperar la información del documento " + uidDocumento, excepcion);
        }
    }

    private Optional<DocumentoVentaImpresionRespuesta> generarRespuestaDesdeDocumento(String uidDocumento,
            String uidActividad, OpcionesImpresionDocumentoVenta opciones, DocumentEntity documento) {
        if (documento == null) {
            LOGGER.warn("No se encontró el documento {} para la actividad {}", uidDocumento, uidActividad);
            return Optional.empty();
        }

        Object ticket = convertirTicket(documento.getDocumentContent());
        if (ticket == null) {
            LOGGER.warn("El documento {} no contiene información de ticket interpretable", uidDocumento);
            return Optional.empty();
        }

        GeneradorFacturaA4.FacturaGenerada factura = generarFactura(ticket, opciones)
                .orElse(null);
        if (factura == null) {
            LOGGER.warn("No se pudo generar la factura para el documento {}", uidDocumento);
            return Optional.empty();
        }

        DocumentoVentaImpresionResultado resultado = construirResultado(uidDocumento, opciones, factura);
        return Optional.of(convertirARespuesta(resultado));
    }

    private Optional<DocumentoVentaImpresionRespuesta> generarRespuestaDesdeServicios(String uidDocumento,
            OpcionesImpresionDocumentoVenta opciones) {
        GeneradorFacturaA4.FacturaGenerada factura = generarFacturaDesdeServicios(uidDocumento, opciones)
                .orElse(null);
        if (factura == null) {
            LOGGER.warn("No se pudo localizar un ticket interpretable para el documento {}", uidDocumento);
            return Optional.empty();
        }

        DocumentoVentaImpresionResultado resultado = construirResultado(uidDocumento, opciones, factura);
        return Optional.of(convertirARespuesta(resultado));
    }

    private Optional<GeneradorFacturaA4.FacturaGenerada> generarFactura(Object ticket,
            OpcionesImpresionDocumentoVenta opciones) {
        try {
            return generadorFacturaA4.generarFactura(ticket, opciones);
        } catch (IOException excepcion) {
            throw new DocumentoVentaImpresionException("Error al generar el informe Jasper del documento", excepcion);
        }
    }

    private Optional<GeneradorFacturaA4.FacturaGenerada> generarFacturaDesdeServicios(String uidDocumento,
            OpcionesImpresionDocumentoVenta opciones) {
        try {
            return generadorFacturaA4.generarFactura(uidDocumento, opciones);
        } catch (IOException excepcion) {
            throw new DocumentoVentaImpresionException("Error al generar el informe Jasper del documento", excepcion);
        }
    }

    private DocumentoVentaImpresionResultado construirResultado(String uidDocumento,
            OpcionesImpresionDocumentoVenta opciones, GeneradorFacturaA4.FacturaGenerada factura) {
        String mimeType = StringUtils.hasText(opciones.getTipoMime()) ? opciones.getTipoMime() : MIME_PDF;
        return new DocumentoVentaImpresionResultado(uidDocumento, opciones.esCopia(), opciones.esEnLinea(), mimeType,
                factura.getNombreFichero(), factura.getContenidoPdf());
    }

    private DocumentoVentaImpresionRespuesta convertirARespuesta(DocumentoVentaImpresionResultado resultado) {
        DocumentoVentaImpresionRespuesta respuesta = new DocumentoVentaImpresionRespuesta();
        respuesta.setUidDocumento(resultado.getUidDocumento());
        respuesta.setTipoMime(resultado.getTipoMime());
        respuesta.setNombreArchivo(resultado.getNombreArchivo());
        respuesta.setCopia(resultado.isCopia());
        respuesta.setEnLinea(resultado.isEnLinea());
        respuesta.setDocumento(Base64.getEncoder().encodeToString(resultado.getContenido()));
        return respuesta;
    }

    private Object convertirTicket(byte[] contenidoDocumento) {
        if (contenidoDocumento == null || contenidoDocumento.length == 0) {
            return null;
        }
        JAXBContext contexto = obtenerContextoTicket();
        if (contexto == null) {
            LOGGER.warn("No existe un contexto JAXB disponible para interpretar tickets de venta");
            return null;
        }
        try {
            Unmarshaller conversor = contexto.createUnmarshaller();
            Object resultado = conversor.unmarshal(new ByteArrayInputStream(contenidoDocumento));
            if (resultado instanceof JAXBElement) {
                return ((JAXBElement<?>) resultado).getValue();
            }
            return resultado;
        } catch (JAXBException excepcion) {
            throw new DocumentoVentaImpresionException("No fue posible interpretar el contenido del documento", excepcion);
        }
    }

    private JAXBContext obtenerContextoTicket() {
        if (contextoTicket == null && !contextoTicketInicializacionFallida) {
            synchronized (contextoTicketLock) {
                if (contextoTicket == null && !contextoTicketInicializacionFallida) {
                    try {
                        contextoTicket = JAXBContext.newInstance(CONTEXTO_JAXB_TICKET);
                    } catch (JAXBException excepcion) {
                        contextoTicketInicializacionFallida = true;
                        LOGGER.warn("No se pudo inicializar el contexto JAXB para los tickets de venta", excepcion);
                    }
                }
            }
        }
        return contextoTicket;
    }

    private String obtenerUidActividad(IDatosSesion datosSesion) {
        try {
            Method metodo = datosSesion.getClass().getMethod("getUidActividad");
            Object valor = metodo.invoke(datosSesion);
            return valor != null ? valor.toString() : null;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException excepcion) {
            LOGGER.debug("No se pudo obtener el uid de actividad actual de la sesión", excepcion);
            return null;
        }
    }

    private void establecerUidActividad(IDatosSesion datosSesion, String uidActividad) {
        try {
            Method metodo = datosSesion.getClass().getMethod("setUidActividad", String.class);
            metodo.invoke(datosSesion, uidActividad);
        } catch (NoSuchMethodException excepcion) {
            throw new DocumentoVentaImpresionException(
                    "La sesión no permite establecer el uid de actividad necesario para la impresión", excepcion);
        } catch (IllegalAccessException | InvocationTargetException excepcion) {
            throw new DocumentoVentaImpresionException(
                    "Error al actualizar el uid de actividad en los datos de sesión", excepcion);
        }
    }
}

