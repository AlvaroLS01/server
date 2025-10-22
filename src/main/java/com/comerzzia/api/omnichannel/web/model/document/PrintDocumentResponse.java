package com.comerzzia.api.omnichannel.web.model.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "PDF content encoded in Base64")
public class PrintDocumentResponse {
        @Schema(description = "Unique identifier for the source sales document")
        private String documentUid;

        @Schema(description = "Suggested file name for the generated document")
        private String documentName;

        @Schema(description = "Mime type for the generated content", defaultValue = "application/pdf")
        private String mimeType;

        @Schema(description = "Base64 encoded PDF content")
        private String content;

        @Schema(description = "Indicates whether the generated document corresponds to a copy")
        private Boolean copy;
}
