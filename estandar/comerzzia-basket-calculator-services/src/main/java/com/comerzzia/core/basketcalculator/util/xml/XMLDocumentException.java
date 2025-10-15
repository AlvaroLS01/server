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
package com.comerzzia.core.basketcalculator.util.xml;


public class XMLDocumentException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -5086664177325890173L;

	/** 
     * Constructor por defecto sin mensaje de error
     */
    public XMLDocumentException() {
    }
        
    /**
     * Constructor con mensaje de error
     * @param msg Mensaje de error
     */
    public XMLDocumentException(String msg) {
        super(msg);
    }
    
    /**
     * Constructor con mensaje de error y causa
     * @param msg Mensaje de error
     * @param e Causa origen del error
     */
    public XMLDocumentException(String msg, Throwable e) {
        super(msg);
        initCause(e);
    }
}
