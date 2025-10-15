package com.comerzzia.pos.services;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Clase que contiene una referencia el contexto de spring de la aplicacion Se
 * ejecuta como post-construct de Spring el metodo setApplicationContext y de
 * esa forma se asigna. Se encuentra deprecado porque la opcion correcta es
 * inyectar el ApplicationContext de Spring
 *
 */
@Component("CalculatorContextHolder")
@Lazy(false)
public class ContextHolder implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	public static ApplicationContext get() {
		if (applicationContext == null) {
			throw new IllegalStateException("No se ha inicializado el contexto de Spring");
		}

		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ContextHolder.applicationContext = applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		if (name.contains(".")) {
			Class<?> clazz;
			try {
				clazz = (Class<?>) Class.forName(name);
			} catch (ClassNotFoundException e2) {
				throw new RuntimeException(e2);
			}
			try {
				return (T)applicationContext.getBean(clazz);
			} catch (NoSuchBeanDefinitionException e) {
				try {
					return (T)clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e1) {
					throw new RuntimeException(e1);
				}
			}
		} else {
			return (T)applicationContext.getBean(name);
		}
	}
	
	@SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> tipoRequerido, Object... args) {
		try{
			//Busca en el contexto por tipo
			return applicationContext.getBean(tipoRequerido, args);
		}catch(NoSuchBeanDefinitionException e){
			//Si falla buscaremos en el contexto por el nombre por defecto del Bean (nombre de la clase con primera letra minúscula)
			String[] split = tipoRequerido.getName().split("\\.");
			String name = split[split.length-1];
			name = Character.toLowerCase(name.charAt(0)) + name.substring(1, name.length());
			return (T) applicationContext.getBean(name, args);
		}
	}
}
