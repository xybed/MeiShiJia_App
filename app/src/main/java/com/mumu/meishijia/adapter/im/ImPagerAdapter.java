package com.mumu.meishijia.adapter.im;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.List;

/**
 * 聊天界面底端功能的adapter
 * Created by Administrator on 2017/4/13.
 */

public class ImPagerAdapter extends PagerAdapter {
    private Context context;
    private List<View> viewList;

    public ImPagerAdapter(Context context, List<View> viewList){
        this.context = context;
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        /*
        这里的代码参考BannerView的代码
         */
        position %= viewList.size();
        View view = viewList.get(position);
        ViewParent viewParent = view.getParent();
        if(viewParent != null){
            ViewGroup viewGroup = (ViewGroup) viewParent;
            viewGroup.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
