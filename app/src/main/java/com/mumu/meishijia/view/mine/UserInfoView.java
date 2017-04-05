package com.mumu.meishijia.view.mine;

/**
 * 个人资料的view
 * Created by Administrator on 2017/3/30.
 */

public interface UserInfoView {
    void modifySuccess(String result);
    void modifyFail(String errMsg);
    void modifyAvatarSuccess(String result);
    void modifyAvatarFail(String errMsg);
}
