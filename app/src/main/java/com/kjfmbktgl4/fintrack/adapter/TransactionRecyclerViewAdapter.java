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

import com.google.android.material.snackbar.Snackbar;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.model.TransactionItem;
import com.kjfmbktgl4.fintrack.ui.EditTransaction;
import com.kjfmbktgl4.fintrack.ui.NavDrawerLauncher;
//import com.kjfmbktgl4.fintrack.ui.TransactionRV;

import java.util.List;

public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransactionRecyclerViewAdapter.ViewHolder> {
	private Context context;
	private List<TransactionItem> transactionItemList;

	public TransactionRecyclerViewAdapter(Context context, List<TransactionItem> transactionItemList) {
		this.context = context;
		this.transactionItemList = transactionItemList;
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
		holder.transDate.setText(""+ transaction.getDateOfTransaction());
		holder.transAccount.setText(transaction.getAccountOfTransaction());

	}

	@Override
	public int getItemCount() {
		return transactionItemList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		public TextView categoryName, transNote, transAmount, transDate, transAccount;
		public ImageView catIcon, delIcon;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			itemView.setOnClickListener(this);

			categoryName = itemView.findViewById(R.id.catTV);
			transNote = itemView.findViewById(R.id.noteTV);
			transAmount = itemView.findViewById(R.id.amountTV);
			transDate = itemView.findViewById(R.id.dateTV);
			transAccount = itemView.findViewById(R.id.accountTV);
			catIcon = itemView.findViewById(R.id.catIV);
			delIcon = itemView.findViewById(R.id.delIV);


		}

		@Override
		public void onClick(View v) {

			int position = getAdapterPosition();
			TransactionItem transactionItem = transactionItemList.get(position);
			Snackbar.make(v, "Start Edit Activity", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
			Intent intent = new Intent(context, EditTransaction.class);

			intent.putExtra("name", transactionItem.getNameCategoryOfTransaction());
			context.startActivity(intent);


//            switch (v.getId()) {
//                case R.id.icon_button:
//                    Log.d("IconClicked", "onClick: " + contact.getPhoneNumber());
//                    break;
//            }


			// Log.d("Clicked", "onClick: " + contact.getName());

		}
	}
}
