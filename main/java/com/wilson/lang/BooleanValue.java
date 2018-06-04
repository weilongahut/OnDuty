package com.wilson.lang;

import java.util.Objects;

/**
 * .
 *
 * @author zhangweilong
 * @create 12/7/17 21:14
 **/
public class BooleanValue {

    private Boolean value;

    public Boolean getValue() {
        return value;
    }

    public static void main(String[] args) {
        BooleanValue value = new BooleanValue();
        System.out.println(Objects.isNull(value.getValue()));
    }
}
