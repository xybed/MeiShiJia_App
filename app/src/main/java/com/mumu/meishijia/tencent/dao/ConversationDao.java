package com.mumu.meishijia.tencent.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.tencent.IMConstant;
import com.mumu.meishijia.tencent.model.MsgContentModel;
import com.mumu.meishijia.tencent.dbmodel.ConversationRealmModel;

import io.realm.Realm;
import lib.realm.MyRealm;

/**
 * 有关会话的数据库操作
 * Created by Administrator on 2017/7/25.
 */

public class ConversationDao {

    public static ConversationRealmModel queryConversation(int conversationId){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        return realm.where(ConversationRealmModel.class)
                .equalTo("user_id", MyApplication.getInstance().getUser().getId())
                .equalTo("conversation_id", conversationId)
                .findFirst();
    }

    public static ConversationRealmModel insertOrUpdateConversation(ConversationRealmModel conversationRealmModel,
                      int conversationId, String avatar, String remark, long time, int msgType,
                      String msgContent, int friendId){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        realm.beginTransaction();
        if(conversationRealmModel == null){
            //如果之前没有这个会话
            conversationRealmModel = new ConversationRealmModel();
            conversationRealmModel.setUnread_msg(1);
        }else {
            conversationRealmModel.setUnread_msg(conversationRealmModel.getUnread_msg() + 1);
        }
        conversationRealmModel.setUser_id(MyApplication.getInstance().getUser().getId());
        conversationRealmModel.setConversation_id(conversationId);
        conversationRealmModel.setAvatar(avatar);
        conversationRealmModel.setRemark(remark);
        conversationRealmModel.setTime(time);
        switch (msgType){
            case IMConstant.MSG_TYPE_TEXT:
                MsgContentModel textMsg = JSON.parseObject(msgContent, MsgContentModel.class, Feature.IgnoreNotMatch, Feature.InitStringFieldAsEmpty);
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
        conversationRealmModel.setPrincipal_id(conversationId);
        conversationRealmModel.setFriend_id(friendId);
        realm.insertOrUpdate(conversationRealmModel);
        realm.commitTransaction();
        return conversationRealmModel;
    }
}
