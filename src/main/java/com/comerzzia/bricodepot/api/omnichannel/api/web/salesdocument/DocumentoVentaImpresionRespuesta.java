package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentoVentaImpresionRespuesta {

        @JsonProperty("mimeType")
        private final String mimeType;

        @JsonProperty("fileName")
        private final String fileName;

        @JsonProperty("inline")
        private final boolean inline;

        @JsonProperty("copy")
        private final boolean copy;

        @JsonProperty("content")
        private final String content;

        public DocumentoVentaImpresionRespuesta(String mimeType, String fileName, boolean inline, boolean copy, String content) {
                this.mimeType = mimeType;
                this.fileName = fileName;
                this.inline = inline;
                this.copy = copy;
                this.content = content;
        }

        public String getMimeType() {
                return mimeType;
        }

        public String getFileName() {
                return fileName;
        }

        public boolean isInline() {
                return inline;
        }

        public boolean isCopy() {
                return copy;
        }

        public String getContent() {
                return content;
        }
}
