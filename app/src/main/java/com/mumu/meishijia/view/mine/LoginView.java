package com.mumu.meishijia.view.mine;

import com.mumu.meishijia.model.mine.UserModel;

/**
 * 登录view的interface
 * Created by Administrator on 2017/3/29.
 */
public interface LoginView {
    void loginSuccess(UserModel result);
    void loginFail(String errMsg);
}
