package com.mumu.meishijia.model.im;

import io.realm.RealmObject;

/**
 * 联系人的realm
 * Created by Administrator on 2017/4/7.
 */

public class ContactsRealmModel extends RealmObject{
    private int user_id;//用户在后台的id
    private int friend_id;//好友在后台的id
    private String remark;//对好友的备注，默认为好友的昵称
    private String avatar;
    private String sort_letter;//字母排序
    private int principle_id;//好友的pid
    private int is_friend;//是否为好友，有些陌生人的数据也存到此表，0不是，1是

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSort_letter() {
        return sort_letter;
    }

    public void setSort_letter(String sort_letter) {
        this.sort_letter = sort_letter;
    }

    public int getPrinciple_id() {
        return principle_id;
    }

    public void setPrinciple_id(int principle_id) {
        this.principle_id = principle_id;
    }

    public int getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(int is_friend) {
        this.is_friend = is_friend;
    }
}
