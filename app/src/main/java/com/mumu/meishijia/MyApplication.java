package com.mumu.meishijia;

import android.support.multidex.MultiDexApplication;

import com.mumu.meishijia.model.mine.UserModel;

import lib.cache.CacheJsonMgr;
import lib.crash.MyCrashHandler;

/**
 * MyApplication
 * Created by 7mu on 2016/4/22.
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication myApplication;

    private UserModel user;
    private boolean isLogin;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        MyCrashHandler.getInstance().init(this);
        initUserLoginInfo();
    }

    /**
     * 获取用户登录的信息
     */
    private void initUserLoginInfo(){
        //读取用户登陆内容
        Object object = CacheJsonMgr.getInstance(this).getJsonObject(UserModel.class);
        if (object != null) {
            setUser((UserModel) object);
            setLogin(true);
        }
    }

    public static MyApplication getInstance(){
        return myApplication;
    }
    public UserModel getUser() {
        return user;
    }
    public void setUser(UserModel user) {
        this.user = user;
    }
    public boolean isLogin() {
        return isLogin;
    }
    public void setLogin(boolean login) {
        isLogin = login;
    }
}
