package com.mumu.meishijia.model.im;

import io.realm.RealmObject;

/**
 * 消息的realmModel
 * Created by Administrator on 2017/4/17.
 */

public class ChatRealmModel extends RealmObject{
    private int user_id;
    private int conversation_id;
    private int from_id;
    private int to_id;
    private long time;
    private int msg_type;
    private int msg_status;
    private String msg_content;
    private int system_attach;
    private long msg_id;//这个和后台的msg_id不同，这个只是用来区分本地数据库的不同消息id，用来回调设置消息的状态

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

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
    }

    public int getMsg_status() {
        return msg_status;
    }

    public void setMsg_status(int msg_status) {
        this.msg_status = msg_status;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public int getSystem_attach() {
        return system_attach;
    }

    public void setSystem_attach(int system_attach) {
        this.system_attach = system_attach;
    }

    public long getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(long msg_id) {
        this.msg_id = msg_id;
    }
}
