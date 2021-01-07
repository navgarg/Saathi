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

public class TempChart {
    BarChart barChart;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> labelName;
    ArrayList<Chart_Data> tempArrayList = new ArrayList<>();
    private static final String SUB_COLLECTION_TEMP = "Temperature";

    public TempChart(BarChart barChart) {
        this.barChart = barChart;
        barEntriesArrayList = new ArrayList<>();
        labelName = new ArrayList<>();
        drawTempChart();
    }

    private void drawTempChart() {
        tempArrayList.clear();
        tempArrayList = PDashboard.getData(SUB_COLLECTION_TEMP);

        Log.d("TempChart", " "+ tempArrayList);

        for (int i = 0; i < tempArrayList.size(); i++) {
            String date = tempArrayList.get(i).getDate();
            float temp = tempArrayList.get(i).getValue();
            barEntriesArrayList.add(new BarEntry(i, temp));
            //add label for each new entry
            labelName.add(date);
        }

        //create new data set with all the data
        BarDataSet barDataSet = new BarDataSet(barEntriesArrayList, SUB_COLLECTION_TEMP);
        //choose many colors for each bar
        barDataSet.setColors(ColorTemplate.rgb("#dddddd"));

        //set data to the chart
        BarData barData = new BarData(barDataSet);
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
