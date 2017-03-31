package com.mumu.meishijia.api.mine;

import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.model.BaseModel;
import com.mumu.meishijia.model.mine.UserModel;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 注册的api
 * Created by Administrator on 2017/3/28.
 */
public interface UserService {
    @POST(HttpUrl.Register)
    Observable<BaseModel<String>> register(@QueryMap Map<String, String> map);
    @POST(HttpUrl.Login)
    Observable<BaseModel<UserModel>> login(@QueryMap Map<String, String> map);
    @POST(HttpUrl.Logout)
    Observable<BaseModel<String>> logout(@QueryMap Map<String, String> map);
    @POST(HttpUrl.ModifyPwd)
    Observable<BaseModel<String>> modifyPwd(@QueryMap Map<String, String> map);
    @POST(HttpUrl.ModifyUserInfo)
    Observable<BaseModel<String>> modifyUserInfo(@QueryMap Map<String, String> map);
}
