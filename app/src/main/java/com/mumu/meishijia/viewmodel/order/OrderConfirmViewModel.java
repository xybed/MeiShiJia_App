package com.mumu.meishijia.viewmodel.order;

import com.mumu.meishijia.api.OrderApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.mine.ReceivingAddress;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 77 on 2018/11/8 0008.
 * 确认订单ViewModel
 */

public class OrderConfirmViewModel extends BaseViewModel{

    public Observable<ReceivingAddress> getDefaultReceivingAddress(int userId){
        Map<String, Integer> params = new HashMap<>();
        params.put("user_id", userId);
        return HttpRetrofit.create(OrderApi.class)
                .getDefaultReceivingAddress(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new HttpResultFunc<ReceivingAddress>());
    }
}
