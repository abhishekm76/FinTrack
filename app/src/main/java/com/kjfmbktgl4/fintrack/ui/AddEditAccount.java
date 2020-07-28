package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.util.Preferences;
import com.kjfmbktgl4.fintrack.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddEditAccount extends AppCompatActivity implements View.OnClickListener {
	Button save, cancel;
	EditText accountName;
	private String mAccountToEdit, mEditedAccount;
	boolean mIsNew;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_simplelist);
		save = findViewById(R.id.button_SaveDF);
		cancel = findViewById(R.id.button_cancelDF);
		accountName = findViewById(R.id.editTextDF);
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
		setInitialValues();
	}

	private void setInitialValues() {
		mIsNew = getIntent().getBooleanExtra("isNew", false);
		if (!mIsNew) {
			mAccountToEdit = getIntent().getStringExtra("AccountName");
			accountName.setText(mAccountToEdit);
		}
	}

	@Override
	public void onClick(View pView) {
		switch (pView.getId()) {
			case R.id.button_SaveDF:
				mEditedAccount = accountName.getText().toString();
				saveEditedName();
				break;
			case R.id.button_cancelDF:
				break;
		}
		Intent intent = new Intent(this, AccountRV.class);
		startActivity(intent);

	}

	private void saveEditedName() {
		List<String> accountNameList;
		accountNameList = Preferences.getArrayPrefs("AccountNames", this);
		if (!mIsNew) {
			Collections.replaceAll(accountNameList, mAccountToEdit, mEditedAccount);
		} else {
			accountNameList.add(mEditedAccount);
		}
		Preferences.setArrayPrefs("AccountNames", accountNameList, this);
	}
}