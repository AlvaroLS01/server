package com.comerzzia.core.servicios;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Clase que contiene una referencia el contexto de spring de la aplicacion Se ejecuta como post-construct de Spring el
 * metodo setApplicationContext y de esa forma se asigna. Se encuentra deprecado porque la opcion correcta es inyectar
 * el ApplicationContext de Spring
 */
@Component
@Lazy(false)
public class ContextHolder implements ApplicationContextAware {

	protected static final Logger log = Logger.getLogger(ContextHolder.class);

	private static ApplicationContext applicationContext;

	public static ApplicationContext get() {
		if (applicationContext == null) {
			throw new IllegalStateException("No se ha inicializado el contexto de Spring");
		}

		return applicationContext;
	}

	protected ContextHolder() {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		log.info("********************** comerzzia Spring context info ****************************");
		log.info("\t Id: " + applicationContext.getId());
		log.info("\t Application: " + applicationContext.getApplicationName());
		log.info("\t Name: " + applicationContext.getDisplayName());
		log.info("*********************************************************************************");
		ContextHolder.applicationContext = applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws ClassNotFoundException {
		if (name.contains(".")) {
			Class<?> clazz = Class.forName(name);
			try {
				return (T) get().getBean(clazz);
			}
			catch (NoSuchBeanDefinitionException e) {
				try {
					return (T) clazz.newInstance();
				}
				catch (InstantiationException | IllegalAccessException e1) {
					throw new RuntimeException(e1);
				}
			}
		}
		else {
			return (T) get().getBean(name);
		}
	}
}
