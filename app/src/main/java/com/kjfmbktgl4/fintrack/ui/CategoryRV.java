package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.adapter.CategoryRVAdapter;

import com.kjfmbktgl4.fintrack.adapter.TransactionRecyclerViewAdapter;
import com.kjfmbktgl4.fintrack.util.Preferences;

import java.util.List;

public class CategoryRV extends AppCompatActivity  {
	private RecyclerView recyclerView;
	private CategoryRVAdapter recyclerviewAdapter;
	private List<String> categoryList;
	Context mContext;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=this;
		setContentView(R.layout.activity_simplelist_r_v);
		checkUpdatedCategory();
		getAllData();
		setUpRecyclerView();
		setUpFab();
	}

	private void setUpFab() {
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intentNew = new Intent(CategoryRV.this,AddNewCategory.class);
				startActivity(intentNew);
			}
		});
	}

	private void checkUpdatedCategory() {
		Intent intentFromEdit=getIntent();
		String updatedCategory= intentFromEdit.getStringExtra("updatedCategory");
	}

	private void setUpRecyclerView() {
		recyclerView = findViewById(R.id.cat_recyclerview);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		recyclerviewAdapter = new CategoryRVAdapter(mContext,categoryList);
		recyclerView.setAdapter(recyclerviewAdapter);
	}

	private void getAllData() {
		List<String> mcategoryName;
		mcategoryName = Preferences.getArrayPrefs("CategoryNames", this);
		categoryList= mcategoryName;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.recycler_search, menu);
		MenuItem searchMenuItem = menu.findItem(R.id.action_search);
		SearchView searchView= (SearchView) searchMenuItem.getActionView();
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				recyclerviewAdapter.getFilter().filter(newText);
				return false;
			}
		});

	return super.onCreateOptionsMenu(menu);
	}
}