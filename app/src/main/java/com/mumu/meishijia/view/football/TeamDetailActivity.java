package com.mumu.meishijia.view.football;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.DividerGridItemDecoration;
import com.mumu.meishijia.adapter.football.PlayerAdapter;
import com.mumu.meishijia.model.football.FootballPlayer;
import com.mumu.meishijia.presenter.football.TeamDetailPresenter;
import com.mumu.meishijia.view.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamDetailActivity extends BaseActivity<TeamDetailPresenter> implements TeamDetailView{
    public static final String TEAM_ID = "team_id";

    @BindView(R.id.img_team)
    ImageView imgTeam;
    @BindView(R.id.txt_team_name)
    TextView txtTeamName;
    @BindView(R.id.txt_team_eng_name)
    TextView txtTeamEngName;
    @BindView(R.id.txt_extra_info)
    TextView txtExtraInfo;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private int teamId;
    private PlayerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        teamId = intent.getIntExtra(TEAM_ID, 0);
        initUI();
        presenter.getTeam(teamId);
    }

    private void initUI(){
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        adapter = new PlayerAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getSuccess(List<FootballPlayer> playerList) {
        adapter.setData(playerList);
    }
}
