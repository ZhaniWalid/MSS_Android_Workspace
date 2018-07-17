package com.android_client.ms_solutions.mss.mss_androidapplication_client;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Canvas.GlideCircleTransformation;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.HomeFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.NotificationsFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.ProfileAdminMarchantFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.ReportingStatisticsFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.SettingsScrollViewAdminMerchantFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.UserManagementFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.TransactionsFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Gestures.MyGestureFilter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Gestures.SimpleGestureListener;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.MessageBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

public class HomeAdminMarchantActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String accessToken;
    private static SampleApi api = SampleApiFactory.getInstance();
    public String welcomeUser,goodByUser;
    public String [] welcomeUsr;

    //public ImageView imgView_FragmentProfileUsr;
    public NavigationView navigationView;
    //public FloatingActionButton fab;
    public DrawerLayout drawer;
    public View navHeader;
    public ImageView imgProfile;

    public TextView txtViewUsrName,txtViewEmail;


    //private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";

    // static values of user after login
    public static String id_user_static_adminMerchant;
    public static String userName_static_adminMerchant;
    public static String userEmail_static_adminMerchant;
    public static String userFirstName_static_adminMerchant;
    public static String userLastName_static_adminMerchant;
    public static String userPassword_static_adminMerchant;
    public static String userPhoneNumber_static_adminMerchant;
    public static String userOrganizationId_static_adminMerchant; // == 42 --> Marchant ( Acces Permited ) else == 1 => Bank ( Acces Denied )

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "Home";
    /* private static final String TAG_PHOTOS = "photos";
     private static final String TAG_MOVIES = "movies"; */
    private static final String TAG_NOTIFICATIONS = "Notifications";
    private static final String TAG_USERS_MANAGEMENT = "Users Management";
    private static final String TAG_SHARE= "Share";
    private static final String TAG_SEND= "Send";
    private static final String TAG_SETTINGS = "Settings";
    private static final String TAG_PPROFILE= "Profile";
    private static final String TAG_TRANSACTIONS= "Transactions";
    private static final String TAG_REPORTING= "Reporting And Statistics";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    public static String   TitleNotif = "",MsgNotif = "";
    public static boolean  isContentJsonAvailable;

    //private MyGestureFilter detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin_marchant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Les FindFiews
        navHeader = navigationView.getHeaderView(0);
        txtViewUsrName = (TextView) navHeader.findViewById(R.id.textViewUsrName);
        txtViewEmail = (TextView) navHeader.findViewById(R.id.textViewMail);
        imgProfile = (ImageView) navHeader.findViewById(R.id.imageViewProfile);

        // Void AfterViews
        afterViews();

        // Load Nav Header
        loadNavHeader();

        GetJsonNotifOfRejectedTransFirebase();

        //sendNotification_2(MyFirebaseMessagingService.jsonTitle,MyFirebaseMessagingService.jsonMessage);

        // Intent From Login Activity to Home Activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            accessToken = (String) bundle.get("AccessToken");
            Log.e("TokenLog","Access Token ADMIN : "+ accessToken);
            //txtViewUsrName.setText(accessToken);
        }

        // Intent From MyFirebaseMessagingService to get Notification
        /*Intent intentNotification = getIntent();
        Bundle bundle1 = intentNotification.getExtras();
        if(bundle1 != null){
            int flagsNotification = intentNotification.getFlags();
            String getIT = (String) bundle1.get("getIt");

            System.err.println("Flags of Notification HomeAdmin Intent is : "+flagsNotification);
            System.err.println("Put Extra of Notification HomeAdmin Intent is : "+getIT);

        }*/
        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this project the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        Bundle bundle1 = getIntent().getExtras();
        if (bundle1 != null) {
            for (String key : bundle1.keySet()) {
                  //String value = bundle1.getString(key);
                 ///boolean isContentAvailable = (boolean) bundle1.get("ContentAvaible");
                boolean isContentAvailable = bundle1.getBoolean("ContentAvaible");

                 if (key.equals("ContentAvaible")) {
                       if (isContentAvailable) {
                     Log.e("ContentAvailable Admin", String.valueOf(true));
                     System.err.println("Bundle Get Intent Notification Content Available Admin is : " + true);
                     //  Intent intent2 = new Intent(this, AnotherActivity.class);
                     //  intent.putExtra("value", value);
                     // startActivity(intent);
                     //  finish();
                     NotificationsFragment notificationsFragment = new NotificationsFragment();
                     goToFragment(notificationsFragment);
                 }
              }
            }
        }

        //subscribeToPushService();
       /* boolean contentAvaible = getIntent().getBooleanExtra("ContentAvaible",true);
        if (contentAvaible) {
                    Fragment fragment = new NotificationsFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frag_notification, fragment).commit();
        } */

        // Handler
        mHandler = new Handler();

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles_admin_merchant);

        // initializing navigation menu
        //setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        //getActionBar().setDisplayHomeAsUpEnabled(false);

        // findFiewById of the Profil Picture in the 'fragment_profile layout'
        //imgView_FragmentProfileUsr = ProfileFragment.imgView_ProfileUsr;

        // Detect touched area
        //detector = new MyGestureFilter(HomeAdminMarchantActivity.this, this);

    }

    private Fragment goToFragment(Fragment fragment){
        return fragment;
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            // drawer.closeDrawers();
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        //super.onBackPressed(); // should be commented this line in order to disable back press to 'LoginActivity'
        // disable going back to the LoginActivity
        //  moveTaskToBack(false);
        //Toast.makeText(getApplicationContext(), "You Can't Go Back to Login Screen,You Should Logout First", Toast.LENGTH_LONG).show();
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_admin) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_home_mss_admin){
            //Replacing the main content with ContentFragment Which is our Inbox View;
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
        } else if (id == R.id.nav_notifications_mss_admin){
            navItemIndex = 1;
            CURRENT_TAG = TAG_NOTIFICATIONS;
        } else if (id == R.id.nav_transactions_mss_admin) {
            navItemIndex = 2;
            CURRENT_TAG = TAG_TRANSACTIONS;
        } else if (id == R.id.nav_reporting_mss_admin){
            navItemIndex = 3;
            CURRENT_TAG = TAG_REPORTING;
        } else if (id == R.id.nav_profile_user_mss_admin){
            navItemIndex = 4;
            CURRENT_TAG = TAG_PPROFILE;
            //setImageForUserProfile();
        } else if (id == R.id.nav_manage_mss_admin){
            navItemIndex = 5;
            CURRENT_TAG = TAG_USERS_MANAGEMENT;
        } else if (id == R.id.nav_settings_mss_admin){
            navItemIndex = 6;
            CURRENT_TAG = TAG_SETTINGS;
            // settings activity
            /*Intent intent = new Intent(HomeAdminMarchantActivity.this,SettingsActivity.class);
            startActivity(intent);*/
        }
       /*
        else if (id == R.id.nav_share_mss_admin) {
            navItemIndex = 5;
            CURRENT_TAG = TAG_SHARE;
        } else if (id == R.id.nav_send_mss_admin) {
            navItemIndex = 6;
            CURRENT_TAG = TAG_SEND;
        }
        */
        else if (id == R.id.nav_logout_mss_admin) {
            logoutViews();
            LoginActivity_.intent(HomeAdminMarchantActivity.this).start();
            //startActivity(new Intent(HomeActivity.this,LoginActivity.class));
        } else {
            navItemIndex = 0;
        }

        //Checking if the item is in checked state or not, if not make it in checked state
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);

        loadHomeFragment();

       /* if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    void loadNavHeader() {
        // name, website
        //txtName.setText(welcomeUser);
        //txtWebsite.setText("www.mssolutions-group.com");

        // loading header background image
        /*Glide.with(this).load(R.drawable.nav_menu_header_bg)
               .crossFade()
               .diskCacheStrategy(DiskCacheStrategy.ALL)
               .into(imgNavHeaderBg);*/

        // Loading profile image

        // Those 2 solutions works with 2 differents Class , but i prefer the first one 1

        // Solution 1 : with class " GlideCircleTransformation "
        Glide.with(getApplicationContext())
                .load(getResources().getIdentifier("my_foto_profile", "drawable", this.getPackageName())) // load image from local storage 'drawable' to image view
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .bitmapTransform(new GlideCircleTransformation(getApplicationContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // Solution 2 : with class " CircleTransform "
          /*Glide.with(this)
                .load(getResources().getIdentifier("my_foto_profile", "drawable", this.getPackageName())) // load image from local storage 'drawable' to image view
               .placeholder(R.mipmap.ic_launcher)
               .error(R.mipmap.ic_launcher)
               .crossFade()
               .thumbnail(0.5f)
               .bitmapTransform(new CircleTransform(this))
               .diskCacheStrategy(DiskCacheStrategy.ALL)
               .into(imgProfile);*/

        // showing dot next to notifications label
        navigationView.getMenu().getItem(1).setActionView(R.layout.notification_dot_alert);
    }

    // show or hide the fab
    /*private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }*/

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // notifications
                NotificationsFragment notificationsFragment = new NotificationsFragment();
                return notificationsFragment;
            case 2:
                // Transactions fragment
                TransactionsFragment transactionsFragment = new TransactionsFragment();
                return transactionsFragment;
            case 3:
                // Reporting & Statistics fragment
                ReportingStatisticsFragment reportingStatisticsFragment = new ReportingStatisticsFragment();
                return reportingStatisticsFragment;
            case 4:
                // Profile 'Admin Marchant' fragment
                ProfileAdminMarchantFragment profileAdminMarchantFragment = new ProfileAdminMarchantFragment();
                return profileAdminMarchantFragment;
            case 5:
               // Users Management fragment
               UserManagementFragment userManagementFragment = new UserManagementFragment();
               return userManagementFragment;
            case 6:
                // settings fragment
                SettingsScrollViewAdminMerchantFragment settingsScrollViewAdminMerchantFragment = new SettingsScrollViewAdminMerchantFragment();
                return settingsScrollViewAdminMerchantFragment;
            default:
                return new HomeFragment();
        }
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            //toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_mss, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("MyData")
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //awaitingDriverRequesting.dismiss();
            //awaitingDriverPhone.setText(intent.getExtras().getString("phone")); //setting values to the TextViews
            //awaitingDriverLat.setText(intent.getExtras().getDouble("lat"));
            //awaitingDriverLng.setText(intent.getExtras().getDouble("lng"));
            String title = intent.getExtras().getString("title");
            String message = intent.getExtras().getString("message");

            sendNotification(title,message);
        }
    };
*/

   /* @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        android.app.Fragment myFragment =  getFragmentManager().findFragmentByTag(HomeFragment.HOME_FRAGMENT_TAG);
        // Call onTouchEvent of SimpleGestureFilter class
        myFragment.detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onSwipe(int direction) {

    }

    @Override
    public void onDoubleTap() {

    }*/

    void afterViews() {
        new GetUserProfileTask().execute();
    }
    void logoutViews() {
        new logoutUserTask().execute(id_user_static_adminMerchant);
    }


    //// Début : Parties des : Classes AsyncTask

    /// Class : GetUserProfileTask
    class GetUserProfileTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                welcomeUsr = api.GetUserProfile(String.format("Bearer %s", accessToken)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            //adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.activity_main,welcomeUser);
            //setListAdapter(adapter);
            id_user_static_adminMerchant = welcomeUsr[0];
            userName_static_adminMerchant = welcomeUsr[1];
            userEmail_static_adminMerchant = welcomeUsr[2];
            userFirstName_static_adminMerchant = welcomeUsr[3];
            userLastName_static_adminMerchant = welcomeUsr[4];
            userPassword_static_adminMerchant = welcomeUsr[5];
            userPhoneNumber_static_adminMerchant = welcomeUsr[6];
            userOrganizationId_static_adminMerchant = welcomeUsr[7];

            String welcome = "Welcome To MSS : ";

            txtViewUsrName.setText(welcome + userName_static_adminMerchant);
            txtViewEmail.setText(userEmail_static_adminMerchant);
            super.onPostExecute(s);
        }
    }

    /// Class : logoutUserTask
    class logoutUserTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                goodByUser = api.Logout(id_user_static_adminMerchant).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.activity_main,welcomeUser);
            //setListAdapter(adapter);
            Toast.makeText(getApplicationContext(),goodByUser,Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }
    }


    private void subscribeToPushService() {
       //FirebaseMessaging.getInstance().subscribeToTopic("news");

       // Log.d("AndroidBash", "Subscribed");
        //Toast.makeText(MainActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();

        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        Log.e("AndroidBash FCM Token", token);
        Toast.makeText(HomeAdminMarchantActivity.this,"Your Firebase FCM Token : "+ token, Toast.LENGTH_SHORT).show();
    }

    private  NotificationManager notificationManager;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void sendNotification_2(String title, String message) {
        //Intent[] intents= new Intent[1];
        //Intent intent = new Intent(this, HomeAdminMarchantActivity.class);
        //intent.putExtra("getIt","Test Notification Zabbbbbbbb");
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
        //  Log.e(TAG, "Notification JSON " + jsonObject.toString());
      /*  try{
            JSONObject data = jsonObject.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");
            //String imageUrl = data.getString("image");
*/
        // intents[0] = intent;
        //  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,PendingIntent.FLAG_ONE_SHOT);

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

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels();
        }else {
            // API 16 onwards
            Notification.Builder builder = new Notification.Builder(this);
            builder.setAutoCancel(true)
                    //.setLights(0,0,0)
                    //.setContentIntent(pendingIntent)
                    .setSound(defaultSoundUri)
                    //.setColor(R.color.green)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setCategory("MSS Transactions Notification")
                    .setOngoing(false)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis());
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
            notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.notify(0, notification);
        }

/*
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }*/
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(){
        CharSequence adminChannelName = getString(R.string.notifications_admin_channel_name);
        String adminChannelDescription = getString(R.string.notifications_admin_channel_description);

        final String ADMIN_CHANNEL_ID ="admin_channel";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.GREEN);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

    public static MessageBindingModel messageBindingModel;

    public static void GetJsonNotifOfRejectedTransFirebase(){
        new NotificationOfRejectedTransactionsFirebaseTask().execute();
    }

    //class AsyncTask
    //class NotificationOfRejectedTransactionsFirebaseTask
   public static class NotificationOfRejectedTransactionsFirebaseTask extends AsyncTask <String,String,MessageBindingModel>{

        @Override
        protected MessageBindingModel doInBackground(String... strings) {

            try {
                //messageBindingModel = api.GetNotificationOfRejectedTransactionsFirebase().execute().body();
                messageBindingModel = api.PostNotifRejectedTranscFromFireBaseCloud().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }


            /*
            try {
                remoteMessageServiceRassZebi = api.fireBaseRassZebi().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            */

            return messageBindingModel;
        }

        //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(MessageBindingModel messageBindingModel) {

            if (messageBindingModel != null){

                MsgNotif = messageBindingModel.getNotificationData().getMessage();
                TitleNotif = messageBindingModel.getNotificationData().getTitle();
                isContentJsonAvailable = messageBindingModel.isContent_available();
                //priorityMerchant = messageBindingModel.getPriority();
            }else{
                Log.e("Firebase Json Admin Res","No Notification Data to get to messageBindingModel");
            }

            //sendNotification_2(messageBindingModel.getNotificationData().getTitle(),messageBindingModel.getNotificationData().getMessage());
            super.onPostExecute(messageBindingModel);
        }

        /*
        @Override
        protected void onPostExecute(String string) {

            String jsonTitle   = messageBindingModel.getNotificationData().getTitle();
            String jsonMessage = messageBindingModel.getNotificationData().getMessage();


            Map m = new HashMap();
            m.put("title",jsonTitle);
            m.put("message",jsonMessage);
            remoteMessageService.getData().entrySet().add();


            if( messageBindingModel != null){
                //sendNotification(jsonTitle,jsonMessage);
                    //remoteMessageServiceRassZebi.getData();
                System.err.println("No Errors ZEBI");
            }else{
                System.err.println("There is no notification to send ZEBI");
            }

            super.onPostExecute(string);
        } */
    }


    /// Fin : Parties des : Classes AsyncTask
}
