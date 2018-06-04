package com.wilson.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 周期线程池测试.
 *
 * @author zhangweilong
 * @create 2/27/18 11:10
 **/
public class ScheduledThreadPoolExecutorTest {

    public static void main(String[] args) {

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(Integer.MAX_VALUE);
//        ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(1);
        final List<Object> ranList = new ArrayList<>();
        long start = System.currentTimeMillis();
        scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                final CountDownLatch countDownLatch = new CountDownLatch(1);

                try {
                    ranList.add(new Object());
                    System.out.println("[" + Thread.currentThread().getId() + "]: ran\n");
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("[" + (System.currentTimeMillis() - start) / 10 +"] thread in tatol, [" + ranList
                //    .size() + "]" + " thread have ran");

                System.out.println("scheduled thread queue size: " + scheduledThreadPoolExecutor.getQueue().size());
//                System.out.println("scheduled thread pool size: " + scheduledThreadPoolExecutor.getPoolSize());
//                System.out.println("scheduled thread task count: " + scheduledThreadPoolExecutor.getTaskCount());
//                System.out.println("scheduled thread completed task count: " + scheduledThreadPoolExecutor
//                    .getCompletedTaskCount());
//                System.out.println("" + scheduledThreadPoolExecutor.getMaximumPoolSize());
//                System.out.println("" + scheduledThreadPoolExecutor.getLargestPoolSize());
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(scheduledThreadPoolExecutor.getQueue().size());
    }
}
