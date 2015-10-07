package com.vishwanathgowdak.TimeUtil;


import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class VishTime implements VishTimeInterface{

	private static VishTime vishtime= new VishTime();

	public static VishTime getInstance(){
		return vishtime==null?new VishTime():vishtime;
	}
	private VishTime(){

	}
	
	@Override
	public Calendar convertCalTimeZone(Date date, TimeZone from, TimeZone to) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(from);
		cal.setTimeInMillis(date.getTime());
		cal.setTimeZone(to);
		return cal;

	}
}
