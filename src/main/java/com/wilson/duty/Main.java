package com.wilson.duty;

import com.google.common.collect.Lists;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;

/**
 * 排班.
 *
 * @author zhangweilong
 * @create 6/4/18 11:38
 **/
public class Main {

    private static List<String> employees;

    private static List<EmployeeDuty> employeeDuties;

    private static List<Integer> daysOfMonth;

    public static void main(String[] args) {

        employees = Lists.newArrayList();
        for (int i = 0; i < 7; i ++) {

            employees.add("00" + (i + 1));
        }

        daysOfMonth = Lists.newArrayList();

        Locale.setDefault(Locale.CHINA);

        GregorianCalendar d = new GregorianCalendar();
        // d.add(Calendar.MONTH, 1);
        int month = d.get(Calendar.MONTH);

        d.set(Calendar.DAY_OF_MONTH, 1);

        do {
            int day = d.get(Calendar.DAY_OF_MONTH);
            daysOfMonth.add(day);
            d.add(Calendar.DAY_OF_MONTH, 1);
        } while(d.get(Calendar.MONTH) == month);

        employeeDuties = Lists.newArrayList();

        for (String employeeName : employees) {
//            System.out.println(employeeName);
            employeeDuties.add(new EmployeeDuty(employeeName));

        }

        for (Integer day : daysOfMonth) {
            // System.out.println(day);
            setSchedule(day, employeeDuties);
        }

        // 可能有3天的情况
        adjustSchedule(employeeDuties);

        for (EmployeeDuty employeeDuty : employeeDuties) {
            System.out.println(employeeDuty.getEmployeeName() + " 值班时间：" + StringUtils.join(employeeDuty.getDutyDays(),
                ","));
        }

        printEmployeeDuties(employeeDuties);
    }

    private static void adjustSchedule(List<EmployeeDuty> employeeDuties) {

        List<EmployeeDuty> lessThanFourDuties = Lists.newArrayList();
        List<EmployeeDuty> moreThanFourDuties = Lists.newArrayList();

        for (EmployeeDuty employeeDuty : employeeDuties) {
            if (employeeDuty.getDutyDays().size() > 4) {
                moreThanFourDuties.add(employeeDuty);
            }

            if (employeeDuty.getDutyDays().size() < 4) {
                lessThanFourDuties.add(employeeDuty);
            }

            if (employeeDuty.getDutyDays().size() == 4 && hasSundayDuty(employeeDuty)) {
                lessThanFourDuties.add(employeeDuty);
            }
        }

        Iterator<EmployeeDuty> iterator = moreThanFourDuties.iterator();
        while (iterator.hasNext()) {

            EmployeeDuty employeeDuty = iterator.next();
            if (hasSundayDuty(employeeDuty)) {
                iterator.remove();
                continue;
            }
            Integer adjustDayOfMonth = null;
            EmployeeDuty adjustEmployeeDuty = null;

            for (Integer day : employeeDuty.getDutyDays()) {

                adjustEmployeeDuty = getAvalibleEmployeeForTheDayCanRepeatDayOfWeek(day, lessThanFourDuties);
                if (Objects.nonNull(adjustEmployeeDuty)) {

                    adjustDayOfMonth = day;
                    break;
                }
            }


            if (Objects.nonNull(adjustEmployeeDuty) && Objects.nonNull(adjustDayOfMonth)) {
                employeeDuty.getDutyDays().remove(adjustDayOfMonth);
                adjustEmployeeDuty.getDutyDays().add(adjustDayOfMonth);
                lessThanFourDuties.remove(adjustEmployeeDuty);
                iterator.remove();
            }
        }
    }

    private static boolean hasSundayDuty(EmployeeDuty employeeDuty) {

        GregorianCalendar calendar = new GregorianCalendar();
        for (Integer dutyDay : employeeDuty.getDutyDays()) {

            calendar.set(Calendar.DAY_OF_MONTH, dutyDay);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SUNDAY) {
                return true;
            }
        }

        return false;
    }

    private static void printEmployeeDuties(List<EmployeeDuty> employeeDuties) {

        Locale.setDefault(Locale.CHINA);

        GregorianCalendar d = new GregorianCalendar();
        int today = d.get(Calendar.DAY_OF_MONTH);

        int weekday = d.get(Calendar.DAY_OF_WEEK);
        int firstDayOfWeek = d.getFirstDayOfWeek();
        int month = d.get(Calendar.MONTH);

        int indent = 0;
        while(weekday != firstDayOfWeek)
        {
            indent++;
            d.add(Calendar.DAY_OF_MONTH, -1);
            weekday = d.get(Calendar.DAY_OF_WEEK);
        }
        String[] weekdayNames = new DateFormatSymbols().getShortWeekdays();
        do
        {
            System.out.printf("%6s", weekdayNames[weekday]);
            d.add(Calendar.DAY_OF_MONTH, 1);
            weekday = d.get(Calendar.DAY_OF_WEEK);
        }while(weekday != firstDayOfWeek);

        if(d.get(Calendar.MONTH) != month) {
            d.add(Calendar.MONTH, -1);
        }

        System.out.println();
        for(int i = 0; i < indent; i++)
        {
            System.out.print(" ");
        }
        d.set(Calendar.DAY_OF_MONTH, 1);

        int dayOfWeekDay = d.get(Calendar.DAY_OF_WEEK);
        for (int i = 0; i < (dayOfWeekDay - 1); i++) {
            System.out.printf("        ");
        }

        do{
            int day = d.get(Calendar.DAY_OF_MONTH);
            String dutyEmployeeName = getDutyNameOfTheDay(day, employeeDuties);
            System.out.printf("%2d(%s)",day, dutyEmployeeName);

            if (day == today) {
                System.out.print(" ");
            } else {
                System.out.print(" ");
            }

            d.add(Calendar.DAY_OF_MONTH, 1);
            weekday = d.get(Calendar.DAY_OF_WEEK);
            if(weekday == firstDayOfWeek)
            {
                System.out.println();
                System.out.print(" ");
            }
        }while(d.get(Calendar.MONTH) == month);
    }

    private static String getDutyNameOfTheDay(int theDay, List<EmployeeDuty> employeeDuties) {

        for (EmployeeDuty employeeDuty : employeeDuties) {
            if (employeeDuty.getDutyDays().contains(theDay)) {
                return employeeDuty.getEmployeeName();
            }
        }
        return null;
    }

    private static void setSchedule(int dayOfMonth, List<EmployeeDuty> employeeDuties) {

        EmployeeDuty availbleEmployee = getAvalibleEmployeeForTheDay(dayOfMonth, employeeDuties);
        if (Objects.nonNull(availbleEmployee)) {

            availbleEmployee.getDutyDays().add(dayOfMonth);
        } else {

        }
    }

    private static EmployeeDuty getAvalibleEmployeeForTheDay(int dayOfMonth, List<EmployeeDuty> employeeDuties) {

        if (employeeDuties.isEmpty()) {
            return null;
        }

        EmployeeDuty availbleEmployee = null;
        List<EmployeeDuty> cadidates = Lists.newArrayList();
        for (EmployeeDuty employeeDuty : employeeDuties) {

            if (dayOfMonth == 1) {
                cadidates.addAll(employeeDuties);
                break;
            } else if (employeeDuty.getDutyDays().size() == 0 || (employeeDuty.getDutyDays().size() < 5
                && (dayOfMonth - employeeDuty.getDutyDays().get(employeeDuty.getDutyDays().size() - 1) < 10)
                && (dayOfMonth - employeeDuty.getDutyDays().get(employeeDuty.getDutyDays().size() - 1)) > 5
                && dayOfWeekNotRepeat(dayOfMonth, employeeDuty))) {

                cadidates.add(employeeDuty);
            }

        }

        System.out.println("cadidate size: " + cadidates.size() + " for day: " + dayOfMonth);
        if (!cadidates.isEmpty()) {


            for (EmployeeDuty employeeDuty : cadidates) {

                if (Objects.isNull(availbleEmployee)) {

                    availbleEmployee = employeeDuty;
                } else if (availbleEmployee.getDutyDays().size() > employeeDuty.getDutyDays().size()
                    && (employeeDuty.getDutyDays().size() < 5 || (employeeDuty.getDutyDays().size() == 4 &&
                    hasSundayDuty(employeeDuty)))) {

                        availbleEmployee = employeeDuty;
                }
            }
        }

        return availbleEmployee;
    }

    private static EmployeeDuty getAvalibleEmployeeForTheDayCanRepeatDayOfWeek(int dayOfMonth, List<EmployeeDuty>
        employeeDuties) {

        if (employeeDuties.isEmpty()) {
            return null;
        }

        EmployeeDuty availbleEmployee = null;
        List<EmployeeDuty> cadidates = Lists.newArrayList();
        for (EmployeeDuty employeeDuty : employeeDuties) {

            if (dayOfMonth == 1) {
                cadidates.addAll(employeeDuties);
                break;
            } else if (employeeDuty.getDutyDays().size() == 0 || (employeeDuty.getDutyDays().size() < 5 &&
                hasAvailableDay(dayOfMonth, employeeDuty))) {

                cadidates.add(employeeDuty);
            }

        }

        if (!cadidates.isEmpty()) {

            System.out.println("cadidate size: " + cadidates.size() + " for day: " + dayOfMonth);

            for (EmployeeDuty employeeDuty : cadidates) {
                if (Objects.isNull(availbleEmployee)) {

                    availbleEmployee = employeeDuty;
                } else if ( availbleEmployee.getDutyDays().size() >
                    employeeDuty.getDutyDays().size() && employeeDuty.getDutyDays().size() < 4) {


                    availbleEmployee = employeeDuty;
                }
            }
        }

        return availbleEmployee;
    }

    private static boolean hasAvailableDay(int dayOfMonth, EmployeeDuty employeeDuty) {

        for (Integer day : employeeDuty.getDutyDays()) {
            if (Math.abs(dayOfMonth - day) <= 10
                && Math.abs(dayOfMonth - day) >= 5) {
                return true;
            }
        }

        return false;
    }

    private static boolean dayOfWeekNotRepeat(int dayOfMonth, EmployeeDuty employeeDuty) {

        Locale.setDefault(Locale.CHINA);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        for (Integer dutyDay : employeeDuty.getDutyDays()) {

            calendar.set(Calendar.DAY_OF_MONTH, dutyDay);
            int dutyDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == dutyDayOfWeek) {
                return false;
            }
        }

        return true;
    }

}
