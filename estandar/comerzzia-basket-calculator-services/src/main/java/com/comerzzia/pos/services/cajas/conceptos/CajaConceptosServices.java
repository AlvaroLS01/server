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

package com.comerzzia.pos.services.cajas.conceptos;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.persistence.cajas.conceptos.CashJournalConcept;
import com.comerzzia.pos.persistence.cajas.conceptos.CashJournalConceptExample;
import com.comerzzia.pos.persistence.cajas.conceptos.CashJournalConceptMapper;

@Service
public class CajaConceptosServices {    
    protected static final Logger log = Logger.getLogger(CajaConceptosServices.class);
    
    @Autowired
    protected CashJournalConceptMapper cajaConceptoMapper;
        
    public List<CashJournalConcept> consultarConceptosCaja(String uidActividad) {
        // consultamos la base de datos
        CashJournalConceptExample exampleCajaConcepto = new CashJournalConceptExample();
        exampleCajaConcepto.or().andActivoEqualTo(Boolean.TRUE).andUidActividadEqualTo(uidActividad);

        log.debug("cargarConceptosCaja() - Cargando conceptos de caja para el uidActividad " + uidActividad);
        return cajaConceptoMapper.selectByExample(exampleCajaConcepto);            
    }

}
