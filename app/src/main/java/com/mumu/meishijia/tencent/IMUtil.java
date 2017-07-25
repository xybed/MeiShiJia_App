package com.mumu.meishijia.tencent;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.hwangjr.rxbus.RxBus;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.api.im.ImService;
import com.mumu.meishijia.constant.RxBusAction;
import com.mumu.meishijia.http.HttpRequestParams;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.http.RetroResListener;
import com.mumu.meishijia.im.IMConstant;
import com.mumu.meishijia.im.model.MessageFactory;
import com.mumu.meishijia.tencent.dao.ConversationDao;
import com.mumu.meishijia.tencent.dbmodel.ChatRealmModel;
import com.mumu.meishijia.model.im.PrincipalModel;
import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.tencent.dao.ChatDao;
import com.mumu.meishijia.tencent.dao.PrincipalDao;
import com.mumu.meishijia.tencent.dbmodel.ConversationRealmModel;
import com.mumu.meishijia.tencent.dbmodel.PrincipalRealmModel;
import com.mumu.meishijia.view.im.ConversationActivity;
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

import lib.utils.DateUtil;
import lib.utils.MyLogUtil;
import lib.utils.NumberUtil;
import lib.utils.SystemUtil;

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
    private long notifyTime;//记录通知时间

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
        ChatRealmModel firstMsg;
        int realUnReadMsgNum = 0;
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

            //消息存数据库
            ChatDao.insertMsg(chatRealmModel);
            realUnReadMsgNum++;
            firstMsg = chatRealmModel;
            refreshChat();

            //查询通讯录中可有此发送者信息，如若没有，则网络请求
            PrincipalRealmModel principalRealmModel = PrincipalDao.queryPrincipalInfo(NumberUtil.parseInt(conversation.getPeer(), 0));
            if(principalRealmModel == null){
                requestPrincipalInfo(conversation.getPeer(), chatRealmModel);
            }else {
                //如果有，就插入或更新会话项
                insertOrUpdateConversation(principalRealmModel, chatRealmModel);
            }
        }
    }

    private void insertOrUpdateConversation(PrincipalRealmModel principal, ChatRealmModel chat){
        //查询数据库中是否有此会话
        ConversationRealmModel conversationRealmModel = ConversationDao.queryConversation(principal.getPrincipal_id());
        //插入或更新会话
        ConversationDao.insertOrUpdateConversation(conversationRealmModel, principal.getPrincipal_id(), principal.getAvatar(), principal.getRemark(),
                chat.getTime(), chat.getMsg_type(), chat.getMsg_content(), principal.getPrincipal_user_id());
        refreshConversation();
        refreshMineUnreadMsg();
        notifyMsg("新消息", "新消息", ConversationActivity.class);
    }

    private void requestPrincipalInfo(final String principalId, final ChatRealmModel chat){
        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
        HttpRequestParams params = new HttpRequestParams();
        params.put("id", MyApplication.getInstance().getUser().getId());
        params.put("principal_id", principalId);
        httpRetrofit.getModel(httpRetrofit.getApiService(ImService.class, HttpUrl.GetPrincipalInfo, params).getPrincipalInfo(params.urlParams), "", new RetroResListener<PrincipalModel>() {
            @Override
            protected void onSuccess(PrincipalModel result) {
                //把PrincipalModel转为PrincipalRealmModel
                PrincipalRealmModel principal = new PrincipalRealmModel();
                principal.setUser_id(MyApplication.getInstance().getUser().getId());
                principal.setPrincipal_user_id(result.getPrincipal_user_id());
                principal.setRemark(result.getRemark());
                principal.setAvatar(result.getAvatar());
                principal.setPrincipal_id(result.getPrincipal_id());
                insertOrUpdateConversation(principal, chat);
            }

            @Override
            protected void onFailure(String errMsg) {
                MyLogUtil.e("消息主体err", "请求"+principalId+"的主体信息失败");
            }
        });
    }

    private static void refreshChat(){
        RxBus.get().post(RxBusAction.ChatList, "");
    }

    private static void refreshConversation(){
        RxBus.get().post(RxBusAction.ConversationList, "");
    }

    private static void refreshMineUnreadMsg(){
        RxBus.get().post(RxBusAction.MineUnreadMsg, "");
    }

    private void notifyMsg(String title, String content, Class clazz){
        //发送通知栏消息
        if (!SystemUtil.isFrontRunning(context, SystemUtil.getAppPackageName(context))) {
            if ((System.currentTimeMillis() - notifyTime) < 3 * 1000)
                return;
            notifyTime = System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
            notificationManager.cancel(IM_SDK_APP_ID);
            Intent mIntent = new Intent(context, clazz);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//看情况是否需要设置
            mIntent.putExtra("isFromJPush", "1");

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, mIntent, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(title)
                    .setContentText(content)
                    .setTicker(content)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            notificationManager.notify(IM_SDK_APP_ID, builder.build());
        }
    }

}
