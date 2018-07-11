package com.mumu.meishijia.model.im;

import com.google.gson.annotations.SerializedName;

/**
 * 联系人的model
 * Created by Administrator on 2017/4/6.
 */

public class Contacts {
    @SerializedName("friend_id")
    private int friendId;

    private String avatar;

    private String remark;//对好友的备注

    @SerializedName("sort_letter")
    private String sortLetter;//字母排序

    @SerializedName("principal_id")
    private int principalId;

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
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

    public String getSortLetter() {
        return sortLetter;
    }

    public void setSortLetter(String sortLetter) {
        this.sortLetter = sortLetter;
    }

    public int getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(int principalId) {
        this.principalId = principalId;
    }
}
