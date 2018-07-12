package com.mumu.meishijia.view.mine;

import com.mumu.meishijia.model.mine.User;
import com.mumu.meishijia.view.BaseView;

/**
 * Created by Administrator on 2017/3/27.
 */

public interface RegisterView extends BaseView{
    void onTimerRunning(int totalTime);
    void onTimerEnd();
    void registerSuccess(String result);
    void loginSuccess(User result);
}
