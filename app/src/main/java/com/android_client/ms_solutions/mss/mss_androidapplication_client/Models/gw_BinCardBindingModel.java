package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 11/06/2018.
 */

public class gw_BinCardBindingModel {

    @SerializedName("binCardLabel")
    public String BinCardLabel;
    @SerializedName("nbreBinCards")
    public int NbreBinCards;

    public String getBinCardLabel() {
        return BinCardLabel;
    }

    public void setBinCardLabel(String binCardLabel) {
        BinCardLabel = binCardLabel;
    }

    public int getNbreBinCards() {
        return NbreBinCards;
    }

    public void setNbreBinCards(int nbreBinCards) {
        NbreBinCards = nbreBinCards;
    }
}
