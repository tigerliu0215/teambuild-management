package com.oocl.com.teambuildmanagement.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author：Jonas Yu on 2017/1/2 01:26
 * Description:
 */
public class SharedPreferenceUtil {
    public static String PREFERENCE_NAME = "com.oocl.docu.teambuildmanagement.pref";

    private SharedPreferenceUtil(){
        throw new AssertionError();
    }

    public static boolean putString(Context context, String preferenceName, String key, String value){
        SharedPreferences settings = context.getSharedPreferences(preferenceName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static boolean putString(Context context, String key, String value) {
        return putString(context, PREFERENCE_NAME, key, value);
    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    public static String getString(Context context, String key,
                                   String defaultValue) {
        return getString(context, PREFERENCE_NAME, key, defaultValue);
    }

    public static String getString(Context context, String preferenceName,
                                   String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(
                preferenceName, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }


    /**
     * @Title putBoolean
     * @param context
     * @param key
     * @param value
     * @return boolean
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        return putBoolean(context, PREFERENCE_NAME, key, value);
    }
    /**
     * @Title putBoolean
     * @param context
     * @param preferenceName
     * @param key
     * @param value
     * @return boolean
     */
    public static boolean putBoolean(Context context,String preferenceName, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(
                preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * @Title getBoolean
     * @param context
     * @param key
     * @return boolean
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * @Title getBoolean
     * @param context
     * @param key
     * @param defaultValue
     * @return boolean
     */
    public static boolean getBoolean(Context context, String key,
                                     boolean defaultValue) {
        return getBoolean(context, PREFERENCE_NAME, key, defaultValue);
    }
    /**
     * @Title getBoolean
     * @param context
     * @param preferenceName
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(Context context,String preferenceName, String key,
                                     boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(
                preferenceName, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }



    public static boolean remove(Context context,String key) {
        return remove(context, PREFERENCE_NAME, key);
    }

    public static boolean remove(Context context, String preferenceName,
                                 String key) {
        SharedPreferences settings = context.getSharedPreferences(
                preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        return editor.commit();
    }
}
