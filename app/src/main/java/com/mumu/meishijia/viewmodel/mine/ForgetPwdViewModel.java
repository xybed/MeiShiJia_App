package com.mumu.meishijia.viewmodel.mine;

import com.mumu.meishijia.api.UserApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 忘记密码的viewModel
 * Created by Administrator on 2017/3/29.
 */

public class ForgetPwdViewModel extends BaseViewModel{

    public Observable<String> modifyPwd(String username, String password){
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        return HttpRetrofit.create(UserApi.class)
                .modifyPwd(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<String>());
    }

}
