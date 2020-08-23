package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kjfmbktgl4.fintrack.R;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MobileAds.initialize(this, getResources().getString(R.string.adMob_ID));

		BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
		NavController navController = Navigation.findNavController(this,R.id.fragment_nav_host);
		NavigationUI.setupWithNavController(bottomNavigationView,navController);

		Toolbar lToolbar = findViewById(R.id.toolbar);
		setSupportActionBar(lToolbar);
		getSupportActionBar().setTitle("Recent Transactions");

		AppBarConfiguration lAppBarConfiguration = new AppBarConfiguration.Builder(R.id.mainFragment,R.id.barChartFragment,R.id.pieChartFragment,R.id.settingsFragment).build();
		NavigationUI.setupWithNavController(lToolbar,navController,lAppBarConfiguration);

	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater lInflater = getMenuInflater();
		lInflater.inflate(R.menu.recycler_search,menu);
		MenuItem item = menu.findItem(R.id.action_search);
		SearchView lSearchView= (SearchView) item.getActionView();

		lSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}*/
}