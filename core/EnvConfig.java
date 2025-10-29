package com.exercicio.social.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EnvConfig {
    private final Map<String, String> values = new ConcurrentHashMap<>();

    public String get(String key) {
        String v = values.get(key);
        if (v != null) return v;
        return System.getenv(key);
    }

    public void set(String key, String value) {
        values.put(key, value);
    }

    public boolean has(String key) {
        return get(key) != null;
    }
}
