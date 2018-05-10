package com.android_client.ms_solutions.mss.mss_androidapplication_client.Classes;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 21/03/2018.
 */

public class AuthenticationResult {

    private boolean isSuccessful;
    private String accessToken;
    private String error;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public AuthenticationResult(boolean isSuccessful, String accessToken, String error) {
        this.isSuccessful = isSuccessful;
        this.accessToken = accessToken;
        this.error = error;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getError() {
        return error;
    }

}
