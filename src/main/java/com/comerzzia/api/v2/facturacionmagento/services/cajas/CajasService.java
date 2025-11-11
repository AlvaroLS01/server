package com.comerzzia.api.v2.facturacionmagento.services.cajas;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.core.service.util.ComerzziaDatosSesion;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.Caja;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.CajaBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.CajaExample;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.CajaMapper;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.lineas.CajaLineaRecuentoBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.lineas.CajaLineaRecuentoExample;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.lineas.CajaLineaRecuentoMapper;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.movimientos.CajaMovimientoBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.movimientos.CajaMovimientoExample;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.movimientos.CajaMovimientosMapper;
import com.comerzzia.api.v2.facturacionmagento.services.cajas.lineas.CajaLineasService;
import com.comerzzia.api.v2.facturacionmagento.services.cajas.movimientos.CajaMovimientosService;
import com.comerzzia.api.v2.facturacionmagento.services.contadores.ContadorService;
import com.comerzzia.api.v2.facturacionmagento.services.documentos.TiposDocumentoService;
import com.comerzzia.api.v2.facturacionmagento.services.usuarios.UsuariosService;
import com.comerzzia.core.model.tiposdocumentos.TipoDocumentoBean;
import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.servicios.sesion.DatosSesionBean;
import com.comerzzia.core.servicios.variables.VariablesService;
import com.comerzzia.core.servicios.ventas.tickets.TicketService;
import com.comerzzia.core.util.fechas.Fecha;
import com.comerzzia.omnichannel.model.documents.sales.ticket.UsuarioBean;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.EmpresaBean;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.Tienda;
import com.comerzzia.pos.persistence.core.contadores.ContadorBean;
import com.comerzzia.pos.services.cajas.CajasServiceException;
import com.comerzzia.pos.util.i18n.I18N;
import com.comerzzia.pos.util.xml.MarshallUtil;
import com.comerzzia.pos.util.xml.MarshallUtilException;

@Component
public class CajasService {

	protected static final Logger log = Logger.getLogger(CajasService.class);

	@Autowired
	protected UsuariosService usuariosService;
	@Autowired
	protected VariablesService variablesService;
	@Autowired
	protected CajaMapper cajaMapper;
	@Autowired
	protected ContadorService contadorService;
	@Autowired
	protected TiposDocumentoService tiposDocumentoService;
	@Autowired
	protected CajaMovimientosService cajaMovimientosService;
	@Autowired
	protected CajaLineasService cajaLineasService;
	@Autowired
	protected TicketService ticketService;

	@Autowired
	protected CajaMovimientosMapper cajaMovimientoMapper;

	@Autowired
	protected CajaLineaRecuentoMapper cajaLineaRecuentoMapper;

	@Resource(name = "datosSesionRequest")
	ComerzziaDatosSesion datosSesionRequest;

	public CajaBean consultarCajaAbierta(String uidActividad, String codCaja, String codAlm) throws Exception {
		log.debug("consultarCajaAbierta() - Consultando caja abierta con codCaja = " + codCaja + " y codAlm " + codAlm);

		CajaExample cajaExample = new CajaExample();
		cajaExample.or().andUidActividadEqualTo(uidActividad).andCodcajaEqualTo(codCaja).andCodalmEqualTo(codAlm).andFechaCierreIsNull();
		List<CajaBean> cajaAbiertaList = cajaMapper.selectByExample(cajaExample);

		if (cajaAbiertaList == null || cajaAbiertaList.isEmpty()) {
			String msg = "No se ha encontrado caja abierta con codCaja = " + codCaja + " y codAlm = " + codAlm;
			log.error("consultarCajaAbierta() - " + msg);
			return null; /* Devolvemos null porque no debemos cortar el proceso */
		}

		CajaBean cajaAbierta = cajaAbiertaList.get(0);
		log.debug("consultarCajaAbierta() - Caja con codCaja = " + codCaja + " abierta encontrada con uidDiarioCaja = " + cajaAbierta.getUidDiarioCaja());

		return cajaAbierta;
	}

	public CajaBean aperturaCaja(DatosSesionBean datosSesion, UsuarioBean usuario, String codCaja, String codAlm) throws Exception {
		log.debug("aperturaCaja() - Abriendo caja con codCaja = " + codCaja + ", codAlm = " + codAlm + " y usuario = " + usuario);

		CajaBean cajaAbierta = new CajaBean();
		try {
			cajaAbierta.setUidActividad(datosSesion.getUidActividad());
			cajaAbierta.setUidDiarioCaja(UUID.randomUUID().toString());
			cajaAbierta.setCodAlmacen(codAlm);
			cajaAbierta.setCodCaja(codCaja);
			cajaAbierta.setUsuario(usuario.getDesusuario());
			cajaAbierta.setFechaApertura(new Date());
			cajaAbierta.setFechaCierre(null);
			cajaAbierta.setUsuarioCierre(null);
			cajaAbierta.setFechaEnvio(null);

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaContable = formatter.parse(formatter.format(cajaAbierta.getFechaApertura()));
			cajaAbierta.setFechaContable(fechaContable);
			cajaMapper.insert(cajaAbierta);
		}
		catch (Exception e) {
			String msg = "Error abriendo caja con codCaja = " + codCaja + ", codAlm = " + codAlm + " y usuario = " + usuario + ": " + e.getMessage();
			log.error("aperturaCaja() - " + msg);
			throw new Exception(msg, e);
		}

		return cajaAbierta;
	}

	@SuppressWarnings("deprecation")
	public void cierreCaja(DatosSesionBean datosSesion, UsuarioBean usuario, CajaBean cajaBean, Tienda tienda, EmpresaBean empresa, String codPais) throws Exception {
		log.debug("cierreCaja() - Cerrando caja " + cajaBean.getCodCaja() + " de la tienda " + tienda.getCodAlmacen());

		SqlSession sqlSession = null;

		String codTipoDocumento = "CJCIERRE";
		Map<String, String> parametrosContador = new HashMap<>();

		parametrosContador.put("CODEMP", empresa.getCodEmpresa());
		parametrosContador.put("CODALM", tienda.getCodAlmacen());
		parametrosContador.put("CODSERIE", tienda.getCodAlmacen());
		parametrosContador.put("CODCAJA", cajaBean.getCodCaja());
		parametrosContador.put("CODTIPODOCUMENTO", codTipoDocumento);
		parametrosContador.put("PERIODO", ((new Fecha()).getAño().toString()));

		try {
			TipoDocumentoBean tipoDoc = tiposDocumentoService.consultar(datosSesion, codTipoDocumento, codPais);
			ContadorBean ticketContador = contadorService.obtenerContador(tipoDoc.getIdContador(), parametrosContador, datosSesion.getUidActividad());

			cajaBean.setFechaCierre(new Date());
			cajaBean.setFechaEnvio(new Date());
			cajaBean.setUsuarioCierre(usuario.getUsuario());

			Caja caja = rellenarMovimientosCaja(datosSesion, cajaBean);
			TicketBean ticketBean = generarTicketCierre(datosSesion, caja, ticketContador, tipoDoc.getIdTipoDocumento());

			sqlSession = datosSesion.getSqlSessionFactory().openSession();
			ticketService.insertaTicket(sqlSession, ticketBean);
			sqlSession.commit();

			cajaMapper.cierreCajaDateTimeByPrimaryKey(cajaBean);

		}
		catch (Exception e) {
			String msg = "Error cerrando caja " + cajaBean.getCodCaja() + " de la tienda " + tienda.getCodAlmacen() + ": " + e.getMessage();
			log.error("cierreCaja() - " + msg, e);
			throw new Exception(msg, e);
		}
		catch (Throwable e) {
			String msg = "Error cerrando caja " + cajaBean.getCodCaja() + " de la tienda " + tienda.getCodAlmacen() + ": " + e.getMessage();
			log.error("cierreCaja() - " + msg, e);
			throw new Exception(msg, e);
		}
		finally {
			sqlSession.close();
		}

	}

	private Caja rellenarMovimientosCaja(DatosSesionBean datosSesion, CajaBean cajaBean) throws Exception {
		log.debug("rellenarMovimientosCaja()");

		Caja caja = new Caja(cajaBean);

		List<CajaMovimientoBean> movimientos = cajaMovimientosService.consultarMovimientos(cajaBean.getUidActividad(), caja.getUidDiarioCaja());
		caja.setMovimientosVenta(movimientos);

		List<CajaLineaRecuentoBean> lineasRecuento = cajaLineasService.consultarLineasRecuento(cajaBean.getUidActividad(), caja.getUidDiarioCaja());
		caja.setLineasRecuento(lineasRecuento);

		caja.recalcularTotales(datosSesion);
		caja.recalcularTotalesRecuento(datosSesion);

		return caja;
	}

	private TicketBean generarTicketCierre(DatosSesionBean datosSesion, Caja caja, ContadorBean ticketContador, Long idTipoDoc) throws MarshallUtilException, UnsupportedEncodingException {
		log.debug("generarTicketCierre() - Generando ticket de cierre de caja");

		TicketBean ticket = new TicketBean();
		ticket.setUidActividad(datosSesion.getUidActividad());
		ticket.setCodAlmacen(caja.getCodAlm());
		ticket.setCodCaja(caja.getCodCaja());
		ticket.setFecha(caja.getFechaApertura());
		ticket.setIdTicket(ticketContador.getValor());
		ticket.setUidTicket(caja.getUidDiarioCaja());
		ticket.setTicket(MarshallUtil.crearXML(caja));
		log.debug("TICKET: " + ticket.getUidTicket() + "\n" + new String(ticket.getTicket(), "UTF-8") + "\n");
		ticket.setIdTipoDocumento(idTipoDoc);
		ticket.setSerieTicket(ticketContador.getDivisor3());

		String codTicket = contadorService.obtenerValorTotalConSeparador(datosSesion, ticketContador.getDivisor3(), ticketContador.getValorFormateado());

		ticket.setCodTicket(codTicket);
		ticket.setProcesado(TicketBean.PENDIENTE);
		ticket.setFirma("*");
		ticket.setLocatorId(ticket.getUidTicket());

		return ticket;
	}

	public Integer consultarProximaLineaDetalleCaja(SqlSession sqlSession, String uidDiarioCaja) throws CajasServiceException {
		String uidActividad = datosSesionRequest.getActivityUid();
		try {
			log.debug("consultarProximaLineaDetalleCaja() - Obteniendo próximo número de línea de detalle de caja para uidDiarioCaja: " + uidDiarioCaja);
			CajaMovimientoExample exampleCajaMovimiento = new CajaMovimientoExample();
			exampleCajaMovimiento.or().andUidDiarioCajaEqualTo(uidDiarioCaja).andUidActividadEqualTo(uidActividad);
			log.debug("crearMovimiento() - obteniendo el numero de linea del movimiento");
			Integer linea = cajaMovimientoMapper.selectMaximaLineaMovimiento(exampleCajaMovimiento);
			if (linea == null) {
				linea = 1;
			}
			return linea;

		}
		catch (Exception e) {
			sqlSession.rollback();
			String msg = "Se ha producido un error consultando siguiente línea de detalle de caja para uidDiarioCaja: " + uidDiarioCaja + " : " + e.getMessage();
			log.error("consultarProximaLineaDetalleCaja() - " + msg, e);
			throw new CajasServiceException(I18N.getTexto("Error al consultar los movimientos de caja en el sistema"), e);
		}
	}

	public void crearMovimiento(SqlSession sqlSession, CajaMovimientoBean movimiento, String uidActividad, String uidDiarioCaja, DatosSesionBean datosSesion, String usuario)
	        throws CajasServiceException {
		log.debug("crearMovimiento - creando movimiento");
		try {

			// añadimos al objeto el uidActividad y el uiddiarioCaja de sesion
			movimiento.setUidActividad(uidActividad);
			movimiento.setUidDiarioCaja(uidDiarioCaja);
			movimiento.setUsuario(usuario);

			log.debug("crearMovimiento() - Comprobando cual es la proxima linea disponible");
			Integer idLinea = consultarProximaLineaDetalleCaja(sqlSession, uidDiarioCaja);

			if (movimiento.getLinea() != idLinea || movimiento.getLinea() == null) {
				movimiento.setLinea(idLinea);
			}

			log.debug("crearMovimiento() - Insertando movimiento de caja");
			cajaMovimientoMapper.insert(movimiento);
		}
		catch (Exception e) {
			String msg = "Se ha producido un error insertando movimiento de caja con código de pago: " + movimiento.getCodMedioPago() + " : " + e.getMessage();
			log.error("crearMovimiento() - " + msg, e);
			throw new CajasServiceException(I18N.getTexto("Error al insertar movimiento de caja"), e);
		}
	}

	public void salvarRecuento(SqlSession sqlSession, Caja caja, String uidActividad) throws CajasServiceException {
		log.debug("salvarRecuento() - Salvando recuento para caja con uid: " + caja.getUidDiarioCaja());

		CajaLineaRecuentoExample example = new CajaLineaRecuentoExample();
		example.or().andUidActividadEqualTo(uidActividad).andUidDiarioCajaEqualTo(caja.getUidDiarioCaja());

		List<CajaLineaRecuentoBean> recuentosActuales = cajaLineaRecuentoMapper.selectByExample(example);

		log.debug("salvarRecuento() - Comprobamos si existe en bbdd si es así update, si no, insert");
		Integer linea = 1;
		for (CajaLineaRecuentoBean lineaRecuento : caja.getLineasRecuento()) {
			boolean encontrado = false;
			CajaLineaRecuentoBean lineaActual = null;
			for (CajaLineaRecuentoBean recuentoBBDD : recuentosActuales) {
				if (recuentoBBDD.getCodMedioPago().equals(lineaRecuento.getCodMedioPago())) {
					encontrado = true;
					lineaActual = recuentoBBDD;
					break;
				}
			}
			if (!encontrado) {
				lineaRecuento.setUidDiarioCaja(caja.getUidDiarioCaja());
				lineaRecuento.setUidActividad(uidActividad);
				lineaRecuento.setLinea(linea + recuentosActuales.size());
				cajaLineaRecuentoMapper.insert(lineaRecuento);
				linea++;
			}
			else {
				lineaActual.setValor(lineaActual.getValor().add(lineaRecuento.getValor()));
				cajaLineaRecuentoMapper.updateByPrimaryKey(lineaActual);
			}
		}
	}
}
