package com.mumu.meishijia.view.mine;

import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.view.BaseView;

/**
 * 登录view的interface
 * Created by Administrator on 2017/3/29.
 */
public interface LoginView extends BaseView{
    void loginSuccess(UserModel result);
}
