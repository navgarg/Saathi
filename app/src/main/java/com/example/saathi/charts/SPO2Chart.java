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

public class SPO2Chart {
    LineChart lineChart;
    ArrayList<Entry> barEntriesArrayList;
    ArrayList<Chart_Data> spo2ArrayList = new ArrayList<>();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String docid = "";
    private static final String SUB_COLLECTION_SPO2 = "SPO2";

    public SPO2Chart(LineChart lineChart){
        this.lineChart = lineChart;
        barEntriesArrayList = new ArrayList<>();
        drawSPO2Chart();
    }

    private void drawSPO2Chart(){
        spo2ArrayList.clear();
        spo2ArrayList = PDashboard.getData(SUB_COLLECTION_SPO2);

        spo2ArrayList = PDashboard.arrayList;
        Log.d("drawSPO2", "" + spo2ArrayList);

        spo2ArrayList.add(new Chart_Data("key", 9));
        //spo2ArrayList.add(new Chart_Data("key", 78));



        for (int i =0; i < spo2ArrayList.size();i++){
            float spo2 = spo2ArrayList.get(i).getValue();
            barEntriesArrayList.add(new BarEntry(i, spo2));
        }
        LineDataSet lineDataSet = new LineDataSet(barEntriesArrayList, SUB_COLLECTION_SPO2);
        lineDataSet.setColors(ColorTemplate.rgb("#dddddd"));

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
