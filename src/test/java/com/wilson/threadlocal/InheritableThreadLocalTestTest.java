package com.wilson.threadlocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * .
 *
 * @author zhangweilong
 * @create 3/30/18 21:37
 **/
public class InheritableThreadLocalTestTest {

    public static void main(String[] args) {
        InheritableThreadLocalTest.setValue("1", "tested");
        System.out.println(InheritableThreadLocalTest.getValue("1"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(InheritableThreadLocalTest.getValue("1"));
            }
        }).start();

        List<String> test = new ArrayList<>();
        test.add("1");
        test.add("1");
        test.add("1");

        CountDownLatch countDownLatch = new CountDownLatch(3);
        test.parallelStream().forEach(string -> new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(InheritableThreadLocalTest.getValue("1"));
                countDownLatch.countDown();
            }
        }).start());

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
