package com.mumu.meishijia.view.football;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.football.LeaguePagerAdapter;
import com.mumu.meishijia.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FootballFragment extends BaseFragment {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;

    private LeaguePagerAdapter adapter;

    public FootballFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_football, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view){
        unbinder = ButterKnife.bind(this, view);
        adapter = new LeaguePagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
