package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class SalesDocumentPrintRequest {

    private final boolean copy;
    private final String templateOverride;
    private final String outputDocumentName;
    private final Map<String, Object> customParams;

    private SalesDocumentPrintRequest(Builder builder) {
        this.copy = builder.copy;
        this.templateOverride = builder.templateOverride;
        this.outputDocumentName = builder.outputDocumentName;
        this.customParams = Collections.unmodifiableMap(new LinkedHashMap<>(builder.customParams));
    }

    public boolean isCopy() {
        return copy;
    }

    public String getTemplateOverride() {
        return templateOverride;
    }

    public String getOutputDocumentName() {
        return outputDocumentName;
    }

    public Map<String, Object> getCustomParams() {
        return customParams;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private boolean copy;
        private String templateOverride;
        private String outputDocumentName;
        private Map<String, Object> customParams = new LinkedHashMap<>();

        public Builder copy(boolean copy) {
            this.copy = copy;
            return this;
        }

        public Builder templateOverride(String templateOverride) {
            this.templateOverride = templateOverride;
            return this;
        }

        public Builder outputDocumentName(String outputDocumentName) {
            this.outputDocumentName = outputDocumentName;
            return this;
        }

        public Builder addCustomParam(String key, Object value) {
            Objects.requireNonNull(key, "key");
            if (value != null) {
                this.customParams.put(key, value);
            }
            return this;
        }

        public Builder customParams(Map<String, Object> customParams) {
            this.customParams.clear();
            if (customParams != null) {
                this.customParams.putAll(customParams);
            }
            return this;
        }

        public SalesDocumentPrintRequest build() {
            return new SalesDocumentPrintRequest(this);
        }
    }
}

