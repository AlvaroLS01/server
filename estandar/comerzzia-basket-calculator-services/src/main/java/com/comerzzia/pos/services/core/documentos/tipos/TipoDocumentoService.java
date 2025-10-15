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


package com.comerzzia.pos.services.core.documentos.tipos;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.persistence.core.documentos.tipos.POSTipoDocumentoMapper;
import com.comerzzia.pos.persistence.core.documentos.tipos.TipoDocumentoBean;
import com.comerzzia.pos.persistence.core.documentos.tipos.TipoDocumentoExample;
import com.comerzzia.pos.services.core.documentos.DocumentoException;
import com.comerzzia.pos.services.core.documentos.DocumentoNotFoundException;

@Service
public class TipoDocumentoService {
    
    protected static final Logger log = Logger.getLogger(TipoDocumentoService.class);
    
    @Autowired
    protected POSTipoDocumentoMapper tipoDocumentoMapper;
    
    public List<TipoDocumentoBean> consultarTiposDocumentos(String uidActividad, String codPais) throws DocumentoException, DocumentoNotFoundException {
        try{
            TipoDocumentoExample filtro = new TipoDocumentoExample();
            filtro.or().andUidActividadEqualTo(uidActividad).andCodpaisEqualTo(codPais);
            filtro.setOrderByClause(TipoDocumentoExample.ORDER_BY_CODTIPODOCUMENTO);
            
            List<TipoDocumentoBean> tiposDocumentos = tipoDocumentoMapper.selectByExample(filtro);
            
            if(tiposDocumentos != null && !tiposDocumentos.isEmpty()){
                return tiposDocumentos;
            }
            else{
                throw new DocumentoNotFoundException();
            }            
        }
        catch(DocumentoNotFoundException e){
            log.error("consultarTiposDocumentos() - No se ha encontrado ningún tipo de documento para la actividad configurada");
            throw new DocumentoNotFoundException();
        }
        catch(Exception e){
            String msg = "Se ha producido un error consultando documentos para instancia y pais. " + e.getMessage();
            log.error("consultarTiposDocumentos()() - " + msg, e);
            throw new DocumentoException(e);
        }
    }
    
    
}
