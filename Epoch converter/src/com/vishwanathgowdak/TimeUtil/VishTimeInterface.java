package com.vishwanathgowdak.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public interface VishTimeInterface {

	public String covertTimeZone(Date date, TimeZone from, TimeZone to, SimpleDateFormat sdf);
	public Calendar CovertCalTimeZone(Date date, TimeZone from, TimeZone to);
	
}
