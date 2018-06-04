package com.wilson.thread;

import java.util.concurrent.CountDownLatch;

/**
 * .
 *
 * @author zhangweilong
 * @create 2/27/18 15:43
 **/
public class TestNativeOutOfMemoryError {

    public static void main(String[] args) {
        for (int i = 0; ; i++) {
            System.out.println("current threads count: " + i);
            new Thread(new HoldThread()).start();
        }
    }

    static class HoldThread extends Thread {

        private byte [] occupy;

        CountDownLatch latch = new CountDownLatch(1);

        public HoldThread() {
            this.setDaemon(true);
            occupy = new byte[2048 * 1024];
            for (int i = 0; i < occupy.length; i ++) {
                occupy[i] = new Byte("1");
            }
        }

        @Override
        public void run() {
            try {
                latch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
