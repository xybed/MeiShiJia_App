package com.mumu.meishijia.im.model;

/**
 * 接收到消息的json字符串的model
 * Created by Administrator on 2017/4/17.
 */

public class MsgJsonModel {
    private int msg_type;
    private MsgDataModel data;
    private long msg_id;//前端存储的消息id，用来区分标志消息状态，会一起传递到后台，但和后台的msg_id不同，后台还要改变这个字段
    private int conversation_id;

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
    }

    public MsgDataModel getData() {
        return data;
    }

    public void setData(MsgDataModel data) {
        this.data = data;
    }

    public long getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(long msg_id) {
        this.msg_id = msg_id;
    }

    public int getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(int conversation_id) {
        this.conversation_id = conversation_id;
    }
}
