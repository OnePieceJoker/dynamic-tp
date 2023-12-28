package org.dromara.dynamictp.test.core.spring;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.dromara.dynamictp.common.properties.DtpProperties;
import org.dromara.dynamictp.core.spring.YamlPropertySourceFactory;
import org.dromara.dynamictp.core.support.BinderHelper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Maps;

@PropertySource(value = "classpath:/demo-dtp-dev.yml", 
        factory = YamlPropertySourceFactory.class)
@SpringBootTest(classes = PropertiesBinderTest.class)
@EnableAutoConfiguration
// 去掉@RunWith这个注解就无法获取到AbstractEnvironment，但master分支下是正常的。。。。
@RunWith(SpringRunner.class)
public class PropertiesBinderTest {

    @Autowired
    private AbstractEnvironment environment;

    @Test
    public void testBindDtpPropertiesWithMap() {
        Map<Object, Object> properties = Maps.newHashMap();
        properties.put("spring.dynamic.tp.enabled", false);
        properties.put("spring.dynamic.tp.executors[0].threadPoolName", "test_dtp");
        DtpProperties dtpProperties = DtpProperties.getInstance();
        BinderHelper.bindDtpProperties(properties, dtpProperties);
        Assertions.assertEquals(properties.get("spring.dynamic.tp.executors[0].threadPoolName"), 
            dtpProperties.getExecutors().get(0).getThreadPoolName());
    }

    @Test
    public void testBindDtpPropertiesWithEnvironment() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1000);
        DtpProperties dtpProperties = DtpProperties.getInstance();
        BinderHelper.bindDtpProperties(environment, dtpProperties);
        String threadPoolName = environment.getProperty("spring.dynamic.tp.executors[0].threadPoolName");
        Assertions.assertEquals(threadPoolName, dtpProperties.getExecutors().get(0).getThreadPoolName());
    }
    
}
