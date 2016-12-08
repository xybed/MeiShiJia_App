package com.mumu.meishijia;

import android.support.multidex.MultiDexApplication;

import lib.crash.MyCrashHandler;

/**
 * Created by 7mu on 2016/4/22.
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        MyCrashHandler.getInstance().init(this);
    }

    public static MyApplication getInstance(){
        return myApplication;
    }
}
