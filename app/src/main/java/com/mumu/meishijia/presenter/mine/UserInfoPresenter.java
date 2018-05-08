package com.mumu.meishijia.presenter.mine;

import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.mine.UserInfoView;
import com.mumu.meishijia.viewmodel.mine.UserInfoViewModel;

/**
 * 个人资料的presenter
 * Created by Administrator on 2017/3/30.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoView, UserInfoViewModel>{

    public UserInfoPresenter(BaseView view){
        super(view);
    }

    public void modifyUserInfo(String id, String nickname, String realName,
                               String sex, String birthday, String email, String province, String city, String signature){
        model.modifyUserInfo(id, nickname, realName, sex, birthday, email, province, city, signature)
                .subscribe(new RxObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if(view != null)
                            view.modifySuccess(s);
                    }
                });
    }

    public void modifyAvatar(String filePath){
        model.modifyAvatar(filePath)
                .subscribe(new RxObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if(view != null)
                            view.modifyAvatarSuccess(s);
                    }
                });
    }

}
