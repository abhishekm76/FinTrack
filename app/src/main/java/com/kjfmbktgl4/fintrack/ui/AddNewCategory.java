package com.kjfmbktgl4.fintrack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.util.Preferences;

import java.util.Collections;
import java.util.List;

public class AddNewCategory extends AppCompatActivity implements View.OnClickListener {

	EditText categoryName;
	Button save, cancel;
	private String meditedString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_simplelist);
		categoryName = findViewById(R.id.editTextDF);
		save = findViewById(R.id.button_SaveDF);
		cancel = findViewById(R.id.button_cancelDF);
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
		startActivity(intentback);
	}

	private void UpdateCategory() {
		List<String> mcategoryName;
		mcategoryName = Preferences.getArrayPrefs("CategoryNames", this);
		mcategoryName.add(meditedString);
		Preferences.setArrayPrefs("CategoryNames", mcategoryName, this);
	}
}


