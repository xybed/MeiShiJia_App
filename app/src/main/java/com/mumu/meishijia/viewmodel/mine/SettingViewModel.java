package com.mumu.meishijia.viewmodel.mine;

import com.mumu.meishijia.api.mine.UserApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 设置的viewModel
 * Created by Administrator on 2017/3/30.
 */

public class SettingViewModel extends BaseViewModel{

    public Observable<String> logout(){
        Map<String, String> params = new HashMap<>();
        return HttpRetrofit.create(UserApi.class)
                .logout(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<String>());
    }

}
