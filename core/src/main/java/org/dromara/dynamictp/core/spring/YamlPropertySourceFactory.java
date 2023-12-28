package org.dromara.dynamictp.core.spring;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
        factoryBean.setResources(resource.getResource());
        Properties properties = factoryBean.getObject();
        if (Objects.isNull(properties) 
                || StringUtils.isBlank(resource.getResource().getFilename())) {
            return null;
        }
        return new PropertiesPropertySource(resource.getResource().getFilename(), properties);
    }
    
}
