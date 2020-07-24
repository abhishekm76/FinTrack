package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.adapter.CategoryRVAdapter;

import com.kjfmbktgl4.fintrack.util.Preferences;

import java.util.List;

public class CategoryRV extends AppCompatActivity  {
	private RecyclerView recyclerView;
	private CategoryRVAdapter recyclerviewAdapter;
	private List<String> categoryList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_r_v);
		getAllData();
		setUpRecyclerView();
	}

	private void setUpRecyclerView() {
		recyclerView = findViewById(R.id.cat_recyclerview);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		recyclerviewAdapter = new CategoryRVAdapter(this,categoryList);
		recyclerView.setAdapter(recyclerviewAdapter);
	}

	private void getAllData() {
		List<String> mcategoryName;
		mcategoryName = Preferences.getArrayPrefs("CategoryNames", this);
		categoryList= mcategoryName;

	}
}