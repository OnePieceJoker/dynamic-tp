package org.dromara.dynamictp.common.constant;

public final class DynamicTpConst {

    private DynamicTpConst() {}

    public static final String MAIN_PROPERTIES_PREFIX = "spring.dynamic.tp";

    public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static final Integer M_1 = 1024 * 1024;
    
    public static final String DTP_ENABLED_PROP = MAIN_PROPERTIES_PREFIX + ".enabled";

    public static final String OS_NAME_KEY = "os.name";

    public static final String OS_LINUX_PREFIX = "linux";

    public static final String OS_WIN_PREFIX = "win";

    public static final String TRACE_ID = "traceId";

        /**
     * Dtp executor properties const.
     */
    public static final String THREAD_POOL_NAME = "threadPoolName";

    public static final String THREAD_POOL_ALIAS_NAME = "threadPoolAliasName";

    public static final String ALLOW_CORE_THREAD_TIMEOUT = "allowCoreThreadTimeOut";

    public static final String NOTIFY_ITEMS = "notifyItems";

    public static final String PLATFORM_IDS = "platformIds";

    public static final String NOTIFY_ENABLED = "notifyEnabled";

    public static final String WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN = "waitForTasksToCompleteOnShutdown";

    public static final String AWAIT_TERMINATION_SECONDS = "awaitTerminationSeconds";

    public static final String PRE_START_ALL_CORE_THREADS = "preStartAllCoreThreads";

    public static final String REJECT_ENHANCED = "rejectEnhanced";

    public static final String REJECT_HANDLER_TYPE = "rejectHandlerType";

    public static final String RUN_TIMEOUT = "runTimeout";

    public static final String QUEUE_TIMEOUT = "queueTimeout";

    public static final String TASK_WRAPPERS = "taskWrappers";

    public static final String PLUGIN_NAMES = "pluginNames";

    public static final String AWARE_NAMES = "awareNames";

    
}
