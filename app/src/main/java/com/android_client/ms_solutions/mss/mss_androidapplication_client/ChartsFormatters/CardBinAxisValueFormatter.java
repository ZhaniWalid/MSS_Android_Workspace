package com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsFormatters;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 12/06/2018.
 */

public class CardBinAxisValueFormatter implements IAxisValueFormatter {

    private String CardBinLabel;
    private DecimalFormat mFormat;
    private String[] listCardBinLabel;
    private int size;

    public CardBinAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    public CardBinAxisValueFormatter(String cardBinLabel) {
        this.CardBinLabel = cardBinLabel;
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    public CardBinAxisValueFormatter(String[] listCardBinLabel, int size) {
        this.listCardBinLabel = listCardBinLabel;
        this.size = size;
        this.listCardBinLabel = new String[size];
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        String x = "",y = "";

        /*
        for (String aListCardBinLabel : listCardBinLabel) {
            y = String.format(aListCardBinLabel);
        }
        */

        for (String s : listCardBinLabel){
            x = String.format(" (%s) ", s);
        }

        return x;

        //return mFormat.format(value) + CardBinLabel;
        //return x;
    }
}
