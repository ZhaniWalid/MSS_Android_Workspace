package com.android_client.ms_solutions.mss.mss_androidapplication_client.Classes;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.NewsFeedTest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 09/07/2018.
 */

public class NewsFeedJsonLoaderTest {

    private static final String TAG = "NewsFeedJsonLoaderTest";

    public static List<NewsFeedTest> loadFeeds(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "news_feed_test.json"));
            List<NewsFeedTest> feedList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                NewsFeedTest feed = gson.fromJson(array.getString(i), NewsFeedTest.class);
                feedList.add(feed);
            }
            return feedList;
        }catch (Exception e){
            Log.d(TAG,"seedGames parseException " + e);
            e.printStackTrace();
            return null;
        }
    }

    private static String loadJSONFromAsset(Context context, String jsonFileName) {
        String json;
        InputStream is;
        try {
            AssetManager manager = context.getAssets();
            Log.d(TAG,"path "+jsonFileName);
            is = manager.open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
