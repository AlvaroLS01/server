package com.comerzzia.core.basketcalculator.util.xml.sax;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.comerzzia.core.basketcalculator.util.fechas.Fecha;


public class XMLSaxParserController {

	private String currentTag;
	private String currentTagGroup;
	private Map<String, StringBuffer> data;
	
	public XMLSaxParserController(){
		data = new HashMap<String, StringBuffer>();
	}
	
	/** Marca el inicio de un nuevo tag y borra los datos históricos de dicho tag. */
	public void setStartTag(String tag){
		currentTag = tag;
		data.remove(currentTag);
	}

	/** Marca el inicio de un nuevo tag de grupo. */
	public void setStartTagGroup(String tagGroup){
		currentTagGroup = tagGroup;
	}
	
	/** Marca el fin de un tag eliminando el tag actual. */
	public void setEndTag(){
		currentTag = null;
	}

	/** Marca el fin de un tag de grupo eliminando el tag de grupo actual. */
	public void setEndTagGroup(){
		currentTagGroup = null;
	}
	
	/** Indica si el tag actual es el indicado por parámetro */
	public boolean isTag(String tag){
		return currentTag != null && currentTag.equals(tag);
	}

	/** Indica si el tag de grupo actual es el indicado por parámetro */
	public boolean isTagGroup(String tagGroup){
		return currentTagGroup != null && currentTagGroup.equals(tagGroup);
	}
	
	/** Devuelve el tag actual. */
	public String getCurrentTag(){
		return currentTag;
	}

	/** Devuelve el tag de grupo actual. */
	public String getCurrentTagGroup(){
		return currentTagGroup;
	}
	
	/** Almacena datos como contenido del tag actual. */
	public void put(String value){
		StringBuffer buffer = data.get(currentTag);
		if (buffer == null){
			buffer = new StringBuffer("");
			data.put(currentTag, buffer);
		}
		buffer.append(value);
	}
	
	public String getDataString(String key){
		if (data.get(key) == null){
			return null;
		}
		return data.get(key).toString();
	}
	public byte[] getDataBytes(String key){
		if (data.get(key) == null){
			return null;
		}
		return data.get(key).toString().getBytes();
	}
	public Long getDataLong(String key){
		if (data.get(key) == null){
			return null;
		}
		return Long.parseLong(data.get(key).toString());
	}
	public Date getDataDateSQL(String key, String patronFecha) {
		if (data.get(key) == null){
			return null;
		}
		return Fecha.getFecha(data.get(key).toString(), patronFecha).getSQL();
	}
	public java.util.Date getDataDate(String key, String patronFecha) {
		if (data.get(key) == null){
			return null;
		}
		return Fecha.getFecha(data.get(key).toString(), patronFecha).getDate();
	}
	public Timestamp getDataTimestamp(String key, String patronFecha) {
		if (data.get(key) == null){
			return null;
		}
		return Fecha.getFecha(data.get(key).toString(), patronFecha).getTimestamp();
	}
	public Integer getDataInteger(String key){
		if (data.get(key) == null){
			return null;
		}
		return Integer.parseInt(data.get(key).toString());
	}
	public Double getDataDouble(String key){
		if (data.get(key) == null){
			return null;
		}
		return Double.parseDouble(data.get(key).toString());
	}
	public BigDecimal getDataBigDecimal(String key){
		if (data.get(key) == null){
			return null;
		}
		return new BigDecimal(data.get(key).toString());
	}
	
	
}
