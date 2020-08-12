package com.kjfmbktgl4.fintrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.kjfmbktgl4.fintrack.model.Accounts;
import com.kjfmbktgl4.fintrack.util.Preferences;

import java.util.ArrayList;
import java.util.List;

public class AccountsViewModel extends AndroidViewModel {
	ArrayList<Accounts> mAccountName;

	public AccountsViewModel(@NonNull Application application) {
		super(application);
	}


	public ArrayList<Accounts> getmAccountName() {
		List<String> accountNameString;
		mAccountName = new ArrayList<Accounts>();
		accountNameString = Preferences.getArrayPrefs("AccountNames",getApplication());
		for (String accountNameItem : accountNameString) {
			Accounts account = new Accounts();
			account.setStringaccountName(accountNameItem);
			mAccountName.add(account);
		}
		return mAccountName;
	}
}
