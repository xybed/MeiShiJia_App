package com.mumu.meishijia.viewmodel.mine;

import com.mumu.meishijia.api.OrderApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.mine.ReceivingAddress;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 77 on 2018/10/23 0023.
 * 收货地址列表model
 */

public class ReceivingAddressViewModel extends BaseViewModel{

    public Observable<List<ReceivingAddress>> getReceivingAddress(Integer userId){
        Map<String, Integer> params = new HashMap<>();
        params.put("user_id", userId);
        return HttpRetrofit.create(OrderApi.class)
                .getReceivingAddress(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new HttpResultFunc<List<ReceivingAddress>>());
    }

    public Observable<String> deleteReceivingAddress(Integer id){
        return HttpRetrofit.create(OrderApi.class)
                .deleteReceivingAddress(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new HttpResultFunc<String>());
    }
}
