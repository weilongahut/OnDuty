package com.wilson.duty;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * .
 *
 * @author zhangweilong
 * @create 6/4/18 13:20
 **/
public class EmployeeDuty {

    private String employeeName;

    private List<Integer> dutyDays;

    public EmployeeDuty() {
    }

    public EmployeeDuty(String employeeName) {

        this.employeeName = employeeName;
        this.dutyDays = Lists.newArrayList();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public List<Integer> getDutyDays() {
        return dutyDays;
    }

    public void setDutyDays(List<Integer> dutyDays) {
        this.dutyDays = dutyDays;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmployeeDuty{");
        sb.append("employeeName='").append(employeeName).append('\'');
        sb.append(", dutyDays=").append(dutyDays);
        sb.append('}');
        return sb.toString();
    }
}
