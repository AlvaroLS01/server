package com.comerzzia.omnichannel.service.salesdocument.metadata;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class DocumentMetadata {
	public static final String SCHEMA_VERSION = "SCHEMA_VERSION";
	public static final String PRINT_TEMPLATE = "PRINT_TEMPLATE";
	public static final String MIME_TYPE = "MIME_TYPE";
	public static final String LOCALE = "LOCALE";	
	public static final String COMPANY_CODE = "COMPANY_CODE";
	
	public static final String DOC_TYPE_ID = "DOC_TYPE_ID";
	public static final String DOC_TYPE_CODE = "DOC_TYPE_CODE";
	public static final String DOCUMENT_CODE = "DOCUMENT_CODE";
	
	Map<String, Object> metadata = new HashMap<String, Object>();
	
	public void put(String key, Object value) {
		metadata.put(key, value);
	}
	
	public void putString(String key, String value) {
		metadata.put(key, value);
	}
	
	public Map<String, Object> getMetadata() {
		return metadata;
	}
	
	public Object get(String key) {
		return metadata.get(key);
	}
	
	public String getString(String key) {
		return (String)metadata.get(key);
	}
	
	public void setSchemaVersion(String value) {
		putString(SCHEMA_VERSION, value);
	}
	
	public String getSchemaVersion() {
		return getString(SCHEMA_VERSION);
	}
	
	public void setPrintTemplate(String value) {
		putString(PRINT_TEMPLATE, value);
	}
	
	public String getPrintTemplate() {
		return getString(PRINT_TEMPLATE);
	}
	
	public void setMimeType(String value) {
		putString(MIME_TYPE, value);
	}
	
	public String getMimeType() {
		return getString(MIME_TYPE);
	}
	
	public void setLocale(Locale value) {
		put(LOCALE, value);
	}
	
	public void setLocale(String value) {
		if (StringUtils.isNotEmpty(value)) {
		   put(LOCALE, new Locale(value));
		}
	}
	
	public Locale getLocale() {
		return (Locale)get(LOCALE);
	}
	
	public void setCompanyCode(String value) {
		put(COMPANY_CODE, value);
	}
	
	public String getCompanyCode() {
		return getString(COMPANY_CODE);
	}
	
	public void setDocTypeId(Long docTypeId) {
		put(DOC_TYPE_ID, docTypeId);
	}
	
	public Long getDocTypeId() {
		return (Long)get(DOC_TYPE_ID);
	}
	
	public void setDocTypeCode(String value) {
		put(DOC_TYPE_CODE, value);
	}
	
	public String getDocTypeCode() {
		return getString(DOC_TYPE_CODE);
	}
	
	public void setDocumentCode(String value) {
		put(DOCUMENT_CODE, value);
	}
	
	public String getDocumentCode() {
		return getString(DOCUMENT_CODE);
	}
		
}
