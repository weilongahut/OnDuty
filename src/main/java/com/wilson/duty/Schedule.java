package com.wilson.duty;

import java.util.Date;

/**
 * .
 *
 * @author zhangweilong
 * @create 6/4/18 11:40
 **/
public class Schedule {

    private Date date;

    private String employeeName;

    public Schedule() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Schedule{");
        sb.append("date=").append(date);
        sb.append(", employeeName='").append(employeeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
