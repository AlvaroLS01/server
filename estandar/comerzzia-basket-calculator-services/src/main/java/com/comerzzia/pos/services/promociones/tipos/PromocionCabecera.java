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


package com.comerzzia.pos.services.promociones.tipos;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.Promocion;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;

@Component
@Scope("prototype")
public abstract class PromocionCabecera extends Promocion {

	protected IPromocionTicket getPromocionTipo(DocumentoPromocionable<IPromocionTicket> documento, Long idTipoPromocion) {
    	for (IPromocionTicket promocion : documento.getPromociones()) {
			if (promocion.getIdTipoPromocion().equals(idTipoPromocion)){
				return promocion;
			}
		}
    	return null;
	}

	@Override
	public boolean isAplicacionCabecera() {
		return true;
	}

}
