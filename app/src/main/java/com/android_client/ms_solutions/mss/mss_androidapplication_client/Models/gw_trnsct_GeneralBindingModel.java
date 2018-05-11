package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

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
   // @SerializedName("amountAuthorisedNumeric")
   // public String AmountAuthorisedNumeric ;
    @SerializedName("amount")
    @Nullable
    public double Amount;
    @SerializedName("etatTransaction")
    public String EtatTransaction ;
    @SerializedName("bankOfRequest")
    public String BankOfRequest ;

    // Extended Part

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

    public gw_trnsct_GeneralBindingModel() {
    }

    public gw_trnsct_GeneralBindingModel(String idTransaction, String IdMerchant, String IdTerminalMerchant, String IdHost, @Nullable double Amount, String EtatTransaction, String BankOfRequest) {
        this.idTransaction = idTransaction;
        this.IdMerchant = IdMerchant;
        this.IdTerminalMerchant = IdTerminalMerchant;
        this.IdHost = IdHost;
        this.Amount = Amount;
        this.EtatTransaction = EtatTransaction;
        this.BankOfRequest = BankOfRequest;
    }

    public gw_trnsct_GeneralBindingModel(String IdMerchant, String IdTerminalMerchant, String IdHost, @Nullable double Amount, String EtatTransaction, String BankOfRequest) {
        this.IdMerchant = IdMerchant;
        this.IdTerminalMerchant = IdTerminalMerchant;
        this.IdHost = IdHost;
        this.Amount = Amount;
        this.EtatTransaction = EtatTransaction;
        this.BankOfRequest = BankOfRequest;
    }

    public gw_trnsct_GeneralBindingModel(String idTransaction, String idMerchant, String idTerminalMerchant, String idHost, @Nullable double amount, String etatTransaction, String bankOfRequest, String etatCloture, String currentDate, String timeSystemTransaction, String transactiontype, String responseCode, String FID_F_ApprovalCode, String cardMask) {
        this.idTransaction = idTransaction;
        IdMerchant = idMerchant;
        IdTerminalMerchant = idTerminalMerchant;
        IdHost = idHost;
        Amount = amount;
        EtatTransaction = etatTransaction;
        BankOfRequest = bankOfRequest;
        EtatCloture = etatCloture;
        CurrentDate = currentDate;
        TimeSystemTransaction = timeSystemTransaction;
        Transactiontype = transactiontype;
        ResponseCode = responseCode;
        this.FID_F_ApprovalCode = FID_F_ApprovalCode;
        CardMask = cardMask;
    }

    public gw_trnsct_GeneralBindingModel(String idMerchant, String idTerminalMerchant, String idHost, @Nullable double amount, String etatTransaction, String bankOfRequest, String etatCloture, String currentDate, String timeSystemTransaction, String transactiontype, String responseCode, String FID_F_ApprovalCode, String cardMask) {
        IdMerchant = idMerchant;
        IdTerminalMerchant = idTerminalMerchant;
        IdHost = idHost;
        Amount = amount;
        EtatTransaction = etatTransaction;
        BankOfRequest = bankOfRequest;
        EtatCloture = etatCloture;
        CurrentDate = currentDate;
        TimeSystemTransaction = timeSystemTransaction;
        Transactiontype = transactiontype;
        ResponseCode = responseCode;
        this.FID_F_ApprovalCode = FID_F_ApprovalCode;
        CardMask = cardMask;
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

    /*
    public String getAmountAuthorisedNumeric() {
        return AmountAuthorisedNumeric;
    }

    public void setAmountAuthorisedNumeric(String amountAuthorisedNumeric) {
        AmountAuthorisedNumeric = amountAuthorisedNumeric;
    }
    */

    @Nullable
    public double getAmount() {
        return Amount;
    }

    public void setAmount(@Nullable double amount) {
        Amount = amount;
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
