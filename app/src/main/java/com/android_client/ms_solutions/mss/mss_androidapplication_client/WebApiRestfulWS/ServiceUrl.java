package com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 21/03/2018.
 */

public class ServiceUrl {

    //public static final String REST_SERVICE_URL ="http://localhost:11520/";192.168.1.91
    public static final String REST_SERVICE_URL ="http://192.168.1.97:83/MSS_AspDotNetToAndroid_WebApi_WS/";
    //public static final String REST_SERVICE_URL ="http://10.0.2.2:83/MSS_AspDotNetToAndroid_WebApi_WS/";
    public static final String REST_SERVICE_API = REST_SERVICE_URL+ "api/";
    public static final String REGISTER = REST_SERVICE_API + "Account/Register";
    public static final String TOKEN = REST_SERVICE_URL +"Token";
    public static final String VALUES = REST_SERVICE_API + "values";

    public static final String WELCOME_AFTER_LOGIN = REST_SERVICE_API + "User/Profile";
    public static final String LOGOUT = REST_SERVICE_API + "User/Logout";

}
