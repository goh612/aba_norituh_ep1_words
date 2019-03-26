package com.example.mohea.aba_ep1_words;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.animation.Animation;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.set.GraphSet;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class GraphView {

    private LineChart lineChart;
    private Activity activity;

    public GraphView(LineChart lineChart, Activity activity) {
        this.lineChart = lineChart;
        this.activity = activity;
    }

    public void setGraph(ArrayList<GraphSet>[] arrayLists, int position, final String name){

        lineChart.clear();

        List<Entry> startEntries = new ArrayList<>();
        List<Entry> centerEntries = new ArrayList<>();
        List<Entry> endEntries = new ArrayList<>();

        for(int i=0;i<arrayLists[0].size();i++){
            GraphSet set = arrayLists[0].get(i);
            startEntries.add(new Entry(i+1,set.getScore(),set.getScoreId()));
        }
        for(int i=0;i<arrayLists[1].size();i++){
            GraphSet set = arrayLists[1].get(i);
            centerEntries.add(new Entry(i+1,set.getScore(),set.getScoreId()));
        }
        for(int i=0;i<arrayLists[2].size();i++){
            GraphSet set = arrayLists[2].get(i);
            endEntries.add(new Entry(i+1,set.getScore(),set.getScoreId()));
        }

        LineDataSet[] dataSets = null;

        switch (position){
            case 1:
                dataSets = new LineDataSet[1];
                dataSets[0] = new LineDataSet(startEntries,Constant.GRAPH_POSITION_START);
                break;
            case 2:
                dataSets = new LineDataSet[1];
                dataSets[0] = new LineDataSet(centerEntries,Constant.GRAPH_POSITION_CENTER);
                break;
            case 3:
                dataSets = new LineDataSet[1];
                dataSets[0] = new LineDataSet(startEntries,Constant.GRAPH_POSITION_END);
                break;
            case 4:
                dataSets = new LineDataSet[2];
                dataSets[0] = new LineDataSet(startEntries,Constant.GRAPH_POSITION_START);
                dataSets[1] = new LineDataSet(centerEntries,Constant.GRAPH_POSITION_CENTER);
                break;
            case 5:
                dataSets = new LineDataSet[2];
                dataSets[0] = new LineDataSet(startEntries,Constant.GRAPH_POSITION_START);
                dataSets[1] = new LineDataSet(endEntries,Constant.GRAPH_POSITION_END);
                break;
            case 6:
                dataSets = new LineDataSet[2];
                dataSets[0] = new LineDataSet(centerEntries,Constant.GRAPH_POSITION_CENTER);
                dataSets[1] = new LineDataSet(endEntries,Constant.GRAPH_POSITION_END);
                break;
            case 7:
                dataSets = new LineDataSet[3];
                dataSets[0] = new LineDataSet(startEntries,Constant.GRAPH_POSITION_START);
                dataSets[1] = new LineDataSet(centerEntries,Constant.GRAPH_POSITION_CENTER);
                dataSets[2] = new LineDataSet(endEntries,Constant.GRAPH_POSITION_END);
                break;

                default:
                    Toast.makeText(activity, Constant.REQUEST_POSITON, Toast.LENGTH_SHORT).show();
                    return;

        }


        for(int i=0;i<dataSets.length;i++){

            dataSets[i].setCircleRadius(5);
            dataSets[i].setCircleHoleRadius(2);
            dataSets[i].setLineWidth(3);
            dataSets[i].setCircleHoleColor(Color.WHITE);
            dataSets[i].setHighlightEnabled(true);
            dataSets[i].setDrawHighlightIndicators(false);
            dataSets[i].setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            dataSets[i].setValueTextSize(10);

            String label = dataSets[i].getLabel();

            if(label.equals(Constant.GRAPH_POSITION_START)) {
                dataSets[i].setCircleColor(Color.parseColor("#89CE84"));
                dataSets[i].setColor(Color.parseColor("#89CE84"));
            }else if(label.equals(Constant.GRAPH_POSITION_CENTER)){
                dataSets[i].setCircleColor(Color.parseColor("#BED6F3"));
                dataSets[i].setColor(Color.parseColor("#BED6F3"));
            }else if(label.equals(Constant.GRAPH_POSITION_END)){
                dataSets[i].setCircleColor(Color.parseColor("#ffc34d"));
                dataSets[i].setColor(Color.parseColor("#ffc34d"));
            }


        }

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);

        lineChart.setScaleEnabled(false);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                new RetryDialog(activity).show((long)e.getData(),name);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setAxisMinimum(1);
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineWidth(3);
        xAxis.setAxisLineColor(Color.parseColor("#00000000"));
        xAxis.setGridColor(Color.parseColor("#00000000"));
        xAxis.setGridLineWidth(3);
        xAxis.setTextSize(15);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisLineWidth(3);
        yAxis.setAxisLineColor(Color.parseColor("#00000000"));
        yAxis.setAxisMaximum(100);
        yAxis.setAxisMinimum(0);
        yAxis.setLabelCount(5);
        yAxis.setGridColor(Color.parseColor("#33000000"));
        yAxis.setGridLineWidth(1);

        YAxis bad = lineChart.getAxisRight();
        bad.setEnabled(false);

        int duration = arrayLists[0].size();

        lineChart.getDescription().setText("시도 회기");

        lineChart.animateX(duration*50);
        lineChart.invalidate();

    }

}
