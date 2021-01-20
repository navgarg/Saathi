package com.example.saathi.data;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.saathi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.saathi.data.Constants.COLLECTION_DOCTOR;
import static com.example.saathi.data.Constants.COLLECTION_PATIENT;
import static com.example.saathi.data.Constants.DB_COLOR_AMBER;
import static com.example.saathi.data.Constants.DB_COLOR_GREEN;
import static com.example.saathi.data.Constants.DB_DOCTORS;
import static com.example.saathi.data.Constants.DB_NEW_PATIENTS;
import static com.example.saathi.data.Constants.DB_PATIENTS;
import static com.example.saathi.data.Constants.DB_UID;
//todo: test everything
public class NewPatientAdapter extends ArrayAdapter<Person> {

    static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static final String TAG = "NewPatientAdapter";

    public NewPatientAdapter(Activity context, ArrayList<Person> person) {
        super(context, 0, person);
    }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItemView = convertView;
            if(listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_new_pat, parent, false);
            }

            final Person currentPerson = getItem(position);
            ImageView details = listItemView.findViewById(R.id.list_new_pat_details);

            TextView name = listItemView.findViewById(R.id.list_new_pat_name);
            name.setText(currentPerson.getName());

            TextView info = listItemView.findViewById(R.id.list_new_pat_info);
            info.setText(currentPerson.getInfo());

            if (currentPerson.getColor().equals(DB_COLOR_GREEN)){
                name.setTextColor(Color.parseColor("#388E3C"));
            }
            else if (currentPerson.getColor().equals(DB_COLOR_AMBER)){
                name.setTextColor(Color.parseColor("#ffbf00"));
            }
            else{
                name.setTextColor(Color.parseColor("#FF3D00"));
            }
            details.setVisibility(android.view.View.VISIBLE);
            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo: complete
                }
            });

            final ImageView accept = listItemView.findViewById(R.id.list_new_pat_accept);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo
                    db.collection(COLLECTION_PATIENT)
                            .whereEqualTo(DB_UID, currentPerson.getUid())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        List<String> arraylistCurrDoc;
                                        Map<String, Object> reading = new HashMap<>();
                                        String docid;
                                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                            docid = document.getId();
                                            arraylistCurrDoc = (List<String>) document.get(DB_DOCTORS);
                                            Log.d(TAG, "person added2 " + arraylistCurrDoc);
                                            arraylistCurrDoc.add(user.getUid());
                                            reading.put(DB_DOCTORS, arraylistCurrDoc);
                                            db.collection(COLLECTION_PATIENT)
                                                    .document(docid).set(reading)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            });
                                        }

                                    }
                                }
                            });

                    db.collection(COLLECTION_DOCTOR)
                            .whereEqualTo(DB_UID, user.getUid())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        List<String> arraylistCurrPat, arrayListNewPat;
                                        Map<String, Object> reading = new HashMap<>();
                                        String docid;
                                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                            docid = document.getId();
                                            arraylistCurrPat = (List<String>) document.get(DB_PATIENTS);
                                            arrayListNewPat = (List<String>) document.get(DB_NEW_PATIENTS);
                                            arrayListNewPat.remove(user.getUid());
                                            arraylistCurrPat.add(currentPerson.getUid());
                                            Log.d(TAG, "person added2 " + arraylistCurrPat);
                                            reading.put(DB_PATIENTS, arraylistCurrPat);
                                            reading.put(DB_NEW_PATIENTS, arrayListNewPat);
                                            db.collection(COLLECTION_DOCTOR)
                                                    .document(docid).set(reading)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                        }
                                                    });
                                        }

                                    }
                                }
                            });
                }
            });

            ImageView decline = listItemView.findViewById(R.id.list_new_pat_decline);
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo
                    db.collection(COLLECTION_DOCTOR)
                            .whereEqualTo(DB_UID, user.getUid())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        List<String> arraylistCurrPat, arrayListNewPat;
                                        Map<String, Object> reading = new HashMap<>();
                                        String docid;
                                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                            docid = document.getId();
                                            arrayListNewPat = (List<String>) document.get(DB_NEW_PATIENTS);
                                            arrayListNewPat.remove(user.getUid());
                                            reading.put(DB_NEW_PATIENTS, arrayListNewPat);
                                            db.collection(COLLECTION_DOCTOR)
                                                    .document(docid).set(reading)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                        }
                                                    });
                                        }

                                    }
                                }
                            });
                }
            });


            return listItemView;
        }
    }