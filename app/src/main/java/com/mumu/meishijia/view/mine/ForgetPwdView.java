package com.mumu.meishijia.view.mine;

import com.mumu.meishijia.view.BaseView;

/**
 * 忘记密码的view
 * Created by Administrator on 2017/3/29.
 */

public interface ForgetPwdView extends BaseView{
    void onTimerRunning(int totalTime);
    void onTimerEnd();
    void modifySuccess(String result);
}
