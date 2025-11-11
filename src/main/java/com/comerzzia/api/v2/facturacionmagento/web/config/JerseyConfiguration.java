package com.comerzzia.api.v2.facturacionmagento.web.config;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import io.swagger.v3.core.util.Json;
 
@Component
public class JerseyConfiguration extends ResourceConfig
{
	protected static Logger log = LoggerFactory.getLogger(JerseyConfiguration.class);
	
    public JerseyConfiguration()
    {
    	log.info("JerseyConfiguration() - Configuring REST services...");
    	    	
    	Json.mapper().registerModule(new JaxbAnnotationModule());

    	// Carga de la integracion de swagger con jersey
    	packages("io.swagger.v3.jaxrs2.integration.resources");
    	    
    	// El siguiente codigo es para solucionar la carga de jersey cuando la aplicacion
    	// esta empaquetada y se ejecuta como war/jar
    	// Si se llama al metodo packages con com.comerzzia.api se lanza un error:
    	// Error creating bean with name 'org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration'. File not found ....    	
    	ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Path.class));
        provider.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
     // Lecturas Rest paquete estandar                    
        provider.findCandidateComponents("com.comerzzia.api").forEach(beanDefinition -> {
            try {
                log.info("registering {} to jersey config", beanDefinition.getBeanClassName());
                register(Class.forName(beanDefinition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                log.warn("Failed to register: {}", beanDefinition.getBeanClassName());
            }
        });
    }    
}