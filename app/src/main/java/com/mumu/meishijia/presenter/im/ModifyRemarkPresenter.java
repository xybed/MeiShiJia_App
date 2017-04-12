package com.mumu.meishijia.presenter.im;

import com.mumu.meishijia.view.im.ModifyRemarkView;
import com.mumu.meishijia.viewmodel.im.ModifyRemarkViewModel;

/**
 * 备注信息的presenter
 * Created by Administrator on 2017/4/11.
 */

public class ModifyRemarkPresenter implements ModifyRemarkViewModel.ModifyRemarkListener{

    private ModifyRemarkView view;
    private ModifyRemarkViewModel viewModel;

    public ModifyRemarkPresenter(ModifyRemarkView view) {
        this.view = view;
        viewModel = new ModifyRemarkViewModel(this);
    }

    public void modifyRemark(int userId, int friendId, String remark){
        viewModel.modifyRemark(userId, friendId, remark);
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
