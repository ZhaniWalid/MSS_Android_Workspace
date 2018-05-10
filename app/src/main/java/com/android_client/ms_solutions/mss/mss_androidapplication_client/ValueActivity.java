package com.android_client.ms_solutions.mss.mss_androidapplication_client;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 22/03/2018.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ListActivity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;

//import com.hintdesk.core.utils.JSONHttpClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_value)
public class ValueActivity extends ListActivity {

    SampleApi api = SampleApiFactory.getInstance();
    String [] values;
    ListAdapter adapter = null;
    //static Intent intent;

    @Extra("AccessToken")
    String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Like this inside this methode ' onCreate '  : " setContentView(R.layout.activity_value); "
        // or like that above the class : " @EActivity(R.layout.activity_value) "
        // => It's the same thing
    }

    @AfterViews
    void afterViews() {
        new GetValuesTask().execute();
    }

    class GetValuesTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                values = api.GetValues(String.format("Bearer %s", accessToken)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            adapter = new ArrayAdapter(ValueActivity.this,android.R.layout.simple_list_item_1,values);
            setListAdapter(adapter);
            super.onPostExecute(s);
        }
    }


}
