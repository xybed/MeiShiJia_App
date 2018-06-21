package com.mumu.meishijia.http;

import com.mumu.meishijia.BuildConfig;
import com.mumu.meishijia.MyApplication;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
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

    private static void initRetrofit(){
        if(retrofit == null){
            synchronized (HttpRetrofit.class){
                if(retrofit == null){
                    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                            .connectTimeout(10000, TimeUnit.MILLISECONDS)
                            .sslSocketFactory(MySSLSocketFactory.getSocketFactory(MyApplication.getInstance()));

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

                    retrofit = new Retrofit.Builder()
                            .client(httpClientBuilder.build())
                            .baseUrl(BuildConfig.Base_Api_Url)
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

//    private void goLogin(){
//        MyApplication.getInstance().setUser(null);
//        MyApplication.getInstance().setLogin(false);
//        CacheJsonMgr.getInstance(context).deleteJson(UserModel.class.getSimpleName());
//        Intent intent = new Intent(context, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(intent);
//    }

    /**
     * 构建上传图片的multipart，网上摘录方法
     * @param filePath 图片路径
     * @return part
     */
    public static MultipartBody.Part createImageMultipart(String filePath){
        File file = new File(filePath);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData("img_file", file.getName(), imageBody);
    }
}
