package com.compiler.abohaoya.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.compiler.abohaoya.R;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends AppCompatActivity {

    Spinner cityNameSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        cityNameSpinner = (Spinner) findViewById(R.id.cityNameSpinner);

        List<String> list = new ArrayList<String>();
        list.add("Dhaka");
        list.add("Rajshahi");
        list.add("Khulna");
        list.add("Rangpur");
        list.add("Sylhet");
        list.add("Borishal");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityNameSpinner.setAdapter(adapter);

        cityNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(CityActivity.this, ""+adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CityActivity.this, MainActivity.class);
                String cityName = String.valueOf(intent.putExtra("cityName", adapterView.getItemAtPosition(i).toString()));
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
