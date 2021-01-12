package com.example.saathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.saathi.charts.PulseChart;
import com.example.saathi.charts.SPO2Chart;
import com.example.saathi.charts.TempChart;
import com.example.saathi.data.Chart_Data;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.example.saathi.data.Constants.COLLECTION_PATIENT;

public class PDashboard extends AppCompatActivity {

    static String type;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public static ArrayList<Chart_Data> arrayList = new ArrayList<>();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String docid = "9KpT3EhcCR4kVr8ogcCC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdashboard);

        TextView greet = findViewById(R.id.pdash_greet);
        //greet.setText("Hi " + user.getDisplayName() + "! Good Day!");

        findViewById(R.id.new_reading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PDashboard.this, NewReading.class));
            }
        });

        //new TempChart((BarChart) findViewById(R.id.temp_chart));
        //todo: see why accessing db is a prob
        new SPO2Chart((LineChart) findViewById(R.id.spo2_chart));
        new PulseChart((LineChart) findViewById(R.id.pulse_chart), getData("Pulse"));

    }

    public static ArrayList<Chart_Data> getData(final String docType){
        arrayList.clear();
        type = docType;

        db.collection(COLLECTION_PATIENT).whereEqualTo("uid", "uid")//todo: make uid dynamic
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    docid = document.getId();
                                    db.collection(COLLECTION_PATIENT).document(docid).collection(type)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if(task.getResult() != null) {
                                                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                                arrayList.add(new Chart_Data(document.getId(), Float.parseFloat(document.get(type).toString())));

                                                            }
                                                        }
                                                    } else {
                                                        Log.w(TAG, "Error getting documents. ", task.getException());
                                                    }
                                                }
                                            });
                                }
                                Log.d("getData", "outside loop 1" + arrayList);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents. ", task.getException());
                        }
                        Log.d("getdata", "onCreate" + arrayList);
                    }
                });

        Log.d("getData3", " " + arrayList);
        return arrayList;
    }
}