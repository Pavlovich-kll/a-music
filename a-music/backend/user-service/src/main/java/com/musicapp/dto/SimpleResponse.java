package com.musicapp.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Value;

import java.io.IOException;


/**
 * DTO представление для случаев, когда нужен ответ с одним атрибутом
 * propertyName задает имя атрибута
 * propertyValue задает значение атрибута
 */
@Value
@Builder(setterPrefix = "with")
@JsonSerialize(using = SimpleResponse.CustomSerializer.class)
public class SimpleResponse {

    String propertyName;
    Object propertyValue;

    public static class CustomSerializer extends JsonSerializer<SimpleResponse> {

        @Override
        public void serialize(SimpleResponse simpleResponse,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider)
                throws IOException {

            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField(simpleResponse.getPropertyName(), simpleResponse.getPropertyValue());
            jsonGenerator.writeEndObject();
        }
    }
}
