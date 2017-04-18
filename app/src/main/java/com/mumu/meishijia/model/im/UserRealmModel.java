package com.mumu.meishijia.model.im;

/**
 * 用户表，不同于联系人，也可以包括陌生人
 * Created by Administrator on 2017/4/18.
 */

public class UserRealmModel {
    private int user_id;
    private int friend_id;
    private String avatar;
    private String remark;
    private int principle_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
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

    public int getPrinciple_id() {
        return principle_id;
    }

    public void setPrinciple_id(int principle_id) {
        this.principle_id = principle_id;
    }
}
