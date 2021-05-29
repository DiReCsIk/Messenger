package com.nure.ua.data_container;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.nure.ua.adapter.LocalDateTimeAdapter;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

@Getter
public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 1461054012373027410L;
    private final String commandName;
    private final JsonObject attributes;

    public Request(String commandName) {
        this.commandName = commandName;
        this.attributes = new JsonObject();
    }

    public void putAttribute(String key, Object value, Type type) {
        attributes.add(key, new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).enableComplexMapKeySerialization().create().toJsonTree(value, type));
    }

    public <E> E getAttribute(String key, Class<E> clazz) {
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).enableComplexMapKeySerialization().create().fromJson(attributes.get(key), clazz);
    }

    public String getCommandName() {
        return commandName;
    }
}
