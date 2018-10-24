package com.mumu.meishijia.viewmodel.mine;

import com.mumu.meishijia.api.OrderApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 77 on 2018/10/24 0024.
 * 收货地址信息model
 */

public class ReceivingAddressEditModel extends BaseViewModel{

    public Observable<String> addReceivingAddress(int userId, String name, String phone,
                      String province, String city, String address, boolean type){
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("name", name);
        params.put("phone", phone);
        params.put("province", province);
        params.put("city", city);
        params.put("address", address);
        params.put("type", type ? 1 : 0);
        return HttpRetrofit.create(OrderApi.class)
                .addReceivingAddress(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new HttpResultFunc<String>());
    }

    public Observable<String> updateReceivingAddress(int id, String name, String phone,
                     String province, String city, String address, boolean type){
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        params.put("phone", phone);
        params.put("province", province);
        params.put("city", city);
        params.put("address", address);
        params.put("type", type ? 1 : 0);
        return HttpRetrofit.create(OrderApi.class)
                .updateReceivingAddress(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new HttpResultFunc<String>());
    }
}
