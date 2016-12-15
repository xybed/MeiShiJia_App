package com.mumu.meishijia.view.food;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.view.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.ScreenUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.img_banner)
    ImageView imgBanner;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        ButterKnife.bind(this, view);
        ScreenUtil.measureViewByImg(getActivity(), imgBanner, 2.9);
        swipeRefresh.setColorSchemeResources(R.color.theme_color_green_a);
        swipeRefresh.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
    }

    @OnClick({R.id.llay_jia_love, R.id.llay_kitchen_qa, R.id.llay_cookbook_category})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.llay_jia_love:
                break;
            case R.id.llay_kitchen_qa:
                break;
            case R.id.llay_cookbook_category:
                intent = new Intent(getActivity(), RecipeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
