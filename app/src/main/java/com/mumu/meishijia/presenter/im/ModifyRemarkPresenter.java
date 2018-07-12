package com.mumu.meishijia.presenter.im;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.im.ModifyRemarkView;
import com.mumu.meishijia.viewmodel.im.ModifyRemarkViewModel;

/**
 * 备注信息的presenter
 * Created by Administrator on 2017/4/11.
 */

public class ModifyRemarkPresenter extends BasePresenter<ModifyRemarkView, ModifyRemarkViewModel>{


    public ModifyRemarkPresenter(BaseView view) {
        super(view);
    }

    public void modifyRemark(int friendId, String remark){
        int userId = MyApplication.getInstance().getUser().getId();
        model.modifyRemark(userId, friendId, remark)
                .subscribe(new RxObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if(view != null)
                            view.modifySuccess(s);
                    }
                });
    }

}
