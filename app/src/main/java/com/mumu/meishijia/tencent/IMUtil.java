package com.mumu.meishijia.tencent;

import android.content.Context;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.model.mine.UserModel;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMUser;

import java.util.List;

/**
 * 腾讯im的统一处理类
 * Created by Administrator on 2017/7/20.
 */

public class IMUtil {
    private static final int IM_SDK_APP_ID = 1400036325;
    private static final String IM_ACCOUNT_TYPE = "14229";

    private static IMUtil imUtil;
    private Context context;
    private TIMMessageListener messageListener;

    private IMUtil(Context context){
        this.context = context;
    }

    public static IMUtil getInstance(){
        if(imUtil == null){
            imUtil = new IMUtil(MyApplication.getInstance());
        }
        return imUtil;
    }

    /**
     * 在使用SDK进一步操作之前，需要初始化SDK。在存在多进程的情况下，请只在一个进程进行SDK初始化。
     */
    public void initTIMSDK(){
        TIMManager.getInstance().init(context, IM_SDK_APP_ID, IM_ACCOUNT_TYPE);

    }

    /**
     * 在多数情况下，用户需要感知新消息的通知，这时只需注册新消息通知回调 TIMMessageListener，
     * 在用户登录状态下，会拉取离线消息，
     * 为了不漏掉消息通知，需要在登录之前注册新消息通知。
     */
    public void addMessageListener(){
        //正常只调一次增加消息监听器，保证只有一次的话，在添加前先移除
        TIMManager.getInstance().removeMessageListener(messageListener);
        messageListener = new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                return false;//除非用户在OnNewMessages回调中返回true，此时将不再继续回调下一个消息监听器
            }
        };
        //添加一个消息监听器，默认情况下所有消息监听器都将按添加顺序被回调一次
        TIMManager.getInstance().addMessageListener(messageListener);
    }

    /**
     * 登录腾讯imSDK
     */
    public void loginIM(){
        MyApplication myApplication = MyApplication.getInstance();
        if(myApplication.isLogin()){
            UserModel userModel = myApplication.getUser();
            if(userModel == null)
                return;

            TIMUser timUser = new TIMUser();
            timUser.setAccountType(IM_ACCOUNT_TYPE);
            timUser.setAppIdAt3rd(String.valueOf(IM_SDK_APP_ID));
            timUser.setIdentifier(String.valueOf(userModel.getPrincipal_id()));

            TIMManager.getInstance().login(IM_SDK_APP_ID, timUser, userModel.getIm_usersig(), new TIMCallBack() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(int code, String desc) {

                }
            });
        }
    }

    /**
     * 登出腾讯imSDK
     */
    public void logoutIM(){
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(int code, String desc) {

            }
        });
    }

}
