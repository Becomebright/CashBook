package com.example.cashbook.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by dsz62 on 2017/7/10.
 */

public class YAxisValueFormatter implements IAxisValueFormatter {
    private DecimalFormat format;

    public YAxisValueFormatter() {
        format = new DecimalFormat("###,###,##0.00");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return format.format(value) + "￥";
    }
}
