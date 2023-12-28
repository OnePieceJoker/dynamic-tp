package org.dromara.dynamictp.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.CollectionUtils;

public class ExtensionServiceLoader {

    private static final Map<Class<?>, List<?>> EXTENSION_MAP = new ConcurrentHashMap<>();

    private ExtensionServiceLoader() {}

    @SuppressWarnings("unchecked")
    public static <T> List<T> get(Class<T> clazz) {
        List<T> services = (List<T>) EXTENSION_MAP.get(clazz);
        if (CollectionUtils.isEmpty(services)) {
            services = load(clazz);
            if (CollectionUtils.isNotEmpty(services)) {
                EXTENSION_MAP.put(clazz, services);
            }
        }
        return services;
    }

    public static <T> T getFirst(Class<T> clazz) {
        List<T> services = get(clazz);
        return CollectionUtils.isEmpty(services) ? null : services.get(0);
    }

    private static <T> List<T> load(Class<T> clazz) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
        List<T> services = new ArrayList<>();
        for (T service : serviceLoader) {
            services.add(service);
        }
        return services;
    }
    
}
