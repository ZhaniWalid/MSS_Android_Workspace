package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 10/07/2018.
 */

public class TheEconomistGoogleNewsBindingModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private int totalResults;
    @SerializedName("articles")
    @Expose
    private List<TheEconomistArticleBindingModel> listOfArticles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<TheEconomistArticleBindingModel> getListOfArticles() {
        return listOfArticles;
    }

    public void setListOfArticles(List<TheEconomistArticleBindingModel> listOfArticles) {
        this.listOfArticles = listOfArticles;
    }
}
