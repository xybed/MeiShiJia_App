package com.mumu.meishijia.viewmodel.mine;

import com.mumu.meishijia.api.UserApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 注册的viewModel
 * Created by Administrator on 2017/3/28.
 */
public class RegisterViewModel extends BaseViewModel{

    public Observable<String> register(String username, String password, String verifyCode){
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("verify_code", verifyCode);
        return HttpRetrofit.create(UserApi.class)
                .register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<String>());
    }

    public Observable<UserModel> login(String username, String password){
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        return HttpRetrofit.create(UserApi.class)
                .login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<UserModel>());
    }

}
