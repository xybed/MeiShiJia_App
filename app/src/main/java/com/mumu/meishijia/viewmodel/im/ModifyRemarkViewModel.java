package com.mumu.meishijia.viewmodel.im;

import com.mumu.meishijia.api.ImApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 备注信息的viewModel
 * Created by Administrator on 2017/4/11.
 */

public class ModifyRemarkViewModel extends BaseViewModel{

    public Observable<String> modifyRemark(int userId, int friendId, String remark){
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId+"");
        params.put("friend_id", friendId+"");
        params.put("remark", remark);
        return HttpRetrofit.create(ImApi.class)
                .modifyRemark(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<String>());
    }

}
