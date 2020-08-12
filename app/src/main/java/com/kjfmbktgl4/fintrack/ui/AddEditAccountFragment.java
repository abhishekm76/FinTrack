package com.kjfmbktgl4.fintrack.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.util.Preferences;

import java.util.Collections;
import java.util.List;

public class AddEditAccountFragment extends Fragment implements View.OnClickListener {
	Button save, cancel;
	ImageButton deleteButton;
	EditText accountName;
	TextInputLayout editTIL;
	private String mAccountToEdit, mEditedAccount;
	boolean mIsNew, mIsError;
	private List<String> maccountNameList;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {

		Bundle bundle = getArguments();
		mAccountToEdit = bundle.getString("AccountName");
		mIsNew = bundle.getBoolean("isNew", false);


		View v = inflater.inflate(R.layout.add_edit_simple_fragment,container,false);
		save = v.findViewById(R.id.button_SaveDF);
		cancel = v.findViewById(R.id.button_cancelDF);
		deleteButton = v.findViewById(R.id.imageButton_delDF);
		accountName = v.findViewById(R.id.editTextDF);
		editTIL = v.findViewById(R.id.ediTextTIL);



		return v;
		/*return super.onCreateView(inflater, container, savedInstanceState);*/
	}

	@Override
	public void onViewCreated(@NonNull View view,
	                          @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		maccountNameList = Preferences.getArrayPrefs("AccountNames", getContext());
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		setUpToolbar();
		setInitialValues();


	}

	private void setInitialValues() {
		if (!mIsNew) {
			accountName.setText(mAccountToEdit);
		}
	}
	/*@Override
	public void onBackPressed(){
		NavUtils.navigateUpFromSameTask(this);
	}
*/
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


	}

	private void goBackToPrevActivity() {
		/*Intent intent = new Intent(this, AccountRV.class);
		startActivity(intent);*/
		((AddEditAccount)getActivity()).goBackToPrevActivity();

	}

	private void deleteItem() {
		maccountNameList.remove(mAccountToEdit);
		Preferences.setArrayPrefs("AccountNames", maccountNameList, getContext());
	}
	private void setUpToolbar() {
		((AddEditAccount)getActivity()).setUpToolbar();

	}
	private void saveEditedName() {
		checkForError();
		if (!mIsError) {
			if (!mIsNew) {
				Collections.replaceAll(maccountNameList, mAccountToEdit, mEditedAccount);
			} else {
				maccountNameList.add(mEditedAccount);
			}
			Preferences.setArrayPrefs("AccountNames", maccountNameList, getContext());
		}else	{
			editTIL.setError("Please enter an account name");
		}
	}

	private void checkForError() {
		mIsError = TextUtils.isEmpty(mEditedAccount) || TextUtils.isEmpty(mEditedAccount.trim());


	}
	private void deleteAlertDialog(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
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
