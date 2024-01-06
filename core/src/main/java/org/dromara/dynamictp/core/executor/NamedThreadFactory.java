package org.dromara.dynamictp.core.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


public class NamedThreadFactory implements ThreadFactory {

    private final ThreadGroup group;

    private final String namePrefix;

    private final boolean daemon;

    private final Integer priority;

    private final AtomicInteger seq = new AtomicInteger(1);

    public NamedThreadFactory(String namePrefix, boolean daemon, int priority) {
        this.daemon = daemon;
        this.priority = priority;
        // SecurityManager被弃用
        // SecurityManager s = System.getSecurityManager();
        // group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        group = Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix;
    }

    public NamedThreadFactory(String namePrefix) {
        this(namePrefix, false, Thread.NORM_PRIORITY);
    }

    public NamedThreadFactory(String namePrefix, boolean daemon) {
        this(namePrefix, daemon, Thread.NORM_PRIORITY);
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = namePrefix + "-" + seq.getAndIncrement();
        Thread t = new Thread(group, r, name);
        t.setDaemon(daemon);
        t.setPriority(priority);
        return t;
    }

    public String getNamePrefix() {
        return namePrefix;
    }
    
}
