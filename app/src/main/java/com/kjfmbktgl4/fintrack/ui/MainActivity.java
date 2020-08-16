package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kjfmbktgl4.fintrack.R;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
		NavController navController = Navigation.findNavController(this,R.id.fragment_nav_host);
		NavigationUI.setupWithNavController(bottomNavigationView,navController);

		Toolbar lToolbar = findViewById(R.id.toolbar);

		AppBarConfiguration lAppBarConfiguration = new AppBarConfiguration.Builder(R.id.mainFragment,R.id.barChartFragment,R.id.pieChartFragment,R.id.settingsFragment).build();
		NavigationUI.setupWithNavController(lToolbar,navController,lAppBarConfiguration);

	}
}