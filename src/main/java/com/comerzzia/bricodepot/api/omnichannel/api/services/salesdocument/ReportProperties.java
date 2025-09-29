package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "reports")
public class ReportProperties {

    /**
     * Base path where JRXML templates and subreports live. Defaults to the classpath
     * location used by the legacy applications.
     */
    private String basePath = "classpath:/informes";

    /**
     * Sub-directory that contains the subreports for the invoice.
     */
    private String subreportDirectory = "subreports";

    /**
     * Default prefix used when composing the invoice file name.
     */
    private String defaultDocumentName = "factura";

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getSubreportDirectory() {
        return subreportDirectory;
    }

    public void setSubreportDirectory(String subreportDirectory) {
        this.subreportDirectory = subreportDirectory;
    }

    public String getDefaultDocumentName() {
        return defaultDocumentName;
    }

    public void setDefaultDocumentName(String defaultDocumentName) {
        this.defaultDocumentName = defaultDocumentName;
    }
}

