package com.comerzzia.api.v2.facturacionmagento.services.usuarios;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.services.facturacion.exception.FacturacionException;
import com.comerzzia.core.model.usuarios.UsuarioExample;
import com.comerzzia.core.persistencia.usuarios.UsuarioMapper;
import com.comerzzia.core.servicios.sesion.DatosSesionBean;
import com.comerzzia.core.servicios.variables.VariableException;
import com.comerzzia.core.servicios.variables.VariableNotFoundException;
import com.comerzzia.core.servicios.variables.VariablesService;
import com.comerzzia.omnichannel.model.documents.sales.ticket.UsuarioBean;

@Component
public class UsuariosService {

	protected static final Logger log = Logger.getLogger(UsuariosService.class);
	
	protected static final String VARIABLE_X_USUARIO_APERTURA_CAJA = "X_USUARIO_APERTURA_CAJA";
	
	@Autowired
	protected UsuarioMapper usuarioMapper;
	@Autowired
	protected VariablesService variablesService;
	
	public UsuarioBean obtenerUsuario(String uidInstancia, String idUsuario) throws FacturacionException {
		log.debug("obtenerUsuario() - Consultando usuario con ID: " + idUsuario);
		
		UsuarioExample usuarioExample = new UsuarioExample();
		usuarioExample.or().andUidInstanciaEqualTo(uidInstancia).andIdUsuarioEqualTo(Long.parseLong(idUsuario));
		List<com.comerzzia.core.model.usuarios.UsuarioBean> usuarioList = usuarioMapper.selectByExample(usuarioExample);
		
		if(usuarioList == null || usuarioList.isEmpty()) {
			String msg = "No se ha encontrado usuario con ID " + idUsuario + " y uidInstancia " + uidInstancia;
			log.error("obtenerUsuario() - " + msg);
			throw new FacturacionException(msg);
		}
		
		UsuarioBean usuario = new UsuarioBean();
		usuario.setUsuario(usuarioList.get(0).getUsuario());
		usuario.setDesusuario(usuarioList.get(0).getDesUsuario());
		
		return usuario;
	}

	public UsuarioBean obtenerUsuarioPredeterminado(DatosSesionBean datosSesion) throws FacturacionException {
		log.debug("obtenerUsuarioPredeterminado()");
		
		UsuarioBean usuario = new UsuarioBean();
		try {
			String idUsuario = variablesService.consultarValor(datosSesion, VARIABLE_X_USUARIO_APERTURA_CAJA);
			log.debug("obtenerUsuarioPredeterminado() - idUsuario predeterminado consultado: " + idUsuario);
			usuario = obtenerUsuario(datosSesion.getUidInstancia(), idUsuario);
		}
		catch (VariableException | VariableNotFoundException e) {
			String msg = "Error obteniendo VARIABLE del usuario predeterminado: " + e.getMessage();
			log.error("obtenerUsuarioPredeterminado() - " + msg);
			throw new FacturacionException(msg, e);
		}
		
		return usuario;
	}
	
}
