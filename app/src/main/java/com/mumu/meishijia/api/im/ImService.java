package com.mumu.meishijia.api.im;

import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.model.BaseModel;
import com.mumu.meishijia.model.im.ContactsDetailModel;
import com.mumu.meishijia.model.im.ContactsModel;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * im的接口
 * Created by Administrator on 2017/4/10.
 */

public interface ImService {
    @GET(HttpUrl.GetContacts)
    Observable<BaseModel<List<ContactsModel>>> getContacts(@QueryMap Map<String, String> map);
    @GET(HttpUrl.GetContactsDetail)
    Observable<BaseModel<ContactsDetailModel>> getContactsDetail(@QueryMap Map<String, String> map);
}
