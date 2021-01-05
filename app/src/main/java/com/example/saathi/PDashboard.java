package com.example.saathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.saathi.charts.PulseChart;
import com.example.saathi.charts.SPO2Chart;
import com.example.saathi.charts.TempChart;
import com.example.saathi.data.Chart_Data;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class PDashboard extends AppCompatActivity {

    static String type;
    static ArrayList<Chart_Data> arrayList;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String docid = "";
    private static final String COLLECTION_NAME = "Patient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdashboard);
        //getActionBar().hide();

        //find charts
        BarChart barChart = findViewById(R.id.temp_chart);
        LineChart spo2Chart = findViewById(R.id.spo2_chart);
        LineChart pulseChart = findViewById(R.id.pulse_chart);

        new TempChart(barChart);
        new SPO2Chart(spo2Chart);
        new PulseChart(pulseChart);

    }

    public static ArrayList<Chart_Data> getData(String docType){
        type = docType;
        db.collection(COLLECTION_NAME).whereEqualTo("uid", "uid")//todo: make uid dynamic
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    Log.d("getData1", "in for1");
                                    docid = document.getId();
                                    db.collection(COLLECTION_NAME).document(docid).collection(type)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if(task.getResult() != null) {
                                                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                                Log.d("getDat2a", "in for 2");
                                                                arrayList.add(new Chart_Data(document.getId(), (Integer) document.get(type)));
                                                            }
                                                        }
                                                    } else {
                                                        Log.w(TAG, "Error getting documents. ", task.getException());
                                                    }
                                                }
                                            });
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents. ", task.getException());
                        }
                    }
                });


        Log.d("getData3", " " + arrayList);
        return arrayList;
    }
}