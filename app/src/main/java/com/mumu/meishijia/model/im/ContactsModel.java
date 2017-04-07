package com.mumu.meishijia.model.im;

/**
 * 联系人的model
 * Created by Administrator on 2017/4/6.
 */

public class ContactsModel {
    private int id;
    private String username;
    private String nickname;
    private String avatar;
    private String remark;//对好友的备注
    private String province;
    private String city;
    private int sex;
    private String signature;
    private String sort_letter;//字母排序
    private int is_friend;
    private int principle_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSort_letter() {
        return sort_letter;
    }

    public void setSort_letter(String sort_letter) {
        this.sort_letter = sort_letter;
    }

    public int getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(int is_friend) {
        this.is_friend = is_friend;
    }

    public int getPrinciple_id() {
        return principle_id;
    }

    public void setPrinciple_id(int principle_id) {
        this.principle_id = principle_id;
    }
}
