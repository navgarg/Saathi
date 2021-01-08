package com.example.saathi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //startActivity(new Intent(this, PDashboard.class));
        startActivity(new Intent(this, LoginActivity.class)); //working :)
        //startActivity(new Intent(this, NewReading.class)); //working :)
    }
}