package com.vishwanathgowdak.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public interface VishTimeInterface {

	public String covertTimeZone(Date date, TimeZone from, TimeZone to, SimpleDateFormat sdf);
	public Calendar ConvertCalTimeZone(Date date, TimeZone from, TimeZone to);
	
}
