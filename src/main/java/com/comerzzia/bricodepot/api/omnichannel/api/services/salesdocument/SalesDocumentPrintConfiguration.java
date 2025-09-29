package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.executor.PdfBoxReportRenderer;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.executor.ReportRenderer;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.repository.EmptySalesDocumentRepository;

@Configuration
@EnableConfigurationProperties(ReportProperties.class)
public class SalesDocumentPrintConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesDocumentPrintConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public SalesDocumentRepository salesDocumentRepository() {
        LOGGER.warn("Using empty SalesDocumentRepository - no tickets will be returned");
        return new EmptySalesDocumentRepository();
    }

    @Bean
    @ConditionalOnMissingBean
    public ReportRenderer reportRenderer(ReportProperties properties) {
        return new PdfBoxReportRenderer(properties);
    }
}

