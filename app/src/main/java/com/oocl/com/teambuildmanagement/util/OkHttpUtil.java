package com.oocl.com.teambuildmanagement.util;

import com.oocl.com.teambuildmanagement.common.App;
import com.oocl.com.teambuildmanagement.common.SharedPreferenceDict;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Author：Jonas Yu on 2017/1/2 01:43
 * Description:
 */
public class OkHttpUtil {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final OkHttpClient mOkHttpClient = new OkHttpClient();

    public static void get(String url, Callback callback){
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        String tempCookie = SharedPreferenceUtil.getString(App.getContext(), SharedPreferenceDict.USER_SESSION_COOKIE,"");
        if(!tempCookie.equals("")){
            LogUtil.info("cookie " + tempCookie);
            builder.addHeader("Cookie",tempCookie);
        }
        Request request = builder.build();
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
                .addHeader("Cookie",SharedPreferenceUtil.getString(App.getContext(), SharedPreferenceDict.USER_SESSION_COOKIE,""))
                .post(builder.build())
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void postByJson(String url,String json,Callback callback){
        RequestBody body = RequestBody.create(JSON, null==json?"":json);
        LogUtil.info("json " + json);
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.post(body);
        String tempCookie = SharedPreferenceUtil.getString(App.getContext(), SharedPreferenceDict.USER_SESSION_COOKIE,"");
        if(!tempCookie.equals("")){
            LogUtil.info("cookie " + tempCookie);
            builder.addHeader("Cookie",tempCookie);
        }
        Request request = builder.build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void main(String[] args){

//        OkHttpUtil.post("http://112.74.166.187:8443/api/auth/signup", null, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                System.out.println(e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println(response.body().string());
//                System.out.println(response.code());
//            }
//        });

//        OkHttpUtil.postByJson("http://112.74.166.187:8443/api/auth/signin", JsonUtil.toJson(login), new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                System.out.println(e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println(response.body().string());
//                System.out.println(response.code());
//                System.out.println(response.header("set-cookie"));
//
//                if(null != response.header("set-cookie")){
//                    String[] cookieArr = response.header("set-cookie").split(";");
//                    System.out.println(cookieArr.length);
//                }
//            }
//        });

//        OkHttpUtil.postByJson("http://112.74.166.187:8443/api/activities/comments/publish/5875c5a0810e3724cf288fe3", JsonUtil.toJson(comment), new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println(response.body().string());
//            }
//        });



    }
}
