package org.dromara.dynamictp.common.em;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import org.dromara.dynamictp.common.ex.DtpException;
import org.dromara.dynamictp.common.queue.MemorySafeLinkedBlockingQueue;
import org.dromara.dynamictp.common.queue.VariableLinkedBlockingQueue;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static org.dromara.dynamictp.common.constant.DynamicTpConst.M_1;

@Slf4j
@Getter
public enum QueueTypeEnum {
    
    ARRAY_BLOCKING_QUEUE(1, "ArrayBlockingQueue"),

    LINKED_BLOCKING_QUEUE(2, "LinkedBlockingQueue"),

    PRIORITY_BLOCKING_QUEUE(3, "PriorityBlockingQueue"),

    DELAY_QUEUE(4, "DelayQueue"),

    SYNCHRONOUS_QUEUE(5, "SynchronousQueue"),

    LINKED_TRANSFER_QUEUE(6, "LinkedTransferQueue"),

    LINKED_BLOCKING_DEQUE(7, "LinkedBlockingDeque"),

    VARIABLE_LINKED_BLOCKING_QUEUE(8, "VariableLinkedBlockingQueue"),

    MEMORY_SAFE_LINKED_BLOCKING_QUEUE(9, "MemorySafeLinkedBlockingQueue");

    private final Integer code;
    private final String name;

    QueueTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static BlockingQueue<Runnable> bulidLbq(String name, int capacity) {
        return buildLbq(name, capacity, false, 256);
    }

    @SuppressWarnings("all")
    public static BlockingQueue<Runnable> buildLbq(
        String name, int capacity, boolean fair, int maxFreeMemory
    ) {
        BlockingQueue<Runnable> blockingQueue = null;
        if (Objects.equals(name, ARRAY_BLOCKING_QUEUE.getName())) {
            blockingQueue = new ArrayBlockingQueue<>(capacity);
        } else if (Objects.equals(name, LINKED_BLOCKING_QUEUE.getName())) {
            blockingQueue = new LinkedBlockingQueue<>(capacity);
        } else if (Objects.equals(name, PRIORITY_BLOCKING_QUEUE.getName())) {
            blockingQueue = new PriorityBlockingQueue<>(capacity);
        } else if (Objects.equals(name, DELAY_QUEUE.getName())) {
            blockingQueue = new DelayQueue();
        } else if (Objects.equals(name, SYNCHRONOUS_QUEUE.getName())) {
            blockingQueue = new SynchronousQueue<>(fair);
        } else if (Objects.equals(name, LINKED_TRANSFER_QUEUE.getName())) {
            blockingQueue = new LinkedTransferQueue<>();
        } else if (Objects.equals(name, LINKED_BLOCKING_DEQUE.getName())) {
            blockingQueue = new LinkedBlockingDeque<>(capacity);
        } else if (Objects.equals(name, VARIABLE_LINKED_BLOCKING_QUEUE.getName())) {
            blockingQueue = new VariableLinkedBlockingQueue<>(capacity);
        } else if (Objects.equals(name, MEMORY_SAFE_LINKED_BLOCKING_QUEUE.getName())) {
            blockingQueue = new MemorySafeLinkedBlockingQueue<>(capacity, maxFreeMemory * M_1);
        }
        if (blockingQueue != null) {
            return blockingQueue;
        }

        log.error("Cannot find specified BlockingQueue {}", name);
        throw new DtpException("Cannot find specified BlockingQueue " + name);
    }
}
