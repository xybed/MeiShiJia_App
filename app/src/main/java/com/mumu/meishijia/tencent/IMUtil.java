package com.mumu.meishijia.tencent;

import android.content.Context;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.im.IMConstant;
import com.mumu.meishijia.im.model.MessageFactory;
import com.mumu.meishijia.model.im.ChatRealmModel;
import com.mumu.meishijia.model.im.ContactsRealmModel;
import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.tencent.dao.ChatDao;
import com.mumu.meishijia.tencent.dao.ContactsDao;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMTextElem;
import com.tencent.TIMUser;
import com.tencent.TIMUserStatusListener;

import java.util.List;

import lib.utils.NumberUtil;

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
        loginIM();
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
            public boolean onNewMessages(List<TIMMessage> msgList) {
                MyApplication myApplication = MyApplication.getInstance();
                if(!myApplication.isLogin() && !myApplication.isIMLogin()){
                    return true;
                }
                if(msgList.size() < 1){
                    return true;
                }
                saveMsg(msgList);
                return true;//除非用户在OnNewMessages回调中返回true，此时将不再继续回调下一个消息监听器
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
        if(myApplication.isLogin() && !myApplication.isIMLogin()){
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
            //设置互踢
            setTIMUserStatusListener();
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

    /**
     * 互踢的监听
     */
    private void setTIMUserStatusListener(){
        TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {

            }

            @Override
            public void onUserSigExpired() {

            }
        });
    }

    /**
     * 监听下来的消息，把TIMElem转成数据库model，存入数据库，并刷新界面
     */
    private void saveMsg(List<TIMMessage> msgList){
        MyApplication myApplication = MyApplication.getInstance();
        for(int i=0;i<msgList.size();i++){
            TIMMessage msg = msgList.get(i);
            //获取这条消息的会话
            TIMConversation conversation = msg.getConversation();
            //设置此条消息已读，之后就不会重复拉取
            conversation.setReadMessage(msg);

            if(msg.isSelf()){
                continue;
            }
            TIMElem elem = MessageFactory.getCanShowElem(msg);
            if(elem == null){
                continue;
            }

            //转换消息，TIMElem转换成ChatRealmModel
            ChatRealmModel chatRealmModel = null;
            if(elem.getType() == TIMElemType.Text){
                chatRealmModel = MessageFactory.transTIMTextElem2RealmModel((TIMTextElem) elem);
            }else if(elem.getType() == TIMElemType.Image){

            }else if(elem.getType() == TIMElemType.Sound){

            }

            if(chatRealmModel == null){
                continue;
            }

            //添加数据
            chatRealmModel.setUser_id(myApplication.getUser().getId());
            chatRealmModel.setConversation_id(NumberUtil.parseInt(conversation.getPeer(), 0));
            chatRealmModel.setFrom_id(NumberUtil.parseInt(conversation.getPeer(), 0));
            chatRealmModel.setTo_id(myApplication.getUser().getPrincipal_id());
            chatRealmModel.setTime(msg.timestamp());
            //在转换的时候已经设置过消息类型，所以这里不再设置
            chatRealmModel.setMsg_status(IMConstant.MSG_STATUS_SUCCESS);
            chatRealmModel.setSystem_attach(IMConstant.SYSTEM_ATTACH_YES);
            chatRealmModel.setMsg_id(msg.getMsgId());

            //存数据库
            ChatDao.insertMsg(chatRealmModel);

            //查询通讯录中可有此发送者信息，如若没有，则网络请求
            ContactsRealmModel contactsRealmModel = ContactsDao.queryContacts(NumberUtil.parseInt(conversation.getPeer(), 0));
        }
    }



}
