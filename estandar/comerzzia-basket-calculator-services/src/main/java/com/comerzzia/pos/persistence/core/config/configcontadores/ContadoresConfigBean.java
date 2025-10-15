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
package com.comerzzia.pos.persistence.core.config.configcontadores;



public class ContadoresConfigBean {

	private static final long serialVersionUID = -962588072341150852L;

	private String idContador;
	private boolean usaCodEmp;
	private boolean usaCodSerie;
	private boolean usaPeriodo;
	private String descripcion;

	
	protected void initNuevoBean() {

	}

	public String getIdContador() {
		return idContador;
	}

	public void setIdContador(String idContador) {
		this.idContador = idContador.toUpperCase();
	}

	public String getUsaCodEmp() {
		return (usaCodEmp) ? "S" : "N";
	}

	public void setUsaCodEmp(String usaCodEmp) {
		this.usaCodEmp = usaCodEmp.equals("S");
	}

	public void setUsaCodEmp(boolean usaCodEmp) {
		this.usaCodEmp = usaCodEmp;
	}

	public boolean isUsaCodEmp() {
		return usaCodEmp;
	}

	public String getUsaCodSerie() {
		return (usaCodSerie) ? "S" : "N";
	}

	public void setUsaCodSerie(String usaCodSerie) {
		this.usaCodSerie = usaCodSerie.equals("S");
	}

	public void setUsaCodSerie(boolean usaCodSerie) {
		this.usaCodSerie = usaCodSerie;
	}

	public boolean isUsaCodSerie() {
		return usaCodSerie;
	}

	public String getUsaPeriodo() {
		return (usaPeriodo) ? "S" : "N";
	}

	public void setUsaPeriodo(String usaPeriodo) {
		this.usaPeriodo = usaPeriodo.equals("S");
	}

	public void setUsaPeriodo(boolean usaPeriodo) {
		this.usaPeriodo = usaPeriodo;
	}

	public boolean isUsaPeriodo() {
		return usaPeriodo;
	}

	public String getStrUsaCodEmp() {
		String res = "No";
		if (usaCodEmp) {
			res = "Sí";
		}
		return res;
	}

	public String getStrUsaCodSerie() {
		String res = "No";
		if (usaCodSerie) {
			res = "Sí";
		}
		return res;
	}

	public String getStrUsaPeriodo() {
		String res = "No";
		if (usaPeriodo) {
			res = "Sí";
		}
		return res;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
