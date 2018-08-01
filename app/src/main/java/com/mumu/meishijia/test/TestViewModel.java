package com.mumu.meishijia.test;

import com.mumu.meishijia.api.FootballApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.football.Ranking;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 77 on 2018/8/1 0001.
 */

public class TestViewModel extends BaseViewModel{
    public Observable<List<Ranking>> getRanking(int type){
        Map<String, Integer> params = new HashMap<>();
        params.put("type", type);
        return HttpRetrofit.create(FootballApi.class)
                .getRanking(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<List<Ranking>>());
    }
}
