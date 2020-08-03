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
import com.kjfmbktgl4.fintrack.model.Category;
import com.kjfmbktgl4.fintrack.ui.AddEditAccount;
import com.kjfmbktgl4.fintrack.ui.AddEditCategory;

import java.util.List;

public class CategoryRVAdapter2 extends RecyclerView.Adapter<CategoryRVAdapter2.ViewHolder> {
	List<Category> mCategoryList;
	Context mContext;
	TextView mCategoryName;
	ImageView mdeleteIV;

	public CategoryRVAdapter2(List<Category> pCategoryList, Context pContext) {
		mCategoryList = pCategoryList;
		mContext = pContext;

	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simplelist_rv_row, parent, false);
		CategoryRVAdapter2.ViewHolder vh = new CategoryRVAdapter2.ViewHolder(view);
		return vh;

	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Category category = mCategoryList.get(position);
		mCategoryName.setText(category.getMcategoryName());

	}

	@Override
	public int getItemCount() {
		return mCategoryList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			mCategoryName = itemView.findViewById(R.id.categoryNameTV);
			mdeleteIV = itemView.findViewById(R.id.deleteCategoryIV);
			itemView.setOnClickListener(this);

		}

		@Override
		public void onClick(View pView) {
			Category categoryClicked = mCategoryList.get(getAdapterPosition());
			String categoryNameToEdit = categoryClicked.getMcategoryName();
			Intent intent = new Intent(mContext, AddEditCategory.class);
			intent.putExtra("CategoryName", categoryNameToEdit);
			mContext.startActivity(intent);
		}
	}
}
