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
package com.comerzzia.core.basketcalculator.servicios.sesion;

import java.util.UUID;

import com.comerzzia.core.basketcalculator.model.usuarios.UsuarioBean;
import com.comerzzia.core.basketcalculator.util.i18n.Translation;


/**
 * Datos de una sesión
 * 
 */
public class DatosSesionBean extends BasicSessionBean {	
	private String uidSesion;
	
	private UsuarioBean usuario;	
	    	
	private boolean desglose1Activo;
	
	private boolean desglose2Activo;
	
	private String tituloDesglose1;
	
	private String tituloDesglose2;

	public DatosSesionBean() {
		uidSesion = UUID.randomUUID().toString();
	}
	
	public DatosSesionBean(String uidSesion) {
		this.uidSesion = uidSesion;
	}
	
	public UsuarioBean getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
		this.setUser(usuario.getUsuario());
		this.setUserId(usuario.getIdUsuario());
	}

//
//	/**
//	 * Establece el valor del atributo variables
//	 * @param variables Mapa con las variables que se van a guardar en sesión
//	 */
//	public void setVariables(Map<String, String> variables) {
//		this.variables = variables;
//	}
//
//	/**
//	 * Obtiene el valor del atributo variables
//	 * @return Mapa con las variables guardadas en sesión
//	 */
//	public Map<String, String> getVariables() {
//		return variables;
//	}
//	
//	/**
//     * Guarda el valor de una variable guardada en sesión
//     * @param variable Nombre de la variable
//     * @param valor Valor de la variable
//     */
//    public void setVariable(String variable, String valor) {
//        variables.put(variable, valor);
//    }
//    
//    /**
//     * Obtiene el valor de una variable guardada en sesión
//     * @param variable Nombre de la variable
//     * @return Valor de la variable
//     */
//    public String getVariable(String variable) {
//        return (String) variables.get(variable);
//    }
//	
//	
//	/**
//	 * Obtiene los permisos que el usuario de la sesión tiene sobre la acción indicada
//	 * @param accion
//	 * @return
//	 */
//	public PermisosEfectivosAccionBean getPermisos(AccionBean accion) {
//		return permisos.getPermisos(accion);
//	}
//	
//	
//	/**
//	 * Obtiene los permisos que el usuario de la sesión tiene sobre la acción indicada
//	 * Se consulta la caché de permisos de la sesión, y si no se encuentra en ella
//	 * se obtienen y se cachean para sucesivas consultas
//	 * @param accion
//	 * @return
//	 */
//	public PermisosEfectivosAccionBean getPermisosCache(AccionBean accion) {
//		return permisos.getPermisosCache(accion);
//	}

	@Override
	public void setUidInstancia(String uidInstancia) {
		super.setUidInstancia(uidInstancia);
	}
	
	@Override
	public void setUidActividad(String uidActividad) {
		super.setUidActividad(uidActividad);
//		
//		this.desglose1Activo = false;
//		this.desglose2Activo = false;
//		try {
//			String desglose1Titulo = ServicioVariablesImpl.get().consultarValorCache(this, "ARTICULOS.DESGLOSE1_TITULO");
//			if(desglose1Titulo != null && !desglose1Titulo.isEmpty()) {
//				this.desglose1Activo = true;
//				this.tituloDesglose1 = desglose1Titulo;
//			}
//		
//			String desglose2Titulo = ServicioVariablesImpl.get().consultarValorCache(this, "ARTICULOS.DESGLOSE2_TITULO");
//			if(desglose2Titulo != null && !desglose2Titulo.isEmpty()) {
//				this.desglose2Activo = true;
//				this.tituloDesglose2 = desglose2Titulo;
//			}
//		} catch (Exception e) {
//			
//		}
	}
	
	
	@Override
	public void setTranslation(Translation translation) {
		super.setTranslation(translation);
		
		if (translation != null) {
		   super.setLocale(translation.getLocale());
		}
	}

	public String getUidSesion() {
		return uidSesion;
	}
	

	public boolean isDesglose1Activo() {
		return desglose1Activo;
	}

	public void setDesglose1Activo(boolean desglose1Activo) {
		this.desglose1Activo = desglose1Activo;
	}

	public boolean isDesglose2Activo() {
		return desglose2Activo;
	}

	public void setDesglose2Activo(boolean desglose2Activo) {
		this.desglose2Activo = desglose2Activo;
	}

	public String getTituloDesglose1() {
		return tituloDesglose1;
	}

	public void setTituloDesglose1(String tituloDesglose1) {
		this.tituloDesglose1 = tituloDesglose1;
	}

	public String getTituloDesglose2() {
		return tituloDesglose2;
	}

	public void setTituloDesglose2(String tituloDesglose2) {
		this.tituloDesglose2 = tituloDesglose2;
	}
}

