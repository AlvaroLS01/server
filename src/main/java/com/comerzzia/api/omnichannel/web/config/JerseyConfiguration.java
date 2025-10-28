package com.comerzzia.api.omnichannel.web.config;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import com.comerzzia.api.omnichannel.web.rest.salesdoc.SalesDocumentResource;
import com.comerzzia.bricodepot.api.omnichannel.api.web.rest.salesdoc.BricodepotSalesDocumentResource;
import com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument.DocumentoVentaImpresionFilter;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import io.swagger.v3.core.util.Json;

@Component
public class JerseyConfiguration extends ResourceConfig {

	private static final Logger log = LoggerFactory.getLogger(JerseyConfiguration.class);

	@Autowired
	public JerseyConfiguration(ApplicationContext applicationContext, DocumentoVentaImpresionFilter documentoVentaImpresionFilter) {
		log.info("JerseyConfiguration() - Configuring REST services...");

		Json.mapper().registerModule(new JaxbAnnotationModule());

		packages("io.swagger.v3.jaxrs2.integration.resources");

		register(documentoVentaImpresionFilter);
		registerCustomResources(applicationContext);
		registerStandardResources();
	}

	private void registerCustomResources(ApplicationContext applicationContext) {
		BricodepotSalesDocumentResource resource = applicationContext.getBean(BricodepotSalesDocumentResource.class);
		log.info("registering {} to jersey config", resource.getClass().getName());
		register(resource);
	}

	private void registerStandardResources() {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(Path.class));
		provider.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
		provider.findCandidateComponents("com.comerzzia.api").forEach(beanDefinition -> {
			String className = beanDefinition.getBeanClassName();
			try {
				Class<?> candidateClass = Class.forName(className);
				if (SalesDocumentResource.class.equals(candidateClass)) {
					log.info("Skipping registration {}", className);
					return;
				}
				log.info("registering {} to jersey config", className);
				register(candidateClass);
			}
			catch (ClassNotFoundException e) {
				log.warn("Failed to register: {}", className, e);
			}
		});
	}
}
