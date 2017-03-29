package com.mumu.meishijia.viewmodel.mine;

import com.mumu.meishijia.api.mine.UserService;
import com.mumu.meishijia.http.HttpRequestParams;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.http.RetroResListener;
import com.mumu.meishijia.model.mine.UserModel;

/**
 * 登录的viewModel
 * Created by Administrator on 2017/3/29.
 */

public class LoginViewModel {

    public LoginViewModel(LoginListener listener){
        this.listener = listener;
    }

    public void login(String username, String password){
        HttpRequestParams params = new HttpRequestParams();
        params.put("username", username);
        params.put("password", password);
        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
        httpRetrofit.getModel(httpRetrofit.getApiService(UserService.class, HttpUrl.Login, params).login(params.urlParams), UserModel.class.getSimpleName(), new RetroResListener<UserModel>() {
            @Override
            protected void onSuccess(UserModel result) {
                if(listener != null)
                    listener.loginSuccess(result);
            }

            @Override
            protected void onFailure(String errMsg) {
                if(listener != null)
                    listener.loginFail(errMsg);
            }
        });
    }

    private LoginListener listener;
    public interface LoginListener{
        void loginSuccess(UserModel result);
        void loginFail(String errMsg);
    }
}
