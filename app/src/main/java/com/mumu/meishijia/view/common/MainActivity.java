package com.mumu.meishijia.view.common;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.mumu.meishijia.R;
import com.mumu.meishijia.tencent.IMUtil;
import com.mumu.meishijia.view.BaseActivity;
import com.mumu.meishijia.view.food.FoodFragment;
import com.mumu.meishijia.view.football.FootballFragment;
import com.mumu.meishijia.view.mine.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.utils.PinyinSortUtil;
import lib.utils.StringUtil;
import lib.utils.ToastUtil;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    private Fragment content;
    private Fragment foodFragment;
    private Fragment footballFragment;
    private Fragment mineFragment;

    private long firstTime;
    private long secondTime;
    private long spaceTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initFragment();
        initBottomNavigationBar();
    }

    private void initFragment() {
        content = new FoodFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content, content).commit();
        transparencyStatusBar();
    }

    private void initBottomNavigationBar(){
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setActiveColor(R.color.theme_color_orange_c);
        bottomNavigationBar.setInActiveColor(R.color.theme_color_black_a);
        bottomNavigationBar.setBarBackgroundColor(R.color.theme_color_white);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.icon_food_tab, "美食佳"))
                .addItem(new BottomNavigationItem(R.drawable.icon_shop_tab, "数据"))
                .addItem(new BottomNavigationItem(R.drawable.icon_mine_tab, "我的"))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switchFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private Fragment getFoodFragment() {
        if (foodFragment == null)
            foodFragment = new FoodFragment();
        return foodFragment;
    }

    private Fragment getFootballFragment() {
        if (footballFragment == null)
            footballFragment = new FootballFragment();
        return footballFragment;
    }

    private Fragment getMineFragment() {
        if (mineFragment == null)
            mineFragment = new MineFragment();
        return mineFragment;
    }

    private void switchFragment(int position) {
        switch (position) {
            case 0:
                switchFragment(content, getFoodFragment());
                transparencyStatusBar();
                break;
            case 1:
                switchFragment(content, getFootballFragment());
                transparencyStatusBar(false);
                break;
            case 2:
                switchFragment(content, getMineFragment());
                transparencyStatusBar();
                break;
        }
    }

    /**
     * 切换fragment
     *
     * @param from
     * @param to
     */
    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            content = to;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {//如果已经加过了就显示，这样的做法更有效率
                fragmentTransaction.hide(from).add(R.id.content, to).commit();
            } else {
                fragmentTransaction.hide(from).show(to).commit();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            firstTime = System.currentTimeMillis();
            spaceTime = firstTime - secondTime;
            secondTime = firstTime;
            if (spaceTime > 2000) {
                ToastUtil.show("再按一次退出程序");
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IMUtil.getInstance().addMessageListener();
    }

}
