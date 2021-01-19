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

import static com.example.saathi.data.Constants.COLLECTION_PULSE;

public class PulseChart {
    LineChart lineChart;
    ArrayList<Entry> barEntriesArrayList;
    ArrayList<Chart_Data> pulseArrayList = new ArrayList<>();
    static final String TAG = "PulseChart";

    public PulseChart(LineChart lineChart){
        pulseArrayList.clear();
        PDashboard.getPulseData();
        Log.d(TAG, "array: " + pulseArrayList);
        this.lineChart = lineChart;
        barEntriesArrayList = new ArrayList<>();
        //drawPulseChart();
    }

    public void drawPulseChart(ArrayList<Chart_Data> pulseArrayList){
        Log.d(TAG, "array2: " + pulseArrayList);
        if(pulseArrayList.size() > 7) {
            pulseArrayList = (ArrayList<Chart_Data>) pulseArrayList.subList(pulseArrayList.size()-7, pulseArrayList.size());
        }

        for (int i = 0; i < pulseArrayList.size(); i++) {
            float pulse = pulseArrayList.get(i).getValue();
            barEntriesArrayList.add(new BarEntry(i, pulse));
            Log.d(TAG, "drawPulseChart: array: " + barEntriesArrayList);
        }

        Log.d(TAG, "drawPulseChart: creating chart");
        LineDataSet lineDataSet = new LineDataSet(barEntriesArrayList, COLLECTION_PULSE);
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
