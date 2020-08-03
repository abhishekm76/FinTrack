package com.kjfmbktgl4.fintrack.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateConverters {

	public static String dateToString(Date date){
		String epochdate;
			epochdate = DateFormat.getDateInstance().format(date.getTime());
		return epochdate;
	}

	public static Long dateToLong(Date date){
		Long dateinmillis = date.getTime();
		return dateinmillis;
	}
	public static String getcurrentDateInMilLs(){
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DAY_OF_YEAR, 1);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MILLISECOND,0);
		Date endDate= cal1.getTime();
		String curDateString = String.valueOf(DateConverters.dateToLong(endDate));
		return curDateString;
	}
	public static String getFirstOfCurrentYearInMills(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MILLISECOND,0);
		Date firstDate = cal.getTime();
		String firstDateString= String.valueOf(DateConverters.dateToLong(firstDate));
		return firstDateString;
	}
}
