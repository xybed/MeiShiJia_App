package com.mumu.meishijia.model.im;

/**
 * 联系人的model
 * Created by Administrator on 2017/4/6.
 */

public class ContactsModel {
    private int friend_id;
    private String avatar;
    private String remark;//对好友的备注
    private String sort_letter;//字母排序
    private int principal_id;

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

    public String getSort_letter() {
        return sort_letter;
    }

    public void setSort_letter(String sort_letter) {
        this.sort_letter = sort_letter;
    }

    public int getPrincipal_id() {
        return principal_id;
    }

    public void setPrincipal_id(int principal_id) {
        this.principal_id = principal_id;
    }
}
