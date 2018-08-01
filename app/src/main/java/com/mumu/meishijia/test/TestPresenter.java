package com.mumu.meishijia.test;

import com.mumu.meishijia.model.football.Ranking;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;

import java.util.List;

/**
 * Created by 77 on 2018/8/1 0001.
 */

public class TestPresenter extends BasePresenter<TestView, TestViewModel>{
    public TestPresenter(BaseView view) {
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
