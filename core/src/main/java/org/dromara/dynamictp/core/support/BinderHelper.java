package org.dromara.dynamictp.core.support;

import java.util.Map;
import java.util.Objects;

import org.dromara.dynamictp.common.pattern.singleton.Singleton;
import org.dromara.dynamictp.common.properties.DtpProperties;
import org.dromara.dynamictp.common.util.ExtensionServiceLoader;
import org.dromara.dynamictp.core.spring.PropertiesBinder;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinderHelper {

    private BinderHelper() {}

    private static PropertiesBinder getBinder() {
        PropertiesBinder binder = Singleton.INST.get(PropertiesBinder.class);
        if (Objects.nonNull(binder)) {
            return binder;
        }
        final PropertiesBinder loadedFirstBinder = ExtensionServiceLoader.getFirst(PropertiesBinder.class);
        if (Objects.isNull(loadedFirstBinder)) {
            log.error("DynamicTp refresh, no SPI for org.dromara.dynamictp.core.spring.PropertiesBinder.");
            return null;
        }
        Singleton.INST.single(PropertiesBinder.class, loadedFirstBinder);
        return loadedFirstBinder;
    }

    public static void bindDtpProperties(Map<?, Object> properties, DtpProperties dtpProperties) {
        final PropertiesBinder binder = getBinder();
        if (Objects.isNull(binder)) {
            return;
        }
        binder.bindDtpProperties(properties, dtpProperties);
    }

    public static void bindDtpProperties(Environment environment, DtpProperties dtpProperties) {
        final PropertiesBinder binder = getBinder();
        if (Objects.isNull(binder)) {
            return;
        }
        binder.bindDtpProperties(environment, dtpProperties);
    }
    
}

