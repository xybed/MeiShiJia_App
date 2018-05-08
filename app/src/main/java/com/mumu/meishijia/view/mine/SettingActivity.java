package com.mumu.meishijia.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hwangjr.rxbus.RxBus;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.constant.RxBusAction;
import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.presenter.mine.SettingPresenter;
import com.mumu.meishijia.tencent.IMUtil;
import com.mumu.meishijia.view.BaseActivity;
import com.mumu.meishijia.view.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.cache.CacheJsonMgr;

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        RxBus.get().register(this);
    }

    @OnClick({R.id.btn_left, R.id.llay_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.llay_logout:
                showLoadingDialog(getString(R.string.user_logging_off), false, false);
                presenter.logout();
                break;
        }
    }

    @Override
    public void logoutSuccess(String result) {
        dismissLoadingDialog();
        //退出登录
        MyApplication.getInstance().setUser(null);
        MyApplication.getInstance().setLogin(false);
        //登出腾讯im
        IMUtil.getInstance().logoutIM();
        //清空用户信息
        CacheJsonMgr.getInstance(this).deleteJson(UserModel.class.getSimpleName());
        RxBus.get().post(RxBusAction.MineUserData, "");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
        super.onDestroy();
    }
}
