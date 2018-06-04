package com.wilson.threadlocal;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * threadLocal变量测试.
 *
 * @author zhangweilong
 * @create 3/30/18 21:33
 **/
public class ThreadLocalTest {

    private static ThreadLocal<Map<String, Object>> threadContext = new ThreadLocal<Map<String, Object>>();

    public ThreadLocalTest() {
        threadContext.set(new HashMap<>());
    }

    public static final Object getValue(String key) {
        synchronized (threadContext) {
            if (Objects.isNull(threadContext) || Objects.isNull(threadContext.get())) {

                threadContext = new ThreadLocal<Map<String, Object>>();
                threadContext.set(new HashMap<>());
            }
        }
        return threadContext.get().get(key);
    }

    public static final void setValue(String key, Object value) {
        synchronized (threadContext) {
            if (Objects.isNull(threadContext) || Objects.isNull(threadContext.get())) {
                threadContext = new ThreadLocal<>();
                threadContext.set(new HashMap<>());
            }
        }
        threadContext.get().put(key, value);
    }
}
