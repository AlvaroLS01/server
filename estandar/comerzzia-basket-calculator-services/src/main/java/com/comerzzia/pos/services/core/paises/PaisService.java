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


package com.comerzzia.pos.services.core.paises;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.persistence.paises.PaisBean;
import com.comerzzia.pos.persistence.paises.PaisExample;
import com.comerzzia.pos.persistence.paises.PaisMapper;
import com.comerzzia.pos.services.core.sesion.Sesion;

@Service
public class PaisService {    
    protected final Logger log = Logger.getLogger(PaisService.class);
    
    @Autowired
    Sesion sesion;
    
    @Autowired
    protected PaisMapper paisMapper;
    
    public List<PaisBean> consultarPaises() throws PaisNotFoundException{        
        log.trace("consultarPaises() - Consultando países configurados...");
        
        try{
            PaisExample examplePais = new PaisExample();
            examplePais.or().andActivoEqualTo(Boolean.TRUE);
            examplePais.setOrderByClause(PaisExample.ORDER_BY_CODPAIS_DESC);
            List<PaisBean> paisesBean = paisMapper.selectByExample(examplePais);
            
            if(!paisesBean.isEmpty()){
                return paisesBean;
            }
            else{
                throw new PaisNotFoundException();
            }
        }catch(PaisNotFoundException ex){
            log.error("consultarPaises() - No se encontraron paises en la base de datos");
            throw ex;
        }
    }
    
    public PaisBean consultarCodPais(String codPais) throws PaisNotFoundException, PaisServiceException{        
        return consultarPais(codPais, "").get(0);
    }
    
    
    public List<PaisBean> consultarPais(String codPais, String descPais) throws PaisNotFoundException, PaisServiceException{
        try{
            PaisExample examplePais = new PaisExample();
            PaisExample.Criteria criteriaPais = examplePais.createCriteria();
            criteriaPais.andUidInstanciaEqualTo(sesion.getUidInstancia()).andActivoEqualTo(Boolean.TRUE);            
            if(codPais!=null && !codPais.isEmpty()){
                criteriaPais.andCodPaisLikeInsensitive(codPais);
            }
            if(descPais!= null && !descPais.isEmpty()){
                criteriaPais.andDesPaisLikeInsensitive("%"+descPais+"%");
            }
            examplePais.setOrderByClause(PaisExample.ORDER_BY_DESPAIS);
            
            List<PaisBean> paisesBean = paisMapper.selectByExample(examplePais);
            
            if(!paisesBean.isEmpty()){
                return paisesBean;
            }
            else{
                throw new PaisNotFoundException();
            }
        }catch(PaisNotFoundException ex){
            log.warn("consultarPais() No se encontró el pais en la base de datos : "+ codPais);
            throw ex;
        }catch(Exception ex){
            log.error("consultarPais() Error consltando pais en base de datos : "+ex.getMessage(), ex);
            throw new PaisServiceException(ex);
        }
    }
    
}
