package com.comerzzia.pos.services.payments.configuration;

public class ConfigurationPropertyDto {

	public static String TYPE_TEXT = "typeText";

	public static String TYPE_AMOUNT = "typeAmount";

	public static String TYPE_DATE = "typeDate";

	private String key;

	private String description;

	private String type;

	public ConfigurationPropertyDto(String key, String description) {
		super();
		this.key = key;
		this.description = description;
		this.type = TYPE_TEXT;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
