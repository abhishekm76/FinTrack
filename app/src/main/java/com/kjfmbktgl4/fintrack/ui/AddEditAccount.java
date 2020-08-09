package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputLayout;
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
	TextInputLayout editTIL;
	private String mAccountToEdit, mEditedAccount;
	boolean mIsNew, mIsError;
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
		editTIL=findViewById(R.id.ediTextTIL);
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
				if(!mIsError){goBackToPrevActivity();}
				break;
			case R.id.button_cancelDF:
				goBackToPrevActivity();
				break;
			case R.id.imageButton_delDF:
				deleteItem();
				goBackToPrevActivity();
		}


	}
private void goBackToPrevActivity(){
	Intent intent = new Intent(this, AccountRV.class);
	startActivity(intent);
}
	private void deleteItem() {
		maccountNameList.remove(mAccountToEdit);
		Preferences.setArrayPrefs("AccountNames", maccountNameList, this);
	}

	private void saveEditedName() {
		checkForError();
		if (!mIsError) {
			if (!mIsNew) {
				Collections.replaceAll(maccountNameList, mAccountToEdit, mEditedAccount);
			} else {
				maccountNameList.add(mEditedAccount);
			}
			Preferences.setArrayPrefs("AccountNames", maccountNameList, this);
		}
	}

	private void checkForError() {
		mIsError = TextUtils.isEmpty(mEditedAccount);
		editTIL.setError("Please enter an account name");
	}
}