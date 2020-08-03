package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
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

public class PieChart extends AppCompatActivity implements View.OnClickListener{
	private ArrayList<CategoryTotals> dataArrayList;
	private ArrayList<CategoryTotals> transactionItemArrayListByCategory = new ArrayList<>();
	private String startDateString,endDateString;
	private TextInputEditText startDateET, endDateET;
	private Button allButton,yearButton,monthButton,refreshButton,todayButton;
	com.github.mikephil.charting.charts.PieChart pieChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pie_chart);
		pieChart = findViewById(R.id.piechart);
		startDateET=findViewById(R.id.selectStartDateForPie);
		endDateET=findViewById(R.id.selectEndDateForPie);
		allButton = findViewById(R.id.btn_all);
		yearButton=findViewById(R.id.btn_2018);
		monthButton=findViewById(R.id.btn_thisMonth);
		todayButton=findViewById(R.id.btn_today);
		refreshButton=findViewById(R.id.refreshGraphs);

		allButton.setOnClickListener(this);
		yearButton.setOnClickListener(this);
		monthButton.setOnClickListener(this);
		todayButton.setOnClickListener(this);
		refreshButton.setOnClickListener(this);



		setStartAndEndDates();
		setDatePickers();
		dataArrayList= getDataForGraph();
		drawPiechart(dataArrayList);
	}

	private void setDatePickers() {
		String startDate = DateConverters.longStringToDateString(startDateString);
		startDateET.setText(startDate);
		String endDate = DateConverters.longStringToDateString(endDateString);
		endDateET.setText(endDate);
	}

	private void setStartAndEndDates() {
		if (startDateString==null || endDateString==null){
			Date endDate=Calendar.getInstance().getTime();
			endDateString = DateConverters.getcurrentDateInMilLs();
			startDateString=DateConverters.getFirstOfCurrentYearInMills();
			Log.d(Util.TAG,startDateString+" "+endDateString);

		}

	}

	private ArrayList<CategoryTotals> getDataForGraph() {
		DatabaseHandler db = new DatabaseHandler(PieChart.this);
		transactionItemArrayListByCategory = (ArrayList<CategoryTotals>) db.getTransactionsByCategoryByPeriod(startDateString,endDateString);
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
		set.setColors(ColorTemplate.MATERIAL_COLORS);
		data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(13f);
		data.setValueTextColor(Color.DKGRAY);
		pieChart.setDrawHoleEnabled(true);
		pieChart.setCenterText("Total Expenses By Category");
		pieChart.setCenterTextSize(18f);
		pieChart.getLegend().setEnabled(false);
		pieChart.setUsePercentValues(false);
		pieChart.animateY(1500);
		pieChart.invalidate();
	}

	public void drawAllData(){
	// set start and end dates to cover all data
		startDateString = DateConverters.getEpochStart();
		endDateString = DateConverters.getcurrentDateInMilLs();
		Log.d(Util.TAG,"all "+startDateString+" "+endDateString);

	}

	public void drawThisYear(){
		// set start and end dates to cover this year
		startDateString = DateConverters.getFirstOfCurrentYearInMills();
		endDateString = DateConverters.getcurrentDateInMilLs();
		Log.d(Util.TAG,"all "+startDateString+" "+endDateString);

	}
	public void drawThisMonth(){
		startDateString = DateConverters.getFirstOfMonth();
		endDateString = DateConverters.getcurrentDateInMilLs();
		Log.d(Util.TAG,"month "+startDateString+" "+endDateString);

		// set start and end dates to cover this month
	}
	public void drawToday(){
		startDateString = DateConverters.getFirstOfWeek();
		endDateString = DateConverters.getcurrentDateInMilLs();
		Log.d(Util.TAG,"month "+startDateString+" "+endDateString);




		// set start and end dates to cover today
	}
	public void drawCustom(){
		// set start and end dates to cover custom period
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
			case R.id.btn_today:
				drawToday();
				break;
			case R.id.refreshGraphs:
				drawCustom();
				break;

 		}
		setDatePickers();
		dataArrayList= getDataForGraph();
		drawPiechart(dataArrayList);
	}

}