package com.wilson.threadlocal;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * .
 *
 * @author zhangweilong
 * @create 3/30/18 21:41
 **/
public class InheritableThreadLocalTest {

    private volatile static ThreadLocal<Map<String, Object>> threadContext = new InheritableThreadLocal<>();

    public InheritableThreadLocalTest() {
        threadContext.set(new HashMap<>());
    }

    public static final Object getValue(String key) {
        synchronized (threadContext) {
            if (Objects.isNull(threadContext) || Objects.isNull(threadContext.get())) {

                threadContext = new InheritableThreadLocal<Map<String, Object>>();
                threadContext.set(new HashMap<>());
            }
        }
        return threadContext.get().get(key);
    }

    public static final void setValue(String key, Object value) {
        synchronized (threadContext) {
            if (Objects.isNull(threadContext) || Objects.isNull(threadContext.get())) {
                threadContext = new InheritableThreadLocal<>();
                threadContext.set(new HashMap<>());
            }
        }
        threadContext.get().put(key, value);
    }
}
