package com.mumu.meishijia.viewmodel.mine;

import com.mumu.meishijia.api.mine.UserService;
import com.mumu.meishijia.http.HttpRequestParams;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.http.RetroResListener;

/**
 * 设置的viewModel
 * Created by Administrator on 2017/3/30.
 */

public class SettingViewModel {

    public SettingViewModel(SettingListener listener){
        this.listener = listener;
    }

    public void logout(){
//        HttpRequestParams params = new HttpRequestParams();
//        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
//        httpRetrofit.getModel(httpRetrofit.getApiService(UserService.class, HttpUrl.Logout, params).logout(params.urlParams), "", new RetroResListener<String>() {
//            @Override
//            protected void onSuccess(String result) {
//                if(listener != null)
//                    listener.logoutSuccess(result);
//            }
//
//            @Override
//            protected void onFailure(String errMsg) {
//                if(listener != null)
//                    listener.logoutFail(errMsg);
//            }
//        });
    }

    private SettingListener listener;
    public interface SettingListener{
        void logoutSuccess(String result);
        void logoutFail(String errMsg);
    }
}
