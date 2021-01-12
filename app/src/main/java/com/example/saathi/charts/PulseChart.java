package com.example.saathi.charts;

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

public class PulseChart {
    LineChart lineChart;
    ArrayList<Entry> barEntriesArrayList;
    ArrayList<Chart_Data> pulseArrayList = new ArrayList<>();
    private static final String SUB_COLLECTION_PULSE = "Pulse";

    public PulseChart(LineChart lineChart, ArrayList<Chart_Data> pulseArrayList){
        this.lineChart = lineChart;
        barEntriesArrayList = new ArrayList<>();
        pulseArrayList.clear();
        this.pulseArrayList = pulseArrayList;
        drawPulseChart();
    }

    private void drawPulseChart(){
        for (int i =0; i < pulseArrayList.size();i++){
            //String date = pulseArrayList.get(i).getDate();
            float pulse = pulseArrayList.get(i).getValue();
            barEntriesArrayList.add(new BarEntry(i, pulse));
        }

        LineDataSet lineDataSet = new LineDataSet(barEntriesArrayList,SUB_COLLECTION_PULSE);
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
