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

package com.comerzzia.pos.persistence.promociones.tipos;

import java.util.HashMap;
import java.util.Map;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;

public class PromocionTipoBean {

	private Long idTipoPromocion;
	
	private String desTipoPromocion;
	
	private byte[] configuracion;

	private Map<String, String> configuracionMap;
	
	public void parseConfiguracion() throws XMLDocumentException {
		configuracionMap = new HashMap<>();
		try {
			if (configuracion != null) {
				XMLDocument xml = new XMLDocument(configuracion);
				for (XMLDocumentNode element : xml.getRoot().getHijos()) {
					configuracionMap.put(element.getNombre(), element.getValue());
				}
			}
		} catch(XMLDocumentException e) {
			throw e;
		} catch(Exception e) {
			throw new XMLDocumentException(e.getMessage(), e);
		}
	}
	
	public Long getIdTipoPromocion() {
		return idTipoPromocion;
	}

	public void setIdTipoPromocion(Long idTipoPromocion) {
		this.idTipoPromocion = idTipoPromocion;
	}

	public String getDesTipoPromocion() {
		return desTipoPromocion;
	}

	public void setDesTipoPromocion(String desTipoPromocion) {
		this.desTipoPromocion = desTipoPromocion;
	}

	public byte[] getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(byte[] configuracion) {
		this.configuracion = configuracion;
	}

	public Map<String, String> getConfiguracionMap() {
		return configuracionMap;
	}

	public void setConfiguracionMap(Map<String, String> configuracionMap) {
		this.configuracionMap = configuracionMap;
	}
	
	public String getManejador() {
		String manejador = null;
		if (configuracionMap != null) {
			manejador = configuracionMap.get("Manejador");
		} 
		return manejador;
	}
	
	public String getTipoAplicacion() {
		String tipoAplicacion = null;
		if (configuracionMap != null) {
			tipoAplicacion = configuracionMap.get("TipoAplicacion");
		} 
		return tipoAplicacion;
	}
	
	public String getCodMedioPagoMenosIngreso() {
		String tipoAplicacion = null;
		if (configuracionMap != null) {
			tipoAplicacion = configuracionMap.get("CodMedioPagoMenosIngreso");
		} 
		return tipoAplicacion;
	}
	
}
