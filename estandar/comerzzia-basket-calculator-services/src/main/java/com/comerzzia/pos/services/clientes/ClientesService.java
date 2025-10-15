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

package com.comerzzia.pos.services.clientes;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.core.basketcalculator.util.base.Estado;
import com.comerzzia.pos.persistence.clientes.ClienteBean;
import com.comerzzia.pos.persistence.clientes.ClienteExample;
import com.comerzzia.pos.persistence.clientes.ClienteExample.Criteria;
import com.comerzzia.pos.persistence.clientes.ClienteKey;
import com.comerzzia.pos.persistence.clientes.ClienteMapper;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.util.i18n.I18N;

@Service
public class ClientesService {

    protected static final Logger log = Logger.getLogger(ClientesService.class);
    
    @Autowired
    Sesion sesion;
    
    @Autowired
    protected ClienteMapper clienteMapper;

    /** Devuelve el cliente con código indicado por parámetro. El cliente debe de
     * estar activo. Si no se encuentra, se lanzará una excepción.
     * @param codCliente :: Código de cliente buscado
     * @return :: Cliente
     * @throws ClienteNotFoundException :: Lanzada si no se encuentra el cliente
     * en base de datos
     * @throws ClientesServiceException
     */
    public ClienteBean consultarCliente(String codCliente) throws ClienteNotFoundException, ClientesServiceException {
        String uidActividad = sesion.getUidActividad();
        ClienteBean cliente = consultarCliente(uidActividad, codCliente);
        cliente.setIdGrupoImpuestos(sesion.getImpuestos().getGrupoImpuestos().getIdGrupoImpuestos());
        return cliente;
    }

    /** Devuelve el cliente con código indicado por parámetro. El cliente debe de
     * estar activo. Si no se encuentra, se lanzará una excepción.
     * @param uidActividad :: UidActividad
     * @param codCliente :: Código de cliente buscado
     * @return :: Cliente
     * @throws ClienteNotFoundException :: Lanzada si no se encuentra el cliente
     * en base de datos
     * @throws ClientesServiceException
     */
    public ClienteBean consultarCliente(String uidActividad, String codCliente) throws ClienteNotFoundException, ClientesServiceException {
        try {
            ClienteBean cliente;
            ClienteKey keyCliente = new ClienteKey();
            keyCliente.setCodCliente(codCliente);
            keyCliente.setUidActividad(uidActividad);
            log.debug("consultarCliente() - consultando cliente con codCliente" + codCliente);
            cliente = clienteMapper.selectByPrimaryKey(keyCliente);

            if (cliente != null) {
                if (cliente.getActivo()) {
                    return cliente;
                }
            }
            log.debug("consultarCliente() - No se ha encontrado al cliente" + codCliente);
            throw new ClienteNotFoundException();
        }
        catch (ClienteNotFoundException e) {
            log.error("consultarCliente() - Cliente no encontrado para codigo " + codCliente);
            throw e;
        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando el cliente con codigo " + codCliente + ": " + e.getMessage();
            log.error("consultarCliente() - " + msg, e);
            throw new ClientesServiceException(e);
        }
    }

    /**
     * Consulta clientes que concuerdan con los parámetros de búsqueda. El cliente debe de
     * estar activo. 
     *
     * @param cif :: Cif de cliente buscado
     * @param desCliente :: Descripción del cliente buscada
     * @return List<ClienteBean> lista de clientes
     * @throws ClientesServiceException
     */
    public List<ClienteBean> consultarClientes(String codTipoIdent, String ident, String desCliente) throws ClientesServiceException {
        log.debug("consultarClientes() - consultando clientes con identificador :"+ ident + " , descliente :"+desCliente );
        List<ClienteBean> res;
        try {
            String uidActividad = sesion.getUidActividad();
            ClienteExample example = new ClienteExample();
            Criteria or = example.or();
            or.andUidActividadEqualTo(uidActividad).andActivoEqualTo(Boolean.TRUE);
            if(codTipoIdent != null && !codTipoIdent.isEmpty()){
            	or.andCodtipoidenEqualTo(codTipoIdent);
	        }
            if(ident != null && !ident.isEmpty()){
            	or.andCifLikeInsensitive(ident+"%");
	        }
            if(desCliente != null && !desCliente.isEmpty()){
            	or.andDesClienteLikeInsensitive("%"+desCliente+"%");
	        }
            res = clienteMapper.selectByExample(example);
            Integer idGrupoImpuestos = sesion.getImpuestos().getGrupoImpuestos().getIdGrupoImpuestos();
            for (ClienteBean cliente : res) {
            	cliente.setIdGrupoImpuestos(idGrupoImpuestos);
			}
        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando clientes con cif :"+ ident + " , descliente :"+desCliente + ": " + e.getMessage();
            log.error("consultarClientes() - " + msg, e);
            throw new ClientesServiceException(e);
        }
        
        
        return res;   
    }

    /**
     * Consulta clientes que concuerdan con los parámetros de búsqueda. El cliente debe de
     * estar activo. 
     *
     * @param cif :: Cif de cliente buscado
     * @return List<ClienteBean> lista de clientes
     * @throws ClientesServiceException
     */
    public List<ClienteBean> consultarClientes(String codTipoIdent, String ident) throws ClientesServiceException {
        log.debug("consultarClientesDatosObligatorios() - consultando clientes con codTipoIdent: " + codTipoIdent + " y ident:" + ident);
        List<ClienteBean> res = null;
        try {
        	if(StringUtils.isNotBlank(ident)) {
	            String uidActividad = sesion.getUidActividad();
	            ClienteExample example = new ClienteExample();
	            
	            Criteria or = example.createCriteria();
	            or.andUidActividadEqualTo(uidActividad).andActivoEqualTo(Boolean.TRUE);
	            if(codTipoIdent != null && !StringUtils.isBlank(codTipoIdent)) {
	            	or.andCodtipoidenEqualTo(codTipoIdent);
	            }
	            else {
	            	or.andCodtipoidenIsNull();
	            }
	            or.andCifEqualTo(ident.trim());
	            
	            if(codTipoIdent == null || StringUtils.isBlank(codTipoIdent)) {
	            	Criteria orAux = example.createCriteria();
	            	orAux.andUidActividadEqualTo(uidActividad).andActivoEqualTo(Boolean.TRUE);
	            	orAux.andCodtipoidenEqualTo("");
	            	orAux.andCifEqualTo(ident);
	            	example.or(orAux);
	            }
	            
	            res = clienteMapper.selectByExample(example);
	            Integer idGrupoImpuestos = sesion.getImpuestos().getGrupoImpuestos().getIdGrupoImpuestos();
	            for (ClienteBean cliente : res) {
	            	cliente.setIdGrupoImpuestos(idGrupoImpuestos);
				}
            }
        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando clientes:" + e.getMessage();
            log.error("consultarClientes() - " + msg, e);
            throw new ClientesServiceException(e);
        }
        
        
        return res;   
    }
    
	/**
	 * Mediante este método se impone la restricción de integridad por código que impide existan clientes
	 * con el mismo CIF.
	 * 
	 * @param cliente
	 * @throws ClientesServiceException
	 * @throws ClienteConstraintViolationException
	 */
	public void comprobarExistenciaClientePorCif(ClienteBean cliente) throws ClientesServiceException, ClienteConstraintViolationException {
		log.debug("comprobarExistenciaClientePorCif() - Comprobando que no existe ningún cliente con el mismo documento en el sistema. CIF: " + cliente.getCif());
		
		// Si el CIF es vacío, continuar
		if (cliente.getCif() != null && !cliente.getCif().isEmpty()) {
			List<ClienteBean> res;
	        try {
	        	// Si el cliente ya existe, comprobar que el CIF es el mismo para no hacer la validación
	            if (!cliente.isEstadoNuevo()) {
					ClienteBean clienteAntiguo = consultarCliente(cliente.getCodCliente());
					
					if (cliente.getCif().equalsIgnoreCase(clienteAntiguo.getCif())) {
						return;
					}
				}
	            
	            String uidActividad = sesion.getUidActividad();
	            ClienteExample example = new ClienteExample();
	            example.or().andCifEqualTo(cliente.getCif()).andUidActividadEqualTo(uidActividad);
	            res = clienteMapper.selectByExample(example);
	            if(!res.isEmpty()){
	            	throw new ClienteConstraintViolationException(I18N.getTexto("Ya existe un cliente con el mismo documento en el sistema"));
	            }
	        } catch (ClienteNotFoundException e) {
	        	return;
	        }
		}
		
		return;
	}
	
	
	public int salvar(ClienteBean cliente) throws ClientesServiceException, ClienteConstraintViolationException {
		int cambios = 0;
		
		switch (cliente.getEstadoBean()) {
			case Estado.NUEVO:
				cambios = crear(cliente);
				break;
			      
			case Estado.MODIFICADO:
				cambios =  modificar(cliente);
				break;
			      
			case Estado.BORRADO:
				cambios = eliminar(cliente.getCodCliente());
				break;
		}
		
		return cambios;
	}

	public int  eliminar(String codCliente) throws ClientesServiceException, ClienteConstraintViolationException {
		log.debug("eliminar() - Eliminando cliente con código: " + codCliente);
        try {
        	String uidActividad = sesion.getUidActividad();
        	
            ClienteKey key = new ClienteKey();
            key.setUidActividad(uidActividad);
            key.setCodCliente(codCliente);
			int num = clienteMapper.deleteByPrimaryKey(key);
			return num;
        }catch (Exception e) {
        	if(e.getCause() instanceof SQLIntegrityConstraintViolationException){
    			throw new ClienteConstraintViolationException(I18N.getTexto("No se puede eliminar el cliente ya que tiene registros asociados"));
    		}
			String msg = "Se ha producido un error al borrar el cliente: " + codCliente + ": " + e.getMessage();
			log.error("eliminar() - " + msg, e);
    		throw new ClientesServiceException(msg);
        }
	}

	public int modificar(ClienteBean cliente) throws ClientesServiceException, ClienteConstraintViolationException {
		log.debug("modificar() - Modificando cliente con código: " + cliente.getCodCliente());
        	String uidActividad = sesion.getUidActividad();
        	cliente.setUidActividad(uidActividad);
        	
        	comprobarExistenciaClientePorCif(cliente);
        	
			//Si el cliente está inactivo y antes no tenía fecha de baja se pone la de hoy
			if(!cliente.getActivo() && cliente.getFechaBaja() == null){
				cliente.setFechaBaja(new Date());
			}
			//Si el cliente está activo y antes estaba de baja se pone la fecha de baja a nula
			else if(cliente.getActivo() && cliente.getFechaBaja() != null){
				cliente.setFechaBaja(null);
			}
        	
            int num = clienteMapper.updateByPrimaryKey(cliente);
            return num;
	}

	public int crear(ClienteBean cliente) throws ClientesServiceException, ClienteConstraintViolationException {
		log.debug("crear() - Creando cliente con código: " + cliente.getCodCliente());
        try {
        	String uidActividad = sesion.getUidActividad();
        	cliente.setUidActividad(uidActividad);
        	
        	comprobarExistenciaClientePorCif(cliente);

        	//Indicamos que la fecha del alta será hoy
			cliente.setFechaAlta(new Date());
			                         
			//Si se da de alta inactivo ponemos fecha de baja
			if(!cliente.getActivo()){
				cliente.setFechaBaja(new Date());
			}
        	
            int num = clienteMapper.insert(cliente);
            return num;   
        }catch(ClienteConstraintViolationException e){
        	throw e;
		}catch (Exception e) {
			String msg = "Se ha producido un error al crear el cliente: " + cliente.getCodCliente() + ": " + e.getMessage();
			log.error("crear() - " + msg, e);
    		throw new ClientesServiceException(msg);
        }
	}
	
}
