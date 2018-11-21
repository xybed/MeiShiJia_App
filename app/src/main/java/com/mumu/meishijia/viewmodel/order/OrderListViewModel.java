package com.mumu.meishijia.viewmodel.order;

import com.mumu.meishijia.api.OrderApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.order.Order;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 77 on 2018/11/20 0020.
 * 订单列表ViewModel
 */

public class OrderListViewModel extends BaseViewModel{
    public Observable<List<Order>> getOrderList(int userId, int type, int pageIndex, int pageSize){
        Map<String, Integer> params = new HashMap<>();
        params.put("userId", userId);
        if(type != -1){
            params.put("type", type);
        }
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        return HttpRetrofit.create(OrderApi.class)
                .getOrderList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new HttpResultFunc<List<Order>>());
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
