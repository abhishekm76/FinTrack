package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.TransactionItem;
import com.kjfmbktgl4.fintrack.util.Preferences;
import com.kjfmbktgl4.fintrack.util.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AddNewTransaction extends AppCompatActivity implements View.OnClickListener {
	Button saveButton;
	Button cancelButton;
	String selCategoryName, selAccountName;
	TextInputEditText dateET, noteET, amountET;
	Chip categoryChip, accountChip;
	ChipGroup categoryChipGroup, accountChipGroup;
	TextInputLayout dateTIL, amountTIL;
	private final DatabaseHandler db = new DatabaseHandler(AddNewTransaction.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_transaction);
		saveButton = findViewById(R.id.buttonsave);
		saveButton.setOnClickListener(this);
		categoryChipGroup = findViewById(R.id.catChipGroup);
		accountChipGroup=findViewById(R.id.actChipGroup);

		cancelButton = findViewById(R.id.buttoncancel);
		cancelButton.setOnClickListener(this);

		dateET = findViewById(R.id.editTextDate);
		dateET.setOnClickListener(this);

	/*	dateTIL = findViewById(R.id.dateTIL);
		dateTIL.setOnClickListener(this);*/

		amountTIL = findViewById(R.id.amountTIL);
		amountET = findViewById(R.id.editTextAmount);
		noteET = findViewById(R.id.editTextNote);

		setDateAndCurrency();
		createCategoryViews();
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.buttonsave:
				saveNewTransacton();
				Intent intent1 = new Intent(this, NavDrawerLauncher.class);
				startActivity(intent1);

				break;
			case R.id.buttoncancel:
				Intent intent = new Intent(this, NavDrawerLauncher.class);
				startActivity(intent);
				break;

			case R.id.editTextDate:
				//case R.id.dateTIL:
				pickerShow();
				break;
		}
	}

	private void saveNewTransacton() {

		TransactionItem transaction = new TransactionItem();
		int id = categoryChipGroup.getCheckedChipId();
		if (id != -1) {
			categoryChip = findViewById(categoryChipGroup.getCheckedChipId());
			transaction.setNameCategoryOfTransaction(String.valueOf(categoryChip.getText()));
		}
		transaction.setNoteOfTransaction(String.valueOf(noteET.getText()));
		transaction.setAmountOfTransaction(Long.parseLong(String.valueOf(amountET.getText())));

		try {
			Date transDate = DateFormat.getDateInstance().parse(dateET.getText().toString());
			transaction.setDateOfTransaction(transDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int idAccount = accountChipGroup.getCheckedChipId();
		if (idAccount != -1) {
			accountChip = findViewById(accountChipGroup.getCheckedChipId());
			transaction.setAccountOfTransaction(String.valueOf(accountChip.getText()));
		}

		db.addTransaction(transaction);
		db.close();


	}

	private void setDateAndCurrency() {
		Locale curLocale = Locale.getDefault();
		Currency curr = Currency.getInstance(curLocale);
		String symbol = curr.getSymbol();

		amountTIL.setPrefixText(symbol);
		Calendar calendar = Calendar.getInstance();
		String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
		dateET.setText(currentDate);
	}

	private void createCategoryViews() {
		List<String> mcategoryName;
		mcategoryName = Preferences.getArrayPrefs("CategoryNames", this);
		Log.d(Util.TAG, "Category Names " + mcategoryName.size());
		ChipGroup chipGroup = findViewById(R.id.catChipGroup);


		for (String category : mcategoryName) {
			Chip chip = new Chip(this);
			chip.setTextAppearance(android.R.style.TextAppearance_Material_Body1);

			chip.setRippleColorResource(R.color.colorPrimary);
			chip.setChipIconResource(R.drawable.ic_outline_delete_24);
			chip.setChipCornerRadius(0);
			chip.setCheckable(true);
			chip.setText(category);

			chipGroup.addView(chip);
		}

		List<String> accountNames;
		accountNames = Preferences.getArrayPrefs("AccountNames", this);
		ChipGroup chipGroupAccount = findViewById(R.id.actChipGroup);
		for (String accountName : accountNames) {
			Chip chip = new Chip(this);
			chip.setTextAppearance(android.R.style.TextAppearance_Material_Body1);
			chip.setRippleColorResource(R.color.colorPrimary);
			chip.setChipIconResource(R.drawable.ic_outline_delete_24);
			chip.setChipCornerRadius(0);
			chip.setCheckable(true);
			chip.setText(accountName);
			chipGroupAccount.addView(chip);
			chipGroupAccount.setSingleSelection(true);

		}

	}

	public void pickerShow() {
		final Calendar cldr = Calendar.getInstance();
		int day = cldr.get(Calendar.DAY_OF_MONTH);
		int month = cldr.get(Calendar.MONTH);
		int year = cldr.get(Calendar.YEAR);
		// date picker dialog
		DatePickerDialog picker = new DatePickerDialog(AddNewTransaction.this,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						cldr.set(year, monthOfYear, dayOfMonth);
						String setDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(cldr.getTime());

						dateET.setText(setDate);
					}
				}, year, month, day);
		picker.show();


	}

}