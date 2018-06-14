package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 13/06/2018.
 */

public class gw_BankOfPayementBindingModel {

    @SerializedName("bankName")
    public String BankName;
    @SerializedName("nbBankPerNameBank")
    public int NbBankPerNameBank;

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public int getNbBankPerNameBank() {
        return NbBankPerNameBank;
    }

    public void setNbBankPerNameBank(int nbBankPerNameBank) {
        NbBankPerNameBank = nbBankPerNameBank;
    }
}
