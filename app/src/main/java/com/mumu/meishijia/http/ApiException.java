package com.mumu.meishijia.http;

/**
 * Created by 7mu on 2016/8/24.
 */
public class ApiException extends RuntimeException {
    public ApiException(String errMsg){
        super(errMsg);
    }
}
