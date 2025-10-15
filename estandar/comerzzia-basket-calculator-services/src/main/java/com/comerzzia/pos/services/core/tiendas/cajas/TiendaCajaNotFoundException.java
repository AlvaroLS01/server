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

package com.comerzzia.pos.services.core.tiendas.cajas;

import com.comerzzia.pos.util.i18n.I18N;


public class TiendaCajaNotFoundException  extends com.comerzzia.pos.util.exception.Exception{
    
    protected static final long serialVersionUID = 1L;

    public TiendaCajaNotFoundException() {
        super();
    }

    public TiendaCajaNotFoundException(String message) {
        super(message);
    }

    public TiendaCajaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TiendaCajaNotFoundException(Throwable cause) {
        super(cause);
    }

    public TiendaCajaNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessageDefault() {
    	return I18N.getTexto("La caja configurada no se encuentra registrada en el sistema");
    }
}
