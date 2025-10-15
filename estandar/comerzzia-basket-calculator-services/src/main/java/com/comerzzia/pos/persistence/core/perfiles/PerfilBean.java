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
package com.comerzzia.pos.persistence.core.perfiles;

import com.comerzzia.core.basketcalculator.util.base.MantenimientoBean;




public class PerfilBean extends MantenimientoBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8725175787378855728L;
	// Perfiles
	public static final long PERFIL_ADMINISTRADOR = 0;
	public static final long PERFIL_USUARIO = 1;
	// Accesos
	public static final int ACCESO_ADMINISTRADOR = 2;
	public static final int ACCESO_USUARIO = 1;

	private Long idPerfil;
	private String desPerfil;

	protected void initNuevoBean() {
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getDesPerfil() {
		return desPerfil;
	}

	public void setDesPerfil(String desPerfil) {
		this.desPerfil = desPerfil;
	}

}
