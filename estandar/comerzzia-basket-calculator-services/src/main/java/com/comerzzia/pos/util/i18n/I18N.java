/**
 * ComerZZia 3.0
 *
 * Copyright (c) 2008-2015 Comerzzia, S.L.  All Rights Reserved.
 *
 * THIS WORK IS  SUBJECT  TO  SPAIN  AND  INTERNATIONAL  COPYRIGHT  LAWS  AND
 * TREATIES.   NO  PART  OF  THIS  WORK MAY BE  USED,  PRACTICED,  PERFORMED
 * COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED,  ABRIDGED, CONDENSED,
 * EXPANDED,  COLLECTED,  COMPILED,  LINKED,  RECAST, TRANSFORMED OR ADAPTED
 * WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION
 * OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO
 * CRIMINAL AND CIVIL LIABILITY.
 *
 * CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL
 * RESTRICTIONS.
 */
package com.comerzzia.pos.util.i18n;

import gnu.gettext.GettextResource;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.comerzzia.pos.util.config.AppConfig;

public class I18N {

	private static List<String> translationPropertiesBaseNames = new LinkedList<String>();
	private static List<String> translationPropertiesBaseNamesExt = new LinkedList<String>();
	
    //Textos de etiquetas
    private static ResourceBundle resourceBundleFXML;

    // Logger
    private static final Logger log = Logger.getLogger(I18N.class.getName());

    static{
    	log.debug("I18N() - cargando archivo de idiomas");
        Locale locale = new Locale(AppConfig.idioma, AppConfig.pais);
        Locale.setDefault(locale); //Para el uso de mensajes localizados directamente en validación. Se valida en el LOCALE por defecto.
        resourceBundleFXML = new ResourceBundle() {
			
			@Override
			public boolean containsKey(String key) {
				return true;
			}

			@Override
			protected Object handleGetObject(String key) {
				return I18N.getTexto(key);
			}
			
			@Override
			public Enumeration<String> getKeys() {
				return Collections.enumeration(Collections.<String>emptyList());
			}
		};
    }

    private static String readBundle(String baseName, String texto, Locale locale, boolean ext){
		if(baseName != null){
			ResourceBundle resourceBundle = null;
			try{
				resourceBundle = GettextResource.getBundle(baseName, locale);
			}catch(MissingResourceException e){
				if(!ext){
					log.trace("readBundle() - No se encuentra el resource de properties de I18N " + baseName);
				}
			}
			
			if(resourceBundle != null){
				String gettext = GettextResource.gettext(resourceBundle, texto);
				if(gettext == texto){
					//Si el es mismo, delvolveremos null para que getTexto intente buscar alternativas
					return null;
				}else{
					return gettext;
				}
			}
		}
		return null;
	}
    
    /**
     * Obtiene el recurso de idiomas de las etiquetas
     *
     * @return
     */
    public static ResourceBundle getResourceBundle() {
        return resourceBundleFXML;
    }

    public static String getTexto(String texto, Locale locale){
		if(texto == null){
			return null;
		}
		String traduccion = null;
		
		if(locale != null){
			int index = translationPropertiesBaseNamesExt.size() - 1;
			while(traduccion == null && index >= 0){
				traduccion = readBundle(translationPropertiesBaseNamesExt.get(index), texto, locale, false);
				index--;
			}
			
			index = translationPropertiesBaseNames.size() - 1;
			while(traduccion == null && index >= 0){
				traduccion = readBundle(translationPropertiesBaseNames.get(index), texto, locale, false);
				index--;
			}
			
			if(traduccion != null){
				return traduccion;
			}
			
		}
		
		return texto;
	}
    
    /**
     * Obtiene un texto del archivo de Mensajes
     *
     * @param cadena
     * @return
     */
    public static String getTexto(String cadena) {
    	return getTexto(cadena, Locale.getDefault());
    }
    
    public static String getTexto(String cadena, Object...arguments ) {
    	return MessageFormat.format(getTexto(cadena), arguments);
    }
    
    public static List<String> getTranslationPropertiesBaseNameExt() {
	    return translationPropertiesBaseNamesExt;
    }

	public static void addTranslationPropertiesBaseNameExt(String translationPropertiesBaseNameExt) {
	    if(!translationPropertiesBaseNamesExt.contains(translationPropertiesBaseNameExt)) {
	    	translationPropertiesBaseNamesExt.add(translationPropertiesBaseNameExt);
		}
    }

	public static void addTranslationPropertiesBaseName(String translationPropertiesBaseName) {
		if(!translationPropertiesBaseNames.contains(translationPropertiesBaseName)) {
			translationPropertiesBaseNames.add(translationPropertiesBaseName);
		}
	}
	
	public static List<String> getTranslationPropertiesBaseNames() {
	    return translationPropertiesBaseNames;
    }
    
}
