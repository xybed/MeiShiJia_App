package com.mumu.meishijia.http;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mumu.meishijia.BuildConfig;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.model.BaseModel;
import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.view.mine.LoginActivity;

import java.net.SocketTimeoutException;
import java.util.List;

import lib.cache.CacheJsonMgr;
import lib.utils.MD5Util;
import lib.utils.MyLogUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Retrofit统一处理类
 * Created by 7mu on 2016/8/24.
 */
public class HttpRetrofit {
    private static HttpRetrofit httpRetrofit;
    private Retrofit retrofit;
    private Context context;
    //缓存管理
    private CacheJsonMgr cacheJsonMgr;

    private HttpRetrofit(Context context){
        this.context = context;
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(BuildConfig.Base_Api_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        cacheJsonMgr = CacheJsonMgr.getInstance(context);
    }

    public static HttpRetrofit getInstance(){
        if(httpRetrofit == null)
            httpRetrofit = new HttpRetrofit(MyApplication.getInstance());
        return httpRetrofit;
    }

    public <T> T getApiService(Class<T> clazz, String url, HttpRequestParams params){
        addBaseParams(url, params);
        return retrofit.create(clazz);
    }

    private void addBaseParams(String url, HttpRequestParams params){
        //添加必要请求参数
        params.put("ver", BuildConfig.DEVELOP_VERSION);
        params.put("platform", "android");
        if(MyApplication.getInstance().isLogin()){
            params.put("token", MyApplication.getInstance().getUser().getToken());
        }else{
            params.put("token", "");
        }
        params.put("sign", MD5Util.createParamSign(params.urlParams, HttpUrl.TOKEN_KEY));
        url = (url.startsWith("http") ? "" : HttpUrl.BASE_API_URL) + url;
        MyLogUtil.e("http_请求url", url + (url.contains("?") ? "" : "?") + params);
    }

    public <T> Subscriber getModel(Observable<BaseModel<T>> observable, final String cacheKey, final RetroResListener<T> retroResListener){
        Subscriber<T> subscriber = new Subscriber<T>() {
            @Override
            public void onCompleted() {
                retroResListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                MyLogUtil.e("http_错误", e.toString());
                if(e instanceof SocketTimeoutException){
                    retroResListener.onFailure("连接超时");
                }else if(e instanceof JsonSyntaxException){
                    retroResListener.onFailure("json格式不符");
                }else if("未登录".equals(e.getMessage())){
                    retroResListener.onFailure(e.getMessage());
                    goLogin();
                }else{
                    retroResListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(T t) {
                if(!TextUtils.isEmpty(cacheKey)){
                    cacheJsonMgr.saveJson(new Gson().toJson(t), cacheKey);
                }
                retroResListener.onSuccess(t);
            }
        };
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<T>())
                .subscribe(subscriber);
        return subscriber;
    }

    public <T> Subscriber getList(Observable<BaseModel<List<T>>> observable, final String cacheKey, final RetroResListener<List<T>> retroResListener){
        Subscriber<List<T>> subscriber = new Subscriber<List<T>>() {
            @Override
            public void onCompleted() {
                retroResListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                MyLogUtil.e("http_错误", e.toString());
                if(e instanceof SocketTimeoutException){
                    retroResListener.onFailure("连接超时");
                }else if(e instanceof JsonSyntaxException){
                    retroResListener.onFailure("json格式不符");
                }else if("未登录".equals(e.getMessage())){
                    retroResListener.onFailure(e.getMessage());
                    goLogin();
                }else{
                    retroResListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(List<T> t) {
                if(!TextUtils.isEmpty(cacheKey)){
                    cacheJsonMgr.saveJson(new Gson().toJson(t), cacheKey);
                }
                retroResListener.onSuccess(t);
            }
        };
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<List<T>>())
                .subscribe(subscriber);
        return subscriber;
    }

    public void cancelRequest(Subscriber subscriber){
        subscriber.unsubscribe();
    }

    private void goLogin(){
        MyApplication.getInstance().setUser(null);
        MyApplication.getInstance().setLogin(false);
        CacheJsonMgr.getInstance(context).deleteJson(UserModel.class.getSimpleName());
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
