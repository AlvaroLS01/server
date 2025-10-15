package com.comerzzia.pos.util.config;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;

import com.comerzzia.pos.services.ContextHolder;

/**
 * <p>
 * Clase que contiene el contexto Spring de la aplicación, con identificador "comerzzia", necesario para utilizar la inyección de dependencia.
 * Esta clase escanea el paquete com.comerzzia y busca los componentes y las clases de configuración para inicializar los
 * beans que podrán ser utilizados en cualquier momento.
 * </p>
 * <p>
 * Esta clase no inicializa los beans en el momento de la búsqueda inicial para evitar operaciones propias de JavaFX fuera del 
 * hilo principal de la aplicación, por lo que se realiza la inicialización en el momento que sea necesario un determinado bean.
 * </p>
 */
//@ComponentScan(basePackages = "com.comerzzia", lazyInit = true, 
//@ComponentScan(basePackages = {"com.comerzzia.core.servicios", "com.comerzzia.pos"}, lazyInit = true,
//	excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.comerzzia\\.omnichannel\\..*"),
//			          @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.comerzzia\\.core\\.omnichannel\\..*"),
//			          @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.comerzzia\\.pos\\.gui\\.config\\.SpringApplicationConfiguration")})
public class SpringContext {
	
    private static final Logger log = Logger.getLogger(SpringContext.class);

//	private static AbstractApplicationContext context;
//	
//	public static void inicializarBeans() {
//		if(context == null) {
//			// Hacemos que escanee los paquetes a partir de esta clase para poder activar la carga lazy de los beans
//			context = new AnnotationConfigApplicationContext(com.comerzzia.core.servicios.ContextHolder.class, SpringContext.class);
//			context.setId("comerzzia");
//		}
//	}
	
	/**
	 * Devuelve un bean del tipo que se le pasa por parámetro. Si encuentra más de un bean del mismo tipo devolverá aquel cuyo nombre
	 * coincida con el de la clase que se pasa.
	 * @param tipoRequerido Clase del objeto
	 * @return Objeto del tipo requerido
	 */
	@SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> tipoRequerido) {
		try{
			//Busca en el contexto por tipo
			return ContextHolder.get().getBean(tipoRequerido);
		}catch(NoSuchBeanDefinitionException e){
			//Si falla buscaremos en el contexto por el nombre por defecto del Bean (nombre de la clase con primera letra minúscula)
			String[] split = tipoRequerido.getName().split("\\.");
			String name = split[split.length-1];
			name = Character.toLowerCase(name.charAt(0)) + name.substring(1, name.length());
			return (T) ContextHolder.get().getBean(name);
		}
	}
	
	/**
	 * Devuelve un bean del tipo que se le pasa por parámetro. Si encuentra más de un bean del mismo tipo devolverá aquel cuyo nombre
	 * coincida con el de la clase que se pasa.
	 * @param tipoRequerido Clase del objeto
	 * @param args Parámetros que se le pasan al constructor
	 * @return Objeto del tipo requerido
	 */
	@SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> tipoRequerido, Object... args) {
		try{
			//Busca en el contexto por tipo
			return ContextHolder.get().getBean(tipoRequerido, args);
		}catch(NoSuchBeanDefinitionException e){
			//Si falla buscaremos en el contexto por el nombre por defecto del Bean (nombre de la clase con primera letra minúscula)
			String[] split = tipoRequerido.getName().split("\\.");
			String name = split[split.length-1];
			name = Character.toLowerCase(name.charAt(0)) + name.substring(1, name.length());
			return (T) ContextHolder.get().getBean(name, args);
		}
	}
	
	/**
	 * Añade al contexto existente los beans de un nuevo contexto.
	 * @param newContext Contexto a añadir
	 */
//	public static void addContext(AbstractApplicationContext newContext) {
//		if(context == null) {
//			inicializarBeans();
//		}
//
//		String[] singletonNames = newContext.getBeanFactory().getSingletonNames();
//		for (String s : singletonNames) {
//			if (!context.getBeanFactory().containsSingleton(s)) {
//				context.getBeanFactory().registerSingleton(s, newContext.getBeanFactory().getSingleton(s));
//            }
//		}
//	}
	
	public static <T> void destroyBean(Class<T> tipoRequerido) {
		String[] split = tipoRequerido.getName().split("\\.");
		String name = split[split.length-1];
		name = Character.toLowerCase(name.charAt(0)) + name.substring(1, name.length());
		ContextHolder.get().getAutowireCapableBeanFactory();
		((DefaultListableBeanFactory) ((AbstractApplicationContext)ContextHolder.get()).getBeanFactory()).destroySingleton(name);
	}
	
	public static <T> Map<String, T> getBeansOfType(Class<T> type) {
		return ContextHolder.get().getBeansOfType(type);
	}

}
