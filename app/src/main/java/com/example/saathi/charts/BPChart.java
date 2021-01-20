package com.example.saathi.charts;

import android.util.Log;

import com.example.saathi.PDashboard;
import com.example.saathi.data.Chart_Data;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import static com.example.saathi.data.Constants.COLLECTION_TEMP;
import static com.example.saathi.data.Constants.DB_DIASTOLIC;
import static com.example.saathi.data.Constants.DB_SYSTOLIC;

public class BPChart {
    BarChart barChart;
    ArrayList<BarEntry> barEntriesArrayList1, barEntriesArrayList2;
    ArrayList<String> labelName;
    ArrayList<Chart_Data> systolic = new ArrayList<>();
    ArrayList<Chart_Data> diastolic = new ArrayList<>();

    public BPChart(BarChart barChart) {
        this.barChart = barChart;
        barEntriesArrayList1 = new ArrayList<>();
        barEntriesArrayList2 = new ArrayList<>();
        labelName = new ArrayList<>();
        systolic.clear();
        diastolic.clear();
        PDashboard.getBPData();
        //drawTempChart();
    }

    public void drawBPChart(ArrayList<Chart_Data> arrayList1, ArrayList<Chart_Data> arrayList2) {
        if(arrayList1.size() > 7 && arrayList2.size() >7) {
            systolic.addAll(arrayList1.subList(arrayList1.size() - 7, arrayList1.size()));
            diastolic.addAll(arrayList2.subList(arrayList2.size() - 7, arrayList2.size()));
        }


        for (int i = 0; i < systolic.size(); i++) {
            String date = systolic.get(i).getDate();
            float sys = systolic.get(i).getValue();
            float dia = diastolic.get(i).getValue();
            barEntriesArrayList1.add(new BarEntry(i, sys));
            barEntriesArrayList2.add(new BarEntry(i, dia));
            //add label for each new entry
            labelName.add(date);
        }

        //create new data set with all the data
        BarDataSet barDataSet1 = new BarDataSet(barEntriesArrayList1, DB_SYSTOLIC);
        barDataSet1.setColors(ColorTemplate.rgb("#06A94D"));
        BarDataSet barDataSet2 = new BarDataSet(barEntriesArrayList2, DB_DIASTOLIC);
        barDataSet1.setColors(ColorTemplate.rgb("#F95A2C"));

        //set data to the chart
        BarData barData = new BarData(barDataSet1, barDataSet2);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);

        //Format the x-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelName));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(labelName.size());
        xAxis.setLabelRotationAngle(0);
        barChart.animateY(2000);
        barChart.invalidate();

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(20f);
    }
}
