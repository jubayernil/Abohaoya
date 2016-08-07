package com.compiler.abohaoya.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.compiler.abohaoya.Adapter.ForcrastListAdapter;
import com.compiler.abohaoya.R;
import com.compiler.abohaoya.model.City;
import com.compiler.abohaoya.model.WeatherPreference;
import com.compiler.abohaoya.pojo.CurrentWeatherResponse;
import com.compiler.abohaoya.pojo.WeatherForecaseResponse;
import com.compiler.abohaoya.pojo.WeatherList;
import com.compiler.abohaoya.service.Constant;
import com.compiler.abohaoya.service.WeatherServiceApi;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ToggleButton temtogglebutton;
    WeatherPreference weatherPreference;
    ForcrastListAdapter adapter;
    private TextView cityNameTextView;
    private TextView tempTextView;
    private TextView degreeTextView;
    private TextView celciusFahrenheitTextView;
    private TextView todayMaxMinTempTextView;
    private TextView weatherSummaryTextView;
    private TextView weatherDetailTextView;
    private TextView sunRiseTextView;
    private TextView sunSetTextView;
    private ImageView skyImageView;
    private ImageButton findLocationBtn;
    private ListView forcrastListView;
    private String cityName;
    private String tempUnit;
    private WeatherServiceApi weatherServiceApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        weatherPreference = new WeatherPreference(MainActivity.this);
        findLocationBtn = (ImageButton) findViewById(R.id.findLocationBtn);
        cityNameTextView = (TextView) findViewById(R.id.cityNameTextView);
        tempTextView = (TextView) findViewById(R.id.tempTextView);
        degreeTextView = (TextView) findViewById(R.id.degreeTextView);
        celciusFahrenheitTextView = (TextView) findViewById(R.id.celciusFahrenheitTextView);
        todayMaxMinTempTextView = (TextView) findViewById(R.id.todayMaxMinTempTextView);
        weatherSummaryTextView = (TextView) findViewById(R.id.weatherSummaryTextView);
        weatherDetailTextView = (TextView) findViewById(R.id.weatherDetailTextView);
        sunRiseTextView = (TextView) findViewById(R.id.sunRiseTextView);
        sunSetTextView = (TextView) findViewById(R.id.sunSetTextView);
        skyImageView = (ImageView) findViewById(R.id.skyImageView);
        temtogglebutton = (ToggleButton) findViewById(R.id.temtogglebutton);

        forcrastListView = (ListView) findViewById(R.id.forcrastListView);

        temtogglebutton.setTextOff((char) 0x00B0 + "C");
        temtogglebutton.setTextOn((char) 0x00B0 + "F");
        temtogglebutton.setText((char) 0x00B0 + "C");

        //TODO: get city input from city name click
        findLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLangDialog();
            }
        });


        if (weatherPreference.getTempUnit() == null) {
            weatherPreference.setTempUnit(Constant.CELSIUS_UNIT);
            weatherPreference.setCityName("Dhaka");
        }
        if (weatherPreference.getCityName() != null) {
            cityNameTextView.setText(weatherPreference.getCityName());
        }
//        Toast.makeText(MainActivity.this, "OnCreate: " + weatherPreference.getTempUnit(), Toast.LENGTH_LONG).show();
        tempUnit = weatherPreference.getTempUnit();

        networkLibraryInitialize();
        getCurrentWeatherData();
        getWeatherForecastData();
    }

    private void getWeatherForecastData() {
        String url = "forecast/daily?q=Dhaka&mode=json&units=metric&cnt=7&appid=20c5d5dba6ab6ff1a57258e71ca55a0e";
        Call<WeatherForecaseResponse> forecaseResponseCall = weatherServiceApi.getAllWeatherForecast(url);
        forecaseResponseCall.enqueue(new Callback<WeatherForecaseResponse>() {
            @Override
            public void onResponse(Call<WeatherForecaseResponse> call, Response<WeatherForecaseResponse> response) {
                WeatherForecaseResponse wfr = response.body();
                ArrayList<WeatherList> weatherLists = new ArrayList<WeatherList>();
                weatherLists = (ArrayList<WeatherList>) wfr.getList();
//TODO: here to add list view
                adapter = new ForcrastListAdapter(MainActivity.this, weatherLists);
                forcrastListView.setAdapter(adapter);
                for (WeatherList weatherList : weatherLists) {
                    Log.d("ListResult", "onResponse: " + weatherList.getDt() + " clouds " + weatherList.getClouds()
                            + " getDeg " + weatherList.getDeg());
                }
            }

            @Override
            public void onFailure(Call<WeatherForecaseResponse> call, Throwable t) {
                Log.e("onFailure", "onFailure: " + t);
            }
        });
    }

    private void getCurrentWeatherData() {
        String userUrl = "weather?q=" + cityName + "&units=" + tempUnit + Constant.ALP_KEY;
        Call<CurrentWeatherResponse> currentWeatherResponseCall = weatherServiceApi.getAllWeather(userUrl);
        currentWeatherResponseCall.enqueue(new Callback<CurrentWeatherResponse>() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                final CurrentWeatherResponse currentWeatherResponse = response.body();

                tempTextView.setText(String.valueOf((int) Math.ceil(currentWeatherResponse.getMain().getTemp())));
                degreeTextView.setText("" + (char) 0x00B0);
                //celciusFahrenheitTextView.setText("C");
                todayMaxMinTempTextView.setText("Today " + String.valueOf((int) Math.ceil(currentWeatherResponse.getMain().getTempMax())) + (char) 0x00B0 + " ~ " +
                        String.valueOf((int) Math.ceil(currentWeatherResponse.getMain().getTempMin())) + (char) 0x00B0);
                weatherSummaryTextView.setText(currentWeatherResponse.getWeather().get(0).getMain());
                weatherDetailTextView.setText(currentWeatherResponse.getWeather().get(0).getDescription());
                Picasso.with(getApplicationContext()).load(currentWeatherResponse.getWeather().get(0).getIcon()).into(skyImageView);
                sunRiseTextView.setText("Surise " + String.valueOf(fullTimeConvertMillis_to_Time(currentWeatherResponse.getSys().getSunrise())));
//                sunSetTextView.setText("Sunset "+String.valueOf(convertMilliToTime(currentWeatherResponse.getSys().getSunset())));
                sunSetTextView.setText("Sunset " + String.valueOf(fullTimeConvertMillis_to_Time(currentWeatherResponse.getSys().getSunset())));
                //Toast.makeText(MainActivity.this, "Icon: "+currentWeatherResponse.getWeather().get(0).getIcon(), Toast.LENGTH_SHORT).show();
                temtogglebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean on = ((ToggleButton) view).isChecked();
                        if (on) {
                            /*tempTextView.setText(String.valueOf((int) Math.ceil(currentWeatherResponse.getMain().getTemp())));

                            todayMaxMinTempTextView.setText("Today "+String.valueOf((int) Math.ceil(currentWeatherResponse.getMain().getTempMax()))+(char) 0x00B0+" ~ "+
                                    String.valueOf((int) Math.ceil(currentWeatherResponse.getMain().getTempMin()))+(char) 0x00B0);*/
                            weatherPreference.setTempUnit(Constant.FAHRENHEIT_UNIT);
                            Toast.makeText(MainActivity.this, "" + weatherPreference.getTempUnit(), Toast.LENGTH_LONG).show();
                            tempUnit = weatherPreference.getTempUnit();
                            celciusFahrenheitTextView.setText("F");
                            getCurrentWeatherData();
                        } else {
                            /*tempTextView.setText(String.valueOf((int) Math.ceil(currentWeatherResponse.getMain().getTemp())));

                            todayMaxMinTempTextView.setText("Today "+String.valueOf((int) Math.ceil(currentWeatherResponse.getMain().getTempMax()))+(char) 0x00B0+" ~ "+
                                    String.valueOf((int) Math.ceil(currentWeatherResponse.getMain().getTempMin()))+(char) 0x00B0);*/
                            weatherPreference.setTempUnit(Constant.CELSIUS_UNIT);
                            Toast.makeText(MainActivity.this, "" + weatherPreference.getTempUnit(), Toast.LENGTH_LONG).show();
                            tempUnit = weatherPreference.getTempUnit();
                            celciusFahrenheitTextView.setText("C");
                            getCurrentWeatherData();
                        }
                    }
                });
                temtogglebutton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Need Internet Connection", Toast.LENGTH_SHORT).show();
                temtogglebutton.setVisibility(View.GONE);
            }
        });
    }

    private void networkLibraryInitialize() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherServiceApi = retrofit.create(WeatherServiceApi.class);
    }

    private String convertToCelsius(double temp) {
        double dTemp = Math.ceil(temp);
        int iTemp = (int) (dTemp - 273.16);
        return String.valueOf(iTemp);
    }

    private String convertToFahrenheit(double temp) {
        double dTemp = Math.ceil(temp);
        int iTemp = (int) (dTemp * 9 / 5 - 459.67);
        return String.valueOf(iTemp);
    }

    private String convertMilliToTime(long millis) {
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)) % 24;
        String time = String.format("%02d:%02d:%02d", hour, minute, second);
        return time;
    }

    public String fullTimeConvertMillis_to_Time(long millis) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+6"));
        String convertedTime = simpleDateFormat.format(new Date(millis * 1000));

//
//        Date date = new Date(millis);
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//        String dateFormatted = formatter.format(date);


//        long ts = millis;
//        Date localTime = new Date(ts);
//        String format = "yyyy/MM/dd HH:mm:ss";
//        SimpleDateFormat sdf = new SimpleDateFormat(format);
//
//        // Convert Local Time to UTC (Works Fine)
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date gmtTime = new Date(sdf.format(localTime));
//        System.out.println("Local:" + localTime.toString() + "," + localTime.getTime() + " --> UTC time:"
//                + gmtTime.toString() + "," + gmtTime.getTime());
//
//        // **** YOUR CODE **** END ****
//
//        // Convert UTC to Local Time
//        Date fromGmt = new Date(gmtTime.getTime() + TimeZone.getDefault().getOffset(localTime.getTime()));
//        System.out.println("UTC time:" + gmtTime.toString() + "," + gmtTime.getTime() + " --> Local:"
//                + fromGmt.toString() + "-" + fromGmt.getTime());
        return convertedTime;
    }


    // Alert dialogue box get city name element(spinner and editText)
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialogue_list_row, null);
        dialogBuilder.setView(dialogView);

        final Spinner citySpinner = (Spinner) dialogView.findViewById(R.id.cityNameSpnr);
        final EditText cityEditText = (EditText) dialogView.findViewById(R.id.cityNameEt);

        dialogBuilder.setTitle("Select city: ");
//Bitmlab401 wifi password
        //set adapter to spinner
        ArrayList<City> cityArrayList = new ArrayList<>();
        City city = new City();
        cityArrayList = city.getAllCity();

        ArrayAdapter<City> spinnerArrayAdapter = new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, cityArrayList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(spinnerArrayAdapter);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(cityEditText, InputMethodManager.SHOW_IMPLICIT);

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(cityEditText.getWindowToken(), 0);

                if (cityEditText.getText().toString().isEmpty()) {
                    cityNameTextView.setText(citySpinner.getSelectedItem().toString());
                    weatherPreference.setCityName(citySpinner.getSelectedItem().toString());
                    cityName = cityNameTextView.getText().toString();
                    getCurrentWeatherData();
                    adapter.notifyDataSetChanged();
                } else {
                    cityNameTextView.setText(cityEditText.getText().toString());
                    weatherPreference.setCityName(citySpinner.getSelectedItem().toString());
                    cityName = cityNameTextView.getText().toString();
                    getCurrentWeatherData();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

}
