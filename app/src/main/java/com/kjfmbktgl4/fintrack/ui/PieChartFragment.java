package com.kjfmbktgl4.fintrack.ui;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.CategoryTotals;
import com.kjfmbktgl4.fintrack.util.DateConverters;
import com.kjfmbktgl4.fintrack.util.Util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PieChartFragment extends Fragment implements View.OnClickListener {
	private ArrayList<CategoryTotals> dataArrayList;
	private ArrayList<CategoryTotals> transactionItemArrayListByCategory = new ArrayList<>();
	private String startDateString, endDateString;
	private TextInputEditText startDateET, endDateET;
	private Boolean setDate = false;
	private Boolean usePercent = true;
	private Switch switchButton;
	private Button allButton, yearButton, monthButton, refreshButton, todayButton;
	com.github.mikephil.charting.charts.PieChart pieChart;
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_pie_chart,container,false);
		pieChart = v.findViewById(R.id.piechart);
		startDateET = v.findViewById(R.id.selectStartDateForPie);
		endDateET = v.findViewById(R.id.selectEndDateForPie);
		allButton = v.findViewById(R.id.btn_all);
		yearButton = v.findViewById(R.id.btn_2018);
		monthButton = v.findViewById(R.id.btn_thisMonth);
		todayButton = v.findViewById(R.id.btn_today);
		refreshButton = v.findViewById(R.id.refreshGraphs);
		switchButton = v.findViewById(R.id.switch_percent);
		return v;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		allButton.setOnClickListener(this);
		yearButton.setOnClickListener(this);
		monthButton.setOnClickListener(this);
		todayButton.setOnClickListener(this);
		refreshButton.setOnClickListener(this);
		startDateET.setOnClickListener(this);
		endDateET.setOnClickListener(this);
		switchSetUp();

		//setUpToolbar();
		setStartAndEndDates();
		setDatePickers();
		dataArrayList = getDataForGraph();
		drawPiechart(dataArrayList);
	}

	private void switchSetUp() {
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
		DatePickerDialog picker = new DatePickerDialog(getContext(),
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


	}
	private void setStartAndEndDates() {
		if (startDateString == null || endDateString == null) {
			//Date endDate=Calendar.getInstance().getTime();
			endDateString = DateConverters.getcurrentDateInMilLs();
			startDateString = DateConverters.getFirstOfCurrentYearInMills();
			Log.d(Util.TAG, startDateString + " " + endDateString);

		}

	}

	private ArrayList<CategoryTotals> getDataForGraph() {
		DatabaseHandler db = new DatabaseHandler(getContext());
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

		if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
			int[] darkMode = getContext().getResources().getIntArray(R.array.ColorArrayDark);
			set.setColors(darkMode, 255);
		//	pieChart.setEntryLabelColor(Color.DKGRAY);
		//	data.setValueTextColor(Color.DKGRAY);
		}else{
			int[] lightMode = getContext().getResources().getIntArray(R.array.ColorArrayLight);
			set.setColors(lightMode, 255);
			//pieChart.setEntryLabelColor(Color.WHITE);
			//pieChart.setCenterTextColor(Color.DKGRAY);
		}
		TypedValue colorForText = new TypedValue();
		Resources.Theme theme = getContext().getTheme();
		theme.resolveAttribute(R.attr.colorSurface, colorForText, true);
		@ColorInt int colorText = colorForText.data;
		pieChart.setEntryLabelColor(colorText);
		data.setValueTextColor(colorText);
		pieChart.setHoleColor(colorText);

/*
		pieChart.setHoleColor(colorSurface);
		pieChart.setCenterTextColor(colorText);*/

		data.setValueTextSize(13f);
		data.setValueFormatter(new PercentFormatter(pieChart));
		pieChart.setUsePercentValues(usePercent);
		pieChart.getDescription().setEnabled(false);
		pieChart.setDrawHoleEnabled(true);
	/*	pieChart.setCenterText("Details By Category");
		pieChart.setCenterTextSize(18f);*/
		pieChart.getLegend().setEnabled(false);

		pieChart.animateY(500);
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
	private void setDatePickers() {
		String startDate = DateConverters.longStringToDateString(startDateString);
		startDateET.setText(startDate);
		String endDate = DateConverters.longStringToDateString(endDateString);
		endDateET.setText(endDate);
	}

	private void setUpToolbar() {
	//	((PieChart)getActivity()).setUpToolbar("Distribution Graph");
	}
}
