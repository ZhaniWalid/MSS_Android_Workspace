package com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 21/03/2018.
 */

import android.icu.text.DisplayContext;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Utils.NullOnEmptyConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SampleApiFactory {

    private static SampleApi instance = null;

    public static SampleApi getInstance() {
        if (instance == null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.97:83/MSS_AspDotNetToAndroid_WebApi_WS/")  //.baseUrl("http://10.0.2.2:83/MSS_AspDotNetToAndroid_WebApi_WS/")
                    .client(client)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            instance = retrofit.create(SampleApi.class);
        }
        return instance;
    }

}
