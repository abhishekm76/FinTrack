package com.kjfmbktgl4.fintrack.ui;

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

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NavDrawerLauncher extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

	private AppBarConfiguration mAppBarConfiguration;
	private RecyclerView recyclerView;
	private DatabaseHandler db;
	private TransactionRecyclerViewAdapter recyclerViewAdapter;
	private List<TransactionItem> transactionItemArrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		recyclerView = findViewById(R.id.NavRV);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		/*mAppBarConfiguration = new AppBarConfiguration.Builder(
				R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
				.setDrawerLayout(drawer)
				.build();*/


		addInitialTestData(1);
		setUpTransactionRV();

	}

	private void setUpTransactionRV() {
		// Hello set up recycler view
		//recyclerView = findViewById(R.id.transactionRV);
		/*recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));*/


		transactionItemArrayList = new ArrayList<>();
		DatabaseHandler db = new DatabaseHandler(NavDrawerLauncher.this);

		List<TransactionItem> transactionItemList = db.getAllTransactions();

		for (TransactionItem transactionItem : transactionItemList) {
			Log.d("MainActivity", "onCreate: " + transactionItem.getNameCategoryOfTransaction());
			transactionItemArrayList.add(transactionItem);
		}

		//setup adapter
		recyclerViewAdapter = new TransactionRecyclerViewAdapter(NavDrawerLauncher.this, transactionItemArrayList);
		recyclerView.setAdapter(recyclerViewAdapter);

	}

	private void addInitialTestData(int noOfItems) {
		DatabaseHandler db = new DatabaseHandler(NavDrawerLauncher.this);
		for (int i = 0; i < noOfItems; i++) {
			TransactionItem transaction = new TransactionItem();
			transaction.setNameCategoryOfTransaction("Baby Shopping");
			transaction.setNoteOfTransaction("This is a detailed note on the transaction");
			transaction.setAmountOfTransaction((long) (Math.random() * 100));
			transaction.setDateOfTransaction(System.currentTimeMillis());
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
		int id= menuItem.getItemId();
		switch (id){

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
		View v=findViewById(R.id.fab);
		Snackbar.make(v,message,Snackbar.LENGTH_LONG).show();
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