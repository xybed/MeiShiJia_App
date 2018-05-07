package com.mumu.meishijia;

import android.support.multidex.MultiDexApplication;

import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.tencent.IMUtil;

import io.realm.Realm;
import lib.cache.CacheJsonMgr;
import lib.crash.MyCrashHandler;
import lib.realm.MyRealm;
import lib.utils.ToastUtil;

/**
 * MyApplication
 * Created by 7mu on 2016/4/22.
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication myApplication;

    private UserModel user;
    private boolean isLogin;
    private boolean isIMLogin;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        MyCrashHandler.getInstance().init(this);
        initUserLoginInfo();
        initRealm();
        //统一设置toast的context,context为app可避免内存泄漏
        ToastUtil.setContext(this);
        //验证了，这个添加离线消息，必须放在这个位置，且初始化IM要放在启动页面中，才会正常
        IMUtil.getInstance().addOfflineMsgListener();
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

    /**
     * 初始化realm，realm配置，创建表
     */
    private void initRealm(){
        Realm.init(this);
        MyRealm.getInstance().init();
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
    public boolean isIMLogin() {
        return isIMLogin;
    }
    public void setIMLogin(boolean IMLogin) {
        isIMLogin = IMLogin;
    }

}
