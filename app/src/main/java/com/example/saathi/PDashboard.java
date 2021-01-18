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
import com.example.saathi.data.Constants;
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
import static com.example.saathi.data.Constants.COLLECTION_PULSE;
import static com.example.saathi.data.Constants.COLLECTION_SPO2;
import static com.example.saathi.data.Constants.DB_DATE;

public class PDashboard extends AppCompatActivity {

    static String type;
    static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public static ArrayList<Chart_Data> arrayList = new ArrayList<>();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String docid = "";
    static final String TAG = "PDashboard";
    static SPO2Chart spo2Chart;
    static PulseChart pulseChart;
    static TempChart tempChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdashboard);

        TextView greet = findViewById(R.id.pdash_greet);
        greet.setText("Hi " + user.getDisplayName() + "! Good Day!");

        findViewById(R.id.new_reading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PDashboard.this, NewReading.class));
            }
        });

        //todo: add bp chart
        tempChart = new TempChart((BarChart) findViewById(R.id.temp_chart));
        //spo2Chart = new SPO2Chart((LineChart) findViewById(R.id.spo2_chart));
        //pulseChart = new PulseChart((LineChart) findViewById(R.id.pulse_chart));

    }
    //todo: they're working indi, why not together? ask dad abt how to do and know all are diff - can we make diff methods and not care abt efficiency? ig it'll be better then

    public static ArrayList<Chart_Data> getData(final String docType){
        type = docType;
        Log.d(TAG, "getData: ");
        db.collection(COLLECTION_PATIENT).whereEqualTo("uid", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d(TAG, "in onComplete");
                        if (task.isSuccessful()) {
                            if(task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    docid = document.getId();
                                    Log.d(TAG, "docid: " + docid);
                                    db.collection(COLLECTION_PATIENT).document(docid).collection(type)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        arrayList.clear();
                                                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                            arrayList.add(new Chart_Data(document.get(DB_DATE).toString(), Float.parseFloat(document.get(type).toString())));
                                                            Log.d(TAG, "chart added: " + type + " added: " + Float.parseFloat(document.get(type).toString()));
                                                        }
//                                                        Log.d(TAG, "onComplete: arraylist: " + arrayList);
//                                                        switch(type){
//                                                            case COLLECTION_SPO2:
//                                                                spo2Chart.drawSPO2Chart(arrayList);
//                                                                break;
//                                                            case COLLECTION_PULSE:
//                                                                pulseChart.drawPulseChart(arrayList);
//                                                                break;
//                                                        }
//                                                        return;
                                                        //pulseChart.drawPulseChart(arrayList);
                                                        //spo2Chart.drawSPO2Chart(arrayList);
                                                        tempChart.drawTempChart(arrayList);
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