package com.example.saathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.saathi.charts.BPChart;
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
import static com.example.saathi.data.Constants.COLLECTION_BP;
import static com.example.saathi.data.Constants.COLLECTION_PATIENT;
import static com.example.saathi.data.Constants.COLLECTION_PULSE;
import static com.example.saathi.data.Constants.COLLECTION_SPO2;
import static com.example.saathi.data.Constants.COLLECTION_TEMP;
import static com.example.saathi.data.Constants.DB_DATE;
import static com.example.saathi.data.Constants.DB_DIASTOLIC;
import static com.example.saathi.data.Constants.DB_DOCTORS;
import static com.example.saathi.data.Constants.DB_SYSTOLIC;

public class PDashboard extends AppCompatActivity {

    static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public static ArrayList<Chart_Data> arrayList = new ArrayList<>();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String docid = "";
    static final String TAG = "PDashboard";
    static SPO2Chart spo2Chart;
    static PulseChart pulseChart;
    static TempChart tempChart;
    static BPChart bpChart;

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
        spo2Chart = new SPO2Chart((LineChart) findViewById(R.id.spo2_chart));
        pulseChart = new PulseChart((LineChart) findViewById(R.id.pulse_chart));
        bpChart = new BPChart((BarChart) findViewById(R.id.bp_chart));

    }
    //todo: they're working indi, why not together? ask dad abt how to do and know all are diff - can we make diff methods and not care abt efficiency? ig it'll be better then

    public static ArrayList<Chart_Data> getTempData(){
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
                                    db.collection(COLLECTION_PATIENT).document(docid).collection(COLLECTION_TEMP)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        arrayList.clear();
                                                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                            arrayList.add(new Chart_Data(document.get(DB_DATE).toString(), Float.parseFloat(document.get(COLLECTION_TEMP).toString())));
                                                            Log.d(TAG, "chart added: " + COLLECTION_TEMP + " added: " + Float.parseFloat(document.get(COLLECTION_TEMP).toString()));
                                                        }
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

    public static ArrayList<Chart_Data> getSPO2Data(){
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
                                    db.collection(COLLECTION_PATIENT).document(docid).collection(COLLECTION_SPO2)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        arrayList.clear();
                                                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                            arrayList.add(new Chart_Data(document.get(DB_DATE).toString()
                                                                    , Float.parseFloat(document.get(COLLECTION_SPO2).toString())));
                                                            Log.d(TAG, "chart added: " + COLLECTION_SPO2 +
                                                                    " added: " + Float.parseFloat(document.get(COLLECTION_SPO2).toString()));
                                                        }
                                                        spo2Chart.drawSPO2Chart(arrayList);
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

    public static ArrayList<Chart_Data> getPulseData(){
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
                                    db.collection(COLLECTION_PATIENT).document(docid).collection(COLLECTION_PULSE)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        arrayList.clear();
                                                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                            arrayList.add(new Chart_Data(document.get(DB_DATE).toString(), Float.parseFloat(document.get(COLLECTION_PULSE).toString())));
                                                            Log.d(TAG, "chart added: " + COLLECTION_PULSE + " added: " + Float.parseFloat(document.get(COLLECTION_PULSE).toString()));
                                                        }
                                                        pulseChart.drawPulseChart(arrayList);
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

    public static ArrayList<Chart_Data> getBPData(){
        final ArrayList<Chart_Data> systolic = new ArrayList<>();
        final ArrayList<Chart_Data> diastolic = new ArrayList<>();
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
                                    db.collection(COLLECTION_PATIENT).document(docid).collection(COLLECTION_BP)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        arrayList.clear();
                                                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                            systolic.add(new Chart_Data(document.get(DB_DATE).toString()
                                                                    , Float.parseFloat(document.get(DB_SYSTOLIC).toString())));
                                                            diastolic.add(new Chart_Data(document.get(DB_DATE).toString()
                                                                    , Float.parseFloat(document.get(DB_DIASTOLIC).toString())));
                                                            Log.d(TAG, "chart added: " + COLLECTION_BP
                                                                    + " added: " + Float.parseFloat(document.get(DB_SYSTOLIC).toString()) +Float.parseFloat(document.get(DB_DIASTOLIC).toString()) );
                                                        }
                                                        bpChart.drawBPChart(systolic, diastolic);
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