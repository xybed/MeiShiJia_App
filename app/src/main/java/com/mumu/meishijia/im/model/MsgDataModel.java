package com.mumu.meishijia.im.model;

/**
 * 消息内容data的model
 * Created by Administrator on 2017/4/17.
 */

public class MsgDataModel {
    private int from_id;
    private int to_id;
    private long time;
    private String msg_content;//根据不同的消息类型，有不同的json格式字符串
    private int system_attach;//0不显示，1显示
    //为了方便取聊天对象的数据的机制，加上的数据
    private String avatar;
    private String remark;
    private String friend_id;

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

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }
}
