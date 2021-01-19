package com.example.saathi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        //greet.setText("Hi Dr. " + user.getDisplayName() + "! Good Day!");
        greet.setText("Hi " + "Navya" + "! Good Day!");

        CardView newPatients = findViewById(R.id.new_patients);
        newPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(DDashboard.this, YourPatients.class));
            }
        });

        CardView yourPatients = findViewById(R.id.your_patients);
        yourPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DDashboard.this, YourPatients.class));
            }
        });

        //todo
    }
}