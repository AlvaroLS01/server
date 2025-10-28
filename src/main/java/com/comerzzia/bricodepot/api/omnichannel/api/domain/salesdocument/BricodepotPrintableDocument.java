package com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument;

import java.util.Arrays;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;

public class BricodepotPrintableDocument {

	private static final String APPLICATION_PDF = "application/pdf";

	private final String documentUid, fileName, mimeType;
	private final byte[] content;

	public BricodepotPrintableDocument(String documentUid, String fileName, String mimeType, byte[] content) {
		this.documentUid = documentUid;
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.content = content != null ? Arrays.copyOf(content, content.length) : new byte[0];
	}

	public byte[] getContent() {
		return Arrays.copyOf(content, content.length);
	}

	public Response toResponse(String requestedMimeType) {
		String effectiveMimeType = StringUtils.defaultIfBlank(mimeType, requestedMimeType);
		String normalizedFileName = StringUtils.defaultIfBlank(fileName, documentUid);

		if (StringUtils.equalsIgnoreCase(APPLICATION_PDF, effectiveMimeType) && !StringUtils.endsWithIgnoreCase(normalizedFileName, ".pdf")) {
			normalizedFileName = normalizedFileName + ".pdf";
		}

		String base64Content = Base64.getEncoder().encodeToString(content);
		return new Response(documentUid, normalizedFileName, effectiveMimeType, base64Content, content.length);
	}

	public static class Response {

		private final String documentUid, fileName, mimeType, base64Content;
		private final int contentLength;

		public Response(String documentUid, String fileName, String mimeType, String base64Content, int contentLength) {
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
}