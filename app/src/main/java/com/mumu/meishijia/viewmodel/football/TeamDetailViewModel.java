package com.mumu.meishijia.viewmodel.football;

import com.mumu.meishijia.api.FootballApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.model.football.FootballPlayer;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 77 on 2018/7/10 0010.
 */

public class TeamDetailViewModel extends BaseViewModel{

    public Observable<List<FootballPlayer>> getTeam(int teamId){
        Map<String, Integer> params = new HashMap<>();
        params.put("team_id", teamId);
        return HttpRetrofit.create(FootballApi.class)
                .getTeam(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<List<FootballPlayer>>());
    }
}
