package com.kjfmbktgl4.fintrack.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.model.TransactionItem;
//import com.kjfmbktgl4.fintrack.ui.EditTransaction;
import com.kjfmbktgl4.fintrack.ui.MainFragmentDirections;
import com.kjfmbktgl4.fintrack.util.DateConverters;
import com.kjfmbktgl4.fintrack.util.Util;
//import com.kjfmbktgl4.fintrack.ui.TransactionRV;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransactionRecyclerViewAdapter.ViewHolder> implements Filterable {
	private Context context;
	private List<TransactionItem> transactionItemList;
	private List<TransactionItem> mtransactionItemListAll;
	private NavController mNavController;

	public TransactionRecyclerViewAdapter(Context context, List<TransactionItem> transactionItemList, NavController pNavController) {
		this.context = context;
		this.transactionItemList = transactionItemList;

		mtransactionItemListAll = new ArrayList<>();
		for(TransactionItem item:transactionItemList){
			//mtransactionItemListAll.add(item.clone());
		}

		//mtransactionItemListAll.addAll(transactionItemList);


		//mtransactionItemListAll=transactionItemList;
		this.mNavController=pNavController;
	}

	@NonNull
	@Override
	public TransactionRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_rv_row, parent, false);
		ViewHolder vh = new ViewHolder(view);
		return vh;
	}

	@Override
	public void onBindViewHolder(@NonNull TransactionRecyclerViewAdapter.ViewHolder holder, int position) {
		TransactionItem transaction = transactionItemList.get(position);
		holder.categoryName.setText(transaction.getNameCategoryOfTransaction());
		holder.transNote.setText(transaction.getNoteOfTransaction());
		holder.transAmount.setText(""+transaction.getAmountOfTransaction());



		Date transactionDate = new Date(transaction.getDateOfTransaction());
		String tranDate = DateFormat.getDateInstance(DateFormat.FULL).format(transactionDate);
		int weekday = transactionDate.getDay();
		holder.transDate.setText(tranDate);
		holder.transAccount.setText(transaction.getAccountOfTransaction());

		//coloru formats
		Log.d(Util.TAG,"postion "+ position);
		    int color= Util.getColorCode(weekday);
		/*	holder.categoryName.setTextColor(color);
			holder.transAmount.setTextColor(color);*/
			holder.barVertical.setBackgroundColor(color);



	}

	@Override
	public int getItemCount() {
		return transactionItemList.size();
	}

	@Override
	public Filter getFilter() {

		return filter;
	}

	Filter filter = new Filter() {



		@Override
		protected FilterResults performFiltering(CharSequence pCharSequence) {
			List<TransactionItem> filteredList = new ArrayList<>();
			if(pCharSequence.toString().isEmpty()){
				filteredList.addAll(mtransactionItemListAll);

			}else{
				for(TransactionItem item:transactionItemList){
					String category = item.getNameCategoryOfTransaction().toString().toLowerCase();
					String note = item.getNoteOfTransaction().toString().toLowerCase();
					String accoount = item.getAccountOfTransaction().toString().toLowerCase();
					if(category.contains(pCharSequence.toString().toLowerCase())|note.contains(pCharSequence.toString().toLowerCase())|accoount.contains(pCharSequence.toString().toLowerCase())){
						filteredList.add(item);
					}
				}
			}
			FilterResults lFilterResults = new FilterResults();
			lFilterResults.values=filteredList;

			return lFilterResults;
		}

		@Override
		protected void publishResults(CharSequence pCharSequence, FilterResults pFilterResults) {
			transactionItemList.clear();
			transactionItemList.addAll((Collection<? extends TransactionItem>) pFilterResults.values);
			notifyDataSetChanged();
		}
	};


	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		public TextView categoryName, transNote, transAmount, transDate, transAccount;
		public ImageView catIcon, delIcon;
		public View barVertical;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			itemView.setOnClickListener(this);

			categoryName = itemView.findViewById(R.id.catTV);
			transNote = itemView.findViewById(R.id.noteTV);
			transAmount = itemView.findViewById(R.id.amountTV);
			transDate = itemView.findViewById(R.id.dateTV);
			transAccount = itemView.findViewById(R.id.accountTV);
			barVertical= itemView.findViewById(R.id.barVertical);



		}

		@Override
		public void onClick(View v) {

			int position = getAdapterPosition();
			TransactionItem transactionItem = transactionItemList.get(position);

			MainFragmentDirections.ActionMainFragmentToEditTransactionFragment action = MainFragmentDirections.actionMainFragmentToEditTransactionFragment();
			action.setIsNew(false);
			action.setIdTransaction(transactionItem.getId());
			mNavController.navigate(action);

/*
			Intent intent = new Intent(context, EditTransaction.class);

			intent.putExtra("id", transactionItem.getId());
			context.startActivity(intent);
*/

		}
	}
}
