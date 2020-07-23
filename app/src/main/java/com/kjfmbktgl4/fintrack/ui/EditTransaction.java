package com.kjfmbktgl4.fintrack.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.kjfmbktgl4.fintrack.util.Preferences;
import com.kjfmbktgl4.fintrack.util.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditTransaction extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
	Button saveButton;
	Button cancelButton;
	TextInputEditText dateET,amountET,noteET;
	String categoryName;
	TextInputLayout amountTIL;
	int id;
	private final DatabaseHandler db = new DatabaseHandler(EditTransaction.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_transaction);
		//Set Up Nav Drawer
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		Menu menu = navigationView.getMenu();
		menu.findItem(R.id.nav_home).setChecked(true);
		amountET = findViewById(R.id.editTextAmount);
		noteET = findViewById(R.id.editTextNote);

		saveButton = findViewById(R.id.buttonsave);
		saveButton.setOnClickListener(this);

		cancelButton = findViewById(R.id.buttoncancel);
		cancelButton.setOnClickListener(this);

		dateET = findViewById(R.id.editTextDate);
		dateET.setOnClickListener(this);

		amountTIL = findViewById(R.id.amountTIL);
		createCategoryViews();
		setValuesToEdit();

		setDateAndCurrency();



	}

	private void setValuesToEdit() {
		Intent intent = getIntent();
		id = intent.getIntExtra("id",0);
		TransactionItem transactionItem = new TransactionItem();
		transactionItem = db.getOneTransaction(id);
		snacky(transactionItem.getNameCategoryOfTransaction());

		amountET.setText((String.valueOf(transactionItem.getAmountOfTransaction())));
		noteET.setText(transactionItem.getNoteOfTransaction());

		Date transactionDate = new Date(transactionItem.getDateOfTransaction());
		String tranDate = DateFormat.getDateInstance(DateFormat.FULL).format(transactionDate);
		dateET.setText(tranDate);

		categoryName = transactionItem.getNameCategoryOfTransaction();

		ChipGroup chipGroup = findViewById(R.id.catChipGroup);
		for (int i=0; i<chipGroup.getChildCount();i++){
			Chip chip = (Chip)chipGroup.getChildAt(i);
			if(categoryName.equals(chip.getText())){
				chip.setChecked(true);
			}
		}
		chipGroup.setSingleSelection(true);












	}

	private void setDateAndCurrency() {
		Locale curLocale = Locale.getDefault();
		Currency curr = Currency.getInstance(curLocale);
		String symbol = curr.getSymbol();
		View v = findViewById(R.id.currTV);
		amountTIL.setPrefixText(symbol);

	}

	private String setCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		return DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
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
			chipGroup.setSingleSelection(true);
		}
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
		int id = menuItem.getItemId();
		switch (id) {

			case R.id.nav_home:
				snacky("Home");
				break;

			case R.id.nav_gallery:
				snacky("gallery");
				break;
			case R.id.nav_slideshow:
				snacky("slideshow");
				break;
		}
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	private void snacky(String message) {
		View v = findViewById(R.id.nav_view);
		Snackbar.make(v, message, Snackbar.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.buttonsave:
				/*ChipGroup chipGroup = findViewById(R.id.catChipGroup);
				Chip selChip = findViewById(chipGroup.getCheckedChipId());
				Snackbar.make(v, "you have a problem" + chipGroup.getCheckedChipId(), Snackbar.LENGTH_LONG).show();*/
				//snacky("The chip you selected is  " + selChip.getText());
				updateTransaction();
				break;
			case R.id.buttoncancel:
				Intent intent = new Intent(this, CategoryList.class);
				startActivity(intent);
				break;

			case R.id.editTextDate:
				//case R.id.dateTIL:
				pickerShow();
				break;
		}

	}

	private void updateTransaction() {
		TransactionItem transaction = new TransactionItem();
		transaction.setNameCategoryOfTransaction("Food");
		transaction.setNoteOfTransaction(String.valueOf(noteET.getText()));
		transaction.setAmountOfTransaction(Long.parseLong(String.valueOf(amountET.getText())));
		transaction.setId(id);
		try {
			Date transDate = DateFormat.getDateInstance().parse(String.valueOf(dateET.getText()));
			transaction.setDateOfTransaction(transDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ChipGroup chipGroup = findViewById(R.id.catChipGroup);
		Chip selChip = findViewById(chipGroup.getCheckedChipId());
		String selChipString = String.valueOf(selChip.getText());
		transaction.setNameCategoryOfTransaction(selChipString);

		db.updateTransaction(transaction);



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

}