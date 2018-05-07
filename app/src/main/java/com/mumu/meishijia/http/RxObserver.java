package com.mumu.meishijia.http;

import com.google.gson.JsonSyntaxException;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by qiqi on 2017/11/8.
 * Retrofit的观察者
 */

public abstract class RxObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T value) {
        onSuccess(value);
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof SocketTimeoutException){
            onFail("连接超时");
        }else if(e instanceof JsonSyntaxException){
            onFail("json格式不符");
        }else if("未登录".equals(e.getMessage())){
            onFail(e.getMessage());
//                    goLogin();
        }else{
            onFail(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(T t);
    protected void onFail(String errMsg){}
}
