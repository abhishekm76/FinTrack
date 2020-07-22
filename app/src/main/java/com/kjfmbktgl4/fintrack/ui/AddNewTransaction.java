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
import com.kjfmbktgl4.fintrack.util.Preferences;
import com.kjfmbktgl4.fintrack.util.Util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class AddNewTransaction extends AppCompatActivity implements View.OnClickListener {
	Button saveButton;
	Button cancelButton;
	TextInputEditText dateET;
	TextInputLayout dateTIL, amountTIL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_transaction);
		saveButton = findViewById(R.id.buttonsave);
		saveButton.setOnClickListener(this);

		cancelButton = findViewById(R.id.buttoncancel);
		cancelButton.setOnClickListener(this);

		dateET = findViewById(R.id.editTextDate);
		dateET.setOnClickListener(this);

		dateTIL=findViewById(R.id.dateTIL);
		dateTIL.setOnClickListener(this);

		amountTIL=findViewById(R.id.amountTIL);

		setDateAndCurrency();
		createCategoryViews();
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.buttonsave:
				ChipGroup chipGroup = findViewById(R.id.catChipGroup);
				Chip selChip = findViewById(chipGroup.getCheckedChipId());
				Snackbar.make(view, "you have a problem"+ chipGroup.getCheckedChipId(), Snackbar.LENGTH_LONG).show();

				break;
			case R.id.buttoncancel:
				Intent intent = new Intent(this,NavDrawerLauncher.class);
				startActivity(intent);
				break;

			case R.id.editTextDate:
			case R.id.dateTIL:
				pickerShow();
				break;
		}
	}
	private void setDateAndCurrency() {
		Locale curLocale = Locale.getDefault();
		Currency curr = Currency.getInstance(curLocale);
		String symbol = curr.getSymbol();
		View v = findViewById(R.id.currTV);
		amountTIL.setPrefixText(symbol);
		Calendar calendar = Calendar.getInstance();
		String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
		dateET.setText(currentDate);
	}
	private void createCategoryViews() {
		List<String> mcategoryName;
		mcategoryName = Preferences.getArrayPrefs("CategoryNames", this);
		Log.d(Util.TAG, "Category Names " + String.valueOf(mcategoryName.size()));
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
						dateET.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
					}
				}, year, month, day);
		picker.show();
	}

}