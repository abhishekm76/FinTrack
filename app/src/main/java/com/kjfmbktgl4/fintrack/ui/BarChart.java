package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.PeriodTotal;

import java.util.ArrayList;
import java.util.List;

public class BarChart extends AppCompatActivity {
	com.github.mikephil.charting.charts.BarChart barChart;
	private List<PeriodTotal> dataList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_chart);
		barChart = findViewById(R.id.barChart);

		dataList=getDataForGraph();
		drawGraph(dataList);

	}

	private void drawGraph(List<PeriodTotal> dataList) {
		List<BarEntry> entries = new ArrayList<>();
		Float index =0f;
		for (int i=0; i<dataList.size();i++){
			String labelName = dataList.get(i).getPeriodName();
			int totalOfPeriod = (dataList.get(i).getTotalOfPeriod());
			index = Float.valueOf(i);
			entries.add(new BarEntry(index,totalOfPeriod));

		}

		BarDataSet set = new BarDataSet(entries, "Total By Period");
		BarData data = new BarData(set);
		data.setBarWidth(0.9f); // set custom bar width
		barChart.setData(data);
		barChart.setFitBars(true); // make the x-axis fit exactly all bars
		XAxis xAxis = barChart.getXAxis();
		xAxis.setTextSize(10f);
		xAxis.setTextColor(Color.DKGRAY);
		xAxis.setDrawAxisLine(true);
		xAxis.setDrawGridLines(false);
// set a custom value formatter
		xAxis.setValueFormatter(new MyCustomFormatter());
// and more...
		barChart.invalidate(); // refresh

	}

	private List<PeriodTotal> getDataForGraph() {
		DatabaseHandler db = new DatabaseHandler(BarChart.this);
		List<PeriodTotal> periodTotalList = db.getTransactionsByPeriod();
		return periodTotalList;
	}


	public class MyCustomFormatter extends ValueFormatter{

	}
}