package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 22/06/2018.
 */

public class NotificationBindingModel {

    @SerializedName("title")
    public String Title;
    @SerializedName("message")
    public String Message;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
