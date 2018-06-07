package com.mumu.meishijia.api;

import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.model.BaseModel;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Jason on 2018/6/7 0007.
 */

public interface CommonApi {
    @Multipart
    @POST(HttpUrl.UploadImage)
    Observable<BaseModel<String>> uploadImage(@Part MultipartBody.Part part);
}
