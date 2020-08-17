package com.kjfmbktgl4.fintrack.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.adapter.AccountRVAdapter;
import com.kjfmbktgl4.fintrack.model.Accounts;
import com.kjfmbktgl4.fintrack.util.Preferences;

import java.util.ArrayList;
import java.util.List;

public class AccountRVFragment extends Fragment implements View.OnClickListener {
	Context mContext;
	public List<Accounts> mAccountName;
	private AccountRVAdapter mRecyclerviewAdapter;
	FloatingActionButton fab;
	RecyclerView recyclerView;
	String mType;


	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {

		Bundle bundle = getArguments();
		mType = bundle.getString("Type");

		View v =  inflater.inflate(R.layout.account_rv_fragment,container,false);
		fab=v.findViewById(R.id.fab);
		recyclerView = v.findViewById(R.id.cat_recyclerview);
		return v;
	}

	@Override
	public void onViewCreated(@NonNull View view,
	                          @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mContext=getContext();
		fab.setOnClickListener(this);
		setUpToolbar();
		getAllData();
		setUpRecyclerView();
	}

	@Override
	public void onResume() {
		mRecyclerviewAdapter.notifyDataSetChanged();
		super.onResume();
	}

	private void setUpToolbar() {
		//((AccountRV)getActivity()).setUpToolbar();
	}

/*	@Override
	public void onBackPressed(){
		NavUtils.navigateUpFromSameTask(this);
	}*/

	private void setUpRecyclerView() {
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
		mRecyclerviewAdapter = new AccountRVAdapter(mAccountName,mContext,mType);
		recyclerView.setAdapter(mRecyclerviewAdapter);

		DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
		recyclerView.addItemDecoration(mDividerItemDecoration);
	}

	private void getAllData() {
		List<String> accountNameString;
		mAccountName = new ArrayList<Accounts>();

		if(mType.equals("Account")) {
			accountNameString = Preferences.getArrayPrefs("AccountNames", mContext);
		} else {
			accountNameString = Preferences.getArrayPrefs("CategoryNames", mContext);
		}

		for (String accountNameItem : accountNameString){
			Accounts account = new Accounts();
			account.setStringaccountName(accountNameItem);
			mAccountName.add(account);
		}
	}

	@Override
	public void onClick(View pView) {
		Intent intent = new Intent(getContext(), AddEditAccount.class);
		intent.putExtra("Type", mType);
		switch (pView.getId()) {
			case R.id.fab:
				intent.putExtra("isNew", true);
				break;
		}
		startActivity(intent);
	}

}
