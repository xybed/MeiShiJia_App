package com.mumu.meishijia.im;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.hwangjr.rxbus.RxBus;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.constant.RxBusAction;
import com.mumu.meishijia.im.model.BaseMessage;
import com.mumu.meishijia.im.model.MessageFactory;
import com.mumu.meishijia.im.model.MsgContentModel;
import com.mumu.meishijia.im.model.MsgJsonModel;
import com.mumu.meishijia.model.im.ChatRealmModel;
import com.mumu.meishijia.model.im.ConversationRealmModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import lib.realm.MyRealm;

/**
 * 统一处理Im的工具
 * Created by Administrator on 2017/4/17.
 */

public class IMUtil {

    /**
     * 接收到新消息
     * 1.把json转成MsgJsonModel
     * 2.MsgJsonModel中的数据存入消息数据库，ChatRealmModel
     * @param message 接收到的消息的json字符串
     */
    public static void receiveNewMessage(String message){
        MsgJsonModel jsonModel = JSON.parseObject(message, MsgJsonModel.class, Feature.IgnoreNotMatch, Feature.InitStringFieldAsEmpty);

        //如果是自己发的消息，那就是要改变消息的状态
        if(jsonModel.getData().getFrom_id() == MyApplication.getInstance().getUser().getPrincipal_id()){
            updateMsgStatus(jsonModel);
            refreshChat();
            return;
        }
        ChatRealmModel chatRealmModel = new ChatRealmModel();
        chatRealmModel.setUser_id(MyApplication.getInstance().getUser().getId());
        chatRealmModel.setConversation_id(jsonModel.getConversation_id());
        chatRealmModel.setFrom_id(jsonModel.getData().getFrom_id());
        chatRealmModel.setTo_id(jsonModel.getData().getTo_id());
        chatRealmModel.setTime(jsonModel.getData().getTime());
        chatRealmModel.setMsg_type(jsonModel.getMsg_type());
        chatRealmModel.setMsg_status(1);
        chatRealmModel.setMsg_content(jsonModel.getData().getMsg_content());
        chatRealmModel.setSystem_attach(jsonModel.getData().getSystem_attach());

        saveMsg(chatRealmModel);

        ConversationRealmModel conversationRealmModel = queryConversation(jsonModel.getConversation_id());
        if(conversationRealmModel == null){
            //如果之前没有这个会话
            conversationRealmModel = new ConversationRealmModel();
            conversationRealmModel.setUnread_msg(1);
        }else {
            conversationRealmModel.setUnread_msg(conversationRealmModel.getUnread_msg() + 1);
        }
        conversationRealmModel.setUser_id(MyApplication.getInstance().getUser().getId());
        conversationRealmModel.setConversation_id(jsonModel.getConversation_id());
        conversationRealmModel.setAvatar(jsonModel.getData().getAvatar());
        conversationRealmModel.setRemark(jsonModel.getData().getRemark());
        conversationRealmModel.setTime(jsonModel.getData().getTime());
        switch (jsonModel.getMsg_type()){
            case IMConstant.MSG_TYPE_TEXT:
                MsgContentModel textMsg = JSON.parseObject(jsonModel.getData().getMsg_content(), MsgContentModel.class, Feature.IgnoreNotMatch, Feature.InitStringFieldAsEmpty);
                conversationRealmModel.setContent(textMsg.getText());
                break;
            case IMConstant.MSG_TYPE_PICTURE:
                conversationRealmModel.setContent("[图片]");
                break;
            case IMConstant.MSG_TYPE_VOICE:
                conversationRealmModel.setContent("[语音]");
                break;
            case IMConstant.MSG_TYPE_TIP:
                conversationRealmModel.setContent("[系统消息]");
                break;
        }
        conversationRealmModel.setPrincipal_id(jsonModel.getConversation_id());

        saveConversation(conversationRealmModel);

        refreshChat();
        refreshConversation();
    }

    private static void updateMsgStatus(final MsgJsonModel model){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ChatRealmModel chatRealmModel = realm.where(ChatRealmModel.class)
                        .equalTo("user_id", MyApplication.getInstance().getUser().getId())
                        .equalTo("conversation_id", model.getConversation_id())
                        .equalTo("msg_id", model.getMsg_id())
                        .findFirst();
                chatRealmModel.setMsg_status(IMConstant.MSG_STATUS_SUCCESS);
            }
        });
    }

    private static void saveMsg(ChatRealmModel chatRealmModel){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        realm.beginTransaction();
        realm.copyToRealm(chatRealmModel);
        realm.commitTransaction();
    }

    private static ConversationRealmModel queryConversation(int conversation_id){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        return realm.where(ConversationRealmModel.class)
                .equalTo("user_id", MyApplication.getInstance().getUser().getId())
                .equalTo("conversation_id", conversation_id)
                .findFirst();
    }

    private static void saveConversation(ConversationRealmModel conversationRealmModel){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        realm.beginTransaction();
        realm.insertOrUpdate(conversationRealmModel);
        realm.commitTransaction();
    }

    private static void refreshChat(){
        RxBus.get().post(RxBusAction.ChatList, "");
    }

    private static void refreshConversation(){
        RxBus.get().post(RxBusAction.ConversationList, "");
    }
}
