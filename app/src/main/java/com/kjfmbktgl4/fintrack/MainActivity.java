package com.kjfmbktgl4.fintrack;

import android.os.Bundle;
import android.util.Log;

import com.kjfmbktgl4.fintrack.adapter.TransactionRecyclerViewAdapter;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.TransactionItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private RecyclerView recyclerView;
	private DatabaseHandler db;
	private TransactionRecyclerViewAdapter recyclerViewAdapter;
	private List<TransactionItem> transactionItemArrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_rv);
		recyclerView = findViewById(R.id.transactionRV);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		//DatabaseHandler db = new DatabaseHandler(MainActivity.this);

	/*	BottomNavigationView navView = findViewById(R.id.nav_view);
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(navView, navController);*/

		//addInitialTestData(5);// enters x no of transactions into the table for testing
		setUpTransactionRV(); // sets up the recycler view adapter

	}

	private void setUpTransactionRV() {
		// Hello set up recycler view
		//recyclerView = findViewById(R.id.transactionRV);
		/*recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));*/


		transactionItemArrayList = new ArrayList<>();
		DatabaseHandler db = new DatabaseHandler(MainActivity.this);

		List<TransactionItem> transactionItemList = db.getAllTransactions();

		for (TransactionItem transactionItem : transactionItemList) {
			Log.d("MainActivity", "onCreate: " + transactionItem.getNameCategoryOfTransaction());
			transactionItemArrayList.add(transactionItem);
		}

		//setup adapter
		recyclerViewAdapter = new TransactionRecyclerViewAdapter(MainActivity.this, transactionItemArrayList);
		recyclerView.setAdapter(recyclerViewAdapter);

	}

	private void addInitialTestData(int noOfItems){
		DatabaseHandler db = new DatabaseHandler(MainActivity.this);
		for (int i=0; i<noOfItems;i++){
			TransactionItem transaction = new TransactionItem();
			transaction.setNameCategoryOfTransaction("Baby Shopping");
			transaction.setNoteOfTransaction("This is a detailed note on the transaction");
			transaction.setAmountOfTransaction((long) (Math.random()*100));
			transaction.setDateOfTransaction(System.currentTimeMillis());
			transaction.setAccountOfTransaction("HDFC "+i);
			db.addTransaction(transaction);
		}



	}
}