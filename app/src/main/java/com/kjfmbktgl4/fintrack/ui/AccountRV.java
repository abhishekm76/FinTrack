package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.adapter.AccountRVAdapter;
import com.kjfmbktgl4.fintrack.adapter.CategoryRVAdapter;
import com.kjfmbktgl4.fintrack.model.Accounts;
import com.kjfmbktgl4.fintrack.util.Preferences;

import java.util.ArrayList;
import java.util.List;

public class AccountRV extends AppCompatActivity {
	Context mContext;
	public List<Accounts> mAccountName;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_r_v);
		//checkUpdatedCategory();
		getAllData();
		setUpRecyclerView();
		//setUpFab();
	}

	private void setUpRecyclerView() {
		RecyclerView recyclerView;
		recyclerView = findViewById(R.id.cat_recyclerview);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		AccountRVAdapter recyclerviewAdapter = new AccountRVAdapter(mAccountName,mContext);
		recyclerView.setAdapter(recyclerviewAdapter);
	}

	private void getAllData() {
		List<String> accountNameString;
		mAccountName = new ArrayList<Accounts>();
		accountNameString = Preferences.getArrayPrefs("AccountNames", this);
		for (String accountNameItem : accountNameString){
			Accounts account = new Accounts();
			account.setStringaccountName(accountNameItem);
			mAccountName.add(account);
		}
	}
}