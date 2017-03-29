package com.mumu.meishijia.view.mine;

import com.mumu.meishijia.model.mine.UserModel;

/**
 * Created by Administrator on 2017/3/27.
 */

public interface RegisterView {
    void onTimerRunning(int totalTime);
    void onTimerEnd();
    void registerSuccess(String result);
    void registerFail(String errMsg);
    void loginSuccess(UserModel result);
    void loginFail(String errMsg);
}
