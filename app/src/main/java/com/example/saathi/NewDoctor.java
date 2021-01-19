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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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


    static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String TAG = "NewDoctor";
    static String docid = "";
    List<Person> arrayListAllDoc = new ArrayList<>();
    List<Person> arrayList = new ArrayList<>();
    List<Person> arraylistCurrDoc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_doctor);
        Log.d(TAG, "getting data");

        final RecyclerView chooseDoctorRecyclerView = findViewById(R.id.recycler_view_choose_doc);

        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        chooseDoctorRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(chooseDoctorRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        chooseDoctorRecyclerView.addItemDecoration(dividerItemDecoration);


        //todo: filter doctors who're already present
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
                                arrayListAllDoc.add(person);
                                Log.d(TAG, "person added1 "+ arrayListAllDoc);
                            }


                        }
                    }
                });

        db.collection(COLLECTION_PATIENT).whereEqualTo(DB_UID, user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                       //todo: get array and store uids
                                                       Person person = new Person(document.get(DB_NAME).toString(),
                                                               document.get(DB_SPECIALITY).toString(), document.get(DB_UID).toString()
                                                               , COLLECTION_DOCTOR, document.get(DB_PHONE).toString());
                                                       arraylistCurrDoc.add(person);
                                                       Log.d(TAG, "person added2 " + arrayListAllDoc);
                                                   }

                                               }
                                           }
                                       });

        for (int i = 0; i<arraylistCurrDoc.size(); i++){
            if (!arraylistCurrDoc.contains(arrayListAllDoc.get(i))){
                arrayList.add(arrayListAllDoc.get(i));
            }

        }

        ChooseDocAdapter recyclerViewAdapter = new ChooseDocAdapter(arrayList,NewDoctor.this);
        chooseDoctorRecyclerView.setAdapter(recyclerViewAdapter);
        Log.d(TAG, "arraylist sent");
    }
}