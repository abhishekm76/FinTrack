package com.kjfmbktgl4.fintrack.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.TransactionItem;
import com.kjfmbktgl4.fintrack.util.DateConverters;
import com.kjfmbktgl4.fintrack.util.Preferences;
import com.kjfmbktgl4.fintrack.util.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditTransaction extends AppCompatActivity implements View.OnClickListener {
	Button saveButton;
	Button cancelButton;
	TextInputEditText dateET, amountET, noteET;
	Chip categoryChip, accountChip;
	ChipGroup categoryChipGroup, accountChipGroup;
	Boolean error = false;

	String categoryName, accountName;
	TextInputLayout amountTIL;
	public boolean isNew = false;
	int id;
	private final DatabaseHandler db = new DatabaseHandler(EditTransaction.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isNew = getIntent().getBooleanExtra("isNew", false);
		setContentView(R.layout.add_edit_trans);

		amountET = findViewById(R.id.editTextAmount);
		noteET = findViewById(R.id.editTextNote);
		saveButton = findViewById(R.id.buttonsave);
		saveButton.setOnClickListener(this);
		cancelButton = findViewById(R.id.buttoncancel);
		cancelButton.setOnClickListener(this);
		dateET = findViewById(R.id.editTextDate);
		dateET.setOnClickListener(this);
		amountTIL = findViewById(R.id.amountTIL);
		categoryChipGroup = findViewById(R.id.catChipGroup);
		accountChipGroup = findViewById(R.id.actChipGroup);


		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		createCategoryViews();
		if (!isNew) {
			setValuesToEdit();
		} else {
			setCurrentDate();
			setDefaultSelection();
		}
		setCurrency();
	}

	private void setDefaultSelection() {
		ChipGroup chipGroup = findViewById(R.id.catChipGroup);
		Chip chip = (Chip) chipGroup.getChildAt(0);
		chip.setChecked(true);
		chipGroup.setSingleSelection(true);

		ChipGroup chipGroupAccount = findViewById(R.id.actChipGroup);
		Chip chipAct = (Chip) chipGroupAccount.getChildAt(0);
		chipAct.setChecked(true);
		chipGroupAccount.setSingleSelection(true);


	}

	private void setValuesToEdit() {
		Intent intent = getIntent();
		id = intent.getIntExtra("id", 0);
		TransactionItem transactionItem = new TransactionItem();
		transactionItem = db.getOneTransaction(id);

		amountET.setText((String.valueOf(transactionItem.getAmountOfTransaction())));
		noteET.setText(transactionItem.getNoteOfTransaction());

		Date transactionDate = new Date(transactionItem.getDateOfTransaction());
		String tranDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(transactionDate);
		dateET.setText(tranDate);


		categoryName = transactionItem.getNameCategoryOfTransaction();
		accountName = transactionItem.getAccountOfTransaction();

		ChipGroup chipGroup = findViewById(R.id.catChipGroup);
		for (int i = 0; i < chipGroup.getChildCount(); i++) {
			Chip chip = (Chip) chipGroup.getChildAt(i);
			if (categoryName.equals(chip.getText())) {
				chip.setChecked(true);
			}
		}
		chipGroup.setSingleSelection(true);

		ChipGroup chipGroupAccount = findViewById(R.id.actChipGroup);
		for (int i = 0; i < chipGroupAccount.getChildCount(); i++) {
			Chip chip = (Chip) chipGroupAccount.getChildAt(i);
			if (accountName.equals(chip.getText())) {
				chip.setChecked(true);
			}
		}
		chipGroupAccount.setSingleSelection(true);
	}

	private void setCurrency() {
		Locale curLocale = Locale.getDefault();
		Currency curr = Currency.getInstance(curLocale);
		String symbol = curr.getSymbol();
		amountTIL.setPrefixText(symbol);
	}

	private void setCurrentDate() {
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
			Chip chip = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout, chipGroup, false);
			chip.setText(category);
			chipGroup.addView(chip);
			chipGroup.setSingleSelection(true);
		}

		List<String> accountNames;
		accountNames = Preferences.getArrayPrefs("AccountNames", this);
		ChipGroup chipGroupAccount = findViewById(R.id.actChipGroup);
		for (String accountName : accountNames) {
			Chip chip = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout, chipGroupAccount, false);
			chip.setText(accountName);
			chipGroupAccount.addView(chip);
			chipGroupAccount.setSingleSelection(true);
		}

	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.buttonsave:
				if (isNew) {
					saveNewTransacton();
				} else {
					updateTransaction();
				}
				if(!error) {
					startPrevAct();
				}
				break;
			case R.id.buttoncancel:
				startPrevAct();
				break;

			case R.id.editTextDate:
				//case R.id.dateTIL:
				pickerShow();
				break;
		}

	}

	private void startPrevAct() {

			Intent intent = new Intent(this, NavDrawerLauncher.class);
			startActivity(intent);


	}

	private void updateTransaction() {
		checkForErrors();
		if(!error) {
			TransactionItem transaction = new TransactionItem();
			//transaction.setNameCategoryOfTransaction("Food");
			transaction.setNoteOfTransaction(String.valueOf(noteET.getText()));
			transaction.setAmountOfTransaction(Long.parseLong(String.valueOf(amountET.getText())));
			transaction.setId(id);
			transaction.setDateOfTransaction(DateConverters.dateStringToLong(dateET.getText().toString()));
			ChipGroup chipGroup = findViewById(R.id.catChipGroup);
			Chip selChip = findViewById(chipGroup.getCheckedChipId());
			String selChipString = String.valueOf(selChip.getText());
			transaction.setNameCategoryOfTransaction(selChipString);

			ChipGroup chipGroupAccount = findViewById(R.id.actChipGroup);
			Chip selChipAccount = findViewById(chipGroupAccount.getCheckedChipId());
			String selChipStringAccount = String.valueOf(selChipAccount.getText());
			transaction.setAccountOfTransaction(selChipStringAccount);

			db.updateTransaction(transaction);
		}
	}

	public void pickerShow() {
		final Calendar cldr = Calendar.getInstance();
		int day = cldr.get(Calendar.DAY_OF_MONTH);
		int month = cldr.get(Calendar.MONTH);
		int year = cldr.get(Calendar.YEAR);
		// date picker dialog
		DatePickerDialog picker = new DatePickerDialog(EditTransaction.this,
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

	private void saveNewTransacton() {
		checkForErrors();
		if(!error) {
			TransactionItem transaction = new TransactionItem();
			int id = categoryChipGroup.getCheckedChipId();
			if (id != -1) {
				categoryChip = findViewById(categoryChipGroup.getCheckedChipId());
				transaction.setNameCategoryOfTransaction(String.valueOf(categoryChip.getText()));
			}

			transaction.setNoteOfTransaction(String.valueOf(noteET.getText()));
			transaction.setAmountOfTransaction(Long.parseLong(String.valueOf(amountET.getText())));
			transaction.setDateOfTransaction(DateConverters.dateStringToLong(dateET.getText().toString()));
			int idAccount = accountChipGroup.getCheckedChipId();
			if (idAccount != -1) {
				accountChip = findViewById(accountChipGroup.getCheckedChipId());
				transaction.setAccountOfTransaction(String.valueOf(accountChip.getText()));
			}
			db.addTransaction(transaction);
			db.close();
		}


	}

	private void checkForErrors() {

		if (TextUtils.isEmpty(amountET.getText())) {
			amountTIL.setError("Please enter an amount");
			error = true;
		} else {
			error = false;
		}


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!isNew) {
			getMenuInflater().inflate(R.menu.simple_delete, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		//noinspection SimplifiableIfStatement
		if (id == R.id.delete) {
			deleteEntry();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void deleteEntry() {
		Log.d(Util.TAG, "delete " + id);
		db.deleteOne(id);
		Intent intent2 = new Intent(this, NavDrawerLauncher.class);
		startActivity(intent2);
	}
}