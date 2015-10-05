package com.vishwanathgowdak.EpochConverter;

import java.util.Calendar;

import java.util.GregorianCalendar;

public class CurrentSelectionValues {
	private static String year="1970";
	private static String month="1";
	private static String hour="0";
	private static String date="1";
	private static String minute="0";
	private static String second="0";
	public static String getYear() {
		if (Integer.valueOf(year)<10){
			return "0"+year;
		}else{
			return year;
		}
	}
	public static void setYear(int year) {
		CurrentSelectionValues.year = new Integer(year).toString();
	}
	public static String getMonth() {
		if (Integer.valueOf(month)<10){
			return "0"+month;
		}else{
			return month;
		}
	}
	public static void setMonth(int month) {
		CurrentSelectionValues.month = new Integer(month).toString();
	}
	public static String getDate() {
		if (Integer.valueOf(date)<10){
			return "0"+date;
		}else{
			return date;
		}
	}
	public static  void setDate(int date) {
		CurrentSelectionValues.date = new Integer(date).toString();
	}
	public static String getHour() {
		if(Integer.valueOf(hour)<10)
			return "0"+hour;
		else
			return hour;
	}
	public static void setHour(int hour) {
		CurrentSelectionValues.hour = new Integer(hour).toString();
	}
	public static String  getMinute() {
		if (Integer.valueOf(minute)<10){
			return "0"+minute;
		}else{
			return minute;
		}
		
	}
	public static void setMinute(int minute) {
		CurrentSelectionValues.minute = new Integer(minute).toString();
	}
	public static String getSecond() {
		if (Integer.valueOf(second)<10){
			return "0"+second;
		}else{
			return second;
		}
	}
	public static void setSecond(int second) {
		CurrentSelectionValues.second = new Integer(second).toString();
	}

	public static void assignNowValue(){
		Calendar cal=new GregorianCalendar();
		setYear(cal.get(Calendar.YEAR));
		setMonth(cal.get(Calendar.MONTH));
		setDate(cal.get(Calendar.DATE));
		setHour(cal.get(Calendar.HOUR));
		setMinute(cal.get(Calendar.MINUTE));
		setSecond(cal.get(Calendar.SECOND));
	}
}
