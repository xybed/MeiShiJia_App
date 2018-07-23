package com.mumu.meishijia.http;

import com.alibaba.fastjson.JSON;
import com.mumu.meishijia.BuildConfig;
import com.mumu.meishijia.MyApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lib.utils.MD5Util;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by qiqi on 2017/11/7.
 * 网络请求的基本参数的拦截器
 */

public class BaseParamsInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String path = request.url().url().getPath();
        //为了解决上传图片时奇怪的json语法错误问题
        if(("/"+HttpUrl.UploadImage).equals(path))
            return chain.proceed(request);

        String sign = createSign(chain);
        String token;
        if(MyApplication.getInstance().isLogin()){
            token = MyApplication.getInstance().getUser().getToken();
        }else {
            token = "";
        }
        request = request
                .newBuilder()
                .addHeader("ver", BuildConfig.DEVELOP_VERSION)
                .addHeader("platform", "android")
                .addHeader("token", token)
                .addHeader("sign", sign)
                .build();
        return chain.proceed(request);
    }

    private String createSign(Chain chain) throws IOException {
        Request request = chain.request();
        String method = request.method();
        List<String> keyList = new ArrayList<>();
        if("GET".equals(method)){
            String query = request.url().query();
            if(query != null){
                String[] parameters = query.split("&");
                keyList.addAll(Arrays.asList(parameters));
            }
        }else{
            RequestBody requestBody = request.body();
            if(requestBody != null){
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                String queryJson = buffer.readUtf8();
                Map<String, String> paramsMap = JSON.parseObject(queryJson, Map.class);
                for(String key : paramsMap.keySet()){
                    keyList.add(key + "=" + paramsMap.get(key));
                }
            }
        }
        keyList.add("platform=android");
        keyList.add("ver="+BuildConfig.DEVELOP_VERSION);
        if(MyApplication.getInstance().isLogin()){
            keyList.add("token="+MyApplication.getInstance().getUser().getToken());
        }else {
            keyList.add("token=");
        }
        return MD5Util.createParamSign(keyList, HttpUrl.TOKEN_KEY);
    }
}
