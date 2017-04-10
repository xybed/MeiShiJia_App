package com.mumu.meishijia.model.im;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 联系人的realm
 * Created by Administrator on 2017/4/7.
 */

public class ContactsRealmModel extends RealmObject{
    private int userId;//用户在后台的id
    private int friendId;//好友在后台的id
    private String remark;//对好友的备注，默认为好友的昵称
    private String avatar;
    private String sort_letter;//字母排序

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
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
}
