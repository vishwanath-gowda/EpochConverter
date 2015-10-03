package com.vishwanathgowdak.EpochConverter;

import java.util.HashMap;
import java.util.Map;

public class MonthMap {
	
	static final MonthMap mp= new MonthMap();
	private final Map<Integer, String> m = new HashMap<Integer, String>();
	private MonthMap(){	}
	
	{
		m.put(1, "Jan");
		m.put(2, "Feb");
		m.put(3, "Mar");
		m.put(4, "Apr");
		m.put(5, "May");
		m.put(6, "Jun");
		m.put(7, "Jul");
		m.put(8, "Aug");
		m.put(9, "Sep");
		m.put(10, "Oct");
		m.put(11, "Nov");
		m.put(12, "Dec");
		
	}
	public static MonthMap getInstance(){
		return mp;
	}
	
	public String getShortMonth(int i){
		 return (i>12 || i < 1)?null:m.get(i);
	}
	
	 

}
