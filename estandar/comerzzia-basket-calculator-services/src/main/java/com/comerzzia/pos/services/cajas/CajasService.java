/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND
 * INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED,
 * DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST,
 * TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK
 * WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE
 * AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */

package com.comerzzia.pos.services.cajas;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.comerzzia.core.basketcalculator.util.fechas.Fecha;
import com.comerzzia.omnichannel.model.documents.sales.cashjournal.CashJournalDocument;
import com.comerzzia.pos.persistence.cajas.CashJournalExample;
import com.comerzzia.pos.persistence.cajas.CashJournalHdr;
import com.comerzzia.pos.persistence.cajas.CashJournalMapper;
import com.comerzzia.pos.persistence.cajas.conceptos.CashJournalConcept;
import com.comerzzia.pos.persistence.cajas.movimientos.CashJournalLine;
import com.comerzzia.pos.persistence.cajas.movimientos.CashJournalLineExample;
import com.comerzzia.pos.persistence.cajas.movimientos.CashJournalLineKey;
import com.comerzzia.pos.persistence.cajas.movimientos.CashJournalLineMapper;
import com.comerzzia.pos.persistence.cajas.movimientos.CashJournalSummaryDTO;
import com.comerzzia.pos.persistence.cajas.recuentos.CashJournalCountLine;
import com.comerzzia.pos.persistence.cajas.recuentos.CashJournalCountLineExample;
import com.comerzzia.pos.persistence.cajas.recuentos.CashJournalCountLineMapper;
import com.comerzzia.pos.persistence.core.contadores.ContadorBean;
import com.comerzzia.pos.persistence.core.documentos.tipos.TipoDocumentoBean;
import com.comerzzia.pos.persistence.tickets.TicketBean;
import com.comerzzia.pos.services.cajas.conceptos.CajaConceptosServices;
import com.comerzzia.pos.services.core.contadores.ServicioContadores;
import com.comerzzia.pos.services.core.documentos.Documentos;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.core.tiendas.Tienda;
import com.comerzzia.pos.services.core.variables.VariablesServices;
import com.comerzzia.pos.services.ticket.TicketsService;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.i18n.I18N;

@Component
public class CajasService {

	public static final String CODIGO_CONCEPTO_APERTURA = "00";

	protected static final Logger log = Logger.getLogger(CajasService.class);

	@Autowired
	Sesion sesion;

	@Autowired
	protected TicketsService ticketsService;

	@Autowired
	protected CajaConceptosServices cajaConceptosServices;
	
	@Autowired
	private VariablesServices variablesServices;

	@Autowired
	protected ServicioContadores servicioContadores;

	@Autowired
	protected CashJournalCountLineMapper cajaLineaRecuentoMapper;

	@Autowired
	protected CashJournalLineMapper cajaMovimientoMapper;

	@Autowired
	protected CashJournalMapper cajaMapper;
	
	@Autowired
	protected ModelMapper modelMapper;

	/**
	 * Obtiene el próximo id línea de recuento de caja para la caja actualmente abierta en sesión
	 *
	 * @return Integer (idLinea)
	 * @throws CajasServiceException
	 */
	public Integer consultarProximaLineaRecuentoCaja() throws CajasServiceException {
		String uidActividad = sesion.getUidActividad();
		String uidDiarioCaja = sesion.getSesionCaja().getUidDiarioCaja();
		try {
			log.debug("consultarProximaLineaRecuentoCaja() - Obteniendo próximo número de línea de recuento de caja para uidDiarioCaja: " + uidDiarioCaja);

			CashJournalCountLineExample exampleCajaLineaRecuento = new CashJournalCountLineExample();
			exampleCajaLineaRecuento.or().andUidActividadEqualTo(uidActividad).andUidDiarioCajaEqualTo(uidDiarioCaja);
			Integer linea = cajaLineaRecuentoMapper.selectMaxLineId(exampleCajaLineaRecuento);
			if (linea == null) {
				linea = 1;
			}
			return linea;
		}
		catch (Exception e) {
			String msg = "Se ha producido un error consultando siguiente línea de recuento de caja para uidDiarioCaja: " + uidDiarioCaja + " : " + e.getMessage();
			log.error("consultarProximaLineaRecuentoCaja() - " + msg, e);
			throw new CajasServiceException(I18N.getTexto("Error al consultar los recuentos de cajas en el sistema"), e);
		}
	}

	/**
	 * Obtiene el próximo id línea de detalle de caja para la caja actualmente abierta en sesión
	 *
	 * @return Integer (idLinea)
	 * @throws CajasServiceException
	 */
	public Integer consultarProximaLineaDetalleCaja() throws CajasServiceException {
		String uidActividad = sesion.getUidActividad();
		String uidDiarioCaja = sesion.getSesionCaja().getUidDiarioCaja();
		try {
			log.debug("consultarProximaLineaDetalleCaja() - Obteniendo próximo número de línea de detalle de caja para uidDiarioCaja: " + uidDiarioCaja);

			CashJournalLineExample exampleCajaMovimiento = new CashJournalLineExample();
			exampleCajaMovimiento.or().andUidDiarioCajaEqualTo(uidDiarioCaja).andUidActividadEqualTo(uidActividad);
			log.debug("crearMovimiento() - obteniendo el numero de linea del movimiento");
			Integer linea = cajaMovimientoMapper.selectMaxLineId(exampleCajaMovimiento);
			if (linea == null) {
				linea = 1;
			}
			return linea;
		}
		catch (Exception e) {
			String msg = "Se ha producido un error consultando siguiente línea de detalle de caja para uidDiarioCaja: " + uidDiarioCaja + " : " + e.getMessage();
			log.error("consultarProximaLineaDetalleCaja() - " + msg, e);
			throw new CajasServiceException(I18N.getTexto("Error al consultar los movimientos de caja en el sistema"), e);
		}
	}

	/**
	 * Registra un nuevo movimiento de apertura de caja según los parámetros indicados
	 *
	 * @param importe
	 * @param fecha
	 * @throws CajasServiceException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void crearMovimientoApertura(BigDecimal importe, Date fecha) throws CajasServiceException {
		log.debug("crearMovimientoApertura() - Registrando movimiento de apertura de caja por importe: " + importe);
		try {
			CashJournalLine movimiento = new CashJournalLine();
			movimiento.setOutput(importe);
			movimiento.setConcept(I18N.getTexto("SALDO INICIAL"));
			movimiento.setPaymentMethodCode(sesion.getSesionCaja().getMedioPagoDefecto().getCodMedioPago());
			movimiento.setMovConceptCode(CODIGO_CONCEPTO_APERTURA);
			movimiento.setCashJournalDate(fecha);
			movimiento.setUserCode(sesion.getUser());

			crearMovimiento(movimiento);
		}
		catch (CajasServiceException e) {
			throw e;
		}
		catch (Exception e) {
			String msg = "Se ha producido un error insertando movimiento de caja por apertura de caja: " + e.getMessage();
			log.error("crearMovimientoApertura() - " + msg, e);
			throw new CajasServiceException(I18N.getTexto("Error al insertar movimiento de caja"), e);
		}		
	}

	/**
	 * Registra un nuevo movimiento manual según los parámetros indicados
	 *
	 * @param importe
	 * @param codConcepto
	 * @param documento
	 * @throws CajasServiceException
	 */
	@Transactional(rollbackFor = Exception.class)
	public CashJournalLine crearMovimientoManual(BigDecimal importe, String codConcepto, String documento, String descConcepto) throws CajasServiceException {
		log.debug("crearMovimientoManual() - Registrando movimiento manual por importe: " + importe + ". Y concepto: " + codConcepto);

		CashJournalLine movimiento = new CashJournalLine();
		try {
			CashJournalConcept concepto = sesion.getSesionCaja().getConceptoCaja(codConcepto);
			if (concepto == null) {
				log.error("crearMovimientoManual() - Se está intentando insertar un movimiento con concepto nulo. Código concepto: " + codConcepto);
				throw new CajasServiceException(I18N.getTexto("Error al insertar movimiento de caja"));
			}
			concepto.setCashJournalConceptDes(descConcepto);
			if(CashJournalConcept.MOV_INPUT.equals(concepto.getInOutType())) {
				movimiento.setOutput(importe);
			}else if(CashJournalConcept.MOV_OUTPUT.equals(concepto.getInOutType())) {
				movimiento.setInput(importe);
			}else {
				if (BigDecimalUtil.isMayorACero(importe)) {
					movimiento.setInput(importe); // salida de caja
				}
				else {
					movimiento.setOutput(importe.negate()); // entrada de caja
				}
			}
			movimiento.setMovConceptCode(concepto.getCashJournalConceptCode());
			movimiento.setConcept(concepto.getCashJournalConceptDes());
			movimiento.setPaymentMethodCode(sesion.getSesionCaja().getMedioPagoDefecto().getCodMedioPago());
			movimiento.setSalesDocCode(documento);
			movimiento.setCashJournalDate(new Date());
			movimiento.setUserCode(sesion.getUser());

			crearMovimiento(movimiento);
		}
		catch (CajasServiceException e) {
			throw e;
		}
		catch (Exception e) {
			String msg = "Se ha producido un error insertando movimiento de caja por concepto: " + codConcepto + " : " + e.getMessage();
			log.error("crearMovimientoManual() - " + msg, e);
			throw new CajasServiceException(I18N.getTexto("Error al insertar movimiento de caja"), e);
		}		

		return movimiento;
	}


	

	/**
	 * Registra un nuevo movimiento indicado por parámetro a la caja abierta actualmente.
	 *
	 * @param movimiento
	 *            :: Nuevo movimiento
	 * @throws CajasServiceException
	 */
	public void crearMovimiento(CashJournalLine movimiento) throws CajasServiceException {
		try {
			String uidActividad = sesion.getUidActividad();
			String uidDiarioCaja = sesion.getSesionCaja().getUidDiarioCaja();

			// añadimos al objeto el uidActividad y el uiddiarioCaja de sesion
			movimiento.setActivityId(uidActividad);
			movimiento.setCashJournalUid(uidDiarioCaja);
			movimiento.setUserCode(sesion.getUser());

			if (movimiento.getLineId() == null) {
				Integer idLinea = consultarProximaLineaDetalleCaja();
				movimiento.setLineId(idLinea);
			}
			log.debug("crearMovimiento() - Insertando movimiento de caja");
			cajaMovimientoMapper.insert(movimiento);
		}
		catch (Exception e) {
			String msg = "Se ha producido un error insertando movimiento de caja con código de pago: " + movimiento.getPaymentMethodCode() + " : " + e.getMessage();
			log.error("crearMovimiento() - " + msg, e);
			throw new CajasServiceException(I18N.getTexto("Error al insertar movimiento de caja"), e);
		}
	}


	/**
	 * Elimina todas las líneas de recuento registradas de la caja y registra nuevamente las actuales.
	 *
	 * @param caja
	 * @throws CajasServiceException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void salvarRecuento(CashJournalDTO caja) throws CajasServiceException {
		try {
			log.debug("salvarRecuento() - Salvando recuento para caja con uid: " + caja.getCashJournalUid());

			String uidActividad = sesion.getUidActividad();

			CashJournalCountLineExample example = new CashJournalCountLineExample();
			example.or().andUidActividadEqualTo(uidActividad).andUidDiarioCajaEqualTo(caja.getCashJournalUid());

			log.debug("salvarRecuento() - Borramos todas las líneas de recuentos anteriores...");
			cajaLineaRecuentoMapper.deleteByExample(example);

			log.debug("salvarRecuento() - Insertamos nuevas líneas de recuento...");
			Integer linea = 0;
			for (CashJournalCountLine lineaRecuento : caja.getCashJournalCountLines()) {
				lineaRecuento.setCashJournalUid(caja.getCashJournalUid());
				lineaRecuento.setActivityId(uidActividad);
				lineaRecuento.setLineId(linea);
				cajaLineaRecuentoMapper.insert(lineaRecuento);
				linea++;
			}
		}
		catch (Exception e) {
			String msg = "Se ha producido un error salvando recuento de caja: " + e.getMessage();
			log.error("crearLineaRecuento() - " + msg, e);
			throw new CajasServiceException(I18N.getTexto("Error al salvar recuento de caja"), e);
		}
	}

	/**
	 * Crea un nuevo registro en la tabla de cajas cabecera indicando la fecha apertura pasada por parámetro. Utiliza la
	 * tienda y caja de la sesión. Si ya existe una caja abierta para esa caja y tienda lanza una excepción.
	 *
	 * @param fechaApertura
	 *            :: Fecha con la que se abrirá la caja
	 * @return Caja
	 * @throws CajasServiceException
	 * @throws CajaEstadoException
	 *             :: Lanzada si la caja ya está abierta
	 */
	public CashJournalDTO crearCaja(Date fechaApertura) throws CajasServiceException, CajaEstadoException {
		try {
			String uidActividad = sesion.getUidActividad();
			String codAlmacen = sesion.getSesionCaja().getCodAlm();
			String codCaja = sesion.getSesionCaja().getCodCaja();

			CashJournalExample exampleCaja = new CashJournalExample();
			exampleCaja.or().andUidActividadEqualTo(uidActividad).andCodAlmacenEqualTo(codAlmacen).andCodcajaEqualTo(codCaja).andFechaCierreIsNull();
			log.debug("crearCaja() - consultado caja con codAlmacen" + codAlmacen + "  y codCaja " + codCaja);
			List<CashJournalHdr> cajasBean = cajaMapper.selectByExample(exampleCaja);

			if (!cajasBean.isEmpty()) {
				log.warn("crearCaja() - Error creando caja. La caja esta marcada como abierta");
				throw new CajaEstadoException(I18N.getTexto("Ya existe una caja abierta en el sistema "));
			}
			CashJournalHdr cajaBean = new CashJournalHdr();
			cajaBean.setActivityId(uidActividad);
			cajaBean.setCashJournalUid(UUID.randomUUID().toString());
			cajaBean.setStoreCode(codAlmacen);
			cajaBean.setTillCode(codCaja);
			cajaBean.setOpenUserCode(sesion.getUser());
			cajaBean.setOpeningDate(fechaApertura);
			cajaBean.setClosingDate(null);
			cajaBean.setCloseUserCode(null);
			cajaBean.setTransmissionDate(null);
			
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaContable = formatter.parse(formatter.format(fechaApertura));

			
			cajaBean.setAccountingDate(fechaContable);
			log.debug("crearCaja() - Insertando caja con codAlmacen " + codAlmacen + "  y codCaja " + codCaja);

			// Dependiendo si la fecha tiene hora o no, llamamos a un método u otro del mapper
			Calendar calendarApertura = Calendar.getInstance();
			calendarApertura.setTime(fechaApertura);
			if (calendarApertura.get(Calendar.HOUR_OF_DAY) == 0 && calendarApertura.get(Calendar.MINUTE) == 0 && calendarApertura.get(Calendar.SECOND) == 0
			        && calendarApertura.get(Calendar.MILLISECOND) == 0) {
				cajaMapper.insertFechaAperturaDate(cajaBean);
			}
			else {
				cajaMapper.insertFechaAperturaDateTime(cajaBean);
			}

			return new CashJournalDTO(cajaBean);
		}
		catch (CajaEstadoException e) {
			String msg = "Se ha producido un error insertando caja con fecha de apertura" + fechaApertura + " :" + e.getMessage();
			log.error("crearCaja() - " + msg);
			throw e;
		}
		catch (Exception e) {
			String msg = "Se ha producido un error insertando caja con fecha de apertura" + fechaApertura + " :" + e.getMessage();
			log.error("crearCaja() - " + msg, e);
			throw new CajasServiceException(I18N.getTexto("Error realizando apertura de caja"), e);
		}
	}

	/**
	 * Cierra la caja indicada con la fecha que se pasa por parámetro.
	 *
	 * @param caja
	 *            Caja abierta
	 * @param fechaCierre
	 *            Fecha de cierre de la caja
	 * @throws CajasServiceException
	 */	
	@Transactional(rollbackFor = Exception.class)
	public void cerrarCaja(CashJournalDTO caja, Date fechaCierre) throws CajasServiceException {
		
		try {
			Tienda tienda = sesion.getAplicacion().getTienda();
			String codTipoDocumento = sesion.getAplicacion().getDocumentos().getDocumento(Documentos.CIERRE_CAJA).getCodtipodocumento();
	
			log.debug("cerrarCaja() - Obteniendo contado para identificador...");
			Map<String, String> parametrosContador = new HashMap<>();
			parametrosContador.put("CODEMP", tienda.getAlmacenBean().getCodEmpresa());
			parametrosContador.put("CODALM", tienda.getAlmacenBean().getCodAlmacen());
			parametrosContador.put("CODSERIE", tienda.getAlmacenBean().getCodAlmacen());
			parametrosContador.put("CODCAJA", caja.getTillCode());
			parametrosContador.put("CODTIPODOCUMENTO", codTipoDocumento);
			parametrosContador.put("PERIODO", ((new Fecha(caja.getOpeningDate())).getAño().toString()));
	
			TipoDocumentoBean documentoActivo = sesion.getAplicacion().getDocumentos().getDocumento(codTipoDocumento);
	
			ContadorBean ticketContador = servicioContadores.obtenerContador(documentoActivo.getIdContador(), parametrosContador, sesion.getUidActividad());
			
			String codTicket = servicioContadores.obtenerValorTotalConSeparador(ticketContador.getDivisor3(), ticketContador.getValorFormateado());
			
			caja.setTransmissionDate(new Date());
			caja.setCloseUserCode(sesion.getUser());
			TicketBean ticket = new TicketBean();
			ticket.setCodAlmacen(tienda.getAlmacenBean().getCodAlmacen());
			ticket.setCodcaja(caja.getTillCode());
			ticket.setFecha(caja.getOpeningDate());
			ticket.setIdTicket(ticketContador.getValor());
			ticket.setUidTicket(caja.getCashJournalUid());
			
			CashJournalDocument cashJournalDocument = modelMapper.map(caja, CashJournalDocument.class);
			
			// el procesamiento requiere que la lista exista
			if (caja.getCashJournalCountLines() == null) {
				cashJournalDocument.setCashJournalCountLines(new ArrayList<>()); 				
			};
			
			// create cash journal xml document
			ByteArrayOutputStream  sw = new ByteArrayOutputStream ();
	        
			JAXBContext context = JAXBContext.newInstance(CashJournalDocument.class);				
			Marshaller mar = context.createMarshaller();
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			mar.marshal(cashJournalDocument, sw);			

			ticket.setTicket(sw.toByteArray());

			log.debug("TICKET: " + ticket.getUidTicket() + "\n" + new String(ticket.getTicket(), "UTF-8") + "\n");
			ticket.setIdTipoDocumento(documentoActivo.getIdTipoDocumento());
			ticket.setSerieTicket(ticketContador.getDivisor3());
			ticket.setCodTicket(codTicket);
			ticket.setFirma("*");
			ticket.setLocatorId(ticket.getUidTicket());
	
			ticketsService.insertarTicket(ticket, false);
	
			CashJournalHdr cajaBean = new CashJournalHdr();
			cajaBean.setCashJournalUid(caja.getCashJournalUid());
			cajaBean.setActivityId(sesion.getUidActividad());
			cajaBean.setClosingDate(fechaCierre);
			cajaBean.setCloseUserCode(sesion.getUser());
	
			// Dependiendo si la fecha tiene hora o no, llamamos a un método u otro del mapper
			Calendar calendarCierre = Calendar.getInstance();
			calendarCierre.setTime(fechaCierre);
			if (calendarCierre.get(Calendar.HOUR_OF_DAY) == 0 && calendarCierre.get(Calendar.MINUTE) == 0 && calendarCierre.get(Calendar.SECOND) == 0 && calendarCierre.get(Calendar.MILLISECOND) == 0) {
				cajaMapper.cierreCajaDateByPrimaryKey(cajaBean);
			}
			else {
				cajaMapper.cierreCajaDateTimeByPrimaryKey(cajaBean);
			}
		} catch (Exception e) {
			throw new CajasServiceException(e);
		}
	}


	public CashJournalDTO consultarUltimaCajaCerrada() throws CajasServiceException, CajaEstadoException {
		try {
			CashJournalExample exampleCaja = new CashJournalExample();
			exampleCaja.or().andUidActividadEqualTo(sesion.getUidActividad()).andCodAlmacenEqualTo(sesion.getAplicacion().getCodAlmacen())
			        .andCodcajaEqualTo(sesion.getAplicacion().getCodCaja()).andFechaCierreIsNotNull();
			exampleCaja.setOrderByClause(CashJournalExample.ORDER_BY_FECHA_APERTURA_DESC);
			log.debug("consultarUltimaCajaCerrada() - Consultado caja cerrada en sesion");
			List<CashJournalHdr> cajasBean = cajaMapper.selectByExample(exampleCaja);

			if (cajasBean.isEmpty()) {
				throw new CajaEstadoException(I18N.getTexto("No existen cajas cerrada en el sistema"));
			}
			return new CashJournalDTO(cajasBean.get(0));
		}
		catch (CajaEstadoException e) {
			throw e;
		}
		catch (Exception e) {
			String msg = "Se ha producido un error consultando caja cerrada en sesion :" + e.getMessage();
			log.error("consultarCajaAbierta() - " + msg, e);
			throw new CajasServiceException(I18N.getTexto("Error al consultar caja abierta en sesión del sistema"), e);
		}
	}

	/**
	 * Devuelve la caja abierta actualmente para la tienda y caja de la sesión. Si no existe ninguna caja abierta lanza
	 * una excepción.
	 *
	 * @return Caja
	 * @throws CajasServiceException
	 * @throws CajaEstadoException
	 *             :: Lanzada si no existe caja abierta
	 */
	public CashJournalDTO consultarCajaAbierta(String uidActividad, String codAlm, String codCaja) throws CajasServiceException, CajaEstadoException {
		try {
			CashJournalExample exampleCaja = new CashJournalExample();
			exampleCaja.or().andUidActividadEqualTo(uidActividad).andCodAlmacenEqualTo(codAlm)
			        .andCodcajaEqualTo(codCaja).andFechaCierreIsNull();
			log.debug("consultarCajaAbierta() - Consultado caja abierta en sesion");
			List<CashJournalHdr> cajasBean = cajaMapper.selectByExample(exampleCaja);

			if (cajasBean.isEmpty()) {
				throw new CajaEstadoException(I18N.getTexto("No existe caja abierta en el sistema"));
			}
			return new CashJournalDTO(cajasBean.get(0));
		}
		catch (CajaEstadoException e) {
			throw e;
		}
		catch (Exception e) {
			String msg = "Se ha producido un error consultando caja abierta en sesion :" + e.getMessage();
			log.error("consultarCajaAbierta() - " + msg, e);
			throw new CajasServiceException(I18N.getTexto("Error al consultar caja abierta en sesión del sistema"), e);
		}
	}

	/**
	 * Actualiza la caja pasada por parámetro con los movimientos registrados en BBDD para ella distinguiendo entre
	 * movimientos de venta y movimientos manuales
	 *
	 * @param caja
	 *            CajaBean
	 * @throws CajasServiceException
	 */
	public void consultarMovimientos(CashJournalDTO caja) throws CajasServiceException {
		try {
			log.debug("consultarMovimientos() - Consultando movimientos de caja para " + caja.getCashJournalUid());

			CashJournalLineExample exampleCajaMovimiento = new CashJournalLineExample();
			exampleCajaMovimiento.or().andUidActividadEqualTo(sesion.getUidActividad()).andUidDiarioCajaEqualTo(caja.getCashJournalUid()).andIdDocumentoIsNotNull();
			exampleCajaMovimiento.setOrderByClause(CashJournalLineExample.ORDER_BY_LINEA);
			List<CashJournalLine> movimientosVentas = cajaMovimientoMapper.selectByExample(exampleCajaMovimiento);

			exampleCajaMovimiento.clear();
			exampleCajaMovimiento.or().andUidActividadEqualTo(sesion.getUidActividad()).andUidDiarioCajaEqualTo(caja.getCashJournalUid()).andIdDocumentoIsNull();
			exampleCajaMovimiento.setOrderByClause(CashJournalLineExample.ORDER_BY_LINEA);
			List<CashJournalLine> movimientosApuntes = cajaMovimientoMapper.selectByExample(exampleCajaMovimiento);

			caja.setSalesMovements(movimientosVentas);
			caja.setManualMovements(movimientosApuntes);
		}
		catch (Exception e) {
			String msg = "Se ha producido un error consultando los movimientos de caja con uid: " + caja.getCashJournalUid() + " : " + e.getMessage();
			log.error("consultarMovimientos() - " + msg);
			throw new CajasServiceException(I18N.getTexto("Error al consultar los movimientos de caja en el sistema"), e);
		}
	}

	/**
	 * Actualiza la caja pasada por parámetro con las líneas de recuento registradas en BBDD para ella
	 *
	 * @param caja
	 * @throws CajasServiceException
	 */
	public void consultarRecuento(CashJournalDTO caja) throws CajasServiceException {
		try {
			CashJournalCountLineExample exampleCajaLineaRecuento = new CashJournalCountLineExample();

			exampleCajaLineaRecuento.or().andUidDiarioCajaEqualTo(caja.getCashJournalUid());
			log.debug("consultarRecuento() - Consultando recuento para caja con uid: " + caja.getCashJournalUid());
			List<CashJournalCountLine> recuento = cajaLineaRecuentoMapper.selectByExample(exampleCajaLineaRecuento);
			caja.setCashJournalCountLines(recuento);
		}
		catch (Exception e) {
			String msg = "Se ha producido un error consultando los recuentos de caja con UID: " + caja.getCashJournalUid() + " : " + e.getMessage();
			log.error("consultarRecuento() - " + msg);
			throw new CajasServiceException(I18N.getTexto("Error al consultar los recuentos de cajas en el sistema"), e);
		}
	}

	public CashJournalLine consultarMovimientoApartado(String uidDiarioCaja, int linea, String uidActividad) throws CajasServiceException {
		try {
			CashJournalLineKey key = new CashJournalLineKey();
			key.setLineId(linea);
			key.setActivityId(uidActividad);
			key.setCashJournalUid(uidDiarioCaja);

			return cajaMovimientoMapper.selectByPrimaryKey(key);
		}
		catch (Exception e) {
			String msg = "Se ha producido un error consultando los recuentos de caja con UID: " + uidDiarioCaja + " : " + e.getMessage();
			log.error("consultarRecuento() - " + msg);
			throw new CajasServiceException(I18N.getTexto("Error al consultar los recuentos de cajas en el sistema."), e);
		}
	}
	

	public boolean validarImporteBloqueoRetirada() throws CajasServiceException{
		log.debug("validarImporteBloqueoRetirada()");
		boolean isBloqueo = false;
		BigDecimal cantidadBloqueo = variablesServices.getVariableAsBigDecimal(VariablesServices.IMPORTE_BLOQUEO_RETIRADA);
		if(cantidadBloqueo != null && !BigDecimalUtil.isIgualACero(cantidadBloqueo)){
			BigDecimal acumulado = consultarAcumuladoCaja(sesion.getUidActividad(), sesion.getSesionCaja().getUidDiarioCaja());
			log.debug("validarImporteBloqueoRetirada() Acumulado: " + acumulado + " Bloqueo fijado en: " + cantidadBloqueo);
			if(BigDecimalUtil.isMayor(acumulado, cantidadBloqueo)){
				isBloqueo = true;
			}
		}
		return isBloqueo;
	}
	
	public boolean validarImporteAvisoRetirada() throws CajasServiceException{
		log.debug("validarImporteAvisoRetirada()");
		boolean isRetirada = false;
		BigDecimal avisoRetirada = variablesServices.getVariableAsBigDecimal(VariablesServices.IMPORTE_AVISO_RETIRADA);
		if(avisoRetirada != null && !BigDecimalUtil.isIgualACero(avisoRetirada)){
			BigDecimal acumulado = consultarAcumuladoCaja(sesion.getUidActividad(), sesion.getSesionCaja().getUidDiarioCaja());
			log.debug("validarImporteAvisoRetirada() Acumulado: " + acumulado + " Aviso fijado en: " + avisoRetirada);
			if(BigDecimalUtil.isMayor(acumulado, avisoRetirada)){				
				isRetirada = true;
			}
		}
		return isRetirada;
	}
	
	public BigDecimal consultarAcumuladoCaja(String uidActividad, String uidDiarioCaja) throws CajasServiceException {
		log.debug("consultarAcumuladoCaja()");
		BigDecimal acumuladorCaja = null;

		try {					
			BigDecimal contAcumulado = cajaMapper.contAcumuladoCaja(uidActividad, uidDiarioCaja);
			if(contAcumulado == null){
				acumuladorCaja = BigDecimal.ZERO;
			}else{
				acumuladorCaja = contAcumulado;
			}
			return acumuladorCaja;
		}
		catch (Exception e) {
			String msg = "Se ha producido un error consultando la cantidad acumulada en caja: " + uidDiarioCaja + " : " + e.getMessage();
			log.error("consultarAcumuladoCaja() - " + msg);
			throw new CajasServiceException(I18N.getTexto("Error al consultar la cantidada acumulada en caja."), e);
		}
	}

    public void consultarTotales(CashJournalDTO caja) throws CajasServiceException {
		try {
			log.debug("consultarTotales() - Consultando totales de caja para " + caja.getCashJournalUid());
			
			List<CashJournalSummaryDTO> resumenCaja = cajaMovimientoMapper.selectCashJournalSummary(sesion.getUidActividad(), caja.getCashJournalUid());
			
			CashJournalTotalsDTO totales = new CashJournalTotalsDTO();
			for(CashJournalSummaryDTO resumen : resumenCaja) {
				if(resumen.getDocType().equals("SALES")) {
					totales.setTotalSalesInput(resumen.getTotal() != null ? resumen.getTotal().abs() : BigDecimal.ZERO);
					caja.setInputDocumentsCount(resumen.getDocCount().intValue());
				}
				else if(resumen.getDocType().equals("RETURNS")) {
					totales.setTotalSalesOutput(resumen.getTotal() != null ? resumen.getTotal().abs() : BigDecimal.ZERO);
					caja.setOutputDocumentsCount(resumen.getDocCount().intValue());
				}
				else if(resumen.getDocType().equals("MOV_INPUT")) {
					totales.setTotalMovementsInput(resumen.getTotal() != null ? resumen.getTotal().abs() : BigDecimal.ZERO);
				}
				else if(resumen.getDocType().equals("MOV_OUTPUT")) {
					totales.setTotalMovementsOutput(resumen.getTotal() != null ? resumen.getTotal().abs() : BigDecimal.ZERO);				
				}
			}
			caja.setCashJournalTotals(totales);
		}
		catch (Exception e) {
			String msg = "Se ha producido un error consultando los totales de caja con uid: " + caja.getCashJournalUid() + " : " + e.getMessage();
			log.error("consultarTotales() - " + msg, e);
			throw new CajasServiceException(I18N.getTexto("Error al consultar los totales de caja en el sistema"), e);
		}
    }

}
