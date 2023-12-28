package org.dromara.dynamictp.core.spring;

import java.util.Map;

import org.dromara.dynamictp.common.properties.DtpProperties;
import org.springframework.core.env.Environment;

public interface PropertiesBinder {

    /**
     * bind dtp properties
     *
     * @param properties   properties
     * @param dtpProperties dtp properties
     */
    void bindDtpProperties(Map<?, Object> properties, DtpProperties dtpProperties);

    /**
     * bind dtp properties
     *
     * @param environment  environment
     * @param dtpProperties dtp properties
     */
    void bindDtpProperties(Environment environment, DtpProperties dtpProperties); 
}
