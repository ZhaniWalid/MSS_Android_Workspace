package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 21/03/2018.
 */

import com.google.gson.annotations.SerializedName;

public class TokenModel {

    @SerializedName("access_token")
    public String AccessToken;

    @SerializedName(".expires")
    public String ExpiresAt;

    @SerializedName("expires_in")
    public Integer ExpiresIn;

    @SerializedName(".issued")
    public String IssuedAt;

    @SerializedName("token_type")
    public String TokenType;

    @SerializedName("userName")
    public String Username;

    @SerializedName("organizationID")
    public int organizationID;

}
