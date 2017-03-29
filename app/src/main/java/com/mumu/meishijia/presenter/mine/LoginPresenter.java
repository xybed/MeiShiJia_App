package com.mumu.meishijia.presenter.mine;

import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.view.mine.LoginView;
import com.mumu.meishijia.viewmodel.mine.LoginViewModel;

import lib.utils.MD5Util;

/**
 * 登录的presenter
 * Created by Administrator on 2017/3/29.
 */

public class LoginPresenter implements LoginViewModel.LoginListener{
    private LoginView view;
    private LoginViewModel viewModel;

    public LoginPresenter(LoginView view){
        this.view = view;
        viewModel = new LoginViewModel(this);
    }

    public void login(String username, String password){
        password = MD5Util.MD5(password);
        viewModel.login(username, password);
    }

    @Override
    public void loginSuccess(UserModel result) {
        view.loginSuccess(result);
    }

    @Override
    public void loginFail(String errMsg) {
        view.loginFail(errMsg);
    }
}
