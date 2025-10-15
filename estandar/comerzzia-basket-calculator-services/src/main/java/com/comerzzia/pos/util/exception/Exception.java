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


package com.comerzzia.pos.util.exception;


public abstract class Exception extends java.lang.Exception{
    private static final long serialVersionUID = 1L;

    private String i18nMsg;
    
    public Exception() {
        super();
    }

    public Exception(String message) {
        super(message);
    }

    public Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public Exception(Throwable cause) {
        super(cause);
        if (cause instanceof Exception){
            i18nMsg = cause.getMessage();
        }
        else{
            i18nMsg = getMessageDefault();
        }
    }

    public Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public abstract String getMessageDefault();

    @Override
    public String getMessage() {
        if (super.getMessage() == null || super.getMessage().isEmpty()){
            return getMessageDefault();
        }
        return super.getMessage(); 
    }
    
    public String getMessageI18N(){
        if (i18nMsg == null || i18nMsg.isEmpty()){
            return getMessage();
        }
        return i18nMsg;
    }
    
    
}
