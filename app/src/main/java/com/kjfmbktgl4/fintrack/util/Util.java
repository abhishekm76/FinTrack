package com.kjfmbktgl4.fintrack.util;

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
}
