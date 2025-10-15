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
package com.comerzzia.core.basketcalculator.util.i18n;

import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import gnu.gettext.GettextResource;

public class Translation {
	
	/** Logger */
	private static Logger log = Logger.getLogger(Translation.class);

   private static Hashtable<Locale, ResourceBundle> trht = new Hashtable<Locale, ResourceBundle> ();
   private static Hashtable<Locale, ResourceBundle> trhtExt = new Hashtable<Locale, ResourceBundle> ();

   private ResourceBundle myResources = null;
   private ResourceBundle myResourcesExt = null;
   
   private static boolean isI18nCargado = true;
   
   private Locale locale;
   
   public Translation(Locale locale){
        this.locale = locale;
        
	    synchronized (trht){
	    
	    	if (!trht.containsKey(locale)){
	    		//Cargamos el estándar	
	    		try{
	               myResources = GettextResource.getBundle ("cmz", locale); //Primero se busca en la raíz de classpath (se debe añadir en modules conf.comerzzia slot i18n)
	            }
	            catch (Exception e){
		    		try{
		    			myResources = GettextResource.getBundle ("comerzzia.i18n.cmz", locale);
		    		}
		    		catch (Exception e2){
		    			//El properties no existe
		    			if(isI18nCargado){
		    				log.warn("No encontrado el fichero *.properties encargado de la internacionalización del backoffice.");
		    				isI18nCargado=false;
		    			}
		    			return;
		    		}
	            }
	    		
    			trht.put ((Locale) locale.clone (), myResources);
	    	}
	    	else{
	            myResources = trht.get (locale);
	     	}
	    }

	    synchronized (trhtExt){ 
		    
	    	if (!trhtExt.containsKey(locale)){
	    		try{
	    			
	    		   //Cargamos el extendido	
	    			myResourcesExt = GettextResource.getBundle ("cmz-ext", locale);
	            }
	            catch (Exception e){
	            	//El properties no existe
	            	return;
	            }

	    		trhtExt.put ((Locale) locale.clone (), myResourcesExt);
	        
	    	}
	    	else{
	    		myResourcesExt = trhtExt.get (locale);
	     	}
	    }
   }
      
   public String getText(String s)
   {
      if (myResources == null && myResourcesExt == null) return s;
      
      //Si tenemos properties externos buscamos el valor ahí primero
      if(myResourcesExt != null && myResourcesExt.containsKey(s)){
    	  return GettextResource.gettext (myResourcesExt, s);
      }
      
      //Si no tenemos valor lo obtenemos del properties estándar
      if(myResources != null){
    	  return GettextResource.gettext (myResources, s);
      }
      
      return s;
   }

   public String N_(String singular, String plural, long n)
   {
      if (myResources == null && myResourcesExt == null) return (n == 1 ? singular : plural);

      //Si tenemos properties externos buscamos el valor ahí primero
      if(myResourcesExt != null && myResourcesExt.containsKey(singular) && myResourcesExt.containsKey(plural)){
    	  return GettextResource.ngettext (myResourcesExt, singular, plural, n);
      }
      
      //Si no tenemos valor lo obtenemos del properties estándar
      if(myResources != null){
    	  return GettextResource.ngettext (myResources, singular, plural, n);
      }

      return (n == 1 ? singular : plural);
   }
  
   public String getText(String s, Object ... args)
   {
      return MessageFormat.format (getText(s), args);
   }
   
   public Locale getLocale() {
	   return locale;
   }

}
