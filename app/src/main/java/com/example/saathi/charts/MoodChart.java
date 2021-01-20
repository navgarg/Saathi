package com.example.saathi.charts;

import android.util.Log;

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

import java.util.ArrayList;

import static com.example.saathi.data.Constants.COLLECTION_SPO2;

public class MoodChart {
    LineChart lineChart;
    ArrayList<Entry> barEntriesArrayList;
    ArrayList<Chart_Data> moodArrayList = new ArrayList<>();
    static final String TAG = "MoodChart";

    public MoodChart(LineChart lineChart){
        this.lineChart = lineChart;
        barEntriesArrayList = new ArrayList<>();
        moodArrayList.clear();
        //PDashboard.getMoodData();
        drawMoodChart();
        Log.d(TAG, "array: " + moodArrayList);
    }

    public void drawMoodChart(){
        moodArrayList.add(new Chart_Data("", (float) 0.4));
        moodArrayList.add(new Chart_Data("", (float) 0.3));
        moodArrayList.add(new Chart_Data("", (float) 0.4));
        moodArrayList.add(new Chart_Data("", (float) 0.2));
        moodArrayList.add(new Chart_Data("", (float) 0.5));
        moodArrayList.add(new Chart_Data("", (float) 0.6));
        moodArrayList.add(new Chart_Data("", (float) 0.5));
        Log.d(TAG, "getMoodData: array: " + moodArrayList);
        Log.d(TAG, "array:2 " + moodArrayList);
//        if(arrayList.size() > 7) {
//            moodArrayList.addAll(arrayList.subList(arrayList.size() - 7, arrayList.size()));
//            Log.d(TAG, "drawTempChart: array: " + moodArrayList.size());
//        }
        for (int i =0; i < moodArrayList.size();i++){
            float spo2 = moodArrayList.get(i).getValue();
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
        leftAxis.setEnabled(false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(20f);

    }
}
