package com.kjfmbktgl4.fintrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.kjfmbktgl4.fintrack.ui.MainActivity;
import com.kjfmbktgl4.fintrack.ui.NavDrawerLauncher;

public class Splash extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}