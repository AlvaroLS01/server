package com.comerzzia.pos.services.promociones.tipos.especificos;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNodeNotFoundException;
import com.comerzzia.pos.persistence.fidelizacion.CustomerCouponDTO;
import com.comerzzia.pos.services.cupones.CuponesServices;
import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;
import com.comerzzia.pos.services.promociones.filtro.FiltroLineasPromocion;
import com.comerzzia.pos.services.promociones.filtro.LineasAplicablesPromoBean;
import com.comerzzia.pos.services.promociones.tipos.PromocionCabecera;
import com.comerzzia.pos.services.promociones.tipos.componente.CondicionPrincipalPromoBean;
import com.comerzzia.pos.services.promociones.tipos.componente.GrupoComponentePromoBean;
import com.comerzzia.pos.services.ticket.cupones.CuponAplicadoTicket;
import com.comerzzia.pos.services.ticket.lineas.LineaTicket;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;

@Component
@Scope("prototype")
public class PromocionAplicacionCupon extends PromocionCabecera {

	protected static final Logger log = Logger.getLogger(PromocionAplicacionCupon.class);

	protected CondicionPrincipalPromoBean condiciones;
	protected GrupoComponentePromoBean aplicacion;
	protected GrupoComponentePromoBean lineasAplicacion;
	protected String tipoFiltro;
	
	@Autowired
	private CuponesServices cuponesServices;

	@Override
	public boolean aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento) {
		if(StringUtils.isBlank(getCupon())) {
			return false;
		}
		log.trace("aplicarPromocion() - " + this);
		
		List<CustomerCouponDTO> coupons = documento.getCouponsAppliyingPromotion(getIdPromocion());
		if(coupons != null) {
			int i = 0;
			for (CustomerCouponDTO coupon : coupons) {
				log.debug("aplicarPromocion() - Aplicando cupón " + i + " de " + coupons.size() + ": " + coupon.getCouponCode());
				this.setCustomerCoupon(coupon);
				// Obtenemos las líneas aplicables según el filtro configurado
				FiltroLineasPromocion filtroLineas = createFiltroLineasPromocion(documento);
				filtroLineas.setFiltrarPromoExclusivas(false); // Da igual que las líneas tengan una promoción exclusiva
				LineasAplicablesPromoBean lineasAplicables = filtroLineas.getNumCombosCondicion(condiciones);
				if (lineasAplicables.isEmpty()) {
					log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar por no existir líneas aplicables en el documento .");
					continue;
				}

				lineasAplicables = filtroLineas.getLineasAplicablesGrupo(lineasAplicacion);
				if (lineasAplicables.isEmpty()) {
					log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar por no existir líneas aplicables en el documento .");
					continue;
				}

				// Obtenemos el importe de descuento
				BigDecimal valorConfigurado = customerCoupon.getBalance();
				if(valorConfigurado == null) {
					valorConfigurado = cuponesServices.getImporteDescuentoCupon(customerCoupon.getCouponCode());
				}

				if (valorConfigurado == null) {
					log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar porque no se ha podido leer el importe del cupón.");
					continue;
				}
				BigDecimal importeLineasConDto = lineasAplicables.getImporteLineasConDto();
				BigDecimal totalesPromocionesCabecera = BigDecimal.ZERO;
				for (LineaDocumentoPromocionable linea : documento.getLineasDocumentoPromocionable()) {
					totalesPromocionesCabecera = totalesPromocionesCabecera.add(((LineaTicket) linea).getImporteTotalPromocionesMenosIngreso());
				}

				// Aplicamos la promoción sobre el ticket
				PromocionTicket promocionTicket = createPromocionTicket(customerCoupon);
				BigDecimal importePromocion = BigDecimal.ZERO;

				BigDecimal importeDespuesPromociones = importeLineasConDto.subtract(totalesPromocionesCabecera);
				if (BigDecimalUtil.isMayorOrIgual(valorConfigurado, importeDespuesPromociones)) {
					importePromocion = importeDespuesPromociones;
				}
				else {
					importePromocion = valorConfigurado;
				}

				promocionTicket.setImporteTotalAhorro(importePromocion);
				documento.addPromocion(promocionTicket);

				BigDecimal porcentajeDescuento = BigDecimal.ZERO;
				importeDespuesPromociones = importeLineasConDto.subtract(totalesPromocionesCabecera);
				if (!BigDecimalUtil.isIgualACero(importePromocion)) {
					porcentajeDescuento = BigDecimalUtil.getTantoPorCiento(importeDespuesPromociones, importePromocion);
				}

				lineasAplicables.aplicaDescuentoPorcentajeGeneral(promocionTicket, porcentajeDescuento);

				CuponAplicadoTicket cupon = documento.getCuponAplicado(customerCoupon.getCouponCode());
				if (cupon != null) {
					cupon.setTextoPromocion(promocionTicket.getTextoPromocion());
				}
				i++;
			}
			
		}
		
		

		return true;
	}

	@Override
	public void leerDatosPromocion(byte[] datosPromocion) {
		try {
			XMLDocument xmlPromocion = new XMLDocument(datosPromocion);
			condiciones = new CondicionPrincipalPromoBean(xmlPromocion.getNodo("condicionLineas"));
			aplicacion = new GrupoComponentePromoBean(xmlPromocion.getNodo("aplicacion"));
			lineasAplicacion = new GrupoComponentePromoBean(xmlPromocion.getNodo("lineasAplicacion"));

			Collections.sort(aplicacion.getReglas());
            
            String storeLanguageCode = getStoreLanguageCode();
            
            String textPromo = null;
            String textPromoDefault = null;
        	List<XMLDocumentNode> textPromoNodes = xmlPromocion.getNodos("textoPromocion");
        	for(XMLDocumentNode textPromoNode : textPromoNodes) {
        		String textPromoLanguageCode = textPromoNode.getAtributoValue("lang", true);
        		if(StringUtils.isNotBlank(textPromoLanguageCode)){
        			if(textPromoLanguageCode.equals(storeLanguageCode)) {
        				textPromo = textPromoNode.getValue();
        				break;
        			}
        		}else {
        			textPromoDefault = textPromoNode.getValue();
        		}
        	}

        	if(StringUtils.isBlank(textPromo)) {
        		textPromo = textPromoDefault;
        	}

            setTextoPromocion(textPromo);
			try {
				tipoFiltro = xmlPromocion.getNodo("tipoFiltro").getValue();
			}
			catch (XMLDocumentNodeNotFoundException ignore) {
			}
			if (tipoFiltro == null) {
				tipoFiltro = "Importe";
			}
		}
		catch (XMLDocumentException e) {
			log.error("Error al leer los datos de la promoción de tipo descuento  cabecera: " + e.getMessage(), e);
		}
	}

	public boolean isImporte() {
		return tipoFiltro.equals("Importe");
	}

	public boolean isPorcentaje() {
		return tipoFiltro.equals("Porcentaje");
	}

	public GrupoComponentePromoBean getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(GrupoComponentePromoBean aplicacion) {
		this.aplicacion = aplicacion;
	}

	@Override
	public boolean isAplicacionCupon() {
		return true;
	}

}