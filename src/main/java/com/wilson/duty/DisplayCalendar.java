package com.wilson.duty;

import java.text.DateFormatSymbols;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Locale;

public class DisplayCalendar {
	public static void main(String[] args){
		Locale.setDefault(Locale.CHINA);
		
		GregorianCalendar d = new GregorianCalendar();
		int today = d.get(Calendar.DAY_OF_MONTH);
		
		int weekday = d.get(Calendar.DAY_OF_WEEK);
		int firstDayOfWeek = d.getFirstDayOfWeek();
		int month = d.get(Calendar.MONTH);
		
		int indent = 0;
		while(weekday != firstDayOfWeek) {
			indent++;
			d.add(Calendar.DAY_OF_MONTH, -1);
			weekday = d.get(Calendar.DAY_OF_WEEK);
		}

		String[] weekdayNames = new DateFormatSymbols().getShortWeekdays();
		do {
			System.out.printf("%4s", weekdayNames[weekday]);
			d.add(Calendar.DAY_OF_MONTH, 1);
			weekday = d.get(Calendar.DAY_OF_WEEK);
		} while (weekday != firstDayOfWeek);
		
		if(d.get(Calendar.MONTH) != month) {
			d.add(Calendar.MONTH, -1);
		}
		
		System.out.println();
		for(int i = 0; i < indent; i++) {
			System.out.print(" ");
		}

		d.set(Calendar.DAY_OF_MONTH, 1);

		int dayOfWeekDay = d.get(Calendar.DAY_OF_WEEK);
		for (int i = 0; i < (dayOfWeekDay - 1); i++) {
			System.out.printf("      ");
		}
		
		do{
			int day = d.get(Calendar.DAY_OF_MONTH);
			System.out.printf("%3d",day);
			
			if (day == today) {
				System.out.print("*  ");
			} else {
				System.out.print("   ");
			}
			
			d.add(Calendar.DAY_OF_MONTH, 1);
			weekday = d.get(Calendar.DAY_OF_WEEK);
			if (weekday == firstDayOfWeek) {
				System.out.println();
				System.out.print(" ");
			} else {

			}
		}while(d.get(Calendar.MONTH) == month);		
	}
}
