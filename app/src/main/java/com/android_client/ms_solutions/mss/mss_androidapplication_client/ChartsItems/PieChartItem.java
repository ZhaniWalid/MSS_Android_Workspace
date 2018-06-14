package com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsItems;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Holders.PieChartViewHolder;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 04/06/2018.
 */

public class PieChartItem extends ChartItem {

    private Typeface mTf;
    private SpannableString mCenterText;

    public PieChartItem(ChartData<?> cd,Context c) {
        super(cd);

        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
        mCenterText = generateCenterText();
    }

    @Override
    public int getItemType() {
        return TYPE_PIECHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {
        PieChartViewHolder holder = null;

        if (convertView == null) {

            holder = new PieChartViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.piechart_list_item, null);
            holder.pieChart = convertView.findViewById(R.id.Piechart);

            convertView.setTag(holder);

        } else {
            holder = (PieChartViewHolder) convertView.getTag();
        }

        // apply styling
        holder.pieChart.getDescription().setEnabled(false);
        holder.pieChart.setHoleRadius(52f);
        holder.pieChart.setTransparentCircleRadius(57f);
        holder.pieChart.setCenterText(mCenterText);
        holder.pieChart.setCenterTextTypeface(mTf);
        holder.pieChart.setCenterTextSize(9f);
        holder.pieChart.setUsePercentValues(true);
        holder.pieChart.setExtraOffsets(5, 10, 50, 10);

        mChartData.setValueFormatter(new PercentFormatter());
        mChartData.setValueTypeface(mTf);
        mChartData.setValueTextSize(11f);
        mChartData.setValueTextColor(Color.WHITE);
        // set data
        holder.pieChart.setData((PieData) mChartData);

        Legend l = holder.pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.pieChart.animateY(900);

        return convertView;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("MPAndroidChart\ncreated by\nPhilipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, 14, 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.VORDIPLOM_COLORS[0]), 0, 14, 0);
        s.setSpan(new RelativeSizeSpan(.9f), 14, 25, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, 25, 0);
        s.setSpan(new RelativeSizeSpan(1.4f), 25, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 25, s.length(), 0);
        return s;
    }
}
