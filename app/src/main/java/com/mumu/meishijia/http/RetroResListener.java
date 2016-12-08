package com.mumu.meishijia.http;

/**
 * Created by 7mu on 2016/8/24.
 */
public abstract class RetroResListener<T> {
    protected void onCompleted(){
        //呼应retrofit的onCompleted，在响应完成时可有操作也可无
    }
    protected abstract void onSuccess(T result);
    protected abstract void onFailure(String errMsg);
}
