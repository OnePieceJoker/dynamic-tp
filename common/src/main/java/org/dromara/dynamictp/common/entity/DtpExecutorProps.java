package org.dromara.dynamictp.common.entity;

import org.dromara.dynamictp.common.em.QueueTypeEnum;
import org.dromara.dynamictp.common.em.RejectedTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DtpExecutorProps extends TpExecutorProps {

    /**
     * Executor type, used in create phase, see {@link org.dromara.dynamictp.core.executor.ExecutorType}
     */
    private String executorType;

    /**
     * Blocking queue type, see {@link QueueTypeEnum}
     */
    private String queueType = QueueTypeEnum.VARIABLE_LINKED_BLOCKING_QUEUE.getName();

    /**
     * BlockingQueue capacity.
     */
    private int queueCapacity = 1024;

    /**
     * If fair strategy, for SynchronousQueue
     */
    private boolean fair = false;

    /**
     * Max free memory for MemorySafeLBQ, unit M
     */
    private int maxFreeMemory = 16;

    /**
     * RejectedExecutionHandler type, see {@link RejectedTypeEnum}
     */
    private String rejectedHandlerType = RejectedTypeEnum.ABORT_POLICY.getName();

    /**
     * If allow core thread timeout.
     */
    private boolean allowCoreThreadTimeOut = false;

    /**
     * Thread name prefix.
     */
    private String threadNamePrefix = "dtp";

    /**
     * Whether to wait for scheduled tasks to complete on shutdown,
     * not interrupting running tasks and executing all tasks in the queue.
     */
    private boolean waitForTasksToCompleteOnShutdown = true;

    /**
     * The maximum number of seconds that this executor is supposed to block
     * on shutdown in order to wait for remaining tasks to complete their execution
     * before the rest of the container continues to shut down.
     */
    private int awaitTerminationSeconds = 3;

    /**
     * If pre start all core threads.
     */
    private boolean preStartAllCoreThreads = false;

    /**
     * If enhance reject.
     */
    private boolean rejectEnhanced = true;

    /**
     * check core param is inValid
     *
     * @return boolean return true means params is inValid
     */
    public boolean coreParamIsInValid() {
        return this.getCorePoolSize() < 0
                || this.getMaximumPoolSize() <= 0
                || this.getMaximumPoolSize() < this.getCorePoolSize()
                || this.getKeepAliveTime() < 0;
    } 
}
