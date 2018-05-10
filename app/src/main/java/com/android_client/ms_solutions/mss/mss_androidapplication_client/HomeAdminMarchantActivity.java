package com.android_client.ms_solutions.mss.mss_androidapplication_client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.SettingsScrollViewAdminMerchantFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.UserManagementFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.TransactionsFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;

public class HomeAdminMarchantActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String accessToken;
    public SampleApi api = SampleApiFactory.getInstance();
    public String welcomeUser,goodByUser;
    public String [] welcomeUsr;

    //public ImageView imgView_FragmentProfileUsr;
    public NavigationView navigationView;
    public FloatingActionButton fab;
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
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin_marchant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

        // Intent From Login Activity to Home Activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            accessToken = (String) bundle.get("AccessToken");
            //txtViewUsrName.setText(accessToken);
        }

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
        Toast.makeText(getApplicationContext(), "You Can't Go Back to Login Screen,You Should Logout First", Toast.LENGTH_LONG).show();
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
        } else if (id == R.id.nav_profile_user_mss_admin){
            navItemIndex = 3;
            CURRENT_TAG = TAG_PPROFILE;
            //setImageForUserProfile();
        } else if (id == R.id.nav_manage_mss_admin){
            navItemIndex = 4;
            CURRENT_TAG = TAG_USERS_MANAGEMENT;
        } else if (id == R.id.nav_settings_mss_admin){
            navItemIndex = 5;
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }

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
                // Profile 'Admin Marchant' fragment
                ProfileAdminMarchantFragment profileAdminMarchantFragment = new ProfileAdminMarchantFragment();
                return profileAdminMarchantFragment;
            case 4:
               // Users Management fragment
               UserManagementFragment userManagementFragment = new UserManagementFragment();
               return userManagementFragment;
            case 5:
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
            toggleFab();
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
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

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

    /// Fin : Parties des : Classes AsyncTask
}
