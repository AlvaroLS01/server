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

package com.comerzzia.pos.services.core.sesion;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.core.usuarios.UsuarioBean;
import com.comerzzia.pos.services.core.perfiles.PerfilException;
import com.comerzzia.pos.services.core.perfiles.ServicioPerfiles;
import com.comerzzia.pos.services.core.usuarios.UsuarioInvalidLoginException;
import com.comerzzia.pos.services.core.usuarios.UsuariosService;
import com.comerzzia.pos.services.core.usuarios.UsuariosServiceException;
import com.comerzzia.pos.services.core.variables.VariablesServices;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SesionUsuario {
    protected final Logger log = Logger.getLogger(getClass());
    
    protected UsuarioBean usuario;
    protected Boolean superAdministrador = false;    
    
    @Autowired
    protected VariablesServices variablesServices;
    
    @Autowired
    protected ServicioPerfiles servicioPerfiles;
    
    @Autowired
    protected UsuariosService usuariosService;
    
    protected Sesion sesion;
    
    public void init(Sesion sesion) throws SesionInitException, UsuarioInvalidLoginException {
       this.sesion = sesion;
       
       if (sesion.getUser() == null) return;
       
       try {
           UsuarioBean usuarioBean = usuariosService.consultarUsuario(sesion, sesion.getUser());
           this.usuario = usuarioBean;
           setIsSuperAdministrador();
       }
       catch (UsuariosServiceException ex) {
           log.error("init() -  Error iniciando sesión de usuario " + usuario + " - " + ex.getMessageI18N());
           throw new SesionInitException(ex.getMessageI18N(), ex);
       }
       catch (Exception ex) {
           log.error("init() - Error inicializando sesión de usuario: " + usuario + " - " + ex.getMessage(), ex);
           throw new SesionInitException(ex);
       }
   }

    public void init(String usuario, String password) throws SesionInitException, UsuarioInvalidLoginException {       
        try {
            UsuarioBean usuarioBean = usuariosService.login(sesion, usuario, password);
            this.usuario = usuarioBean;
            setIsSuperAdministrador();
        }
        catch (UsuarioInvalidLoginException ex) {
            throw ex;
        }
        catch (UsuariosServiceException ex) {
            log.error("init() -  Error iniciando sesión de usuario " + usuario + " - " + ex.getMessageI18N());
            throw new SesionInitException(ex.getMessageI18N(), ex);
        }
        catch (Exception ex) {
            log.error("init() - Error inicializando sesión de usuario: " + usuario + " - " + ex.getMessage(), ex);
            throw new SesionInitException(ex);
        }               
    }

    public UsuarioBean getUsuario() {
        return usuario;
    }

    
    public Boolean isSuperAdministrador(Long idUsuario) {
    	return true;
//    	try {
//			boolean isAdmin = false;
//			ParametrosBuscarPerfilesBean param = new ParametrosBuscarPerfilesBean();
//			param.setTamañoPagina(Integer.MAX_VALUE);
//			param.setNumPagina(1);
//			param.setIdUsuario(idUsuario);
//			@SuppressWarnings("unchecked")
//			List<PerfilBean> perfiles = (List<PerfilBean>) servicioPerfiles.consultar(sesion, param).getPagina();
//			for (PerfilBean perfilBean : perfiles) {
//				if (perfilBean.getIdPerfil().equals(0l)) {
//					isAdmin = true;
//					break;
//				}
//			}
//			return isAdmin;
//		} catch (PerfilException e) {
//			throw new RuntimeException(e);
//		}
    }

	public Boolean isSuperAdministrador() {
		return superAdministrador;
	}

	public void setIsSuperAdministrador() throws PerfilException {
		this.superAdministrador = true;
//		this.superAdministrador = false;
//		ParametrosBuscarPerfilesBean param = new ParametrosBuscarPerfilesBean();
//        param.setTamañoPagina(Integer.MAX_VALUE);
//        param.setNumPagina(1);
//        param.setIdUsuario(usuario.getIdUsuario());
//		@SuppressWarnings("unchecked")
//		List<PerfilBean> perfiles = (List<PerfilBean>) servicioPerfiles.consultar(sesion, param).getPagina();
//		for (PerfilBean perfilBean : perfiles) {
//			if(perfilBean.getIdPerfil().equals(0l)){
//				this.superAdministrador = true;
//				break;
//			}
//		}
	}

	public void clear() {
		usuario = null;
    }			
}
