package com.kjfmbktgl4.fintrack.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.adapter.AccountRVAdapter;
import com.kjfmbktgl4.fintrack.model.Accounts;
import com.kjfmbktgl4.fintrack.util.Preferences;

import java.util.ArrayList;
import java.util.List;

public class CategoryRVFragment extends Fragment implements View.OnClickListener {
	Context mContext;
	public List<Accounts> mAccountName;
	private AccountRVAdapter mRecyclerviewAdapter;
	FloatingActionButton fab;
	RecyclerView recyclerView;


	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
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
		((CategoryRV)getActivity()).setUpToolbar();
	}

/*	@Override
	public void onBackPressed(){
		NavUtils.navigateUpFromSameTask(this);
	}*/

	private void setUpRecyclerView() {
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
		mRecyclerviewAdapter = new AccountRVAdapter(mAccountName,mContext,"Category");
		recyclerView.setAdapter(mRecyclerviewAdapter);

		DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
		recyclerView.addItemDecoration(mDividerItemDecoration);
	}

	private void getAllData() {
		List<String> accountNameString;
		mAccountName = new ArrayList<Accounts>();
		accountNameString = Preferences.getArrayPrefs("CategoryNames",mContext);
		for (String accountNameItem : accountNameString){
			Accounts account = new Accounts();
			account.setStringaccountName(accountNameItem);
			mAccountName.add(account);
		}
	}

	@Override
	public void onClick(View pView) {
		Intent intent = new Intent(getActivity(), AddEditAccount.class);
		intent.putExtra("Type", "Category");
		switch (pView.getId()) {
			case R.id.fab:
				intent.putExtra("isNew", true);
				break;
		}
		startActivity(intent);
	}
}