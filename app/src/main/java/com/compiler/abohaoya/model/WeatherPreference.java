package com.compiler.abohaoya.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mobile App Develop on 6-8-16.
 */
public class WeatherPreference {
    private static final String TEMP_UNIT_KEY = "tempUnit";
    private static final String CITY_NAME_KEY = "cityName";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public WeatherPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("SaveTempUnit", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getTempUnit() {
        String tempUnit = sharedPreferences.getString(TEMP_UNIT_KEY, null);
        return tempUnit;
    }

    public void setTempUnit(String tempUnit) {
        editor.putString(TEMP_UNIT_KEY, tempUnit);
        editor.commit();
    }

    public String getCityName() {
        String cityName = sharedPreferences.getString(CITY_NAME_KEY, null);
        return cityName;
    }

    public void setCityName(String cityName) {
        editor.putString(CITY_NAME_KEY, cityName);
        editor.commit();
    }

}
