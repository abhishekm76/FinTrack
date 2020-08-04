package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.textfield.TextInputEditText;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.PeriodTotal;
import com.kjfmbktgl4.fintrack.util.DateConverters;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BarChart extends AppCompatActivity implements View.OnClickListener {
	com.github.mikephil.charting.charts.BarChart barChart;
	private List<PeriodTotal> dataList;
	private String startDateString, endDateString;
	private TextInputEditText startDateET, endDateET;
	private Boolean setDate = false;
	private Button allButton, yearButton, monthButton, refreshButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_chart);
		barChart = findViewById(R.id.barChart);
		startDateET = findViewById(R.id.selectStartDateForBar);
		endDateET = findViewById(R.id.selectEndDateForBar);
		allButton = findViewById(R.id.btn_all);
		yearButton = findViewById(R.id.btn_2018);
		monthButton = findViewById(R.id.btn_thisMonth);
		refreshButton = findViewById(R.id.refreshGraphs);

		startDateET.setOnClickListener(this);
		endDateET.setOnClickListener(this);
		allButton.setOnClickListener(this);
		yearButton.setOnClickListener(this);
		monthButton.setOnClickListener(this);
		refreshButton.setOnClickListener(this);

		dataList = getDataForGraph();
		drawGraph(dataList);

	}

	private void drawGraph(List<PeriodTotal> dataList) {
		List<BarEntry> entries = new ArrayList<>();
		ArrayList<String> xAxisLabels = new ArrayList<>();
		Float index;
		for (int i = 0; i < dataList.size(); i++) {
			String labelName = dataList.get(i).getPeriodName();
			int totalOfPeriod = (dataList.get(i).getTotalOfPeriod());
			index = Float.valueOf(i);
			entries.add(new BarEntry(index, totalOfPeriod));
			xAxisLabels.add(labelName);
		}

		BarDataSet set = new BarDataSet(entries, "Total Value Of Transactions By Period");
		BarData data = new BarData(set);
		data.setBarWidth(0.9f); // set custom bar width
		barChart.setData(data);
		barChart.setFitBars(false); // make the x-axis fit exactly all bars
		barChart.getDescription().setEnabled(false);
		barChart.setPinchZoom(true);
		XAxis xAxis = barChart.getXAxis();
		xAxis.setTextSize(10f);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		xAxis.setTextColor(Color.DKGRAY);
		xAxis.setDrawAxisLine(true);
		xAxis.setDrawGridLines(false);
		// set a custom value formatter
		xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));
		barChart.invalidate(); // refresh

	}

	private List<PeriodTotal> getDataForGraph() {
		DatabaseHandler db = new DatabaseHandler(BarChart.this);
		//List<PeriodTotal> periodTotalList = db.getTransactionsByPeriod();
		List<PeriodTotal> periodTotalList=db.getTransactionsByPeriodFiltered(startDateString,endDateString);

		return periodTotalList;
	}

	@Override
	public void onClick(View pView) {
		switch (pView.getId()) {
			case R.id.btn_all:
				drawAllData();
				break;
			case R.id.btn_2018:
				drawThisYear();
				break;
			case R.id.btn_thisMonth:
				drawThisMonth();
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
			/*setDatePickers();
			dataArrayList= getDataForGraph();
			drawPiechart(dataArrayList);*/

		}
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
		DatePickerDialog picker = new DatePickerDialog(BarChart.this,
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

	private void drawCustom () {
		startDateString = DateConverters.dateStringToLongString(startDateET.getText().toString());
		endDateString = DateConverters.dateStringToLongString(endDateET.getText().toString());

	}


	private void drawThisMonth () {
		startDateString = DateConverters.getFirstOfMonth();
		endDateString = DateConverters.getcurrentDateInMilLs();

	}

	private void drawThisYear () {
		startDateString = DateConverters.getFirstOfCurrentYearInMills();
		endDateString = DateConverters.getcurrentDateInMilLs();

	}

	private void drawAllData () {
		startDateString = DateConverters.getEpochStart();
		endDateString = DateConverters.getcurrentDateInMilLs();
	}

}