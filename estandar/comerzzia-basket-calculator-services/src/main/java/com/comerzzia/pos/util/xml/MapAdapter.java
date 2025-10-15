package com.comerzzia.pos.util.xml;

import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;

public class MapAdapter extends XmlAdapter<MapWrapper, Map<String, Object>> {

	@Override
	public Map<String, Object> unmarshal(MapWrapper v) throws Exception {
		Map<String, Object> map = v.toMap();

		return map;
	}

	@SuppressWarnings({ "rawtypes" })
    @Override
	public MapWrapper marshal(Map<String, Object> m) throws Exception {
		MapWrapper wrapper = new MapWrapper();

		if(m != null) {
			for (Map.Entry<String, Object> entry : m.entrySet()) {
				if(entry.getValue() instanceof List) {
					int index = 0;
					for(Object listValue : (List) entry.getValue()) {
						wrapper.addEntry(new JAXBElement<Object>(new QName(entry.getKey() + "_" + index), Object.class, listValue));
						index++;
					}
				}
				else {
					wrapper.addEntry(new JAXBElement<Object>(new QName(entry.getKey()), Object.class, entry.getValue()));
				}
			}
		}

		return wrapper;
	}

}