package com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument;

import java.util.Arrays;
import java.util.Objects;

public class BricodepotPrintedDocument {

	private final String documentUid;
	private final String fileName;
	private final String mimeType;
	private final byte[] content;

	public BricodepotPrintedDocument(String documentUid, String fileName, String mimeType, byte[] content) {
		this.documentUid = documentUid;
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.content = content != null ? Arrays.copyOf(content, content.length) : new byte[0];
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

	public byte[] getContent() {
		return Arrays.copyOf(content, content.length);
	}

	public int getContentLength() {
		return content.length;
	}

	@Override
	public int hashCode() {
		return Objects.hash(documentUid, fileName, mimeType, Arrays.hashCode(content));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		BricodepotPrintedDocument other = (BricodepotPrintedDocument) obj;
		return Objects.equals(this.documentUid, other.documentUid) && Objects.equals(this.fileName, other.fileName) && Objects.equals(this.mimeType, other.mimeType)
		        && Arrays.equals(this.content, other.content);
	}

	@Override
	public String toString() {
		return "BricodepotPrintedDocument{" + "documentUid='" + documentUid + '\'' + ", fileName='" + fileName + '\'' + ", mimeType='" + mimeType + '\'' + ", contentLength=" + content.length + '}';
	}
}
