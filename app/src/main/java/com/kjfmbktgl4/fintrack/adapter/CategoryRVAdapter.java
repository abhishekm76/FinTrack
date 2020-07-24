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

	public class ViewHolder extends RecyclerView.ViewHolder {

		public TextView categoryName;


		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			categoryName = itemView.findViewById(R.id.catTV);
		}
	}
}
