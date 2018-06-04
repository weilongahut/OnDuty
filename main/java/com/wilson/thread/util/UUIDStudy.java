package com.wilson.thread.util;

import java.util.UUID;

/**
 * uuid.
 *
 * @author zhangweilong
 * @create 12/1/17 11:46
 **/
public class UUIDStudy {

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }
}
