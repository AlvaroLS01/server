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
package com.comerzzia.core.basketcalculator.util.base;

import java.io.Serializable;

public class ParametrosBuscarBean implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2613843971878447723L;

	private int tamañoPagina = 10;
	private int numPagina = 0;
	private String orden = "";
	
	/**
	 * @return the tamañoPagina
	 */
	public int getTamañoPagina() {
		return tamañoPagina;
	}
	
	/**
	 * @param tamañoPagina the tamañoPagina to set
	 */
	public void setTamañoPagina(int tamañoPagina) {
		if (tamañoPagina > 0){
			this.tamañoPagina = tamañoPagina;
		}
	}
	
	/**
	 * @return the numPagina
	 */
	public int getNumPagina() {
		return numPagina;
	}
	
	/**
	 * @param numPagina the numPagina to set
	 */
	public void setNumPagina(int numPagina) {
		this.numPagina = numPagina;
	}
	
	/**
	 * @return the orden
	 */
	public String getOrden() {
		return orden;
	}
	
	/**
	 * @param orden the orden to set
	 */
	public void setOrden(String orden) {
		this.orden = (orden != null) ? orden : "";
	}
}
