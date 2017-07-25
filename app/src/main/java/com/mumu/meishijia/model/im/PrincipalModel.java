package com.mumu.meishijia.model.im;

/**
 * 消息主体的model
 * Created by Administrator on 2017/7/25.
 */

public class PrincipalModel {
    private int principal_user_id;//消息主体的用户id
    private String remark;//消息主体应该显示的名字，或是对好友的备注名
    private String avatar;
    private int principal_id;//消息主体id

    public int getPrincipal_user_id() {
        return principal_user_id;
    }

    public void setPrincipal_user_id(int principal_user_id) {
        this.principal_user_id = principal_user_id;
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

    public int getPrincipal_id() {
        return principal_id;
    }

    public void setPrincipal_id(int principal_id) {
        this.principal_id = principal_id;
    }
}
