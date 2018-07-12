package com.mumu.meishijia.viewmodel.im;

import com.mumu.meishijia.api.ImApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.im.ContactsDetail;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 联系人详细资料viewModel
 * Created by 77 on 2017/4/10.
 */

public class ContactsDetailViewModel extends BaseViewModel{

    public Observable<ContactsDetail> getContactsDetail(Integer userId, Integer friendId){
        Map<String, Integer> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("friend_id", friendId);
        return HttpRetrofit.create(ImApi.class)
                .getContactsDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<ContactsDetail>());
    }

}
