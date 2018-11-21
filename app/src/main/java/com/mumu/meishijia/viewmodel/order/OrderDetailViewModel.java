package com.mumu.meishijia.viewmodel.order;

import com.mumu.meishijia.api.OrderApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.order.Order;
import com.mumu.meishijia.viewmodel.BaseViewModel;

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
}
