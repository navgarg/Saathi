package com.example.saathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.example.saathi.data.Constants.COLLECTION_PATIENT;
import static com.example.saathi.data.Constants.DB_DATE;
import static com.example.saathi.data.Constants.DB_TIME;

public class AddReading extends AppCompatActivity {

    String date, time;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reading);

        //Get current date and time and format
        final Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM", Locale.getDefault());
        date = df.format(d);
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        time = tf.format(d);

        //Assign values to all text views
        TextView title = findViewById(R.id.add_reading_title);
        title.setText(getIntent().getStringExtra("title"));

        TextView desc = findViewById(R.id.add_reading_desc);
        desc.setText(getIntent().getStringExtra("desc"));

        TextView unit = findViewById(R.id.add_reading_unit);
        unit.setText(getIntent().getStringExtra("unit"));

        final EditText date_et = findViewById(R.id.add_reading_date);
        date_et.setText(date);

        final EditText time_et = findViewById(R.id.add_reading_time);
        time_et.setText(time);

        date_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                date = date_et.getText().toString();
                return false;
            }
        });

        time_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                time = time_et.getText().toString();
                return false;
            }
        });

        final EditText reading_et = findViewById(R.id.edit_text_reading);

        Button addreading = findViewById(R.id.reading_button);
        addreading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddReading.this, "Adding reading", Toast.LENGTH_SHORT).show();
                String reading_val = reading_et.getText().toString();
                Map<String, Object> reading = new HashMap<>();

                if(!time_et.getText().toString().equals(time)){
                    time = time_et.getText().toString();
                }
                if(!date_et.getText().toString().equals(date)){
                    date = date_et.getText().toString();
                }

                if (!getIntent().getStringExtra("title").equals("Blood Pressure")) {
                    reading.put(getIntent().getStringExtra("title"), reading_val);
                    reading.put(DB_DATE, date);
                    reading.put(DB_TIME, time);
                    Log.d("AddReading", "" + date + " " + time);
                }
                else{
                    reading.put("Systolic", reading_val.split(" ")[0]);
                    reading.put("Diastolic", reading_val.split(" ")[1]);
                    reading.put(DB_DATE, date);
                    reading.put(DB_TIME, time);
                }

                if (!reading_val.equals("")) {
                    db.collection(COLLECTION_PATIENT).document(PDashboard.docid).collection(getIntent().getStringExtra("title")).document()
                            .set(reading)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("AddReading", "DocumentSnapshot successfully written!");
                                    Toast.makeText(AddReading.this, "Reading added successfully!", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("AddReading", "Error writing document", e);
                                }
                            });
                } else {
                    Toast.makeText(AddReading.this, "Please add reading", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
