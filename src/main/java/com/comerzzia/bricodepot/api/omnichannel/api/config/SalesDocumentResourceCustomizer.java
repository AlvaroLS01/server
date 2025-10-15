package com.comerzzia.bricodepot.api.omnichannel.api.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument.DocumentoVentaImpresionController;

/**
 * Replaces the standard omnichannel sales document resource with the
 * Brico-specific implementation so the Jersey endpoints delegate to the
 * customised PDF generation logic.
 */
@Component
public class SalesDocumentResourceCustomizer implements BeanDefinitionRegistryPostProcessor, Ordered {

    private static final String SALES_DOCUMENT_RESOURCE_BEAN_NAME = "salesDocumentResource";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (registry.containsBeanDefinition(SALES_DOCUMENT_RESOURCE_BEAN_NAME)) {
            BeanDefinition definition = registry.getBeanDefinition(SALES_DOCUMENT_RESOURCE_BEAN_NAME);
            if (definition instanceof AbstractBeanDefinition) {
                AbstractBeanDefinition abstractDefinition = (AbstractBeanDefinition) definition;
                abstractDefinition.setBeanClass(DocumentoVentaImpresionController.class);
                abstractDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
                return;
            }
        }

        RootBeanDefinition replacementDefinition = new RootBeanDefinition(DocumentoVentaImpresionController.class);
        replacementDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        registry.registerBeanDefinition(SALES_DOCUMENT_RESOURCE_BEAN_NAME, replacementDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // Nothing to customise in the bean factory phase.
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
