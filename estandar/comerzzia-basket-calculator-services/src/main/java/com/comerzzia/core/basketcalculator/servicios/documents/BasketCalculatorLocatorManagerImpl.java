package com.comerzzia.core.basketcalculator.servicios.documents;

import java.util.HashMap;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.services.ContextHolder;

@Service
public class BasketCalculatorLocatorManagerImpl implements LocatorManager {
	protected static LocatorManager instance;
	
	@Deprecated
	public static LocatorManager get(){
		if (instance != null) {
		   return instance;
		} else { 
   		   return (LocatorManager) ContextHolder.get().getBean(LocatorManager.class);
		}
	}

	@Override
	public String encode(HashMap<String, Object> params) {
		String codAlmacen = "";
		String codCaja = "";
				
		if (params.containsKey("codAlmacen")) {
			codAlmacen = (String)params.get("codAlmacen");
		} else {
			codAlmacen = RandomStringUtils.randomNumeric(4);
		}
		
		if (params.containsKey("codCaja")) {
			codCaja = (String)params.get("codCaja");
		} else {
			codCaja = RandomStringUtils.randomNumeric(2);
		}		
						
		return encode(codAlmacen, codCaja);

	}
	
	@Override
	public String encode(String codAlmacen, String codCaja) {
		return StringUtils.leftPad(StringUtils.right(codAlmacen, 4), 4, '0') +
			   StringUtils.right(codCaja, 2) +
	           StringUtils.leftPad(Long.toString(System.currentTimeMillis()), 13, '0');

	}
	
	@Override
	public HashMap<String, Object> decode(String locator) throws LocatorParseException {	
		return new HashMap<>();
	}
	
}
