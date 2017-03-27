package com.mumu.meishijia.presenter.mine;

import com.mumu.meishijia.view.mine.RegisterView;

import lib.utils.TimerUtil;

/**
 * Created by Administrator on 2017/3/27.
 */

public class RegisterPresenter implements TimerUtil.TimerListener{

    private RegisterView view;
    private TimerUtil timerUtil;

    public RegisterPresenter(RegisterView view){
        this.view = view;
        timerUtil = new TimerUtil(10, 10, this);
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
