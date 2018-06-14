package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters.ChartDataAdapter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsItems.BarChartItem;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsItems.ChartItem;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsItems.LineChartItem;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsItems.PieChartItem;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_TransactionStatusBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MultiChartsTransactionStatusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MultiChartsTransactionStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultiChartsTransactionStatusFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listViewMultiCharts;
    private ArrayList<ChartItem> listChartsItems;

    private SampleApi api = SampleApiFactory.getInstance();
    List<gw_TransactionStatusBindingModel> list_TransactionsStatusValues;
    private static int sizeOfListTransactionsStatus,mySize;
    String sizeOfListTransactionsStatus_String = "";

    private ArrayList<PieEntry> PieEntries;
    PieData myPieData;

    // Values Class Asyntask onPostExecute() Method
    private int[] list_nbTrnsPerStat_ToReturn;
    private String[] list_etatTrns_ToReturn;
    private int totalTransactions_ToReturn = 0;

    // values to get data from Class Asyntask onPostExecute() Method
    private int[] list_nbTrnsPerStat;
    private String[] list_etatTrns;
    private int totalTransactions;

    EditText editTextGetNbrTrnsc;

    public MultiChartsTransactionStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MultiChartsTransactionStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MultiChartsTransactionStatusFragment newInstance(String param1, String param2) {
        MultiChartsTransactionStatusFragment fragment = new MultiChartsTransactionStatusFragment();
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
        View view =  inflater.inflate(R.layout.fragment_multi_charts, container, false);

        editTextGetNbrTrnsc = view.findViewById(R.id.edtTxtNbTrnsPerStat);
        listViewMultiCharts = view.findViewById(R.id.listViewMultiCharts);
        listChartsItems = new ArrayList<>();

        list_TransactionsStatusValues = new ArrayList<>();

        // 30 items
        for (int i = 0; i < 3; i++) {

            if(i % 3 == 0) {
                listChartsItems.add(new LineChartItem(generateDataLine(i + 1), MultiChartsTransactionStatusFragment.this.getContext() ));
            } else if(i % 3 == 1) {
                listChartsItems.add(new BarChartItem(generateDataBar(i + 1), MultiChartsTransactionStatusFragment.this.getContext() ));
            } else if(i % 3 == 2) {
                listChartsItems.add(new PieChartItem(generateDataPie(i + 1), MultiChartsTransactionStatusFragment.this.getContext() ));
            }
        }

        ChartDataAdapter cda = new ChartDataAdapter(MultiChartsTransactionStatusFragment.this.getContext(), listChartsItems);
        listViewMultiCharts.setAdapter(cda);

        GetTransactionsStatusValues();

        return view;
    }

    private void getReportingStatisticsFrag(){
        Fragment frag = new ReportingStatisticsFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentMultiReportsContainer, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
        ft.addToBackStack(null);
        ft.commit();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
      /*  if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        } */

        getReportingStatisticsFrag();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    /*
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    */
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

    // ChartsData Generator

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> e1 = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            e1.add(new Entry(i, (int) (Math.random() * 65) + 40));
        }

        LineDataSet d1 = new LineDataSet(e1, "New DataSet " + cnt + ", (1)");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        ArrayList<Entry> e2 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            e2.add(new Entry(i, e1.get(i).getY() - 30));
        }

        LineDataSet d2 = new LineDataSet(e2, "New DataSet " + cnt + ", (2)");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
        sets.add(d1);
        sets.add(d2);

        LineData cd = new LineData(sets);
        return cd;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateDataBar(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i, (int) (Math.random() * 70) + 30));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        return cd;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private PieData generateDataPie(int cnt) {
        System.err.println("size Of Chart : "+mySize);

        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            entries.add(new PieEntry((float) ((Math.random() * 70) + 30), "Quarter " + (i+1)));
        }

        PieDataSet d = new PieDataSet(entries, "Transaction Status");

        // space between slices
        d.setSliceSpace(6f);
        d.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData cd = new PieData(d);
        return cd;
    }

    private void GetTransactionsStatusValues(){
        new TransactionsStatusTaks().execute();
    }

    // Class AsynTask
    // class TransactionsStatusTaks
    class TransactionsStatusTaks  extends AsyncTask<String, String, PieData> {

        @Override
        protected PieData doInBackground(String... params) {

            try {
                list_TransactionsStatusValues = api.GetOnlyTransactionsStatus().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        /*
        @Override
        protected PieData doInBackground(PieData... pieDatas) {

            //pieDatas = new PieData[1];

            try {
                list_TransactionsStatusValues = api.GetOnlyTransactionsStatus().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            sizeOfListTransactionsStatus = list_TransactionsStatusValues.size();
            //int size2 = list_TransactionsStatusValues.size();

            list_nbTrnsPerStat_ToReturn = new int[sizeOfListTransactionsStatus];
            list_etatTrns_ToReturn = new String[sizeOfListTransactionsStatus];

            PieEntries = new ArrayList<>();
            // Println to verify in the console ,if i am getting the values or not
            System.err.println("size Of List Transactions Status is : " +sizeOfListTransactionsStatus);

            for(int i=0 ; i<sizeOfListTransactionsStatus;i++){

                list_nbTrnsPerStat_ToReturn[i] = list_TransactionsStatusValues.get(i).getNbreTransactionsParEtatTransaction();
                list_etatTrns_ToReturn[i] = list_TransactionsStatusValues.get(i).getEtatTransaction();

                totalTransactions_ToReturn += list_nbTrnsPerStat_ToReturn[i] ;

                PieEntries.add(new PieEntry((float) ((list_nbTrnsPerStat_ToReturn[i] * 100) / totalTransactions_ToReturn) ,list_etatTrns_ToReturn[i]));

                System.err.println(" Transaction Status To Return :"+list_etatTrns_ToReturn[i]+" => Value Nbr StatusPerTrans To Return: "+list_nbTrnsPerStat_ToReturn[i]);
            }
            System.err.println("Total Transactions To Return = "+totalTransactions_ToReturn);

            PieDataSet d = new PieDataSet(PieEntries, "Transaction Status");

            // space between slices
            d.setSliceSpace(6f);
            d.setColors(ColorTemplate.COLORFUL_COLORS);

            PieData pieData = new PieData(d);

            return pieData;
        }
       */

        @Override
        protected void onPostExecute(PieData pieData) {

            sizeOfListTransactionsStatus = list_TransactionsStatusValues.size();
            //int size2 = list_TransactionsStatusValues.size();

            list_nbTrnsPerStat_ToReturn = new int[sizeOfListTransactionsStatus];
            list_etatTrns_ToReturn = new String[sizeOfListTransactionsStatus];

            PieEntries = new ArrayList<>();
            // Println to verify in the console ,if i am getting the values or not
            System.err.println("size Of List Transactions Status is : " +sizeOfListTransactionsStatus);

            for(int i=0 ; i<sizeOfListTransactionsStatus;i++){

                list_nbTrnsPerStat_ToReturn[i] = list_TransactionsStatusValues.get(i).getNbreTransactionsParEtatTransaction();
                list_etatTrns_ToReturn[i] = list_TransactionsStatusValues.get(i).getEtatTransaction();

                totalTransactions_ToReturn += list_nbTrnsPerStat_ToReturn[i] ;

                PieEntries.add(new PieEntry((float) ((list_nbTrnsPerStat_ToReturn[i] * 100) / totalTransactions_ToReturn) ,list_etatTrns_ToReturn[i]));

                System.err.println(" Transaction Status To Return :"+list_etatTrns_ToReturn[i]+" => Value Nbr StatusPerTrans To Return: "+list_nbTrnsPerStat_ToReturn[i]);
            }
            System.err.println("Total Transactions To Return = "+totalTransactions_ToReturn);

            editTextGetNbrTrnsc.setText(String.valueOf(sizeOfListTransactionsStatus));

            super.onPostExecute(myPieData);

            mySize = Integer.parseInt(editTextGetNbrTrnsc.getText().toString());

        }

/*
        @Override
        protected void onPostExecute(String string) {

            sizeOfListTransactionsStatus = list_TransactionsStatusValues.size();
            int size2 = list_TransactionsStatusValues.size();

            list_nbTrnsPerStat_ToReturn = new int[sizeOfListTransactionsStatus];
            list_etatTrns_ToReturn = new String[sizeOfListTransactionsStatus];

            PieEntries = new ArrayList<>();
            // Println to verify in the console ,if i am getting the values or not
            System.err.println("size Of List Transactions Status is : " +sizeOfListTransactionsStatus);

            for(int i=0 ; i<sizeOfListTransactionsStatus;i++){

                list_nbTrnsPerStat_ToReturn[i] = list_TransactionsStatusValues.get(i).getNbreTransactionsParEtatTransaction();
                list_etatTrns_ToReturn[i] = list_TransactionsStatusValues.get(i).getEtatTransaction();

                totalTransactions_ToReturn += list_nbTrnsPerStat_ToReturn[i] ;

                PieEntries.add(new PieEntry((float) ((list_nbTrnsPerStat_ToReturn[i] * 100) / totalTransactions_ToReturn) ,list_etatTrns_ToReturn[i]));

                System.err.println(" Transaction Status To Return :"+list_etatTrns_ToReturn[i]+" => Value Nbr StatusPerTrans To Return: "+list_nbTrnsPerStat_ToReturn[i]);
            }
            System.err.println("Total Transactions To Return = "+totalTransactions_ToReturn);

            super.onPostExecute(string);
        }
        */


    }

}
