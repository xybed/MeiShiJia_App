package com.mumu.meishijia.view.mine;

import com.mumu.meishijia.view.BaseView;

/**
 * 个人资料的view
 * Created by Administrator on 2017/3/30.
 */

public interface UserInfoView extends BaseView{
    void modifySuccess(String result);
    void uploadSuccess(String result);
}
