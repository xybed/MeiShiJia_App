package com.mumu.meishijia.presenter.mine;

import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.mine.SettingView;
import com.mumu.meishijia.viewmodel.mine.SettingViewModel;

/**
 * 设置的presenter
 * Created by Administrator on 2017/3/30.
 */

public class SettingPresenter extends BasePresenter<SettingView, SettingViewModel>{

    public SettingPresenter(BaseView view){
        super(view);
    }

    public void logout(){
        model.logout()
                .subscribe(new RxObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if(view != null)
                            view.logoutSuccess(s);
                    }
                });
    }

}
