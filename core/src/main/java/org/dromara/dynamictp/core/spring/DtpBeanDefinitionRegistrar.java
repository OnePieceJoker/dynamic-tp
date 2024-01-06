package org.dromara.dynamictp.core.spring;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.collections4.CollectionUtils;
import org.dromara.dynamictp.common.entity.DtpExecutorProps;
import org.dromara.dynamictp.common.properties.DtpProperties;
import org.dromara.dynamictp.common.spring.SpringBeanHelper;
import org.dromara.dynamictp.core.executor.ExecutorType;
import org.dromara.dynamictp.core.executor.NamedThreadFactory;
import org.dromara.dynamictp.core.reject.RejectHandlerGetter;
import org.dromara.dynamictp.core.support.BinderHelper;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import com.google.common.collect.Maps;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

import static org.dromara.dynamictp.common.constant.DynamicTpConst.ALLOW_CORE_THREAD_TIMEOUT;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.AWAIT_TERMINATION_SECONDS;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.AWARE_NAMES;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.NOTIFY_ENABLED;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.NOTIFY_ITEMS;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.PLATFORM_IDS;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.PLUGIN_NAMES;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.PRE_START_ALL_CORE_THREADS;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.QUEUE_TIMEOUT;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.REJECT_ENHANCED;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.REJECT_HANDLER_TYPE;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.RUN_TIMEOUT;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.TASK_WRAPPERS;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.THREAD_POOL_ALIAS_NAME;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.THREAD_POOL_NAME;
import static org.dromara.dynamictp.common.constant.DynamicTpConst.WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN;
import static org.dromara.dynamictp.common.em.QueueTypeEnum.buildLbq;

@Slf4j
public class DtpBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        DtpProperties dtpProperties = DtpProperties.getInstance();
        BinderHelper.bindDtpProperties(environment, dtpProperties);
        val executors = dtpProperties.getExecutors();
        if (CollectionUtils.isEmpty(executors)) {
            log.warn("DynamicTp registrar, no executors are configured.");
            return;
        }

        executors.forEach(e -> {
            Class<?> executorTypeClass = ExecutorType.getClass(e.getExecutorType());
            Map<String, Object> propertyValues = buildPropertyValues(e);
            Object[] args = buildConstructorArgs(executorTypeClass, e);
            SpringBeanHelper.register(registry, e.getThreadPoolName(), executorTypeClass, propertyValues, args);
        });
    }

    private Map<String, Object> buildPropertyValues(DtpExecutorProps props) {
        Map<String, Object> propertyValues = Maps.newHashMap();
        propertyValues.put(THREAD_POOL_NAME, props.getThreadPoolName());
        propertyValues.put(THREAD_POOL_ALIAS_NAME, props.getThreadPoolAliasName());
        propertyValues.put(ALLOW_CORE_THREAD_TIMEOUT, props.isAllowCoreThreadTimeOut());
        propertyValues.put(WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN, props.isWaitForTasksToCompleteOnShutdown());
        propertyValues.put(AWAIT_TERMINATION_SECONDS, props.getAwaitTerminationSeconds());
        propertyValues.put(PRE_START_ALL_CORE_THREADS, props.isPreStartAllCoreThreads());
        propertyValues.put(REJECT_HANDLER_TYPE, props.getRejectedHandlerType());
        propertyValues.put(REJECT_ENHANCED, props.isRejectEnhanced());
        propertyValues.put(RUN_TIMEOUT, props.getRunTimeout());
        propertyValues.put(QUEUE_TIMEOUT, props.getQueueTimeout());

        return propertyValues;
    }

    private Object[] buildConstructorArgs(Class<?> clazz, DtpExecutorProps props) {
        BlockingQueue<Runnable> taskQueue;
        taskQueue = buildLbq(props.getQueueType(), 
                        props.getQueueCapacity(), 
                        props.isFair(), 
                        props.getMaxFreeMemory()
        );

        return new Object[] {
            props.getCorePoolSize(),
            props.getMaximumPoolSize(),
            props.getKeepAliveTime(),
            props.getUnit(),
            taskQueue,
            new NamedThreadFactory(props.getThreadNamePrefix()),
            RejectHandlerGetter.buildRejectedHandler(props.getRejectedHandlerType())
        };
    }
    
}
