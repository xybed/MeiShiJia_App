package com.mumu.meishijia.view;

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
        showDialog();
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

    private void showDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("版本更新");
        dialog.setMessage("发现新版本");
        dialog.setPositiveButton("立即下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bodata-download.oss-cn-shanghai.aliyuncs.com/app/fnxp-release-v1.0-2017-10-18.apk"));
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.create().show();
    }

    List<String> tempList = new ArrayList<>();
    private void searchFilter(String filterStr){
        List<String> strList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            //如果为空，strList为缓存的list
        } else {
            strList.clear();
            for (String str: tempList) {
                //匹配两个字符串
                if(StringUtil.isMatching(str, filterStr)){
                    strList.add(str);
                }
            }
        }
        PinyinSortUtil pinyinSortUtil = new PinyinSortUtil();
        // 根据a-z进行排序
        pinyinSortUtil.Sort(strList, "getSortLetters");
        //刷新界面
    }
}
