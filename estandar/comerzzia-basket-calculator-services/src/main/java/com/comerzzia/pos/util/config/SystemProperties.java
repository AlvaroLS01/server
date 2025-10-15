package com.comerzzia.pos.util.config;

import java.util.HashMap;
import java.util.Map;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;

public class SystemProperties {

	private Map<String, String> properties = new HashMap<>();
	
	
	public void readProperties(XMLDocumentNode xmlDocumentNode) {
		if(xmlDocumentNode != null){
			for (XMLDocumentNode hijo : xmlDocumentNode.getHijos()) {
				properties.put(hijo.getNombre(), hijo.getValue());
			}
		}
	}
	
	public Map<String, String> getProperties(){
		return properties;
	}
	
}
