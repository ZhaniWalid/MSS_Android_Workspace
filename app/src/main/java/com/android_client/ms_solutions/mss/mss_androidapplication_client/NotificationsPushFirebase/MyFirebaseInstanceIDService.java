package com.android_client.ms_solutions.mss.mss_androidapplication_client.NotificationsPushFirebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 20/06/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Added by me
        String deviceToken = SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
        Log.d(TAG,"Device Token"+deviceToken);
        //Displaying token on logcat
        Log.d(TAG,"Refreshed token"+refreshedToken);
        //System.err.println(TAG+": " + " Refreshed token: " + refreshedToken);
        //System.err.println("Walid , Your FCM ( Firebase Token ) is : "+refreshedToken);

        //calling the method store token and passing token
        storeToken(refreshedToken);
    }

    private void storeToken(String token) {
        //saving the token on shared preferences
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);
    }
}
