package com.example.saathi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //startActivity(new Intent(this, PDashboard.class));
        //startActivity(new Intent(this, LoginActivity.class)); //working :)
        //startActivity(new Intent(this, NewReading.class)); //working :)
        //startActivity(new Intent(this, ProfileActivity.class)); //todo: do we need this? not tested for getting info as i dont hv account :(
        //startActivity(new Intent(this, YourDoctors.class));
        startActivity(new Intent(this, DDashboard.class));
        //startActivity(new Intent(this, ChatActivity.class));
    }
}