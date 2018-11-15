package com.mumu.meishijia.viewmodel.order;

import com.mumu.meishijia.api.OrderApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.order.ShoppingCart;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 77 on 2018/11/12 0012.
 * 购物车ViewModel
 */

public class ShoppingCartViewModel extends BaseViewModel{

    public Observable<List<ShoppingCart>> getShoppingCart(int userId){
        Map<String, Integer> params = new HashMap<>();
        params.put("user_id", userId);
        return HttpRetrofit.create(OrderApi.class)
                .getShoppingCart(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new HttpResultFunc<List<ShoppingCart>>());
    }
}
