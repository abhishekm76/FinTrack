package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.adapter.AccountRVAdapter;
import com.kjfmbktgl4.fintrack.model.Accounts;
import com.kjfmbktgl4.fintrack.util.Preferences;

import java.util.ArrayList;
import java.util.List;

public class AccountRV extends AppCompatActivity /*implements View.OnClickListener*/ {
	Context mContext;
/*	public List<Accounts> mAccountName;
	private AccountRVAdapter mRecyclerviewAdapter;*/

	@Override
	protected void onResume() {
		super.onResume();
		//mRecyclerviewAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simplelist_r_v);
		mContext=this;

		if (savedInstanceState == null) {
			AccountRVFragment fragment = new AccountRVFragment();
			fragment.setArguments(getIntent().getExtras());
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.container_fragment_accountrv, fragment)
					.commit();
		}
		/*getAllData();
		setUpRecyclerView();
		setUpFab();*/
	}
	public void setUpToolbar() {
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("FinTrack");
		getSupportActionBar().setSubtitle("Accounts");
	}


	/*private void setUpFab() {
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(this);

	}
	@Override
	public void onBackPressed(){
		NavUtils.navigateUpFromSameTask(this);
	}

	private void setUpRecyclerView() {
		RecyclerView recyclerView;
		recyclerView = findViewById(R.id.cat_recyclerview);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerviewAdapter = new AccountRVAdapter(mAccountName,mContext);
		recyclerView.setAdapter(mRecyclerviewAdapter);

		DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
		recyclerView.addItemDecoration(mDividerItemDecoration);
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

	@Override
	public void onClick(View pView) {
		Intent intent = new Intent(this,AddEditAccount.class);
		intent.putExtra("isNew",true);
		startActivity(intent);
	}*/
}