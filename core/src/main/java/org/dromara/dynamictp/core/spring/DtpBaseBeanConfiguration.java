package org.dromara.dynamictp.core.spring;

import org.dromara.dynamictp.common.properties.DtpProperties;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class DtpBaseBeanConfiguration {
    
    @Bean
    public DtpProperties dtpProperties() {
        return DtpProperties.getInstance();
    }
}
