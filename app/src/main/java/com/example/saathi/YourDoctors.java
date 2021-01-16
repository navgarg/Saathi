package com.example.saathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.saathi.data.Constants;
import com.example.saathi.data.Person;
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
import static com.example.saathi.data.Constants.DB_DOCTORS;
import static com.example.saathi.data.Constants.DB_NAME;
import static com.example.saathi.data.Constants.DB_SPECIALITY;
import static com.example.saathi.data.Constants.DB_UID;

public class YourDoctors extends AppCompatActivity {


    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String TAG = "YourDoctors";
    List<Person> arrayList = new ArrayList<>();
    ArrayList<String> uidList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_doctors);

        FloatingActionButton fab = findViewById(R.id.add_doctor_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YourDoctors.this, NewDoctor.class));
            }
        });

        Log.d(TAG, "getting data");
        //todo: get data from db
        //todo: update to use firebase uid
        db.collection(COLLECTION_PATIENT)
                .whereEqualTo(DB_UID, "9Jid11RmKQSOXMItthSSVFAB1OT2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot document : task.getResult().getDocuments()){
                                uidList = (ArrayList) document.get(DB_DOCTORS);
                                Log.d(TAG, "uidList: "+ uidList);
                            }
                        }
                    }
                });

        for (String uid : uidList){
            db.collection(COLLECTION_DOCTOR)
                    .whereEqualTo(DB_UID, uid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (DocumentSnapshot document : task.getResult().getDocuments()){
                                    arrayList.add(new Person(document.get(DB_NAME).toString()
                                            , document.get(DB_SPECIALITY).toString(), document.get(DB_UID).toString(), COLLECTION_DOCTOR));
                                    Log.d(TAG, "person added");
                                }
                            }
                        }
                    });
        }




    }
}