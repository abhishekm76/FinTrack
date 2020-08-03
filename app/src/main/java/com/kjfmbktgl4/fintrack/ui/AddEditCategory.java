package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.util.Preferences;

import java.util.Collections;
import java.util.List;

public class AddEditCategory extends AppCompatActivity implements View.OnClickListener {
	Button save, cancel;
	ImageButton deleteButton;
	EditText categoryName;
	private String mCategoryToEdit, mEditedCategory;
	boolean mIsNew;
	private List<String> mcategoryNameList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		save = findViewById(R.id.button_SaveDF);
		cancel = findViewById(R.id.button_cancelDF);
		deleteButton = findViewById(R.id.imageButton_delDF);
		categoryName = findViewById(R.id.editTextDF);
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		mcategoryNameList = Preferences.getArrayPrefs("CategoryNames", this);
		mCategoryToEdit= getIntent().getStringExtra("CategoryName");
		mIsNew = getIntent().getBooleanExtra("isNew", false);
		setInitialValues();
	}

	private void setInitialValues() {
		if (!mIsNew) {
			categoryName.setText(mCategoryToEdit);
		}
	}

	@Override
	public void onClick(View pView) {

		switch (pView.getId()) {
			case R.id.button_SaveDF:
				mEditedCategory = categoryName.getText().toString();
				saveEditedName();
				break;
			case R.id.button_cancelDF:
				break;
			case R.id.imageButton_delDF:
				deleteItem();
		}
		Intent intent = new Intent(this, CategoryRV.class);
		startActivity(intent);

	}

	private void deleteItem() {
		mcategoryNameList.remove(mCategoryToEdit);
		Preferences.setArrayPrefs("CategoryNames", mcategoryNameList, this);
	}

	private void saveEditedName() {

		if (!mIsNew) {
			Collections.replaceAll(mcategoryNameList, mCategoryToEdit, mEditedCategory);
		} else {
			mcategoryNameList.add(mEditedCategory);
		}
		Preferences.setArrayPrefs("CategoryNames", mcategoryNameList, this);
	}

}