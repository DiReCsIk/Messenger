package com.nure.ua.data_container;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.nure.ua.adapter.LocalDateTimeAdapter;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

import static com.nure.ua.util.constant.UtilConstants.SESSION;

@Getter
public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = 4572101357034175123L;
    private final JsonObject attributes;

    public Response(Session session) {
        this.attributes = new JsonObject();
        putAttribute(SESSION, session, Session.class);
    }

    public boolean hasAttribute(String key) {
        return attributes.has(key);
    }

    public void putAttribute(String key, Object value, Type type) {
        attributes.add(key, new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).enableComplexMapKeySerialization().create().toJsonTree(value, type));
    }

    public <E> E getAttribute(String key, Type type) {
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).enableComplexMapKeySerialization().create().fromJson(attributes.get(key), type);
    }
}
