package com.mumu.meishijia.model.im;

import io.realm.RealmObject;

/**
 * 会话的realm
 * Created by Administrator on 2017/4/12.
 */

public class ConversationRealmModel extends RealmObject {
    private int user_id;
    private int conversation_id;//会话的id，单聊可以是friend_id，群聊可以是group_id
    private String avatar;
    private String remark;
    private String time;//最新消息的时间
    private String content;//最新消息
    private int unread_msg;
    private int principle_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(int conversation_id) {
        this.conversation_id = conversation_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUnread_msg() {
        return unread_msg;
    }

    public void setUnread_msg(int unread_msg) {
        this.unread_msg = unread_msg;
    }

    public int getPrinciple_id() {
        return principle_id;
    }

    public void setPrinciple_id(int principle_id) {
        this.principle_id = principle_id;
    }
}
