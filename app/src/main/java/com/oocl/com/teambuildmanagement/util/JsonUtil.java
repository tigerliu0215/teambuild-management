package com.oocl.com.teambuildmanagement.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Author：Jonas Yu on 2017/1/2 00:50
 * Description:
 */
public class JsonUtil {
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
    public static String toJson(Object obj,String dateFormat) {
        Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();
        return gson.toJson(obj);
    }


    public static <T> T fromJson(String json, Class<T> classOfT) {
        try{
            Gson gson = new Gson();
            return gson.fromJson(json, classOfT);
        }catch (Throwable t){

        }
        return null;
    }

    public static <T> T fromJson(String json, TypeToken<T> type) {
        try{
            Gson gson = new Gson();
            return gson.fromJson(json, type.getType());
        }catch (Throwable t){

        }
        return null;
    }


}
