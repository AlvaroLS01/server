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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.core.basketcalculator.servicios.sesion.IDatosSesion;
import com.comerzzia.pos.persistence.core.usuarios.POSUsuarioMapper;
import com.comerzzia.pos.persistence.core.usuarios.UsuarioBean;
import com.comerzzia.pos.persistence.core.usuarios.UsuarioExample;
import com.comerzzia.pos.persistence.core.usuarios.UsuarioExample.Criteria;
import com.comerzzia.pos.persistence.core.usuarios.almacenes.AlmacenUsuarioExample;
import com.comerzzia.pos.persistence.core.usuarios.almacenes.AlmacenUsuarioKey;
import com.comerzzia.pos.persistence.core.usuarios.almacenes.POSAlmacenUsuarioMapper;
import com.comerzzia.pos.services.core.sesion.Sesion;

@Service
public class UsuariosService {

    protected static Logger log = Logger.getLogger(UsuariosService.class);
  
    @Autowired
    Sesion sesion;
    
    @Autowired
    protected POSUsuarioMapper usuarioMapper;
    
    @Autowired
    protected POSAlmacenUsuarioMapper almacenUsuarioMapper;
    
    /**
     * Realiza login sobre la tabla de usuarios. Si el login es correcto, introduce en sesión el UsuarioBean
     * correspondiente. Si es incorrecto, lanza una excepción. El usuario debe de estar activo.
     *
     * @param usuario :: Login del usuario
     * @param password :: Password en claro del usuario (sin encriptar en md5)
     * @return UsuarioBean del login
     * @throws UsuarioInvalidLoginException :: Lanzada si el login es incorrecto
     * @throws com.comerzzia.pos.services.core.usuarios.UsuariosServiceException
     */
    public UsuarioBean login(IDatosSesion datosSesion, String usuario, String password) throws UsuarioInvalidLoginException, UsuariosServiceException {
        try {
            log.debug("login() - Realizando login con usuario:  " + usuario);
            UsuarioBean usuarioBean = consultarUsuario(datosSesion, usuario);
            return usuarioBean;
        }
        catch (UsuariosServiceException e) {
            throw e;
        }
        catch (UsuarioNotFoundException e) {
            log.info("login() - Usuario no encontrado. ");
            throw new UsuarioInvalidLoginException(e);
        }
        catch (Exception e) {
            String msg = "Se ha producido un error realizando login de usuario: " + usuario + " : " + e.getMessage();
            log.error("login() - " + msg, e);
            throw new UsuariosServiceException("error.service.core.usuarios.service.login", e);
        }
    }

    /**
     * Realiza consulta sobre la tabla de usuarios. Si no se encuentra devuelve una excepción.
     * El usuario debe de estar activo.
     *
     * @param usuario :: Login del usuario
     * @return UsuarioBean
     * @throws com.comerzzia.pos.services.core.usuarios.UsuarioNotFoundException (Si no se encuentra el usuario)
     * @throws com.comerzzia.pos.services.core.usuarios.UsuariosServiceException
     */
    public UsuarioBean consultarUsuario(IDatosSesion datosSesion, String usuario) throws UsuarioNotFoundException, UsuariosServiceException {
        try {
            if (usuario == null) {
                log.error("consultarUsuario() - Cadena de entrada de usuario nula ");
                throw new UsuarioNotFoundException();
            }

            String uidInstancia = datosSesion.getUidInstancia();
            UsuarioExample usuarioExample = new UsuarioExample();
            usuarioExample.or().andUsuarioEqualTo(usuario.toUpperCase()).andUidInstanciaEqualTo(uidInstancia).andActivoEqualTo(Boolean.TRUE);
            log.debug("consultarUsuario() - Consultando usuario: " + usuario);
            List<UsuarioBean> usuarios = usuarioMapper.selectByExample(usuarioExample);
            if (usuarios != null && !usuarios.isEmpty()) {
                return usuarios.get(0);
            }
            else {
                log.info("consultarUsuario() - Usuario no encontrado. ");
                throw new UsuarioNotFoundException();
            }
        }
        catch (UsuarioNotFoundException e) {
            log.warn("El usuario no existe : " +usuario);
            throw e;
        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando usuario:  " + usuario + ": " + e.getMessage();
            log.error("consultarUsuario() - " + msg);
            throw new UsuariosServiceException(e);
        }     
    }


    public List<UsuarioBean> consultarUsuarios(ParametrosBuscarUsuariosBean param) throws UsuariosServiceException {
        try {
            String uidInstancia = sesion.getUidInstancia();
            UsuarioExample usuarioExample = new UsuarioExample();

            Criteria or = usuarioExample.or();
			if (StringUtils.isNotBlank(param.getDesUsuario()) && StringUtils.isNotBlank(param.getUsuario())) {
                or.andUsuarioLikeInsensitive(param.getUsuario() + "%").andDesusuarioLikeInsensitive("%" + param.getDesUsuario() + "%");
            }
            else if (StringUtils.isNotBlank(param.getUsuario())) {
                or.andUsuarioLikeInsensitive(param.getUsuario() + "%");
            }
            else if (StringUtils.isNotBlank(param.getDesUsuario())) {
                or.andDesusuarioLikeInsensitive("%" + param.getDesUsuario() + "%");
            }
			or.andActivoEqualTo(Boolean.TRUE).andUidInstanciaEqualTo(uidInstancia);

            log.debug("consultarUsuarios() - Consultando usuarios.");
            List<UsuarioBean> usuarios = usuarioMapper.selectByExample(usuarioExample);

            log.debug("consultarUsuarios() - Filtrando los usuarios que son de esta tienda.");
            List<UsuarioBean> usuariosFiltrados = filtrarUsuariosTienda(usuarios);

            return usuariosFiltrados;

        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando usuarios: " + e.getMessage();
            log.error("consultarUsuarios() - " + msg);
            throw new UsuariosServiceException(e);
        }
    }

	protected List<UsuarioBean> filtrarUsuariosTienda(List<UsuarioBean> usuarios) {
	    AlmacenUsuarioExample exampleAlmacenes = new AlmacenUsuarioExample();
	    exampleAlmacenes.or().andUidActividadEqualTo(sesion.getUidActividad()).andUidInstanciaEqualTo(sesion.getUidInstancia())
	            .andCodalmEqualTo(sesion.getAplicacion().getCodAlmacen());
	    List<AlmacenUsuarioKey> almacenesUsuario = almacenUsuarioMapper.selectByExample(exampleAlmacenes);
	    
	    List<UsuarioBean> usuariosFiltrados = new ArrayList<UsuarioBean>();
	    for(UsuarioBean usuario : usuarios) {
	    	for(AlmacenUsuarioKey almacenUsuario : almacenesUsuario) {
	    		if(usuario.getIdUsuario().equals(almacenUsuario.getIdUsuario())) {
	    			usuariosFiltrados.add(usuario);
	    		}
	    	}
	    }
	    return usuariosFiltrados;
    }
}
