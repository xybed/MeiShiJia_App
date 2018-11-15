package com.mumu.meishijia.viewmodel.product;

import com.mumu.meishijia.api.OrderApi;
import com.mumu.meishijia.api.ProductApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.product.Product;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 77 on 2018/8/17 0017.
 */

public class ProductDetailViewModel extends BaseViewModel{
    public Observable<Product> getProductDetail(int id){
        return HttpRetrofit.create(ProductApi.class)
                .getProductDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<Product>());
    }

    public Observable<String> addShoppingCart(int userId, int productId, int num){
        Map<String, Integer> params = new HashMap<>();
        params.put("userId", userId);
        params.put("productId", productId);
        params.put("num", num);
        return HttpRetrofit.create(OrderApi.class)
                .addShoppingCart(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new HttpResultFunc<String>());
    }
}
