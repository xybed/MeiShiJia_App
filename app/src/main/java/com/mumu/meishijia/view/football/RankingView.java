package com.mumu.meishijia.view.football;

import com.mumu.meishijia.model.football.Ranking;
import com.mumu.meishijia.view.BaseView;

import java.util.List;

/**
 * 排名的view
 * Created by Administrator on 2017/6/8.
 */

public interface RankingView extends BaseView{
    void getSuccess(List<Ranking> result);
}
