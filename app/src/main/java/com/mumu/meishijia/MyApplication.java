package com.mumu.meishijia;

import android.support.multidex.MultiDexApplication;

import com.mumu.meishijia.im.IMUtil;
import com.mumu.meishijia.model.mine.UserModel;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import lib.cache.CacheJsonMgr;
import lib.crash.MyCrashHandler;
import lib.realm.MyRealm;
import lib.utils.MyLogUtil;

/**
 * MyApplication
 * Created by 7mu on 2016/4/22.
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication myApplication;

    private UserModel user;
    private boolean isLogin;
    private WebSocketClient webSocketClient;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        MyCrashHandler.getInstance().init(this);
        initUserLoginInfo();
        initRealm();
        setWebSocketConnect();
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

    public void setWebSocketConnect(){
        if(isLogin()){
            URI uri = URI.create(BuildConfig.Base_Socket_Url + "/" + getUser().getPrincipal_id());
            //这里传Draft_17貌似跟后台使用tomcat的版本有关，默认是Draft_10，使用10连不上，17可以
            webSocketClient = new WebSocketClient(uri, new Draft_17()) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    MyLogUtil.e("webSocket", "onOpen回调");
                }

                @Override
                public void onMessage(String message) {
                    MyLogUtil.e("webSocket", "onMessage回调");
                    MyLogUtil.e("webSocket", message);
                    IMUtil.receiveNewMessage(message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    //连接断开，remote判定是客户端断开还是服务端断开
                    MyLogUtil.e("webSocket", "onClose回调");
                    MyLogUtil.e("webSocket", reason);
                    MyLogUtil.e("webSocket", code+"");
                }

                @Override
                public void onError(Exception ex) {
                    MyLogUtil.e("webSocket", "onError回调");
                    ex.printStackTrace();
                }
            };
            webSocketClient.connect();
        }
    }

    public WebSocketClient getWebSocket(){
        return webSocketClient;
    }

    public void closeWebSocket(){
        if(webSocketClient != null)
            webSocketClient.close();
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
