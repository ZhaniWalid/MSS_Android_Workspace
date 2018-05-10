package com.android_client.ms_solutions.mss.mss_androidapplication_client.Utils;


/**
 * Created by Walid Zhani @Walid.Zhy7 on 21/03/2018.
 */

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class NullOnEmptyConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody body) throws IOException {

                // added by me
                /*String returnValue = body.string();
                if (returnValue.startsWith("[")){
                    returnValue = returnValue.substring(1);
                }
                if (returnValue.endsWith(",[]]")){
                    returnValue = returnValue.substring(0, returnValue.length() - 4);
                }
                Log.d("", returnValue);*/
                // fin added by me

                if (body.contentLength() == 0) return null;
                return delegate.convert(body);
            }
        };
    }

}
