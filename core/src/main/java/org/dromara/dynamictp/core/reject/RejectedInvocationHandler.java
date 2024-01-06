package org.dromara.dynamictp.core.reject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;

public class RejectedInvocationHandler implements InvocationHandler {

    private final Object target;

    public RejectedInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            beforeReject((Runnable) args[0], (Executor) args[1]);
            Object result = method.invoke(target, args);
            afterReject((Runnable) args[0], (Executor) args[1]);
            return result;
        } catch (InvocationTargetException ex) {
            throw ex.getCause();
        }
    }

    private void beforeReject(Runnable runnable, Executor executor) {
        // TODO 
    }

    private void afterReject(Runnable runnable, Executor executor) {
        // TODO
    }
    
}
