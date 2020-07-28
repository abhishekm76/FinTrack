package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.util.Preferences;

import java.util.Collections;
import java.util.List;

public class EditCategory extends AppCompatActivity implements View.OnClickListener {

	EditText categoryName;
	Button save, cancel;
	private String mstringToEdit, meditedString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_simplelist);
		Intent intent = getIntent();
		mstringToEdit = intent.getStringExtra("editcategory");
		categoryName = findViewById(R.id.editTextDF);
		save = findViewById(R.id.button_SaveDF);
		cancel = findViewById(R.id.button_cancelDF);
		categoryName.setText(mstringToEdit);
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);


	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.button_SaveDF:
				meditedString = categoryName.getText().toString();
				UpdateCategory();
				break;
			case R.id.button_cancelDF:
				break;
		}
		Intent intentback = new Intent(this, CategoryRV.class);
		////intentback.putExtra("updatedcategory", categoryName.getText().toString());
		startActivity(intentback);
	}

	private void UpdateCategory() {
		List<String> mcategoryName;
		mcategoryName = Preferences.getArrayPrefs("CategoryNames", this);
		Collections.replaceAll(mcategoryName, mstringToEdit, meditedString);
		Preferences.setArrayPrefs("CategoryNames", mcategoryName, this);
	}
}