package com.android_client.ms_solutions.mss.mss_androidapplication_client;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 21/03/2018.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Activities.AboutUsActivity;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Activities.PrivacyPolicyActivity;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.PlaceholderFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.io.IOException;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /**
     * Used to store the last screen title.
     */
    private CharSequence mTitle;

    ///
    SampleApi api = SampleApiFactory.getInstance();
    String[] welcomeUser;
    ListAdapter adapter = null;

    @Extra("AccessToken")
    String accessToken;
    ///
    NavigationView navigationView;
    View navHeader;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    //@BindView (R.id.nav_view)  NavigationView navigationView;
    //@BindView (R.id.drawer_layout)  DrawerLayout drawer;
    //@BindView (R.id.view_container)  View navHeader; //,headerLayout
    //@BindView (R.id.name) TextView txtName;
    //@BindView (R.id.website)  TextView txtWebsite;
   // @BindView (R.id.img_header_bg) ImageView imgNavHeaderBg;
   // @BindView (R.id.img_profile) ImageView imgProfile;
     ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private FloatingActionButton fab;
    private FrameLayout content;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    //private static final String urlProfileImg = "R.drawable.my_foto_profile";

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "Home";
   /* private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "movies"; */
    private static final String TAG_NOTIFICATIONS = "Notifications";
    private static final String TAG_SETTINGS = "Settings";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        Log.e("test ",extras.getString("AccessToken")); */
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //actionBar = getSupportActionBar();

        //mHandler = new Handler();

        drawer =  findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
       // fab = (FloatingActionButton) findViewById(R.id.fab);
       // navHeader = findViewById(R.id.view_container);
      //  navHeader = LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView, false);
        //navigationView.addHeaderView(navHeader);

        //inflate header layout
        //View navHeader =  navigationView.inflateHeaderView(R.layout.nav_header_main);
        //LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView);
       // headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        //navigationView = headerLayout.findViewById(R.id.nav_view);
     //   navHeader = navigationView.inflateHeaderView(R.layout.nav_header_main);
      //  navHeader = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
       // navigationView.addHeaderView(navHeader);
        //navHeader = navigationView.getHeaderView(0);

//        final View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
//
//        // Navigation view header
        //imgNavHeaderBg =   navigationView.getHeaderView(0).findViewById(R.id.img_header_bg);
        //imgProfile =   navigationView.getHeaderView(1).findViewById(R.id.img_profile);

        RelativeLayout LV = navigationView.findViewById(R.id.view_container);
        LinearLayout LL = LV.findViewById(R.id.linearLayoutHeaderMain);
        txtName =  LL.findViewById(R.id.name);
       // txtWebsite =  navHeader.findViewById(R.id.website);
       CharSequence Test = "test";
       txtName.setText(Test);
       navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(1))
                .commit();

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.nav_home));
        mTitle = getTitle();

        //content = findViewById(R.id.frame);
        // load toolbar titles from string resources
        //activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
                Snackbar.make(content, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                menuItem.setChecked(true);
                drawer.closeDrawers();
                return true;
            }

           @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case android.R.id.home:
                        drawer.openDrawer(GravityCompat.START);
                        return true;
                }
                return super.onOptionsItemSelected(item);
            }

        });*/

      /*  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        // Display User Name After Successful Login
        //afterViews();

        // load nav menu header data
        //loadNavHeader();

        // initializing navigation menu
        //setUpNavigationView();

       /* if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }*/
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

   /* @ViewById
    TextView  website;

    @ViewById
    TextView name;*/

    @AfterViews
    void afterViews() {
        new GetUserProfileTask().execute();
    }

    class GetUserProfileTask extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {
            try {
                welcomeUser = api.GetUserProfile(String.format("Bearer %s", accessToken)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.activity_main,welcomeUser);
            //setListAdapter(adapter);
            txtName.setText(welcomeUser[0]);
            txtWebsite.setText("www.mssolutions-group.com");

            super.onPostExecute(s);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
       // int position = 0;
        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {
            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.nav_home:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;
            /*        case R.id.nav_photos:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PHOTOS;
                        break;
                    case R.id.nav_movies:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_MOVIES;
                        break;
            */
            case R.id.nav_notifications:
                navItemIndex = 1;
                CURRENT_TAG = TAG_NOTIFICATIONS;
                break;
            case R.id.nav_settings:
                navItemIndex = 2;
                CURRENT_TAG = TAG_SETTINGS;
                break;
            case R.id.nav_about_us:
                // launch new intent instead of loading fragment
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                drawer.closeDrawers();
                return true;
            case R.id.nav_privacy_policy:
                // launch new intent instead of loading fragment
                startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                drawer.closeDrawers();
                return true;
            default:
                navItemIndex = 0;
        }

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(mTitle);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(navItemIndex + 1))
                .commit();
        return true;
    }



    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
   /* private void loadNavHeader() {
        // name, website
        //txtName.setText(welcomeUser);
        //txtWebsite.setText("www.mssolutions-group.com");

        // loading header background image
        Glide.with(this).load(R.drawable.nav_menu_header_bg)
               .crossFade()
               .diskCacheStrategy(DiskCacheStrategy.ALL)
               .into(imgNavHeaderBg);

       // Loading profile image
      Glide.with(this).load(R.drawable.my_foto_profile)
               .crossFade()
               .thumbnail(0.5f)
               .bitmapTransform(new CircleTransform(this))
               .diskCacheStrategy(DiskCacheStrategy.ALL)
               .into(imgProfile);

        // showing dot next to notifications label
        //navigationView.getMenu().getItem(1).setActionView(R.layout.notification_dot_alert);
    } */

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
  /*  private void loadHomeFragment() {
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
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
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
    } */

  /*  private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
           case 1:
                // photos
                PhotosFragment photosFragment = new PhotosFragment();
                return photosFragment;
            case 2:
                // movies fragment
                MoviesFragment moviesFragment = new MoviesFragment();
                return moviesFragment;

            case 2:
                // notifications fragment
                NotificationsFragment notificationsFragment = new NotificationsFragment();
                return notificationsFragment;

            case 3:
                // settings fragment
                UserManagementFragment settingsFragment = new UserManagementFragment();
                return settingsFragment;
            default:
                return new HomeFragment();
        }
    } */

 /*   private void setToolbarTitle() {
        //getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        //navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    } */

  /*  private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_notifications:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_NOTIFICATIONS;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
       // drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        //actionBarDrawerToggle.syncState();
    } */

  /*  @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
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

        super.onBackPressed();
    } */

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    } */

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    } */

    // show or hide the fab
   /* private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }*/
}
