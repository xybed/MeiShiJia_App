package com.mumu.meishijia.api;

import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.model.BaseModel;
import com.mumu.meishijia.model.im.ContactsDetail;
import com.mumu.meishijia.model.im.Contacts;
import com.mumu.meishijia.model.im.PrincipalModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * im的接口
 * Created by Administrator on 2017/4/10.
 */

public interface ImApi {
    @GET(HttpUrl.GetContacts)
    Observable<BaseModel<List<Contacts>>> getContacts(@QueryMap Map<String, Integer> map);
    @GET(HttpUrl.GetContactsDetail)
    Observable<BaseModel<ContactsDetail>> getContactsDetail(@QueryMap Map<String, Integer> map);
    @POST(HttpUrl.ModifyRemark)
    Observable<BaseModel<String>> modifyRemark(@Body Map<String, String> map);
    @GET(HttpUrl.GetPrincipalInfo)
    Observable<BaseModel<PrincipalModel>> getPrincipalInfo(@QueryMap Map<String, String> map);
}
