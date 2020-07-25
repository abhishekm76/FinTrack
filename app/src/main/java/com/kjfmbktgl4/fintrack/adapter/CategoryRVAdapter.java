package com.kjfmbktgl4.fintrack.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.kjfmbktgl4.fintrack.R;


import com.kjfmbktgl4.fintrack.ui.EditCategory;
import com.kjfmbktgl4.fintrack.util.Preferences;
import com.kjfmbktgl4.fintrack.util.Util;


import java.util.List;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {
	private Context context;
	private List<String> categoryList;

	public CategoryRVAdapter(Context context, List<String> categoryList) {
		this.context = context;
		this.categoryList = categoryList;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_rv_row, parent, false);
		ViewHolder vh = new ViewHolder(view);
		return vh;
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		String category = categoryList.get(position);
		holder.categoryName.setText(category);
	}

	@Override
	public int getItemCount() {
		return categoryList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		boolean confirm;
		public TextView categoryName;
		public ImageView deleteIcon;


		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			Context context;
			categoryName = itemView.findViewById(R.id.catTV);
			deleteIcon = itemView.findViewById(R.id.delIV);
			categoryName.setOnClickListener(this);
			deleteIcon.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.delIV: //item clicked
					confirmDel(String.valueOf(categoryName.getText()));
					break;
				case R.id.catTV: //item clicked
					Intent intent = new Intent(context, EditCategory.class);
					intent.putExtra("editcategory",categoryName.getText());
					context.startActivity(intent);
					break;
			}
		}

		private void deleteItem(String s) {

			categoryList.remove(s);
			Preferences.setArrayPrefs("CategoryNames", categoryList, context);
			notifyItemRemoved(getAdapterPosition());

		}

		private void confirmDel(String s) {
			final String delString = s;
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			alertDialogBuilder.setTitle("Confirm Delete Category");
			alertDialogBuilder.setMessage("This will delete the category for new entries. Old entries tagged to this category will continue to be visible. To change old entries click on the from the home screen and select a new category ");
			alertDialogBuilder.setPositiveButton("yes",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							deleteItem(delString);
						}
					});

			alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

					//finish();
				}
			});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();


		}
	}
}
