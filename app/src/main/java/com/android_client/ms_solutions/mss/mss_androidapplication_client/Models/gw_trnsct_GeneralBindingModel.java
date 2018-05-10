package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 07/05/2018.
 */

// The ' SerializedNames ' : to know them we should at the result of the web service
// in for exmple 'Postman Client' and then copy the Name of results
// Exmple for these class URL : ' http://localhost:83/MSS_AspDotNetToAndroid_WebApi_WS/api/User/GetFiltrableTransactions '
/*
[
    {
        "idTransaction": "OBVsa+Y1gU+6h1aup3MapQ==",
        "idMerchant": "mp",
        "idTerminalMerchant": "0776522013",
        .
        .
        .
 */

public class gw_trnsct_GeneralBindingModel {

    @SerializedName("idTransaction")
    public String idTransaction ;
    @SerializedName("idMerchant")
    public String IdMerchant ;
    @SerializedName("idTerminalMerchant")
    public String IdTerminalMerchant ;
    @SerializedName("idHost")
    public String IdHost ;
    @SerializedName("amountAuthorisedNumeric")
    public String AmountAuthorisedNumeric ;
    @SerializedName("etatTransaction")
    public String EtatTransaction ;
    @SerializedName("bankOfRequest")
    public String BankOfRequest ;

    public gw_trnsct_GeneralBindingModel() {
    }

    public gw_trnsct_GeneralBindingModel(String idTransaction, String IdMerchant, String IdTerminalMerchant, String IdHost, String AmountAuthorisedNumeric, String EtatTransaction, String BankOfRequest) {
        this.idTransaction = idTransaction;
        this.IdMerchant = IdMerchant;
        this.IdTerminalMerchant = IdTerminalMerchant;
        this.IdHost = IdHost;
        this.AmountAuthorisedNumeric = AmountAuthorisedNumeric;
        this.EtatTransaction = EtatTransaction;
        this.BankOfRequest = BankOfRequest;
    }

    public gw_trnsct_GeneralBindingModel(String IdMerchant, String IdTerminalMerchant, String IdHost, String AmountAuthorisedNumeric, String EtatTransaction, String BankOfRequest) {
        this.IdMerchant = IdMerchant;
        this.IdTerminalMerchant = IdTerminalMerchant;
        this.IdHost = IdHost;
        this.AmountAuthorisedNumeric = AmountAuthorisedNumeric;
        this.EtatTransaction = EtatTransaction;
        this.BankOfRequest = BankOfRequest;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdMerchant() {
        return IdMerchant;
    }

    public void setIdMerchant(String idMerchant) {
        IdMerchant = idMerchant;
    }

    public String getIdTerminalMerchant() {
        return IdTerminalMerchant;
    }

    public void setIdTerminalMerchant(String idTerminalMerchant) {
        IdTerminalMerchant = idTerminalMerchant;
    }

    public String getIdHost() {
        return IdHost;
    }

    public void setIdHost(String idHost) {
        IdHost = idHost;
    }

    public String getAmountAuthorisedNumeric() {
        return AmountAuthorisedNumeric;
    }

    public void setAmountAuthorisedNumeric(String amountAuthorisedNumeric) {
        AmountAuthorisedNumeric = amountAuthorisedNumeric;
    }

    public String getEtatTransaction() {
        return EtatTransaction;
    }

    public void setEtatTransaction(String etatTransaction) {
        EtatTransaction = etatTransaction;
    }

    public String getBankOfRequest() {
        return BankOfRequest;
    }

    public void setBankOfRequest(String bankOfRequest) {
        BankOfRequest = bankOfRequest;
    }
}
