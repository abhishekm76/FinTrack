package com.kjfmbktgl4.fintrack.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.OffsetDateTime;
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

	public static String longStringToDateString(String dateInMillis){
		Long dateInLong= Long.parseLong(dateInMillis);
		String stringDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(dateInLong);
		return stringDate;
	}

	public static String dateStringToLongString(String dateString){
		try {
			Date setDate = DateFormat.getDateInstance().parse(dateString.toString());
			Long dateinmillis = setDate.getTime();
			String longString = dateinmillis.toString();
			return longString;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getcurrentDateInMilLs(){
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.SECOND, 59);
		cal1.set(Calendar.MINUTE, 59);
		cal1.set(Calendar.HOUR_OF_DAY, 23);
		cal1.set(Calendar.MILLISECOND,999);
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
	public static String getFirstOfMonth(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MILLISECOND,0);
		Date firstDate = cal.getTime();
		String firstDateString= String.valueOf(DateConverters.dateToLong(firstDate));
		return firstDateString;
	}
	public static String getEpochStart(){
		Calendar cal = Calendar.getInstance();
		cal.set(2000,0,1);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MILLISECOND,0);
		Date firstDate = cal.getTime();
		String firstDateString= String.valueOf(DateConverters.dateToLong(firstDate));
		return firstDateString;
	}public static String getFirstOfWeek(){
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DAY_OF_WEEK,1);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MILLISECOND,0);
		Date firstDate = cal.getTime();
		String firstDateString= String.valueOf(DateConverters.dateToLong(firstDate));
		return firstDateString;
	}
}
