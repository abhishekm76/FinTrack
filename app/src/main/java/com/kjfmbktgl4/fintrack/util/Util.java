package com.kjfmbktgl4.fintrack.util;

import java.sql.Timestamp;

public class Util {

	//Database related items
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "financeTracker_db";
	public static final String TABLE_NAME = "financeTransaction";

	//finance table columns names
	public static final String TRAN_ID = "idTran";
	public static final String TRAN_DATE = "dateTran";
	public static final String TRAN_AMOUNT = "amountTran";
	public static final String TRAN_ICON_CAT = "iconCategoryTran";
	public static final String TRAN_CAT_NAME="nameCategoryTran";
	public static final String TRAN_ACT = "accountTran";
	public static final String TRAN_NOTE = "noteTran";

/*
	private int id;
	private long dateOfTransaction;
	private long amountOfTransaction;
	private int categoryOfTransaction;
	private int accountOfTransaction;
	private String noteOfTransaction;*/


	// Upgrade and drop
	public static final String DB_DROP="DROP TABLE IF EXISTS";


	//Tags
	public static final String TAG ="dbTest";

	//Colourlist
	public static final int [] colorArray= {0xFFF44336, 0xFF03A9F4,  0xFFFFC107, 0xFF673ab7, 0xFFCDDC39, 0xFFFF9800, 0xFF9E9E9E};



	//SharePref
	public static final String SPREFNAME ="FINTRACKPREFS";
	//public static final String CATEGORYTOTAL="totalAmountOfTransaction";
	public static final String PERIODTOTAL= "totalByPeriod";
	public static final String PERIODNAME ="nameOfPeriod";

	public Long generateRandomDate(){
		long beginTime = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
		long endTime = Timestamp.valueOf("2020-06-30 00:58:00").getTime();
		long diff = endTime - beginTime + 1;
		return beginTime + (long) (Math.random() * diff);

	}

	public static int getColorCode(int weekday){
		return colorArray[weekday];
	}

}
