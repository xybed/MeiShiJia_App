package com.mumu.meishijia.adapter.im;

import android.view.View;
import android.view.ViewGroup;

import lib.swipelayout.BaseSwipeAdapter;

/**
 * 联系人的adapter
 * Created by Administrator on 2017/4/6.
 */

public class ContactsAdapter extends BaseSwipeAdapter{

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return 0;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return null;
    }

    @Override
    public void fillValues(int position, View convertView) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
