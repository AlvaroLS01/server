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


package com.comerzzia.pos.services.core.documentos.propiedades;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.persistence.core.documentos.propiedades.POSPropiedadDocumentoMapper;
import com.comerzzia.pos.persistence.core.documentos.propiedades.PropiedadDocumentoBean;
import com.comerzzia.pos.persistence.core.documentos.propiedades.PropiedadDocumentoExample;
import com.comerzzia.pos.services.core.documentos.DocumentoException;
import com.comerzzia.pos.services.core.documentos.DocumentoNotFoundException;

@Service
public class PropiedadesDocumentoService {
    
    protected static final Logger log = Logger.getLogger(PropiedadesDocumentoService.class);
    
    @Autowired
    protected POSPropiedadDocumentoMapper propiedadDocumentoMapper;
    
    public List<PropiedadDocumentoBean> consultarPropiedadesDocumentos(String uidActividad) throws DocumentoException, DocumentoNotFoundException {
        try{
            PropiedadDocumentoExample filtro = new PropiedadDocumentoExample();
            filtro.or().andUidActividadEqualTo(uidActividad);
            filtro.setOrderByClause(PropiedadDocumentoExample.ORDER_BY_ID_TIPO_DOCUMENTO);
            
            List<PropiedadDocumentoBean> propiedadesTipoDocumentos = propiedadDocumentoMapper.selectByExample(filtro);
            
            if(propiedadesTipoDocumentos != null && !propiedadesTipoDocumentos.isEmpty()){
                return propiedadesTipoDocumentos;
            }
            else{
                throw new DocumentoNotFoundException();
            }            
        }
        catch(DocumentoNotFoundException e){
            log.error("consultarPropiedadesDocumentos() - No se ha encontrado ninguna propiedad de documentos para la actividad configurada");
            throw new DocumentoNotFoundException();
        }
        catch(Exception e){
            String msg = "Se ha producido un error consultando documentos para instancia y pais. " + e.getMessage();
            log.error("consultarPropiedadesDocumentos() - " + msg, e);
            throw new DocumentoException(e);
        }
    }

    public List<PropiedadDocumentoBean> consultarPropiedadesDocumentos(String uidActividad, String propiedad) {
        PropiedadDocumentoExample filtro = new PropiedadDocumentoExample();
        filtro.or().andUidActividadEqualTo(uidActividad).andPropiedadEqualTo(propiedad);
        filtro.setOrderByClause(PropiedadDocumentoExample.ORDER_BY_ID_TIPO_DOCUMENTO);

        List<PropiedadDocumentoBean> propiedadesTipoDocumentos = propiedadDocumentoMapper.selectByExample(filtro);

        return propiedadesTipoDocumentos;
    }

}
