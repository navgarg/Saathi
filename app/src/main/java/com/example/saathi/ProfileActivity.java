package com.example.saathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.saathi.data.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static com.example.saathi.data.Constants.COLLECTION_DOCTOR;
import static com.example.saathi.data.Constants.COLLECTION_PATIENT;
import static com.example.saathi.data.Constants.DB_AGE;
import static com.example.saathi.data.Constants.DB_NAME;
import static com.example.saathi.data.Constants.DB_PHONE;
import static com.example.saathi.data.Constants.DB_SEX;
import static com.example.saathi.data.Constants.DB_SEX_F;
import static com.example.saathi.data.Constants.DB_SEX_M;

public class ProfileActivity extends AppCompatActivity {

    EditText name, age, phone;
    RadioButton radioButtonPatient, radioButtonDoctor, radioButtonFemale, radioButtonMale;
    RadioGroup radioGroupGender;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "ProfileActivity";
    boolean isPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        name = findViewById(R.id.profile_name);
        age = findViewById(R.id.profile_age);
        phone = findViewById(R.id.profile_phone);
        radioGroupGender = findViewById(R.id.radio_group_gender);
        radioButtonMale = findViewById(R.id.radio_button_male);
        radioButtonFemale = findViewById(R.id.radio_button_female);

        db.collection(COLLECTION_PATIENT).whereEqualTo("uid", user.getUid())
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
                                    if (document.get(DB_SEX).toString().equals(DB_SEX_F)){
                                        radioButtonFemale.setChecked(true);
                                    }
                                    else{
                                        radioButtonMale.setChecked(true);
                                    }
                                    phone.setText(document.get(DB_PHONE).toString());
                                    isPatient = true;
                                }

                            }
                        } else {
                            Log.w(TAG, "Error getting documents: patient db ", task.getException());

                        }
                    }
                });

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
                                    if (document.get(DB_SEX).toString().equals(DB_SEX_F)){
                                        radioButtonFemale.setChecked(true);
                                    }
                                    else{
                                        radioButtonMale.setChecked(true);
                                    }
                                    phone.setText(document.get(DB_PHONE).toString());
                                    isPatient = false;
                                }

                            }
                        } else {
                            Log.w(TAG, "Error getting documents: doctor db ", task.getException());
                        }
                    }
                });

        findViewById(R.id.profile_save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().equals("") && !age.getText().toString().equals("")
                        && !phone.getText().toString().equals("")
                        && radioGroupGender.getCheckedRadioButtonId() != -1) {
                    Map<String, Object> reading = new HashMap<>();
                    reading.put(DB_NAME, name.getText().toString());
                    reading.put(DB_AGE, age.getText().toString());
                    reading.put(DB_PHONE, phone.getText().toString());
                    if (radioButtonFemale.isChecked()){
                        reading.put(DB_SEX, DB_SEX_F);
                    }
                    else {
                        reading.put(DB_SEX, DB_SEX_M);
                    }
                    if (isPatient){
                        db.collection(COLLECTION_PATIENT).document()
                                .set(reading)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProfileActivity.this, "Saved successfully!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ProfileActivity.this, PDashboard.class));
                            }
                        });
                    }
                    else if (!isPatient){
                        db.collection(COLLECTION_DOCTOR).document()
                                .set(reading)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ProfileActivity.this, "Saved successfully!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(ProfileActivity.this, DDashboard.class));
                                    }
                                });
                    }
                }
                else {
                    Toast.makeText(ProfileActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}