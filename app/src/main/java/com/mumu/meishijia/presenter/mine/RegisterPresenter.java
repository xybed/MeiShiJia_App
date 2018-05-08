package com.mumu.meishijia.presenter.mine;

import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.mine.RegisterView;
import com.mumu.meishijia.viewmodel.mine.RegisterViewModel;

import lib.utils.MD5Util;
import lib.utils.TimerUtil;

/**
 * 注册的presenter
 * Created by Administrator on 2017/3/27.
 */

public class RegisterPresenter extends BasePresenter<RegisterView, RegisterViewModel> implements TimerUtil.TimerListener{

    private TimerUtil timerUtil;

    public RegisterPresenter(BaseView view){
        super(view);
        timerUtil = new TimerUtil(60, 60, this);
    }

    public void register(String username, String password, String verifyCode){
        password = MD5Util.MD5(password);
        model.register(username, password, verifyCode)
                .subscribe(new RxObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if(view != null)
                            view.registerSuccess(s);
                    }
                });
    }

    public void login(String username, String password){
        password = MD5Util.MD5(password);
        model.login(username, password)
                .subscribe(new RxObserver<UserModel>() {
                    @Override
                    protected void onSuccess(UserModel userModel) {
                        if(view != null)
                            view.loginSuccess(userModel);
                    }
                });
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

}
