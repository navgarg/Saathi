package com.example.saathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.saathi.data.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.saathi.data.Constants.COLLECTION_DOCTOR;
import static com.example.saathi.data.Constants.COLLECTION_PATIENT;
import static com.example.saathi.data.Constants.DB_AGE;
import static com.example.saathi.data.Constants.DB_NAME;
import static com.example.saathi.data.Constants.DB_PHONE;
import static com.example.saathi.data.Constants.DB_SEX;

public class ProfileActivity extends AppCompatActivity {

    EditText name, age, email, gender, phone;
    RadioButton radioButtonPatient, radioButtonDoctor;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.d(TAG, "in inCreate");

        //todo: in case there has to be a profile page as part of the app
        Intent intent = getIntent();

        name = findViewById(R.id.profile_name);
        age = findViewById(R.id.profile_age);
        email = findViewById(R.id.profile_email);
        gender = findViewById(R.id.profile_gender);
        phone = findViewById(R.id.profile_phone);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        radioButtonPatient = findViewById(R.id.radio_button_patient);
        radioButtonDoctor = findViewById(R.id.radio_button_doctor);

        Log.d(TAG, "in onCreate1");
        Toast.makeText(ProfileActivity.this, "Loading Data", Toast.LENGTH_LONG).show();

        Log.d(TAG, "getting data");
        db.collection(COLLECTION_PATIENT).whereEqualTo("uid", "uid")//todo: make uid dynamic
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    Log.d(TAG, "got data");
                                    name.setText(document.get(DB_NAME).toString());
                                    age.setText(document.get(DB_AGE).toString());
                                    email.setText("test@example.com");
                                    gender.setText(document.get(DB_SEX).toString());
                                    phone.setText(document.get(DB_PHONE).toString());
                                    radioButtonPatient.setChecked(true);
                                    Log.d(TAG, "Patient details set");
                                }

                            }
                        } else {
                            Log.w(TAG, "Error getting documents: patient db ", task.getException());
                            db.collection(COLLECTION_DOCTOR).whereEqualTo("uid", user.getUid())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                if (task.getResult() != null) {
                                                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                        name.setText(document.get(DB_NAME).toString());
                                                        age.setText(document.get(DB_AGE).toString());
                                                        email.setText(user.getEmail());
                                                        gender.setText(document.get(DB_SEX).toString());
                                                        phone.setText(document.get(DB_PHONE).toString());
                                                        radioButtonDoctor.setChecked(true);
                                                        Log.d(TAG, "Doctor details set");
                                                    }

                                                }
                                            } else {
                                                Log.w(TAG, "Error getting documents: doctor db ", task.getException());
                                            }
                                        }
                                    });
                        }
                    }
                });


    }
}