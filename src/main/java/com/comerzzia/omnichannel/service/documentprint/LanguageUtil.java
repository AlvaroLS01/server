package com.comerzzia.omnichannel.service.documentprint;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import com.comerzzia.core.servicios.ContextHolder;

@Component
public class LanguageUtil {
	private static LanguageUtil instance;

	@Autowired
	MessageSourceAccessor messageSourceAccessor;

	public static LanguageUtil getInstance() {
		if (instance == null) {
			try {
				instance = (LanguageUtil) ContextHolder.getBean("languageUtil");
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	public String getString(Locale locale, String key) {
		String result = key;
		
		try {
			result = messageSourceAccessor.getMessage(key, locale);
		} catch (NoSuchMessageException ignore) {
		}

		return result;
	}

	public static String get(Locale locale, String key) {
		return getInstance().getString(locale, key);
	}
}
