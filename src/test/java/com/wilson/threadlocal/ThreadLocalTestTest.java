package com.wilson.threadlocal;

/**
 * .
 *
 * @author zhangweilong
 * @create 3/30/18 21:37
 **/
public class ThreadLocalTestTest {

    public static void main(String[] args) {
        ThreadLocalTest.setValue("1", "tested");
        System.out.println(ThreadLocalTest.getValue("1"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(ThreadLocalTest.getValue("1"));
            }
        }).start();
    }
}
