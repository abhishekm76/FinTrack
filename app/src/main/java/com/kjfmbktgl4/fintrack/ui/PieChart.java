package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.TransactionItem;
import com.kjfmbktgl4.fintrack.util.Util;

import java.util.ArrayList;
import java.util.List;

public class PieChart extends AppCompatActivity {
	private List<TransactionItem> dataArrayList;
	com.github.mikephil.charting.charts.PieChart pieChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pie_chart);
		dataArrayList = (List<TransactionItem>) getIntent().getSerializableExtra("dataArrayList");
		Log.d(Util.TAG, "sieze" + dataArrayList.size());
		pieChart = findViewById(R.id.piechart);
		getDataForGraph();

	}

	private void getDataForGraph() {
		ArrayList<TransactionItem> transactionItemArrayListByCategory = new ArrayList<>();
		DatabaseHandler db = new DatabaseHandler(PieChart.this);
		transactionItemArrayListByCategory = db.getAllTransactionsByCategory();

		Log.d(Util.TAG, "Size of arraylist" + transactionItemArrayListByCategory.size());
		drawPiechart(transactionItemArrayListByCategory);
	}

	public void drawPiechart(ArrayList<TransactionItem> yValues) {

		List<PieEntry> entries = new ArrayList<>();
		for (TransactionItem transactionItem : yValues) {
			Long amount = transactionItem.getAmountOfTransaction();
			String category = transactionItem.getNameCategoryOfTransaction();
			entries.add(new PieEntry(amount,category));
		}


		PieDataSet set = new PieDataSet(entries, "Spend by category");
		PieData data = new PieData(set);
		pieChart.setData(data);

		/*data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(13f);
		data.setValueTextColor(Color.DKGRAY);


		pieChart.setDrawHoleEnabled(true);
		//pieChart.setDescription("Pie Chart");
		pieChart.setCenterText("Total Expenses By Category");
		pieChart.setCenterTextSize(18f);
		pieChart.getLegend().setEnabled(false);
		pieChart.setUsePercentValues(true);
		pieChart.animateY(1500);*/
		pieChart.invalidate();

	}
}