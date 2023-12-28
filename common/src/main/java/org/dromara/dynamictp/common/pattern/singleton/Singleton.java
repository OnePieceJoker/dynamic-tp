package org.dromara.dynamictp.common.pattern.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Singleton {

    INST;

    private static final Map<String, Object> SINGLES = new ConcurrentHashMap<>();

    public void single(final Class<?> clazz, final Object o) {
        SINGLES.put(clazz.getName(), o);
    }

    @SuppressWarnings("all")
    public <T> T get(final Class<T> clazz) {
        return (T) SINGLES.get(clazz.getName());
    }
    
}
