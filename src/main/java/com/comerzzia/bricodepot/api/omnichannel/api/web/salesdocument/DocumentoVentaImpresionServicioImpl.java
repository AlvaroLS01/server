package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
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
    private final ApplicationContext applicationContext;
    private volatile JAXBContext contextoTicket;
    private final Object contextoTicketLock = new Object();
    private volatile boolean contextoTicketInicializacionFallida;
    private volatile Object servicioTickets;
    private volatile boolean servicioTicketsBusquedaFallida;
    private final Object servicioTicketsLock = new Object();

    public DocumentoVentaImpresionServicioImpl(DocumentService documentService,
            GeneradorFacturaA4 generadorFacturaA4,
            @Qualifier("datosSesionRequest") ComerzziaDatosSesion datosSesionRequest,
            ApplicationContext applicationContext) {
        this.documentService = documentService;
        this.generadorFacturaA4 = generadorFacturaA4;
        this.datosSesionRequest = datosSesionRequest;
        this.applicationContext = applicationContext;
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
        IDatosSesion datosSesion = prepararDatosSesion();
        String uidActividadOriginal = obtenerUidActividad(datosSesion);
        String uidActividad = StringUtils.hasText(opciones.getUidActividad()) ? opciones.getUidActividad()
                : uidActividadOriginal;
        if (!StringUtils.hasText(uidActividad)) {
            throw new DocumentoVentaImpresionException(
                    "No se pudo determinar el uid de actividad de la sesión para imprimir el documento "
                            + uidDocumento);
        }

        if (!StringUtils.hasText(opciones.getUidActividad()) && LOGGER.isDebugEnabled()) {
            LOGGER.debug("Utilizando el uid de actividad {} obtenido del contexto de sesión para imprimir {}",
                    uidActividad, uidDocumento);
        }

        try {
            establecerUidActividad(datosSesion, uidActividad);
            DocumentEntity documento = obtenerDocumento(datosSesion, uidDocumento);
            Optional<DocumentoVentaImpresionRespuesta> resultado = generarRespuestaDesdeDocumento(uidDocumento, uidActividad,
                    opciones, documento);
            if (resultado.isPresent()) {
                return resultado;
            }

            LOGGER.debug("Intentando generar la factura {} localizando el ticket desde el repositorio", uidDocumento);
            return generarRespuestaDesdeRepositorio(uidDocumento, uidActividad, datosSesion, opciones);
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

    private Optional<GeneradorFacturaA4.FacturaGenerada> generarFactura(Object ticket,
            OpcionesImpresionDocumentoVenta opciones) {
        try {
            return generadorFacturaA4.generarFactura(ticket, opciones);
        } catch (IOException excepcion) {
            throw new DocumentoVentaImpresionException("Error al generar el informe Jasper del documento", excepcion);
        }
    }

    private Optional<DocumentoVentaImpresionRespuesta> generarRespuestaDesdeRepositorio(String uidDocumento,
            String uidActividad, IDatosSesion datosSesion, OpcionesImpresionDocumentoVenta opciones) {
        Object ticket = localizarTicket(uidDocumento, uidActividad, datosSesion);
        if (ticket == null) {
            LOGGER.warn("No se pudo localizar un ticket interpretable para el documento {}", uidDocumento);
            return Optional.empty();
        }

        GeneradorFacturaA4.FacturaGenerada factura = generarFactura(ticket, opciones).orElse(null);
        if (factura == null) {
            LOGGER.warn("No se pudo generar la factura para el documento {}", uidDocumento);
            return Optional.empty();
        }

        DocumentoVentaImpresionResultado resultado = construirResultado(uidDocumento, opciones, factura);
        return Optional.of(convertirARespuesta(resultado));
    }

    private Object localizarTicket(String uidDocumento, String uidActividad, IDatosSesion datosSesion) {
        byte[] contenido = recuperarContenidoTicket(uidDocumento, uidActividad, datosSesion);
        if (contenido == null || contenido.length == 0) {
            return null;
        }
        return convertirTicket(contenido);
    }

    private byte[] recuperarContenidoTicket(String uidDocumento, String uidActividad, IDatosSesion datosSesion) {
        Object servicio = obtenerServicioTickets();
        if (servicio == null) {
            return null;
        }

        Object ticketBean = consultarTicket(servicio, datosSesion, uidDocumento, uidActividad);
        if (ticketBean == null) {
            LOGGER.warn("El servicio de tickets no devolvió información para el documento {}", uidDocumento);
            return null;
        }

        byte[] contenido = extraerContenidoTicket(ticketBean);
        if (contenido == null || contenido.length == 0) {
            LOGGER.warn("El ticket recuperado para el documento {} no contiene datos", uidDocumento);
        }
        return contenido;
    }

    private Object obtenerServicioTickets() {
        if (servicioTickets != null || servicioTicketsBusquedaFallida) {
            return servicioTickets;
        }
        synchronized (servicioTicketsLock) {
            if (servicioTickets != null || servicioTicketsBusquedaFallida) {
                return servicioTickets;
            }
            Object localizado = localizarBeanTicketService();
            if (localizado == null) {
                localizado = localizarServicioTicketsEstatico();
            }
            if (localizado == null) {
                servicioTicketsBusquedaFallida = true;
                LOGGER.warn("No se pudo localizar un servicio TicketService/ServicioTicketsImpl para recuperar tickets");
                return null;
            }
            servicioTickets = localizado;
            return servicioTickets;
        }
    }

    private Object localizarBeanTicketService() {
        if (applicationContext == null) {
            return null;
        }
        try {
            Class<?> tipoServicio = Class.forName("com.comerzzia.core.servicios.ventas.tickets.TicketService");
            return applicationContext.getBean(tipoServicio);
        } catch (ClassNotFoundException excepcion) {
            LOGGER.debug("No se encontró la clase TicketService en el classpath", excepcion);
            return null;
        } catch (BeansException excepcion) {
            LOGGER.debug("No se pudo obtener el bean TicketService del contexto", excepcion);
            return null;
        }
    }

    private Object localizarServicioTicketsEstatico() {
        try {
            Class<?> clase = Class.forName("com.comerzzia.core.servicios.ventas.tickets.ServicioTicketsImpl");
            Method metodo = clase.getMethod("get");
            return metodo.invoke(null);
        } catch (ClassNotFoundException excepcion) {
            LOGGER.debug("ServicioTicketsImpl no está disponible en el classpath", excepcion);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException excepcion) {
            LOGGER.warn("No se pudo inicializar ServicioTicketsImpl para recuperar tickets", excepcion);
        }
        return null;
    }

    private Object consultarTicket(Object servicio, IDatosSesion datosSesion, String uidDocumento, String uidActividad) {
        if (servicio == null) {
            return null;
        }

        List<Object[]> intentos = new ArrayList<>();
        if (datosSesion != null) {
            intentos.add(new Object[] { datosSesion, uidDocumento });
            intentos.add(new Object[] { uidDocumento, datosSesion });
        }
        if (StringUtils.hasText(uidActividad)) {
            intentos.add(new Object[] { uidDocumento, uidActividad });
            intentos.add(new Object[] { uidActividad, uidDocumento });
            if (datosSesion != null) {
                intentos.add(new Object[] { datosSesion, uidActividad });
                intentos.add(new Object[] { uidActividad, datosSesion });
                intentos.add(new Object[] { datosSesion, uidDocumento, uidActividad });
                intentos.add(new Object[] { uidDocumento, datosSesion, uidActividad });
                intentos.add(new Object[] { uidDocumento, uidActividad, datosSesion });
                intentos.add(new Object[] { uidActividad, uidDocumento, datosSesion });
            }
        }
        intentos.add(new Object[] { uidDocumento });

        for (Object[] parametros : intentos) {
            Object ticket = intentarInvocar(servicio, "consultarTicketUid", parametros);
            if (ticket != null) {
                return ticket;
            }
        }

        LOGGER.warn("El servicio {} no dispone de un método compatible para consultar tickets por uid", servicio.getClass().getName());
        return null;
    }

    private Object intentarInvocar(Object destino, String metodo, Object[] parametros) {
        Method candidato = localizarMetodoCompatible(destino.getClass(), metodo, parametros);
        if (candidato == null) {
            return null;
        }
        try {
            return candidato.invoke(destino, parametros);
        } catch (IllegalAccessException | InvocationTargetException excepcion) {
            LOGGER.debug("No se pudo invocar {} en {}", metodo, destino.getClass().getName(), excepcion);
            return null;
        }
    }

    private Method localizarMetodoCompatible(Class<?> tipo, String nombre, Object[] argumentos) {
        for (Method metodo : tipo.getMethods()) {
            if (!metodo.getName().equals(nombre)) {
                continue;
            }
            Class<?>[] parametros = metodo.getParameterTypes();
            if (parametros.length != argumentos.length) {
                continue;
            }
            boolean compatible = true;
            for (int i = 0; i < parametros.length; i++) {
                Object argumento = argumentos[i];
                Class<?> tipoParametro = parametros[i];
                if (argumento == null) {
                    if (tipoParametro.isPrimitive()) {
                        compatible = false;
                        break;
                    }
                    continue;
                }
                if (!esAsignable(tipoParametro, argumento)) {
                    compatible = false;
                    break;
                }
            }
            if (compatible) {
                return metodo;
            }
        }
        return null;
    }

    private boolean esAsignable(Class<?> parametro, Object argumento) {
        if (parametro.isInstance(argumento)) {
            return true;
        }
        if (parametro.isPrimitive()) {
            Class<?> envoltorio = obtenerWrapper(parametro);
            return envoltorio != null && envoltorio.isInstance(argumento);
        }
        return parametro.isAssignableFrom(argumento.getClass());
    }

    private Class<?> obtenerWrapper(Class<?> tipoPrimitivo) {
        if (tipoPrimitivo == boolean.class) {
            return Boolean.class;
        }
        if (tipoPrimitivo == byte.class) {
            return Byte.class;
        }
        if (tipoPrimitivo == char.class) {
            return Character.class;
        }
        if (tipoPrimitivo == double.class) {
            return Double.class;
        }
        if (tipoPrimitivo == float.class) {
            return Float.class;
        }
        if (tipoPrimitivo == int.class) {
            return Integer.class;
        }
        if (tipoPrimitivo == long.class) {
            return Long.class;
        }
        if (tipoPrimitivo == short.class) {
            return Short.class;
        }
        return null;
    }

    private byte[] extraerContenidoTicket(Object ticketBean) {
        if (ticketBean == null) {
            return null;
        }

        byte[] contenido = intentarObtenerContenido(ticketBean, "getTicket");
        if (contenido != null) {
            return contenido;
        }
        contenido = intentarObtenerContenido(ticketBean, "getXml");
        if (contenido != null) {
            return contenido;
        }

        for (Method metodo : ticketBean.getClass().getMethods()) {
            if (metodo.getParameterCount() != 0) {
                continue;
            }
            try {
                Object valor = metodo.invoke(ticketBean);
                byte[] bytes = convertirABytes(valor);
                if (bytes != null) {
                    return bytes;
                }
            } catch (IllegalAccessException | InvocationTargetException excepcion) {
                LOGGER.debug("No se pudo invocar {} en {}", metodo.getName(), ticketBean.getClass().getName(), excepcion);
            }
        }
        return null;
    }

    private byte[] intentarObtenerContenido(Object origen, String nombreMetodo) {
        try {
            Method metodo = origen.getClass().getMethod(nombreMetodo);
            Object valor = metodo.invoke(origen);
            return convertirABytes(valor);
        } catch (NoSuchMethodException excepcion) {
            return null;
        } catch (IllegalAccessException | InvocationTargetException excepcion) {
            LOGGER.debug("No se pudo invocar {} en {}", nombreMetodo, origen.getClass().getName(), excepcion);
            return null;
        }
    }

    private byte[] convertirABytes(Object valor) {
        if (valor == null) {
            return null;
        }
        if (valor instanceof byte[]) {
            return (byte[]) valor;
        }
        if (valor instanceof String) {
            return ((String) valor).getBytes(StandardCharsets.UTF_8);
        }
        return null;
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

