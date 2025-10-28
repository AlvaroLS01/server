package com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument;

import java.util.Arrays;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;

/**
 * Immutable value object that represents the binary document generated for a
 * sales document print request. The class encapsulates the previous domain and
 * API payload structures, keeping the conversion rules in a single, concise
 * place.
 */
public record BricodepotPrintableDocument(String documentUid, String fileName, String mimeType, byte[] content) {

        private static final String APPLICATION_PDF = "application/pdf";

        public BricodepotPrintableDocument {
                content = content != null ? Arrays.copyOf(content, content.length) : new byte[0];
        }

        @Override
        public byte[] content() {
                return Arrays.copyOf(content, content.length);
        }

        public int contentLength() {
                return content.length;
        }

        /**
         * Builds the response DTO expected by the REST layer while applying common
         * adjustments (file name fallbacks, extension inference, base64 conversion).
         *
         * @param requestedMimeType mime type requested by the consumer (may be blank)
         * @return an immutable response view for the current printable document
         */
        public Response toResponse(String requestedMimeType) {
                String effectiveMimeType = StringUtils.defaultIfBlank(mimeType, requestedMimeType);
                String normalizedFileName = buildResponseFileName(effectiveMimeType);
                String base64Content = Base64.getEncoder().encodeToString(content);

                return new Response(documentUid, normalizedFileName, effectiveMimeType, base64Content, contentLength());
        }

        private String buildResponseFileName(String effectiveMimeType) {
                String result = StringUtils.defaultIfBlank(fileName, documentUid);

                if (StringUtils.equalsIgnoreCase(APPLICATION_PDF, effectiveMimeType)
                                && !StringUtils.endsWithIgnoreCase(result, ".pdf")) {
                        return result + ".pdf";
                }

                return result;
        }

        /**
         * Response projection sent back to API consumers.
         */
        public record Response(String documentUid, String fileName, String mimeType, String base64Content, int contentLength) {
        }
}
