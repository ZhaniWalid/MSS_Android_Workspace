package com.android_client.ms_solutions.mss.mss_androidapplication_client.ChartsFormatters;

import android.os.AsyncTask;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_TransactionStatusBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;


/**
 * Created by Walid Zhani @Walid.Zhy7 on 10/06/2018.
 */

public class TransactionStatusAxisValueFormatter implements IAxisValueFormatter {

    private String[] transactionStatus,list_etatTrns_ToReturn;
    private int[] list_nbTrnsPerStat_ToReturn;
    private SampleApi api = SampleApiFactory.getInstance();
    private List<gw_TransactionStatusBindingModel> list_TransactionsStatusValues,list2;
    private int myCount = 0;
    private String getFormattedValue = "";
    //private DecimalFormat d;
    List<String> l;

    public TransactionStatusAxisValueFormatter() {
        GetTransactionsStatusValues();
    }

    private List<String> setXAxisTranStatus(int countSize,String[] list_statusTransaction,int[] list_nbTrnsPerStat_ToReturn,List<gw_TransactionStatusBindingModel> lista){

        list_statusTransaction = new String[countSize];
        list_nbTrnsPerStat_ToReturn = new int[countSize];
        transactionStatus = new String[countSize];

        l = new ArrayList<>();

        myCount = countSize;
        list2 = lista;

        for (int i =0 ; i < countSize ; i++){
            list_statusTransaction[i] = lista.get(i).getEtatTransaction();
            list_nbTrnsPerStat_ToReturn[i] = lista.get(i).getNbreTransactionsParEtatTransaction();
            System.err.println("voici : => " + list_statusTransaction[i] + " | "+list_nbTrnsPerStat_ToReturn[i]);
            getFormattedValue = list_statusTransaction[i];
            System.err.println("Value Formatter : =>"+getFormattedValue);
            transactionStatus[i] = lista.get(i).getEtatTransaction();

            l.add(list_statusTransaction[i]);
        }
        //System.err.println("Status Axis : "+getFormattedValue);
        return l;
        //transactionStatus = list_statusTransaction;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        String get = "";
        String toReturn = "";
        int size = l.size();
        String[] t = new String[size];

        for (int i = 0; i < size ; i++){
            t[i] = l.get(i);
            get = t[i];
          // get = transactionStatus[i];
          // System.err.println("Value Of GetFormatter : => "+get);
        }
        return get;
        /*
        switch (get){
            case "Transaction non autorisée":
                toReturn = "Transaction non autorisée";
                break;
            case "Transaction autorisée":
                toReturn = "Transaction autorisée";
                break;
            case "Transaction annulée":
                toReturn = "Transaction annulée";
                break;
            case "Transaction non aboutie":
                toReturn = "Transaction non aboutie";
                break;
        }
        return toReturn;
        */
        //return getFormattedValue;
        //return String.format(getFormattedValue, value);
        //return x;
        //return d.format(value);
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

            int sizeOfListTransactionsStatus = list_TransactionsStatusValues.size();
            //int size2 = list_TransactionsStatusValues.size();

            list_etatTrns_ToReturn = new String[sizeOfListTransactionsStatus];
            list_nbTrnsPerStat_ToReturn = new int[sizeOfListTransactionsStatus];

            // Println to verify in the console ,if i am getting the values or not
            System.err.println("size Of List Transactions Status Axis Formatter is : " +sizeOfListTransactionsStatus);

            int max = 0,maxOfTheMax = 0;

            for (int i = 0; i < sizeOfListTransactionsStatus; i++) {

                list_etatTrns_ToReturn[i] = list_TransactionsStatusValues.get(i).getEtatTransaction();
                list_nbTrnsPerStat_ToReturn[i] = list_TransactionsStatusValues.get(i).getNbreTransactionsParEtatTransaction();

                //totalTransactions_ToReturn += list_nbTrnsPerStat_ToReturn[i];

                //max = Math.max(max,list_nbTrnsPerStat_ToReturn[i]);
               // maxOfTheMax = max + 5;
                setXAxisTranStatus (sizeOfListTransactionsStatus,list_etatTrns_ToReturn,list_nbTrnsPerStat_ToReturn,list_TransactionsStatusValues);
               // setBarChartData(sizeOfListTransactionsStatus,maxOfTheMax,list_nbTrnsPerStat_ToReturn,list_etatTrns_ToReturn,list_TransactionsStatusValues);
                //setPieChartData(sizeOfListTransactionsStatus, list_nbTrnsPerStat_ToReturn , list_etatTrns_ToReturn, totalTransactions_ToReturn,list_TransactionsStatusValues);

                System.err.println(" Transaction Status Axis Formatter To Return : " + list_etatTrns_ToReturn[i]  + " => Value Nbr StatusPerTrans Axis Formatter To Return: " + list_nbTrnsPerStat_ToReturn[i]);
            }
            //setPieChartData(sizeOfListTransactionsStatus, list_nbTrnsPerStat_ToReturn , list_etatTrns_ToReturn, getTotal);

            //System.err.println("Total Transactions Bar Chart To Return = " + totalTransactions_ToReturn);
            System.err.println("Max Value Of Transactions Axis Formatter To Return = " + max);
            System.err.println("Max Of The Max Value Difference by 5 Of Transactions Axis Formatter To Return = " + maxOfTheMax);

            super.onPostExecute(string);
        }
    }
}
