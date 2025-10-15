package com.comerzzia.omnichannel.domain.entity.document;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentContentEntity {
   private String activityUid;
   private String documentUid;
   private byte[] documentContent;
   private String contentUrl;
   private String documentSignature;
   private String documentName;
   private String mimeType;
   private Long documentSize;
}
