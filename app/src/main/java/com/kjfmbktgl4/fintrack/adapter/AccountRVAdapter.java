package com.kjfmbktgl4.fintrack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.model.Accounts;

import java.util.List;

public class AccountRVAdapter extends RecyclerView.Adapter<AccountRVAdapter.ViewHolder> {
	List<Accounts> mAccountsList;
	public TextView maccountNameTV;
	public ImageView mdeleteIconIV;
	Context mContext;

	public AccountRVAdapter(List<Accounts> pAccountsList, Context pContext) {
		mAccountsList = pAccountsList;
		mContext = pContext;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simplelist_rv_row, parent, false);
		ViewHolder vh = new ViewHolder(view);
		return vh;
	
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Accounts account = mAccountsList.get(position);
		maccountNameTV.setText(account.getStringaccountName());

	}

	@Override
	public int getItemCount() {
		return mAccountsList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			maccountNameTV = itemView.findViewById(R.id.categoryNameTV);
			mdeleteIconIV = itemView.findViewById(R.id.deleteCategoryIV);
		}
	}
}
