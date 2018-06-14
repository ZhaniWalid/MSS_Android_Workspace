package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsFormatters.MyAxisValueFormatter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsFormatters.TransactionStatusAxisValueFormatter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsFormatters.XYMarkerView;
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
 * {@link BarChartTransactionStatusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BarChartTransactionStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarChartTransactionStatusFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
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
    private List<gw_TransactionStatusBindingModel> list_TransactionsStatusValues;

    private BarChart mChart;
    private SeekBar mSeekBarY;
    private TextView tvY;
    private Typeface mTfLight;

    // Values Class Asyntask onPostExecute() Method
    private int[] list_nbTrnsPerStat_ToReturn;
    private String[] list_etatTrns_ToReturn;
    private int totalTransactions_ToReturn = 0,sizeOfListTransactionsStatus =0;

    private IAxisValueFormatter xAxisFormatter,xAxisTransactionsStatusFormatter;
    //private String[] xAxis_labelsOfTranStatus;

    public BarChartTransactionStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BarChartTransactionStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BarChartTransactionStatusFragment newInstance(String param1, String param2) {
        BarChartTransactionStatusFragment fragment = new BarChartTransactionStatusFragment();
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
        View view = inflater.inflate(R.layout.fragment_bar_chart_transaction_status, container, false);

        tvY = view.findViewById(R.id.tvYMaxTrnsStatBarChart);
        mSeekBarY = view.findViewById(R.id.seekBarChartTrnsStat2);
        mChart = view.findViewById(R.id.BarchartTrnsStat);

        mTfLight = Typeface.createFromAsset(BarChartTransactionStatusFragment.this.getContext().getAssets(), "OpenSans-Light.ttf");

        afterViewsConfig();
        GetTransactionsStatusValues();

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
        xAxisTransactionsStatusFormatter = new TransactionStatusAxisValueFormatter();

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisTransactionsStatusFormatter);

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

        XYMarkerView mv = new XYMarkerView(BarChartTransactionStatusFragment.this.getContext(), xAxisTransactionsStatusFormatter);
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

    private void setBarChartData(int countSize,int MaxOfMaxValue, int[] list_nbreTrnsPerStatus,String[] list_statusTransaction,List<gw_TransactionStatusBindingModel> lista){

        float start = 0f;

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        list_nbreTrnsPerStatus = new int[countSize];
        list_statusTransaction = new String[countSize];
        BarDataSet[] list_BarDataSet = new BarDataSet[countSize];
        String[] list_statusTransaction_Abriviator = new String[countSize];

        final List<String> xAxis_labelsOfTranStatus = new ArrayList<>();

        //int max = 0;
        // i -> valeurs sur l'axes des x
        for(int i= 0; i< countSize ; i++){

            list_nbreTrnsPerStatus[i] = lista.get(i).getNbreTransactionsParEtatTransaction();
            list_statusTransaction[i] = lista.get(i).getEtatTransaction();

            yVals1.add(new BarEntry(i,(float) list_nbreTrnsPerStatus[i]));

            switch (list_statusTransaction[i]){

                case "Transaction non autorisée":
                    list_statusTransaction_Abriviator[i] = "T.N.Autorisée";
                    break;
                case "Transaction autorisée":
                    list_statusTransaction_Abriviator[i] = "T.Autorisée";
                    break;
                case "Transaction annulée":
                    list_statusTransaction_Abriviator[i] = "T.Annulée";
                    break;
                case "Transaction non aboutie":
                    list_statusTransaction_Abriviator[i] = "T.N.Aboutie";
            }

            xAxis_labelsOfTranStatus.add(list_statusTransaction_Abriviator[i]);

            for (int k=0;k < xAxis_labelsOfTranStatus.size();k++){

                XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTypeface(mTfLight);
                xAxis.setDrawGridLines(false);
                xAxis.setGranularity(1f); // only intervals of 1 day
                xAxis.setLabelCount(7);
                //final int finalK = k;
                xAxis.setValueFormatter(new IndexAxisValueFormatter(list_statusTransaction_Abriviator));
            }

            if (mChart.getData() != null &&
                    mChart.getData().getDataSetCount() > 0) {

                for (int j=0 ; j< mChart.getData().getDataSetCount() ; j++){
                    list_BarDataSet[j] = (BarDataSet) mChart.getData().getDataSetByIndex(j);
                    list_BarDataSet[j].setValues(yVals1);

                }

                mChart.getData().notifyDataChanged();
                mChart.notifyDataSetChanged();

            }else {
                list_BarDataSet[i] = new BarDataSet(yVals1, "Transactions Status");

                //max = Math.max(max,list_nbTrnsPerStat_ToReturn[i]);
                 list_BarDataSet[i].setDrawIcons(false);

              //set1.setColors(ColorTemplate.MATERIAL_COLORS);

            /*int startColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor = ContextCompat.getColor(this, android.R.color.holo_blue_bright);
            set1.setGradientColor(startColor, endColor);*/
           /*
                int startColor1 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_orange_light);
                int startColor2 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_blue_light);
                int startColor3 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_orange_light);
                int startColor4 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_green_light);
                int startColor5 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_red_light);
                int endColor1 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_blue_dark);
                int endColor2 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_purple);
                int endColor3 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_green_dark);
                int endColor4 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_red_dark);
                int endColor5 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_orange_dark);
            */
            /*
            List<GradientColor> gradientColors = new ArrayList<>();
            gradientColors.add(new GradientColor(startColor1, endColor1));
            gradientColors.add(new GradientColor(startColor2, endColor2));
            gradientColors.add(new GradientColor(startColor3, endColor3));
            gradientColors.add(new GradientColor(startColor4, endColor4));
            gradientColors.add(new GradientColor(startColor5, endColor5));

            set1.setGradientColors(gradientColors);
            */
                list_BarDataSet[i].setColors(ColorTemplate.COLORFUL_COLORS);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(list_BarDataSet[i]);

                BarData data = new BarData(dataSets);
                data.setValueTextSize(10f);
                data.setValueTypeface(mTfLight);
                data.setBarWidth(0.9f);

                mChart.setData(data);
            }

        }

    }

    private void setData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = (int) start; i < start + count + 1; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);

            if (Math.random() * 100 < 25) {
                yVals1.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                yVals1.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);

            set1.setValues(yVals1);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The year 2017");

            set1.setDrawIcons(false);
//            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            /*int startColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor = ContextCompat.getColor(this, android.R.color.holo_blue_bright);
            set1.setGradientColor(startColor, endColor);*/

            int startColor1 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_green_dark);
            int endColor4 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_red_dark);
            int endColor5 = ContextCompat.getColor(BarChartTransactionStatusFragment.this.getContext(), android.R.color.holo_orange_dark);

            /*
            List<GradientColor> gradientColors = new ArrayList<>();
            gradientColors.add(new GradientColor(startColor1, endColor1));
            gradientColors.add(new GradientColor(startColor2, endColor2));
            gradientColors.add(new GradientColor(startColor3, endColor3));
            gradientColors.add(new GradientColor(startColor4, endColor4));
            gradientColors.add(new GradientColor(startColor5, endColor5));

            set1.setGradientColors(gradientColors);
            */

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);

            mChart.setData(data);
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

    // SeekBar Overrited Methods
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

    private void GetTransactionsStatusValues(){
        new TransactionsStatusTaks().execute();
    }

    // Class AsynTask
    // class TransactionsStatusTaks
    class TransactionsStatusTaks  extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                list_TransactionsStatusValues = api.GetOnlyTransactionsStatus().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String string) {

            sizeOfListTransactionsStatus = list_TransactionsStatusValues.size();
            //int size2 = list_TransactionsStatusValues.size();

            list_nbTrnsPerStat_ToReturn = new int[sizeOfListTransactionsStatus];
            list_etatTrns_ToReturn = new String[sizeOfListTransactionsStatus];

            // Println to verify in the console ,if i am getting the values or not
            System.err.println("size Of List Transactions Status Bar Chart is : " +sizeOfListTransactionsStatus);

            int max = 0,maxOfTheMax = 0;

            for (int i = 0; i < sizeOfListTransactionsStatus; i++) {

                list_nbTrnsPerStat_ToReturn[i] = list_TransactionsStatusValues.get(i).getNbreTransactionsParEtatTransaction();
                list_etatTrns_ToReturn[i] = list_TransactionsStatusValues.get(i).getEtatTransaction();

                totalTransactions_ToReturn += list_nbTrnsPerStat_ToReturn[i];

                max = Math.max(max,list_nbTrnsPerStat_ToReturn[i]);
                maxOfTheMax = max + 5;

                setBarChartData(sizeOfListTransactionsStatus,maxOfTheMax,list_nbTrnsPerStat_ToReturn,list_etatTrns_ToReturn,list_TransactionsStatusValues);
                //setPieChartData(sizeOfListTransactionsStatus, list_nbTrnsPerStat_ToReturn , list_etatTrns_ToReturn, totalTransactions_ToReturn,list_TransactionsStatusValues);

                System.err.println(" Transaction Status Bar Chart To Return :" + list_etatTrns_ToReturn[i] + " => Value Nbr StatusPerTrans Bar Chart To Return: " + list_nbTrnsPerStat_ToReturn[i]);
            }
            //setPieChartData(sizeOfListTransactionsStatus, list_nbTrnsPerStat_ToReturn , list_etatTrns_ToReturn, getTotal);

            System.err.println("Total Transactions Bar Chart To Return = " + totalTransactions_ToReturn);
            System.err.println("Max Value Of Transactions Bar Chart To Return = " + max);
            System.err.println("Max Of The Max Value Difference by 5 Of Transactions Bar Chart To Return = " + maxOfTheMax);

            super.onPostExecute(string);
        }
    }
}
