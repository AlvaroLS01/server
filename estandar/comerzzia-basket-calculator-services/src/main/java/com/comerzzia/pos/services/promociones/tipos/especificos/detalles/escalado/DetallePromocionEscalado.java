package com.comerzzia.pos.services.promociones.tipos.especificos.detalles.escalado;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.pos.persistence.promociones.detalle.PromocionDetalleBean;
import com.comerzzia.pos.services.promociones.Promocion;
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.DetallePromocion;
import com.comerzzia.pos.util.format.FormatUtil;

public class DetallePromocionEscalado extends DetallePromocion {

	private PromocionDetalleBean detalleBean;
	private boolean xmlLeido;

	private List<TramoEscalado> tramos;

	private String tipoDto;

	public DetallePromocionEscalado(Promocion promocion, PromocionDetalleBean detalle) throws XMLDocumentException {
		super(promocion, detalle);

		this.detalleBean = detalle;
	}

	public void leerXml() throws XMLDocumentException {
		XMLDocument xml = new XMLDocument(detalle.getDatosPromocion());

		XMLDocumentNode nodoTramos = xml.getNodo("tramos");
		for (XMLDocumentNode nodoTramo : nodoTramos.getHijos()) {
			XMLDocumentNode nodoCantidadDesde = nodoTramo.getNodo("cantidadDesde", false);
			XMLDocumentNode nodoCantidadHasta = nodoTramo.getNodo("cantidadHasta", false);
			
			BigDecimal cantidadDesde = BigDecimal.ZERO;
			BigDecimal cantidadHasta = BigDecimal.ZERO;
			BigDecimal valor = BigDecimal.ZERO;
			try {
				cantidadDesde = nodoCantidadDesde.getValueAsBigDecimal();
				cantidadHasta = nodoCantidadHasta.getValueAsBigDecimal();
				valor = nodoTramo.getNodo("valor").getValueAsBigDecimal();
			}catch(NumberFormatException e) {
				cantidadDesde = FormatUtil.getInstance().desformateaImporte(nodoCantidadDesde.getValue());
				cantidadHasta = FormatUtil.getInstance().desformateaImporte(nodoCantidadHasta.getValue());
				valor = FormatUtil.getInstance().desformateaImporte(nodoTramo.getNodo("valor").getValue());
			}

			TramoEscalado tramoEscalado = new TramoEscalado();
			tramoEscalado.setCantidadDesde(cantidadDesde);
			tramoEscalado.setCantidadHasta(cantidadHasta);
			tramoEscalado.setValor(valor);
			addTramo(tramoEscalado);
		}

		tipoDto = xml.getNodo("tipoFiltro").getValue();
		
		String storeLanguageCode = promocion.getStoreLanguageCode();
        for(XMLDocumentNode textPromoNode : xml.getNodos("textoPromocion")) {
        	String languageCode = textPromoNode.getAtributoValue("lang", true);
        	if(StringUtils.isNotBlank(languageCode) && languageCode.equals(storeLanguageCode)) {
        		this.detalle.setTextoPromocion(textPromoNode.getValue());
        		break;
        	}
        }

		xmlLeido = true;
	}

	public PromocionDetalleBean getDetalleBean() {
		return detalleBean;
	}

	public void setDetalleBean(PromocionDetalleBean detalleBean) {
		this.detalleBean = detalleBean;
	}

	public boolean isXmlLeido() {
		return xmlLeido;
	}

	public void setXmlLeido(boolean xmlLeido) {
		this.xmlLeido = xmlLeido;
	}

	public List<TramoEscalado> getTramos() {
		return tramos;
	}

	public void setTramos(List<TramoEscalado> tramos) {
		this.tramos = tramos;
	}

	public void addTramo(TramoEscalado tramo) {
		if (this.tramos == null) {
			this.tramos = new ArrayList<TramoEscalado>();
		}
		this.tramos.add(tramo);
	}

	public String getTipoDto() {
		return tipoDto;
	}

	public void setTipoDto(String tipoDto) {
		this.tipoDto = tipoDto;
	}

	public boolean isTipoDtoNuevoPrecio() {
	    return tipoDto.equals("Precio");
    }
	
	public boolean isTipoDtoDescuento() {		
		return tipoDto.equals("Descuento");
	}
	
	public boolean isTipoDtoImporteDto() {
		return tipoDto.equals("Importe");		
	}

}
