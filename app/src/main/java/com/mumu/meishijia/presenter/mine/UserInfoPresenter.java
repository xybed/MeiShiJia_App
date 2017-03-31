package com.mumu.meishijia.presenter.mine;

import com.mumu.meishijia.view.mine.UserInfoView;
import com.mumu.meishijia.viewmodel.mine.UserInfoViewModel;

/**
 * 个人资料的presenter
 * Created by Administrator on 2017/3/30.
 */

public class UserInfoPresenter implements UserInfoViewModel.UserInfoListener{

    private UserInfoView view;
    private UserInfoViewModel viewModel;

    public UserInfoPresenter(UserInfoView view){
        this.view = view;
        viewModel = new UserInfoViewModel(this);
    }

    public void modifyUserInfo(String id, String nickname, String realName,
                               String sex, String birthday, String email, String city){
        viewModel.modifyUserInfo(id, nickname, realName, sex, birthday, email, city);
    }

    @Override
    public void modifySuccess(String result) {
        view.modifySuccess(result);
    }

    @Override
    public void modifyFail(String errMsg) {
        view.modifyFail(errMsg);
    }
}
