package com.ipaas.monitoringplstformsys.common.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MpaasDateTimeDeserializer extends JsonDeserializer<Date> {
    public MpaasDateTimeDeserializer() {
    }

    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(jsonParser.getValueAsString());
        } catch (Exception var4) {
            Exception e = var4;
            e.printStackTrace();
            return null;
        }
    }
}
