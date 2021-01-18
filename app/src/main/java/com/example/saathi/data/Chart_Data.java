package com.example.saathi.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import static android.content.ContentValues.TAG;

public class Chart_Data {
    String date;
    float value;
    float diastolic, systolic;
    public Chart_Data(String date, float value) {
        this.date = date;
        this.value = value;
    }

    public Chart_Data(String date, float systolic, float diastolic) {
        this.date = date;
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    public String getDate() {
        return date;
    }

    public float getValue() {
        return value;
    }

    public float getDiastolic(){ return diastolic;}

    public float getSystolic() {
        return systolic;
    }
}