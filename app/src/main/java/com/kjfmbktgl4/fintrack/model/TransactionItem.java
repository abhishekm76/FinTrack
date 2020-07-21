package com.kjfmbktgl4.fintrack.model;

import java.io.Serializable;

public class TransactionItem {
	private int id;
	private long dateOfTransaction;
	private long amountOfTransaction;
	private int iconCategoryOfTransaction;
	private String nameCategoryOfTransaction;
	private String accountOfTransaction;
	private String noteOfTransaction;

	public TransactionItem(int id, long dateOfTransaction, long amountOfTransaction, int iconCategoryOfTransaction, String nameCategoryOfTransaction, String accountOfTransaction, String noteOfTransaction) {
		this.id = id;
		this.dateOfTransaction = dateOfTransaction;
		this.amountOfTransaction = amountOfTransaction;
		this.iconCategoryOfTransaction = iconCategoryOfTransaction;
		this.nameCategoryOfTransaction = nameCategoryOfTransaction;
		this.accountOfTransaction = accountOfTransaction;
		this.noteOfTransaction = noteOfTransaction;
	}

	public TransactionItem(long dateOfTransaction, long amountOfTransaction, int iconCategoryOfTransaction, String nameCategoryOfTransaction, String accountOfTransaction, String noteOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
		this.amountOfTransaction = amountOfTransaction;
		this.iconCategoryOfTransaction = iconCategoryOfTransaction;
		this.nameCategoryOfTransaction = nameCategoryOfTransaction;
		this.accountOfTransaction = accountOfTransaction;
		this.noteOfTransaction = noteOfTransaction;
	}

	public TransactionItem() {

	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(long dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public long getAmountOfTransaction() {
		return amountOfTransaction;
	}

	public void setAmountOfTransaction(long amountOfTransaction) {
		this.amountOfTransaction = amountOfTransaction;
	}

	public int getIconCategoryOfTransaction() {
		return iconCategoryOfTransaction;
	}

	public void setIconCategoryOfTransaction(int iconCategoryOfTransaction) {
		this.iconCategoryOfTransaction = iconCategoryOfTransaction;
	}

	public String getAccountOfTransaction() {
		return accountOfTransaction;
	}

	public void setAccountOfTransaction(String accountOfTransaction) {
		this.accountOfTransaction = accountOfTransaction;
	}

	public String getNoteOfTransaction() {
		return noteOfTransaction;
	}

	public void setNoteOfTransaction(String noteOfTransaction) {
		this.noteOfTransaction = noteOfTransaction;
	}
	public String getNameCategoryOfTransaction() {
		return nameCategoryOfTransaction;
	}

	public void setNameCategoryOfTransaction(String nameCategoryOfTransaction) {
		this.nameCategoryOfTransaction = nameCategoryOfTransaction;
	}
}
