package com.mumu.meishijia.viewmodel.product;

import com.mumu.meishijia.api.ProductApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.product.ProductCategory;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 77 on 2018/7/30 0030.
 */

public class ProductCategoryViewModel extends BaseViewModel{

    public Observable<List<ProductCategory>> getProductCategory(int id){
        return HttpRetrofit.create(ProductApi.class)
                .getProductCategory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<List<ProductCategory>>());
    }
}
