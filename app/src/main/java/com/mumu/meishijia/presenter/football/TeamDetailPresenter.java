package com.mumu.meishijia.presenter.football;

import com.mumu.meishijia.model.football.FootballPlayer;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.football.TeamDetailView;
import com.mumu.meishijia.viewmodel.football.TeamDetailViewModel;

import java.util.List;

/**
 * Created by 77 on 2018/7/10 0010.
 */

public class TeamDetailPresenter extends BasePresenter<TeamDetailView, TeamDetailViewModel>{
    public TeamDetailPresenter(BaseView view) {
        super(view);
    }

    public void getTeam(int teamId){
        model.getTeam(teamId)
                .subscribe(new RxObserver<List<FootballPlayer>>() {
                    @Override
                    protected void onSuccess(List<FootballPlayer> playerList) {
                        if(view != null)
                            view.getSuccess(playerList);
                    }
                });
    }
}
