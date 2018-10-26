package com.mumu.meishijia.http;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit统一处理类
 * Created by 7mu on 2016/8/24.
 */
public class HttpRetrofit {
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    private static void initRetrofit(){
        if(retrofit == null){
            synchronized (HttpRetrofit.class){
                if(retrofit == null){
                    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                            .connectTimeout(30000, TimeUnit.MILLISECONDS)
                            .readTimeout(30000, TimeUnit.MILLISECONDS)
                            .writeTimeout(30000, TimeUnit.MILLISECONDS);
//                            .sslSocketFactory(MySSLSocketFactory.getSocketFactory(MyApplication.getInstance()))
//                            .hostnameVerifier(new HostnameVerifier() {
//                                @Override
//                                public boolean verify(String s, SSLSession sslSession) {
//                                    //TODO 为避免验证hostname暂时做的处理
//                                    return true;
//                                }
//                            });

                    //设置log的拦截器
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            if(message.startsWith("{")){
                                Logger.json(message);
                            }else {
                                Logger.d(message);
                            }
                        }
                    });
                    //添加拦截器
                    BaseParamsInterceptor baseParamsInterceptor = new BaseParamsInterceptor();
                    httpClientBuilder.addInterceptor(baseParamsInterceptor);
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    httpClientBuilder.addInterceptor(httpLoggingInterceptor);

                    okHttpClient = httpClientBuilder.build();
                    retrofit = new Retrofit.Builder()
                            .client(okHttpClient)
                            .baseUrl(HttpUrl.BASE_API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
    }

    public static <T> T create(Class<T> clazz){
        if(retrofit == null)
            initRetrofit();
        return retrofit.create(clazz);
    }

    public static OkHttpClient getOkHttpClient(){
        if(okHttpClient == null)
            initRetrofit();
        return okHttpClient;
    }

    /**
     * 构建上传图片的multipart，网上摘录方法
     * @param filePath 图片路径
     * @return part
     */
    public static MultipartBody.Part createImageMultipart(String filePath){
        File file = new File(filePath);
        RequestBody imageBody = RequestBody.create(MultipartBody.FORM, file);
        return MultipartBody.Part.createFormData("img_file", file.getName(), imageBody);
    }
}
