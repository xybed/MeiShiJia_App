package com.mumu.meishijia.presenter.mine;

import com.mumu.meishijia.model.mine.User;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.mine.LoginView;
import com.mumu.meishijia.viewmodel.mine.LoginViewModel;

import lib.utils.MD5Util;

/**
 * 登录的presenter
 * Created by Administrator on 2017/3/29.
 */

public class LoginPresenter extends BasePresenter<LoginView, LoginViewModel>{

    public LoginPresenter(BaseView view){
        super(view);
    }

    public void login(String username, String password){
        password = MD5Util.MD5(password);
        model.login(username, password)
                .subscribe(new RxObserver<User>() {
                    @Override
                    protected void onSuccess(User user) {
                        if(view != null)
                            view.loginSuccess(user);
                    }
                });
    }

}
