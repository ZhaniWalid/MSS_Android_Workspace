package com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsItems;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Holders.BarChartViewHolder;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.ChartData;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 05/06/2018.
 */

public class BarChartItem extends ChartItem {

    private Typeface mTf;

    public BarChartItem(ChartData<?> cd,Context c) {
        super(cd);
        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_BARCHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        BarChartViewHolder holder = null;

        if (convertView == null) {

            holder = new BarChartViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.barchart_list_item, null);
            holder.barChart = convertView.findViewById(R.id.BarChart);

            convertView.setTag(holder);

        } else {
            holder = (BarChartViewHolder) convertView.getTag();
        }

        // apply styling
        holder.barChart.getDescription().setEnabled(false);
        holder.barChart.setDrawGridBackground(false);
        holder.barChart.setDrawBarShadow(false);

        XAxis xAxis = holder.barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = holder.barChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = holder.barChart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChartData.setValueTypeface(mTf);

        // set data
        holder.barChart.setData((BarData) mChartData);
        holder.barChart.setFitBars(true);

        // do not forget to refresh the chart
//        holder.chart.invalidate();
        holder.barChart.animateY(700);

        return convertView;
    }
}
