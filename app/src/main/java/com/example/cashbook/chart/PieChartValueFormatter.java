package com.example.cashbook.chart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by dsz62 on 2017/7/11.
 */

public class PieChartValueFormatter implements IValueFormatter {
    private DecimalFormat format;

    public PieChartValueFormatter() {
        format = new DecimalFormat("###,###,##0.00");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return format.format(value) + "￥";
    }
}
