package com.mumu.meishijia.api;

import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.model.BaseModel;
import com.mumu.meishijia.model.football.FootballPlayer;
import com.mumu.meishijia.model.football.Ranking;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 有关足球的接口
 * Created by Administrator on 2017/6/7.
 */

public interface FootballApi {
    @GET(HttpUrl.GetRanking)
    Observable<BaseModel<List<Ranking>>> getRanking(@QueryMap Map<String, Integer> map);

    @GET(HttpUrl.GetTeam)
    Observable<BaseModel<List<FootballPlayer>>> getTeam(@QueryMap Map<String, Integer> map);
}
