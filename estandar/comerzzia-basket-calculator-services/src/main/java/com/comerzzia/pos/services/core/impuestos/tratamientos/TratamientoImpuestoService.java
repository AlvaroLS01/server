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
package com.comerzzia.pos.services.core.impuestos.tratamientos;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.persistence.core.impuestos.tratamientos.POSTratamientoImpuestoMapper;
import com.comerzzia.pos.persistence.core.impuestos.tratamientos.TratamientoImpuestoBean;
import com.comerzzia.pos.persistence.core.impuestos.tratamientos.TratamientoImpuestoExample;
import com.comerzzia.pos.persistence.core.impuestos.tratamientos.TratamientoImpuestoKey;

@Service
public class TratamientoImpuestoService {
	
	public static final Logger log = Logger.getLogger(TratamientoImpuestoService.class);
	
	@Autowired
	protected POSTratamientoImpuestoMapper tratamientoImpuestoMapper;
	
	public List<TratamientoImpuestoBean> consultarTratamientosImpuesto(String codPais, String uidActividad){
        try{
            TratamientoImpuestoExample filtro = new TratamientoImpuestoExample();
            TratamientoImpuestoExample.Criteria criteria = filtro.createCriteria();
            criteria.andUidActividadEqualTo(uidActividad).andCodpaisEqualTo(codPais);
            
            return tratamientoImpuestoMapper.selectByExample(filtro);         
        }
        catch(Exception e){
            String msg = "Se ha producido un error consultando el grupo de impuestos con vigencia actual. " + e.getMessage();
            log.error("consultarGrupoImpuestosActual() - " + msg, e);
            throw e;
        }
	}

	
	public TratamientoImpuestoBean consultarTratamientoImpuesto(String uidActividad, Long idTratamiento){
        try{
            TratamientoImpuestoKey key = new TratamientoImpuestoKey();
            key.setUidActividad(uidActividad);
            key.setIdTratImpuestos(idTratamiento);
            
            return tratamientoImpuestoMapper.selectByPrimaryKey(key); 
        }
        catch(Exception e){
            String msg = "Se ha producido un error consultando el grupo de impuestos con vigencia actual. " + e.getMessage();
            log.error("consultarGrupoImpuestosActual() - " + msg, e);
            throw e;
        }
	}
}
