package org.dromara.dynamictp.core.spring;

import java.util.concurrent.TimeUnit;

import org.dromara.dynamictp.common.spring.ApplicationContextHolder;
import org.dromara.dynamictp.common.spring.SpringBeanHelper;
import org.dromara.dynamictp.common.timer.HashedWheelTimer;
import org.dromara.dynamictp.core.executor.NamedThreadFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.google.common.collect.Lists;


public class DtpBaseBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    
    private static final String APPLICATION_CONTEXT_HOLDER = "dtpApplicationContextHolder";

    private static final String HASHED_WHEEL_TIMER = "dtpHashedWheelTimer";

    private static final String DTP_POST_PROCESSOR = "dtpPostProcessor";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerHashedWheelTimer(registry);
        SpringBeanHelper.registerIfAbsent(registry, APPLICATION_CONTEXT_HOLDER, ApplicationContextHolder.class);
        
        // ApplicationContextHolder and HashedWheelTimer are required in DtpExecutor execute method, so they must be registered first
        SpringBeanHelper.registerIfAbsent(registry, DTP_POST_PROCESSOR, DtpPostProcessor.class,
                null, Lists.newArrayList(APPLICATION_CONTEXT_HOLDER, HASHED_WHEEL_TIMER));
    }

    private void registerHashedWheelTimer(BeanDefinitionRegistry registry) {
        Object[] constructorArgs = new Object[] {
            new NamedThreadFactory("dtp-runnable-timeout", true),
            10,
            TimeUnit.MICROSECONDS
        };
        SpringBeanHelper.registerIfAbsent(registry, HASHED_WHEEL_TIMER, HashedWheelTimer.class, constructorArgs);
    }
}
