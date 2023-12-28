package org.dromara.dynamictp.common.queue;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@link Runtime#freeMemory()} technology is used to calculate the memory
 * limit by using the percentage of the current maximum available memory.
 * Note: this class simplifies the original implementation.
 *
 * @see <a href="https://github.com/apache/incubator-shenyu/blob/master/shenyu-common/src/main/java/org/apache/shenyu/common/concurrent/MemoryLimitCalculator.java">MemoryLimitCalculator</a>
 */
public class MemoryLimitCalculator {
    
    private static volatile long maxAvailable;
    
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();
    
    static {
        // immediately refresh when this class is loaded to prevent maxAvailable from being 0
        refresh();
        // check every 50 ms to improve performance
        SCHEDULER.scheduleWithFixedDelay(MemoryLimitCalculator::refresh, 50, 50, TimeUnit.MILLISECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(SCHEDULER::shutdown));
    }
    
    private static void refresh() {
        maxAvailable = Runtime.getRuntime().freeMemory();
    }
    
    /**
     * Get the maximum available memory of the current JVM.
     *
     * @return maximum available memory
     */
    public static long maxAvailable() {
        return maxAvailable;
    }
}
