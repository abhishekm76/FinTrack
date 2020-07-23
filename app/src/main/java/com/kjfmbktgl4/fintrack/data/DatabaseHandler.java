package com.kjfmbktgl4.fintrack.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kjfmbktgl4.fintrack.model.CategoryTotals;
import com.kjfmbktgl4.fintrack.model.PeriodTotal;
import com.kjfmbktgl4.fintrack.model.TransactionItem;
import com.kjfmbktgl4.fintrack.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
	public DatabaseHandler(Context context ) {
		super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
//We create our table
/*			public static final String TRAN_ID = "idTran";
	public static final String TRAN_DATE = "dateTran";
	public static final String TRAN_AMOUNT = "amountTran";
	public static final String TRAN_CAT = "categoryTran";
	public static final String TRAN_ACT = "accountTran";
	public static final String TRAN_NOTE = "noteTran";*/

		String CREATE_TRANS_TABLE = "CREATE TABLE " + Util.TABLE_NAME
				+ "("
				+ Util.TRAN_ID + " INTEGER PRIMARY KEY,"
				+ Util.TRAN_DATE + " LONG,"
				+ Util.TRAN_AMOUNT+ " LONG,"
				+ Util.TRAN_ICON_CAT+ " INTEGER,"
				+ Util.TRAN_CAT_NAME+ " TEXT,"
				+ Util.TRAN_ACT+ " TEXT,"
				+ Util.TRAN_NOTE+ " TEXT" // no comma after the last column
				+ ")";
		db.execSQL(CREATE_TRANS_TABLE); //creating our table



	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String DROP_TABLE = Util.DB_DROP;
		db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

		//Create a table again
		onCreate(db);
	}

	/*
	   CRUD = Create, Read, Update, Delete

	 */

	//Add Transaction
	public void addTransaction(TransactionItem transaction_p) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Util.TRAN_DATE, transaction_p.getDateOfTransaction());
		values.put(Util.TRAN_AMOUNT, transaction_p.getAmountOfTransaction());
		values.put(Util.TRAN_ICON_CAT, transaction_p.getIconCategoryOfTransaction());
		values.put(Util.TRAN_CAT_NAME, transaction_p.getNameCategoryOfTransaction());
		values.put(Util.TRAN_ACT, transaction_p.getAccountOfTransaction());
		values.put(Util.TRAN_NOTE, transaction_p.getNoteOfTransaction());

		//Insert to row
		db.insert(Util.TABLE_NAME, null, values);

		Log.d(Util.TAG, "addTransaction: " + "item added with category "+transaction_p.getIconCategoryOfTransaction()+" "+transaction_p.getNameCategoryOfTransaction());
		db.close(); //closing db connection!
	}

	//Get all Transactions
	public List<TransactionItem> getAllTransactions() {
		List<TransactionItem> transactionArrayList = new ArrayList<>();

		SQLiteDatabase db = this.getReadableDatabase();

		//Select all contacts
		String selectAll = "SELECT * FROM " + Util.TABLE_NAME+" ORDER BY " +Util.TRAN_DATE+" DESC ";
		Cursor cursor = db.rawQuery(selectAll, null);

		//Loop through our data
		if (cursor.moveToFirst()) {
			do {
				TransactionItem transactionItem = new TransactionItem();
				transactionItem.setId(cursor.getInt(0));
				transactionItem.setDateOfTransaction(Long.parseLong(cursor.getString(1)));
				transactionItem.setAmountOfTransaction(Long.parseLong(cursor.getString(2)));
				transactionItem.setIconCategoryOfTransaction(cursor.getInt(3));
				transactionItem.setNameCategoryOfTransaction(cursor.getString(4));
				transactionItem.setAccountOfTransaction(cursor.getString(5));
				transactionItem.setNoteOfTransaction(cursor.getString(6));

				//add contact objects to our list
				transactionArrayList.add(transactionItem);
			}while (cursor.moveToNext());
		}
		db.close();
		return transactionArrayList;
	}


	public ArrayList<CategoryTotals> getAllTransactionsByCategory() {
		ArrayList<CategoryTotals> categoryTotalsArrayList = new ArrayList<>();

		SQLiteDatabase db = this.getReadableDatabase();

		//Select all contacts
		String selectAllByCategory = "SELECT "+Util.TRAN_CAT_NAME+",SUM(" +Util.TRAN_AMOUNT + ") as "+Util.CATEGORYTOTAL +" FROM " + Util.TABLE_NAME+ " GROUP BY "+Util.TRAN_CAT_NAME;
		Cursor cursor = db.rawQuery(selectAllByCategory, null);

		//Loop through our data
		if (cursor.moveToFirst()) {
			do {
				CategoryTotals categoryTotals= new CategoryTotals();
				categoryTotals.setNameCategoryOfTransaction(cursor.getString(0));
				categoryTotals.setTotalAmountOfTransaction(Long.parseLong(cursor.getString(1)));

				//add transaction objects to our list
				categoryTotalsArrayList.add(categoryTotals);
			}while (cursor.moveToNext());
		}
		db.close();
		return categoryTotalsArrayList;
	}
	public void deleteAll(){
		SQLiteDatabase db = this.getWritableDatabase();

		//delete all
		db.delete(Util.TABLE_NAME,null,null);
		/*String deleteAll = "DELETE"+" FROM " + Util.TABLE_NAME;
		db.execSQL(deleteAll, null);
		*/db.close();

	}

	public List<PeriodTotal> getTransactionsByPeriod() {
		ArrayList<PeriodTotal> periodTotalArrayList = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();

		//Select period totals
		String selectTotalByPeriod = "SELECT strftime('%m-%Y', "+"datetime("+Util.TRAN_DATE+"/1000, 'unixepoch')) as "+Util.PERIODNAME+", SUM(" +Util.TRAN_AMOUNT + ") as "+Util.PERIODTOTAL +" FROM " + Util.TABLE_NAME+ " GROUP BY "+Util.PERIODNAME+ " ORDER BY "+Util.TRAN_DATE;
		Cursor cursor = db.rawQuery(selectTotalByPeriod, null);

		//Loop through our data
		if (cursor.moveToFirst()) {
			do {
				PeriodTotal periodTotal= new PeriodTotal();
				String periodName =cursor.getString(0);
				periodTotal.setPeriodName(cursor.getString(0));
				periodTotal.setTotalOfPeriod(Integer.parseInt((cursor.getString(1))));

				//add transaction objects to our list
				periodTotalArrayList.add(periodTotal);
			}while (cursor.moveToNext());
		}
		db.close();
		return periodTotalArrayList;
	}
}
