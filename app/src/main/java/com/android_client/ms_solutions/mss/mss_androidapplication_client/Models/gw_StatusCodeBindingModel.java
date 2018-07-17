package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 28/06/2018.
 */

public class gw_StatusCodeBindingModel {

    // Code Status pour chercher les transactions réfusé ( rejeté ) ,tq refus (rejet) code = 20
    @SerializedName("codeStatus")
    public String CodeStatus;
    @SerializedName("codeStatusDescription")
    public String CodeStatusDescription;
    @SerializedName("transactionDate")
    public Date TransactionDate;

    // Newest fields added by me
    @SerializedName("transactionId")
    public String TransactionId;
    @SerializedName("transactionType")
    public String TransactionType;
    @SerializedName("amount")
    public double Amount;
    @SerializedName("cardOfPayement")
    public String CardOfPayement;
    @SerializedName("bankOfRequest")
    public String BankOfRequest;
    @SerializedName("bankNameGateWay")
    public String BankNameGateWay;


    public String getCodeStatus() {
        return CodeStatus;
    }

    public void setCodeStatus(String codeStatus) {
        CodeStatus = codeStatus;
    }

    public String getCodeStatusDescription() {
        return CodeStatusDescription;
    }

    public void setCodeStatusDescription(String codeStatusDescription) {
        CodeStatusDescription = codeStatusDescription;
    }

    public Date getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        TransactionDate = transactionDate;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getCardOfPayement() {
        return CardOfPayement;
    }

    public void setCardOfPayement(String cardOfPayement) {
        CardOfPayement = cardOfPayement;
    }

    public String getBankOfRequest() {
        return BankOfRequest;
    }

    public void setBankOfRequest(String bankOfRequest) {
        BankOfRequest = bankOfRequest;
    }

    public String getBankNameGateWay() {
        return BankNameGateWay;
    }

    public void setBankNameGateWay(String bankNameGateWay) {
        BankNameGateWay = bankNameGateWay;
    }
}
