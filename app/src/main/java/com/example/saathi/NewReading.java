package com.example.saathi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.saathi.data.Constants;

import static com.example.saathi.data.Constants.COLLECTION_BP;
import static com.example.saathi.data.Constants.COLLECTION_PULSE;
import static com.example.saathi.data.Constants.COLLECTION_SPO2;
import static com.example.saathi.data.Constants.COLLECTION_TEMP;

public class NewReading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reading);

        findViewById(R.id.temperature).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewReading.this, AddReading.class);
                intent.putExtra("title", COLLECTION_TEMP);
                intent.putExtra("desc", "Enter the readings from your thermometer after keeping it under the tougue for one minute");
                intent.putExtra("unit", " °F");
                startActivity(intent);
            }
        });
        findViewById(R.id.spo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewReading.this, AddReading.class);
                intent.putExtra("title", COLLECTION_SPO2);
                intent.putExtra("desc", "Enter the readings of your Oxygen level from your Oximeter");
                intent.putExtra("unit", " %");
                startActivity(intent);
            }
        });
        findViewById(R.id.pulse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewReading.this, AddReading.class);
                intent.putExtra("title", COLLECTION_PULSE);
                intent.putExtra("desc", "Enter the readings of your Pulse Rate");
                intent.putExtra("unit", " bpm");
                startActivity(intent);
            }
        });
        findViewById(R.id.blood_pressure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewReading.this, AddReading.class);
                intent.putExtra("title", COLLECTION_BP);
                intent.putExtra("desc", "Enter the readings of your Blood Pressure \n \nAdd space-separated values for the systolic and diastolic readings respectively");
                intent.putExtra("unit", " mmHg");
                startActivity(intent);
            }
        });
    }
}