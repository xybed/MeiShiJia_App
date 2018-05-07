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
        switch (baseModel.getResultCode()){
            case -1:
                throw new ApiException(baseModel.getDetail());
            case -99:
                throw new ApiException("未登录");
            default:
                break;
        }
        return baseModel.getData();
    }
}
