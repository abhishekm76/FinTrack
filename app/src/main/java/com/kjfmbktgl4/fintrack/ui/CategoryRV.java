package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.adapter.CategoryRVAdapter;

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
		setContentView(R.layout.activity_category_r_v);
		checkUpdatedCategory();
		getAllData();
		setUpRecyclerView();
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
}