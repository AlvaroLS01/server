package com.comerzzia.api.v2.facturacionmagento.services.atcud;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.services.impuestos.tratamientos.TratamientoImpuestoService;
import com.comerzzia.api.v2.facturacionmagento.services.ticket.BricodepotCabeceraTicket;
import com.comerzzia.bricodepot.backoffice.persistence.general.tiendas.atcud.AtcudMagento;
import com.comerzzia.core.model.contadores.ContadorBean;
import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.servicios.config.configContadores.ContadoresConfigException;
import com.comerzzia.core.servicios.config.configContadores.ContadoresConfigNotFoundException;
import com.comerzzia.core.servicios.config.configContadores.parametros.ConfigContadoresParametrosException;
import com.comerzzia.core.servicios.contadores.ContadorException;
import com.comerzzia.core.servicios.sesion.DatosSesionBean;
import com.comerzzia.core.servicios.variables.ServicioVariablesImpl;
import com.comerzzia.core.util.base64.Base64Coder;
import com.comerzzia.core.util.numeros.BigDecimalUtil;
import com.comerzzia.firma.pt.HashSaftPt;
import com.comerzzia.omnichannel.model.documents.sales.ticket.TicketVentaAbono;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.FirmaTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.FiscalData;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.FiscalDataProperty;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.SubtotalIvaTicket;
import com.comerzzia.pos.persistence.core.impuestos.tratamientos.TratamientoImpuestoBean;
import com.comerzzia.pos.persistence.tickets.POSTicketMapper;
import com.comerzzia.pos.persistence.tickets.TicketExample;
import com.comerzzia.pos.services.ticket.TicketsServiceException;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.DecimalFormatSymbols;

@Component
public class FiscalDataService {

	protected static final Logger log = Logger.getLogger(FiscalDataService.class);
	private static final String DEFAULT_DOCUMENT_STATE = "N";
	private static final String DEFAULT_CIF = "999999990";
	private static final String DATE_FORMAT_PATTERN = "yyyyMMdd";
	private static final String DEFAULT_ATCUD = "0";
	private static final String DEFAULT_IVA = "0";
	private static final String DEFAULT_SOFTWATE_CERTIFICATE = "2175";
	private static final String PORTUGAL = "PT";
	private static final String PORTUGAL_AZORES = "AC";
	private static final String PORTUGAL_MADEIRA = "MA";
	private static final String SEPARATOR = "*";

	private static final String CODIMP_NORMAL = "1";
	private static final String CODIMP_REDUCED = "2";
	private static final String CODIMP_SUPER_REDUCED = "3";
	private static final String CODIMP_EXEMPT = "4";

	@Autowired
	protected ServicioVariablesImpl servicioVariablesImpl;
	@Autowired
	protected TratamientoImpuestoService taxTreatmentService;
	@Autowired
	protected POSTicketMapper ticketMapper;
	@Autowired
	protected BricodepotServicioContadoresAtcud servicioContadoresAtcud;

	public FiscalData setATCUD(TicketVentaAbono ticketVentaAbono, TicketBean ticketBean, DatosSesionBean datosSesion, SqlSession sqlSession, AtcudMagento atcudAlmacen)
	        throws ContadorException, ContadoresConfigException, ContadoresConfigNotFoundException, ConfigContadoresParametrosException, AtcudException {
		log.debug("setATCUD() - Insertando ATCUD.");
		BricodepotCabeceraTicket cabecera = ((BricodepotCabeceraTicket) ticketVentaAbono.getCabecera());

		FirmaTicket firma = new FirmaTicket();
		firma.setFirma(generarFirma(sqlSession, ticketBean, cabecera));
		if (!firma.getFirma().isEmpty()) {
			cabecera.setFirma(firma);
		}

		String atcudFormado = formarAtcud(datosSesion, atcudAlmacen, sqlSession);

		FiscalData fiscalData = new FiscalData();
		List<FiscalDataProperty> property = new ArrayList<FiscalDataProperty>();
		FiscalDataProperty atcud = new FiscalDataProperty("ATCUD", atcudFormado);
		FiscalDataProperty qr = new FiscalDataProperty("QR", generateQRBase64Data(cabecera, atcudFormado, datosSesion));

		property.add(atcud);
		property.add(qr);

		fiscalData.setProperties(property);
		cabecera.setFiscalData(fiscalData);
		return fiscalData;
	}

	private String formarAtcud(DatosSesionBean datosSesion, AtcudMagento atcudAlmacen, SqlSession sqlSession)
	        throws ContadorException, ContadoresConfigException, ContadoresConfigNotFoundException, ConfigContadoresParametrosException {
		log.debug("formarAtcud() - formando atcud para almacen : " + atcudAlmacen.getCodalm());
		String atcud = "";
		ContadorBean contador = servicioContadoresAtcud.obtenerContador(sqlSession, datosSesion.getUidActividad(), atcudAlmacen.getIdContador(), atcudAlmacen);
		/* Volvemos a setear el valor del contador para que rellene el valorFormateado */
		contador.setValor(contador.getValor());
		atcud = atcudAlmacen.getRango() + "-";
		String valorContador = contador.getValorFormateado();
		atcud = atcud + valorContador;
		return atcud;
	}

	private String generateQRBase64Data(BricodepotCabeceraTicket cabecera, String atcud, DatosSesionBean datosSesion) {
		log.debug("generateQRBase64Data() - Generando QR en Base64.");
		String a = "A:" + cabecera.getEmpresa().getCif();

		String b = "B:";
		if (cabecera.getCliente().getDatosFactura() != null) {
			b += cabecera.getCliente().getDatosFactura().getCif();
		}
		else {
			b += DEFAULT_CIF;
		}

		String c = "C:";
		if (cabecera.getCliente().getDatosFactura() != null) {
			c += cabecera.getCliente().getDatosFactura().getPais();
		}
		else {
			c += cabecera.getCliente().getCodpais();
		}

		String d = "D:" + cabecera.getCodTipoDocumento();

		String e = "E:" + DEFAULT_DOCUMENT_STATE;

		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
		String f = "F:" + dateFormat.format(new Date());

		String g = "G:" + cabecera.getCodTicket();

		String h = "H:" + (StringUtils.isNotBlank(atcud) ? atcud : DEFAULT_ATCUD);

		String tax1 = "";
		String tax2 = "";
		String tax3 = "";
		String tax4 = "";
		String tax5 = "";
		String tax6 = "";
		String tax7 = "";
		String tax8 = "";

		BigDecimal normalBase = BigDecimal.ZERO.setScale(2);
		BigDecimal normalTaxes = BigDecimal.ZERO.setScale(2);
		BigDecimal reducedBase = BigDecimal.ZERO.setScale(2);
		BigDecimal reducedTaxes = BigDecimal.ZERO.setScale(2);
		BigDecimal superReducedBase = BigDecimal.ZERO.setScale(2);
		BigDecimal superReducedTaxes = BigDecimal.ZERO.setScale(2);
		BigDecimal exemptBase = BigDecimal.ZERO.setScale(2);

		for (Object subtotal : cabecera.getSubtotalesIva()) {
			SubtotalIvaTicket subtotalIva = (SubtotalIvaTicket) subtotal;
			switch (subtotalIva.getCodImpuesto()) {
				case CODIMP_NORMAL:
					normalBase = normalBase.add(subtotalIva.getBase());
					normalTaxes = normalTaxes.add(subtotalIva.getImpuestos());
					break;
				case CODIMP_REDUCED:
					reducedBase = reducedBase.add(subtotalIva.getBase());
					reducedTaxes = reducedTaxes.add(subtotalIva.getImpuestos());
					break;
				case CODIMP_SUPER_REDUCED:
					superReducedBase = superReducedBase.add(subtotalIva.getBase());
					superReducedTaxes = superReducedTaxes.add(subtotalIva.getImpuestos());
					break;
				case CODIMP_EXEMPT:
					exemptBase = exemptBase.add(subtotalIva.getBase());
					break;
			}
		}

		Long taxTreatmentId = cabecera.getCliente().getIdTratImpuestos();

		TratamientoImpuestoBean taxTreatment = taxTreatmentService.consultarTratamientoImpuesto(datosSesion, taxTreatmentId);

		if (PORTUGAL_AZORES.equals(taxTreatment.getRegionImpuestos())) {
			tax1 = "J1:" + PORTUGAL_AZORES;
			tax2 = "J2:" + exemptBase.toString();
			tax3 = "J3:" + superReducedBase.toString();
			tax4 = "J4:" + superReducedTaxes.toString();
			tax5 = "J5:" + reducedBase.toString();
			tax6 = "J6:" + reducedTaxes.toString();
			tax7 = "J7:" + normalBase.toString();
			tax8 = "J8:" + normalTaxes.toString();
		}
		else if (PORTUGAL_MADEIRA.equals(taxTreatment.getRegionImpuestos())) {
			tax1 = "K1:" + PORTUGAL_MADEIRA;
			tax2 = "K2:" + exemptBase.toString();
			tax3 = "K3:" + superReducedBase.toString();
			tax4 = "K4:" + superReducedTaxes.toString();
			tax5 = "K5:" + reducedBase.toString();
			tax6 = "K6:" + reducedTaxes.toString();
			tax7 = "K7:" + normalBase.toString();
			tax8 = "K8:" + normalTaxes.toString();
		}
		else {
			tax1 = "I1:" + DEFAULT_IVA;
			if (!BigDecimalUtil.isIgualACero(cabecera.getTotales().getImpuestos())) {
				tax1 = "I1:" + PORTUGAL;
				tax2 = "I2:" + exemptBase.toString();
				tax3 = "I3:" + superReducedBase.toString();
				tax4 = "I4:" + superReducedTaxes.toString();
				tax5 = "I5:" + reducedBase.toString();
				tax6 = "I6:" + reducedTaxes.toString();
				tax7 = "I7:" + normalBase.toString();
				tax8 = "I8:" + normalTaxes.toString();
			}

		}

		String n = "N:" + cabecera.getTotales().getImpuestos().setScale(2).toString();
		String o = "O:" + cabecera.getTotales().getTotal().setScale(2).toString();
		BigDecimal surchargeFee = BigDecimal.ZERO.setScale(2);
		for (Object subtotal : cabecera.getSubtotalesIva()) {
			SubtotalIvaTicket subtotalIva = (SubtotalIvaTicket) subtotal;
			surchargeFee = surchargeFee.add(subtotalIva.getCuotaRecargo());
		}
		String p = "P:" + surchargeFee.toString();

		String firma = cabecera.getFirma().getFirma();
		String q = "Q:" + new StringBuilder().append(firma.charAt(0)).append(firma.charAt(10)).append(firma.charAt(20)).append(firma.charAt(30)).toString();
		String r = "R:" + DEFAULT_SOFTWATE_CERTIFICATE;

		String data = a + SEPARATOR + b + SEPARATOR + c + SEPARATOR + d + SEPARATOR + e + SEPARATOR + f + SEPARATOR + g + SEPARATOR + h + SEPARATOR + tax1 + SEPARATOR
		        + (StringUtils.isNotBlank(tax2) ? tax2 + SEPARATOR : "") + (StringUtils.isNotBlank(tax3) ? tax3 + SEPARATOR : "") + (StringUtils.isNotBlank(tax4) ? tax4 + SEPARATOR : "")
		        + (StringUtils.isNotBlank(tax5) ? tax5 + SEPARATOR : "") + (StringUtils.isNotBlank(tax6) ? tax6 + SEPARATOR : "") + (StringUtils.isNotBlank(tax7) ? tax7 + SEPARATOR : "")
		        + (StringUtils.isNotBlank(tax8) ? tax8 + SEPARATOR : "") + n + SEPARATOR + o + SEPARATOR + p + SEPARATOR + q + SEPARATOR + r;

		log.debug("getFiscalData() - La cadena generada para el QR es " + data);

		Base64Coder base64Coder = new Base64Coder(Base64Coder.UTF8);

		String base64Data = null;
		try {
			base64Data = base64Coder.encodeBase64(data);
		}
		catch (UnsupportedEncodingException e1) {
			throw new RuntimeException(e1);
		}

		return base64Data;
	}

	public String generarFirma(SqlSession sqlSession, TicketBean ticketBean, BricodepotCabeceraTicket cabecera) {
		log.debug("generarFirma() - Generando Firma");

		String firma = "";
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// Convertir la fecha a una cadena en el formato deseado
		String fechaFormateada = formato.format(ticketBean.getFecha());

		DecimalFormatSymbols simbolos = new DecimalFormatSymbols(); // Modificamos el separador para Portugal
		simbolos.setDecimalSeparator('.');
		DecimalFormat formateaSeparadorDecimal = new DecimalFormat("0.00", simbolos);
		String totalTicket = formateaSeparadorDecimal.format(cabecera.getTotales().getTotal().abs()); // siempre en
		                                                                                              // valor absoluto

		SimpleDateFormat formateaFechaSimple = new SimpleDateFormat("yyyy-MM-dd");
		String fechaSimple = formateaFechaSimple.format(cabecera.getFecha());

		try {
			com.comerzzia.pos.persistence.tickets.TicketBean ultimoTicket = consultarUltimoTicketFirma(sqlSession, cabecera.getUidActividad(), ticketBean.getIdTicket(), cabecera.getTipoDocumento(),
			        ticketBean.getSerieTicket());
			String cadena, sFirmaAnterior = "";

			if (ultimoTicket != null) {
				sFirmaAnterior = ultimoTicket.getFirma();
			}

			cadena = fechaSimple + ";" + fechaFormateada + ";" + cabecera.getCodTicket() + ";" + totalTicket + ";" + sFirmaAnterior;

			firma = HashSaftPt.firma(cadena);
		}
		catch (TicketsServiceException e) {
			log.error("generarFirma() - Excepción capturada" + e.getMessage(), e);
		}

		return firma;
	}

	public com.comerzzia.pos.persistence.tickets.TicketBean consultarUltimoTicketFirma(SqlSession sqlSession, String uidActividad, Long idTicket, Long idTipoDoc, String series)
	        throws TicketsServiceException {
		com.comerzzia.pos.persistence.tickets.TicketBean res = null;
		try {
			log.debug("consultarUltimoTicket() - Consultando último ticket en base de datos...");
			TicketExample example = new TicketExample();
			TicketExample.Criteria criteria = example.createCriteria();
			criteria.andUidActividadEqualTo(uidActividad);
			criteria.andIdTicketEqualTo(idTicket - 1);
			criteria.andIdTipoDocumentoEqualTo(idTipoDoc);
			criteria.andSerieEqualTo(series);
			example.setOrderByClause(TicketExample.ORDER_BY_ID_TICKET_DESC);
			List<com.comerzzia.pos.persistence.tickets.TicketBean> resultados = ticketMapper.selectByExample(example);
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
}
