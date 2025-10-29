package com.exercicio.social.core;

import java.util.Collections;
import java.util.Map;

public class UnifiedResponse<T> {
    private final boolean success;
    private final String message;
    private final T payload;
    private final Map<String, Object> meta;

    public UnifiedResponse(boolean success, String message, T payload) {
        this(success, message, payload, Collections.emptyMap());
    }

    public UnifiedResponse(boolean success, String message, T payload, Map<String, Object> meta) {
        this.success = success;
        this.message = message;
        this.payload = payload;
        this.meta = meta;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getPayload() { return payload; }
    public Map<String, Object> getMeta() { return meta; }

    @Override
    public String toString() {
        return "UnifiedResponse{success=" + success + ", message='" + message + "', payload=" + payload + ", meta=" + meta + "}";
    }
}
