package com.mumu.meishijia.api.mine;

import com.mumu.meishijia.model.BaseModel;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 注册的api
 * Created by Administrator on 2017/3/28.
 */
public interface UserService {
    @POST("user/register")
    Observable<BaseModel<String>> register(@QueryMap Map<String, String> map);
}
