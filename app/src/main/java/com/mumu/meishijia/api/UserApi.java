package com.mumu.meishijia.api;

import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.model.BaseModel;
import com.mumu.meishijia.model.mine.User;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 注册的api
 * Created by Administrator on 2017/3/28.
 */
public interface UserApi {
    @POST(HttpUrl.Register)
    Observable<BaseModel<String>> register(@Body Map<String, String> map);
    @POST(HttpUrl.Login)
    Observable<BaseModel<User>> login(@Body Map<String, String> map);
    @POST(HttpUrl.Logout)
    Observable<BaseModel<String>> logout(@Body Map<String, String> map);
    @POST(HttpUrl.ModifyPwd)
    Observable<BaseModel<String>> modifyPwd(@Body Map<String, String> map);
    @POST(HttpUrl.ModifyUserInfo)
    Observable<BaseModel<String>> modifyUserInfo(@Body Map<String, String> map);
}
