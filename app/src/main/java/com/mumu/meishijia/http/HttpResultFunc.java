package com.mumu.meishijia.http;

import com.google.gson.Gson;
import com.mumu.meishijia.model.BaseModel;

import lib.utils.MyLogUtil;
import rx.functions.Func1;

/**
 * Created by 7mu on 2016/8/24.
 */
public class HttpResultFunc<T> implements Func1<BaseModel<T>, T> {
    @Override
    public T call(BaseModel<T> baseModel) {
        MyLogUtil.e("http_返回数据", new Gson().toJson(baseModel));
        switch (baseModel.getResultCode()){
            case -1:
                throw new ApiException("请求错误");
            case -99:
                throw new ApiException("未登录");
            default:
                break;
        }
        return baseModel.getData();
    }

}
