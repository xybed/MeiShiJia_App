package com.mumu.meishijia.viewmodel.mine;

import com.mumu.meishijia.api.mine.UserApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.http.RxObserver;
import com.mumu.meishijia.model.mine.UserModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录的viewModel
 * Created by Administrator on 2017/3/29.
 */

public class LoginViewModel {

    public LoginViewModel(LoginListener listener){
        this.listener = listener;
    }

    public void login(String username, String password){
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        HttpRetrofit.create(UserApi.class)
                .login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<UserModel>())
                .subscribe(new RxObserver<UserModel>() {
                    @Override
                    protected void onSuccess(UserModel userModel) {

                    }
                });
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

    private LoginListener listener;
    public interface LoginListener{
        void loginSuccess(UserModel result);
        void loginFail(String errMsg);
    }
}
