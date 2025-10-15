package com.comerzzia.pos.services.core.sesion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.cajas.conceptos.CashJournalConcept;
import com.comerzzia.pos.persistence.cajas.movimientos.CashJournalLine;
import com.comerzzia.pos.persistence.cajas.movimientos.CashJournalSummaryByPaymentCodeDTO;
import com.comerzzia.pos.persistence.cajas.recuentos.CashJournalCountLine;
import com.comerzzia.pos.persistence.cajas.recuentos.CashJournalCountLineExample;
import com.comerzzia.pos.persistence.cajas.recuentos.CashJournalCountLineExample.Criteria;
import com.comerzzia.pos.persistence.cajas.recuentos.CashJournalCountLineMapper;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;
import com.comerzzia.pos.services.cajas.CashJournalDTO;
import com.comerzzia.pos.services.cajas.CajaEstadoException;
import com.comerzzia.pos.services.cajas.CajasService;
import com.comerzzia.pos.services.cajas.CajasServiceException;
import com.comerzzia.pos.services.cajas.conceptos.CajaConceptosServices;
import com.comerzzia.pos.services.core.sesion.paymentMethods.PaymentMethodsData;
import com.comerzzia.pos.services.core.variables.VariablesServices;
import com.comerzzia.pos.services.mediospagos.MediosPagosService;
import com.comerzzia.pos.services.payments.configuration.PaymentsMethodsConfiguration;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.i18n.I18N;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SesionCaja {
	protected final Logger log = Logger.getLogger(getClass());

	protected CashJournalDTO cajaAbierta;

	protected String codAlm;
	protected String codCaja;

	protected Map<String, CashJournalConcept> conceptosCaja = new HashMap<>();
	protected List<CashJournalConcept> conceptosCajaManual = new ArrayList<>();

	@Autowired
	CashJournalCountLineMapper cajaLineaRecuentoMapper;
	@Autowired
	protected MediosPagosService mediosPagosService;
	@Autowired
	protected CajasService cajasService;
	@Autowired
	private VariablesServices variablesServices;

	@Autowired
	private CajaConceptosServices cajaConceptosServices;

//	@Autowired
//	protected PaymentsManager paymentsManager;

	@Autowired
	StoreTillSessionCache storeTillSessionCache;

	@Autowired
	private PaymentsMethodsConfiguration paymentsMethodsConfiguration;
	
	protected StoreTillSessionKey storeTillSessionKey;
	
	protected SesionAplicacion sesionAplicacion;

	protected PaymentMethodsData paymentMethodsCacheData;

	public void init(StoreTillSessionKey storeTillSessionKey) throws SesionInitException {
		this.storeTillSessionKey = storeTillSessionKey;
		this.codAlm = storeTillSessionKey.getStoreId();
		this.codCaja = storeTillSessionKey.getTillId();
		
		try {
			this.sesionAplicacion = storeTillSessionCache.getStoreTillApplicationSession(storeTillSessionKey);
	
			//paymentsManager.setSesionCaja(this);
			updateConceptsCache();
		
			cajaAbierta = cajasService.consultarCajaAbierta(storeTillSessionKey.getUidActividad(), codAlm, codCaja);
		} catch (CajaEstadoException ignore) {
			log.info("init() - No existe caja abierta. ");
		} catch (Exception ex) {
			log.error("init() - Error inicializando registro de caja: " + ex.getMessage(), ex);
			throw new SesionInitException(ex);
		}
	}

	public void updateConceptsCache() {
		conceptosCaja.clear();
		conceptosCajaManual.clear();

		// construimos mapa y listas
		for (CashJournalConcept concepto : cajaConceptosServices.consultarConceptosCaja(storeTillSessionKey.getUidActividad())) {
			conceptosCaja.put(concepto.getCashJournalConceptCode(), concepto);
			if (concepto.getManual()) {
				conceptosCajaManual.add(concepto);
			}
		}
	}

	public void guardarCierreCaja(Date fechaCierre) throws CajasServiceException {
		log.debug("cerrarCaja() - Cerrando caja ... ");
		this.cajaAbierta.setClosingDate(fechaCierre);
		cajasService.cerrarCaja(cajaAbierta, fechaCierre);
	}

	public void cerrarCaja() {
		cajaAbierta = null;
	}

	public void abrirCajaAutomatica() throws CajasServiceException, CajaEstadoException {
		try {
			// Antes comprobamos que no hay ya caja abierta
			cajaAbierta = cajasService.consultarCajaAbierta(storeTillSessionKey.getUidActividad(), codAlm, codCaja);
			log.debug("abrirCajaAutomatica() - La caja ya se encontraba abierta en BBDD. ");
		} catch (CajaEstadoException ignore) {
			log.debug("abrirCajaAutomatica() - Abriendo nueva caja con saldo inicial cero... ");
			cajaAbierta = cajasService.crearCaja(new Date());
		}
	}

	public void abrirCajaManual(Date fecha, BigDecimal importe) throws CajasServiceException, CajaEstadoException {
		try {
			// Antes comprobamos que no hay ya caja abierta
			cajaAbierta = cajasService.consultarCajaAbierta(storeTillSessionKey.getUidActividad(), codAlm, codCaja);
			log.debug("abrirCajaManual() - La caja ya se encontraba abierta en BBDD. ");
		} catch (CajaEstadoException e) {
			log.debug("abrirCajaManual() - Abriendo nueva caja con parámetros indicados.. ");
			cajaAbierta = cajasService.crearCaja(fecha);
			if (BigDecimalUtil.isMayorACero(importe)) {
				cajasService.crearMovimientoApertura(importe, fecha);
				actualizarDatosCaja();
			}
		}
	}

	public void nuevaLineaRecuento(String codigo, BigDecimal importe, Integer cantidad) {
		log.debug("nuevaLineaRecuento() - Creando nueva línea de recuento...");
		CashJournalCountLine lineaRecuento = new CashJournalCountLine();
		lineaRecuento.setPaymentMethodCode(codigo);
		lineaRecuento.setQuantity(cantidad);
		lineaRecuento.setCountValue(importe);
		cajaAbierta.addCountLine(lineaRecuento);
	}

	public CashJournalLine crearApunteManual(BigDecimal importe, String codConcepto, String documento,
			String descConcepto) throws CajasServiceException {
		log.debug("crearApunteManual() - Creando nuevo movimiento de caja manual...");
		CashJournalLine mov = cajasService.crearMovimientoManual(importe, codConcepto, documento, descConcepto);
		actualizarDatosCaja();

		return mov;
	}

	public void actualizarDatosCaja() throws CajasServiceException {
		// Si la caja no está abierta, no hay que hacer nada
		if (!isCajaAbierta()) {
			return;
		}
		cajasService.consultarMovimientos(cajaAbierta);
		cajasService.consultarTotales(cajaAbierta);
		cajaAbierta.recalculateTotals();
	}

	public void actualizarRecuentoCaja() throws CajasServiceException {
		// Si la caja no está abierta, no hay que hacer nada
		if (!isCajaAbierta()) {
			return;
		}
		cajasService.consultarRecuento(cajaAbierta);
		cuadrarRecuentosAutomaticos();
	}

	public void salvarRecuento() throws CajasServiceException {
		cajasService.salvarRecuento(cajaAbierta);
	}

	public CashJournalDTO getCajaAbierta() {
		return cajaAbierta;
	}

	public boolean isCajaAbierta() {
		return cajaAbierta != null;
	}

	public String getUidDiarioCaja() {
		if (getCajaAbierta() == null) {
			return null;
		}
		return getCajaAbierta().getCashJournalUid();
	}

	public boolean tieneDescuadres() throws CajasServiceException {
		boolean res = false;
		BigDecimal descuadre = BigDecimal.ZERO;
		BigDecimal descuadreMax = variablesServices
				.getVariableAsBigDecimal(VariablesServices.CAJA_IMPORTE_MAXIMO_DESCUADRE);
		cuadrarRecuentosAutomaticos();

		List<CashJournalSummaryByPaymentCodeDTO> recuentosAcumulados = new ArrayList<>(cajaAbierta.getCashJournalSummaryByPaymentCode().values());

		for (CashJournalSummaryByPaymentCodeDTO lineaAcumulado : recuentosAcumulados) {
			descuadre = descuadre.add(lineaAcumulado.getDescuadre());
		}

		if (BigDecimalUtil.isMayor(descuadre.abs(), descuadreMax)) {
			res = true;
		}

		return res;
	}

	public void cuadrarRecuentosAutomaticos() throws CajasServiceException {
		// Si la caja no está abierta, no hay que hacer nada
		if (!isCajaAbierta()) {
			return;
		}
		List<MedioPagoBean> mediosPagoAutomaticoNoManuales = mediosPagosService.getMediosPagoAutomaticoNoManuales();

		// Primero los borramos de recuentos, no se pueden añadir manualmente
		for (ListIterator<CashJournalCountLine> iterator = cajaAbierta.getCashJournalCountLines().listIterator(); iterator
				.hasNext();) {
			CashJournalCountLine cajaLineaRecuento = iterator.next();
			for (MedioPagoBean medioPago : mediosPagoAutomaticoNoManuales) {
				if (cajaLineaRecuento.getPaymentMethodCode().equals(medioPago.getCodMedioPago())) {
					iterator.remove();
					break;
				}
			}
		}
		cajaAbierta.recalculateCountTotals();

		// Añadimos nuevos recuentos con los descuadres
		Map<String, CashJournalSummaryByPaymentCodeDTO> acumulados = cajaAbierta.getCashJournalSummaryByPaymentCode();
		for (MedioPagoBean medioPago : mediosPagoAutomaticoNoManuales) {
			CashJournalSummaryByPaymentCodeDTO cajaLineaAcumulado = acumulados.get(medioPago.getCodMedioPago());
			if (cajaLineaAcumulado != null && !BigDecimalUtil.isIgualACero(cajaLineaAcumulado.getDescuadre())) {
				nuevaLineaRecuento(cajaLineaAcumulado.getMedioPago().getCodMedioPago(),
						cajaLineaAcumulado.getDescuadre().multiply(new BigDecimal(-1)), 1);
			}
		}

		cajasService.salvarRecuento(cajaAbierta);
		cajaAbierta.recalculateCountTotals();
	}

	public void limpiarRecuentos(CashJournalDTO caja) throws CajasServiceException {

		try {
			CashJournalCountLineExample exampleCajaLineaRecuento = new CashJournalCountLineExample();

			exampleCajaLineaRecuento.or().andUidDiarioCajaEqualTo(caja.getCashJournalUid());
			log.debug("limpiarRecuentos() - Borrando recuento para caja con uid: " + caja.getCashJournalUid());
			cajaLineaRecuentoMapper.deleteByExample(exampleCajaLineaRecuento);
		} catch (Exception e) {
			String msg = "Se ha producido un error borrando los recuentos de caja con UID: " + caja.getCashJournalUid()
					+ " : " + e.getMessage();
			log.error("limpiarRecuentos() - " + msg);
			throw new CajasServiceException(I18N.getTexto("Error al borrar los recuentos de cajas en el sistema"), e);
		}
	}

	public boolean existeRecuento(CashJournalDTO caja) throws CajasServiceException {
		try {
			CashJournalCountLineExample exampleCajaLineaRecuento = new CashJournalCountLineExample();

			List<String> medioPagosAutomaticos = mediosPagosService.getCodMediosPagoAutomatico();

			Criteria or = exampleCajaLineaRecuento.or().andUidDiarioCajaEqualTo(caja.getCashJournalUid());
			if (medioPagosAutomaticos != null && !medioPagosAutomaticos.isEmpty()) {
				or.andCodMedioPagoNotIn(medioPagosAutomaticos);
			}
			log.debug("existeRecuento() - Consultando recuento para caja con uid: " + caja.getCashJournalUid());
			List<CashJournalCountLine> recuento = cajaLineaRecuentoMapper.selectByExample(exampleCajaLineaRecuento);
			if (recuento == null || recuento.isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			String msg = "Se ha producido un error consultando los recuentos de caja con UID: "
					+ caja.getCashJournalUid() + " : " + e.getMessage();
			log.error("existeRecuento() - " + msg);
			throw new CajasServiceException(I18N.getTexto("Error al consultar los recuentos de cajas en el sistema"),
					e);
		}
	}

	public Map<String, CashJournalConcept> getConceptosCaja() {
		return conceptosCaja;
	}

	public List<CashJournalConcept> getConceptosCajaManual() {
		return conceptosCajaManual;
	}

	/**
	 * Devuelve el concepto de caja cargado en sesión con el código indicado.
	 * 
	 * @param codConcepto
	 * @return CajaConceptoBean
	 */
	public CashJournalConcept getConceptoCaja(String codConcepto) {
		return conceptosCaja.get(codConcepto);
	}

	public PaymentMethodsData getPaymentMethodsData() {
		if (paymentMethodsCacheData == null) {
			try {
				paymentMethodsCacheData = storeTillSessionCache.getPaymentMethods(storeTillSessionKey);
				mediosPagosService.cargarMediosPago(sesionAplicacion, paymentMethodsCacheData);
				paymentsMethodsConfiguration.loadConfiguration(sesionAplicacion, paymentMethodsCacheData);
			} catch (Exception e) {
				throw new RuntimeException("Error cargando cache de medios de pago", e);
			}
		}
		return paymentMethodsCacheData;
	}

	public MedioPagoBean getMedioPagoDefecto() {
		return getPaymentMethodsData().getMedioPagoDefecto();
	}

	public Map<String, MedioPagoBean> getMediosPago() {
		return getPaymentMethodsData().getMediosPago();
	}

	public String getCodAlm() {
		return codAlm;
	}

	public String getCodCaja() {
		return codCaja;
	}

//	public PaymentsManager getPaymentsManager() {
//		return paymentsManager;
//	}
}
