package com.kjfmbktgl4.fintrack.ui;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.PeriodTotal;
import com.kjfmbktgl4.fintrack.util.DateConverters;
import com.kjfmbktgl4.fintrack.util.Util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BarChartFragment extends Fragment implements View.OnClickListener {
	com.github.mikephil.charting.charts.BarChart barChart;
	private List<PeriodTotal> dataList;
	private String startDateString, endDateString;
	private TextInputEditText startDateET, endDateET;
	private Boolean setDate = false;
	private Button allButton, yearButton, monthButton, refreshButton;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_bar_chart,container,false);
		barChart = v.findViewById(R.id.barChart);
		startDateET = v.findViewById(R.id.selectStartDateForBar);
		endDateET = v.findViewById(R.id.selectEndDateForBar);
		allButton = v.findViewById(R.id.btn_all);
		yearButton = v.findViewById(R.id.btn_2018);
		monthButton = v.findViewById(R.id.btn_thisMonth);
		refreshButton = v.findViewById(R.id.refreshGraphs);


		return v;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

		startDateET.setOnClickListener(this);
		endDateET.setOnClickListener(this);
		allButton.setOnClickListener(this);
		yearButton.setOnClickListener(this);
		monthButton.setOnClickListener(this);
		refreshButton.setOnClickListener(this);

		setUpToolbar();
		drawThisYear();
		setDatePickers();
		dataList = getDataForGraph();
		drawGraph(dataList);

		super.onViewCreated(view, savedInstanceState);
	}

	private void drawGraph(List<PeriodTotal> dataList) {
		List<BarEntry> entries = new ArrayList<>();
		ArrayList<String> xAxisLabels = new ArrayList<>();
		Float index;
		for (int i = 0; i < dataList.size(); i++) {
			String labelName = dataList.get(i).getPeriodName();
			float totalOfPeriod = (dataList.get(i).getTotalOfPeriod());
			index = Float.valueOf(i);
			entries.add(new BarEntry(index, totalOfPeriod));
			xAxisLabels.add(labelName);
		}

		BarDataSet set = new BarDataSet(entries, "");
		BarData data = new BarData(set);

//		data.setBarWidth(0.7f); // set custom bar width
		barChart.setData(data);

		//data.setValueTextColor(android.R.color.white);
		//barChart.setFitBars(true); // make the x-axis fit exactly all bars
		barChart.getDescription().setEnabled(false);
		barChart.setVisibleXRangeMaximum(6);
		barChart.setPinchZoom(true);
		barChart.setDrawBorders(false);
		barChart.setAutoScaleMinMaxEnabled(false);
		barChart.setDrawGridBackground(false);
		// Hide the description
		barChart.getAxisLeft().setDrawLabels(true);
		barChart.getAxisLeft().setValueFormatter(new LargeValueFormatter());
		barChart.getAxisRight().setDrawLabels(false);
		barChart.getAxisLeft().setDrawGridLines(true);
		barChart.getAxisRight().setDrawGridLines(false);
		barChart.getAxisLeft().setDrawAxisLine(false);
		barChart.getAxisRight().setDrawAxisLine(false);

		barChart.getLegend().setEnabled(false);
		barChart.animateY(500);

		XAxis xAxis = barChart.getXAxis();
		xAxis.setLabelCount(entries.size());
		xAxis.setTextSize(10f);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

		xAxis.setDrawAxisLine(true);
		xAxis.setDrawGridLines(false);
		// set a custom value formatter
		xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));


		if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
			set.setColors(Util.colorArrayDark, 255);
			xAxis.setTextColor(Color.WHITE);
			barChart.getAxisLeft().setTextColor(Color.WHITE);
			data.setValueTextColor(Color.WHITE);

		//	xAxis.setTextColor(android.R.color.white);

		}else{
			set.setColors(Util.colorArray, 255);
			xAxis.setTextColor(Color.DKGRAY);
			data.setValueTextColor(Color.DKGRAY);
			barChart.getAxisLeft().setTextColor(Color.DKGRAY);
		//	data.setValueTextColor(android.R.color.black);
		//	barChart.getAxisLeft().setTextColor(android.R.color.black);
		//	xAxis.setTextColor(android.R.color.darker_gray);

		}



		barChart.invalidate(); // refresh

	}

	private List<PeriodTotal> getDataForGraph() {
		DatabaseHandler db = new DatabaseHandler(getContext());
		//List<PeriodTotal> periodTotalList = db.getTransactionsByPeriod();
		List<PeriodTotal> periodTotalList = db.getTransactionsByPeriodFiltered(startDateString, endDateString);

		return periodTotalList;
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
			case R.id.refreshGraphs:
				drawCustom();
				break;

			case R.id.selectStartDateForBar:
			case R.id.selectEndDateForBar:
				pickerShow(pView);
				setDate = true;
				break;
		}

		if (!setDate) {
			setDatePickers();
			dataList = getDataForGraph();
			drawGraph(dataList);

		}
	}

	private void setUpToolbar() {
//		((BarChart)getActivity()).setUpToolbar("Trend Graph");

	}

	private void setDatePickers() {
		String startDate = DateConverters.longStringToDateString(startDateString);
		startDateET.setText(startDate);
		String endDate = DateConverters.longStringToDateString(endDateString);
		endDateET.setText(endDate);
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

	private void drawCustom() {
		startDateString = DateConverters.dateStringToLongString(startDateET.getText().toString());
		endDateString = DateConverters.dateStringToLongString(endDateET.getText().toString());

		if (DateConverters.isStartBeforeEnd(startDateString, endDateString)) {
			dataList = getDataForGraph();
			drawGraph(dataList);
		} else {
			Snackbar.make(startDateET, "Please select a start date that is before the end date", Snackbar.LENGTH_LONG).show();
			Log.d(Util.TAG, "custom " + startDateString + " " + endDateString);
		}
	}


	private void drawThisMonth() {
		startDateString = DateConverters.getFirstOf3PrevMonth();
		endDateString = DateConverters.getcurrentDateInMilLs();

	}
	/*@Override
	public void onBackPressed(){
		NavUtils.navigateUpFromSameTask(this);
	}*/

	private void drawThisYear() {
		startDateString = DateConverters.getFirstOfCurrentYearInMills();
		endDateString = DateConverters.getcurrentDateInMilLs();

	}

	private void drawAllData() {
		startDateString = DateConverters.getEpochStart();
		endDateString = DateConverters.getcurrentDateInMilLs();
	}

}
