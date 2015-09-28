package com.vishwanathgowdak.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TestMain {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/DD HH:mm:ss" , Locale.US);
		TimeZone from = TimeZone.getTimeZone("IST");
		TimeZone to = TimeZone.getTimeZone("UTC");
		Date date = sdf.parse("2015/09/28 01:30:00");
		
		//VishTime.getInstance().covertTimeZone(date, TimeZone.getTimeZone("IST"), TimeZone.getTimeZone("UTC"));
		Calendar cal = VishTime.getInstance().CovertCalTimeZone(date, from, to);
		System.out.println(cal.get(Calendar.HOUR_OF_DAY));
		System.out.println(sdf.format(cal.getTime()));
		
	}

}
