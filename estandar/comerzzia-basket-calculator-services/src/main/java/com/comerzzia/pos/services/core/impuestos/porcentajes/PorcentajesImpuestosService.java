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

package com.comerzzia.pos.services.core.impuestos.porcentajes;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.persistence.core.impuestos.porcentajes.POSPorcentajeImpuestoMapper;
import com.comerzzia.pos.persistence.core.impuestos.porcentajes.PorcentajeImpuestoBean;
import com.comerzzia.pos.persistence.core.impuestos.porcentajes.PorcentajeImpuestoExample;

@Service
public class PorcentajesImpuestosService {
 
    protected static final Logger log = Logger.getLogger(PorcentajesImpuestosService.class);
        
    @Autowired
    protected POSPorcentajeImpuestoMapper porcentajeImpuestoMapper;
    
    /** Obtiene lista de porcentajes de impuestos registrados en el sistema para la 
     * vigencia actual indicada por el id grupo de impuestos
     * @param idGrupoImpuesto
     * @return List<PorcentajeImpuestoBean>
     * @throws com.comerzzia.pos.services.core.impuestos.porcentajes.PorcentajesImpuestosServiceException
     */
    public List<PorcentajeImpuestoBean> consultarPorcentajesImpuestosActual(String uidActividad, Integer idGrupoImpuesto) throws PorcentajesImpuestosServiceException{
        try{
            PorcentajeImpuestoExample filtro = new PorcentajeImpuestoExample();
            filtro.or().andUidActividadEqualTo(uidActividad).andIdGrupoImpuestosEqualTo(idGrupoImpuesto);
            
            List<PorcentajeImpuestoBean> porcentajesImp = porcentajeImpuestoMapper.selectByExample(filtro);
            
            return porcentajesImp;
        }
        catch(Exception e){
            String msg = "Se ha producido un error consultando porcentaje de impuestos con vigencia actual. " + e.getMessage();
            log.error("consultarPorcentajesImpuestosActual() - " + msg, e);
            throw new PorcentajesImpuestosServiceException(e);
        }
    }
}