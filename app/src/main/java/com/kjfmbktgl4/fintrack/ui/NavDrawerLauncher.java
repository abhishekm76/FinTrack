package com.kjfmbktgl4.fintrack.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import com.kjfmbktgl4.fintrack.BuildConfig;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.adapter.TransactionRecyclerViewAdapter;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.TransactionItem;
import com.kjfmbktgl4.fintrack.util.Preferences;
import com.kjfmbktgl4.fintrack.util.Util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;


//// TODO: 04-08-2020 graphs axis is repeatig values in bar chart

// TODO: 04-08-2020 after settign one date on teh graph the other buttons stop working??
// TODO: 23-07-2020 navigation optimise
// TODO: 23-07-2020 set up themes styles and colours
// TODO: 23-07-2020 add income options
// TODO: 23-07-2020 recyclerview search
// TODO: 23-07-2020 add icon options and colours in settings
// TODO: 23-07-2020 OTHER LANGUAGES support
// TODO: 23-07-2020 add apache
// TODO: 23-07-2020 proguard
// TODO: 23-07-2020 backup
// TODO: 23-07-2020 adjsut resize to show input while entering it
// TODO: 23-07-2020 when delete all and restart the chip labels disapper
//// TODO: 23-07-2020 check if prefsize causes an issue when there is no app data present
// TODO: 24-07-2020 saving a transaction with no amount crashes the app
// TODO: 24-07-2020 back sends us to a previous view of the home recycler view?? also category edit
// TODO: 26-07-2020 graph by month is not correct for the entry of the first of the month 

import static com.kjfmbktgl4.fintrack.util.Util.SPREFNAME;

public class NavDrawerLauncher extends AppCompatActivity {
	private AppBarConfiguration mAppBarConfiguration;
	private RecyclerView recyclerView;
	//private DatabaseHandler db;
	private final DatabaseHandler db = new DatabaseHandler(NavDrawerLauncher.this);
	private TransactionRecyclerViewAdapter recyclerViewAdapter;
	public List<TransactionItem> transactionItemArrayList;
	public List<TransactionItem> exportArrayList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		executeStrictModePolicy();


		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_main);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(NavDrawerLauncher.this, EditTransaction.class);
				intent.putExtra("isNew", true);
				startActivity(intent);
			}
		});
/*
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		Menu menu = navigationView.getMenu();
		menu.findItem(R.id.nav_home).setChecked(true);
		//hamburger
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();
*/
		//RV methods
		//addInitialTestData(5);
		//clearSPref(); //use to test sharedPref by deleting it completely
		setUpTransactionRV();
		setUpCategory();
		setUpAccount();
	}

	private void executeStrictModePolicy() {
		if(BuildConfig.DEBUG){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.detectAll()
					.penaltyLog()
					.build();
			StrictMode.setThreadPolicy(policy);


		}

	}

	private void clearSPref() {
		Preferences.clearPrefs(this);
	}

	private void setUpAccount() {
		List<String> account_array;
		account_array = Preferences.getArrayPrefs("AccountNames", this);
		if (account_array.size() == 0) {
			Resources res = getResources();
			account_array = Arrays.asList(res.getStringArray(R.array.account_array));
			Preferences.setArrayPrefs("AccountNames", account_array, this);
		}
	}


	private void setUpCategory() {

		List<String> mcategoryName;
		mcategoryName = Preferences.getArrayPrefs("CategoryNames", this);
		if (mcategoryName.size() == 0) {

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
		//DatabaseHandler db = new DatabaseHandler(NavDrawerLauncher.this);


		List<TransactionItem> transactionItemList = db.getAllTransactions();
		transactionItemArrayList.addAll(transactionItemList);
		//setup adapter
		recyclerViewAdapter = new TransactionRecyclerViewAdapter(NavDrawerLauncher.this, transactionItemArrayList);
		recyclerView.setAdapter(recyclerViewAdapter);

	}

	@Override
	protected void onResume() {
		super.onResume();
		recyclerViewAdapter.notifyDataSetChanged();
	}

	private void addInitialTestData(int noOfItems) {
		//DatabaseHandler db = new DatabaseHandler(NavDrawerLauncher.this);
		Util util = new Util();
		for (int i = 0; i < noOfItems; i++) {
			Random rnd = new Random();
			TransactionItem transaction = new TransactionItem();
			transaction.setNameCategoryOfTransaction("Food");
			transaction.setNoteOfTransaction("This is a detailed note on the transaction and can be quite long");
			transaction.setAmountOfTransaction((long) (Math.random() * 10) * 10 + 10);
			Long ms = util.generateRandomDate();
			transaction.setDateOfTransaction(ms);
			transaction.setAccountOfTransaction("HDFC " + i);
			db.addTransaction(transaction);
			db.close();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nav_drawer_launcher, menu);
		return true;
	}

	/*@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
		int id = menuItem.getItemId();
		switch (id) {

			case R.id.nav_home:

				break;

			case R.id.nav_gallery:

				Intent intentForBarChart = new Intent(this, BarChart.class);
				startActivity(intentForBarChart);

				break;
			case R.id.nav_slideshow:
				Intent intent = new Intent(this, PieChart.class);
				startActivity(intent);
				break;
		}
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;

	}*/

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
		if (id == R.id.delete_all) {
			db.deleteAll();
			recyclerViewAdapter.notifyDataSetChanged();
			return true;
		}
		if (id == R.id.export) {
			View v = findViewById(R.id.fab);
			exportData(v);

			return true;
		}

		if (id == R.id.categoryList) {
			Intent intentCategory = new Intent(this, CategoryRV.class);
			startActivity(intentCategory);

			return true;
		}

		if (id == R.id.accountList) {
			Intent intentAccount = new Intent(this, AccountRV.class);
			startActivity(intentAccount);

			return true;
		}

		if (id == R.id.trend) {
			Intent intentForBarChart = new Intent(this, BarChart.class);
			startActivity(intentForBarChart);

			return true;
		}

		if (id == R.id.distribution) {
			Intent intent = new Intent(this, PieChart.class);
			startActivity(intent);

			return true;
		}


		return super.onOptionsItemSelected(item);
	}

	private void exportData(View view) {
		//generate data
		List<TransactionItem> transactionItemListForExport = db.getAllTransactions();
		exportArrayList = new ArrayList<>();
		exportArrayList.addAll(transactionItemListForExport);
		StringBuilder data = new StringBuilder();
		data.append("Index,Weekday,Day,Year,Amount,Account,Category,Note");
		for (int i = 0; i < exportArrayList.size(); i++) {
			Date transactionDate = new Date(exportArrayList.get(i).getDateOfTransaction());
			String tranDate = DateFormat.getDateInstance(DateFormat.FULL).format(transactionDate);

			data.append("\n" + String.valueOf(i) + "," + String.valueOf(tranDate));
			data.append("," + String.valueOf(exportArrayList.get(i).getAmountOfTransaction()));
			data.append("," + String.valueOf(exportArrayList.get(i).getAccountOfTransaction()));
			data.append("," + String.valueOf(exportArrayList.get(i).getNameCategoryOfTransaction()));
			data.append("," + String.valueOf(exportArrayList.get(i).getNoteOfTransaction()));

		}


		try {
			//saving the file into device
			FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
			out.write((data.toString()).getBytes());
			out.close();

			//exporting
			Context context = getApplicationContext();
			File filelocation = new File(getFilesDir(), "data.csv");
			Uri path = FileProvider.getUriForFile(context, "com.kjfmbktgl4.fintrack.fileprovider", filelocation);
			Intent fileIntent = new Intent(Intent.ACTION_SEND);
			fileIntent.setType("text/csv");
			fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
			fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			fileIntent.putExtra(Intent.EXTRA_STREAM, path);
			startActivity(Intent.createChooser(fileIntent, "Exported data"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private class AsyncDataFetch extends AsyncTask<Void,Void,List<TransactionItem>>{


		@Override
		protected List<TransactionItem> doInBackground(Void... voids) {
			List<TransactionItem> listAsync = new ArrayList<>();
			listAsync=db.getAllTransactions();
			return listAsync;
		}

		@Override
		protected void onPostExecute(List<TransactionItem> transactionItems) {
			transactionItemArrayList.addAll(transactionItems);
		}
	}

}