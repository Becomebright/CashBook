package com.example.cashbook.chart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cashbook.Consumption;
import com.example.cashbook.R;
import com.example.cashbook.history_bill.HistoryBillActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static com.example.cashbook.R.menu.toolbar;

public class ChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private BarDataSet barDataSet;
    private BarData barData;

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.chart_toolbar);
        setSupportActionBar(toolbar);

//        hideActionBar();

        barChart_addData();
        barChart_stylish(); //修改报表格式

//        backButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_backward :
                Toast.makeText(ChartActivity.this, "Backward", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.toolbar_barChart:

                break;
            case R.id.toolbar_pieChart:

                break;
        }
        return true;
    }
    //    //返回上一界面
//    private void backButton() {
//        back = (Button) findViewById(R.id.chart_backward);
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(ChartActivity.this, "Backward", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//    }

    private void barChart_addData() {
        barChart = (BarChart) findViewById(R.id.bar_chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        List<Consumption> consumptions = DataSupport.findAll(Consumption.class);
        Consumption consumption = null;
        float money = 0;
        for(int i=0; i<consumptions.size(); ++i) {
            consumption  = consumptions.get(i);
            Log.e("Time", String.valueOf(consumption.getFormatDate()));
            Log.e("Kind", consumption.getKind());
            Log.e("Money", String.valueOf(consumption.getMoney()));
            if(i==0 || consumption.getFormatDate().equals(consumptions.get(i-1).getFormatDate())) {
                money += consumption.getMoney();
                continue;
            }
            else{
                entries.add(new BarEntry(Float.valueOf(consumptions.get(i-1).getFormatDate()), money));
                money = consumption.getMoney();
            }
        }
        if(!consumptions.isEmpty()) {
            entries.add(new BarEntry(Float.valueOf(consumption.getFormatDate()), money));
        }

        barDataSet = new BarDataSet(entries, "花销");
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();
    }

    private void barChart_stylish() {
        //设置动画效果
        barChart.animateY(1000, Easing.EasingOption.EaseInQuad);
        barChart.animateX(1000, Easing.EasingOption.EaseInQuad);

        //设置图表样式
//        barChart.setDrawBorders(true); //边框
//        barChart.setDoubleTapToZoomEnabled(false); //不允许缩放
//        barChart.setMaxVisibleValueCount(7); //设置一页最大条数
        barChart.setDescription(null); //右下角说明文字
        barChart.setDrawBorders(true); //设置边框
        barChart.setBorderWidth(0.5f); //边框宽度

        //显示数值
        barData.setValueFormatter(new ValueFormatter());

        //横坐标
        XAxis x = barChart.getXAxis();
        x.setLabelCount(7, false); //设置标签个数
        x.setValueFormatter(new XAxisValueFormatter());
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setGranularity(1f); //缩放的时候有用，比如放大的时候，我不想把横轴的月份再细分
        x.enableGridDashedLine(10f, 10f, 0f);    //背景用虚线表格来绘制  给整成虚线

        //纵坐标
        YAxis y = barChart.getAxisLeft();
        y.setValueFormatter(new YAxisValueFormatter());
        y.enableGridDashedLine(10f, 10f, 0f);    //背景用虚线表格来绘制  给整成虚线

        barChart.getAxisRight().setEnabled(false); //隐藏右边的坐标


    }

//    private void hideActionBar() {
//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null){
//            actionBar.hide();
//        }
//    }
}
