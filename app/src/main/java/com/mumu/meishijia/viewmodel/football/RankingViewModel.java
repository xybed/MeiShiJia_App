package com.mumu.meishijia.viewmodel.football;

import com.mumu.meishijia.api.football.FootballService;
import com.mumu.meishijia.http.HttpRequestParams;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.http.RetroResListener;
import com.mumu.meishijia.model.football.RankingModel;

import java.util.List;

/**
 * 排名的ViewModel
 * Created by Administrator on 2017/6/7.
 */

public class RankingViewModel {

    public RankingViewModel(RankingListener listener){
        this.listener = listener;
    }

    public void getRanking(int type){
        HttpRequestParams params = new HttpRequestParams();
        params.put("type", type);
        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
        httpRetrofit.getList(httpRetrofit.getApiService(FootballService.class, HttpUrl.GetRanking, params).getRanking(params.urlParams), "", new RetroResListener<List<RankingModel>>() {
            @Override
            protected void onSuccess(List<RankingModel> result) {
                if(listener != null)
                    listener.getSuccess(result);
            }

            @Override
            protected void onFailure(String errMsg) {
                if(listener != null)
                    listener.getFail(errMsg);
            }
        });
    }

    private RankingListener listener;
    public interface RankingListener{
        void getSuccess(List<RankingModel> result);
        void getFail(String errMsg);
    }
}
