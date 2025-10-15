/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND
 * INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED,
 * DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST,
 * TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK
 * WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE
 * AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */

package com.comerzzia.pos.services.promociones;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.core.basketcalculator.util.fechas.Fecha;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.pos.persistence.fidelizacion.CustomerCouponDTO;
import com.comerzzia.pos.persistence.fidelizacion.FidelizacionBean;
import com.comerzzia.pos.persistence.promociones.PromocionBean;
import com.comerzzia.pos.services.promociones.filtro.FiltroLineasPromocion;
import com.comerzzia.pos.services.promociones.tipos.DatosCuponFuturoDTO;
import com.comerzzia.pos.services.promociones.tipos.componente.CondicionPrincipalPromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.GrupoComponentePromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.IComponentePromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.ItemComponentePromoBean;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.config.SpringContext;
import com.comerzzia.pos.util.date.FechaSemanaBean;

@Component
@Scope("prototype")
public abstract class Promocion implements PromocionTipoAplicacion {

	protected Logger log = Logger.getLogger(getClass());

	public static final Long TIPO_DTO_MENOS_MARGEN = 0L;
	public static final Long TIPO_DTO_MENOS_INGRESO = 1L;
	public static final Long TIPO_DTO_A_FUTURO = 2L;
	
	public static final String FUTURE_DISCOUNT_CARD = "CARD";
	public static final String FUTURE_DISCOUNT_COUPON = "COUPON";

	protected PromocionBean promocionBean;
	protected CondicionPrincipalPromoBean condicionCabecera;
	protected DatosCuponFuturoDTO datosCupon;
	protected boolean activa;
	protected String textoPromocion;
	
	protected CustomerCouponDTO customerCoupon;
	
	protected Map<String, String> extensiones;
	
//	@Autowired
//	protected Sesion sesion;
	
	protected String storeLanguageCode;

	public Promocion() {
	}

	public void init(PromocionBean promocionBean, String storeLanguageCode) throws XMLDocumentException {
		this.promocionBean = promocionBean;
		this.storeLanguageCode = storeLanguageCode;
		
		if (getDatosPromocion() != null) {
			leerCondicionesCabecera(getDatosPromocion());
			leerDatosPromocion(getDatosPromocion());
			leerDatosCupon(getDatosPromocion());
			leerExtensiones(getDatosPromocion());
			readFutureDiscountTypeData(getDatosPromocion());
		}
	}

	public boolean isAplicable() {
		return isActiva();
	}

	public boolean isAplicable(DocumentoPromocionable<IPromocionTicket> documento) {
		boolean aplicable = isAplicable();
		if (!aplicable) {
			return false;
		}
		boolean aplicaATarifas = promocionBean.getAplicaATarifas() != null && this.promocionBean.getAplicaATarifas().equals("V") == true;
		if (!aplicaATarifas) {
			String codTarifa = documento.getCodTarifa();
			if (!promocionBean.getCodTarifa().equals(codTarifa)) {
				log.trace("isAplicable() - " + this + " - Promoción no aplicable por pertenecer a otra tarifa. Tarifa actual: " + codTarifa);
				return false;
			}
		}
		else {
			log.trace("isAplicable() - " + this + " - Promoción configurada para ser aplicable en todas las tarifas");
		}

		if (isSoloFidelizacion() && documento.getDatosFidelizado() == null) {
			log.trace("isAplicable() - " + this + " - Promoción no aplicable por ser sólo para clientes fidelizados.");
			return false;
		}

		if (condicionCabecera == null) {
			return true;
		}

		// Comprobamos si se cumplen las condiciones de cabecera
		List<IComponentePromoBean> reglas = condicionCabecera.getReglas();
		boolean aplica = false;
		if (condicionCabecera.isVacio()) {
			return true;
		}
		else {
			if (condicionCabecera.isAndNexo()) {
				aplica = true;
			}
			else if (condicionCabecera.isOrNexo()) {
				aplica = false;
			}
			else {
				log.warn("isAplicable() - La regla que se está usando en la promoción no está soportada en esta versión de JPOS.");
				return false;
			}
		}
		for (IComponentePromoBean iComponentePromoBean : reglas) {
			if (iComponentePromoBean.isTipoItemRegla()) {
				ItemComponentePromoBean regla = (ItemComponentePromoBean) iComponentePromoBean;
				boolean aplicaRegla = isAplicaRegla(regla, documento);
				if (condicionCabecera.isAndNexo()) {
					aplica &= aplicaRegla;
				}
				else {
					aplica |= aplicaRegla;
				}
				if (!aplica && condicionCabecera.isAndNexo()) {
					// Si es un nexo AND, dejamos de comprobar reglas y devolvemos false.
				    log.trace("isAplicable() - " + this + " - Promoción no aplicable porque la regla de cabecera " + regla.getItem() + " - " + regla.getOperacion() + " - " + regla.getValor() + " no se cumple y es de tipo AND");
					return false;
				}
				else if (aplica && condicionCabecera.isOrNexo()) {
					// Si es OR, devolvemos true
					return true;
				}
				else {
					// En otro caso, seguimos el bucle
				}
			}
			else if (iComponentePromoBean.isTipoGrupoReglas()) {
				GrupoComponentePromoBean grupo = (GrupoComponentePromoBean) iComponentePromoBean;
				boolean aplicaGrupo = isAplicaGrupo(grupo, documento);
				if (condicionCabecera.isAndNexo()) {
					aplica &= aplicaGrupo;
				}
				else {
					aplica |= aplicaGrupo;
				}
				if (!aplica && condicionCabecera.isAndNexo()) {
					// Si es un nexo AND, dejamos de comprobar reglas y devolvemos false.
                    log.trace("isAplicable() - " + this + " - Promoción no aplicable porque la regla de GRUPO de cabecera " + grupo.getAndNexo() + " no se cumple y es de tipo AND");

					return false;
				}
				else if (aplica && condicionCabecera.isOrNexo()) {
					// Si es OR, devolvemos true
					return true;
				}
				else {
					// En otro caso, seguimos el bucle
				}
			}
		}
		
		if (!aplica) {
            log.trace("isAplicable() - " + this + " - Promoción no aplicable porque las condiciones de cabecera no se aplican");
		}
		return aplica;
	}

	protected boolean isAplicaGrupo(GrupoComponentePromoBean grupo, DocumentoPromocionable<IPromocionTicket> documento) {
		boolean aplica = false;
		if (grupo.isAndNexo()) {
			aplica = true;
		}
		else if (grupo.isOrNexo()) {
			aplica = false;
		}
		else {
			log.warn("isAplicaGrupo() - La regla que se está usando en la promoción no está soportada en esta versión de JPOS.");
			return false;
		}
		for (GrupoComponentePromoBean subGrupo : grupo.getGrupos()) {
			boolean aplicaGrupo = isAplicaGrupo(subGrupo, documento);
			if (grupo.isAndNexo()) {
				aplica &= aplicaGrupo;
			}
			else {
				aplica |= aplicaGrupo;
			}
			if (!aplica && grupo.isAndNexo()) {
				// Si es un nexo AND, dejamos de comprobar reglas y devolvemos false.
				return false;
			}
			else if (aplica && grupo.isOrNexo()) {
				// Si es OR, devolvemos true
				return true;
			}
			else {
				// En otro caso, seguimos el bucle
			}
		}

		for (ItemComponentePromoBean regla : grupo.getReglas()) {
			boolean aplicaRegla = isAplicaRegla(regla, documento);
			if (grupo.isAndNexo()) {
				aplica &= aplicaRegla;
			}
			else {
				aplica |= aplicaRegla;
			}
			if (!aplica && grupo.isAndNexo()) {
				// Si es un nexo AND, dejamos de comprobar reglas y devolvemos false.
				return false;
			}
			else if (aplica && grupo.isOrNexo()) {
				// Si es OR, devolvemos true
				return true;
			}
			else {
				// En otro caso, seguimos el bucle
			}
		}

		return aplica;
	}

	protected boolean isAplicaRegla(ItemComponentePromoBean regla, DocumentoPromocionable<IPromocionTicket> documento) {
		switch (regla.getItem()) {
			case ItemComponentePromoBean.ITEM_DIA_SEMANA:
				// Comprobamos días de aplicación de la promoción
				FechaSemanaBean semana = new FechaSemanaBean(regla.getValor());
				Date date = new Date();
				Boolean isFecha = semana.isFecha(date);
				if (regla.isOperacionEqual()) {
					return isFecha;
				}
				else if (regla.isOperacionNoEqual()) {
					return !isFecha;
				}
				else {
					log.warn("isAplicaRegla() - La regla que se está usando en la promoción no está soportada en esta versión de JPOS.");
					return false;
				}
			case ItemComponentePromoBean.ITEM_HORA:
				Fecha actual = new Fecha();
				Fecha fecha = new Fecha(actual.getString("dd/MM/yyyy") + " " + regla.getValor(), "dd/MM/yyyy HH:mm");
				switch (regla.getOperacion()) {
					case ItemComponentePromoBean.OP_LESS:
						if (actual.despuesOrEquals(fecha)) {
							return false;
						}
						break;
					case ItemComponentePromoBean.OP_LESS_EQ:
						if (actual.despues(fecha)) {
							return false;
						}
						break;
					case ItemComponentePromoBean.OP_GREATER:
						if (actual.antesOrEquals(fecha)) {
							return false;
						}
						break;
					case ItemComponentePromoBean.OP_GREATER_EQ:
						if (actual.antes(fecha)) {
							return false;
						}
						break;

					default:
						log.warn("isAplicaRegla() - La regla que se está usando en la promoción no está soportada en esta versión de JPOS.");
						break;
				}
				break;
			case ItemComponentePromoBean.ITEM_COLECTIVO_FIDELIZADOS:
				FidelizacionBean datosFidelizado = documento.getDatosFidelizado();
				if (regla.isOperacionEqual()) {
					if (datosFidelizado == null) {
						return false;
					}
					else {
						List<String> codColectivos = datosFidelizado.getCodColectivos();
						if (codColectivos == null) {
							return false;
						}
						else {
							if (!codColectivos.contains(regla.getValor())) {
								return false;
							}
						}
					}
				}
				else if (regla.isOperacionNoEqual()) {
					if (datosFidelizado != null) {
						List<String> codColectivos = datosFidelizado.getCodColectivos();
						if (codColectivos != null) {
							if (codColectivos.contains(regla.getValor())) {
								return false;
							}
						}
					}else {
						return true;
					}
				}
				else {
					log.warn("isAplicaRegla() - La regla que se está usando en la promoción no está soportada en esta versión de JPOS.");
					return false;
				}
				break;

			case ItemComponentePromoBean.ITEM_ETIQUETAS_FIDELIZADOS:
				FidelizacionBean datosFidelizado2 = documento.getDatosFidelizado();
				if (regla.isOperacionEqual()) {
					if (datosFidelizado2 == null) {
						return false;
					}
					else {
						List<String> uidEtiquetas = datosFidelizado2.getUidEtiquetas();
						if (uidEtiquetas == null) {
							return false;
						}
						else {
							if (!uidEtiquetas.contains(regla.getValor())) {
								return false;
							}
						}
					}
				}
				else if (regla.isOperacionNoEqual()) {
					if (datosFidelizado2 != null) {
						List<String> uidEtiquetas = datosFidelizado2.getUidEtiquetas();
						if (uidEtiquetas != null) {
							if (uidEtiquetas.contains(regla.getValor())) {
								return false;
							}
						}
					}
				}
				else {
					log.warn("isAplicaRegla() - La regla que se está usando en la promoción no está soportada en esta versión de JPOS.");
					return false;
				}
				break;
			case ItemComponentePromoBean.ITEM_IMPORTE_TOTAL:
				return comprobarReglaImporteTotal(regla, documento);
		}
		return true;
	}

	protected boolean comprobarReglaImporteTotal(ItemComponentePromoBean regla, DocumentoPromocionable<IPromocionTicket> documento) {
		BigDecimal total = documento.getCabecera().getTotales().getTotalAPagar();
		BigDecimal valorRegla = regla.getValorAsBigDecimal();
		switch (regla.getOperacion()) {
			case ItemComponentePromoBean.OP_LESS:
				return BigDecimalUtil.isMenor(total, valorRegla);
			case ItemComponentePromoBean.OP_LESS_EQ:
				return BigDecimalUtil.isMenorOrIgual(total, valorRegla);
			case ItemComponentePromoBean.OP_GREATER_EQ:
				return BigDecimalUtil.isMayorOrIgual(total, valorRegla);
			case ItemComponentePromoBean.OP_GREATER:
				return BigDecimalUtil.isMayor(total, valorRegla);
		}
		return false;
	}

	public abstract void leerDatosPromocion(byte[] datosPromocion);

	public void leerCondicionesCabecera(byte[] datosPromocion) throws XMLDocumentException {
		XMLDocument xmlPromocion = new XMLDocument(datosPromocion);
		condicionCabecera = new CondicionPrincipalPromoBean(xmlPromocion.getNodo("condicionCabecera"));
	}

	protected void leerDatosCupon(byte[] datosPromocion) throws XMLDocumentException {
		XMLDocument xmlDatosCupon = new XMLDocument(datosPromocion);
		XMLDocumentNode nodoDatosCupon = xmlDatosCupon.getNodo("datosCupon", true);
		if(nodoDatosCupon != null && !nodoDatosCupon.getHijos().isEmpty()) {
			datosCupon = new DatosCuponFuturoDTO(nodoDatosCupon);
		}
	}

	protected void leerExtensiones(byte[] datosPromocion) throws XMLDocumentException {
		extensiones = new HashMap<String, String>();
		
		XMLDocument xml = new XMLDocument(datosPromocion);
		XMLDocumentNode nodoExtensiones = xml.getNodo("extensiones", true);
		if(nodoExtensiones != null && !nodoExtensiones.getHijos().isEmpty()) {
			for(XMLDocumentNode nodoExtension : nodoExtensiones.getHijos()) {
				String clave = nodoExtension.getNodo("clave").getValue();
				String valor = nodoExtension.getNodo("valor").getValue();
				extensiones.put(clave, valor);
			}
		}
    }
	
	protected void readFutureDiscountTypeData(byte[] datosPromocion) throws XMLDocumentException {
		XMLDocument xmlPromocion = new XMLDocument(datosPromocion);
		XMLDocumentNode futureDiscountTypeNode = xmlPromocion.getNodo("futureDiscountType", true);
		if(futureDiscountTypeNode!=null && StringUtils.isNotBlank(futureDiscountTypeNode.getValue())) {
			setFutureDiscountType(futureDiscountTypeNode.getValue());
		}
		
	}
	
		
	public String getExtension(String clave) {
		return extensiones.get(clave);
	}
	
	public void addExtension(String clave, String valor) {
		if(this.extensiones == null) {
			this.extensiones = new HashMap<String, String>();
		}
		this.extensiones.put(clave, valor);
	}

	public abstract boolean aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento);

	protected PromocionTicket createPromocionTicket(CustomerCouponDTO customerCoupon) {
		PromocionTicket promocionTicket = SpringContext.getBean(PromocionTicket.class, this);
		promocionTicket.setTextoPromocion(getTextoPromocion());
		if (customerCoupon == null) {
			promocionTicket.setAcceso("PROMOCION");
			promocionTicket.setCodAcceso(getIdPromocion().toString());
		}
		else {
			promocionTicket.setAcceso("CUPON");
			promocionTicket.setCodAcceso(customerCoupon.getCouponCode());
		}
		return promocionTicket;
	}

	public boolean aplicaCondicionesCabecera() {
		if (condicionCabecera == null) {
			return true;
		}

		return false;
	}

	protected FiltroLineasPromocion createFiltroLineasPromocion(DocumentoPromocionable<IPromocionTicket> documento) {
		FiltroLineasPromocion filtro = SpringContext.getBean(FiltroLineasPromocion.class);
		filtro.setDocumento(documento);
		filtro.setFiltrarPromoExclusivas(false);
		return filtro;
	}

	public CondicionPrincipalPromoBean getCondicionCabecera() {
		return condicionCabecera;
	}

	public void setCondicionCabecera(CondicionPrincipalPromoBean condicionCabecera) {
		this.condicionCabecera = condicionCabecera;
	}

	public PromocionBean getPromocionBean() {
		return promocionBean;
	}

	public String getCodTarifa() {
		return promocionBean.getCodTarifa();
	}

	public String getDescripcion() {
		return promocionBean.getDescripcion();
	}

	public Date getFechaInicio() {
		return promocionBean.getFechaInicio();
	}

	public Date getFechaFin() {
		return promocionBean.getFechaFin();
	}

	public Boolean getSoloFidelizacion() {
		return promocionBean.getSoloFidelizacion();
	}

	public Boolean isSoloFidelizacion() {
		return promocionBean.getSoloFidelizacion();
	}

	public Long getIdTipoPromocion() {
		return promocionBean.getIdTipoPromocion();
	}

	public Long getVersionTarifa() {
		return promocionBean.getVersionTarifa();
	}

	public Long getTipoDto() {
		return promocionBean.getTipoDto();
	}

	public String getCodColectivo() {
		return promocionBean.getCodColectivo();
	}

	public byte[] getDatosPromocion() {
		return promocionBean.getDatosPromocion();
	}

	public Long getIdPromocion() {
		return promocionBean.getIdPromocion();
	}
	
	public String getFutureDiscountType() {
		return promocionBean.getFutureDiscountType();
	}

	public void setFutureDiscountType(String futureDiscountType) {
		this.promocionBean.setFutureDiscountType(futureDiscountType);
	}

	public boolean isActiva() {
		return activa;
	}

	public void activar() {
		activa = true;
	}

	public void desactivar() {
		activa = false;
	}

	public boolean isDescuentoMenosMargen() {
		return getTipoDto().equals(TIPO_DTO_MENOS_MARGEN);
	}

	public boolean isDescuentoMenosIngreso() {
		return getTipoDto().equals(TIPO_DTO_MENOS_INGRESO);
	}

	public boolean isDescuentoAFuturo() {
		return getTipoDto().equals(TIPO_DTO_A_FUTURO);
	}

	public Boolean getExclusiva() {
		return promocionBean.getExclusiva();
	}

	public Boolean isExclusiva() {
		return getExclusiva();
	}

	public String getCupon() {
		return promocionBean.getCodCupon();
	}

	public boolean isActivaPorCuponFijo() {
		return isActivaPorCupon() && !isActivaPorCuponNumerado();
	}

	public boolean isActivaPorCuponFijoVariable() {
		return isActivaPorCuponFijo() && getCupon().contains("%");
	}

	public boolean isActivaPorCupon() {
		return getCupon() != null;
	}

	public boolean isActivaPorCuponNumerado() {
		return isActivaPorCupon() && getCupon().equals("#TPV#");
	}

	public String getTextoPromocion() {
		return textoPromocion;
	}

	public void setTextoPromocion(String textoPromocion) {
		this.textoPromocion = textoPromocion;
	}

	public CustomerCouponDTO getCustomerCoupon() {
		return customerCoupon;
	}

	public void setCustomerCoupon(CustomerCouponDTO customerCoupon) {
		this.customerCoupon = customerCoupon;
	}

	@Override
	public String toString() {
		return getIdPromocion() + " - (Tarifa: " + getPromocionBean().getCodTarifa() + ") " + getDescripcion();
	}

	@Override
	public boolean isAplicacionFinal() {
		return false;
	}

	@Override
	public boolean isAplicacionCabecera() {
		return false;
	}

	@Override
	public boolean isAplicacionLinea() {
		return false;
	}

	@Override
	public boolean isAplicacionPrecio() {
		return false;
	}

	@Override
	public boolean isAplicacionGeneracionCupon() {
		return false;
	}

	public DatosCuponFuturoDTO getDatosCupon() {
		return datosCupon;
	}

	public void setDatosCupon(DatosCuponFuturoDTO datosCupon) {
		this.datosCupon = datosCupon;
	}
	
	@Override
	public boolean isAplicacionCupon() {
	    return false;
	}
	
	@Override
	public boolean isImporte() {
		return false;
	}

	public String getStoreLanguageCode() {
		return storeLanguageCode;
	}

	public void setStoreLanguageCode(String storeLanguageCode) {
		this.storeLanguageCode = storeLanguageCode;
	}

}
