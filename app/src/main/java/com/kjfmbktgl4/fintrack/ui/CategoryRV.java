package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.kjfmbktgl4.fintrack.adapter.CategoryRVAdapter2;

import com.kjfmbktgl4.fintrack.adapter.TransactionRecyclerViewAdapter;
import com.kjfmbktgl4.fintrack.model.Accounts;
import com.kjfmbktgl4.fintrack.model.Category;
import com.kjfmbktgl4.fintrack.util.Preferences;

import java.util.ArrayList;
import java.util.List;

public class CategoryRV extends AppCompatActivity {
	private RecyclerView recyclerView;
	private CategoryRVAdapter2 recyclerviewAdapter;
	private List<Category> mcategoryList;
	Context mContext;
	private ArrayList<Category> mcategoryArrayList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_simplelist_r_v);
		setUpToolbar();
		checkUpdatedCategory();
		getAllData();
		setUpRecyclerView();
		setUpFab();
	}

	private void setUpToolbar() {
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("FinTrack");
		getSupportActionBar().setSubtitle("Categories");

	}

	@Override
	protected void onResume() {
		super.onResume();
		recyclerviewAdapter.notifyDataSetChanged();
	}

	private void setUpFab() {
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intentNew = new Intent(CategoryRV.this, AddEditCategory.class);
				intentNew.putExtra("isNew", true);
				startActivity(intentNew);
			}
		});
	}

	private void checkUpdatedCategory() {
		Intent intentFromEdit = getIntent();
		String updatedCategory = intentFromEdit.getStringExtra("updatedCategory");
	}

	private void setUpRecyclerView() {
		recyclerView = findViewById(R.id.cat_recyclerview);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		recyclerviewAdapter = new CategoryRVAdapter2(mcategoryList, mContext);
		recyclerView.setAdapter(recyclerviewAdapter);

		DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
		recyclerView.addItemDecoration(mDividerItemDecoration);

	}

	private void getAllData() {
		List<String> mcategoryNameString;
		mcategoryList = new ArrayList<Category>();
		mcategoryNameString = Preferences.getArrayPrefs("CategoryNames", this);
		for (String categoryNameItem : mcategoryNameString) {
			Category category = new Category();
			category.setMcategoryName(categoryNameItem);
			mcategoryList.add(category);
		}

	}

}