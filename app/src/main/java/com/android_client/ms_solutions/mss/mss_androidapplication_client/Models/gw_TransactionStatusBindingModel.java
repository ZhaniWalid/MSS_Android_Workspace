package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 01/06/2018.
 */

public class gw_TransactionStatusBindingModel {

    @SerializedName("etatTransaction")
    public String EtatTransaction;
    @SerializedName("nbreTransactionsParEtatTransaction")
    public int NbreTransactionsParEtatTransaction;

    public String getEtatTransaction() {
        return EtatTransaction;
    }

    public void setEtatTransaction(String etatTransaction) {
        EtatTransaction = etatTransaction;
    }

    public int getNbreTransactionsParEtatTransaction() {
        return NbreTransactionsParEtatTransaction;
    }

    public void setNbreTransactionsParEtatTransaction(int nbreTransactionsParEtatTransaction) {
        NbreTransactionsParEtatTransaction = nbreTransactionsParEtatTransaction;
    }
}
