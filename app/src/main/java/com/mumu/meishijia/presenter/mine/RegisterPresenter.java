package com.mumu.meishijia.presenter.mine;

import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.view.mine.RegisterView;
import com.mumu.meishijia.viewmodel.mine.RegisterViewModel;

import lib.utils.MD5Util;
import lib.utils.TimerUtil;

/**
 * 注册的presenter
 * Created by Administrator on 2017/3/27.
 */

public class RegisterPresenter implements RegisterViewModel.RegisterListener, TimerUtil.TimerListener{

    private RegisterView view;
    private RegisterViewModel viewModel;
    private TimerUtil timerUtil;

    public RegisterPresenter(RegisterView view){
        this.view = view;
        viewModel = new RegisterViewModel(this);
        timerUtil = new TimerUtil(10, 10, this);
    }

    public void register(String username, String password, String verifyCode){
        password = MD5Util.MD5(password);
        viewModel.register(username, password, verifyCode);
    }

    public void login(String username, String password){
        password = MD5Util.MD5(password);
        viewModel.login(username, password);
    }

    public void startTimer(){
        timerUtil.startTimer();
    }

    public void stopTimer(){
        timerUtil.stopTimer();
    }

    @Override
    public void onTimerRunning(int totalTime) {
        view.onTimerRunning(totalTime);
    }

    @Override
    public void onTimerEnd() {
        view.onTimerEnd();
    }

    @Override
    public void registerSuccess(String result) {
        view.registerSuccess(result);
    }

    @Override
    public void registerFail(String errMsg) {
        view.registerFail(errMsg);
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
