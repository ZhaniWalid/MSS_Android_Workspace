package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.annotation.SuppressLint;
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

import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsFormatters.CardBinAxisValueFormatter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsFormatters.MyAxisValueFormatter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsFormatters.XYMarkerView;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_BinCardBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;
import com.github.mikephil.charting.charts.HorizontalBarChart;
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
 * {@link HorizontalBarChartCardBinLabelsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HorizontalBarChartCardBinLabelsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HorizontalBarChartCardBinLabelsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private HorizontalBarChart mChart;
    private SeekBar mSeekBarY;
    private TextView tvY;
    private Typeface mTfLight;

    private SampleApi api = SampleApiFactory.getInstance();
    private List<gw_BinCardBindingModel> list_BinCardLabelsValues;

    // Values Class Asyntask onPostExecute() Method
    private int[] list_nbBinCard_ToReturn;
    private String[] list_binCardLabels_ToReturn;
    private int totalBinCards_ToReturn = 0,sizeOfListBinCards =0;

    private IAxisValueFormatter xAxisCardBinFormatter;

    public HorizontalBarChartCardBinLabelsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HorizontalBarChartCardBinLabelsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HorizontalBarChartCardBinLabelsFragment newInstance(String param1, String param2) {
        HorizontalBarChartCardBinLabelsFragment fragment = new HorizontalBarChartCardBinLabelsFragment();
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
        View view = inflater.inflate(R.layout.fragment_horizontal_bar_chart_card_bin_labels, container, false);

        tvY = view.findViewById(R.id.tvYMaxHorizBarchartCardBin);
        mSeekBarY = view.findViewById(R.id.seekBarHorizBarchartCardBin2);
        mChart = view.findViewById(R.id.HorizBarchartCardBin);

        mTfLight = Typeface.createFromAsset(HorizontalBarChartCardBinLabelsFragment.this.getContext().getAssets(), "OpenSans-Light.ttf");

        GetCardBinLablelsValues();
        afterViewsConfig();

        return view;
    }

    private void afterViewsConfig(){

        mChart.setOnChartValueSelectedListener(this);
        // mChart.setHighlightEnabled(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        mChart.setDrawGridBackground(false);

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(mTfLight);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = mChart.getAxisLeft();
        yl.setTypeface(mTfLight);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = mChart.getAxisRight();
        yr.setTypeface(mTfLight);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        //setData(12, 50);
        mChart.setFitBars(false);
        mChart.animateY(2000);

        // setting data
        mSeekBarY.setProgress(50);
        //mSeekBarX.setProgress(12);

        mSeekBarY.setOnSeekBarChangeListener(this);
        //mSeekBarX.setOnSeekBarChangeListener(this);

        /*
        xAxisCardBinFormatter = new MyAxisValueFormatter();

        XYMarkerView mv = new XYMarkerView(HorizontalBarChartCardBinLabelsFragment.this.getContext(), xAxisCardBinFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv);
        */

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
    }

    private void setHorizontalBarChartData(int countSize, int[] list_nbrePerCardBin,String[] list_cardBinLabels,int totalOfCardBin,List<gw_BinCardBindingModel> lista ){

        float barWidth = 0.9f;
        float spaceForBar = 10f;

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        list_nbrePerCardBin = new int[countSize];
        list_cardBinLabels = new String[countSize];
        BarDataSet[] list_BarDataSet = new BarDataSet[countSize];
        String[] list_BinCardLabels_Abriviator = new String[countSize];

        final List<String> xAxis_labelsOfBinCards = new ArrayList<>();

        for (int i = 0; i < countSize; i++) {

            //float val = (float) (Math.random() * range);
            list_nbrePerCardBin[i] = lista.get(i).getNbreBinCards();
            list_cardBinLabels[i] = lista.get(i).getBinCardLabel();

            //yVals1.add(new BarEntry(i * spaceForBar, val, getResources().getDrawable(R.drawable.star)));
            yVals1.add(new BarEntry(i,(float) list_nbrePerCardBin[i]));


                xAxisCardBinFormatter = new CardBinAxisValueFormatter(list_cardBinLabels,sizeOfListBinCards);

                XYMarkerView mv = new XYMarkerView(HorizontalBarChartCardBinLabelsFragment.this.getContext(), xAxisCardBinFormatter);
                mv.setChartView(mChart); // For bounds control
                mChart.setMarker(mv);



            switch (list_cardBinLabels[i]){
                case "Visa International":
                    list_BinCardLabels_Abriviator[i] = "V.I";
                    break;
                case "Visa National":
                    list_BinCardLabels_Abriviator[i] = "V.N";
                    break;
                case "Visa Gold/Premier International":
                    list_BinCardLabels_Abriviator[i] = "V.G.P.I";
                    break;
                case "MCD Gold International":
                    list_BinCardLabels_Abriviator[i] = "MCD.G.I";
                    break;
                case "MCD National Débit Non Embossé":
                    list_BinCardLabels_Abriviator[i] = "MCD.N.D.N.E";
                    break;
            }

            xAxis_labelsOfBinCards.add(list_BinCardLabels_Abriviator[i]);

         /*
            for (int k=0 ; k < xAxis_labelsOfBinCards.size() ; k++){

                XAxis xl = mChart.getXAxis();
                xl.setPosition(XAxis.XAxisPosition.BOTTOM);
                xl.setTypeface(mTfLight);
                xl.setDrawAxisLine(false);
                xl.setDrawGridLines(true);
                xl.setGranularity(3f);
                //xl.setLabelCount(7);
                xl.setValueFormatter(new IndexAxisValueFormatter(xAxis_labelsOfBinCards));

            }
         */

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

                list_BarDataSet[i] = new BarDataSet(yVals1, "Card Bin");
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

    private void setData(int count, float range) {

        float barWidth = 9f;
        float spaceForBar = 10f;
        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            yVals1.add(new BarEntry(i * spaceForBar, val,
                    getResources().getDrawable(R.drawable.star)));
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "DataSet 1");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(barWidth);
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
     /*   if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }   */
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

    //seekBar Overrited methods
    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        tvY.setText("" + (mSeekBarY.getProgress()));

        //setData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());
        mChart.setFitBars(true);
        mChart.invalidate();
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

        MPPointF position = mChart.getPosition(e, mChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency());

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }

    private void GetCardBinLablelsValues(){
        new CardBinTask().execute();
    }

    // Class AsynTask
    // class CardBinTask
    class CardBinTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                list_BinCardLabelsValues = api.GetOnlyBinCardsLabels().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String string) {

            sizeOfListBinCards = list_BinCardLabelsValues.size();

            list_binCardLabels_ToReturn = new String[sizeOfListBinCards];
            list_nbBinCard_ToReturn = new int[sizeOfListBinCards];

            // Println to verify in the console ,if i am getting the values or not
            System.err.println("size Of List Horizontal Bar Chart Bin Card is : " +sizeOfListBinCards);

            for (int i = 0; i < sizeOfListBinCards; i++) {

                list_nbBinCard_ToReturn[i] = list_BinCardLabelsValues.get(i).getNbreBinCards();
                list_binCardLabels_ToReturn[i] = list_BinCardLabelsValues.get(i).getBinCardLabel();
                //list_nbTrnsPerStat_ToReturn[i] = model.getNbreTransactionsParEtatTransaction();
                //list_etatTrns_ToReturn[i] = model.getEtatTransaction();

                totalBinCards_ToReturn += list_nbBinCard_ToReturn[i];

                setHorizontalBarChartData(sizeOfListBinCards, list_nbBinCard_ToReturn , list_binCardLabels_ToReturn, totalBinCards_ToReturn,list_BinCardLabelsValues);

                System.err.println(" Bin Card Horizontal Bar Chart To Return : " + list_binCardLabels_ToReturn[i] + " => Value NbrPerBinCard Horizontal Bar Chart To Return: " + list_nbBinCard_ToReturn[i]);
            }
            //setPieChartData(sizeOfListTransactionsStatus, list_nbTrnsPerStat_ToReturn , list_etatTrns_ToReturn, getTotal);

            System.err.println("Total Bin Card Horizontal Bar Chart To Return = " + totalBinCards_ToReturn);

            super.onPostExecute(string);
        }
    }
}
