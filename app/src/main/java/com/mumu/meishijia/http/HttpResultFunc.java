package com.mumu.meishijia.http;

import com.mumu.meishijia.model.BaseModel;

import io.reactivex.functions.Function;

/**
 * Created by 7mu on 2016/8/24.
 * 网络请求回调统一处理最外层json数据
 */
public class HttpResultFunc<T> implements Function<BaseModel<T>, T> {

    @Override
    public T apply(BaseModel<T> baseModel) throws Exception {
        int code = baseModel.getCode();
        if(code == 0){
            return baseModel.getData();
        }else if(code == -99){
            throw new ApiException("登录过期");
        }else {
            throw new ApiException(baseModel.getMessage());
        }
    }
}
