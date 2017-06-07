package com.mumu.meishijia.view.football;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.football.RankingAdapter;
import com.mumu.meishijia.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 各联赛排名的界面
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends BaseFragment {
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

    private void initUI(View view) {
        unbinder = ButterKnife.bind(this, view);
        adapter = new RankingAdapter(getActivity());
        listView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
