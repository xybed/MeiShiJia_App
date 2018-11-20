package com.mumu.meishijia.api;

import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.model.BaseModel;
import com.mumu.meishijia.model.mine.ReceivingAddress;
import com.mumu.meishijia.model.order.ShoppingCart;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by 77 on 2018/10/23 0023.
 * 有关订单的api
 */

public interface OrderApi {
    @GET(HttpUrl.GetReceivingAddress)
    Observable<BaseModel<List<ReceivingAddress>>> getReceivingAddress(@QueryMap Map<String, Integer> map);

    @POST(HttpUrl.ReceivingAddress)
    Observable<BaseModel<String>> addReceivingAddress(@Body Map<String, Object> map);

    @PUT(HttpUrl.ReceivingAddress)
    Observable<BaseModel<String>> updateReceivingAddress(@Body Map<String, Object> map);

    @DELETE(HttpUrl.DeleteReceivingAddress)
    Observable<BaseModel<String>> deleteReceivingAddress(@Path("id") Integer id);

    @GET(HttpUrl.ReceivingAddress)
    Observable<BaseModel<ReceivingAddress>> getDefaultReceivingAddress(@QueryMap Map<String, Integer> map);

    @GET(HttpUrl.GetShoppingCart)
    Observable<BaseModel<List<ShoppingCart>>> getShoppingCart(@QueryMap Map<String, Integer> map);

    @POST(HttpUrl.ShoppingCart)
    Observable<BaseModel<String>> addShoppingCart(@Body Map<String, Integer> map);

    @PUT(HttpUrl.ShoppingCart)
    Observable<BaseModel<String>> updateShoppingCart(@Body Map<String, String> map);

    @DELETE(HttpUrl.ShoppingCart)
    Observable<BaseModel<String>> deleteShoppingCart(@QueryMap Map<String, String> map);

    @PUT(HttpUrl.ClearShoppingCart)
    Observable<BaseModel<String>> clearShoppingCart(@Body Map<String, Integer> map);

    @POST(HttpUrl.Order)
    Observable<BaseModel<Integer>> order(@Body Map<String, Object> map);
}
