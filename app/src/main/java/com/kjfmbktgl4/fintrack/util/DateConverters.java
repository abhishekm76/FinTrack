package com.kjfmbktgl4.fintrack.util;

import java.text.DateFormat;
import java.text.ParseException;
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
}
