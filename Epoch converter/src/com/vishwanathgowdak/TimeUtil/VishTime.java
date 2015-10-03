package com.vishwanathgowdak.TimeUtil;

import java.text.SimpleDateFormat;
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
	public String covertTimeZone(Date date, TimeZone from, TimeZone to,SimpleDateFormat sdf) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(from);
		cal.setTimeInMillis(date.getTime());
		//cal.setTimeInMillis(1422431200000L);
		//System.out.println(cal.getTimeInMillis()+" "+date.getTime());
		//System.out.println(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND)+" "+cal.getTimeInMillis());
		cal.setTimeZone(to);
		//System.out.println(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND)+" "+cal.getTimeInMillis());
		sdf.setTimeZone(to);
		return sdf.format(cal.getTime());
	}
	
	@Override
	public Calendar ConvertCalTimeZone(Date date, TimeZone from, TimeZone to) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(from);
		cal.setTimeInMillis(date.getTime());
		cal.setTimeZone(to);
		return cal;

	}
}
