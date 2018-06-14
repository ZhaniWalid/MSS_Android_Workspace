package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsFormatters.MyAxisValueFormatter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsFormatters.XYMarkerView;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_BankOfPayementBindingModel;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BarChartBankNamesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BarChartBankNamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarChartBankNamesFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private BarChart mChart;
    private SeekBar mSeekBarY;
    private TextView tvY;

    private Typeface mTfLight;

    private SampleApi api = SampleApiFactory.getInstance();
    private List<gw_BankOfPayementBindingModel> list_BankNamesValues;

    // Values Class Asyntask onPostExecute() Method
    private int[] list_nbBankNames_ToReturn;
    private String[] list_BankNames_ToReturn;
    private int totalBankNames_ToReturn = 0,sizeOfListBankNames =0;

    private IAxisValueFormatter xAxisBankNamesFormatter;


    public BarChartBankNamesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BarChartBankNamesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BarChartBankNamesFragment newInstance(String param1, String param2) {
        BarChartBankNamesFragment fragment = new BarChartBankNamesFragment();
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
        View view = inflater.inflate(R.layout.fragment_bar_chart_bank_names, container, false);

        tvY = view.findViewById(R.id.tvYMaxbubbleChartBankName);
        mSeekBarY = view.findViewById(R.id.seekBarbubbleChartBankName);
        mChart = view.findViewById(R.id.bubbleChartBankName);

        mTfLight   = Typeface.createFromAsset(BarChartBankNamesFragment.this.getContext().getAssets(), "OpenSans-Light.ttf");

        GetBankNamesValues();
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
        xAxisBankNamesFormatter = new MyAxisValueFormatter();

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

        XYMarkerView mv = new XYMarkerView(BarChartBankNamesFragment.this.getContext(), xAxisBankNamesFormatter);
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

    /*
    private void afterViewsConfigBubbleChart(){

        mSeekBarY.setOnSeekBarChangeListener(this);

        mChart.getDescription().setEnabled(false);

        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawGridBackground(false);

        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        mChart.setMaxVisibleValueCount(200);
        mChart.setPinchZoom(true);

        //mSeekBarX.setProgress(10);
        mSeekBarY.setProgress(50);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTypeface(mTfLight);

        YAxis yl = mChart.getAxisLeft();
        yl.setTypeface(mTfLight);
        yl.setSpaceTop(30f);
        yl.setSpaceBottom(30f);
        yl.setDrawZeroLine(false);

        mChart.getAxisRight().setEnabled(false);

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(mTfLight);
    }
    */

    private void setBarChartData(int countSize, int[] list_nbreBankPerName,String[] list_bankNames,int totalOfCardBin,List<gw_BankOfPayementBindingModel> lista){

        float barWidth = 0.9f;

        ArrayList<BarEntry> yVals = new ArrayList<>();

        list_nbreBankPerName = new int[countSize];
        list_bankNames = new String[countSize];

        //BubbleDataSet[] list_BubbleDataSet = new BubbleDataSet[countSize];
        BarDataSet[] list_BarDataSet = new BarDataSet[countSize];

        String[] list_bankNames_Abriviator = new String[countSize];
        final List<String> xAxis_bankNames = new ArrayList<>();

        for (int i = 0; i < countSize; i++) {

            //float val = (float) (Math.random() * range);
            //float size = (float) (Math.random() * range);
            list_nbreBankPerName[i] = lista.get(i).getNbBankPerNameBank();
            list_bankNames[i] = lista.get(i).getBankName();

            //int countBubble = countSize + 5;

            yVals.add(new BarEntry(i,(float) list_nbreBankPerName[i],getResources().getDrawable(R.drawable.star)));

            switch (list_bankNames[i]){

                case "Amen Banque":
                    list_bankNames_Abriviator[i] = "AB";
                    break;
                case "ABC":
                    list_bankNames_Abriviator[i] = "ABC";
                    break;
                case "AlBaraka":
                    list_bankNames_Abriviator[i] = "AlBaraka";
                    break;
                case "Arab Tunisian Bank":
                    list_bankNames_Abriviator[i] = "ATB";
                    break;
                case "Attijari bank":
                    list_bankNames_Abriviator[i] = "ATTIJARI";
                    break;
                case "Banque Centrale de Tunisie":
                    list_bankNames_Abriviator[i] = "BCT";
                    break;
                case "BFT":
                    list_bankNames_Abriviator[i] = "BFT";
                    break;
                case "Banque de l'Habitat":
                    list_bankNames_Abriviator[i] = "BH";
                    break;
                case "BIAT":
                    list_bankNames_Abriviator[i] = "BIAT";
                    break;
                case "Banque Nationale Agricole":
                    list_bankNames_Abriviator[i] = "BNA";
                    break;
                case "BT":
                    list_bankNames_Abriviator[i] = "BT";
                    break;
                case "BTE":
                    list_bankNames_Abriviator[i] = "BTE";
                    break;
                case "Banque Tuniso-Koweitienne":
                    list_bankNames_Abriviator[i] = "BTK";
                    break;
                case "BTL":
                    list_bankNames_Abriviator[i] = "BTL";
                    break;
                case "NAIB":
                    list_bankNames_Abriviator[i] = "NAIB";
                    break;
                case "ONP":
                    list_bankNames_Abriviator[i] = "ONP";
                    break;
                case "Société tunisienne de banque":
                    list_bankNames_Abriviator[i] = "STB";
                    break;
                case "Stusid":
                    list_bankNames_Abriviator[i] = "Stusid";
                    break;
                case "TIB":
                    list_bankNames_Abriviator[i] = "TIB";
                    break;
                case "TQB":
                    list_bankNames_Abriviator[i] = "TQB";
                    break;
                case "Union bancaire pour le commerce et l'industrie":
                    list_bankNames_Abriviator[i] = "UBCI";
                    break;
                case "Union internationale de banques":
                    list_bankNames_Abriviator[i] = "UIB";
                    break;
                case "Zitouna":
                    list_bankNames_Abriviator[i] = "Zitouna";
                    break;
            }

            xAxis_bankNames.add(list_bankNames_Abriviator[i]);

            for (int k=0 ; k < xAxis_bankNames.size(); k++){

                XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTypeface(mTfLight);
                xAxis.setDrawGridLines(false);
                xAxis.setGranularity(1f); // only intervals of 1 day
                xAxis.setLabelCount(7);
                xAxis.setValueFormatter(new IndexAxisValueFormatter(list_bankNames_Abriviator));
            }


            if (mChart.getData() != null &&
                    mChart.getData().getDataSetCount() > 0) {

                //set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
                //set1.setValues(yVals1);

                for (int j=0 ; j< mChart.getData().getDataSetCount() ; j++){
                    list_BarDataSet[j] = (BarDataSet) mChart.getData().getDataSetByIndex(j);
                    list_BarDataSet[j].setValues(yVals);
                }
                mChart.getData().notifyDataChanged();
                mChart.notifyDataSetChanged();

            } else {
                //set1 = new BarDataSet(yVals1, "DataSet 1");
                //set1.setDrawIcons(false);

                list_BarDataSet[i] = new BarDataSet(yVals, "Bank Used For Payment");
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

            /*
            list_BubbleDataSet[i] = new BubbleDataSet(yVals, "Bank Used For Payment");
            list_BubbleDataSet[i].setDrawIcons(false);
            list_BubbleDataSet[i].setColors(ColorTemplate.COLORFUL_COLORS);

            ArrayList<IBubbleDataSet> dataSets = new ArrayList<>();
            dataSets.add(list_BubbleDataSet[i]);

           /// create a data object with the datasets
            BubbleData data = new BubbleData(dataSets);
            data.setDrawValues(false);
            data.setValueTypeface(mTfLight);
            data.setValueTextSize(8f);
            data.setValueTextColor(Color.WHITE);
            data.setHighlightCircleWidth(1.5f);

            mChart.setData(data);
            mChart.invalidate();
            */
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

    // seekBar Overritted methods
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void GetBankNamesValues(){
      new BankNamesOfPaymentTask().execute();
    }

    // class AsynkTask
    // class BankNamesOfPaymentTask
    class BankNamesOfPaymentTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                list_BankNamesValues = api.GetOnlyBankNamesOfPayment().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String string) {

            sizeOfListBankNames = list_BankNamesValues.size();

            list_BankNames_ToReturn = new String[sizeOfListBankNames];
            list_nbBankNames_ToReturn = new int[sizeOfListBankNames];

            // Println to verify in the console ,if i am getting the values or not
            System.err.println("size Of List Pie Chart Bank Names is : " +sizeOfListBankNames);

            for (int i = 0; i < sizeOfListBankNames; i++) {

                list_nbBankNames_ToReturn[i] = list_BankNamesValues.get(i).getNbBankPerNameBank();
                list_BankNames_ToReturn[i] = list_BankNamesValues.get(i).getBankName();
                //list_nbTrnsPerStat_ToReturn[i] = model.getNbreTransactionsParEtatTransaction();
                //list_etatTrns_ToReturn[i] = model.getEtatTransaction();

                totalBankNames_ToReturn += list_nbBankNames_ToReturn[i];

                setBarChartData(sizeOfListBankNames, list_nbBankNames_ToReturn , list_BankNames_ToReturn, totalBankNames_ToReturn,list_BankNamesValues);

                System.err.println(" Bank Name Pie Chart To Return : " + list_BankNames_ToReturn[i] + " => Value NbrBankPerName Pie Chart To Return: " + list_nbBankNames_ToReturn[i]);
            }
            //setPieChartData(sizeOfListTransactionsStatus, list_nbTrnsPerStat_ToReturn , list_etatTrns_ToReturn, getTotal);

            System.err.println("Total Number Of Bank Names Pie Chart To Return = " + totalBankNames_ToReturn);

            super.onPostExecute(string);
        }
    }

}
