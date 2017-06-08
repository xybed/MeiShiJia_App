package com.mumu.meishijia.view.football;

import com.mumu.meishijia.model.football.RankingModel;

import java.util.List;

/**
 * 排名的view
 * Created by Administrator on 2017/6/8.
 */

public interface RankingView {
    void getSuccess(List<RankingModel> result);
    void getFail(String errMsg);
}
