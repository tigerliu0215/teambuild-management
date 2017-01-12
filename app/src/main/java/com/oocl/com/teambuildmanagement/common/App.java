package com.oocl.com.teambuildmanagement.common;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by YUJO2 on 1/12/2017.
 */

public class App extends Application {
    public static Context context = null;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    public static Context getContext(){
        return context;
    }
}
