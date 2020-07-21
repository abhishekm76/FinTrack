package com.kjfmbktgl4.fintrack.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.adapter.TransactionRecyclerViewAdapter;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.TransactionItem;
import com.kjfmbktgl4.fintrack.util.Preferences;
import com.kjfmbktgl4.fintrack.util.Util;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.kjfmbktgl4.fintrack.util.Util.SPREFNAME;

public class NavDrawerLauncher extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	private AppBarConfiguration mAppBarConfiguration;
	private RecyclerView recyclerView;
	private DatabaseHandler db;
	private TransactionRecyclerViewAdapter recyclerViewAdapter;
	public List<TransactionItem> transactionItemArrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	/*	recyclerView = findViewById(R.id.NavRV);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));*/
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		Menu menu = navigationView.getMenu();
		menu.findItem(R.id.nav_home).setChecked(true);
		//hamburger
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();
		//RV methods
		addInitialTestData(5);
		setUpTransactionRV();
		setUpCategory();
	}

	private void setUpCategory() {

		List<String> mcategoryName;
		mcategoryName = Preferences.getArrayPrefs("CategoryNames", this);

		SharedPreferences sp = getSharedPreferences(SPREFNAME, MODE_PRIVATE);
		if ((mcategoryName == null)) {

			Resources res = getResources();
			mcategoryName = Arrays.asList(res.getStringArray(R.array.category_array));
			Preferences.setArrayPrefs("CategoryNames", mcategoryName, this);
			Log.d(Util.TAG, "writing new sp again in navdl: " + mcategoryName.size());
		}
	}

	private void setUpTransactionRV() {
		// Hello set up recycler view
		recyclerView = findViewById(R.id.NavRV);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		transactionItemArrayList = new ArrayList<>();
		DatabaseHandler db = new DatabaseHandler(NavDrawerLauncher.this);
		List<TransactionItem> transactionItemList = db.getAllTransactions();
		for (TransactionItem transactionItem : transactionItemList) {
			transactionItemArrayList.add(transactionItem);
		}
		//setup adapter
		recyclerViewAdapter = new TransactionRecyclerViewAdapter(NavDrawerLauncher.this, transactionItemArrayList);
		recyclerView.setAdapter(recyclerViewAdapter);
	}


	private void addInitialTestData(int noOfItems) {
		DatabaseHandler db = new DatabaseHandler(NavDrawerLauncher.this);
		for (int i = 0; i < noOfItems; i++) {
			Random rnd = new Random();
			TransactionItem transaction = new TransactionItem();
			transaction.setNameCategoryOfTransaction("Food");
			transaction.setNoteOfTransaction("This is a detailed note on the transaction");
			transaction.setAmountOfTransaction((long) (Math.random() * 100));

			Long ms = -1546361722L + (Math.abs(rnd.nextLong()) % (1L * 365 * 24 * 60 * 60 * 1000));
			transaction.setDateOfTransaction(ms);
			transaction.setAccountOfTransaction("HDFC " + i);
			db.addTransaction(transaction);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nav_drawer_launcher, menu);
		return true;
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
				Intent intent = new Intent(this, PieChart.class);
				startActivity(intent);
				break;
		}
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;

	}

	private void snacky(String message) {
		View v = findViewById(R.id.fab);
		Snackbar.make(v, message, Snackbar.LENGTH_LONG).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}