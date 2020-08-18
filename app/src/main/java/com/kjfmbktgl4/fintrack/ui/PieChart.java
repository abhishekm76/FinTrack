/*
package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.CategoryTotals;
import com.kjfmbktgl4.fintrack.util.DateConverters;
import com.kjfmbktgl4.fintrack.util.Util;

import java.text.DateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PieChart extends AppCompatActivity */
/*implements View.OnClickListener *//*
{
	*/
/*private ArrayList<CategoryTotals> dataArrayList;
	private ArrayList<CategoryTotals> transactionItemArrayListByCategory = new ArrayList<>();
	private String startDateString, endDateString;
	private TextInputEditText startDateET, endDateET;
	private Boolean setDate = false;
	private Boolean usePercent = true;
	private Switch switchButton;
	private Button allButton, yearButton, monthButton, refreshButton, todayButton;
	com.github.mikephil.charting.charts.PieChart pieChart;*//*


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pie_chart);

		if (savedInstanceState == null) {
			PieChartFragment fragment = new PieChartFragment();
			*/
/*fragment.setArguments(getIntent().getExtras());*//*

			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.container_pieChart, fragment)
					.commit();
		}

	}
	*/
/*	pieChart = findViewById(R.id.piechart);
		startDateET = findViewById(R.id.selectStartDateForPie);
		endDateET = findViewById(R.id.selectEndDateForPie);
		allButton = findViewById(R.id.btn_all);
		yearButton = findViewById(R.id.btn_2018);
		monthButton = findViewById(R.id.btn_thisMonth);
		todayButton = findViewById(R.id.btn_today);
		refreshButton = findViewById(R.id.refreshGraphs);
		switchButton = findViewById(R.id.switch_percent);
		allButton.setOnClickListener(this);
		yearButton.setOnClickListener(this);
		monthButton.setOnClickListener(this);
		todayButton.setOnClickListener(this);
		refreshButton.setOnClickListener(this);
		startDateET.setOnClickListener(this);
		endDateET.setOnClickListener(this);


		setUpToolbar();
		setStartAndEndDates();
		setDatePickers();
		dataArrayList = getDataForGraph();
		drawPiechart(dataArrayList);

		switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// The toggle is enabled
					usePercent = false;
				} else {
					// The toggle is disabled
					usePercent = true;
				}
				drawCustom();
			}
		});
	}

	private void setDatePickers() {
		String startDate = DateConverters.longStringToDateString(startDateString);
		startDateET.setText(startDate);
		String endDate = DateConverters.longStringToDateString(endDateString);
		endDateET.setText(endDate);
	}*//*


	public void setUpToolbar(String pSubtitle) {
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("FinTrack");
		getSupportActionBar().setSubtitle(pSubtitle);

	}

	*/
/*private void setStartAndEndDates() {
		if (startDateString == null || endDateString == null) {
			//Date endDate=Calendar.getInstance().getTime();
			endDateString = DateConverters.getcurrentDateInMilLs();
			startDateString = DateConverters.getFirstOfCurrentYearInMills();
			Log.d(Util.TAG, startDateString + " " + endDateString);

		}

	}

	private ArrayList<CategoryTotals> getDataForGraph() {
		DatabaseHandler db = new DatabaseHandler(PieChart.this);
		transactionItemArrayListByCategory = (ArrayList<CategoryTotals>) db.getTransactionsByCategoryByPeriod(startDateString, endDateString);
		return transactionItemArrayListByCategory;
	}

	public void drawPiechart(ArrayList<CategoryTotals> yValues) {

		List<PieEntry> entries = new ArrayList<>();
		for (CategoryTotals categoryTotals : yValues) {
			Long amount = categoryTotals.getTotalAmountOfTransaction();
			String category = categoryTotals.getNameCategoryOfTransaction();
			entries.add(new PieEntry(amount, category));
		}
		PieDataSet set = new PieDataSet(entries, "Spend by category");
		PieData data = new PieData(set);
		pieChart.setData(data);
		set.setColors(Util.colorArray);

		data.setValueTextSize(13f);

		data.setValueTextColor(Color.WHITE);

		data.setValueFormatter(new PercentFormatter(pieChart));
		pieChart.setUsePercentValues(usePercent);

		pieChart.getDescription().setEnabled(false);
		pieChart.setDrawHoleEnabled(true);
		pieChart.setCenterText("Details By Category");
		pieChart.setCenterTextSize(18f);
		pieChart.getLegend().setEnabled(false);

		pieChart.animateY(1500);
		pieChart.invalidate();
	}

	public void drawAllData() {
		// set start and end dates to cover all data
		startDateString = DateConverters.getEpochStart();
		endDateString = DateConverters.getcurrentDateInMilLs();
		Log.d(Util.TAG, "all " + startDateString + " " + endDateString);

	}

	public void drawThisYear() {
		// set start and end dates to cover this year
		startDateString = DateConverters.getFirstOfCurrentYearInMills();
		endDateString = DateConverters.getcurrentDateInMilLs();
		Log.d(Util.TAG, "all " + startDateString + " " + endDateString);

	}

	public void drawThisMonth() {
		startDateString = DateConverters.getFirstOfMonth();
		endDateString = DateConverters.getcurrentDateInMilLs();
		Log.d(Util.TAG, "month " + startDateString + " " + endDateString);

		// set start and end dates to cover this month
	}

	public void drawToday() {
		startDateString = DateConverters.getFirstOfWeek();
		endDateString = DateConverters.getcurrentDateInMilLs();
		Log.d(Util.TAG, "month " + startDateString + " " + endDateString);


		// set start and end dates to cover today
	}

	public void drawCustom() {
		// set start and end dates to cover custom period


		startDateString = DateConverters.dateStringToLongString(startDateET.getText().toString());
		endDateString = DateConverters.dateStringToLongString(endDateET.getText().toString());

		if (DateConverters.isStartBeforeEnd(startDateString, endDateString)) {
			dataArrayList = getDataForGraph();
			drawPiechart(dataArrayList);
		}
		else{
		Snackbar.make(startDateET, "Please select a start date that is before the end date" , Snackbar.LENGTH_LONG).show();
		Log.d(Util.TAG, "custom " + startDateString + " " + endDateString);}
	}
	@Override
	public void onBackPressed(){
		NavUtils.navigateUpFromSameTask(this);
	}

	@Override
	public void onClick(View pView) {
		switch (pView.getId()) {
			case R.id.btn_all:
				drawAllData();
				setDate = false;
				break;
			case R.id.btn_2018:
				drawThisYear();
				setDate = false;
				break;
			case R.id.btn_thisMonth:
				drawThisMonth();
				setDate = false;
				break;
			case R.id.btn_today:
				drawToday();
				setDate = false;
				break;
			case R.id.refreshGraphs:
				drawCustom();
				break;

			case R.id.selectStartDateForPie:
			case R.id.selectEndDateForPie:
				pickerShow(pView);
				setDate = true;
				break;
		}

		if (!setDate) {
			setDatePickers();
			dataArrayList = getDataForGraph();
			drawPiechart(dataArrayList);
		}


	}

	public void pickerShow(final View pView) {
		final Calendar cldr = Calendar.getInstance();
		int day = cldr.get(Calendar.DAY_OF_MONTH);
		int month = cldr.get(Calendar.MONTH);
		int year = cldr.get(Calendar.YEAR);
		// date picker dialog
		DatePickerDialog picker = new DatePickerDialog(PieChart.this,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						cldr.set(year, monthOfYear, dayOfMonth);
						String setDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(cldr.getTime());
						EditText setDateET = (EditText) pView;
						setDateET.setText(setDate);

					}
				}, year, month, day);
		picker.show();


	}*//*


}*/
