package com.mumu.meishijia.viewmodel.product;

import com.mumu.meishijia.api.ProductApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.product.Product;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 77 on 2018/8/10 0010.
 */

public class ProductListViewModel extends BaseViewModel{

    public Observable<List<Product>> getProductList(int categoryId, int pageIndex, int pageSize){
        Map<String, Integer> params = new HashMap<>();
        params.put("category_id", categoryId);
        params.put("page_index", pageIndex);
        params.put("page_size", pageSize);
        return HttpRetrofit.create(ProductApi.class)
                .getProductList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<List<Product>>());
    }

    public Observable<List<Product>> searchProductList(String keyword, int pageIndex, int pageSize){
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("page_index", pageIndex);
        params.put("page_size", pageSize);
        return HttpRetrofit.create(ProductApi.class)
                .searchProductList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<List<Product>>());
    }
}
