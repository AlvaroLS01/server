package com.comerzzia.pos.services.promociones.tipos;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNodeNotFoundException;

public class DatosCuponFuturoDTO {

	protected String titulo;

	protected String descripcion;

	protected Long idPromocionAplicacion;

	protected String couponTypeCode;

	protected String urlImage;

	protected Integer customerMaxUses;
	
	protected Map<String, String> titleTranslations = new HashMap<String, String>();
	
	protected Map<String, String> descriptionTranslations = new HashMap<String, String>();

	public DatosCuponFuturoDTO(XMLDocumentNode xmlDatosCupon) throws XMLDocumentNodeNotFoundException {
		
		for(XMLDocumentNode titleNode: xmlDatosCupon.getHijos("tituloCupon")) {
			String lang = titleNode.getAtributoValue("lang", true);
			if(StringUtils.isNotBlank(lang)) {
				titleTranslations.put(lang, titleNode.getValue());
			}else {
				titulo = xmlDatosCupon.getNodo("tituloCupon").getValue();
			}
		}
		
		for(XMLDocumentNode descriptionNode: xmlDatosCupon.getHijos("descripcionCupon")) {
			String lang = descriptionNode.getAtributoValue("lang", true);
			if(StringUtils.isNotBlank(lang)) {
				descriptionTranslations.put(lang, descriptionNode.getValue());
			}else {
				descripcion = xmlDatosCupon.getNodo("descripcionCupon").getValue();
			}
		}

		XMLDocumentNode nodoIdPromoAplicar = xmlDatosCupon.getNodo("idPromoAplicar", true);
		if (nodoIdPromoAplicar != null) {
			idPromocionAplicacion = nodoIdPromoAplicar.getValueAsLong();
		}

		XMLDocumentNode nodoCouponTypeCode = xmlDatosCupon.getNodo("couponTypeCode", true);
		if (nodoCouponTypeCode != null) {
			couponTypeCode = nodoCouponTypeCode.getValue();
		}
		else {
			couponTypeCode = "DEFAULT";
		}

		XMLDocumentNode nodoUrlImage = xmlDatosCupon.getNodo("urlImage", true);
		if (nodoUrlImage != null) {
			urlImage = nodoUrlImage.getValue();
		}

		XMLDocumentNode nodoCustomerMaxUses = xmlDatosCupon.getNodo("customerMaxUses", true);
		if (nodoCustomerMaxUses != null) {
			customerMaxUses = nodoCustomerMaxUses.getValueAsInteger();
		}
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdPromocionAplicacion() {
		return idPromocionAplicacion;
	}

	public void setIdPromocionAplicacion(Long idPromocionAplicacion) {
		this.idPromocionAplicacion = idPromocionAplicacion;
	}

	public String getCouponTypeCode() {
		return couponTypeCode;
	}

	public void setCouponTypeCode(String couponTypeCode) {
		this.couponTypeCode = couponTypeCode;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public Integer getCustomerMaxUses() {
		return customerMaxUses;
	}

	public void setCustomerMaxUses(Integer customerMaxUses) {
		this.customerMaxUses = customerMaxUses;
	}

	public Map<String, String> getTitleTranslations() {
		return titleTranslations;
	}

	public void setTitleTranslations(Map<String, String> titleTranslations) {
		this.titleTranslations = titleTranslations;
	}

	public Map<String, String> getDescriptionTranslations() {
		return descriptionTranslations;
	}

	public void setDescriptionTranslations(Map<String, String> descriptionTranslations) {
		this.descriptionTranslations = descriptionTranslations;
	}

}
