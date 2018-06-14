package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsFormatters.MyAxisValueFormatter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsFormatters.TransactionStatusAxisValueFormatter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsFormatters.XYMarkerView;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_MerchantTypeTransactionBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_TransactionStatusBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BarChartMerchantTypeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BarChartMerchantTypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarChartMerchantTypeFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private SampleApi api = SampleApiFactory.getInstance();
    private List<gw_MerchantTypeTransactionBindingModel> list_MerchantTypeValues;

    private BarChart mChart;
    private SeekBar mSeekBarY;
    private TextView tvY;
    private Typeface mTfLight;

    // Values Class Asyntask onPostExecute() Method
    private int[] list_nbMerchTypes_ToReturn;
    private String[] list_merchantTypes_ToReturn;
    private int totalMerchantType_ToReturn = 0,sizeOfListMerchantsType =0;

    private IAxisValueFormatter xAxisMerchantTypeFormatter;

    public BarChartMerchantTypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BarChartMerchantTypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BarChartMerchantTypeFragment newInstance(String param1, String param2) {
        BarChartMerchantTypeFragment fragment = new BarChartMerchantTypeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bar_chart_merchant_type, container, false);

        tvY = view.findViewById(R.id.tvYMaxMerchantTypeBarChart);
        mSeekBarY = view.findViewById(R.id.seekBarChartMerchantType2);
        mChart = view.findViewById(R.id.BarchartMerchantType);

        mTfLight = Typeface.createFromAsset(BarChartMerchantTypeFragment.this.getContext().getAssets(), "OpenSans-Light.ttf");

        GetMerchantTypesValues();
        afterViewsConfig();

        return view;
    }

    private void afterViewsConfig(){

        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        mChart.setFitBars(false);
        mChart.animateY(2000);

        //xAxisFormatter = new DayAxisValueFormatter(mChart);
        xAxisMerchantTypeFormatter = new MyAxisValueFormatter();

        /*
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisMerchantTypeFormatter);
        */

        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(mTfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        XYMarkerView mv = new XYMarkerView(BarChartMerchantTypeFragment.this.getContext(), xAxisMerchantTypeFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        //setData(12, 50);

        // setting data
        mSeekBarY.setProgress(50);
        //mSeekBarX.setProgress(12);

        mSeekBarY.setOnSeekBarChangeListener(this);
        //mSeekBarX.setOnSeekBarChangeListener(this);

        // mChart.setDrawLegend(false);
    }

    private void setBarChartData(int countSize, int[] list_nbrePerMerchType,String[] list_merchantTypes,int totalOfMerchantTypes,List<gw_MerchantTypeTransactionBindingModel> lista ){

        float barWidth = 0.9f;

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        list_nbrePerMerchType = new int[countSize];
        list_merchantTypes = new String[countSize];
        BarDataSet[] list_BarDataSet = new BarDataSet[countSize];
        String[] list_merchantType_Abriviator = new String[countSize];

        final List<String> xAxis_labelsOfMerchantType = new ArrayList<>();

        for (int i = 0; i < countSize ; i++) {
            list_nbrePerMerchType[i] = lista.get(i).getNbreMerchantType();
            list_merchantTypes[i] = lista.get(i).getMerchantType();

            yVals1.add(new BarEntry(i,(float) list_nbrePerMerchType[i]));

            switch (list_merchantTypes[i]){

                case "non_conventionné":
                    list_merchantType_Abriviator[i] = "Non Conventionné";
                    break;
                case "conventionné":
                    list_merchantType_Abriviator[i] = "Conventionné";
                    break;

            }

            xAxis_labelsOfMerchantType.add(list_merchantType_Abriviator[i]);

            for (int k=0 ; k < xAxis_labelsOfMerchantType.size(); k++){

                XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTypeface(mTfLight);
                xAxis.setDrawGridLines(false);
                xAxis.setGranularity(1f); // only intervals of 1 day
                xAxis.setLabelCount(7);
                xAxis.setValueFormatter(new IndexAxisValueFormatter(list_merchantType_Abriviator));
            }


            if (mChart.getData() != null &&
                    mChart.getData().getDataSetCount() > 0) {

                //set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
                //set1.setValues(yVals1);

                for (int j=0 ; j< mChart.getData().getDataSetCount() ; j++){
                    list_BarDataSet[j] = (BarDataSet) mChart.getData().getDataSetByIndex(j);
                    list_BarDataSet[j].setValues(yVals1);
                }
                mChart.getData().notifyDataChanged();
                mChart.notifyDataSetChanged();

            } else {
                //set1 = new BarDataSet(yVals1, "DataSet 1");
                //set1.setDrawIcons(false);

                list_BarDataSet[i] = new BarDataSet(yVals1, "Merchant Type");
                list_BarDataSet[i].setDrawIcons(false);
                list_BarDataSet[i].setColors(ColorTemplate.COLORFUL_COLORS);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(list_BarDataSet[i]);

                BarData data = new BarData(dataSets);
                data.setValueTextSize(10f);
                data.setValueTypeface(mTfLight);
                data.setBarWidth(barWidth);
                mChart.setData(data);
            }
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }  */
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // seekBar Overrited methods
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private RectF mOnValueSelectedRectF = new RectF();
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + mChart.getLowestVisibleX() + ", high: "
                        + mChart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }


    private void GetMerchantTypesValues(){
        new MerchantTypeTask().execute();
    }

    // Class AsynTask
    // class MerchantTypeTask
    class MerchantTypeTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                list_MerchantTypeValues = api.GetOnlyMerchantTypes().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String string) {

            sizeOfListMerchantsType = list_MerchantTypeValues.size();

            list_merchantTypes_ToReturn = new String[sizeOfListMerchantsType];
            list_nbMerchTypes_ToReturn = new int[sizeOfListMerchantsType];

            // Println to verify in the console ,if i am getting the values or not
            System.err.println("size Of List Pie Chart Merchant Type is : " +sizeOfListMerchantsType);

            for (int i = 0; i < sizeOfListMerchantsType; i++) {

                list_nbMerchTypes_ToReturn[i] = list_MerchantTypeValues.get(i).getNbreMerchantType();
                list_merchantTypes_ToReturn[i] = list_MerchantTypeValues.get(i).getMerchantType();
                //list_nbTrnsPerStat_ToReturn[i] = model.getNbreTransactionsParEtatTransaction();
                //list_etatTrns_ToReturn[i] = model.getEtatTransaction();

                totalMerchantType_ToReturn += list_nbMerchTypes_ToReturn[i];

                setBarChartData(sizeOfListMerchantsType, list_nbMerchTypes_ToReturn , list_merchantTypes_ToReturn, totalMerchantType_ToReturn,list_MerchantTypeValues);

                System.err.println(" Merchant Type Pie Chart To Return : " + list_merchantTypes_ToReturn[i] + " => Value NbrPerMerchType Pie Chart To Return: " + list_nbMerchTypes_ToReturn[i]);
            }
            //setPieChartData(sizeOfListTransactionsStatus, list_nbTrnsPerStat_ToReturn , list_etatTrns_ToReturn, getTotal);

            System.err.println("Total Merchant Type Pie Chart To Return = " + totalMerchantType_ToReturn);

            super.onPostExecute(string);
        }
    }

}
