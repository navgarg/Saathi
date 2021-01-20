package com.example.saathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.saathi.data.Person;
import com.example.saathi.data.PersonAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.saathi.data.Constants.COLLECTION_DOCTOR;
import static com.example.saathi.data.Constants.COLLECTION_PATIENT;
import static com.example.saathi.data.Constants.DB_AGE;
import static com.example.saathi.data.Constants.DB_COLOR;
import static com.example.saathi.data.Constants.DB_DOCTORS;
import static com.example.saathi.data.Constants.DB_IS_CRITICAL;
import static com.example.saathi.data.Constants.DB_NAME;
import static com.example.saathi.data.Constants.DB_PATIENTS;
import static com.example.saathi.data.Constants.DB_PHONE;
import static com.example.saathi.data.Constants.DB_SEX;
import static com.example.saathi.data.Constants.DB_SPECIALITY;
import static com.example.saathi.data.Constants.DB_UID;

public class YourPatients extends AppCompatActivity {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String TAG = "YourPatients";
    ArrayList<Person> arrayList = new ArrayList<>();
    ArrayList<String> uidList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_patients);

        //todo: update to use firebase uid
        db.collection(COLLECTION_DOCTOR)
                .whereEqualTo(DB_UID, "KYMtRiIwoqYf71IJWertbiQ7kDC3")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot document : task.getResult().getDocuments()){
                                uidList = (ArrayList) document.get(DB_PATIENTS);
                                for (String uid : uidList){
                                    db.collection(COLLECTION_PATIENT)
                                            .whereEqualTo(DB_UID, uid)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()){
                                                        for (DocumentSnapshot document : task.getResult().getDocuments()){
                                                            arrayList.add(new Person(document.get(DB_NAME).toString()
                                                                    , document.get(DB_UID).toString()
                                                                    , COLLECTION_PATIENT
                                                                    , document.get(DB_AGE) + ", " + document.get(DB_SEX)
                                                                    , document.get(DB_PHONE).toString()
                                                                    , document.get(DB_COLOR).toString()));
                                                        }
                                                        PersonAdapter adapter = new PersonAdapter(YourPatients.this, arrayList);
                                                        ListView listView =  findViewById(R.id.list_view_your_pat);
                                                        listView.setAdapter(adapter);
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    }
                });





    }

}