package org.dromara.dynamictp.core.support.selector;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * ExecutorSelector related
 *
 **/
public interface ExecutorSelector {

    /**
     * select executor
     *
     * @param executors executors
     * @param arg arg
     * @return executor
     */
    Executor select(List<Executor> executors, Object arg);
}
