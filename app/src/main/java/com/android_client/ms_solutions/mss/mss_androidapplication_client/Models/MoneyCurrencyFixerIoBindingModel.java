package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 11/07/2018.
 */

public class MoneyCurrencyFixerIoBindingModel {

    @SerializedName("success")
    @Expose
    private boolean Success;
    @SerializedName("timestamp")
    @Expose
    private int timeStamp;
    @SerializedName("base")
    @Expose
    private String BaseMoneyCurrency;
    @SerializedName("date")
    @Expose
    private String DateOfTodayCurrency;
    @SerializedName("rates")
    @Expose
    private JsonObject rates;
    private int imageIdOfCountry;
    private String descriptionWithCurrencyValue;


    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getBaseMoneyCurrency() {
        return BaseMoneyCurrency;
    }

    public void setBaseMoneyCurrency(String baseMoneyCurrency) {
        BaseMoneyCurrency = baseMoneyCurrency;
    }

    public String getDateOfTodayCurrency() {
        return DateOfTodayCurrency;
    }

    public void setDateOfTodayCurrency(String dateOfTodayCurrency) {
        DateOfTodayCurrency = dateOfTodayCurrency;
    }

    public JsonObject getRates() {
        return rates;
    }

    public void setRates(JsonObject rates) {
        this.rates = rates;
    }

    public double getRate(String code) {
        return rates.has(code) ? rates.get(code).getAsDouble() : 0;
    }

    public int getImageIdOfCountry() {
        return imageIdOfCountry;
    }

    public void setImageIdOfCountry(int imageIdOfCountry) {
        this.imageIdOfCountry = imageIdOfCountry;
    }

    public String getDescriptionWithCurrencyValue() {
        return descriptionWithCurrencyValue;
    }

    public void setDescriptionWithCurrencyValue(String descriptionWithCurrencyValue) {
        this.descriptionWithCurrencyValue = descriptionWithCurrencyValue;
    }
}
