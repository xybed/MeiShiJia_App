package com.mumu.meishijia.presenter.mine;

import com.mumu.meishijia.view.mine.SettingView;
import com.mumu.meishijia.viewmodel.mine.SettingViewModel;

/**
 * 设置的presenter
 * Created by Administrator on 2017/3/30.
 */

public class SettingPresenter implements SettingViewModel.SettingListener{

    private SettingView view;
    private SettingViewModel viewModel;

    public SettingPresenter(SettingView view){
        this.view = view;
        viewModel = new SettingViewModel(this);
    }

    public void logout(){
        viewModel.logout();
    }

    @Override
    public void logoutSuccess(String result) {
        view.logoutSuccess(result);
    }

    @Override
    public void logoutFail(String errMsg) {
        view.logoutFail(errMsg);
    }
}
