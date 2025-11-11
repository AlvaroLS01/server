package com.comerzzia.api.v2.facturacionmagento.services.ticket.audit;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.FacturacionRequest;
import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.servicios.contadores.ServicioContadoresImpl;
import com.comerzzia.core.servicios.sesion.DatosSesionBean;
import com.comerzzia.core.servicios.ventas.tickets.TicketService;

/**
 * @author jbn
 * @since 27/08/2021
 * @version 1.0
 */
@Service
public class TicketAuditService {

	public static final String AUDITABLE_EVENT_ID_COUNTER = "ID_AUDITABLE_EVENT";

	protected static final Logger log = Logger.getLogger(TicketAuditService.class);

	@Autowired
	private ServicioContadoresImpl contadoresService;
	@Autowired
	private TicketService ticketsService;

	/**
	 * Convierte un evento de auditoria en un ticket y lo guarda en DB
	 * 
	 * @see com.comerzzia.bricodepot.pos.gui.ventas.tickets.BricodepotTicketManager
	 * @see com.comerzzia.bricodepot.pos.services.ticket.BricodepotTicketService
	 * @param auditEvent
	 *            Evento
	 * @param request
	 * @param datosSesion
	 */
	@SuppressWarnings("deprecation")
	public synchronized void saveAuditEvent(TicketAuditEvent auditEvent, FacturacionRequest request, DatosSesionBean datosSesion) {

		byte[] xmlBytes = null;
		TicketBean ticket;
		SqlSession sqlSession = null;
		log.debug("registerAuditEvent() - Construyendo objeto persistente");
		try {
			// Construimos objeto persistente
			ticket = new TicketBean();

			ticket.setUidActividad(datosSesion.getUidActividad());
			
			// uid documento
			String uidTicket = UUID.randomUUID().toString();
			ticket.setUidTicket(uidTicket);
			// id documento
			Long idTicket = contadoresService.obtenerValorContador(datosSesion, AUDITABLE_EVENT_ID_COUNTER);
			ticket.setIdTicket(idTicket);
			// serie documento
			String serieTicket = auditEvent.getCodAlmacen() + "/" + request.getTicket().getTicketHeader().getPosId();
			ticket.setSerieTicket(serieTicket);
			// cod documento
			String codigoTicket = auditEvent.getCodAlmacen() + "/" + request.getTicket().getTicketHeader().getPosId() + "/" + String.format("%08d", idTicket);
			ticket.setCodTicket(codigoTicket);
			// firma documento (no
			ticket.setFirma("*");
			// tipo documento
			ticket.setIdTipoDocumento(661166L);

			ticket.setCodAlmacen(auditEvent.getCodAlmacen());
			ticket.setCodCaja(request.getTicket().getTicketHeader().getPosId());
			ticket.setFecha(auditEvent.getMoment());
			
			// localizador
			// formato: yyMMdd[codalmacen][idticket con padding][3 ultimos caracteres del
			// uid ticket]
			SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
			String locator = format.format(auditEvent.getMoment()) + auditEvent.getCodAlmacen() + String.format("%06d", idTicket) + StringUtils.right(ticket.getUidTicket(), 3);
			ticket.setLocatorId(locator);
			ticket.setProcesado("N");
			// xml
			JAXBContext jaxbContext = JAXBContext.newInstance(TicketAuditEvent.class, HashMap.class);
			Marshaller marshaller = jaxbContext.createMarshaller();

			// Configurar el marshaller para que produzca un XML con formato
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Convertir el objeto en XML
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			marshaller.marshal(auditEvent, outputStream);

			// Obtener el arreglo de bytes resultante
			xmlBytes = outputStream.toByteArray();

			ticket.setTicket(xmlBytes);
			log.debug("registerAuditEvent() - Saving ticket");
			sqlSession = datosSesion.getSqlSessionFactory().openSession();
			ticketsService.insertaTicket(sqlSession, ticket);
			sqlSession.commit();
		}
		catch (Exception e) {
			log.error("registerAuditEvent() - Error saving document: " + e.getMessage());
		}
		finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}

	}
}
