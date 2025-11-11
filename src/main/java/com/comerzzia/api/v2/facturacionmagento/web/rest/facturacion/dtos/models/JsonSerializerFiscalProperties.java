package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;


import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JsonSerializerFiscalProperties extends StdSerializer<FiscalDataItems> {

    public JsonSerializerFiscalProperties() {
        this(null);
    }

    public JsonSerializerFiscalProperties(Class<FiscalDataItems> t) {
        super(t);
    }

    @Override
    public void serialize(FiscalDataItems items, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        for (FiscalProperty fiscalDataItem : items.getFiscalDataItem()) {
            gen.writeObjectFieldStart("fiscalDataItem");
            gen.writeStringField("name", fiscalDataItem.getName());
            gen.writeStringField("value", fiscalDataItem.getValue());
            gen.writeEndObject();
        }
        gen.writeEndObject();
    }
}