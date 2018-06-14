package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_TransactionStatusBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PieChartTransactionStatusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PieChartTransactionStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PieChartTransactionStatusFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private PieChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;
    private Typeface mTfRegular;
    private Typeface mTfLight;

    private SampleApi api = SampleApiFactory.getInstance();
    private List<gw_TransactionStatusBindingModel> list_TransactionsStatusValues;

    // Values Class Asyntask onPostExecute() Method
    private int[] list_nbTrnsPerStat_ToReturn;
    private String[] list_etatTrns_ToReturn;
    private int totalTransactions_ToReturn = 0,sizeOfListTransactionsStatus =0;

    public PieChartTransactionStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PieChartTransactionStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PieChartTransactionStatusFragment newInstance(String param1, String param2) {
        PieChartTransactionStatusFragment fragment = new PieChartTransactionStatusFragment();
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
        View view = inflater.inflate(R.layout.fragment_pie_chart_transaction_status, container, false);

        //tvX = view.findViewById(R.id.tvXMaxTrnsStat);
        //mSeekBarX = view.findViewById(R.id.seekBarTrnsStat1);
        tvY = view.findViewById(R.id.tvYMaxTrnsStat);
        mSeekBarY = view.findViewById(R.id.seekBarTrnsStat2);
        mChart = view.findViewById(R.id.PiechartTrnsStat);

        mTfRegular = Typeface.createFromAsset(PieChartTransactionStatusFragment.this.getContext().getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(PieChartTransactionStatusFragment.this.getContext().getAssets(), "OpenSans-Light.ttf");

        GetTransactionsStatusValues();
        afterViewsConfig();

        return view;
    }

    private void afterViewsConfig(){

        // Seek Bars
        //mSeekBarX.setProgress(4);
        mSeekBarY.setProgress(10);

        // Pie Chart
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, -80);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterTextTypeface(mTfLight);
        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        //setData(4, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        //mSeekBarX.setOnSeekBarChangeListener(this);
        mSeekBarY.setOnSeekBarChangeListener(this);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);
    }

    private void setPieChartData(int countSize, int[] list_nbreTrnsPerStatus,String[] list_statusTransaction,int totalOfTransactions,List<gw_TransactionStatusBindingModel> lista ){

       // float mult = range;
        ArrayList<PieEntry> entries = new ArrayList<>();

        list_nbreTrnsPerStatus = new int[countSize];
        list_statusTransaction = new String[countSize];

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < countSize ; i++) {
            list_nbreTrnsPerStatus[i] = lista.get(i).getNbreTransactionsParEtatTransaction();
            list_statusTransaction[i] = lista.get(i).getEtatTransaction();

            entries.add(new PieEntry((float) ((list_nbreTrnsPerStatus[i] * 100) / totalOfTransactions) ,list_statusTransaction[i], getResources().getDrawable(R.drawable.star)));

        }

        PieDataSet dataSet = new PieDataSet(entries, "Transactions Status");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        // MPAndroidChart\ndeveloped by Philipp Jahoda
        SpannableString s = new SpannableString("MssPieChart\nTransactions Status");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 11, 0);// 11 = MssPieChart.length
        s.setSpan(new StyleSpan(Typeface.NORMAL), 11, s.length() - 20, 0);// 20 = Transactions Status.length
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 11, s.length() - 20, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 11, s.length() - 20, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 20, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 20, s.length(), 0);
        return s;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pie, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleIcons: {
                for (IDataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawIcons(!set.isDrawIconsEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHole: {
                if (mChart.isDrawHoleEnabled())
                    mChart.setDrawHoleEnabled(false);
                else
                    mChart.setDrawHoleEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionDrawCenter: {
                if (mChart.isDrawCenterTextEnabled())
                    mChart.setDrawCenterText(false);
                else
                    mChart.setDrawCenterText(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleXVals: {

                mChart.setDrawEntryLabels(!mChart.isDrawEntryLabelsEnabled());
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                // mChart.saveToGallery("title"+System.currentTimeMillis());
                mChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            }
            case R.id.actionTogglePercent:
                mChart.setUsePercentValues(!mChart.isUsePercentValuesEnabled());
                mChart.invalidate();
                break;
            case R.id.animateX: {
                mChart.animateX(1400);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1400);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(1400, 1400);
                break;
            }
            case R.id.actionToggleSpin: {
                mChart.spin(1000, mChart.getRotationAngle(), mChart.getRotationAngle() + 360, Easing.EasingOption.EaseInCubic);
                break;
            }
        }
        return true;
    }

    private void getListViewMenuFrag(){
        Fragment frag = new ReportingStatisticsFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentReportPieChartTrnStat, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

        //ft.hide(this);
        //ft.show(frag);
        //ft.commit();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
           // getListViewMenuFrag();
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
        } */
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
    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        //tvX.setText("" + (mSeekBarX.getProgress()));
        tvY.setText("" + (mSeekBarY.getProgress()));

        //setData(mSeekBarX.getProgress(), mSeekBarY.getProgress());
        //setPieChartData(mSeekBarX.getProgress(),null,null, mSeekBarY.getProgress(),null);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    private void GetTransactionsStatusValues(){
        new TransactionsStatusTaks().execute();
    }

    // Class AsynTask
    // class TransactionsStatusTaks
    class TransactionsStatusTaks  extends AsyncTask<String, String, String>{

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
            System.err.println("size Of List Pie Chart Transactions Status is : " +sizeOfListTransactionsStatus);

                for (int i = 0; i < sizeOfListTransactionsStatus; i++) {

                    list_nbTrnsPerStat_ToReturn[i] = list_TransactionsStatusValues.get(i).getNbreTransactionsParEtatTransaction();
                    list_etatTrns_ToReturn[i] = list_TransactionsStatusValues.get(i).getEtatTransaction();
                    //list_nbTrnsPerStat_ToReturn[i] = model.getNbreTransactionsParEtatTransaction();
                    //list_etatTrns_ToReturn[i] = model.getEtatTransaction();

                    totalTransactions_ToReturn += list_nbTrnsPerStat_ToReturn[i];

                    setPieChartData(sizeOfListTransactionsStatus, list_nbTrnsPerStat_ToReturn , list_etatTrns_ToReturn, totalTransactions_ToReturn,list_TransactionsStatusValues);

                    System.err.println(" Transaction Status Pie Chart To Return :" + list_etatTrns_ToReturn[i] + " => Value Nbr StatusPerTrans Pie Chart To Return: " + list_nbTrnsPerStat_ToReturn[i]);
                }
               //setPieChartData(sizeOfListTransactionsStatus, list_nbTrnsPerStat_ToReturn , list_etatTrns_ToReturn, getTotal);

               System.err.println("Total Transactions Pie Chart To Return = " + totalTransactions_ToReturn);


            super.onPostExecute(string);
        }
    }

}
