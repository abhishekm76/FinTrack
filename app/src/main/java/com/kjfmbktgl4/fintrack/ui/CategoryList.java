package com.kjfmbktgl4.fintrack.ui;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.util.Preferences;
import com.kjfmbktgl4.fintrack.util.Util;

import java.util.List;

import static com.kjfmbktgl4.fintrack.util.Util.SPREFNAME;

public class CategoryList extends ListActivity {

	//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
	List<String> listItems;

	//DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
	ArrayAdapter<String> adapter;

	//RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED


	EditText newCategory;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.category_listview);
		newCategory = findViewById(R.id.categoryEditText);
		getListData();
		adapter=new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				listItems);
		setListAdapter(adapter);
	}

	private void getListData() {
		List<String> mcategoryName;
		mcategoryName= Preferences.getArrayPrefs("CategoryNames",this);
		listItems=mcategoryName;
	/*	for (String s : listItems = mcategoryName) {
			listItems.add(s);
		};	*/



	}

	//METHOD WHICH WILL HANDLE DYNAMIC INSERTION
	public void addItems(View v) {
		String mnewCategoryName = String.valueOf(newCategory.getText());
		adapter.add(String.valueOf(newCategory.getText()));
		saveNewCategory(mnewCategoryName);

	}

	private void saveNewCategory(String mnewCategoryName) {
		//listItems.add(mnewCategoryName);
		SharedPreferences sp = getSharedPreferences(SPREFNAME,MODE_PRIVATE);
		if(!(mnewCategoryName==null)){
			Preferences.setArrayPrefs("CategoryNames",listItems,this);
			Log.d(Util.TAG, "saveNewCategory: " + listItems.size());
		}

	}


}
