package com.comerzzia.api.v2.facturacionmagento.services.facturacion;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.Caja;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.CajaBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.lineas.CajaLineaRecuentoBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.movimientos.CajaMovimientoBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.cupones.CuponBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.motivos.Motivo;
import com.comerzzia.api.v2.facturacionmagento.persistence.promociones.PromocionBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.tarifas.Tarifa;
import com.comerzzia.api.v2.facturacionmagento.persistence.tarjetas.TarjetaBean;
import com.comerzzia.api.v2.facturacionmagento.services.articulos.ArticulosService;
import com.comerzzia.api.v2.facturacionmagento.services.atcud.AtcudException;
import com.comerzzia.api.v2.facturacionmagento.services.atcud.FiscalDataService;
import com.comerzzia.api.v2.facturacionmagento.services.cajas.CajasService;
import com.comerzzia.api.v2.facturacionmagento.services.clientes.ClientesService;
import com.comerzzia.api.v2.facturacionmagento.services.contadores.ContadorService;
import com.comerzzia.api.v2.facturacionmagento.services.contadores.config.ConfigContadoresService;
import com.comerzzia.api.v2.facturacionmagento.services.contadores.rangos.ConfigContadorRangosService;
import com.comerzzia.api.v2.facturacionmagento.services.cupones.CuponesService;
import com.comerzzia.api.v2.facturacionmagento.services.documentos.TiposDocumentoService;
import com.comerzzia.api.v2.facturacionmagento.services.facturacion.exception.FacturacionException;
import com.comerzzia.api.v2.facturacionmagento.services.fidelizacion.FidelizacionService;
import com.comerzzia.api.v2.facturacionmagento.services.promociones.PromocionesService;
import com.comerzzia.api.v2.facturacionmagento.services.tarifas.TarifasService;
import com.comerzzia.api.v2.facturacionmagento.services.tarjetas.TarjetasService;
import com.comerzzia.api.v2.facturacionmagento.services.ticket.BricodepotCabeceraTicket;
import com.comerzzia.api.v2.facturacionmagento.services.ticket.audit.TicketAuditEvent;
import com.comerzzia.api.v2.facturacionmagento.services.ticket.audit.TicketAuditService;
import com.comerzzia.api.v2.facturacionmagento.services.ticket.lineas.BricodepotLineaTicket;
import com.comerzzia.api.v2.facturacionmagento.services.tiendas.TiendasService;
import com.comerzzia.api.v2.facturacionmagento.services.usuarios.UsuariosService;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.FacturacionRequest;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.FacturacionResponse;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.DeliveryData;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.Event;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.IdentificationCard;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.IdentificationCards;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.InvoiceData;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.LoyaltyDetails;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.PaymentsData;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.PaymentData;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.Promotion;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.PromotionApplied;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.Reason;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.Seller;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.TaxData;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.TaxesData;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.Ticket;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.TicketIssueData;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.TicketItem;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.TicketItems;
import com.comerzzia.bricodepot.backoffice.persistence.general.tiendas.atcud.AtcudMagento;
import com.comerzzia.bricodepot.backoffice.services.general.tiendas.atcud.BricodepotServicioAtcud;
import com.comerzzia.core.model.tiposdocumentos.TipoDocumentoBean;
import com.comerzzia.core.model.tiposdocumentos.prop.TipoDocumentoPropBean;
import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.servicios.config.configContadores.ContadoresConfigException;
import com.comerzzia.core.servicios.config.configContadores.ContadoresConfigNotFoundException;
import com.comerzzia.core.servicios.contadores.ContadorNotFoundException;
import com.comerzzia.core.servicios.documents.LocatorManager;
import com.comerzzia.core.servicios.empresas.EmpresaException;
import com.comerzzia.core.servicios.empresas.EmpresaNotFoundException;
import com.comerzzia.core.servicios.empresas.EmpresasService;
import com.comerzzia.core.servicios.sesion.DatosSesionBean;
import com.comerzzia.core.servicios.variables.ServicioVariablesImpl;
import com.comerzzia.core.servicios.ventas.tickets.TicketException;
import com.comerzzia.core.servicios.ventas.tickets.TicketService;
import com.comerzzia.core.util.base.KeyConstraintViolationException;
import com.comerzzia.core.util.fechas.Fecha;
import com.comerzzia.core.util.numeros.BigDecimalUtil;
import com.comerzzia.omnichannel.model.documents.sales.ticket.CuponAplicadoTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.PromocionTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.TicketVentaAbono;
import com.comerzzia.omnichannel.model.documents.sales.ticket.UsuarioBean;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.CabeceraTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.ClienteBean;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.DatosDocumentoOrigenTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.DatosFactura;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.EmpresaBean;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.FidelizacionBean;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.FirmaTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.FiscalData;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.SubtotalIvaTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.Tienda;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.TotalesTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.lineas.LineaTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.lineas.PromocionLineaTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.pagos.PagoTicket;
import com.comerzzia.pos.persistence.core.config.configcontadores.ConfigContadorBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ConfigContadorParametroBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoExample;
import com.comerzzia.pos.persistence.core.contadores.ContadorBean;
import com.comerzzia.pos.persistence.tickets.POSTicketMapper;
import com.comerzzia.pos.services.cajas.CajasServiceException;
import com.comerzzia.pos.services.core.config.configContadores.ContadoresConfigInvalidException;
import com.comerzzia.pos.services.core.config.configContadores.parametros.ConfigContadoresParametrosInvalidException;
import com.comerzzia.pos.services.core.contadores.ContadorServiceException;

@Component
@Scope("prototype")
public class FacturacionService {

                protected static final Logger log = Logger.getLogger(FacturacionService.class);

                private static final String PROPIEDAD_POS_FORMATO_IMPRESION = "POS.FORMATO_IMPRESION";

                private static final Set<String> DOCUMENTOS_DEVOLUCION = new HashSet<>(Arrays.asList("NC", "FR"));

	@Autowired
	protected TicketService ticketService;
	@Autowired
	protected CajasService cajasService;
	@Autowired
	protected TiposDocumentoService tiposDocumentoService;
	@Autowired
	protected TiendasService tiendasService;
	@Autowired
	protected ClientesService clientesService;
	@Autowired
	protected EmpresasService empresasService;
	@Autowired
	protected ConfigContadoresService configContadoresService;
	@Autowired
	protected ConfigContadorRangosService configContadorRangosService;
	@Autowired
	protected ContadorService contadorService;
	@Autowired
	protected LocatorManager locatorManager;
	@Autowired
	protected UsuariosService usuariosService;
	@Autowired
	protected FidelizacionService fidelizacionService;
	@Autowired
	protected ArticulosService articulosService;
	@Autowired
	protected TarifasService tarifasService;
	@Autowired
	protected PromocionesService promocionesService;
	@Autowired
	protected CuponesService cuponesService;
	@Autowired
	protected TicketAuditService ticketAuditService;
	@Autowired
	protected TarjetasService tarjetasService;
	@Autowired
	protected ServicioVariablesImpl servicioVariablesImpl;
	@Autowired
	protected FiscalDataService fiscalDataService;

	protected FacturacionRequest request;

	protected Caja cajaAbierta;

	protected DatosSesionBean datosSesion;

	protected UsuarioBean usuario;

	protected Tienda tienda;

	protected TipoDocumentoBean tipoDocumento;

	protected EmpresaBean empresa;

	protected ClienteBean cliente;

	protected TicketVentaAbono ticket;

	protected static JAXBContext jaxbContext;

	protected static Marshaller marshaller;
	
    @Autowired
    protected POSTicketMapper ticketMapper;

	@SuppressWarnings({ "deprecation"})
	public synchronized FacturacionResponse facturar(DatosSesionBean datosSesionBean, FacturacionRequest facturacionRequest) {
		log.debug("facturar()");
		SqlSession sqlSession = null;
		TicketBean ticketBean = null;
                FacturacionResponse response = new FacturacionResponse();
                try {
                        inicializarVariables(datosSesionBean, facturacionRequest);
                        ajustarValoresDevolucion();
                        sqlSession = datosSesion.getSqlSessionFactory().openSession();
                        gestionCaja();
			
			ticketBean = rellenarTicket(sqlSession);

			log.debug("facturar() - Facturando ticket con UID: " + ticketBean.getUidTicket());
			
			registarMovimientosCaja(sqlSession, datosSesionBean);
			ticketService.insertaTicket(sqlSession, ticketBean);
			nuevaLineaRecuento(sqlSession);
			sqlSession.commit();

			response = generarResponse(ticketBean);

		}
		catch (FacturacionException e) {
			log.error("facturar() - Ha ocurrido un error: " + e.getMessage(), e);
			return new FacturacionResponse(e.getMessage());
		}
		catch (TicketException e) {
			log.error("facturar() - Ha ocurrido un error: " + e.getMessage(), e);
			sqlSession.rollback();
			return new FacturacionResponse(e.getMessage());
		}
		catch (KeyConstraintViolationException e) {
			log.error("facturar() - Ha ocurrido un error: " + e.getMessage(), e);
			sqlSession.rollback();
			return new FacturacionResponse(e.getMessage());
		}
		catch (CajasServiceException e) {
			log.error("facturar() - Ha ocurrido un error: " + e.getMessage(), e);
			sqlSession.rollback();
			return new FacturacionResponse(e.getMessage());
		}
		catch (AtcudException e) {
			log.error("facturar() - Ha ocurrido un error: " + e.getMessage(), e);
			sqlSession.rollback();
			return new FacturacionResponse(e.getMessage());
		}
		finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		
		return response;
	}

	private void nuevaLineaRecuento(SqlSession sqlSession) throws CajasServiceException {
		log.debug("nuevaLineaRecuento() - Creando nueva linea de recuento");
		List<PagoTicket> pagos = ticket.getPagos();
		for (PagoTicket pago : pagos) {
			CajaLineaRecuentoBean lineaRecuento = new CajaLineaRecuentoBean();
			lineaRecuento.setCodMedioPago(pago.getCodMedioPago());
			lineaRecuento.setCantidad(1);
			lineaRecuento.setValor(pago.getImporte());
			cajaAbierta.addLineaRecuento(lineaRecuento);
		}
		cajasService.salvarRecuento(sqlSession, cajaAbierta, ticket.getUidActividad());
	}

        private void inicializarVariables(DatosSesionBean datosSesionBean, FacturacionRequest facturacionRequest) throws FacturacionException {
                log.debug("inicializarVariables()");

                request = facturacionRequest;
                datosSesion = datosSesionBean;
                tienda = obtenerTienda();
                cliente = obtenerCliente();
                tipoDocumento = obtenerTipoDocumento(datosSesionBean);
                empresa = obtenerEmpresa(datosSesionBean);

                /* Consultamos el usuario predeterminado para apertura de caja y para la cabecera del ticket */
                usuario = usuariosService.obtenerUsuarioPredeterminado(datosSesion);
        }

        private void ajustarValoresDevolucion() throws FacturacionException {
                log.debug("ajustarValoresDevolucion()");

                if (!esDocumentoDevolucion()) {
                        return;
                }

                if (cliente == null || StringUtils.isBlank(cliente.getCodpais())) {
                        return;
                }

                if (!"ES".equalsIgnoreCase(cliente.getCodpais())) {
                        return;
                }

                try {
                        Ticket ticketRequest = request.getTicket();
                        if (ticketRequest != null) {
                                ajustarTotalesDevolucion(ticketRequest);
                                ajustarLineasDevolucion(ticketRequest);
                        }
                        ajustarPagosDevolucion();
                }
                catch (NumberFormatException e) {
                        String msg = "Error ajustando los importes de la devolución: " + e.getMessage();
                        log.error("ajustarValoresDevolucion() - " + msg, e);
                        throw new FacturacionException(msg, e);
                }
        }

        private boolean esDocumentoDevolucion() {
                if (tipoDocumento == null || StringUtils.isBlank(tipoDocumento.getCodTipoDocumento())) {
                        return false;
                }

                String codigoTipoDoc = StringUtils.upperCase(tipoDocumento.getCodTipoDocumento());
                return DOCUMENTOS_DEVOLUCION.contains(codigoTipoDoc);
        }

        private void ajustarTotalesDevolucion(Ticket ticketRequest) {
                TicketIssueData ticketIssueData = ticketRequest.getTicketIssueData();
                if (ticketIssueData == null) {
                        return;
                }

                ticketIssueData.setTotalBaseAmount(toNegative(ticketIssueData.getTotalBaseAmount()));
                ticketIssueData.setTotalTaxAmount(toNegative(ticketIssueData.getTotalTaxAmount()));
                ticketIssueData.setTotalGrossAmount(toNegative(ticketIssueData.getTotalGrossAmount()));

                TaxesData taxesData = ticketIssueData.getTaxesData();
                if (taxesData != null && taxesData.getTaxData() != null) {
                        for (TaxData tax : taxesData.getTaxData()) {
                                tax.setBaseAmount(toNegative(tax.getBaseAmount()));
                                tax.setTaxAmount(toNegative(tax.getTaxAmount()));
                                tax.setTotal(toNegative(tax.getTotal()));
                        }
                }
        }

        private void ajustarLineasDevolucion(Ticket ticketRequest) {
                TicketItems ticketItems = ticketRequest.getTicketItems();
                if (ticketItems == null || ticketItems.getTicketItem() == null) {
                        return;
                }

                for (TicketItem ticketItem : ticketItems.getTicketItem()) {
                        ticketItem.setPriceWithoutDTO(toNegative(ticketItem.getPriceWithoutDTO()));
                        ticketItem.setTotalPriceWithoutDTO(toNegative(ticketItem.getTotalPriceWithoutDTO()));
                        ticketItem.setPrice(toNegative(ticketItem.getPrice()));
                        ticketItem.setTotalPrice(toNegative(ticketItem.getTotalPrice()));
                        ticketItem.setAmount(toNegative(ticketItem.getAmount()));
                        ticketItem.setTotalAmount(toNegative(ticketItem.getTotalAmount()));
                        ticketItem.setDiscount(toNegative(ticketItem.getDiscount()));
                        ticketItem.setUnitPrice(toNegative(ticketItem.getUnitPrice()));

                        if (ticketItem.getPromotions() != null && ticketItem.getPromotions().getPromotion() != null) {
                                for (Promotion promotion : ticketItem.getPromotions().getPromotion()) {
                                        promotion.setDiscountAmount(toNegative(promotion.getDiscountAmount()));
                                }
                        }

                        Reason reason = ticketItem.getReason();
                        if (reason != null) {
                                reason.setOriginalItemPrice(toNegative(reason.getOriginalItemPrice()));
                                reason.setItemPriceApplied(toNegative(reason.getItemPriceApplied()));
                        }
                }

                if (request.getPromotionsSummary() != null && request.getPromotionsSummary().getPromotionsApplied() != null
                                && request.getPromotionsSummary().getPromotionsApplied().getPromotionApplied() != null) {
                        for (PromotionApplied promotionApplied : request.getPromotionsSummary().getPromotionsApplied().getPromotionApplied()) {
                                BigDecimal negativeAmount = toNegative(BigDecimal.valueOf(promotionApplied.getDiscountAmount()));
                                if (negativeAmount != null) {
                                        promotionApplied.setDiscountAmount(negativeAmount.doubleValue());
                                }
                        }
                }
        }

        private void ajustarPagosDevolucion() {
                PaymentsData paymentsData = request.getPaymentsData();
                if (paymentsData == null || paymentsData.getPaymentData() == null) {
                        return;
                }

                for (PaymentData payment : paymentsData.getPaymentData()) {
                        payment.setPaymentAmount(toNegative(payment.getPaymentAmount()));
                }
        }

        private BigDecimal toNegative(BigDecimal value) {
                if (value == null) {
                        return null;
                }

                return value.signum() > 0 ? value.negate() : value;
        }

        private String toNegative(String value) {
                if (StringUtils.isBlank(value)) {
                        return value;
                }

                BigDecimal numericValue = new BigDecimal(value);
                return toNegative(numericValue).toPlainString();
        }

	private Tienda obtenerTienda() throws FacturacionException {
		String storeId = request.getStore().getStoreId();
		log.debug("obtenerTienda() - Consultando tienda con ID: " + storeId);

		Tienda tienda = null;
		try {
			tienda = tiendasService.consultarTienda(datosSesion.getUidActividad(), storeId);
			log.debug("obtenerTienda() - Tienda consultada: " + tienda.getCodAlmacen() + " " + tienda.getDesAlmacen());

			/* Añadimos la información a la request para tenerla al generar el response */
			request.getStore().setAddress(tienda.getDomicilio());
			request.getStore().setName(tienda.getDesAlmacen());
			request.getStore().setPhone(tienda.getTelefono1());
			request.getStore().setPostalCode(tienda.getCp());
			request.getStore().setProvince(tienda.getProvincia());
			request.getStore().setStoreId(tienda.getCodAlmacen());
			request.getStore().setTown(tienda.getPoblacion());

			ClienteBean clienteAsociadoTienda = clientesService.consultarPorCod(datosSesion.getUidActividad(), tienda.getCodAlmacen());
			request.getStore().setIdentificationType(clienteAsociadoTienda.getTipoIdentificacion());
			request.getStore().setTaxIdentificationNumber(clienteAsociadoTienda.getCif());

		}
		catch (Exception e) {
			String msg = "Error consultando tienda " + storeId;
			log.error("obtenerTienda() - " + msg);
			throw new FacturacionException(msg, e);
		}

		return tienda;
	}

	private TipoDocumentoBean obtenerTipoDocumento(DatosSesionBean datosSesion) throws FacturacionException {
		String invoiceDocumentType = request.getTicket().getTicketHeader().getInvoiceDocumentType();
		String country = cliente.getCodpais();
		log.debug("obtenerTipoDocumento() - Obteniendo tipo documento: " + invoiceDocumentType + " para el pais: " + country);

		TipoDocumentoBean tipoDocumento = null;
		try {
			tipoDocumento = tiposDocumentoService.consultar(datosSesion, invoiceDocumentType, country);
		}
		catch (Exception e) {
			String msg = "Error consultando tipo de documento " + invoiceDocumentType + " para el país: " + country;
			log.error("obtenerTipoDocumento() - " + msg);
			throw new FacturacionException(msg, e);
		}

		return tipoDocumento;
	}

	private EmpresaBean obtenerEmpresa(DatosSesionBean datosSesion) throws FacturacionException {
		log.debug("obtenerEmpresa()");

		EmpresaBean empresa = null;
		Seller seller = request.getSeller();
		String codEmpresa = null;
		if (seller != null && StringUtils.isNotBlank(seller.getSellerCode())) {
			try {
				codEmpresa = seller.getSellerCode();
				com.comerzzia.core.model.empresas.EmpresaBean empresaConsultada = empresasService.consultar(datosSesion, codEmpresa);
				if (empresaConsultada != null) {
					empresa = new EmpresaBean();
					empresa.setCif(empresaConsultada.getCif());
					empresa.setCodEmpresa(empresaConsultada.getCodEmp());
					empresa.setCp(empresaConsultada.getCp());
					empresa.setDesEmpresa(empresaConsultada.getDesEmp());
					empresa.setDomicilio(empresaConsultada.getDomicilio());
					empresa.setFax(empresaConsultada.getFax());
					empresa.setNombreComercial(empresaConsultada.getNombreComercial());
					empresa.setPoblacion(empresaConsultada.getPoblacion());
					empresa.setProvincia(empresaConsultada.getProvincia());
					empresa.setTelefono1(empresaConsultada.getTelefono1());
					empresa.setTelefono2(empresaConsultada.getTelefono2());

					/* Añadimos la información a la request para tenerla al generar el response */
					seller.setAddresss(empresa.getDomicilio());
					seller.setCountry(empresaConsultada.getCodPais());
					seller.setName(empresa.getDesEmpresa());
					seller.setPhone(empresa.getTelefono1());
					seller.setPostalCode(empresa.getCp());
					seller.setProvince(empresa.getProvincia());
					seller.setSellerCode(empresa.getCodEmpresa());
					seller.setTaxIdentificationNumber(empresa.getCif());
					seller.setTown(empresa.getPoblacion());
					seller.setIdentificationType(empresaConsultada.getCodtipoiden());
				}
			}
			catch (EmpresaException | EmpresaNotFoundException e) {
				String msg = "Error consultando la empresa " + codEmpresa;
				log.error("obtenerEmpresa() - " + msg);
				throw new FacturacionException(msg, e);
			}
		}

		return empresa;
	}

	private void gestionCaja() throws FacturacionException {
		log.debug("gestionCaja()");

		String codCaja = request.getTicket().getTicketHeader().getPosId();
		String codAlm = tienda.getCodAlmacen();
		CajaBean cajaBean = null;
		try {
			cajaBean = cajasService.consultarCajaAbierta(datosSesion.getUidActividad(), codCaja, codAlm);
			/* Si hay una caja abierta, comprobamos la fecha de su apertura, si no, abrimos una caja */
			if (cajaBean != null) {
				Date fechaApertura = cajaBean.getFechaApertura();
				Calendar calendarHoy = Calendar.getInstance();
				Calendar calendarFechaApertura = Calendar.getInstance();
				calendarFechaApertura.setTime(fechaApertura);

				boolean abiertaHoy = calendarHoy.get(Calendar.YEAR) == calendarFechaApertura.get(Calendar.YEAR) && calendarHoy.get(Calendar.MONTH) == calendarFechaApertura.get(Calendar.MONTH)
				        && calendarHoy.get(Calendar.DAY_OF_MONTH) == calendarFechaApertura.get(Calendar.DAY_OF_MONTH);

				if (!abiertaHoy) {
					log.debug("gestionCaja() - NO existe una caja abierta hoy");
					request.getStore().getStoreId();
					String country = cliente.getCodpais();
					cajasService.cierreCaja(datosSesion, usuario, cajaBean, tienda, empresa, country);
					cajaBean = cajasService.aperturaCaja(datosSesion, usuario, codCaja, codAlm);
				}
			}
			else {
				cajaBean = cajasService.aperturaCaja(datosSesion, usuario, codCaja, codAlm);
			}
		}
		catch (Exception e) {
			String msg = "Error en la gestión de caja: " + e.getMessage();
			log.error("gestionCaja() - " + msg);
			throw new FacturacionException(msg, e);
		}

		cajaAbierta = new Caja(cajaBean);
	}

	private TicketBean rellenarTicket(SqlSession sqlSession) throws FacturacionException, AtcudException {
		log.debug("rellenarTicket()");

		TicketBean ticketBean = new TicketBean();

		try {
			ticketBean.setUidActividad(datosSesion.getUidActividad());
			ticketBean.setCodAlmacen(cajaAbierta.getCodAlm());
			ticketBean.setCodCaja(cajaAbierta.getCodCaja());
			/* Fecha */
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			String issueDate = request.getTicket().getTicketIssueData().getIssueDate();
			Date fecha = dateFormat.parse(issueDate);
			ticketBean.setFecha(fecha);

			ticketBean.setIdTipoDocumento(tipoDocumento.getIdTipoDocumento());
			ticketBean.setProcesado(TicketBean.PENDIENTE);

			/* XML */
			ticket = new TicketVentaAbono();

			rellenarCabecera(ticket, ticketBean);
			
			AtcudMagento atcudAlmacen = comprobarATCUD(ticketBean, sqlSession);
			
			rellenarPiePromociones(ticket);
			rellenarLineas(ticket);
			rellenarPagos(ticket);
			rellenarExtensiones(ticket);
			rellenarCuponesAplicados(ticket);
			setContadoresTicket(ticketBean, ticket);
			setLocatorID(ticketBean, ticket);
			if (ticket.getCabecera().getCliente().getCodpais().equals("PT")) {
				FiscalData atcudGenerado = fiscalDataService.setATCUD(ticket, ticketBean, datosSesion, sqlSession, atcudAlmacen);
				request.setNewFiscalData(atcudGenerado);
			}
			
			if (ticket.getCabecera() != null && ticket.getCabecera().getFirma() != null) {
				log.debug("rellenarTicket() - La firma no es nula y se setea al ticketBean");
				ticketBean.setFirma(ticket.getCabecera().getFirma().getFirma());
			}
			else {
				log.debug("rellenarTicket() - La firma es nula y se setea al ticketBean y al xml del ticket para que contemplen el mismo valor");
				String firma = fiscalDataService.generarFirma(sqlSession, ticketBean, (BricodepotCabeceraTicket) ticket.getCabecera());
				FirmaTicket firmaTicket = new FirmaTicket();
				firmaTicket.setFirma(ticketBean.getFirma());
				ticketBean.setFirma(firma);
				ticket.getCabecera().setFirma(firmaTicket);
			}
		   	
			byte[] xmlTicket = transformarTicket(ticket);
			ticketBean.setTicket(xmlTicket);

			// Generacion de tickets de eventos.
			if (request.getTicket().getTicketHeader().getAuditEvents() != null) {
				generarAuditEvents((BricodepotCabeceraTicket) ticket.getCabecera(), ticket.getUidTicket());
			}

		}
		catch (AtcudException e) {
			log.error("rellenarTicket() - Error rellenando el atcud: " + e.getMessage(), e);
			throw new AtcudException(e.getMessage());
		}
		catch (Exception e) {
			log.error("rellenarTicket() - Error rellenando el ticket: " + e.getMessage(), e);
			throw new FacturacionException(e.getMessage(), e);

		}

		return ticketBean;

	}
	
	private AtcudMagento comprobarATCUD(TicketBean ticketBean, SqlSession sqlSession) throws AtcudException {
		AtcudMagento atcudAlmacen = null;
		if (ticket.getCabecera().getCliente().getCodpais().equals("PT")) {
			atcudAlmacen = BricodepotServicioAtcud.get().consultarAtcudPorAlmacenYTipoDoc(sqlSession, ticketBean.getCodAlmacen(), empresa.getCodEmpresa(), tipoDocumento.getCodTipoDocumento(),
			        ticketBean.getUidActividad());

			if (atcudAlmacen == null) {
				log.debug("comprobarATCUD() - No existe ATCUD configurado para el almacen : " + ticketBean.getCodAlmacen() + " y tipo de documento : " + tipoDocumento.getCodTipoDocumento());
				String msg = "No existe ATCUD configurado para el almacen : " + ticketBean.getCodAlmacen() + " y tipo de documento : " + tipoDocumento.getCodTipoDocumento();
				throw new AtcudException(msg);
			}

			try {
				ContadorBean ticketContador = obtenerContadorActivo(ticket, true);
				if (!atcudAlmacen.getMascaraDivisor3().equals(ticketContador.getDivisor3())) {
					throw new ContadorNotFoundException(
					        "El divisor 3 del contador del ATCUD " + atcudAlmacen.getMascaraDivisor3() + " no coincide con el del contador de ticket " + ticketContador.getDivisor3());
				}
			}
			catch (ContadorNotFoundException | ContadoresConfigNotFoundException | ContadoresConfigException | ContadoresConfigInvalidException | ConfigContadoresParametrosInvalidException
			        | ContadorServiceException e) {
				String msg = "Ha ocurrido un error al comprobar el contador activo para obtener el ATCUD: " + e.getMessage();
				log.debug("comprobarATCUD() - " + msg);
				throw new AtcudException(msg, e);
			}
		}

		return atcudAlmacen;
	}

	private void generarAuditEvents(BricodepotCabeceraTicket cabecera, String uidTicketVenta) {

		for (TicketAuditEvent auditEvent : cabecera.getAuditEvents()) {
			auditEvent.setUidTicketVenta(uidTicketVenta);
			ticketAuditService.saveAuditEvent(auditEvent, request, datosSesion);
		}

	}

	private String obtenerFormatoImpresion() {
		log.debug("obtenerFormatoImpresion()");

		List<TipoDocumentoPropBean> propiedadesTipoDocumento = tipoDocumento.getPropiedadesTipoDocumento();

		for (TipoDocumentoPropBean prop : propiedadesTipoDocumento) {
			if (prop.getPropiedad().equals(PROPIEDAD_POS_FORMATO_IMPRESION)) {
				return prop.getValor();
			}
		}

		return null;
	}

	private void rellenarCabecera(TicketVentaAbono ticketVentaAbono, TicketBean ticketBean) throws FacturacionException {

		log.debug("rellenarCabecera()");

		BricodepotCabeceraTicket cabecera = new BricodepotCabeceraTicket();
		cabecera.setUidTicket(ticketBean.getUidTicket());
		cabecera.setUidActividad(datosSesion.getUidActividad());
		cabecera.setCodCaja(ticketBean.getCodCaja());
		cabecera.setUidDiarioCaja(cajaAbierta.getUidDiarioCaja());
		cabecera.setFecha(ticketBean.getFecha());
		cabecera.setTipoDocumento(tipoDocumento.getIdTipoDocumento());
		cabecera.setCodTipoDocumento(tipoDocumento.getCodTipoDocumento());
		cabecera.setDesTipoDocumento(tipoDocumento.getDesTipoDocumento());
		cabecera.setCajero(usuario);
		cabecera.setTienda(tienda);
		cabecera.setEmpresa(empresa);

		if (request.getTicket().getTicketHeader().getAuditEvents() != null) {
			setearAuditEvents(cabecera);
		}
		FidelizacionBean datosFidelizado = obtenerDatosFidelizado();
		cabecera.setDatosFidelizado(datosFidelizado);
		try {
			if (request.getIdentificationCards() == null && cabecera.getDatosFidelizado() != null) {
				IdentificationCards tarjetas = new IdentificationCards();
				IdentificationCard tarjeta = new IdentificationCard();
				List<IdentificationCard> listaTarjetas = new ArrayList<>();

				// Cogemos el typeCode de la tarjeta
				TarjetaBean tarjetaBean = tarjetasService.consultar(datosSesion.getUidInstancia(), cabecera.getDatosFidelizado().getNumTarjetaFidelizado());

				tarjeta.setCardType(tarjetaBean.getCodTipoTarj());
				tarjeta.setCardCode(tarjetaBean.getNumTarjetaFidelizado());
				listaTarjetas.add(tarjeta);
				tarjetas.setIdentificationCard(listaTarjetas);
				request.setIdentificationCards(tarjetas);

			}
		}
		catch (Exception e) {
			log.warn("rellenarCabecera()- Error al consultar la tarjeta del fidelizado." + e.getMessage() + e);
		}

		String formatoImpresion = obtenerFormatoImpresion();
		cabecera.setFormatoImpresion(formatoImpresion);

		cabecera.setCliente(cliente);

		ClienteBean datosEnvio = obtenerDatosEnvio();
		cabecera.setDatosEnvio(datosEnvio);

		DatosDocumentoOrigenTicket datosTicketOrigen = obtenerDatosTicketOrigen(cliente.getIdTratImpuestos());
		cabecera.setDatosDocOrigen(datosTicketOrigen);

		rellenarTotalesCabecera(cabecera);
		rellenarImpuestosCabecera(cabecera);

		ticketVentaAbono.setCabecera(cabecera);
	}

	private void setearAuditEvents(BricodepotCabeceraTicket cabecera) throws FacturacionException {
		try {
			for (Event auditEvent : request.getTicket().getTicketHeader().getAuditEvents().getEvent()) {

				TicketAuditEvent audit = new TicketAuditEvent();

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				String auditDate = auditEvent.getDate();
				Date auditDateFormatted = dateFormat.parse(auditDate);

				audit.setMoment(auditDateFormatted);
				audit.setType(TicketAuditEvent.Type.valueOf(auditEvent.getType()));
				audit.setResolution(auditEvent.getEventDescription());
				audit.setSuccessful(new Boolean(auditEvent.getApplicable()));
				audit.setIdUsuario(Long.parseLong(auditEvent.getUserId()));
				audit.setDesUsuario(auditEvent.getUserDescription());
				audit.setIdUsuarioSupervisor(Long.parseLong(auditEvent.getSupervisorId()));
				audit.setDesUsuarioSupervisor(auditEvent.getSupervisorDescription());
				audit.setCodArticulo(auditEvent.getArticleCode());
				audit.setDesArticulo(auditEvent.getArticleDescription());
				audit.setCantidad(new BigDecimal(auditEvent.getArticleQuantity()));
				audit.setPrecioSinDtoOriginal(new BigDecimal(auditEvent.getOriginalItemPrice()));
				audit.setPrecioSinDtoAplicado(new BigDecimal(auditEvent.getItemPriceApplied()));
				audit.setUidActividad(datosSesion.getUidActividad());
				audit.setCodAlmacen(tienda.getCodAlmacen());

				cabecera.getAuditEvents().add(audit);

			}
		}
		catch (ParseException e) {
			log.error("setearAuditEvents() - Error al parsear la fecha. " + e.getMessage() + e);
			throw new FacturacionException(e);
		}
		catch (NumberFormatException e) {
			log.error("setearAuditEvents() - Error en el parseo de datos numéricos. " + e.getMessage() + e);
			throw new FacturacionException(e);
		}

	}

	private FidelizacionBean obtenerDatosFidelizado() throws FacturacionException {
		log.debug("obtenerDatosFidelizado()");

		FidelizacionBean fidelizacionBean = null;

		/* Comprobamos si la request trae un fidelizado */
		LoyaltyDetails loyaltyDetails = request.getLoyaltyDetails();
		if (loyaltyDetails != null && StringUtils.isNotBlank(loyaltyDetails.getLoyalCustomerId())) {

			Long idFidelizado = Long.parseLong(loyaltyDetails.getLoyalCustomerId());
			log.debug("obtenerDatosFidelizado() - La request trae fidelizado con ID: " + idFidelizado);

			/* Comprobamos si la request trae tarjetas */
			IdentificationCards identificationCards = request.getIdentificationCards();
			if (identificationCards != null && identificationCards.getIdentificationCard() != null && !identificationCards.getIdentificationCard().isEmpty()) {
				log.debug("obtenerDatosFidelizado() - La request trae identificationCards");

				List<IdentificationCard> identificationCardList = identificationCards.getIdentificationCard();
				for (IdentificationCard card : identificationCardList) {
					if (fidelizacionBean == null) {
						String cardCode = card.getCardCode();
						try {
							fidelizacionBean = fidelizacionService.consultar(datosSesion.getUidInstancia(), idFidelizado, cardCode);
						}
						catch (Exception e) {
							log.error("obtenerDatosFidelizado() - " + e.getMessage());
							throw new FacturacionException(e.getMessage(), e);
						}
					}
				}
			}
			else {
				try {
					fidelizacionBean = fidelizacionService.consultar(datosSesion.getUidInstancia(), idFidelizado);
				}
				catch (Exception e) {
					log.error("obtenerDatosFidelizado() - " + e.getMessage());
					throw new FacturacionException(e.getMessage(), e);
				}
			}
		}

		return fidelizacionBean;
	}

	private void rellenarTotalesCabecera(CabeceraTicket cabecera) {
		log.debug("rellenarTotalesCabecera()");

		TicketIssueData ticketIssueData = request.getTicket().getTicketIssueData();
		TotalesTicket totales = new TotalesTicket();

		BigDecimal base = ticketIssueData.getTotalBaseAmount();
		BigDecimal totalTaxAmount = ticketIssueData.getTotalTaxAmount();
		BigDecimal total = ticketIssueData.getTotalGrossAmount();
		totales.setBase(base);
		totales.setImpuestos(totalTaxAmount);
		totales.setTotal(total);
		cabecera.setTotales(totales);
	}

	private void rellenarImpuestosCabecera(CabeceraTicket cabecera) {
		log.debug("rellenarImpuestosCabecera() - Rellenamos los subtotales");

		List<TaxData> taxData = request.getTicket().getTicketIssueData().getTaxesData().getTaxData();
		List<SubtotalIvaTicket> subtotales = new ArrayList<>();

		for (TaxData tax : taxData) {
			SubtotalIvaTicket subtotal = new SubtotalIvaTicket();
			subtotal.setBase(tax.getBaseAmount());
			subtotal.setCodImpuesto(tax.getTaxTypeCode());
			subtotal.setCuota(tax.getTaxAmount());
			subtotal.setImpuestos(tax.getTaxAmount());
			subtotal.setPorcentaje(tax.getTaxPercentage());
			subtotal.setTotal(tax.getTotal());
			subtotal.setCuotaRecargo(BigDecimal.ZERO);
			subtotal.setPorcentajeRecargo(BigDecimal.ZERO);
			subtotales.add(subtotal);
		}
		cabecera.setSubtotalesIva(subtotales);
	}

	private ClienteBean obtenerCliente() throws FacturacionException {
		log.debug("obtenerCliente()");

		ClienteBean cliente = null;
		InvoiceData invoiceData = request.getInvoiceData();
		try {
			/* Añadimos la información de la request */
			if (cliente == null && invoiceData != null) {
				cliente = new ClienteBean();
				cliente.setDomicilio(invoiceData.getAddress());
				cliente.setCodpais(invoiceData.getCountry());
				cliente.setCodCliente(invoiceData.getTaxIdentificationNumber());
				cliente.setCif(invoiceData.getTaxIdentificationNumber());
				cliente.setTipoIdentificacion(invoiceData.getIdentificationType());
				cliente.setDesCliente(invoiceData.getName());
				cliente.setTelefono1(invoiceData.getPhone());
				cliente.setCp(invoiceData.getPostalCode());
				cliente.setProvincia(invoiceData.getProvince());
				cliente.setPoblacion(invoiceData.getTown());
				cliente.setIdTratImpuestos(Long.parseLong(invoiceData.getTaxesTreatmentId()));

				/* Grupo de impuestos */
				int grupoImpuestos = clientesService.consultarGrupoImpuestos(datosSesion.getUidActividad());
				cliente.setIdGrupoImpuestos(grupoImpuestos);

				/* Rellenamos los datosFactura */
				DatosFactura datosFactura = new DatosFactura();
				datosFactura.setDomicilio(cliente.getDomicilio());
				datosFactura.setCif(cliente.getCif());
				datosFactura.setTipoIdentificacion(cliente.getTipoIdentificacion());
				datosFactura.setTelefono(cliente.getTelefono1());
				datosFactura.setCp(cliente.getCp());
				datosFactura.setProvincia(cliente.getProvincia());
				datosFactura.setPoblacion(cliente.getPoblacion());
				datosFactura.setNombre(cliente.getDesCliente());
				datosFactura.setPais(cliente.getCodpais());
				cliente.setDatosFactura(datosFactura);
			}

			/* Si no tenemos información del cliente, añadimos el genérico de la tienda */
			if (invoiceData == null) {
				String codAlm = tienda.getCodAlmacen();
				cliente = clientesService.consultarPorCod(datosSesion.getUidActividad(), codAlm);
				request.setInvoiceData(new InvoiceData());
			}

			/* Añadimos el código del cliente a la request para añadirlo al generar el response */
			request.getInvoiceData().setCode(cliente.getCodCliente());
			request.getInvoiceData().setAddress(cliente.getDomicilio());
			request.getInvoiceData().setCountry(cliente.getCodpais());
			request.getInvoiceData().setIdentificationType(cliente.getTipoIdentificacion());
			request.getInvoiceData().setName(cliente.getDesCliente());
			request.getInvoiceData().setPhone(cliente.getTelefono1());
			request.getInvoiceData().setPostalCode(cliente.getCp());
			request.getInvoiceData().setProvince(cliente.getProvincia());
			request.getInvoiceData().setTaxesTreatmentId(cliente.getIdTratImpuestos().toString());
			request.getInvoiceData().setTaxIdentificationNumber(cliente.getCodCliente());
			request.getInvoiceData().setTown(cliente.getPoblacion());

			/* Añadimos el codPais a la Store, ya que cuando lo rellenamos no teníamos el cliente */
			request.getStore().setCountry(cliente.getCodpais());
		}
		catch (Exception e) {
			log.error("obtenerCliente() - " + e.getMessage());
			throw new FacturacionException(e.getMessage(), e);
		}

		return cliente;
	}

	private ClienteBean obtenerDatosEnvio() throws FacturacionException {
		log.debug("obtenerDatosEnvio()");

		ClienteBean cliente = null;
		DeliveryData deliveryData = request.getDeliveryData();
		try {
			/* Si tenemos información, buscamos el cliente */
			if (deliveryData != null && StringUtils.isNotBlank(deliveryData.getTaxIdentificationNumber())) {
				String taxIdentificationNumber = deliveryData.getTaxIdentificationNumber();
				log.debug("obtenerCliente() - Cliente en la request: " + taxIdentificationNumber);
				cliente = clientesService.consultarPorCif(datosSesion.getUidActividad(), taxIdentificationNumber);
			}

			/* Si no se ha encontrado un cliente, añadimos la información de la request */
			if (cliente == null && deliveryData != null) {
				cliente = new ClienteBean();
				cliente.setDomicilio(deliveryData.getAddress());
				cliente.setCodpais(deliveryData.getCountry());
				cliente.setCodCliente(deliveryData.getTaxIdentificationNumber());
				cliente.setCif(deliveryData.getTaxIdentificationNumber());
				cliente.setTipoIdentificacion(deliveryData.getIdentificationType());
				cliente.setDesCliente(deliveryData.getName());
				cliente.setTelefono1(deliveryData.getPhone());
				cliente.setCp(deliveryData.getPostalCode());
				cliente.setProvincia(deliveryData.getProvince());
				cliente.setPoblacion(deliveryData.getTown());
			}
		}
		catch (Exception e) {
			log.error("obtenerDatosEnvio() - " + e.getMessage());
			throw new FacturacionException(e.getMessage(), e);
		}

		return cliente;
	}

	private DatosDocumentoOrigenTicket obtenerDatosTicketOrigen(Long idTratImpuestos) throws FacturacionException {
		DatosDocumentoOrigenTicket datosTicketOrigen = null;

		try {
			String uidTicketOrigen = request.getTicket().getTicketHeader().getOriginalTicket();

			if (StringUtils.isNotBlank(uidTicketOrigen)) {
				log.debug("obtenerDatosTicketOrigen() - Consultando ticket con UID = " + uidTicketOrigen);
				TicketBean ticketOrigen = ticketService.consultarTicketUid(datosSesion, uidTicketOrigen);

				datosTicketOrigen = new DatosDocumentoOrigenTicket();
				datosTicketOrigen.setCaja(ticketOrigen.getCodCaja());
				datosTicketOrigen.setCodTicket(ticketOrigen.getCodTicket());
				datosTicketOrigen.setCodTipoDoc(ticketOrigen.getCodTipoDocumento());
				datosTicketOrigen.setDesTipoDoc(ticketOrigen.getDesTipoDocumento());
				datosTicketOrigen.setIdTipoDoc(ticketOrigen.getIdTipoDocumento());
				datosTicketOrigen.setIdTratImpuestos(idTratImpuestos);
				datosTicketOrigen.setNumFactura(ticketOrigen.getIdTicket());
				datosTicketOrigen.setRecoveredOnline(true);
				datosTicketOrigen.setSerie(ticketOrigen.getSerieTicket());
				datosTicketOrigen.setUidTicket(ticketOrigen.getUidTicket());
				Date fechaOrigen = ticketOrigen.getFecha();
				datosTicketOrigen.setFecha(fechaOrigen);

				/* Añadimos a la request la fecha origen para que al generar el response más tarde tenga este dato */
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				String fechaFormateada = dateFormat.format(fechaOrigen);
				request.getTicket().getTicketIssueData().setOrigenIssueDate(fechaFormateada);
			}
		}
		catch (Exception e) {
			String msg = "Error consultando ticket origen " + e.getMessage();
			log.error("obtenerDatosTicketOrigen() - " + msg);
			throw new FacturacionException(msg, e);
		}

		return datosTicketOrigen;
	}

	private void rellenarLineas(TicketVentaAbono ticketVentaAbono) throws FacturacionException {
		log.debug("rellenarLineas() - Rellenamos las lineas");

		List<TicketItem> ticketItems = request.getTicket().getTicketItems().getTicketItem();
		List<LineaTicket> lineas = new ArrayList<>();
		for (TicketItem ticketItem : ticketItems) {
			BricodepotLineaTicket linea = new BricodepotLineaTicket();
			linea.setCantidad(ticketItem.getQuantity());
			linea.setIdLinea(ticketItem.getOrder());
			linea.setCodArticulo(ticketItem.getItemCode());
			linea.setDesArticulo(ticketItem.getItemDescription());
			linea.setCodImpuesto(ticketItem.getTaxTypeCode());
			linea.setDesglose1(ticketItem.getCombination1Code());
			linea.setDesglose2(ticketItem.getCombination2Code());
			linea.setLineaDocumentoOrigen(ticketItem.getOrigenTicketLine() == 0 ? null : ticketItem.getOrigenTicketLine());

			linea.setPrecioSinDto(ticketItem.getPriceWithoutDTO());
			linea.setPrecioTotalSinDto(ticketItem.getTotalPriceWithoutDTO());
			linea.setPrecioConDto(ticketItem.getPrice());
			linea.setPrecioTotalConDto(ticketItem.getTotalPrice());
			linea.setImporteConDto(ticketItem.getAmount());
			linea.setImporteTotalConDto(ticketItem.getTotalAmount());
			linea.setDescuento(ticketItem.getDiscount());

			/* Tarifa */
			try {
				Tarifa tarifaArticuloActiva = tarifasService.consultarTarifaActivaArticulo(datosSesion.getUidActividad(), tienda.getCodAlmacen(), ticketItem.getItemCode());
				if (tarifaArticuloActiva != null) {
					linea.setCodtar(tarifaArticuloActiva.getCodTar());
					linea.setPrecioTarifaOrigen(tarifaArticuloActiva.getPrecioVenta());
					linea.setPrecioTotalTarifaOrigen(tarifaArticuloActiva.getPrecioTotal());
					linea.setDescuentoManual(BigDecimal.ZERO);
				}
			}
			catch (Exception e) {
				throw new FacturacionException(e);
			}

			/* Promociones */
			if (ticketItem.getPromotions() != null) {
				List<Promotion> promotions = ticketItem.getPromotions().getPromotion();
				List<PromocionLineaTicket> promociones = new ArrayList<>();
				for (Promotion promotion : promotions) {
					PromocionLineaTicket promocionLinea = new PromocionLineaTicket();
					long idPromocion = promotion.getPromotionId();
					promocionLinea.setIdPromocion(idPromocion);
					promocionLinea.setTipoDescuento(promotion.getDtoType());
					promocionLinea.setImporteTotalDto(promotion.getDiscountAmount());
					long idTipoPromocion = promocionesService.consultarIdTipoPromocion(datosSesion.getUidActividad(), idPromocion);
					promocionLinea.setIdTipoPromocion(idTipoPromocion);
					
					for(PromocionTicket promo : ticketVentaAbono.getPromociones()) {
						if(promo.getIdPromocion().equals(Long.valueOf(promotion.getPromotionId()))) {
							if(StringUtils.isNotBlank(promo.getCodAcceso())) {
								promocionLinea.setAcceso("CUPON");
							}
							else {
								promocionLinea.setAcceso("PROMOCION");
							}
						}
						else {
							promocionLinea.setAcceso("PROMOCION");
						}
						promocionLinea.setCodAcceso(promo.getCodAcceso());
					}
					

					promociones.add(promocionLinea);
				}

				linea.setPromociones(promociones);
			}

			/* Motivo */
			if (ticketItem.getReason() != null) {
				linea.setMotivo(new Motivo());
				linea.getMotivo().setCodigo(ticketItem.getReason().getCode());
				linea.getMotivo().setCodigoTipo(ticketItem.getReason().getTypeCode());
				linea.getMotivo().setDescripcion(ticketItem.getReason().getDescription());
				linea.getMotivo().setComentario(ticketItem.getReason().getComment());
				linea.getMotivo().setPrecioSinDtoOriginal(new BigDecimal(ticketItem.getReason().getOriginalItemPrice()));
				linea.getMotivo().setPrecioSinDtoAplicado(new BigDecimal(ticketItem.getReason().getItemPriceApplied()));
				linea.getMotivo().setUidActividad(datosSesion.getUidActividad());
			}

			lineas.add(linea);

			/* Consultamos el codBar y lo añadimos al ticketItem para añadirlo al response cuando lo generemos */
			String ean = articulosService.consultarCodBar(datosSesion.getUidActividad(), linea.getCodArticulo());
			ticketItem.setEan(ean);

		}
		ticketVentaAbono.setLineas(lineas);
		ticketVentaAbono.getCabecera().setCantidadArticulos(request.getTicket().getTicketItems().getTotalQuantity());
	}

	private void rellenarPiePromociones(TicketVentaAbono ticketVentaAbono) {
		log.debug("rellenarPiePromociones() - Rellenamos las promociones");
		List<PromocionTicket> listaPromociones = new ArrayList<PromocionTicket>();

		if (request.getPromotionsSummary() != null) {
			List<PromotionApplied> promotionApplied = request.getPromotionsSummary().getPromotionsApplied().getPromotionApplied();
			if (!promotionApplied.isEmpty()) {
				for (PromotionApplied promotion : promotionApplied) {
					PromocionTicket promocionTicket = new PromocionTicket();
					
					PromocionBean promocionBean = promocionesService.consultarPromocion(datosSesion.getUidActividad(), promotion.getPromotionId());
					if(promocionBean != null) {
						promocionTicket.setDescripcionPromocion(promocionBean.getDescripcion());
						promocionTicket.setTipoDescuento(promocionBean.getTipoDto());
						promocionTicket.setExclusiva(promocionBean.getExclusiva());
						promocionTicket.setIdTipoPromocion(promocionBean.getIdTipoPromocion());
						promocionTicket.setTextoPromocion(promocionBean.getDescripcion());
					}
					
					if(StringUtils.isNotBlank(promotion.getCuponPromotionId())) {
						promocionTicket.setAcceso("CUPON");
					}
					else {
						promocionTicket.setAcceso("PROMOCION");
					}
					promocionTicket.setCodAcceso(String.valueOf(promotion.getCuponPromotionId()));
					String discountAmount = String.valueOf(promotion.getDiscountAmount());
					promocionTicket.setImporteTotalAhorro(new BigDecimal(discountAmount));
					promocionTicket.setIdPromocion(Long.valueOf(promotion.getPromotionId()));
					listaPromociones.add(promocionTicket);
				}
			}
		}
		ticketVentaAbono.setPromociones(listaPromociones);
	}
	
	private void rellenarCuponesAplicados(TicketVentaAbono ticketVentaAbono) {
		log.debug("rellenarCuponesAplicados() - Rellenamos los cupones aplicados");
		List<CuponAplicadoTicket> listaCuponesAplicados = new ArrayList<CuponAplicadoTicket>();

		if (request.getPromotionsSummary() != null) {
			List<PromotionApplied> promotionApplied = request.getPromotionsSummary().getPromotionsApplied().getPromotionApplied();
			if (!promotionApplied.isEmpty()) {
				for (PromotionApplied promotion : promotionApplied) {
					if (StringUtils.isNotBlank(promotion.getCuponPromotionId())) {
						CuponAplicadoTicket cuponAplicado = new CuponAplicadoTicket();

						CuponBean cuponBean = cuponesService.consultarCupon(datosSesion.getUidActividad(), promotion.getCuponPromotionId());
						PromocionBean promocionBean = promocionesService.consultarPromocion(datosSesion.getUidActividad(), promotion.getPromotionId());
						if (cuponBean != null && promocionBean != null) {
							cuponAplicado.setCodigo(cuponBean.getCouponCode());
							cuponAplicado.setIdPromocion(cuponBean.getPromotionId());
							cuponAplicado.setTextoPromocion(cuponBean.getCouponDescription());
							cuponAplicado.setTipoCupon(cuponBean.getCouponTypeCode());
							cuponAplicado.setIdTipoPromocion(promocionBean.getIdTipoPromocion());
							cuponAplicado.setImporteTotalAhorrado(BigDecimal.valueOf(promotion.getDiscountAmount()));
						}

						listaCuponesAplicados.add(cuponAplicado);
					}
				}
			}
		}
		ticketVentaAbono.setCuponesAplicados(listaCuponesAplicados);
	}

	private void rellenarPagos(TicketVentaAbono ticketVentaAbono) {
		log.debug("rellenarPagos() - Rellenamos los pagos");

		List<PaymentData> payments = request.getPaymentsData().getPaymentData();
		List<PagoTicket> pagosTicket = new ArrayList<>();
		for (PaymentData payment : payments) {
			PagoTicket pago = new PagoTicket();
			pago.setCodMedioPago(payment.getPaymentMethodCode());
			pago.setDesMedioPago(payment.getPaymentDescription());
			pago.setImporte(payment.getPaymentAmount());

			Map<String, Object> extendedData = new HashMap<String, Object>();
			extendedData.put("documento", request.getTicket().getTicketHeader().getOrigenTicket());
			pago.setExtendedData(extendedData);
			pagosTicket.add(pago);
		}

		ticketVentaAbono.setPagos(pagosTicket);

	}

	private void rellenarExtensiones(TicketVentaAbono ticketVentaAbono) {
		log.debug("rellenarExtensiones() - Rellenando extensiones...");

		if (request.getTicket().getTicketHeader().getOrigenTicket() != null) {
			Map<String, Object> extensiones = new HashMap<String, Object>();
			extensiones.put("origenTicket", request.getTicket().getTicketHeader().getOrigenTicket());
			ticketVentaAbono.setExtensiones(extensiones);
		}
	}

	private void setContadoresTicket(TicketBean ticketBean, TicketVentaAbono ticketVentaAbono) throws FacturacionException {
		try {
			ContadorBean ticketContador = obtenerContadorActivo(ticketVentaAbono, false);

			/* UUID Ticket */
			String uidTicket = UUID.randomUUID().toString();
			ticketBean.setUidTicket(uidTicket);
			ticketVentaAbono.getCabecera().setUidTicket(uidTicket);
			/* ID Ticket */
			ticketBean.setIdTicket(ticketContador.getValor());
			ticketVentaAbono.getCabecera().setIdTicket(ticketContador.getValor());
			/* COD Ticket */
			String codTicket = contadorService.obtenerValorTotalConSeparador(datosSesion, ticketContador.getConfigContador().getValorDivisor3Formateado(), ticketContador.getValorFormateado());
			ticketBean.setCodTicket(codTicket);
			ticketVentaAbono.getCabecera().setCodTicket(codTicket);
			/* SERIE Ticket */
			ticketBean.setSerieTicket(ticketContador.getConfigContador().getValorDivisor3Formateado());
			ticketVentaAbono.getCabecera().setSerieTicket(ticketContador.getConfigContador().getValorDivisor3Formateado());

		}
		catch (Exception e) {
			String msg = "Se ha producido un error consultando el contador activo para idContador = " + tipoDocumento.getIdContador();
			log.error("setContadoresTicket() - " + msg, e);
			throw new FacturacionException(msg, e);
		}

		ticketBean.setLocatorId("*"); // PROVISIONAL
	}

	private ContadorBean obtenerContadorActivo(TicketVentaAbono ticketVentaAbono, boolean simulacion) throws ContadoresConfigNotFoundException, ContadoresConfigException, ContadoresConfigInvalidException,
	        ConfigContadoresParametrosInvalidException, ContadorServiceException, ContadorNotFoundException {
		log.debug("obtenerContador() - Obteniendo contador para identificador...");
		Map<String, String> parametrosContador = new HashMap<>();
		Map<String, String> condicionesVigencias = new HashMap<>();

		parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_CODEMP, ticketVentaAbono.getEmpresa().getCodEmpresa());
		parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_CODALM, ticketVentaAbono.getTienda().getCodAlmacen());
		parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_CODSERIE, ticketVentaAbono.getTienda().getCodAlmacen());
		parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_CODCAJA, ticketVentaAbono.getCodCaja());
		parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_CODDOC, ticketVentaAbono.getCabecera().getCodTipoDocumento());
		parametrosContador.put(ConfigContadorParametroBean.PARAMETRO_PERIODO, ((new Fecha()).getAño().toString()));

		condicionesVigencias.put(ConfigContadorRangoBean.VIGENCIA_CODCAJA, ticketVentaAbono.getCabecera().getCodCaja());
		condicionesVigencias.put(ConfigContadorRangoBean.VIGENCIA_CODALM, ticketVentaAbono.getCabecera().getTienda().getCodAlmacen());
		condicionesVigencias.put(ConfigContadorRangoBean.VIGENCIA_CODEMP, ticketVentaAbono.getCabecera().getEmpresa().getCodEmpresa());

		ConfigContadorBean confContador = configContadoresService.consultar(tipoDocumento.getIdContador());
		if (!confContador.isRangosCargados()) {
			ConfigContadorRangoExample example = new ConfigContadorRangoExample();
			example.or().andIdContadorEqualTo(confContador.getIdContador());
			example.setOrderByClause(ConfigContadorRangoExample.ORDER_BY_RANGO_INICIO + ", " + ConfigContadorRangoExample.ORDER_BY_RANGO_FIN + ", "
			        + ConfigContadorRangoExample.ORDER_BY_RANGO_FECHA_INICIO + ", " + ConfigContadorRangoExample.ORDER_BY_RANGO_FECHA_FIN);
			List<ConfigContadorRangoBean> rangos = configContadorRangosService.consultar(example);

			confContador.setRangos(rangos);
			confContador.setRangosCargados(true);
		}
		ContadorBean ticketContador = contadorService.consultarContadorActivo(confContador, parametrosContador, condicionesVigencias, ticketVentaAbono.getUidActividad(), true, simulacion);
		if (ticketContador == null || ticketContador.getError() != null) {
			throw new ContadorNotFoundException("No se ha encontrado un contador disponible");
		}
		return ticketContador;
	}

	private void setLocatorID(TicketBean ticketBean, TicketVentaAbono ticketVentaAbono) {
		log.debug("setLocatorID() - Obteniendo localizador...");

		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("codAlmacen", ticketBean.getCodAlmacen());
		parametros.put("codCaja", ticketBean.getCodCaja());
		String locatorID = locatorManager.encode(parametros);

		ticketBean.setLocatorId(locatorID);
		ticketVentaAbono.getCabecera().setLocalizador(locatorID);
	}

	private byte[] transformarTicket(TicketVentaAbono ticketVentaAbono) throws Exception {
		log.debug("transformarTicket() - Transformando ticket en XML");

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			// Crear el contexto JAXB y el marshaller
			if (jaxbContext == null) {
				jaxbContext = JAXBContext.newInstance(TicketVentaAbono.class, BricodepotCabeceraTicket.class, BricodepotLineaTicket.class, HashMap.class);
			}
			if (marshaller == null) {
				marshaller = jaxbContext.createMarshaller();

				// Configurar el marshaller para que produzca un XML con formato
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			}

			// Convertir el objeto en XML
			marshaller.marshal(ticketVentaAbono, outputStream);

			// Obtener el arreglo de bytes resultante
			return outputStream.toByteArray();
		}

	}

	private FacturacionResponse generarResponse(TicketBean ticketBean) throws FacturacionException {
		log.debug("generarResponse() - Generando response para el ticket con UID: " + ticketBean.getUidTicket());

		/* La respuesta tiene lo mismo que la petición más el CODTICKET y el UIDTICKET */
		FacturacionResponse response = new FacturacionResponse();

		try {
			BeanUtils.copyProperties(response, request);
			response.setCodTicket(ticketBean.getCodTicket());
			response.setUidTicket(ticketBean.getUidTicket());
			response.setIdLocator(ticketBean.getLocatorId());
			response.setFiscalDataItems(request.getFiscalDataItems());

		}
		catch (Exception e) {
			String msg = "Error generando response: " + e.getMessage();
			log.error("generarResponse() - " + msg);
			throw new FacturacionException(msg, e);
		}

		return response;
	}

	private void registarMovimientosCaja(SqlSession sqlSession, DatosSesionBean datosSesion) throws CajasServiceException {
		log.debug("registrarMovimientosCaja() - Registrando movimientos de caja");
		Integer idLineaCaja = cajasService.consultarProximaLineaDetalleCaja(sqlSession, cajaAbierta.getUidDiarioCaja());

		List<PagoTicket> pagos = ticket.getPagos();

		boolean esVenta = ticket.getCabecera().getDatosDocOrigen() == null;
		for (PagoTicket pago : pagos) {
			CajaMovimientoBean detalleCaja = new CajaMovimientoBean();
			detalleCaja.setLinea(idLineaCaja);
			detalleCaja.setFecha(ticket.getFecha());

			if (!esVenta) {
				if (BigDecimalUtil.isMayorOrIgualACero(ticket.getCabecera().getTotales().getTotal())) {
					if (pago.getImporte().compareTo(BigDecimal.ZERO) >= 0) {
						detalleCaja.setCargo(BigDecimal.ZERO);
						detalleCaja.setAbono(pago.getImporte().abs());
					}
					else {
						detalleCaja.setCargo(pago.getImporte().abs());
						detalleCaja.setAbono(BigDecimal.ZERO);
					}
				}
				else {
					if (pago.getImporte().compareTo(BigDecimal.ZERO) < 0) {
						detalleCaja.setCargo(BigDecimal.ZERO);
						detalleCaja.setAbono(pago.getImporte().abs());
					}
					else {
						detalleCaja.setCargo(pago.getImporte().abs());
						detalleCaja.setAbono(BigDecimal.ZERO);
					}
				}
			}
			else {
				if (pago.getImporte().compareTo(BigDecimal.ZERO) < 0) {
					detalleCaja.setCargo(BigDecimal.ZERO);
					detalleCaja.setAbono(pago.getImporte().abs());
				}
				else {
					detalleCaja.setCargo(pago.getImporte().abs());
					detalleCaja.setAbono(BigDecimal.ZERO);
				}
			}

			detalleCaja.setConcepto(ticket.getCabecera().getDesTipoDocumento() + ": " + ticket.getCabecera().getCodTicket());
			detalleCaja.setDocumento(ticket.getCabecera().getCodTicket());
			detalleCaja.setCodMedioPago(pago.getCodMedioPago());
			detalleCaja.setIdDocumento(ticket.getUidTicket());
			detalleCaja.setIdTipoDocumento(ticket.getCabecera().getTipoDocumento());
			cajasService.crearMovimiento(sqlSession, detalleCaja, ticket.getUidActividad(), ticket.getUidDiarioCaja(), datosSesion, usuario.getUsuario());
			idLineaCaja++;
		}
	}
	
}
