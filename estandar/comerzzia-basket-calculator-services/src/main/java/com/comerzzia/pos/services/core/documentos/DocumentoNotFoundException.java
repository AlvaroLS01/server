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


package com.comerzzia.pos.services.core.documentos;

import com.comerzzia.pos.util.i18n.I18N;


public class DocumentoNotFoundException extends com.comerzzia.pos.util.exception.Exception {

    /**
     * serialVersionUID
     */
    protected static final long serialVersionUID = 1842401465428940473L;

    public DocumentoNotFoundException() {
    }

    public DocumentoNotFoundException(String message) {
        super(message);
    }

    public DocumentoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentoNotFoundException(Throwable cause) {
        super(cause);
    }

    public DocumentoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessageDefault() {
    	return I18N.getTexto("El documento solicitado no est\u00E1 configurado");
    }
}
