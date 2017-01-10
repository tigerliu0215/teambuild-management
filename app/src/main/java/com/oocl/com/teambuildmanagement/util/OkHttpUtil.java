package com.oocl.com.teambuildmanagement.util;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Author：Jonas Yu on 2017/1/2 01:43
 * Description:
 */
public class OkHttpUtil {

    public static final OkHttpClient mOkHttpClient = new OkHttpClient();

    public static void get(String url, Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void post(String url, Map<String,String> params,Callback callback){
        FormBody.Builder builder = new FormBody.Builder();
        if(params != null && params.size() > 0){
            for(Map.Entry<String,String> param: params.entrySet()){
                builder.add(param.getKey(),param.getValue());
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

}
