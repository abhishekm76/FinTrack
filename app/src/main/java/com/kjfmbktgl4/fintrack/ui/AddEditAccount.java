package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;

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
import com.kjfmbktgl4.fintrack.model.Accounts;
import com.kjfmbktgl4.fintrack.viewmodel.AccountsViewModel;

import java.util.ArrayList;

public class AddEditAccount extends AppCompatActivity implements View.OnClickListener {
	Button save, cancel;
	ImageButton deleteButton;
	Accounts accountSelected, accountEdited;
	EditText mAccountNameEditText;
	TextInputLayout editTIL;
	private String mStringAccountToEdit, mStringEditedAccountName;
	boolean mIsNew, mIsError;
	private ArrayList<Accounts> maccountNameList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_simplelist);
		save = findViewById(R.id.button_SaveDF);
		cancel = findViewById(R.id.button_cancelDF);
		deleteButton = findViewById(R.id.imageButton_delDF);
		mAccountNameEditText = findViewById(R.id.editTextDF);
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		//maccountNameList = Preferences.getArrayPrefs("AccountNames", this);
		mStringAccountToEdit = getIntent().getStringExtra("AccountName");
		mIsNew = getIntent().getBooleanExtra("isNew", false);
		editTIL = findViewById(R.id.ediTextTIL);



		final AccountsViewModel ViewModel = new ViewModelProvider(this).get(AccountsViewModel.class);
		ArrayList<Accounts> maccountNameList = ViewModel.getmAccountName();

		setUpViewModel();
		createAccount();
		setInitialValues();
		setUpToolbar();

	}

	private void createAccount() {
		accountSelected= new Accounts();
		if (!mIsNew) {
			accountEdited=new Accounts();
		}
	}

	private void setUpViewModel() {

	}
	private void setInitialValues() {
		if (!mIsNew) {
			accountSelected.setStringaccountName(mStringAccountToEdit);
			mAccountNameEditText.setText(mStringAccountToEdit);
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
				mStringEditedAccountName = mAccountNameEditText.getText().toString();
				accountEdited.setStringaccountName(mStringEditedAccountName);
				saveEditedName1();
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


	}

	private void goBackToPrevActivity() {
		Intent intent = new Intent(this, AccountRV.class);
		startActivity(intent);
	}

	private void deleteItem() {
		maccountNameList.remove(mStringAccountToEdit);
		//this will need to be uneditedPreferences.setArrayPrefs("AccountNames", maccountNameList, this);
	}
	private void setUpToolbar() {
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("FinTrack");
		getSupportActionBar().setSubtitle("Account Details");

	}
	private void saveEditedName1() {

		checkForError();
		if (!mIsError) {
			if (!mIsNew) {
				int index = maccountNameList.indexOf(accountSelected);
				maccountNameList.set(index, accountEdited);
				//Collections.replaceAll(maccountNameList, mAccountToEdit, mEditedAccount);
			} else {
				maccountNameList.add(accountSelected);
			}
			//this will need to be uncommentedPreferences.setArrayPrefs("AccountNames", maccountNameList, this);
		}else	{
			editTIL.setError("Please enter an account name");
		}
	}

	private void checkForError() {
		mIsError = TextUtils.isEmpty(mStringEditedAccountName) || TextUtils.isEmpty(mStringEditedAccountName.trim());


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
				/*finish();*/
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
}