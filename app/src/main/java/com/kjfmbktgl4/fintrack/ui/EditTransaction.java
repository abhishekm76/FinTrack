package com.kjfmbktgl4.fintrack.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.kjfmbktgl4.fintrack.R;

import java.util.Currency;
import java.util.Locale;

public class EditTransaction extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


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

		Locale curLocale = Locale.getDefault();
	Currency curr =Currency.getInstance(curLocale);
	String symbol=curr.getSymbol();
	View v =findViewById(R.id.currTV);


		Snackbar.make(v, "Currnecy is  "+ symbol, Snackbar.LENGTH_LONG)
				.setAction("Action", null).show();

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

}