package com.mumu.meishijia.adapter.football;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mumu.meishijia.view.football.RankingFragment;

/**
 * 联赛的Tablayout的布局的adapter
 * Created by Administrator on 2017/6/7.
 */

public class LeaguePagerAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"西甲", "英超", "意甲", "德甲", "法甲", "中超"};

    private RankingFragment laliga;//西甲
    private RankingFragment premierLeague;//英超
    private RankingFragment serieA;//意甲
    private RankingFragment bundesliga;//德甲
    private RankingFragment ligue;//法甲
    private RankingFragment csl;//中超

    public LeaguePagerAdapter(FragmentManager fm) {
        super(fm);

        Bundle bundle = new Bundle();

        laliga = new RankingFragment();
        bundle.putInt(RankingFragment.LEAGUE_TYPE, 1);
        laliga.setArguments(bundle);

        premierLeague = new RankingFragment();
        bundle = new Bundle();
        bundle.putInt(RankingFragment.LEAGUE_TYPE, 2);
        premierLeague.setArguments(bundle);

        serieA = new RankingFragment();
        bundle = new Bundle();
        bundle.putInt(RankingFragment.LEAGUE_TYPE, 3);
        serieA.setArguments(bundle);

        bundesliga = new RankingFragment();
        bundle = new Bundle();
        bundle.putInt(RankingFragment.LEAGUE_TYPE, 4);
        bundesliga.setArguments(bundle);

        ligue = new RankingFragment();
        bundle = new Bundle();
        bundle.putInt(RankingFragment.LEAGUE_TYPE, 5);
        ligue.setArguments(bundle);

        csl = new RankingFragment();
        bundle = new Bundle();
        bundle.putInt(RankingFragment.LEAGUE_TYPE, 6);
        csl.setArguments(bundle);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return laliga;
        }else if(position == 1){
            return premierLeague;
        }else if(position == 2){
            return serieA;
        }else if(position == 3){
            return bundesliga;
        }else if(position == 4){
            return ligue;
        }else if(position == 5){
            return csl;
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position % titles.length];
    }
}
