package com.android_client.ms_solutions.mss.mss_androidapplication_client.NotificationsPushFirebase;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.HomeActivity;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.HomeAdminMarchantActivity;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.MessageBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import static android.graphics.Color.rgb;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 28/06/2018.
 */

public class MyFirebaseMessagingServiceMerchant extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgServiceMer";
    private static final String MERCHANT_CHANNEL_ID ="merchant_channelMerchant";

    private NotificationManager notificationManager;
    private LocalBroadcastManager broadcaster;

    private MessageBindingModel messageBindingModel;
    private SampleApi api = SampleApiFactory.getInstance();
    private RemoteMessage remoteMessageServiceRassZebi;
    private String postFirebaseRassZebi = "";

    private static String jsonTitleMerchant = HomeActivity.TitleNotifMerchant, jsonMessageMerchant = HomeActivity.MsgNotifMerchant;
    //private static String jsonPriorityMerchant = HomeActivity.priorityMerchant;
    private static boolean content_availableMerchant = HomeActivity.isContentJsonAvailable_Merchant;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessageMerchant) {

        remoteMessageMerchant.getData().put("titleMerchant",jsonTitleMerchant);
        remoteMessageMerchant.getData().put("messageMerchant",jsonMessageMerchant);

        //remoteMessage.getData().putAll(params);

        String from = remoteMessageMerchant.getFrom();
        Log.e(TAG, "Remote Msg From Firebase HomeActv: " + from);

        if (remoteMessageMerchant.getData().size() > 0) {

            //String convertedJsonRemoteMessage = convertStandardJSONString();
            Log.e(TAG, "Data Payload HomeActv: " + remoteMessageMerchant.getData());

            //System.err.println("Json Data Get From Static Values : "+jsonTitle + " , " + jsonMessage + " , "+content_available);
            Log.e("My Json Data HomeActv","Title:"+jsonTitleMerchant+" ,Msg: "+jsonMessageMerchant+" ,ContentAvail: "+content_availableMerchant);

            try {
                //GetJsonNotifOfRejectedTransFirebase();
                JSONObject json = new JSONObject(remoteMessageMerchant.getData());
                //sendNotificationToAdmin(json);
                sendNotificationToMerchant(json);

                //sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
            } catch (Exception e) {
                Log.e(TAG, "Exception HomeActv: " + e.getMessage());
            }
        }else{
            Log.e(TAG,"No New Notification Data To Send");
        }
    }

    private void sendNotificationToMerchant(JSONObject jsonObject) {

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("contentAv",content_availableMerchant);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
        //finish();
        Log.e(TAG, "Notification JSON HomeActv" + jsonObject.toString());

        try {

            //JSONObject start = new JSONObject(String.valueOf(jsonObject.getJSONArray("data")));
            //JSONObject data = jsonObject.getJSONObject("data");
            String titleMerchant = jsonObject.getString("titleMerchant");
            String messageMerchant = jsonObject.getString("messageMerchant");
            //String imageUrl = data.getString("image");

            Log.e(TAG, "Data JSON HomeActv" + jsonObject.toString());
            showNotification(titleMerchant,messageMerchant,intent);

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception HomeActv: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception HomeActv: " + e.getMessage());
        }
    }

    private void showNotification(String title,String message,Intent intent){

        int requestID = (int) System.currentTimeMillis();
        // intents[0] = intent;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

            /*
            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());  // 0 : ID of notification
            */

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels();
        }else {
            // API 16 onwards
            Notification.Builder builder = new Notification.Builder(this);
            builder
                    //.setOngoing(false)
                    //.setPriority(Notification.PRIORITY_DEFAULT)
                    .setWhen(0)
                    .setLights(rgb(0,255,0), 0, 500) // green color
                    //.setColor(getApplication().getResources().getColor(R.color.green))
                    .setContentIntent(pendingIntent)
                    .setSound(defaultSoundUri)
                    .setTicker(title)
                    .setContentTitle(title)
                    //.setContentText(message)
                    .setSubText(message)
                    .setVibrate(new long[]{500, 500, 500, 500, 500})
                    .setSmallIcon(R.drawable.mss_logo_3)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.mss_logo_3))
                    .setStyle(new Notification.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(this.getResources(), R.drawable.firebase_icon))) /*Notification with Image*/
                    .setAutoCancel(true);

            notification = builder.build();
            notification.flags |= Notification.FLAG_NO_CLEAR;
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int notificationId = new Random().nextInt(10000);

            notificationManager.notify(notificationId, notification);
            Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vib != null) {
                vib.vibrate(250);
            }
        }
        //notificationManager.cancel(notificationId);

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void setupChannels(){
        CharSequence merchantChannelName = getString(R.string.notifications_merchant_channel_name);
        String merchantChannelDescription = getString(R.string.notifications_merchant_channel_description);

        NotificationChannel merchantChannel;
        merchantChannel = new NotificationChannel(MERCHANT_CHANNEL_ID, merchantChannelName, NotificationManager.IMPORTANCE_HIGH);
        merchantChannel.setDescription(merchantChannelDescription);
        merchantChannel.enableLights(true);
        merchantChannel.setLightColor(Color.GREEN);
        merchantChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(merchantChannel);
        }
    }
}
