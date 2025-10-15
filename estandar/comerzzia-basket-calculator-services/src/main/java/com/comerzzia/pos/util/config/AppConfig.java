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

package com.comerzzia.pos.util.config;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNodeNotFoundException;
import com.comerzzia.pos.util.i18n.I18N;

//@EnableTransactionManagement
@Configuration
@Lazy(false)
public class AppConfig {
	
    /**
     * Logger
     */
    private static final Logger log = Logger.getLogger(AppConfig.class);
    public static boolean modoDesarrollo;
    public static InterfazInfo interfazInfo;
    public static ModoDesarrolloInfo modoDesarrolloInfo;
	public static final String DEFAULT_SKIN = "standard";
	public static String skin;
    public static Long accionInicio = 200l;
    public static boolean loginBotonera;
    public static String pais = "ES";
    public static String idioma = "ES";
    public static String aplicacion = "JPOS";
    public static String menu = "POS";
    public static Integer segundosInactividad;
    public static String rutaImagenes;
    public static String imagenDefecto;
    public static String formatoFecha;
    public static String formatoHora;
    public static String separadorDecimal;
    public static String separadorGrupos;
    public static Integer numeroDecimales;
    public static String iseRestUrl = "http://127.0.0.1:8080/comerzzia-inStoreEngine/ws/";
    public static long segundosPeticionNotificaciones = 30;
    public static long milisegundosPeticionPeso = 100;
    public static String rutasBasePaquetesContextoSpring = null;
    public static Boolean efectoFondoVentanaModales = true;
    public static Boolean mostrarPreloader = true;
    public static Boolean mostrarTecladoAlfanumerico = false;
    public static SystemProperties systemProperties;
    public static String documentoVenta;
    public static KeyCodesInfo keyCodesInfo;
    public static List<String> ocultarBotones = new ArrayList<String>();
    public static Long accionFidelizado = 221l;
    public static String applicationClassName; 
    public static String preloaderClassName;
    public static boolean showCashOpeningUser = true;
    
    private static final String XML_LOGIN      = "login";
    private static final String XML_BASEDATOS   = "BaseDatos";
    private static final String TAG_DRIVER      = "Driver";
    private static final String TAG_URL         = "URL";
    private static final String TAG_USUARIO     = "Usuario";
    private static final String TAG_PASSWORD    = "Password";
    private static final String TAG_AUTOLOGIN    = "Autologin";

    private static final String XML_INTERFAZ            = "Interfaz";
    private static final String TAG_ANCHO               = "Ancho";
    private static final String TAG_ALTO                = "Alto";
    private static final String TAG_PANTALLA_COMPLETA   = "PantallaCompleta";
    private static final String TAG_EFECTO_FONDO_VENTANAS_MODALES   = "EfectoFondoVentanaModales";
    private static final String TAG_MOSTRAR_PRELOADER   = "MostrarPreloader";
    private static final String TAG_MOSTRAR_TECLADO_ALFANUMERICO= "MostrarTecladoAlfanumerico";
    private static final String TAG_OCULTAR_CONFIGURACION = "OcultarConfiguracion";
    private static final String TAG_SHOW_CASH_OPENING_USER = "ShowCashOpeningUser";

    private static final String TAG_SKIN   = "Skin";
    private static final String TAG_ACCION_INICIO   = "AccionInicio";

    private static final String XML_DESARROLLO      = "Desarrollo";
    
    private static final String XML_LOCALIZACION     	= "Localizacion";
    private static final String XML_IDIOMA      		= "idioma";
    private static final String XML_PAIS      			= "pais";
    private static final String XML_FORMATO_FECHA      	= "formatoFecha";
    private static final String XML_FORMATO_HORA      	= "formatoHora";
    private static final String XML_SEPARADOR_DECIMAL   = "separadorDecimal";
    private static final String XML_SEPARADOR_GRUPOS    = "separadorGrupos";
    private static final String XML_NUMERO_DECIMALES   	= "numeroDecimales";
    
    private static final String XML_APLICACION = "Aplicacion";
    private static final String XML_MENU  = "Menu";
    private static final String XML_ACCION_FIDELIZADO = "AccionFidelizado";

    private static final String TAG_SEGUNDOS_INACTIVIDAD = "SegundosInactividad";

    private static final String XML_IMAGENES_ARTICULOS = "ImagenesArticulos";
    private static final String TAG_RUTA_IMAGENES = "RutaImagenes";
    private static final String TAG_IMAGEN_DEFECTO = "ImagenDefecto";
    
    private static final String XML_ISE = "InStoreEngine";
    private static final String TAG_REST_URL = "RestUrl";
    private static final String TAG_TIEMPO_NOTIFICACIONES = "SegundosPeticionNotificaciones";
    
    private static final String TAG_RUTAS_BASE_PAQUETES_CONTEXTO_SPRING = "RutasBasePaquetesContextoSpring";
    
    private static final String XML_SYSTEM_PROPERTIES = "SystemProperties";
    
    private static final String TAG_DISABLE_JAXB_FASTBOOT = "DesactivaJaxbFastboot";
    
    private static final String TAG_DOCUMENTO_VENTA = "DocumentoVenta";
    
    private static final String TAG_APPLICATION_POSAPPLICATION = "POSApplication";
    private static final String TAG_APPLICATION_POSPRELOADER = "POSPreloader";
    
//    private ComerzziaApp comerzziaApp;
    
//    @Bean 
//	public ComerzziaApp getComerzziaApp() {
//		return comerzziaApp;
//	}
	
//	@Bean
//    public SqlSessionFactory sqlSessionFactory() {
//		log.info("********************** OBTENIENDO SQLSESSIONFACTORY **********************");
//        return Database.getConnectionProvider().getSpringSqlSessionFactory();
//    }
//	    
//	@Bean
//    public DataSourceTransactionManager dataSourceTransactionManager(){
//    	log.info("********************** OBTENIENDO DataSourceTransactionManager **********************");
//    	return new DataSourceTransactionManager(Database.getConnectionProvider().getDataSource());
//    }
	
	public AppConfig() {
		log.info("Construyendo AppConfig");
	}
    
    /**
     * Carga los parametros de configuración de la aplicacion a partir del
     * fichero de configuración comerzzia/comerzzia.xml
     * @throws XMLDocumentException 
     */
//	@PostConstruct
//    private void init() throws XMLDocumentException {        
//        comerzziaApp = ComerzziaApp.get(); 
//				
//		URL url = comerzziaApp.obtenerUrlFicheroConfiguracion("comerzzia-pos.xml");
//        
//		log.info("Usando URL [" + url.toString() + "] para configuración de la aplicación");
//        
//		XMLDocument xmlDocument = new XMLDocument(url);
//        
//        XMLDocumentNode nodeRoot = xmlDocument.getRoot();
//        XMLDocumentNode node = nodeRoot.getNodo(XML_BASEDATOS);
//        interfazInfo = new InterfazInfo();
//        DBInfo dataBaseInfo = new DBInfo();
//        dataBaseInfo.setDatabaseDriverClass(node.getNodo(TAG_DRIVER).getValue());
//        dataBaseInfo.setDatabaseUrl(node.getNodo(TAG_URL).getValue());
//        dataBaseInfo.setDatabaseUsuario(node.getNodo(TAG_USUARIO ).getValue());       
//        dataBaseInfo.setDatabasePassword(node.getNodo(TAG_PASSWORD).getValue());
//        dataBaseInfo.setDatabaseMaxConexionesActivas(5);
//        
//        ComerzziaApp.get().setDbInfo(dataBaseInfo);
//        
//        node = nodeRoot.getNodo(XML_INTERFAZ,true);
//        if(node!=null){
//        	
//        	XMLDocumentNode nodeAlto = node.getNodo(TAG_ALTO, true);
//        	XMLDocumentNode nodeAncho = node.getNodo(TAG_ANCHO, true);
//        	if(nodeAlto!=null && nodeAncho!=null){
//        		interfazInfo.setAlto(nodeAlto.getValueAsInteger());
//        		interfazInfo.setAncho(nodeAncho.getValueAsInteger());
//        		XMLDocumentNode nodePantallaCompleta = node.getNodo(TAG_PANTALLA_COMPLETA, true);
//            	if(nodePantallaCompleta!=null){
//            		interfazInfo.setPantallaCompleta(nodePantallaCompleta.getValueAsBoolean());
//            	}
//            	else{
//            		interfazInfo.setPantallaCompleta(true);
//            	}
//        	}
//        	else{
//        		interfazInfo.setPantallaCompleta(true);
//        	}  
//        	XMLDocumentNode nodo = node.getNodo(TAG_EFECTO_FONDO_VENTANAS_MODALES, true);
//        	if(nodo != null && nodo.getValue() != null){
//        		if(nodo.getValue().equals("false") || nodo.getValue().equals("N")){
//        			efectoFondoVentanaModales = false;
//        		}
//        	}
//        	nodo = node.getNodo(TAG_MOSTRAR_PRELOADER, true);
//        	if(nodo != null && nodo.getValue() != null){
//        		if(nodo.getValue().equals("false") || nodo.getValue().equals("N")){
//        			mostrarPreloader = false;
//        		}
//        	}
//        	nodo = node.getNodo(TAG_MOSTRAR_TECLADO_ALFANUMERICO, true);
//        	if(nodo != null && nodo.getValue() != null){
//        		if(nodo.getValue().equals("true") || nodo.getValue().equals("S")){
//        			mostrarTecladoAlfanumerico = true;
//        		}
//        	}
//        	nodo = node.getNodo(TAG_OCULTAR_CONFIGURACION, true);
//        	if(nodo != null && nodo.getValue() != null && !nodo.getHijos().isEmpty()){
//        		for(XMLDocumentNode nodoBoton : nodo.getHijos()){
//        			String valor = nodoBoton.getValue();
//        			ocultarBotones.add(valor);
//        		}
//        	}
//        	nodo = node.getNodo(TAG_SHOW_CASH_OPENING_USER, true);
//        	if(nodo != null && StringUtils.isNotBlank(nodo.getValue())){
//        		if(nodo.getValue().equals("false") || nodo.getValue().equals("N")){
//        			showCashOpeningUser = false;
//        		}
//        	}
//        }
//        else{
//        	interfazInfo.setPantallaCompleta(true);
//        }
//
//        node = nodeRoot.getNodo(XML_LOGIN, true);
//        if (node == null || node.getValue().isEmpty()){
//        	loginBotonera = true;
//        }
//        else{
//        	loginBotonera = node.getValue().equals("BIENVENIDA");
//        }
//        
//        try{
//            node = nodeRoot.getNodo(XML_DESARROLLO);
//            modoDesarrollo = true;
//        }
//        catch(XMLDocumentNodeNotFoundException e){
//            modoDesarrollo = false;
//        }
//        if (modoDesarrollo){
//            modoDesarrolloInfo = new ModoDesarrolloInfo();
//            modoDesarrolloInfo.setUsuario(node.getNodo(TAG_USUARIO).getValue());
//            modoDesarrolloInfo.setPassword(node.getNodo(TAG_PASSWORD).getValue());
//	        XMLDocumentNode nodo = node.getNodo(TAG_AUTOLOGIN, true);
//	        if (nodo != null) {
//	            modoDesarrolloInfo.setAutologin(nodo.getValueAsBoolean());
//	        }
//        }
//        
//        XMLDocumentNode nodoSkin = nodeRoot.getNodo(TAG_SKIN, true);
//        if(nodoSkin != null){
//        	skin = nodoSkin.getValue();
//        	if(skin == null || skin.isEmpty()){
//        		skin = DEFAULT_SKIN;
//        	}
//        }else{
//        	skin = DEFAULT_SKIN; 
//        }
//
//        XMLDocumentNode nodoAccionInicio = nodeRoot.getNodo(TAG_ACCION_INICIO,true);
//        try{
//        	if(nodoAccionInicio != null){
//        		accionInicio = new Long(nodoAccionInicio.getValue());
//        	}
//        }catch(NumberFormatException e){
//        	log.error("load() - Error al convertir a Long la cadena: " + nodoAccionInicio.getValue());
//        }
//        
//        XMLDocumentNode nodoAccionFidelizado = nodeRoot.getNodo(XML_ACCION_FIDELIZADO,true);
//        try{
//        	if(nodoAccionFidelizado != null){
//        		accionFidelizado = new Long(nodoAccionFidelizado.getValue());
//        	}
//        }catch(NumberFormatException e){
//        	log.error("load() - Error al convertir a Long la cadena: " + nodoAccionFidelizado.getValue());
//        }
//
//        Locale locale = Locale.getDefault();
//		idioma = locale.getLanguage();
//		pais = locale.getCountry();
//        XMLDocumentNode nodoLocalizacion = nodeRoot.getNodo(XML_LOCALIZACION, true);
//    	if(nodoLocalizacion != null) {
//	        XMLDocumentNode nodoIdioma = nodoLocalizacion.getNodo(XML_IDIOMA, true); 
//	    	if(nodoIdioma != null && !nodoIdioma.getValue().isEmpty()) {
//	    		idioma = nodoIdioma.getValue();
//	    	}
//	    	
//	    	XMLDocumentNode nodoPais = nodoLocalizacion.getNodo(XML_PAIS, true);
//	    	if(nodoPais != null && !nodoPais.getValue().isEmpty()) {
//	    		pais = nodoPais.getValue().toLowerCase();
//	    	}
//	    	
//	    	XMLDocumentNode nodoFormatoFecha = nodoLocalizacion.getNodo(XML_FORMATO_FECHA, true);
//	    	if(nodoFormatoFecha != null) {
//	    		formatoFecha = nodoFormatoFecha.getValue();
//	    	}
//	    	
//	    	XMLDocumentNode nodoFormatoHora = nodoLocalizacion.getNodo(XML_FORMATO_HORA, true);
//	    	if(nodoFormatoHora != null) {
//	    		formatoHora = nodoFormatoHora.getValue();
//	    	}
//	    	
//	    	XMLDocumentNode nodoSeparadorDecimal = nodoLocalizacion.getNodo(XML_SEPARADOR_DECIMAL, true);
//	    	if(nodoSeparadorDecimal != null) {
//	    		separadorDecimal = nodoSeparadorDecimal.getValue();
//	    	}
//	    	
//	    	XMLDocumentNode nodoSeparadorGrupos = nodoLocalizacion.getNodo(XML_SEPARADOR_GRUPOS, true);
//	    	if(nodoSeparadorGrupos != null) {
//	    		separadorGrupos = nodoSeparadorGrupos.getValue();
//	    	}
//	    	
//	    	XMLDocumentNode nodoNumeroDecimales = nodoLocalizacion.getNodo(XML_NUMERO_DECIMALES, true);
//	    	if(nodoNumeroDecimales != null) {
//	    		try {
//	                numeroDecimales = Integer.valueOf(nodoNumeroDecimales.getValue());
//                }
//                catch (NumberFormatException e) {
//                	log.error("El número de decimales introducido no es correcto");
//                }
//	    	}
//    	}
//    	if(formatoFecha == null && idioma.equalsIgnoreCase("es")){
//			formatoFecha = "dd/MM/yyyy";
//    	}
//    	
//    	if(formatoHora == null && idioma.equalsIgnoreCase("es")){
//    		formatoHora = "HH:mm";
//    	}
//        
//    	XMLDocumentNode nodoAplicacion = nodeRoot.getNodo(XML_APLICACION, true);
//    	if(nodoAplicacion != null){
//    		aplicacion = nodoAplicacion.getValue();
//    	}
//        XMLDocumentNode nodoMenu = nodeRoot.getNodo(XML_MENU, true);
//        if(nodoMenu != null){
//        	menu = nodoMenu.getValue();
//        }
//
//        XMLDocumentNode nodoSegundosInactividad = nodeRoot.getNodo(TAG_SEGUNDOS_INACTIVIDAD,true);
//        try{
//        	if(nodoSegundosInactividad != null){
//        		segundosInactividad = new Integer(nodoSegundosInactividad.getValue());
//        	}
//        }catch(NumberFormatException e){
//        	log.error("load() - Error al convertir a Integer la cadena: " + nodoSegundosInactividad.getValue());
//        }
//
//        node = nodeRoot.getNodo(XML_IMAGENES_ARTICULOS, true);
//        if(node != null) {
//        	XMLDocumentNode nodoRutaImagenes = node.getNodo(TAG_RUTA_IMAGENES,true);
//        	if(nodoRutaImagenes != null){
//        		rutaImagenes = nodoRutaImagenes.getValue();
//        	}
//        	
//        	XMLDocumentNode nodoImagenDefecto = node.getNodo(TAG_IMAGEN_DEFECTO,true);
//        	if(nodoImagenDefecto != null){
//        		imagenDefecto = nodoImagenDefecto.getValue();
//        	}
//        }
//        
//        node = nodeRoot.getNodo(XML_ISE, true);
//        if(node != null) {
//        	XMLDocumentNode nodo = node.getNodo(TAG_REST_URL, true);
//        	if(nodo != null){
//        		if(nodo.getValue().isEmpty()){
//        			segundosPeticionNotificaciones = 0;
//        		}else{
//	        		iseRestUrl = nodo.getValue();
//	        		if(!iseRestUrl.endsWith("/")){
//	        			iseRestUrl += "/";
//	        		}
//        		}
//        	}
//        	nodo = node.getNodo(TAG_TIEMPO_NOTIFICACIONES, true);
//	    	if(nodo != null){
//	    		if(nodo.getValue().isEmpty()){
//	    			segundosPeticionNotificaciones = 0;
//	    		}else{
//		    		try{
//		    			Long segundos = nodo.getValueAsLong();
//		    			segundosPeticionNotificaciones = segundos;
//		    		}catch(NumberFormatException e){
//		    		}
//	    		}
//	    	}
//        }
//        
//        node = nodeRoot.getNodo(TAG_RUTAS_BASE_PAQUETES_CONTEXTO_SPRING, true);
//        if(node != null) {
//        	if(!node.getValue().isEmpty()){
//        		rutasBasePaquetesContextoSpring = node.getValue();
//        	}
//        }
//        node = nodeRoot.getNodo(TAG_APPLICATION_POSAPPLICATION, true);
//        if(node != null && node.getValue() != null){
//    		applicationClassName = node.getValue();
//        }
//        node = nodeRoot.getNodo(TAG_APPLICATION_POSPRELOADER, true);
//        if(node != null && node.getValue() != null){
//    		preloaderClassName = node.getValue();
//        }
//        node = nodeRoot.getNodo(TAG_DOCUMENTO_VENTA, true);
//        if(node != null) {
//        	if(!node.getValue().isEmpty()){
//        		documentoVenta = node.getValue();
//        	}
//        }
//        
//        keyCodesInfo = KeyCodesInfo.parse(nodeRoot);
//        
//        boolean useJaxbFastboot = true;
//        node = nodeRoot.getNodo(TAG_DISABLE_JAXB_FASTBOOT, true);
//    	if(node != null && node.getValue() != null){
//    		if(node.getValue().equals("true") || node.getValue().equals("S")){
//    			useJaxbFastboot = false;
//    		}
//    	}
//    	if (useJaxbFastboot) {
//    		MarshallUtil.setFastBoot();
//    	}
//        
//        systemProperties = new SystemProperties();
//        try{
//	        //Añadimos la misma ruta que contiene al pos_config.xml como java.library.path (carpeta config)
//	        //Añadimos también la carpeta lib-ext al mismo nivel que config
//	        //y añadimos lo que venga en las SystemProperties del XML como java.library.path
//			URL resource = Thread.currentThread().getContextClassLoader().getResource(TPVConfig.POS_CONFIG_NAME);
//			if(resource != null){
//				String path = resource.toURI().toString();
//				String separator = path.contains("/")? "/" : "\\";
//				File configFile = new File(new URL(path.substring(0, path.lastIndexOf(separator))).toURI());
//		        systemProperties.readProperties(nodeRoot.getNodo(XML_SYSTEM_PROPERTIES, true));
//		        String javaLibraryPath = systemProperties.getProperties().get("java.library.path"); 
//		        if(javaLibraryPath != null){
//		        	log.info("Añadida la siguiente ruta como \"java.library.path\": " + javaLibraryPath);
//		        }
//		        log.info("Añadida la siguiente ruta como \"java.library.path\": " + configFile.getAbsolutePath());
//		        String configLevelPath = configFile.getAbsolutePath().substring(0, configFile.getAbsolutePath().lastIndexOf(File.separator));
//		        String libExtPath = configLevelPath + File.separator + "lib" + File.separator +  "ext";
//		        log.info("Añadida la siguiente ruta como \"java.library.path\": " + libExtPath);
//		        
//		        systemProperties.getProperties().put("java.library.path", 
//		        		System.getProperties().get("java.library.path") + ";" + javaLibraryPath + ";"  + configFile.getAbsolutePath()+";" + libExtPath + ";");
//		        	//La clase ClassLoader cachea la variable java.library.path por lo que la cambiamos a null para surta efecto la linea anterior
//			        Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
//			        fieldSysPath.setAccessible(true);
//			        fieldSysPath.set(null, null);
//			}
//		}catch(Exception e){
//			log.info("Error al añadir al cambiar la variable java.library.path - load() - " + e.getClass().getName() + " - " + e.getLocalizedMessage(), e);
//		}
//        
//        addSystemProperties();
//        addTranslationPropertiesNames();
//    }

    public static String getOptValue(XMLDocumentNode node, String tag) throws XMLDocumentNodeNotFoundException {
    	if(node != null) {
    		XMLDocumentNode nodeValue = node.getNodo(tag, true);
        	if(nodeValue != null && nodeValue.getValue() != null && !nodeValue.getValue().isEmpty()){
        		return nodeValue.getValue();
        	}
        }
    	return null;
    }
    
    protected void addTranslationPropertiesNames() {
		I18N.addTranslationPropertiesBaseName("skins." + DEFAULT_SKIN + ".com.comerzzia.pos.core.gui.i18n.cmz-pos-core");
		if(!AppConfig.DEFAULT_SKIN.equals(skin)){
    		I18N.addTranslationPropertiesBaseName("skins." + skin + ".com.comerzzia.pos.core.gui.i18n.cmz-pos-core");
    		I18N.addTranslationPropertiesBaseNameExt("skins." + skin + ".com.comerzzia.pos.core.gui.i18n.cmz-pos-core-ext");
    	}
	} 
    
    protected void addSystemProperties() {
		Map<String, String> properties = systemProperties.getProperties();
		
		for (Entry<String, String> entry : properties.entrySet()) {
			System.setProperty(entry.getKey(), entry.getValue());
		}
		
		if (log.isDebugEnabled()) {
			Properties props = System.getProperties();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			pw.println();
			for (Enumeration<Object> e = props.keys() ; e.hasMoreElements() ;) {
	            String key = (String)e.nextElement();
	            String val = (String)props.get(key);
	            pw.println(key + "=" + val);
	        }
			StringBuffer sb = sw.getBuffer();
			log.debug("main() - Listando propiedes del sistema:");
			log.debug(sb);			
		}

	}
}
