package com.mumu.meishijia.viewmodel.im;

import com.mumu.meishijia.api.ImApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.im.Contacts;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 联系人的viewModel
 * Created by Administrator on 2017/4/7.
 */

public class ContactsViewModel extends BaseViewModel{

    public Observable<List<Contacts>> getContacts(int userId){
        Map<String, Integer> params = new HashMap<>();
        params.put("user_id", userId);
        return HttpRetrofit.create(ImApi.class)
                .getContacts(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<List<Contacts>>());
    }
}
