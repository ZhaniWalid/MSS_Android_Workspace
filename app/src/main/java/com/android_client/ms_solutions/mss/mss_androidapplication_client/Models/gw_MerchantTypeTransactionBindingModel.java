package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 11/06/2018.
 */

public class gw_MerchantTypeTransactionBindingModel {

    @SerializedName("merchantType")
    public String MerchantType;
    @SerializedName("nbreMerchantType")
    public int NbreMerchantType;

    public String getMerchantType() {
        return MerchantType;
    }

    public void setMerchantType(String merchantType) {
        MerchantType = merchantType;
    }

    public int getNbreMerchantType() {
        return NbreMerchantType;
    }

    public void setNbreMerchantType(int nbreMerchantType) {
        NbreMerchantType = nbreMerchantType;
    }
}
