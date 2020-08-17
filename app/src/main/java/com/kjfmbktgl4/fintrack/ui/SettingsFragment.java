package com.kjfmbktgl4.fintrack.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.TransactionItem;
import com.kjfmbktgl4.fintrack.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
	public List<TransactionItem> transactionItemArrayList;
	public List<TransactionItem> exportArrayList;

	NavController mNavController;
	ImageButton Category, Account, Export, DeleteAll;
	DatabaseHandler db;
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	public SettingsFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment SettingsFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static SettingsFragment newInstance(String param1, String param2) {
		SettingsFragment fragment = new SettingsFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		mNavController = NavHostFragment.findNavController(this);


		View v = inflater.inflate(R.layout.fragment_settings, container, false);
		Category = v.findViewById(R.id.imageButtonCategory);
		Account = v.findViewById(R.id.imageButtonAccount);
		Export = v.findViewById(R.id.imageButtonExport);
		DeleteAll = v.findViewById(R.id.imageButtonDeleteAll);

		return v;

	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Category.setOnClickListener(this);
		Account.setOnClickListener(this);
		Export.setOnClickListener(this);
		DeleteAll.setOnClickListener(this);
		db = new DatabaseHandler(getActivity());

	}

	@Override
	public void onClick(View pView) {
		switch (pView.getId()) {
			case R.id.imageButtonExport:
				new SettingsFragment.AsyncWriteExport().execute();
				break;
			case R.id.imageButtonDeleteAll:
				deleteAlertDialog();
				break;
			case R.id.imageButtonCategory:
				goToCategoryList();
				break;
			case R.id.imageButtonAccount:
				goToAccountList();
				break;
		}
	}

	private void goToCategoryList() {
		SettingsFragmentDirections.ActionSettingsFragmentToAccountRVFragment action = SettingsFragmentDirections.actionSettingsFragmentToAccountRVFragment();
		action.setMType("Category");
		mNavController.navigate(action);

	}

	private void goToAccountList() {
		SettingsFragmentDirections.ActionSettingsFragmentToAccountRVFragment action = SettingsFragmentDirections.actionSettingsFragmentToAccountRVFragment();
		action.setMType("Account");
		mNavController.navigate(action);

	}

	private void deleteAlertDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
		alertDialogBuilder.setMessage("This will delete all data, are you sure you want to delete all the data?");
		alertDialogBuilder.setPositiveButton("yes",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						new SettingsFragment.AsyncDeleteAll().execute();
					}
				});

		alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				/*finish();*/
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private class AsyncDeleteAll extends AsyncTask<Void, Void, Void> {


		@Override
		protected Void doInBackground(Void... voids) {
			db.deleteAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {

		}
	}


	private class AsyncWriteExport extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... voids) {
			exportData();
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			Log.d(Util.TAG, "data exported");
		}
	}

	private void exportData() {

		exportArrayList = new ArrayList<>();
		getAsyncData();



	}

	private void writeExportData(){
		StringBuilder data = new StringBuilder();
		data.append("Index,Weekday,Day,Year,Amount,Account,Category,Note");
		for (int i = 0; i < exportArrayList.size(); i++) {
			Date transactionDate = new Date(exportArrayList.get(i).getDateOfTransaction());
			String tranDate = DateFormat.getDateInstance(DateFormat.FULL).format(transactionDate);

			data.append("\n" + String.valueOf(i) + "," + String.valueOf(tranDate));
			data.append("," + String.valueOf(exportArrayList.get(i).getAmountOfTransaction()));
			data.append("," + String.valueOf(exportArrayList.get(i).getAccountOfTransaction()));
			data.append("," + String.valueOf(exportArrayList.get(i).getNameCategoryOfTransaction()));
			data.append("," + String.valueOf(exportArrayList.get(i).getNoteOfTransaction()));

		}


		try {
			//saving the file into device
			FileOutputStream out = getActivity().openFileOutput("data.csv", Context.MODE_PRIVATE);
			out.write((data.toString()).getBytes());
			out.close();

			//exporting
			Context context = getActivity().getApplicationContext();
			File filelocation = new File(getActivity().getFilesDir(), "data.csv");
			Uri path = FileProvider.getUriForFile(context, "com.kjfmbktgl4.fintrack.fileprovider", filelocation);
			Intent fileIntent = new Intent(Intent.ACTION_SEND);
			fileIntent.setType("text/csv");
			fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
			fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			fileIntent.putExtra(Intent.EXTRA_STREAM, path);
			startActivity(Intent.createChooser(fileIntent, "Exported data"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getAsyncData() {
		transactionItemArrayList = new ArrayList<>();
		SettingsFragment.AsyncDataFetch asyncDataFetch = new SettingsFragment.AsyncDataFetch();
		asyncDataFetch.execute();
	}

	private class AsyncDataFetch extends AsyncTask<Void, Void, List<TransactionItem>> {


		@Override
		protected List<TransactionItem> doInBackground(Void... voids) {
			List<TransactionItem> listAsync = new ArrayList<>();
			listAsync = db.getAllTransactions();
			return listAsync;
		}

		@Override
		protected void onPostExecute(List<TransactionItem> transactionItems) {
			transactionItemArrayList.addAll(transactionItems);
			exportArrayList.addAll(transactionItemArrayList);
			writeExportData();
		}
	}


}