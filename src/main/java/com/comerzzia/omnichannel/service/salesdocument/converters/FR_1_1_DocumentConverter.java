package com.comerzzia.omnichannel.service.salesdocument.converters;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.omnichannel.model.documents.sales.FR_1_1_Document;

@Component
@Scope("prototype")
public class FR_1_1_DocumentConverter extends AbstractTicketVentaAbonoConverter<FR_1_1_Document> {
}