package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 10/05/2018.
 */

// The ' SerializedNames ' : to know them we should at the result of the web service
// in for exmple 'Postman Client' and then copy the Name of results
// Exmple for these class URL : ' http://localhost:83/MSS_AspDotNetToAndroid_WebApi_WS/api/User/GetExtendedFiltrableTransactions '
/*
[
    {
        "idTransaction": "2Fc+0E169UKRbFLXwoektQ==",
        "etatCloture": "non_cloturée",
        "currentDate": "17/03/15",
        .
        .
        .
 */
public class gw_trnsct_ExtendedBindingModel {

    @SerializedName("idTransaction")
    public String idTransaction;
    @SerializedName("etatCloture")
    public String EtatCloture;
    @SerializedName("currentDate")
    public String CurrentDate;
    @SerializedName("timeSystemTransaction")
    public String TimeSystemTransaction;
    @SerializedName("transactiontype")
    public String Transactiontype;
    @SerializedName("responseCode")
    public String ResponseCode;
    @SerializedName("fiD_F_ApprovalCode")
    public String FID_F_ApprovalCode;
    @SerializedName("cardMask")
    public String CardMask;

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getEtatCloture() {
        return EtatCloture;
    }

    public void setEtatCloture(String etatCloture) {
        EtatCloture = etatCloture;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }

    public String getTimeSystemTransaction() {
        return TimeSystemTransaction;
    }

    public void setTimeSystemTransaction(String timeSystemTransaction) {
        TimeSystemTransaction = timeSystemTransaction;
    }

    public String getTransactiontype() {
        return Transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        Transactiontype = transactiontype;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getFID_F_ApprovalCode() {
        return FID_F_ApprovalCode;
    }

    public void setFID_F_ApprovalCode(String FID_F_ApprovalCode) {
        this.FID_F_ApprovalCode = FID_F_ApprovalCode;
    }

    public String getCardMask() {
        return CardMask;
    }

    public void setCardMask(String cardMask) {
        CardMask = cardMask;
    }
}
