package com.example.saathi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DDashboard extends AppCompatActivity {


    static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddashboard);


        TextView greet = findViewById(R.id.ddash_greet);
        //greet.setText("Hi " + user.getDisplayName() + "! Good Day!");
        greet.setText("Hi " + "Navya" + "! Good Day!");

        //todo
    }
}