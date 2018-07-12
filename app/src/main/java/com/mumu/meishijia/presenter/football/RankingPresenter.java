package com.mumu.meishijia.presenter.football;

import com.mumu.meishijia.model.football.Ranking;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.football.RankingView;
import com.mumu.meishijia.viewmodel.football.RankingViewModel;

import java.util.List;

/**
 * 排名的presenter
 * Created by Administrator on 2017/6/8.
 */

public class RankingPresenter extends BasePresenter<RankingView, RankingViewModel>{

    public RankingPresenter(BaseView view) {
        super(view);
    }

    public void getRanking(int type){
        model.getRanking(type)
                .subscribe(new RxObserver<List<Ranking>>() {
                    @Override
                    protected void onSuccess(List<Ranking> rankings) {
                        view.getSuccess(rankings);
                    }
                });
    }

}
