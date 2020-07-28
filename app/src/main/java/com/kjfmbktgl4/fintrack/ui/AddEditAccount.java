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
import com.kjfmbktgl4.fintrack.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AddEditAccount extends AppCompatActivity implements View.OnClickListener {
	Button save, cancel;
	ImageButton deleteButton;
	EditText accountName;
	private String mAccountToEdit, mEditedAccount;
	boolean mIsNew;
	private List<String> maccountNameList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_simplelist);
		save = findViewById(R.id.button_SaveDF);
		cancel = findViewById(R.id.button_cancelDF);
		deleteButton = findViewById(R.id.imageButton_delDF);
		accountName = findViewById(R.id.editTextDF);
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		maccountNameList = Preferences.getArrayPrefs("AccountNames", this);
		mAccountToEdit = getIntent().getStringExtra("AccountName");
		mIsNew = getIntent().getBooleanExtra("isNew", false);
		setInitialValues();
	}

	private void setInitialValues() {
		if (!mIsNew) {
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
			case R.id.imageButton_delDF:
				deleteItem();
		}
		Intent intent = new Intent(this, AccountRV.class);
		startActivity(intent);

	}

	private void deleteItem() {
		maccountNameList.remove(mAccountToEdit);
		Preferences.setArrayPrefs("AccountNames", maccountNameList, this);
	}

	private void saveEditedName() {
		if (!mIsNew) {
			Collections.replaceAll(maccountNameList, mAccountToEdit, mEditedAccount);
		} else {
			maccountNameList.add(mEditedAccount);
		}
		Preferences.setArrayPrefs("AccountNames", maccountNameList, this);
	}
}