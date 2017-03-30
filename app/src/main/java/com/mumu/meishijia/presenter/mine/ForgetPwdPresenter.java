package com.mumu.meishijia.presenter.mine;

import com.mumu.meishijia.view.mine.ForgetPwdView;
import com.mumu.meishijia.viewmodel.mine.ForgetPwdViewModel;

import lib.utils.MD5Util;
import lib.utils.TimerUtil;

/**
 * 忘记密码的presenter
 * Created by Administrator on 2017/3/29.
 */

public class ForgetPwdPresenter implements ForgetPwdViewModel.ForgetPwdListener, TimerUtil.TimerListener {

    private ForgetPwdView view;
    private ForgetPwdViewModel viewModel;
    private TimerUtil timerUtil;

    public ForgetPwdPresenter(ForgetPwdView view){
        this.view = view;
        viewModel = new ForgetPwdViewModel(this);
        timerUtil = new TimerUtil(60, 60, this);
    }

    public void startTimer(){
        timerUtil.startTimer();
    }

    public void stopTimer(){
        timerUtil.stopTimer();
    }

    public void modifyPwd(String username, String password){
        password = MD5Util.MD5(password);
        viewModel.modifyPwd(username, password);
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
    public void modifySuccess(String result) {
        view.modifySuccess(result);
    }

    @Override
    public void modifyFail(String errMsg) {
        view.modifyFail(errMsg);
    }
}
