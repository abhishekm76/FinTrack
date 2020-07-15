package com.kjfmbktgl4.fintrack.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.kjfmbktgl4.fintrack.MainActivity;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.adapter.TransactionRecyclerViewAdapter;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.TransactionItem;

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

public class NavDrawerLauncher extends AppCompatActivity {

	private AppBarConfiguration mAppBarConfiguration;
	private RecyclerView recyclerView;
	private DatabaseHandler db;
	private TransactionRecyclerViewAdapter recyclerViewAdapter;
	private List<TransactionItem> transactionItemArrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nav_drawer_launcher);
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
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		mAppBarConfiguration = new AppBarConfiguration.Builder(
				R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
				.setDrawerLayout(drawer)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
		NavigationUI.setupWithNavController(navigationView, navController);

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
	public boolean onSupportNavigateUp() {
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		return NavigationUI.navigateUp(navController, mAppBarConfiguration)
				|| super.onSupportNavigateUp();
	}

}