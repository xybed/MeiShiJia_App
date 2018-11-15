package com.mumu.meishijia.viewmodel.order;

import com.google.gson.Gson;
import com.mumu.meishijia.api.OrderApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.order.ShoppingCart;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    public Observable<String> deleteShoppingCart(List<Integer> idList){
        Map<String, String> params = new HashMap<>();
        String json = new Gson().toJson(idList);
        try {
            json = URLEncoder.encode(json, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.put("json", json);
        return HttpRetrofit.create(OrderApi.class)
                .deleteShoppingCart(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new HttpResultFunc<String>());
    }
}
