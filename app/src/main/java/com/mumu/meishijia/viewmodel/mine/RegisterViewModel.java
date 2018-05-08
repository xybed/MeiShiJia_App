package com.mumu.meishijia.viewmodel.mine;

import com.mumu.meishijia.model.mine.UserModel;

/**
 * 注册的viewModel
 * Created by Administrator on 2017/3/28.
 */
public class RegisterViewModel {

    public RegisterViewModel(RegisterListener listener){
        this.listener = listener;
    }

    public void register(String username, String password, String verifyCode){
//        HttpRequestParams params = new HttpRequestParams();
//        params.put("username", username);
//        params.put("password", password);
//        params.put("verify_code", verifyCode);
//        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
//        httpRetrofit.getModel(httpRetrofit.getApiService(UserApi.class, HttpUrl.Register, params).register(params.urlParams), "", new RetroResListener<String>() {
//            @Override
//            protected void onSuccess(String result) {
//                if(listener != null)
//                    listener.registerSuccess(result);
//            }
//
//            @Override
//            protected void onFailure(String errMsg) {
//                if(listener != null)
//                    listener.registerFail(errMsg);
//            }
//        });
    }

    public void login(String username, String password){
//        HttpRequestParams params = new HttpRequestParams();
//        params.put("username", username);
//        params.put("password", password);
//        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
//        httpRetrofit.getModel(httpRetrofit.getApiService(UserApi.class, HttpUrl.Login, params).login(params.urlParams), UserModel.class.getSimpleName(), new RetroResListener<UserModel>() {
//            @Override
//            protected void onSuccess(UserModel result) {
//                if(listener != null)
//                    listener.loginSuccess(result);
//            }
//
//            @Override
//            protected void onFailure(String errMsg) {
//                if(listener != null)
//                    listener.loginFail(errMsg);
//            }
//        });
    }

    private RegisterListener listener;
    public interface RegisterListener{
        void registerSuccess(String result);
        void registerFail(String errMsg);
        void loginSuccess(UserModel result);
        void loginFail(String errMsg);
    }
}
