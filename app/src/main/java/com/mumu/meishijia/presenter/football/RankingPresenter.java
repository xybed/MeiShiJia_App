package com.mumu.meishijia.presenter.football;

import com.mumu.meishijia.model.football.RankingModel;
import com.mumu.meishijia.view.football.RankingView;
import com.mumu.meishijia.viewmodel.football.RankingViewModel;

import java.util.List;

/**
 * 排名的presenter
 * Created by Administrator on 2017/6/8.
 */

public class RankingPresenter implements RankingViewModel.RankingListener{

    private RankingView view;
    private RankingViewModel viewModel;

    public RankingPresenter(RankingView view){
        this.view = view;
        viewModel = new RankingViewModel(this);
    }

    public void getRanking(int type){
        viewModel.getRanking(type);
    }

    @Override
    public void getSuccess(List<RankingModel> result) {
        view.getSuccess(result);
    }

    @Override
    public void getFail(String errMsg) {
        view.getFail(errMsg);
    }
}
