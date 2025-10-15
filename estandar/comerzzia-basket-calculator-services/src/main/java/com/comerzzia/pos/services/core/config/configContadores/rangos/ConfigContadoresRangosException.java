/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND
 * INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED,
 * DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST,
 * TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK
 * WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE
 * AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */
package com.comerzzia.pos.services.core.config.configContadores.rangos;

import com.comerzzia.core.basketcalculator.util.base.Exception;

public class ConfigContadoresRangosException extends Exception {

	private static final long serialVersionUID = 2737149980340304069L;

	public ConfigContadoresRangosException() {
	}

	public ConfigContadoresRangosException(String msg) {
		super(msg);
	}

	public ConfigContadoresRangosException(String msg, Throwable e) {
		super(msg, e);
	}

	public ConfigContadoresRangosException(String msg, String msgKey) {
		super(msg, msgKey);
	}

	public ConfigContadoresRangosException(String msg, String msgKey, Throwable e) {
		super(msg, msgKey, e);
	}

}
