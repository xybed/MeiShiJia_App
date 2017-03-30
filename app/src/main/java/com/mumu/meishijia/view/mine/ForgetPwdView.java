package com.mumu.meishijia.view.mine;

/**
 * 忘记密码的view
 * Created by Administrator on 2017/3/29.
 */

public interface ForgetPwdView {
    void onTimerRunning(int totalTime);
    void onTimerEnd();
    void modifySuccess(String result);
    void modifyFail(String errMsg);
}
