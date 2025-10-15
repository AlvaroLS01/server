package com.comerzzia.omnichannel.service.documentprint;

import java.util.Locale;

import com.comerzzia.core.servicios.ContextHolder;


public class LanguageUtilForLocale {
	Locale locale;
	LanguageUtil languageUtil;
	
	public LanguageUtilForLocale(Locale locale) {
		this.locale = locale;
		this.languageUtil = getLanguageUtilInstance();
	}


	protected LanguageUtil getLanguageUtilInstance() {
		try {
			return (LanguageUtil) ContextHolder.getBean("languageUtil");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public String get(String key) {
		return languageUtil.getString(locale, key);
	}

}
