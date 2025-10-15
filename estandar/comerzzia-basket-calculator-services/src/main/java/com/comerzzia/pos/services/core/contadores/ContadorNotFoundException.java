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
package com.comerzzia.pos.services.core.contadores;

import com.comerzzia.pos.util.i18n.I18N;

public class ContadorNotFoundException extends ContadorServiceException {

    /**
     * serialVersionUID
     */
    protected static final long serialVersionUID = -1800018632509166155L;

    public ContadorNotFoundException() {
    }

    public ContadorNotFoundException(String message) {
        super(message);
    }

    public ContadorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContadorNotFoundException(Throwable cause) {
        super(cause);
    }

    public ContadorNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessageDefault() {
    	return I18N.getTexto("El contador que se intenta obtener no est\u00E1 registrado en el sistema");
    }

}
