package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.CategoryTotals;
import com.kjfmbktgl4.fintrack.model.TransactionItem;

import java.util.ArrayList;
import java.util.List;

public class PieChart extends AppCompatActivity {
	private ArrayList<CategoryTotals> dataArrayList;
	private ArrayList<CategoryTotals> transactionItemArrayListByCategory = new ArrayList<>();
	com.github.mikephil.charting.charts.PieChart pieChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pie_chart);
		pieChart = findViewById(R.id.piechart);
		dataArrayList= getDataForGraph();
		drawPiechart(dataArrayList);
	}

	private ArrayList<CategoryTotals> getDataForGraph() {
		DatabaseHandler db = new DatabaseHandler(PieChart.this);
		transactionItemArrayListByCategory = db.getAllTransactionsByCategory();
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
}