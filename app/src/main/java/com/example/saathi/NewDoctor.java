package com.example.saathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.saathi.data.ChooseDocAdapter;
import com.example.saathi.data.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.saathi.data.Constants.COLLECTION_DOCTOR;
import static com.example.saathi.data.Constants.COLLECTION_PATIENT;
import static com.example.saathi.data.Constants.DB_AGE;
import static com.example.saathi.data.Constants.DB_NAME;
import static com.example.saathi.data.Constants.DB_PHONE;
import static com.example.saathi.data.Constants.DB_SEX;
import static com.example.saathi.data.Constants.DB_SPECIALITY;
import static com.example.saathi.data.Constants.DB_UID;

public class NewDoctor extends AppCompatActivity {

    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String TAG = "NewDoctor";
    List<Person> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_doctor);
        Log.d(TAG, "getting data");

        db.collection(COLLECTION_DOCTOR)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                Person person = new Person(document.get(DB_NAME).toString(),
                                        document.get(DB_SPECIALITY).toString(), document.get(DB_UID).toString()
                                        , COLLECTION_DOCTOR, document.get(DB_PHONE).toString());
                                arrayList.add(person);
                                Log.d(TAG, "person added "+ document.get(DB_NAME).toString() + " " +
                                        document.get(DB_SPECIALITY).toString() + " " + document.get(DB_UID).toString());
                            }
                        }
                    }
                });

        RecyclerView chooseDoctorRecyclerView = findViewById(R.id.recycler_view_choose_doc);

        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        chooseDoctorRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(chooseDoctorRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        chooseDoctorRecyclerView.addItemDecoration(dividerItemDecoration);


        ChooseDocAdapter recyclerViewAdapter = new ChooseDocAdapter(getArray(),this);
        chooseDoctorRecyclerView.setAdapter(recyclerViewAdapter);
        Log.d(TAG, "arraylist sent");
    }

    private List<Person> getArray(){
        //todo: safe delete this method cuz we're already getting data in onCreate
        arrayList.add(new Person("Manish Garg", "Pulmonologist", "uid", COLLECTION_DOCTOR, "9868104455"));
        return arrayList;
    }
}