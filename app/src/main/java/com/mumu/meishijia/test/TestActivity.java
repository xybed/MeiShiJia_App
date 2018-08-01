package com.mumu.meishijia.test;

import android.os.Bundle;
import android.widget.ListView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.football.RankingAdapter;
import com.mumu.meishijia.model.football.Ranking;
import com.mumu.meishijia.view.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends BaseActivity<TestPresenter> implements TestView{

    @BindView(R.id.list_view)
    ListView listView;

    private RankingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        adapter = new RankingAdapter(this);
        listView.setAdapter(adapter);

        startRefresh();
        presenter.getRanking(1);
    }

    @Override
    public void getSuccess(List<Ranking> rankings) {
        stopRefresh();
        adapter.setData(rankings);
    }
}
