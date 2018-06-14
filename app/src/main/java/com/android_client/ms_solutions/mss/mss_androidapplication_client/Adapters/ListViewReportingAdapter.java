package com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Holders.ViewReportingHolder;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;

import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 04/06/2018.
 */

public class ListViewReportingAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<String> listReportingTypeNames;

    private ViewReportingHolder holder;
    private String positionOfReporting;

    public ListViewReportingAdapter() {
    }

    public ListViewReportingAdapter(Context context, List<String> listReportingTypeNames) {
        mContext = context;
        this.listReportingTypeNames = listReportingTypeNames;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return listReportingTypeNames.size();
    }

    @Override
    public Object getItem(int i) {
        return listReportingTypeNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        positionOfReporting = listReportingTypeNames.get(position);

        if (view == null){

            holder = new ViewReportingHolder();
            view = inflater.inflate(R.layout.reporting_type_layout,null);

            holder.textViewReportingType = view.findViewById(R.id.txtView_ReportingTypeName);

            view.setTag(holder);
        }else{
            holder = (ViewReportingHolder) view.getTag();
        }

        holder.textViewReportingType.setText(listReportingTypeNames.get(position).toString());
        return view;
    }
}
