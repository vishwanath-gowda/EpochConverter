package com.vishwanathgowdak.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public interface VishTimeInterface {

	
	public Calendar convertCalTimeZone(Date date, TimeZone from, TimeZone to);
	
}
