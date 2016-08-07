package com.compiler.abohaoya.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mobile App Develop on 6-8-16.
 */
public class WeatherPreference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private static final String TEMP_UNIT_KEY = "tempUnit";

    public WeatherPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("SaveTempUnit", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setTempUnit(String tempUnit){
        editor.putString(TEMP_UNIT_KEY, tempUnit);
        editor.commit();
    }

    public String getTempUnit(){
        String tempUnit = sharedPreferences.getString(TEMP_UNIT_KEY, null);
        return tempUnit;
    }
}
