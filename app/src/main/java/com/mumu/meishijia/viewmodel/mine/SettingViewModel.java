package com.mumu.meishijia.viewmodel.mine;

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
//        httpRetrofit.getModel(httpRetrofit.getApiService(UserApi.class, HttpUrl.Logout, params).logout(params.urlParams), "", new RetroResListener<String>() {
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
