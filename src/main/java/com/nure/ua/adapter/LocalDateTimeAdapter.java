package com.nure.ua.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public JsonElement serialize(LocalDateTime ld, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(Timestamp.valueOf(ld));
    }

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return ((Timestamp) jsonDeserializationContext.deserialize(jsonElement, Timestamp.class)).toLocalDateTime();
    }
}
