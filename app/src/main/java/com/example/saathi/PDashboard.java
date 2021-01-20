package com.example.saathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.saathi.charts.BPChart;
import com.example.saathi.charts.GetChartData;
import com.example.saathi.charts.MoodChart;
import com.example.saathi.charts.PulseChart;
import com.example.saathi.charts.SPO2Chart;
import com.example.saathi.charts.TempChart;
import com.example.saathi.data.Chart_Data;
import com.example.saathi.data.Constants;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.example.saathi.LoginActivity.mGoogleSignInClient;
import static com.example.saathi.data.Constants.COLLECTION_BP;
import static com.example.saathi.data.Constants.COLLECTION_PATIENT;
import static com.example.saathi.data.Constants.COLLECTION_PULSE;
import static com.example.saathi.data.Constants.COLLECTION_SPO2;
import static com.example.saathi.data.Constants.COLLECTION_TEMP;
import static com.example.saathi.data.Constants.DB_DATE;
import static com.example.saathi.data.Constants.DB_DIASTOLIC;
import static com.example.saathi.data.Constants.DB_DOCTORS;
import static com.example.saathi.data.Constants.DB_SYSTOLIC;

public class PDashboard extends AppCompatActivity {

    static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//    public static ArrayList<Chart_Data> arrayList = new ArrayList<>();
//    static FirebaseFirestore db = FirebaseFirestore.getInstance();
//    public static String docid = "";
//    static final String TAG = "PDashboard";
    static SPO2Chart spo2Chart;
    static PulseChart pulseChart;
    static TempChart tempChart;
    static BPChart bpChart;
    static MoodChart moodChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdashboard);

        TextView greet = findViewById(R.id.pdash_greet);
        greet.setText("Hi " + user.getDisplayName() + "! Good Day!");

        findViewById(R.id.new_reading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PDashboard.this, NewReading.class));
            }
        });

        findViewById(R.id.talk_to_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(PDashboard.this, ChatActivity.class));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://navyagarg.in/saathi-bot/"));
                startActivity(intent);
            }
        });

        tempChart = new TempChart((BarChart) findViewById(R.id.temp_chart));
        spo2Chart = new SPO2Chart((LineChart) findViewById(R.id.spo2_chart));
        pulseChart = new PulseChart((LineChart) findViewById(R.id.pulse_chart));
        bpChart = new BPChart((BarChart) findViewById(R.id.bp_chart));
        moodChart = new MoodChart((LineChart) findViewById(R.id.mood_chart));


        new GetChartData(spo2Chart, pulseChart, bpChart, tempChart, moodChart, user.getUid());
        Log.d(TAG, "onCreate: initialised all");

        BottomNavigationView navigation = findViewById(R.id.navigation_pdash);

        Menu menu = navigation.getMenu();
        menu.findItem(R.id.action_pdash).setIcon(R.mipmap.assignment_logo_round);
        menu.findItem(R.id.action_your_doctors).setIcon(R.mipmap.doc_icon_round);
        menu.findItem(R.id.action_profile).setIcon(R.mipmap.profile_icon_round);

        navigation.setItemIconTintList(null);
        navigation.getMenu().findItem(R.id.action_pdash).setChecked(true);
        navigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#00c6ae")));
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_your_doctors:
                        startActivity(new Intent(PDashboard.this, YourDoctors.class));
                        break;
                    case R.id.action_pdash:
                        startActivity(new Intent(PDashboard.this, PDashboard.class));
                        break;
                    case R.id.action_profile:
                        startActivity(new Intent(PDashboard.this, ProfileActivity.class));
                        break;
                }
                return false;
            }
        });


    }


}