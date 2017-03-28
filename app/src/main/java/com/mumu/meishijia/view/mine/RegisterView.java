package com.mumu.meishijia.view.mine;

/**
 * Created by Administrator on 2017/3/27.
 */

public interface RegisterView {
    void onTimerRunning(int totalTime);
    void onTimerEnd();
    void registerSuccess(String result);
    void registerFail(String errMsg);
}
