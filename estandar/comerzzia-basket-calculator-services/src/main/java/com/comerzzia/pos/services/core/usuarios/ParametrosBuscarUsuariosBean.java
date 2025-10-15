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
package com.comerzzia.pos.services.core.usuarios;

public class ParametrosBuscarUsuariosBean  {

	/**
	 * serialVersionUID
	 */
	protected static final long serialVersionUID = 5809573177612695732L;

	protected String usuario = "";
	protected String desUsuario = "";
	protected String activo = "";
	
	/**
	 * Constructor
	 */
	public ParametrosBuscarUsuariosBean() {
		
	}


	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}


	/**
	 * @param usuario codusuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = (usuario != null) ? usuario.toUpperCase(): "";
	}


	/**
	 * @return the desusuario
	 */
	public String getDesUsuario() {
		return desUsuario;
	}


	/**
        * @param desUsuario
	 */
	public void setDesUsuario(String desUsuario) {
		this.desUsuario = (desUsuario != null) ? desUsuario : "";
	}


	public String getActivo() {
		return activo;
	}


	public void setActivo(String activo) {
		this.activo = (activo != null) ? activo : "";
	}
}
