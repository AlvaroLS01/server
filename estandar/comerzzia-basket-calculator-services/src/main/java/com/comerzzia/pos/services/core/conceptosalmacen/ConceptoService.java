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

package com.comerzzia.pos.services.core.conceptosalmacen;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.persistence.core.conceptosalmacen.ConceptoAlmacenBean;
import com.comerzzia.pos.persistence.core.conceptosalmacen.ConceptoAlmacenExample;
import com.comerzzia.pos.persistence.core.conceptosalmacen.ConceptoAlmacenKey;
import com.comerzzia.pos.persistence.core.conceptosalmacen.ConceptoAlmacenMapper;

@Service
public class ConceptoService {
 
    protected static String CODAPLICACIONSEL = "VENT";
    protected static final Logger log = Logger.getLogger(ConceptoService.class);
    
    @Autowired
    protected ConceptoAlmacenMapper conceptoAlmacenMapper;
    
     public List<ConceptoAlmacenBean> consultarConceptos(String uidActividad) throws ConceptoServiceException{
        List<ConceptoAlmacenBean> resultado = new LinkedList<>();        
        try{            
            ConceptoAlmacenExample filtro = new ConceptoAlmacenExample();
            filtro.or().andCodaplicacionEqualTo(CODAPLICACIONSEL).andUidActividadEqualTo(uidActividad);
            log.debug("consultarConceptos() - Consultando conceptos de almacén " );
            resultado = conceptoAlmacenMapper.selectByExample(filtro);
                        
            return resultado;
        }
        catch(Exception e){
            String msg = "Se ha producido un error consultando conceptos  "  + ": " + e.getMessage();
            log.error("consultarConceptos() - " + msg, e);
            throw new ConceptoServiceException(e);
        }
    }
     
    /**
     * 
     * @param uidActividad
     * @param codAplicacion
     * @param codConcepto
     * @return
     * @throws ConceptoServiceException
     * @throws ConceptoNotFoundException 
     */
    public ConceptoAlmacenBean consultarConcepto(String uidActividad, String codAplicacion, String codConcepto) throws ConceptoServiceException, ConceptoNotFoundException{
        try{
            ConceptoAlmacenBean conceptoAlmacen;
            
            ConceptoAlmacenKey keyEmpresa = new ConceptoAlmacenKey();
            keyEmpresa.setCodaplicacion(codAplicacion);
            keyEmpresa.setUidActividad(uidActividad);
            keyEmpresa.setCodconalm(codConcepto);
            log.debug("consultarConcepto() - Consultando concepto almacén " + codConcepto);
            conceptoAlmacen = conceptoAlmacenMapper.selectByPrimaryKey(keyEmpresa);
            
            if(conceptoAlmacen != null){
                if(conceptoAlmacen.getActivo()){
                    return conceptoAlmacen;
                }
            }
            throw new ConceptoNotFoundException();
        }
        catch(ConceptoNotFoundException e){
            log.error("consultarConcepto() - Empresa no encontrada para codido de concepto" + codConcepto);
            throw new ConceptoNotFoundException();
        }
        catch(Exception e){
            String msg = "Se ha producido un error consultando concepto con código " + codConcepto + ": " + e.getMessage();
            log.error("consultarConcepto() - " + msg, e);
            throw new ConceptoServiceException(e);
        }
    }
}