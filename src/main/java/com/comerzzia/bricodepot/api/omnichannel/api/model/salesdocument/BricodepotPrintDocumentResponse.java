package com.comerzzia.bricodepot.api.omnichannel.api.model.salesdocument;

public class BricodepotPrintDocumentResponse {

    private final String documentUid;
    private final String fileName;
    private final String mimeType;
    private final String base64Content;
    private final int contentLength;

    public BricodepotPrintDocumentResponse(String documentUid, String fileName, String mimeType, String base64Content, int contentLength) {
        this.documentUid = documentUid;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.base64Content = base64Content;
        this.contentLength = contentLength;
    }

    public String getDocumentUid() {
        return documentUid;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public int getContentLength() {
        return contentLength;
    }
}
