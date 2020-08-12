package com.kjfmbktgl4.fintrack.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.model.Accounts;
import com.kjfmbktgl4.fintrack.ui.AddEditAccount;

import java.util.List;

public class AccountRVAdapter extends RecyclerView.Adapter<AccountRVAdapter.ViewHolder> {
	List<Accounts> mAccountsList;
	public TextView maccountNameTV;
	public ImageView mdeleteIconIV;
	Context mContext;
	String mtypeOfData;

	public AccountRVAdapter(List<Accounts> pAccountsList, Context pContext, String ptypeOfData) {
		mAccountsList = pAccountsList;
		mContext = pContext;
		mtypeOfData=ptypeOfData;


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

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			maccountNameTV = itemView.findViewById(R.id.categoryNameTV);
			/*mdeleteIconIV = itemView.findViewById(R.id.deleteCategoryIV);*/
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View pView) {
			Accounts accountClicked = mAccountsList.get(getAdapterPosition());
			String AccountNameToEdit = accountClicked.getStringaccountName();
			Intent intent = new Intent(mContext, AddEditAccount.class);
			intent.putExtra("Type",mtypeOfData);
			intent.putExtra("AccountName", AccountNameToEdit);


			mContext.startActivity(intent);
		}
	}
}
