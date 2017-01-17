package com.oocl.com.teambuildmanagement.util;

import android.content.Context;

import com.oocl.com.teambuildmanagement.common.SharedPreferenceDict;

import okhttp3.Response;

/**
 * Created by YUJO2 on 1/15/2017.
 */

public class ValidationUtil {

    public static final int LOGIN_NORMAL = 1;
    public static final int LOGIN_INVALID = 2;
    public static final int LOGIN_NOT_AUTHORIZED = 3;
    private static ValidationUtil instance = new ValidationUtil();
    private ValidationUtil(){

    }

    public static ValidationUtil getInstance(){
        return instance;
    }

    public int validateResponse(Response response){
        if(response.code() == 401){
            return LOGIN_INVALID;
        }else if(response.code() == 403){
            return LOGIN_NOT_AUTHORIZED;
        }
        return LOGIN_NORMAL;
    }

    public boolean validationLoginStatus(Context context){
        return SharedPreferenceUtil.getBoolean(context, SharedPreferenceDict.LOGIN_STATUS,false);
    }
}
