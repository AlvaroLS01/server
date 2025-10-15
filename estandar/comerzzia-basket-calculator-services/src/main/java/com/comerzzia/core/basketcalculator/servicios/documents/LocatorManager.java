package com.comerzzia.core.basketcalculator.servicios.documents;

import java.util.HashMap;

public interface LocatorManager {
	
	String encode(HashMap<String, Object> params);

	String encode(String codAlmacen, String codCaja);
		   
    HashMap<String, Object> decode(String locator) throws LocatorParseException;
		
}
