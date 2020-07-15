package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.kjfmbktgl4.fintrack.R;

import java.util.Currency;
import java.util.Locale;

public class EditTransaction extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_transaction);
		Locale curLocale = Locale.getDefault();
	Currency curr =Currency.getInstance(curLocale);
	String symbol=curr.getSymbol();
	View v =findViewById(R.id.currTV);


		Snackbar.make(v, "Currnecy is  "+ symbol, Snackbar.LENGTH_LONG)
				.setAction("Action", null).show();

	}
}