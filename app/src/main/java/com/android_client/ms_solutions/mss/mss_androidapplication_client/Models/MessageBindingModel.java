package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 22/06/2018.
 */

public class MessageBindingModel {

    @SerializedName("to")
    public String to;
    @SerializedName("data")
    public NotificationBindingModel notificationData;
    //public object data { get; set; }
    @SerializedName("content_available")
    public boolean content_available;
    @SerializedName("priority")
    public String priority;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotificationBindingModel getNotificationData() {
        return notificationData;
    }

    public void setNotificationData(NotificationBindingModel notificationData) {
        this.notificationData = notificationData;
    }

    public boolean isContent_available() {
        return content_available;
    }

    public void setContent_available(boolean content_available) {
        this.content_available = content_available;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
