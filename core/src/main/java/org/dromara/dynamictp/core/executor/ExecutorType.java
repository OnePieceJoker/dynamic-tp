package org.dromara.dynamictp.core.executor;

import lombok.Getter;

@Getter
public enum ExecutorType {
        
    /**
     * Executor type.
     */
    // TODO 添加其他的DtpExecutor子类
    // COMMON("common", DtpExecutor.class),
    // EAGER("eager", EagerDtpExecutor.class),
    // SCHEDULED("scheduled", ScheduledDtpExecutor.class),
    // ORDERED("ordered", OrderedDtpExecutor.class);
    COMMON("common", DtpExecutor.class);

    private final String name;

    private final Class<?> clazz;

    ExecutorType(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public static Class<?> getClass(String name) {
        for (ExecutorType type : ExecutorType.values()) {
            if (type.name.equals(name)) {
                return type.getClazz();
            }
        }
        return COMMON.getClazz();
    }
    
}
