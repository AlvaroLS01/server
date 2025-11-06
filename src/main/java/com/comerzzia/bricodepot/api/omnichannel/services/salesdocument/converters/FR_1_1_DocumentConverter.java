package com.comerzzia.bricodepot.api.omnichannel.services.salesdocument.converters;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.bricodepot.api.omnichannel.model.FR_1_1_Document;
import com.comerzzia.omnichannel.service.salesdocument.converters.AbstractTicketVentaAbonoConverter;
 
@Component
@Scope("prototype")
public class FR_1_1_DocumentConverter extends AbstractTicketVentaAbonoConverter<FR_1_1_Document> {
}
