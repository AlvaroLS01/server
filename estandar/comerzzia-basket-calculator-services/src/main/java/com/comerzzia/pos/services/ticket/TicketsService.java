/**
 * ComerZZia 3.0
 *
 * Copyright (c) 2008-2015 Comerzzia, S.L.  All Rights Reserved.
 *
 * THIS WORK IS  SUBJECT  TO  SPAIN  AND  INTERNATIONAL  COPYRIGHT  LAWS  AND
 * TREATIES.   NO  PART  OF  THIS  WORK MAY BE  USED,  PRACTICED,  PERFORMED
 * COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED,  ABRIDGED, CONDENSED,
 * EXPANDED,  COLLECTED,  COMPILED,  LINKED,  RECAST, TRANSFORMED OR ADAPTED
 * WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION
 * OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO
 * CRIMINAL AND CIVIL LIABILITY.
 *
 * CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL
 * RESTRICTIONS.
 */

package com.comerzzia.pos.services.ticket;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.comerzzia.core.basketcalculator.util.fechas.Fecha;
import com.comerzzia.firma.pt.HashSaftPt;
import com.comerzzia.pos.persistence.cajas.movimientos.CashJournalLine;
import com.comerzzia.pos.persistence.core.config.configcontadores.ConfigContadorBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ConfigContadorParametroBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoExample;
import com.comerzzia.pos.persistence.core.contadores.ContadorBean;
import com.comerzzia.pos.persistence.core.documentos.tipos.TipoDocumentoBean;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;
import com.comerzzia.pos.persistence.tickets.POSTicketMapper;
import com.comerzzia.pos.persistence.tickets.TicketBean;
import com.comerzzia.pos.persistence.tickets.TicketExample;
import com.comerzzia.pos.persistence.tickets.TicketKey;
import com.comerzzia.pos.persistence.tickets.TicketLocatorKey;
import com.comerzzia.pos.services.cajas.CajasService;
import com.comerzzia.pos.services.cajas.CajasServiceException;
import com.comerzzia.pos.services.core.config.configContadores.ServicioConfigContadores;
import com.comerzzia.pos.services.core.config.configContadores.rangos.ServicioConfigContadoresRangos;
import com.comerzzia.pos.services.core.contadores.ContadorNotFoundException;
import com.comerzzia.pos.services.core.contadores.ServicioContadores;
import com.comerzzia.pos.services.core.documentos.Documentos;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.core.variables.VariablesServices;
import com.comerzzia.pos.services.ticket.cabecera.FirmaTicket;
import com.comerzzia.pos.services.ticket.lineas.LineaTicket;
import com.comerzzia.pos.services.ticket.lineas.LineaTicketAbstract;
import com.comerzzia.pos.services.ticket.pagos.IPagoTicket;
import com.comerzzia.pos.services.ticket.pagos.PagoTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.config.SpringContext;
import com.comerzzia.pos.util.format.FormatUtil;
import com.comerzzia.pos.util.xml.MarshallUtil;

@Service
@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class TicketsService {

    protected static final Logger log = Logger.getLogger(TicketsService.class);
    
    @Autowired
    protected VariablesServices variablesServices;
    @Autowired
    protected CajasService cajasService;
    
    @Autowired
    Sesion sesion;
    
    @Autowired
    protected Documentos documentos;
    @Autowired
    protected ServicioContadores servicioContadores;
    @Autowired
    protected ServicioConfigContadores servicioConfigContadores;
    @Autowired
    protected ServicioConfigContadoresRangos servicioConfigContadoresRangos;
    
    @Autowired
    protected POSTicketMapper ticketMapper;
        
    public synchronized void setContadorIdTicket(Ticket ticket) throws TicketsServiceException {
    	try {
            log.debug("setContadorIdTicket() - Obteniendo contador para identificador...");
            Map<String, String> parametrosContador = new HashMap<>();
            Map<String, String> condicionesVigencias = new HashMap<>();
            
            parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_CODEMP,ticket.getEmpresa().getCodEmpresa());
            parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_CODALM,ticket.getTienda().getAlmacenBean().getCodAlmacen());
            parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_CODSERIE,ticket.getTienda().getAlmacenBean().getCodAlmacen());
            parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_CODCAJA,ticket.getCodCaja());
            parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_CODDOC,ticket.getCabecera().getCodTipoDocumento());
            parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_PERIODO,((new Fecha()).getAño().toString()));
           
            condicionesVigencias.put(ConfigContadorRangoBean.VIGENCIA_CODCAJA,ticket.getCabecera().getCodCaja());
            condicionesVigencias.put(ConfigContadorRangoBean.VIGENCIA_CODALM,ticket.getCabecera().getTienda().getCodAlmacen());
            condicionesVigencias.put(ConfigContadorRangoBean.VIGENCIA_CODEMP,ticket.getCabecera().getEmpresa().getCodEmpresa());
            
            TipoDocumentoBean documentoActivo = sesion.getAplicacion().getDocumentos().getDocumento(ticket.getCabecera().getCodTipoDocumento());
            ConfigContadorBean confContador = servicioConfigContadores.consultar(documentoActivo.getIdContador());
            if(!confContador.isRangosCargados()){
            	ConfigContadorRangoExample example = new ConfigContadorRangoExample();
    			example.or().andIdContadorEqualTo(confContador.getIdContador());
    			example.setOrderByClause(ConfigContadorRangoExample.ORDER_BY_RANGO_INICIO + ", " + ConfigContadorRangoExample.ORDER_BY_RANGO_FIN + ", "
    					+ ConfigContadorRangoExample.ORDER_BY_RANGO_FECHA_INICIO + ", " + ConfigContadorRangoExample.ORDER_BY_RANGO_FECHA_FIN);
        		List<ConfigContadorRangoBean> rangos = servicioConfigContadoresRangos.consultar(example);
        		
	    		confContador.setRangos(rangos);
	    		confContador.setRangosCargados(true);
	    	}
            ContadorBean ticketContador = servicioContadores.consultarContadorActivo(confContador, parametrosContador,condicionesVigencias,ticket.getUidActividad(), true);
            if(ticketContador==null||ticketContador.getError()!=null){
            	throw new ContadorNotFoundException("No se ha encontrado un contador disponible");
            }
            ticket.setIdTicket(ticketContador.getValor());
            
            String codTicket = servicioContadores.obtenerValorTotalConSeparador(ticketContador.getConfigContador().getValorDivisor3Formateado(), ticketContador.getValorFormateado());
            
            ticket.getCabecera().setSerieTicket(ticketContador.getConfigContador().getValorDivisor3Formateado());
            ticket.getCabecera().setCodTicket(codTicket);
        	ticket.getCabecera().setUidTicket(UUID.randomUUID().toString());
    	}
        catch (Exception e) {
            String msg = "Se ha producido un error procesando ticket con uid " + ticket.getUidTicket() + " : " + e.getMessage();
            log.error("registrarTicket() - " + msg, e);
            throw new TicketsServiceException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized void registrarTicket(Ticket ticket, TipoDocumentoBean tipoDocumento, boolean procesarTicket) throws TicketsServiceException {
        log.debug("registrarTicket() - Procesando ticket ... ");
        byte[] xmlTicket = null;
        TicketBean ticketBean;
		try {
            // Establecemos fecha del ticket
            ticket.setFecha(new Date());
            ticket.setFechaContable(sesion.getSesionCaja().getCajaAbierta().getAccountingDate());
            
//            ComerzziaApp comerzziaApp = ComerzziaApp.get();           
//            ticket.setSoftwareVersion(comerzziaApp.getVersionRevision());
//            
//            if (comerzziaApp.getLocalRepositoryVersion().equals(comerzziaApp.getRemoteRepositoryVersion())) {
//            	ticket.setLocalCopyVersion(comerzziaApp.getLocalRepositoryVersion());
//            } else {
//                ticket.setLocalCopyVersion(comerzziaApp.getLocalRepositoryVersion() + "|" + comerzziaApp.getRemoteRepositoryVersion());
//            }
			
            reiniciarContadoresLineas(ticket);
            
            IPagoTicket cambio = ticket.getTotales().getCambio();
            List<PagoTicket> pagos = ((TicketVenta)ticket).getPagos();
            
            //Borramos pagos a cero
            ListIterator<PagoTicket> listIterator = pagos.listIterator();
            while (listIterator.hasNext()) {
            	PagoTicket pago = listIterator.next();
            	if(BigDecimalUtil.isIgualACero(pago.getImporte())){
            		listIterator.remove();
            	}
            }
            //Añadimos un pago a cero si el importe total es cero y no hay pagos
            if(BigDecimalUtil.isIgualACero(ticket.getCabecera().getTotales().getTotal()) && pagos.size() == 0) {
				PagoTicket pagoVacio = createPago();
				pagoVacio.setMedioPago(sesion.getSesionCaja().getMedioPagoDefecto());
				pagoVacio.setImporte(BigDecimal.ZERO);
				
				Integer paymentId = generateNewPaymentId(ticket);
        		if(paymentId!=null && paymentId>0) {
        			pagoVacio.setPaymentId(paymentId);
        		}
				
				((TicketVenta) ticket).addPago(pagoVacio);
            }
            
            // Generamos movimientos de caja
            registrarMovimientosCaja((TicketVentaAbono) ticket, cambio, pagos);
            
            //Añadimos el cambio como un pago
            if(!BigDecimalUtil.isIgualACero(ticket.getCabecera().getTotales().getCambio().getImporte())){
	            IPagoTicket pagoCodMedPagoCambio = ((TicketVenta)ticket).getPago(cambio.getMedioPago().getCodMedioPago());
	        	MedioPagoBean medioPagoCambio = ticket.getCabecera().getTotales().getCambio().getMedioPago();
	        	
	        	if(pagoCodMedPagoCambio == null){
	        		pagoCodMedPagoCambio = createPago();
	        		pagoCodMedPagoCambio.setEliminable(false);
	        		pagoCodMedPagoCambio.setMedioPago(medioPagoCambio);
	        		
	        		Integer paymentId = generateNewPaymentId(ticket);
	        		if(paymentId!=null && paymentId>0) {
	        			pagoCodMedPagoCambio.setPaymentId(paymentId);
	        		}
	        		
	        		((TicketVenta)ticket).addPago(pagoCodMedPagoCambio);
	        	}
	        	
	        	pagoCodMedPagoCambio.setImporte(pagoCodMedPagoCambio.getImporte().subtract(((TicketVenta)ticket).getTotales().getCambio().getImporte()));
	        	
	        	/*if(BigDecimalUtil.isMayorOrIgualACero(ticket.getCabecera().getTotales().getTotal())){
	        		pagoCodMedPagoCambio.setImporte(pagoCodMedPagoCambio.getImporte().subtract(((TicketVenta)ticket).getTotales().getCambio().getImporte()));
	        	}else{
	        		pagoCodMedPagoCambio.setImporte(pagoCodMedPagoCambio.getImporte().add(((TicketVenta)ticket).getTotales().getCambio().getImporte()));
	        	}*/
            }
            
            //Borramos pagos a cero
            listIterator = pagos.listIterator();
            while (listIterator.hasNext()) {
            	PagoTicket pago = listIterator.next();
            	if(BigDecimalUtil.isIgualACero(pago.getImporte())){
            		listIterator.remove();
            	}
            }
            //Añadimos un pago a cero si el importe total es cero y no hay pagos
            if(BigDecimalUtil.isIgualACero(ticket.getCabecera().getTotales().getTotal()) && pagos.size() == 0) {
				PagoTicket pagoVacio = createPago();
				pagoVacio.setMedioPago(sesion.getSesionCaja().getMedioPagoDefecto());
				pagoVacio.setImporte(BigDecimal.ZERO);
				((TicketVenta) ticket).addPago(pagoVacio);
            }
            
            String firma = generarFirma(ticket);
                        
            // Construimos objeto persistente
            log.debug("registrarTicket() - Construyendo objeto persistente...");
            ticketBean = new TicketBean();
            ticketBean.setCodAlmacen(ticket.getCabecera().getTienda().getAlmacenBean().getCodAlmacen());
            ticketBean.setCodcaja(ticket.getCodCaja());
            ticketBean.setFecha(ticket.getFecha());
            ticketBean.setIdTicket(ticket.getIdTicket());
            ticketBean.setUidTicket(ticket.getUidTicket());
            ticketBean.setIdTipoDocumento(ticket.getCabecera().getTipoDocumento());
            ticketBean.setCodTicket(ticket.getCabecera().getCodTicket());
            ticketBean.setSerieTicket(ticket.getCabecera().getSerieTicket());
            ticketBean.setFirma(firma);
            ticketBean.setLocatorId(ticket.getCabecera().getLocalizador());
            
        	String hashControl = ticket.getCabecera().getFirma().getHashControl();
        	FirmaTicket firmaTicket = new FirmaTicket();
        	firmaTicket.setHashControl(hashControl);
        	firmaTicket.setFirma(ticketBean.getFirma());
        	ticket.getCabecera().setFirma(firmaTicket);
            
            setFiscalData(ticket);
        	
            log.debug("registrarTicket() - Generando XML del ticket...");
            xmlTicket = MarshallUtil.crearXML(ticket);
            ticketBean.setTicket(xmlTicket);
            
            log.debug("registrarTicket() - TICKET INSERT: " + ticket.getUidTicket());
            log.trace(new String(xmlTicket,"UTF-8") + "\n");

            insertarTicket(ticketBean, false);
                        
            log.debug("registrarTicket() - Ticket salvado correctamente.");
        }
        catch (Exception e) {
            String msg = "Se ha producido un error procesando ticket con uid " + ticket.getUidTicket() + " : " + e.getMessage();
            log.error("registrarTicket() - " + msg, e);
            throw new TicketsServiceException(e);
        }
        
        if(ticketBean != null && xmlTicket != null && procesarTicket){
        	try{
        		log.debug("registrarTicket() - Procesando ticket...");
        		procesarTicket(ticketBean, xmlTicket);
        	}catch(Exception e){
        		log.warn("registrarTicket() - Ha ocurrido un error procesando ticket: " + e.getMessage(), e);
        	}
        }
    }
    	
	protected Integer generateNewPaymentId(Ticket ticket) {
    	Integer paymentId = 1;
		if(!ticket.getPagos().isEmpty()) {
			paymentId = ticket.getPagos().stream()
					.mapToInt(x -> ((PagoTicket) x).getPaymentId())
					.max()
					.orElse(0) + 1;
		}		
		return paymentId;
    }

	@Transactional(rollbackFor = Exception.class)
	protected void registrarMovimientosCaja(TicketVentaAbono ticket, IPagoTicket cambio, List<PagoTicket> pagos) throws CajasServiceException {
		log.debug("registrarMovimientosCaja() - Registrando movimientos de caja");

		Integer idLineaCaja = cajasService.consultarProximaLineaDetalleCaja();

		boolean esVenta = ticket.getCabecera().esVenta();

		for (IPagoTicket pago : pagos) {
			if(!pago.isIntroducidoPorCajero() || !pago.isMovimientoCajaInsertado()) {
				CashJournalLine detalleCaja = new CashJournalLine();
				detalleCaja.setLineId(idLineaCaja);
				detalleCaja.setCashJournalDate(ticket.getFecha());
	
				if (!esVenta) {
					if (BigDecimalUtil.isMayorOrIgualACero(ticket.getCabecera().getTotales().getTotal())) {
						if (pago.getImporte().compareTo(BigDecimal.ZERO) >= 0) {
							detalleCaja.setOutput(BigDecimal.ZERO);
							detalleCaja.setInput(pago.getImporte().abs());
						}
						else {
							detalleCaja.setOutput(pago.getImporte().abs());
							detalleCaja.setInput(BigDecimal.ZERO);
						}
					}
					else {
						if (pago.getImporte().compareTo(BigDecimal.ZERO) < 0) {
							detalleCaja.setOutput(BigDecimal.ZERO);
							detalleCaja.setInput(pago.getImporte().abs());
						}
						else {
							detalleCaja.setOutput(pago.getImporte().abs());
							detalleCaja.setInput(BigDecimal.ZERO);
						}
					}
				}
				else {
					if (pago.getImporte().compareTo(BigDecimal.ZERO) < 0) {
						detalleCaja.setOutput(BigDecimal.ZERO);
						detalleCaja.setInput(pago.getImporte().abs());
					}
					else {
						detalleCaja.setOutput(pago.getImporte().abs());
						detalleCaja.setInput(BigDecimal.ZERO);
					}
				}
	
				detalleCaja.setConcept(ticket.getCabecera().getDesTipoDocumento() + ": " + ticket.getCabecera().getCodTicket());
				detalleCaja.setSalesDocCode(ticket.getCabecera().getCodTicket());
				detalleCaja.setPaymentMethodCode(pago.getCodMedioPago());
				detalleCaja.setSalesDocUid(ticket.getUidTicket());
				detalleCaja.setDocTypeId(ticket.getCabecera().getTipoDocumento());
				cajasService.crearMovimiento(detalleCaja);
				idLineaCaja++;
			}
		}

		if (!BigDecimalUtil.isIgualACero(ticket.getCabecera().getTotales().getCambio().getImporte())) {
			log.debug("registrarMovimientosCaja() - Registrando movimiento de cambio");
			CashJournalLine detalleCaja = new CashJournalLine();

			detalleCaja.setLineId(idLineaCaja);

			detalleCaja.setCashJournalDate(ticket.getFecha());
			// Cargo o abono al revés de lo normal
			if (ticket.getCabecera().esVenta()) {
				detalleCaja.setOutput(cambio.getImporte().abs().negate());
				detalleCaja.setInput(BigDecimal.ZERO);
			}
			else {
				detalleCaja.setOutput(BigDecimal.ZERO);
				detalleCaja.setInput(cambio.getImporte().abs().negate());
			}

			detalleCaja.setConcept(ticket.getCabecera().getDesTipoDocumento() + ": " + ticket.getCabecera().getCodTicket() + " (cambio)");
			detalleCaja.setSalesDocCode(ticket.getCabecera().getCodTicket());
			detalleCaja.setPaymentMethodCode(cambio.getCodMedioPago());
			detalleCaja.setSalesDocUid(ticket.getUidTicket());
			detalleCaja.setDocTypeId(ticket.getCabecera().getTipoDocumento());
			cajasService.crearMovimiento(detalleCaja);
		}
	}

	public PagoTicket createPago() {
		return SpringContext.getBean(PagoTicket.class);
	}
    
	protected void reiniciarContadoresLineas(Ticket ticket){
        
        int contador = 1;
        
        for(LineaTicketAbstract linea: (List<LineaTicketAbstract>)ticket.getLineas()){
            linea.setIdLinea(contador);
            contador++;
        }
    }
    
    /**
     * 
     * @param ticket 
     * @param ticket
     * @throws com.comerzzia.pos.services.ticket.TicketsServiceException
     */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public synchronized void procesarTicket(final TicketBean ticket, final byte[] xmlTicket) throws Exception {
    	Thread hiloEnvio = new Thread(new Runnable(){
			
			@Override
			public void run() {
				try {
					boolean enviado = enviarTicket(ticket, xmlTicket);
			        
			        if(enviado) {
			        	marcarTicketProcesado(ticket);
			        }
				}
				catch(Exception e) {
					log.error("procesarTicket() - Ha habido un error al enviar el ticket para procesar: " + e.getMessage(), e);
				}
			}

			protected void marcarTicketProcesado(TicketBean ticket) {
				Long idTipoTienda = sesion.getAplicacion().getTienda().getTiendaBean().getIdTipoTienda();
				if(!idTipoTienda.equals(1L)) {
					try {
						TicketKey key = new TicketKey();
						key.setUidActividad(ticket.getUidActividad());
						key.setUidTicket(ticket.getUidTicket());
						TicketBean ticketBBDD = ticketMapper.selectByPrimaryKey(key);
						
						ticketBBDD.setProcesado(true);
						ticketMapper.updateProcesado(ticketBBDD);
					}
					catch (Exception e) {
						log.error("marcarTicketProcesado() - Ha habido un error al marcar el ticket como procesado: " + e.getMessage(), e);
					}
				}
			}

			protected boolean enviarTicket(final TicketBean ticket, final byte[] xmlTicket) throws UnsupportedEncodingException {
	            boolean result = false;
//	            
//	            TicketRequestRest request = new TicketRequestRest();
//	            List<String> tickets = new ArrayList<String>();
//	            request.setApiKey(variablesServices.getVariableAsString(VariablesServices.WEBSERVICES_APIKEY));
//	            request.setUidActividad(sesion.getUidActividad());
//	            request.setUidTicket(ticket.getUidTicket());
//	            request.setCodAlm(ticket.getCodAlmacen());
//	            request.setCodCaja(ticket.getCodcaja());
//	            request.setCodTicket(ticket.getCodTicket());
//	            request.setFirma(ticket.getFirma());
//	            request.setIdTicket(ticket.getIdTicket());
//	            request.setIdTipoDocumento(ticket.getIdTipoDocumento());
//	            request.setSerieTicket(ticket.getSerieTicket());
//	            tickets.add(new String(xmlTicket, "UTF-8"));
//	            request.setLocatorId(ticket.getLocatorId());
//	            request.setTicketXML(tickets);
//	            ResponsePutRest resultRest = TicketsRest.añadirTicketYProcesar(request);
//	            
//	            result = resultRest.getNumeroInserts()>0;
	            return result;
            }
		});
    	
    	hiloEnvio.start();
    }

    /**
     * Registra en base de datos el ticket indicado. Será registrado con
     * PROCESADO = N a no ser que el ticket de abono se haya mandado a procesar, en cuyo caso se grabará
     * con valor S.
     *
     * @param sqlSession
     * @param ticket :: Ticket que se va a crear
     * @param ticketProcesado
     * @throws TicketsServiceException
     */
    public synchronized void insertarTicket(TicketBean ticket, boolean ticketProcesado) throws TicketsServiceException {
        try {
            log.debug("insertarTicket() - Salvando ticket en base de datos...");
            ticket.setUidActividad(sesion.getUidActividad());
            ticket.setProcesado(ticketProcesado);
            ticketMapper.insert(ticket);
        }
        catch (Exception e) {
            String msg = "Se ha producido un error insertando ticket con uid " + ticket.getUidTicket() + " : " + e.getMessage();
            log.error("insertarTicket() - " + msg, e);
            throw new TicketsServiceException(e);
        }
    }

    /**
     * Consulta una lista de ticket que cumpla con los criterios de búsqueda pasados por
     * parámetro
     *
     * @param caja
     * @param idTicket
     * @param fecha
     * @param idDoc
     * @param idTiposDocValidos 
     * @return
     * @throws TicketsServiceException
     */
    public List<TicketBean> consultarTickets(String caja, Long idTicket, Date fecha, Long idDoc, List<Long> idTiposDocValidos) throws TicketsServiceException {
        try {
            log.debug("consultarTickets() - Consultando ticket en base de datos...");
            TicketExample example = new TicketExample();
            TicketExample.Criteria criteria = example.createCriteria();
            criteria.andUidActividadEqualTo(sesion.getUidActividad())
                    .andCodAlmacenEqualTo(sesion.getAplicacion().getCodAlmacen());
            if(idDoc !=null){
            	criteria.andIdTipoDocumentoEqualTo(idDoc);
            }
            if (caja != null && !caja.isEmpty()) {
                criteria.andCodcajaEqualTo(caja);
            }
            if (idTicket != null) {
                criteria.andIdTicketEqualTo(idTicket);
            }
            if (fecha != null) {
                //Se crea la fecha del día siguiente para crear el intervalo válido para el día del filtro
                Fecha diaSuperior = Fecha.getFecha(fecha);
                diaSuperior.sumaDias(1);
                criteria.andFechaBetween(fecha, diaSuperior.getDate());
            }
            if(idTiposDocValidos != null && !idTiposDocValidos.isEmpty()){
            	criteria.andIdTipoDocumentoIn(idTiposDocValidos);
            }
            example.or(criteria);
            return ticketMapper.selectByExample(example);
        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando tickets en base de datos con parámetros indicados: Fecha: " + fecha + " IdTicket: " + idTicket + " Caja: " + caja + " - " + e.getMessage();
            log.error("consultarTickets() - " + msg, e);
            throw new TicketsServiceException(e);
        }        
    }
    
    public List<TicketBean> consultarTicketLocalizador(String localizador, List<Long> idTiposDocValidos) throws TicketsServiceException{
        try {
            log.debug("consultarTicketLocalizador() - Consultando ticket en base de datos...");
            
            List<TicketBean> tickets = new ArrayList<TicketBean>();
            
            TicketLocatorKey ticketLocatorKey = new TicketLocatorKey();
            ticketLocatorKey.setUidActividad(sesion.getUidActividad());
            ticketLocatorKey.setLocatorId(localizador);
            
            for(Long idTipoDocValido : idTiposDocValidos) {            	
            	ticketLocatorKey.setIdTipoDocumento(idTipoDocValido);
            	TicketBean ticketsEncontrado = ticketMapper.selectByLocatorKey(ticketLocatorKey);
            	if(ticketsEncontrado != null) {
            		tickets.add(ticketsEncontrado);
            	}
            }
            
            return tickets;
        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando tickets en base de datos con localizador " + localizador + ": " + e.getMessage();
            log.error("consultarTicketLocalizador() - " + msg, e);
            throw new TicketsServiceException(e);
        }
    }

    public TicketBean consultarTicket(String uidTicket, String uidActividad) throws TicketsServiceException {
    	TicketBean res = null;
    	try {
    		log.debug("consultarTickets() - Consultando ticket en base de datos...");
    		TicketExample example = new TicketExample();
    		TicketExample.Criteria criteria = example.createCriteria();
    		criteria.andUidTicketEqualTo(uidTicket).andUidActividadEqualTo(uidActividad);            
    		List<TicketBean> resultados = ticketMapper.selectByExampleWithBLOBs(example);
    		if (resultados != null && !resultados.isEmpty()) {
    			res = resultados.get(0);
    		}
    		return res;
    		
    	}
    	catch (Exception e) {
    		String msg = "Se ha producido un error consultando tickets en base de datos con parámetros indicados: uidTicket: " + uidTicket;
    		log.error("consultarTickets() - " + msg, e);
    		throw new TicketsServiceException(e);
    	}
    }
    
    public TicketBean consultarTicket(TicketExample example) throws TicketsServiceException {
        TicketBean res = null;
        try {
            log.debug("consultarTickets() - Consultando ticket en base de datos...");
            List<TicketBean> resultados = ticketMapper.selectByExampleWithBLOBs(example);
            if (resultados != null && !resultados.isEmpty()) {
                res = resultados.get(0);
            }
            return res;

        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando tickets en base de datos con parámetros indicados";
            log.error("consultarTickets() - " + msg, e);
            throw new TicketsServiceException(e);
        }
    }
    
    public TicketBean consultarTicketAbono(String codTienda, String codCaja, String codTicket, Long idTipoDoc) throws TicketsServiceException{
        TicketBean res = null;
        try {
            log.debug("consultarTicketAbono() - Consultando ticket en base de datos...");
            TicketExample example = new TicketExample();
            TicketExample.Criteria criteria = example.createCriteria();
            criteria.andUidActividadEqualTo(sesion.getUidActividad()).andCodAlmacenEqualTo(codTienda).andIdTicketEqualTo(Long.parseLong(codTicket)).andCodcajaEqualTo(codCaja).andIdTipoDocumentoEqualTo(idTipoDoc);
            example.setOrderByClause("FECHA DESC");
            List<TicketBean> resultados = ticketMapper.selectByExampleWithBLOBs(example);
            if (resultados != null && !resultados.isEmpty()) {
                res = resultados.get(0);
            }
            return res;
        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando tickets en base de datos con parámetros indicados: idTienda: " + codTienda + ", idTicket: "+ codTicket;
            log.error("consultarTicketAbono() - " + msg, e);
            throw new TicketsServiceException(e);
        }
    }

    protected String generarFirma(Ticket ticket){
    	log.trace("generarFirma()");
    	
    	String firma = "";
    	
    	String fechaFormateada = FormatUtil.getInstance().formateaFechaHoraTicket(ticket.getFecha());
    	
    	DecimalFormatSymbols simbolos = new DecimalFormatSymbols(); //Modificamos el separador para Portugal
    	simbolos.setDecimalSeparator('.');
    	DecimalFormat formateaSeparadorDecimal = new DecimalFormat("0.00",simbolos);
    	String totalTicket = formateaSeparadorDecimal.format(ticket.getTotales().getTotal().abs()); //siempre en valor absoluto
    	
    	SimpleDateFormat formateaFechaSimple = new SimpleDateFormat("yyyy-MM-dd");
    	String fechaSimple = formateaFechaSimple.format(ticket.getFecha());

    	try {
    		TicketBean ultimoTicket = consultarUltimoTicketFirma(ticket.getUidActividad(), ticket.getIdTicket(), ticket.getCabecera().getTipoDocumento(), ticket.getCabecera().getSerieTicket());
    		String cadena, sFirmaAnterior = "";
    		
    		if(ultimoTicket!=null){
    			sFirmaAnterior = ultimoTicket.getFirma();
    		}
    		
    		cadena = fechaSimple+";"+fechaFormateada+";"+ticket.getCabecera().getCodTicket()+";"+totalTicket+";"+sFirmaAnterior;
    		
    		firma = HashSaftPt.firma(cadena);
    	} catch (TicketsServiceException e) {
			log.error("generarFirma() - Excepción capturada" + e.getMessage(), e);
		} 
    	
    	return firma;
    }
    
    protected void setFiscalData(ITicket ticket) {

//		try {
//			FiscalDataService fiscalService = (FiscalDataService) ContextHolder.getBean("FiscalDataService"+sesion.getAplicacion().getTienda().getCliente().getCodpais());
//			FiscalData fiscalData = fiscalService.getFiscalData(ticket);
//
//			ticket.getCabecera().setFiscalData(fiscalData);
//		} catch (ClassNotFoundException | NoSuchBeanDefinitionException e) {
//			log.debug("setFiscalData() - No hay configurado un servicio fiscal para el país '"+sesion.getAplicacion().getTienda().getCliente().getCodpais()+"'");
//		}		
		
	}

    public TicketBean consultarUltimoTicketFirma(String uidActividad, Long idTicket, Long idTipoDoc, String series) throws TicketsServiceException {
        TicketBean res = null;
        try {
            log.debug("consultarUltimoTicket() - Consultando último ticket en base de datos...");
            TicketExample example = new TicketExample();
            TicketExample.Criteria criteria = example.createCriteria();
            criteria.andUidActividadEqualTo(uidActividad);
            criteria.andIdTicketEqualTo(idTicket-1);
            criteria.andIdTipoDocumentoEqualTo(idTipoDoc);
            criteria.andSerieEqualTo(series);
            example.setOrderByClause(TicketExample.ORDER_BY_ID_TICKET_DESC);
            List<TicketBean> resultados = ticketMapper.selectByExample(example);
            if (resultados != null && !resultados.isEmpty()) {
                res = resultados.get(0);
            }
            
            return res;

        }
        catch (Exception e) {
            log.error("consultarUltimoTicket() - Se ha producido un error consultando el ultimo ticket.", e);
            throw new TicketsServiceException(e);
        }
    }
    
    public TicketBean getMostRecentTicket(String activityUid, Long documentTypeId, String cashCode) throws TicketsServiceException {
    	TicketBean res = null;
    	try {
    		log.debug("getMostRecentTicket() - Consultando último ticket por fecha");
    		TicketExample example = new TicketExample();
    		example.setOrderByClause(TicketExample.ORDER_BY_FECHA_DESC);
            TicketExample.Criteria criteria = example.createCriteria();
            criteria.andIdTipoDocumentoEqualTo(documentTypeId);
            criteria.andUidActividadEqualTo(activityUid);
            criteria.andCodcajaEqualTo(cashCode);
            RowBounds rowBounds = new RowBounds(0,1);
            List<TicketBean> results = ticketMapper.selectByExampleWithBLOBsWithRowbounds(example, rowBounds);
            if (results != null && !results.isEmpty()) {
                res = results.get(0);
            }
            return res;	
    	}catch(Exception e) {
    		log.error("getMostRecentTicket() - Se ha producido un error consultando el último ticket por fecha");
    		throw new TicketsServiceException(e);
    	}
    }
    
    
    
    public void saveEmptyTicket(ITicket ticketPrincipal, TipoDocumentoBean documentoActivo, TipoDocumentoBean documentoOrigen) {
    	try {
    		boolean generarDosDocumentos = false;
    		boolean esDevolucion = !ticketPrincipal.getCabecera().esVenta();
    		
			if(esDevolucion && documentoOrigen != null) {
    			TipoDocumentoBean documentoAbono = documentos.getDocumentoAbono(documentoActivo.getCodtipodocumento());
    			TipoDocumentoBean documentoVenta = documentos.getDocumento(documentoOrigen.getCodtipodocumento());
    			
    			if(!documentoAbono.isSignoLibre() || !documentoVenta.isSignoLibre()) {
    				generarDosDocumentos = true;
    			}
    		}
    		
	    	if(!generarDosDocumentos && documentoActivo.isSignoLibre()) {
    			TicketVentaAbono ticketVacio = generateEmptyTicket(ticketPrincipal, documentoActivo, null);
    			registrarTicket(ticketVacio, documentoActivo, false);
	    	}
	    	else {
    			TipoDocumentoBean documentoAbono = documentos.getDocumentoAbono(documentoActivo.getCodtipodocumento());
    			if(esDevolucion) {
    				documentoAbono = documentoOrigen;
    			}

    			String signoDoc1 = documentoActivo.getConcepto().getSigno();
    			String signoDoc2 = documentoAbono.getConcepto().getSigno();
    			
    			Boolean doc1LineasPositivas = null;    			
    			Boolean doc2LineasPositivas = null;    			
    			if(signoDoc1.equals(signoDoc2)) {
    				if(signoDoc1.equals("+")) {
    					doc1LineasPositivas = true;
    					doc2LineasPositivas = true;
    				}
    				if(signoDoc1.equals("-")) {
    					doc1LineasPositivas = false;
    					doc2LineasPositivas = false;
    				}
    			}
    			else {
    				if(StringUtils.isBlank(signoDoc1)) {
    					doc2LineasPositivas = signoDoc2.equals("+");
    					doc1LineasPositivas = !doc2LineasPositivas;
    				}
    				else if(StringUtils.isBlank(signoDoc2)) {
    					doc1LineasPositivas = signoDoc1.equals("+");
    					doc2LineasPositivas = !doc1LineasPositivas;
    				}
    				else {
    					doc1LineasPositivas = signoDoc1.equals("+");
    					doc2LineasPositivas = signoDoc2.equals("+");
    				}
    			}
    			
    			TicketVentaAbono ticketVenta = generateEmptyTicket(ticketPrincipal, documentoActivo, doc1LineasPositivas);
    			registrarTicket(ticketVenta, documentoActivo, false);
    			
    			TicketVentaAbono ticketAbono = generateEmptyTicket(ticketPrincipal, documentoAbono, doc2LineasPositivas);
    			ticketAbono.getCabecera().setTipoDocumento(documentoAbono.getIdTipoDocumento());
    			setContadorIdTicket((Ticket) ticketAbono);
    			registrarTicket(ticketAbono, documentoAbono, false);
    			ticketPrincipal.getCabecera().setTipoDocumento(documentoActivo.getIdTipoDocumento());
	    	}
    	}
    	catch (Exception e) {
    		log.error("saveEmptyTicket() - Ha ocurrido un error al salvar un ticket vacío: " + e.getMessage(), e);
    	}
    }

	public TicketVentaAbono generateEmptyTicket(ITicket ticketPrincipal, TipoDocumentoBean documentoActivo, Boolean lineasPositivas) {
		// Inicializamos un ticket sin lineas
		TicketVentaAbono ticketVacio = SpringContext.getBean(TicketVentaAbono.class);
		ticketVacio.getCabecera().inicializarCabecera(ticketVacio);
		ticketVacio.inicializarTotales();
		ticketVacio.setCliente(ticketPrincipal.getCliente());
		ticketVacio.setCajero(ticketPrincipal.getCabecera().getCajero());
		IPagoTicket cambio = createPago();
		cambio.setMedioPago(sesion.getSesionCaja().getMedioPagoDefecto());
		ticketVacio.getCabecera().getTotales().setCambio(cambio);
		ticketVacio.getTotales().recalcular();
		ticketVacio.getCabecera().setDocumento(documentoActivo);
		ticketVacio.setEsDevolucion(ticketPrincipal.isEsDevolucion());
		// Añadimos los datos del contador obtenido antes
		ticketVacio.setIdTicket(ticketPrincipal.getIdTicket());
		ticketVacio.getCabecera().setSerieTicket(ticketPrincipal.getCabecera().getSerieTicket());
		ticketVacio.getCabecera().setCodTicket(ticketPrincipal.getCabecera().getCodTicket());
		ticketVacio.getCabecera().setDatosDocOrigen(ticketPrincipal.getCabecera().getDatosDocOrigen());
		ticketVacio.getCabecera().setUidTicket(ticketPrincipal.getCabecera().getUidTicket());

		List<LineaTicket> lineasOriginales = (List<LineaTicket>) ticketPrincipal.getLineas();
		LineaTicket lineaTicketVacio = lineasOriginales.get(0);
		lineaTicketVacio.resetPromociones();
		lineaTicketVacio.recalcularImporteFinal();
		
		if(lineasPositivas == null) {
			ticketVacio.addLinea(lineaTicketVacio.clone());
			LineaTicket lineaNegativa = lineaTicketVacio.clone();
			lineaNegativa.setCantidad(lineaNegativa.getCantidad().negate());
			lineaNegativa.recalcularImporteFinal();
			ticketVacio.addLinea(lineaNegativa);
		}
		else {
			LineaTicket lineaNueva = lineaTicketVacio.clone();
			BigDecimal cantidad = lineaNueva.getCantidad();
			if(lineasPositivas) {
				cantidad = cantidad.abs();
			}
			else {
				cantidad = cantidad.abs().negate();
			}
			lineaNueva.setCantidad(cantidad);
			lineaNueva.recalcularImporteFinal();
			ticketVacio.addLinea(lineaNueva);
		}
		
		ticketVacio.getCabecera().getTotales().recalcular();

		PagoTicket pagoVacio = createPago();
		pagoVacio.setMedioPago(sesion.getSesionCaja().getMedioPagoDefecto());
		pagoVacio.setImporte(ticketVacio.getTotales().getTotalAPagar());
		ticketVacio.addPago(pagoVacio);
		return ticketVacio;
	}
    
}
