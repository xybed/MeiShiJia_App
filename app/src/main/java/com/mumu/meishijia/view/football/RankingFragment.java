package com.mumu.meishijia.view.football;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.football.RankingAdapter;
import com.mumu.meishijia.model.football.Ranking;
import com.mumu.meishijia.presenter.football.RankingPresenter;
import com.mumu.meishijia.view.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 各联赛排名的界面
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends BaseFragment<RankingPresenter> implements RankingView{
    public static final String LEAGUE_TYPE = "league_type";

    @BindView(R.id.list_view)
    ListView listView;
    Unbinder unbinder;

    private RankingAdapter adapter;
    private int leagueType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            leagueType = bundle.getInt(LEAGUE_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        initUI(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startRefresh();
        presenter.getRanking(leagueType);
    }

    private void initUI(View view) {
        unbinder = ButterKnife.bind(this, view);
        adapter = new RankingAdapter(getActivity());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ranking ranking = (Ranking) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), TeamDetailActivity.class);
                intent.putExtra(TeamDetailActivity.TEAM_ID, ranking.getTeam_id());
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        presenter.getRanking(leagueType);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getSuccess(List<Ranking> result) {
        stopRefresh();
        if(result == null || result.size() < 0)
            return;
        adapter.setData(result);
    }

}
