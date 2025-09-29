package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

public class SalesDocumentPrintResponse {

    private final String fileName;
    private final String mimeType;
    private final String content;

    public SalesDocumentPrintResponse(String fileName, String mimeType, String content) {
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getContent() {
        return content;
    }
}

