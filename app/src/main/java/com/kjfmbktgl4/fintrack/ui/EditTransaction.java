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
import android.widget.EditText;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.util.Preferences;
import com.kjfmbktgl4.fintrack.util.Util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditTransaction extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
	Button saveButton;
	Button cancelButton;
	TextInputEditText dateET;
	TextInputLayout dateTIL, amountTIL;

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
		dateET.setText(setCurrentDate());
		createCategoryViews();

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
		String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
		return currentDate;
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
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
				ChipGroup chipGroup = findViewById(R.id.catChipGroup);
				Chip selChip = findViewById(chipGroup.getCheckedChipId());
				Snackbar.make(v, "you have a problem"+ chipGroup.getCheckedChipId(), Snackbar.LENGTH_LONG).show();
				//snacky("The chip you selected is  " + selChip.getText());
				break;
			case R.id.buttoncancel:
				Intent intent = new Intent(this,CategoryList.class);
				startActivity(intent);
				break;

			case R.id.editTextDate:
				pickerShow();
				break;
			case R.id.dateTIL:
				pickerShow();
				break;
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
						dateET.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
					}
				}, year, month, day);
		picker.show();
	}
}