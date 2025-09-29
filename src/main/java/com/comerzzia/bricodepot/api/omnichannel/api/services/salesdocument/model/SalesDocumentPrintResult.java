package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model;

import java.util.Base64;
import java.util.Objects;

public final class SalesDocumentPrintResult {

    private final byte[] pdf;
    private final String fileName;
    private final String mimeType;

    public SalesDocumentPrintResult(byte[] pdf, String fileName, String mimeType) {
        this.pdf = Objects.requireNonNull(pdf);
        this.fileName = Objects.requireNonNull(fileName);
        this.mimeType = Objects.requireNonNull(mimeType);
    }

    public byte[] getPdf() {
        return pdf;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getPdfBase64() {
        return Base64.getEncoder().encodeToString(pdf);
    }
}

