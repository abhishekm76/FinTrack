package com.kjfmbktgl4.fintrack.util;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;

public class Preferences {
	public static void setPrefs(String key, String value, Context context){
		SharedPreferences sharedpreferences = context.getSharedPreferences(Util.SPREFNAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putString(key, value);
		editor.apply();
	}

	public static String getPrefs(String key, Context context){
		SharedPreferences sharedpreferences = context.getSharedPreferences(Util.SPREFNAME, Context.MODE_PRIVATE);
		return sharedpreferences.getString(key, "notfound");
	}

	public static void setArrayPrefs(String arrayName, List<String> array, Context mContext) {
		SharedPreferences prefs = mContext.getSharedPreferences(Util.SPREFNAME, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(arrayName +"_size", array.size());
		for(int i=0;i<array.size();i++)
			editor.putString(arrayName + "_" + i, array.get(i));
		editor.apply();
	}

	public static List<String> getArrayPrefs(String arrayName, Context mContext) {
		SharedPreferences prefs = mContext.getSharedPreferences(Util.SPREFNAME, 0);
		int size = prefs.getInt(arrayName + "_size", 0);
		ArrayList<String> array = new ArrayList<>(size);
		for(int i=0;i<size;i++)
			array.add(prefs.getString(arrayName + "_" + i, null));
		return array;
	}
}
