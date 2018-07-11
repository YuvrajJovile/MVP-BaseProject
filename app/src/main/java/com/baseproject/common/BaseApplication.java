package com.baseproject.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class BaseApplication extends Application {

    private SharedPreferences sharedPreferences;
    private static BaseApplication mBaseApplication;
    private static final String SHARED_FILE = "com.baseProject";


    public static BaseApplication  getInstance(){
        return mBaseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
        sharedPreferences = getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);

    }

    private SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
