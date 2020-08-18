/*
package com.kjfmbktgl4.fintrack.ui;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ItemDecoration;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;


// TODO: 23-07-2020 navigation optimise
//// TODO: 15-08-2020 add view model
// TODO: 11-08-2020 add settings
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
//// TODO: 14-08-2020 check recyclerview scrolling issues
// TODO: 14-08-2020 fragment oncreateview getting called twice?

import static com.kjfmbktgl4.fintrack.util.Util.SPREFNAME;

public class NavDrawerLauncher extends AppCompatActivity {
	private AppBarConfiguration mAppBarConfiguration;
	private RecyclerView recyclerView;
	private String mType;
	//private DatabaseHandler db;
	private final DatabaseHandler db = new DatabaseHandler(NavDrawerLauncher.this);
	private TransactionRecyclerViewAdapter recyclerViewAdapter;
	public List<TransactionItem> transactionItemArrayList;
	public List<TransactionItem> exportArrayList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//executeStrictModePolicy();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_main);

		if (savedInstanceState == null) {
			MainFragment fragment = new MainFragment();
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.container_mainrecyclerview, fragment)
					.commit();
		}

		setUpToolbar();

*/
/*		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(NavDrawerLauncher.this, EditTransaction.class);
				intent.putExtra("isNew", true);
				startActivity(intent);
			}
		});*//*


		//RV methods
		//addInitialTestData(5);
		//clearSPref(); //use to test sharedPref by deleting it completely
*/
/*
		getAsyncData();
		setUpTransactionRV();
		new AsyncSetUpCatAct().execute();
*//*


	}

	private void setUpToolbar() {

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("FinTrack");
		getSupportActionBar().setSubtitle("Home");
	}

	*/
/*private void getAsyncData() {
		transactionItemArrayList = new ArrayList<>();
		AsyncDataFetch asyncDataFetch = new AsyncDataFetch();
		asyncDataFetch.execute();
	}

	private void executeStrictModePolicy() {
		if (BuildConfig.DEBUG) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.detectAll()
					.penaltyLog()
					.build();
			StrictMode.setThreadPolicy(policy);


		}else{

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
		} else{

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
		else{

		}
	}

	private void setUpTransactionRV() {
		// Hello set up recycler view
		recyclerView = findViewById(R.id.NavRV);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerViewAdapter = new TransactionRecyclerViewAdapter(NavDrawerLauncher.this, transactionItemArrayList);
		recyclerView.setAdapter(recyclerViewAdapter);
		*//*
*/
/*ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
		recyclerView.addItemDecoration(itemDecoration);*//*
*/
/*



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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
			case R.id.action_settings:
				break;
			case R.id.delete_all:
				deleteAlertDialog();
				break;
			case R.id.export:
				new AsyncWriteExport().execute();
				break;
			case R.id.categoryList:
				mType = "Category";
				Intent intentCategory = new Intent(this, AccountRV.class);
				intentCategory.putExtra("Type",mType);
				startActivity(intentCategory);
				break;
			case R.id.accountList:
				mType = "Account";
				Intent intentAccount = new Intent(this, AccountRV.class);
				intentAccount.putExtra("Type",mType);
				startActivity(intentAccount);
				break;
			case R.id.trend:
				Intent intentForBarChart = new Intent(this, BarChart.class);
				startActivity(intentForBarChart);
				break;
			case R.id.distribution:
				Intent intent = new Intent(this, PieChart.class);
				startActivity(intent);
				break;
		}

		return super.onOptionsItemSelected(item);
	}
	private void deleteAlertDialog(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("This will delete all data, are you sure you want to delete all the data?");
				alertDialogBuilder.setPositiveButton("yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								new AsyncDeleteAll().execute();
							}
						});

		alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				*//*
*/
/*finish();*//*
*/
/*
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}



	private void exportData() {

		exportArrayList = new ArrayList<>();
		exportArrayList.addAll(transactionItemArrayList);
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

	private class AsyncDataFetch extends AsyncTask<Void, Void, List<TransactionItem>> {


		@Override
		protected List<TransactionItem> doInBackground(Void... voids) {
			List<TransactionItem> listAsync = new ArrayList<>();
			listAsync = db.getAllTransactions();
			return listAsync;
		}

		@Override
		protected void onPostExecute(List<TransactionItem> transactionItems) {
			transactionItemArrayList.addAll(transactionItems);
			recyclerViewAdapter.notifyDataSetChanged();
		}
	}

	private class AsyncSetUpCatAct extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... voids) {
			setUpCategory();
			setUpAccount();
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			Log.d(Util.TAG, "setup completed");
		}
	}

	private class AsyncWriteExport extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... voids) {
			exportData();
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			Log.d(Util.TAG, "data exported");
		}
	}

	private class AsyncDeleteAll extends AsyncTask<Void, Void, Void> {


		@Override
		protected Void doInBackground(Void... voids) {
			db.deleteAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			transactionItemArrayList.clear();
			recyclerViewAdapter.notifyDataSetChanged();
		}
	}*//*


}*/
