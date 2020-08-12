package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.DialogInterface;
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

public class AddEditAccount extends AppCompatActivity /*implements View.OnClickListener*/ {
	/*Button save, cancel;
	ImageButton deleteButton;
	EditText accountName;
	TextInputLayout editTIL;
	private String mAccountToEdit, mEditedAccount;
	boolean mIsNew, mIsError;
	private List<String> maccountNameList;*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_simplelist);

		if (savedInstanceState == null) {
			AddEditAccountFragment fragment = new AddEditAccountFragment();
			fragment.setArguments(getIntent().getExtras());

			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.container_fragment_addeditaccount, fragment)
					.commit();
		}


		/*save = findViewById(R.id.button_SaveDF);
		cancel = findViewById(R.id.button_cancelDF);
		deleteButton = findViewById(R.id.imageButton_delDF);
		accountName = findViewById(R.id.editTextDF);
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		maccountNameList = Preferences.getArrayPrefs("AccountNames", this);
		mAccountToEdit = getIntent().getStringExtra("AccountName");
		mIsNew = getIntent().getBooleanExtra("isNew", false);
		editTIL = findViewById(R.id.ediTextTIL);
		setUpToolbar();
		setInitialValues();*/
	}

	/*private void setInitialValues() {
		if (!mIsNew) {
			accountName.setText(mAccountToEdit);
		}
	}
	@Override
	public void onBackPressed(){
		NavUtils.navigateUpFromSameTask(this);
	}

	@Override
	public void onClick(View pView) {
		switch (pView.getId()) {
			case R.id.button_SaveDF:
				mEditedAccount = accountName.getText().toString();
				saveEditedName();
				if (!mIsError) {
					goBackToPrevActivity();
				}
				break;
			case R.id.button_cancelDF:
				goBackToPrevActivity();
				break;
			case R.id.imageButton_delDF:
				deleteAlertDialog();
				break;
		}


	}*/

	public void goBackToPrevActivity() {
		Intent intent = new Intent(this, AccountRV.class);
		startActivity(intent);
	}
	public void setUpToolbar() {
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("FinTrack");
		getSupportActionBar().setSubtitle("Account Details");

	}

	/*private void deleteItem() {
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
		}else	{
			editTIL.setError("Please enter an account name");
		}
	}

	private void checkForError() {
		mIsError = TextUtils.isEmpty(mEditedAccount) || TextUtils.isEmpty(mEditedAccount.trim());


	}
	private void deleteAlertDialog(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("Are you sure you want to delete this entry?");
		alertDialogBuilder.setPositiveButton("yes",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						deleteItem();
						goBackToPrevActivity();
					}
				});

		alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				*//*finish();*//*
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}*/
}