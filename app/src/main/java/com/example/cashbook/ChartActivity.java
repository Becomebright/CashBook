package com.example.cashbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    private LineChart mLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        mLineChart = (LineChart) findViewById(R.id.line_chart);

        hideActionBar();

        ArrayList<Entry> yValue = new ArrayList<>();
        List<Consumption> consumptions = DataSupport.findAll(Consumption.class);
        float money = 0;
        for(int i=0; i<consumptions.size(); ++i) {
            Consumption consumption  = consumptions.get(i);
            if(i==0 || consumption.getFormatDate().equals(consumptions.get(i-1).getFormatDate())) {
                money += consumption.getMoney();
                continue;
            }
            else{
                Log.e("Time", consumption.getFormatDate());
                Log.e("Money", String.valueOf(money));
                yValue.add(new Entry(Float.valueOf(consumption.getFormatDate()), money));
                money = 0;
            }
        }

        LineChartManager.initSingleLineChart(ChartActivity.this, mLineChart,yValue);

    }
    private void hideActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
    }
}
