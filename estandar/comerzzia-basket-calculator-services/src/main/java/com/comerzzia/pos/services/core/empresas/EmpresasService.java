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

package com.comerzzia.pos.services.core.empresas;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.persistence.core.empresas.EmpresaBean;
import com.comerzzia.pos.persistence.core.empresas.EmpresaKey;
import com.comerzzia.pos.persistence.core.empresas.POSEmpresaMapper;

@Service
public class EmpresasService {
 
    protected static final Logger log = Logger.getLogger(EmpresasService.class);
    
    @Autowired
    protected POSEmpresaMapper empresaMapper;
    
    /** Devuelve la empresa con el código indicado que se encuentre en la tabla de Empresas
     * de BBDD. Si no existe lanza una excepción. La empresa debe de estar activa.
     * @param uidActividad
     * @param codEmpresa
     * @return :: Empresa
     * @throws EmpresaNotFoundException :: Lanzada si no existe la empresa con el código indicado
     * @throws EmpresasServiceException 
     */
    public EmpresaBean consultarEmpresa(String uidActividad, String codEmpresa) throws EmpresasServiceException, EmpresaNotFoundException{
        try{
            EmpresaKey keyEmpresa = new EmpresaKey();
            keyEmpresa.setCodEmpresa(codEmpresa);
            keyEmpresa.setUidActividad(uidActividad);
            
            log.debug("consultarEmpresa() - Consultando empresa con codEmpresa " + codEmpresa);
            
            EmpresaBean empresa = empresaMapper.selectByPrimaryKey(keyEmpresa);
            
            if(empresa != null){
                if(empresa.getActivo()){
                    return empresa;
                }
            }
            throw new EmpresaNotFoundException();
        }
        catch(EmpresaNotFoundException e){
            log.error("consultarEmpresa() - Empresa no encontrada para codido de empresa" + codEmpresa);
            throw new EmpresaNotFoundException();
        }
        catch(Exception e){
            String msg = "Se ha producido un error consultando la empresa con código " + codEmpresa + ": " + e.getMessage();
            log.error("consultarEmpresa() - " + msg, e);
            throw new EmpresasServiceException(e);
        }
    }
}