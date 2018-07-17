package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 09/07/2018.
 */

public class NewsFeedTest {

    @SerializedName("category")
    @Expose
    private String heading;

    @SerializedName("data")
    @Expose
    private List<InfoFeedTest> infoList;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<InfoFeedTest> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<InfoFeedTest> infoList) {
        this.infoList = infoList;
    }
}
