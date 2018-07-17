package com.android_client.ms_solutions.mss.mss_androidapplication_client.NotificationsPushFirebase;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.NotificationsFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.HomeActivity;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.HomeAdminMarchantActivity;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.LoginActivity;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.MessageBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static android.graphics.Color.rgb;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 20/06/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final String ADMIN_CHANNEL_ID ="admin_channel";

    private NotificationManager notificationManager;
    private LocalBroadcastManager broadcaster;

    private MessageBindingModel messageBindingModel;
    private SampleApi api = SampleApiFactory.getInstance();
    private RemoteMessage remoteMessageServiceRassZebi;
    private String postFirebaseRassZebi = "";

    private static String jsonTitle = HomeAdminMarchantActivity.TitleNotif, jsonMessage = HomeAdminMarchantActivity.MsgNotif ;
    private static boolean content_available = HomeAdminMarchantActivity.isContentJsonAvailable;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

      // HomeAdminMarchantActivity.GetJsonNotifOfRejectedTransFirebase();

     /*  Map<String, String> params = new HashMap<String, String>();;
       params.put("title",jsonTitle);
       params.put("message",jsonMessage);  */

        remoteMessage.getData().put("title",jsonTitle);
        remoteMessage.getData().put("message",jsonMessage);

        //remoteMessage.getData().putAll(params);

        String from = remoteMessage.getFrom();
        Log.e(TAG, "Remote Msg From HomeAdm: " + from);

        if (remoteMessage.getData().size() > 0) {

            //String convertedJsonRemoteMessage = convertStandardJSONString();
            Log.e(TAG, "Data Payload HomeAdm" + remoteMessage.getData());

            //System.err.println("Json Data Get From Static Values : "+jsonTitle + " , " + jsonMessage + " , "+content_available);
            Log.e("My Json Data HomeAdm","Title:"+jsonTitle+" ,Msg: "+jsonMessage+" ,ContentAvailable: "+content_available);

            try {
                //GetJsonNotifOfRejectedTransFirebase();
                JSONObject json = new JSONObject(remoteMessage.getData());
                sendNotificationToAdmin(json);
                //sendNotificationToMerchant(json);

                //sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
            } catch (Exception e) {
                Log.e(TAG, "Exception HomeAdm: " + e.getMessage());
            }


         /*   try
            {
                //Map<String, String> params = remoteMessage.getData();
                //JSONObject object = new JSONObject(params);
                JSONObject data = new JSONObject();
                data.getJSONObject("data");
                Log.e("MY JSON OBJECT", data.toString());
                //jsonTitle   = data.getString("title");
                //jsonMessage = data.getString("message");

                sendNotification(jsonTitle,jsonMessage);

                //onMessageReceived(remoteMessageServiceRassZebi);

                //rest of the code
            }

            catch (Exception e){
                Log.e("Error",e.getMessage());
            }  */

            /*
            String X = remoteMessage.getData().get("title");
            String Y = remoteMessage.getData().get("message");
            */


            /*Intent intent = new Intent("MyData");
            intent.putExtra("title", remoteMessage.getData().get("title"));
            intent.putExtra("message", remoteMessage.getData().get("message"));
            //intent.putExtra("lng", remoteMessage.getData().get("DriverLongitude"));
            broadcaster.sendBroadcast(intent);*/
        }else{
            Log.e(TAG,"No New Notification Data To Send");
        }
    }

    public String convertStandardJSONString(String data_json){
        data_json = data_json.replace("\\", "");
        data_json = data_json.replace("\"{", "{");
        data_json = data_json.replace("}\",", "},");
        data_json = data_json.replace("}\"", "}");
        return data_json;
    }

    private void sendNotificationToAdmin(JSONObject jsonObject) {

        Intent intent = new Intent(this, HomeAdminMarchantActivity.class);
        intent.putExtra("ContentAvaible",content_available);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
        //finish();
        Log.e(TAG, "Notification JSON HomeAdm" + jsonObject.toString());

            try {

                //JSONObject start = new JSONObject(String.valueOf(jsonObject.getJSONArray("data")));
                //JSONObject data = jsonObject.getJSONObject("data");
                String title = jsonObject.getString("title");
                String message = jsonObject.getString("message");
                //String imageUrl = data.getString("image");

                Log.e(TAG, "Data JSON HomeAdm" + jsonObject.toString());

                showNotification(title,message,intent);

            } catch (JSONException e) {
                Log.e(TAG, "Json Exception HomeAdm: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception HomeAdm: " + e.getMessage());
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
                    .setLights(rgb(0,255,0), 500, 500) // green color
                    //.setColor(getApplication().getResources().getColor(R.color.green))
                    .setContentIntent(pendingIntent)
                    .setSound(defaultSoundUri)
                    .setTicker(title)
                    .setContentTitle(title)
                    //.setTimeoutAfter(500)
                    //.setContentText(message)
                    .setSubText(message)
                    .setVibrate(new long[]{500, 500, 500, 500, 500})
                    .setSmallIcon(R.drawable.mss_logo_3)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.mss_logo_3))
                    .setStyle(new Notification.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(this.getResources(), R.drawable.firebase_icon))) /*Notification with Image*/
                    .setAutoCancel(true);

            notification = builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int notificationId = new Random().nextInt(10000);

            notificationManager.notify(notificationId, notification);
            //startForeground(notificationId,notification);

            Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vib != null) {
                vib.vibrate(250);
            }
            //stopForeground(true);
            //notificationManager.cancel(notificationId);
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void setupChannels(){
        CharSequence adminChannelName = getString(R.string.notifications_admin_channel_name);
        String adminChannelDescription = getString(R.string.notifications_admin_channel_description);

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.GREEN);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}
