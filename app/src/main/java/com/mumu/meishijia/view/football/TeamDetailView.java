package com.mumu.meishijia.view.football;

import com.mumu.meishijia.model.football.FootballPlayer;
import com.mumu.meishijia.view.BaseView;

import java.util.List;

/**
 * Created by 77 on 2018/7/10 0010.
 */

public interface TeamDetailView extends BaseView{
    void getSuccess(List<FootballPlayer> playerList);
}
