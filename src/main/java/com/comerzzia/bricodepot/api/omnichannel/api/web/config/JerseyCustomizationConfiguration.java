package com.comerzzia.bricodepot.api.omnichannel.api.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument.DocumentoVentaImpresionFilter;
import com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument.DocumentoVentaImpresionServicio;

@Configuration
public class JerseyCustomizationConfiguration {

    @Bean
    public DocumentoVentaImpresionFilter documentoVentaImpresionFilter(
            DocumentoVentaImpresionServicio servicioImpresion) {
        return new DocumentoVentaImpresionFilter(servicioImpresion);
    }
}
