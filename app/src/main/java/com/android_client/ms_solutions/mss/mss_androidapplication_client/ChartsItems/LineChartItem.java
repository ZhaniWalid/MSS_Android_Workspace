package com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsItems;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Holders.LineChartViewHolder;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.LineData;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 05/06/2018.
 */

public class LineChartItem extends ChartItem {

    private Typeface mTf;


    public LineChartItem(ChartData<?> cd,Context c) {
        super(cd);
        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_LINECHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        LineChartViewHolder holder = null;

        if (convertView == null) {

            holder = new LineChartViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.linechart_list_item, null);
            holder.lineChart = convertView.findViewById(R.id.LineChart);

            convertView.setTag(holder);

        } else {
            holder = (LineChartViewHolder) convertView.getTag();
        }

        // apply styling
        // holder.chart.setValueTypeface(mTf);
        holder.lineChart.getDescription().setEnabled(false);
        holder.lineChart.setDrawGridBackground(false);

        XAxis xAxis = holder.lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = holder.lineChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = holder.lineChart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // set data
        holder.lineChart.setData((LineData) mChartData);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.lineChart.animateX(750);

        return convertView;
    }
}
