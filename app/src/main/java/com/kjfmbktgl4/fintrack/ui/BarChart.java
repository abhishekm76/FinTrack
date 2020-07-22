package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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
		barChart.setFitBars(true); // make the x-axis fit exactly all bars
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
		List<PeriodTotal> periodTotalList = db.getTransactionsByPeriod();
		return periodTotalList;
	}

}