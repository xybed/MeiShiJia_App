package com.mumu.meishijia.test;

import com.mumu.meishijia.model.football.Ranking;
import com.mumu.meishijia.view.BaseView;

import java.util.List;

/**
 * Created by 77 on 2018/8/1 0001.
 */

public interface TestView extends BaseView{
    void getSuccess(List<Ranking> rankings);
}
