package com.example.saathi.charts;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.saathi.PDashboard;
import com.example.saathi.data.Chart_Data;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.example.saathi.data.Constants.COLLECTION_PULSE;
import static com.example.saathi.data.Constants.COLLECTION_SPO2;

public class SPO2Chart {
    LineChart lineChart;
    ArrayList<Entry> barEntriesArrayList;
    ArrayList<Chart_Data> spo2ArrayList = new ArrayList<>();
    static final String TAG = "SPO2Chart";

    public SPO2Chart(LineChart lineChart){
        this.lineChart = lineChart;
        barEntriesArrayList = new ArrayList<>();
        spo2ArrayList.clear();
        PDashboard.getSPO2Data();
        Log.d(TAG, "array: " + spo2ArrayList);
    }

    public void drawSPO2Chart(ArrayList<Chart_Data> spo2ArrayList){
        Log.d(TAG, "array:2 " + spo2ArrayList);
        if(spo2ArrayList.size() > 7) {
            spo2ArrayList = (ArrayList<Chart_Data>) spo2ArrayList.subList(spo2ArrayList.size()-7, spo2ArrayList.size());
        }
        for (int i =0; i < spo2ArrayList.size();i++){
            float spo2 = spo2ArrayList.get(i).getValue();
            barEntriesArrayList.add(new BarEntry(i, spo2));
        }
        LineDataSet lineDataSet = new LineDataSet(barEntriesArrayList, COLLECTION_SPO2);
        lineDataSet.setColors(ColorTemplate.rgb("#F95A2C"));

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setDrawLabels(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.animateY(2000);
        lineChart.invalidate();

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(20f);

    }
}
