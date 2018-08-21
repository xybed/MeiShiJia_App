package com.mumu.meishijia.api;

import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.model.BaseModel;
import com.mumu.meishijia.model.product.Product;
import com.mumu.meishijia.model.product.ProductCategory;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 有关商品的api
 * Created by 77 on 2018/7/30 0030.
 */

public interface ProductApi {
    @GET(HttpUrl.GetProductCategory)
    Observable<BaseModel<List<ProductCategory>>> getProductCategory(@Path("id") int id);

    @GET(HttpUrl.GetProductList)
    Observable<BaseModel<List<Product>>> getProductList(@QueryMap Map<String, Integer> map);

    @GET(HttpUrl.SearchProductList)
    Observable<BaseModel<List<Product>>> searchProductList(@QueryMap Map<String, Object> map);

    @GET(HttpUrl.GetProductDetail)
    Observable<BaseModel<Product>> getProductDetail(@Path("id") int id);
}
