package com.kjfmbktgl4.fintrack.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.util.Preferences;
import com.kjfmbktgl4.fintrack.util.Util;

public class MainActivity extends AppCompatActivity {


	private AdView mAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setDarkMode();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpAds();
		setUpNav();


	}

	private void setUpNav() {

		BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
		NavController navController = Navigation.findNavController(this,R.id.fragment_nav_host);
		NavigationUI.setupWithNavController(bottomNavigationView,navController);

		Toolbar lToolbar = findViewById(R.id.toolbar);
		setSupportActionBar(lToolbar);
		getSupportActionBar().setTitle("Recent Transactions");

		AppBarConfiguration lAppBarConfiguration = new AppBarConfiguration.Builder(R.id.mainFragment,R.id.barChartFragment,R.id.pieChartFragment,R.id.settingsFragment).build();
		NavigationUI.setupWithNavController(lToolbar,navController,lAppBarConfiguration);
	}

	private void setUpAds() {
		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});

		mAdView = findViewById(R.id.adViewBanner);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);


	}

	private void setDarkMode() {

		Log.d(Util.TAG," Switched ");
		String status =	Preferences.getPrefs("DarkMode", this);
		boolean isChecked = Boolean.parseBoolean(status);
		if (isChecked){
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
		}else{
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		}
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