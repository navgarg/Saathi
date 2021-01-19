package com.example.saathi.charts;

import android.os.Build;
import android.util.Log;

import com.example.saathi.PDashboard;
import com.example.saathi.data.Chart_Data;
import com.example.saathi.data.Person;
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

public class TempChart {
    BarChart barChart;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> labelName;
    ArrayList<Chart_Data> tempArrayList = new ArrayList<>();

    public TempChart(BarChart barChart) {
        this.barChart = barChart;
        barEntriesArrayList = new ArrayList<>();
        labelName = new ArrayList<>();
        tempArrayList.clear();
        PDashboard.getTempData();
        //drawTempChart();
    }

    public void drawTempChart(ArrayList<Chart_Data> arrayList) {

        Log.d("TempChart", " "+ arrayList);

        if(arrayList.size() > 7) {
            tempArrayList.addAll(arrayList.subList(arrayList.size() - 7, arrayList.size()));
            Log.d("TempChart", "drawTempChart: array: " + tempArrayList.size());
        }

        for (int i = 0; i < tempArrayList.size(); i++) {
            String date = tempArrayList.get(i).getDate();
            float temp = tempArrayList.get(i).getValue();
            barEntriesArrayList.add(new BarEntry(i, temp));
            //add label for each new entry
            labelName.add(date);
        }

        //create new data set with all the data
        BarDataSet barDataSet = new BarDataSet(barEntriesArrayList, COLLECTION_TEMP);
        //choose many colors for each bar
        Log.d("TempChart", "drawTempChart: dataset created");
        barDataSet.setColors(ColorTemplate.rgb("#F95A2C"));

        //set data to the chart
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);

        Log.d("TempChart", "drawTempChart: data set");

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
