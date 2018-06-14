package com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsItems.ChartItem;

import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 05/06/2018.
 */

/** adapter that supports 3 different item types */
public class ChartDataAdapter extends ArrayAdapter<ChartItem> {

    public ChartDataAdapter(Context context,List<ChartItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        return getItem(position).getView(position, convertView, getContext());
    }

    @Override
    public int getItemViewType(int position) {
        // return the views type
        return getItem(position).getItemType();
    }

    @Override
    public int getViewTypeCount() {
        return 3; // we have 3 different item-types
        //return super.getViewTypeCount();
    }
}
