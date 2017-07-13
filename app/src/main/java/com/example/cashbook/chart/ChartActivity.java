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
import com.example.cashbook.choice.Choice;
import com.example.cashbook.history_bill.HistoryBillActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private BarDataSet barDataSet;
    private BarData barData;

    private PieChart pieChart;
    private PieDataSet pieDataSet;
    private PieData pieData;

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        hideActionBar();

        barChart_addData();
        barChart_stylish(); //修改报表格式

        pieChart_addData();
        pieChart_stylish();

        backButton();
    }

    private void pieChart_stylish() {
        //设置动画效果
        pieChart.animateXY(1000, 1300);

        //图表样式
        pieChart.setNoDataText("未添加数据");

        pieChart.setDescription(null); //隐藏右下角说明文字

        pieChart.setCenterText("支出概况"); //设置中心文字
        pieChart.setCenterTextSize(15); //设置中心文字大小

        pieChart.setEntryLabelColor(Color.BLACK); //设置标签文字颜色


        //分区样式
        pieDataSet.setSliceSpace(3); //设置分区间隔

        int[] colors = {
                Color.rgb(255,145,145), Color.rgb(255,190,140), Color.rgb(255,215,125), Color.rgb(255,245,107),
                Color.rgb(250,255,170), Color.rgb(220,255,100), Color.rgb(166,255,172), Color.rgb(178,242,230),
                Color.rgb(162,145,255), Color.rgb(231,158,255), Color.rgb(252,189,232), Color.rgb(255,153,175),
        };
//        十二色环
//        Color.rgb(255, 0, 0), Color.rgb(255, 51, 0), Color.rgb(255, 102, 0), Color.rgb(255, 153, 0),
//                Color.rgb(255, 255, 0), Color.rgb(153,255,0), Color.rgb(0,255,0), Color.rgb(0,255,255),
//                Color.rgb(0, 0, 255), Color.rgb(102,0,255), Color.rgb(255,0,255), Color.rgb(255,0,102),
        pieDataSet.setColors(colors); //设置区间颜色

        pieDataSet.setHighlightEnabled(true); //允许高亮

        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE); //设置种类标签位置--内部
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE); //设置数值标签位置--外部


        //value数值格式化
        pieData.setValueFormatter(new PieChartValueFormatter());
        pieDataSet.setValueTextSize(10); //设置value文字大小
    }

    private void pieChart_addData() {
        pieChart = (PieChart) findViewById(R.id.pie_chart);

        ArrayList<PieEntry> entries = new ArrayList<>();
        List<Choice> choices = DataSupport.findAll(Choice.class);
        int i = 0;
        for(Choice choice : choices) {
            int count = DataSupport
                    .where("kind = ?", choice.getName())
                    .count(Consumption.class);
            if(count==0) continue;
            entries.add(new PieEntry(DataSupport
                    .where("kind = ?", choice.getName())
                    .sum(Consumption.class, "money", float.class)
                    , choice.getName()));
        }
        pieDataSet = new PieDataSet(entries, "");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    //返回上一界面
    private void backButton() {
        back = (Button) findViewById(R.id.chart_backward);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChartActivity.this, "Backward", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void barChart_addData() {
        barChart = (BarChart) findViewById(R.id.bar_chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        List<Consumption> consumptions = DataSupport.order("date asc").find(Consumption.class);
        Consumption consumption = null;
        float money = 0;
        for(int i=0; i<consumptions.size(); ++i) {
            consumption  = consumptions.get(i);
//            Log.e("Time", String.valueOf(consumption.getFormatDate()));
//            Log.e("Kind", consumption.getKind());
//            Log.e("Money", String.valueOf(consumption.getMoney()));
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

        barDataSet = new BarDataSet(entries, "每日花销");
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();
    }

    private void barChart_stylish() {
        //设置动画效果
        barChart.animateY(1000, Easing.EasingOption.EaseInQuad);
        barChart.animateX(1000, Easing.EasingOption.EaseInQuad);

        //设置图表样式
        barChart.setNoDataText("未添加数据");
//        barChart.setDrawBorders(true); //边框
//        barChart.setDoubleTapToZoomEnabled(false); //不允许缩放
//        barChart.setMaxVisibleValueCount(7); //设置一页最大条数
        barChart.setDescription(null); //右下角说明文字
        barChart.setDrawBorders(true); //设置边框
        barChart.setBorderWidth(0.5f); //边框宽度

        //显示数值
        barData.setValueFormatter(new ValueFormatter());
        barData.setValueTextSize(8);
        barChart.setDrawValueAboveBar(true); //设置数值显示在图标上方

        //横坐标
        XAxis x = barChart.getXAxis();
        x.setLabelCount(7); //设置标签个数
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

    private void hideActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
    }
}
