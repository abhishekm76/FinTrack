package com.kjfmbktgl4.fintrack.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.util.Preferences;
import com.kjfmbktgl4.fintrack.util.Util;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class EditTransaction extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
	Button saveButton;
	Button cancelButton;

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

		Locale curLocale = Locale.getDefault();
		Currency curr = Currency.getInstance(curLocale);
		String symbol = curr.getSymbol();
		View v = findViewById(R.id.currTV);
		Snackbar.make(v, "Currnecy is  " + symbol, Snackbar.LENGTH_LONG)
				.setAction("Action", null).show();
		createCategoryViews();
	}

	private void createCategoryViews() {
		List<String> mcategoryName;
		mcategoryName = Preferences.getArrayPrefs("CategoryNames", this);
		Log.d(Util.TAG, "Category Names " + String.valueOf(mcategoryName.size()));
		ChipGroup chipGroup = findViewById(R.id.catChipGroup);
		for (String category : mcategoryName) {
			Chip chip = new Chip(this);
			chip.setTextAppearance(android.R.style.TextAppearance_Material_Body1);
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
				snacky("The chip you selected is  "+selChip.getText());
				break;
			case R.id.buttoncancel:
				snacky("cancelled");
				break;
		}

	}
}