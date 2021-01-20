package com.example.saathi.charts;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.saathi.data.Chart_Data;
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
import static com.example.saathi.data.Constants.DB_DIASTOLIC;
import static com.example.saathi.data.Constants.DB_SYSTOLIC;
import static com.example.saathi.data.Constants.DB_UID;
//todo: check
public class GetChartData {
    public static ArrayList<Chart_Data> arrayList = new ArrayList<>();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String docid;
    static final String TAG = "GetChartData";
    static SPO2Chart spo2Chart;
    static PulseChart pulseChart;
    static TempChart tempChart;
    static BPChart bpChart;
    static MoodChart moodChart;
    static String uid;

    public GetChartData(SPO2Chart spo2Chart, PulseChart pulseChart, BPChart bpChart, TempChart tempChart, MoodChart moodChart, String uid){
        GetChartData.uid = uid;
        GetChartData.spo2Chart = spo2Chart;
        Log.d(TAG, "GetChartData: constructor");
        GetChartData.tempChart = tempChart;
        GetChartData.pulseChart = pulseChart;
        GetChartData.bpChart = bpChart;
        GetChartData.moodChart = moodChart;
    }

    public static ArrayList<Chart_Data> getTempData(){
        Log.d(TAG, "getTempData: ");
        db.collection(COLLECTION_PATIENT).whereEqualTo(DB_UID, uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult() != null) {
                                Log.d(TAG, "onComplete1: " + uid);//uid correct
                                Log.d(TAG, "onComplete1: " + task.getResult().getDocuments());
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    docid = document.getId();
                                    Log.d(TAG, "onComplete: ");
                                    db.collection(COLLECTION_PATIENT).document(docid).collection(COLLECTION_TEMP)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        arrayList.clear();
                                                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                            arrayList.add(new Chart_Data(document.getId().split("-")[0]
                                                                    + " " + document.getId().split("-")[1]
                                                                    , Float.parseFloat(document.get(COLLECTION_TEMP).toString())));
                                                            Log.d(TAG, "onComplete: temp array set");
                                                        }
                                                        tempChart.drawTempChart(arrayList);
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

        return arrayList;
    }

    public static ArrayList<Chart_Data> getSPO2Data(){
        db.collection(COLLECTION_PATIENT).whereEqualTo(DB_UID, uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    docid = document.getId();
                                    db.collection(COLLECTION_PATIENT).document(docid).collection(COLLECTION_SPO2)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        arrayList.clear();
                                                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                            arrayList.add(new Chart_Data(document.getId().split("\\s")[0]
                                                                    + " " + document.getId().split("\\s")[1]
                                                                    , Float.parseFloat(document.get(COLLECTION_SPO2).toString())));
                                                        }
                                                        spo2Chart.drawSPO2Chart(arrayList);
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

        return arrayList;
    }

    public static ArrayList<Chart_Data> getPulseData(){
        db.collection(COLLECTION_PATIENT).whereEqualTo(DB_UID, uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    docid = document.getId();
                                    db.collection(COLLECTION_PATIENT).document(docid).collection(COLLECTION_PULSE)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        arrayList.clear();
                                                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                            arrayList.add(new Chart_Data(document.getId().split("\\s")[0]
                                                                    + " " + document.getId().split("\\s")[1]
                                                                    , Float.parseFloat(document.get(COLLECTION_PULSE).toString())));
                                                        }
                                                        pulseChart.drawPulseChart(arrayList);
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

        return arrayList;
    }

    public static ArrayList<Chart_Data> getBPData(){
        final ArrayList<Chart_Data> systolic = new ArrayList<>();
        final ArrayList<Chart_Data> diastolic = new ArrayList<>();
        db.collection(COLLECTION_PATIENT).whereEqualTo(DB_UID, uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    docid = document.getId();
                                    db.collection(COLLECTION_PATIENT).document(docid).collection(COLLECTION_BP)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        arrayList.clear();
                                                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                            systolic.add(new Chart_Data(document.getId().split("\\s")[0]
                                                                    + " " + document.getId().split("\\s")[1]
                                                                    , Float.parseFloat(document.get(DB_SYSTOLIC).toString())));
                                                            diastolic.add(new Chart_Data(document.getId().split("\\s")[0]
                                                                    + " " + document.getId().split("\\s")[1]
                                                                    , Float.parseFloat(document.get(DB_DIASTOLIC).toString())));
                                                        }
                                                        bpChart.drawBPChart(systolic, diastolic);
                                                        Log.d(TAG, "onComplete: bp data sent");
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

        return arrayList;
    }

}
