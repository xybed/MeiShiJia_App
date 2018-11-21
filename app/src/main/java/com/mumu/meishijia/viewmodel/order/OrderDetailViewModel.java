package com.mumu.meishijia.viewmodel.order;

import com.mumu.meishijia.api.OrderApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.order.Order;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 77 on 2018/11/21 0021.
 * 订单详情ViewModel
 */

public class OrderDetailViewModel extends BaseViewModel{
    public Observable<Order> getOrderDetail(int orderId){
        return HttpRetrofit.create(OrderApi.class)
                .getOrderDetail(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new HttpResultFunc<Order>());
    }

    public Observable<String> updateOrderStatus(int orderId, int status){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", orderId);
        params.put("status", status);
        return HttpRetrofit.create(OrderApi.class)
                .updateOrderStatus(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new HttpResultFunc<String>());
    }
}
