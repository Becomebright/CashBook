package com.example.cashbook.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by dsz62 on 2017/7/10.
 */

public class XAxisValueFormatter implements IAxisValueFormatter {

    private String[] Month;

    public XAxisValueFormatter() {
        Month = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep","Oct", "Nov", "Dec"};
    }

    public String getFormattedValue(float value, AxisBase axis) {
        int date = (int) value;
        int day = date % 100;
        int month = date / 100;
        String day_s = String.valueOf(day);
        return Month[month-1] + " " + day_s;
    }
}
