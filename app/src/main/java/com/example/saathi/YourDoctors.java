package com.example.saathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.saathi.data.Constants;
import com.example.saathi.data.Person;
import com.example.saathi.data.PersonAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
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
import static com.example.saathi.data.Constants.DB_PHONE;
import static com.example.saathi.data.Constants.DB_SPECIALITY;
import static com.example.saathi.data.Constants.DB_UID;

public class YourDoctors extends AppCompatActivity {

    //getting array from db working :)

    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String TAG = "YourDoctors";
    ArrayList<Person> arrayList = new ArrayList<>();
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
                                                                    , document.get(DB_SPECIALITY).toString(), document.get(DB_UID).toString()
                                                                    , COLLECTION_DOCTOR, document.get(DB_PHONE).toString()));
                                                        }
                                                        PersonAdapter adapter = new PersonAdapter(YourDoctors.this, arrayList);
                                                        ListView listView =  findViewById(R.id.list_view_your_doc);
                                                        listView.setAdapter(adapter);
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    }
                });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_your_doc);
        navigation.getMenu().findItem(R.id.action_your_doctors).setChecked(true);
        navigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#00c6ae")));
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_your_doctors:
                        startActivity(new Intent(YourDoctors.this, YourDoctors.class));
                        break;
                    case R.id.action_pdash:
                        startActivity(new Intent(YourDoctors.this, PDashboard.class));
                        break;
                    case R.id.action_profile:
                        startActivity(new Intent(YourDoctors.this, ProfileActivity.class));
                        break;
                }
                return false;
            }
        });


    }
}