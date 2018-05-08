package com.mumu.meishijia.presenter.mine;

import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.mine.ForgetPwdView;
import com.mumu.meishijia.viewmodel.mine.ForgetPwdViewModel;

import lib.utils.MD5Util;
import lib.utils.TimerUtil;

/**
 * 忘记密码的presenter
 * Created by Administrator on 2017/3/29.
 */

public class ForgetPwdPresenter extends BasePresenter<ForgetPwdView, ForgetPwdViewModel> implements TimerUtil.TimerListener {

    private TimerUtil timerUtil;

    public ForgetPwdPresenter(BaseView view){
        super(view);
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
        model.modifyPwd(username, password)
                .subscribe(new RxObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if(view != null)
                            view.modifySuccess(s);
                    }
                });
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
