package com.mumu.meishijia.adapter.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mumu.meishijia.view.order.OrderListFragment;

/**
 * Created by 77 on 2018/11/17.
 * 订单的ViewPager适配器
 */

public class OrderPagerAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"全部", "待付款", "待发货", "待收货", "待评价", "退款"};

    private OrderListFragment allFragment;
    private OrderListFragment payFragment;
    private OrderListFragment sendFragment;
    private OrderListFragment receiveFragment;
    private OrderListFragment commentFragment;
    private OrderListFragment refundFragment;

    public OrderPagerAdapter(FragmentManager fm) {
        super(fm);

        Bundle bundle = new Bundle();

        allFragment = new OrderListFragment();
        bundle.putInt(OrderListFragment.ORDER_STATUS, -1);
        allFragment.setArguments(bundle);

        payFragment = new OrderListFragment();
        bundle = new Bundle();
        bundle.putInt(OrderListFragment.ORDER_STATUS, 0);
        payFragment.setArguments(bundle);

        sendFragment = new OrderListFragment();
        bundle = new Bundle();
        bundle.putInt(OrderListFragment.ORDER_STATUS, 1);
        sendFragment.setArguments(bundle);

        receiveFragment = new OrderListFragment();
        bundle = new Bundle();
        bundle.putInt(OrderListFragment.ORDER_STATUS, 2);
        receiveFragment.setArguments(bundle);

        commentFragment = new OrderListFragment();
        bundle = new Bundle();
        bundle.putInt(OrderListFragment.ORDER_STATUS, 3);
        commentFragment.setArguments(bundle);

        refundFragment = new OrderListFragment();
        bundle = new Bundle();
        bundle.putInt(OrderListFragment.ORDER_STATUS, 4);
        refundFragment.setArguments(bundle);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return allFragment;
        }else if(position == 1){
            return payFragment;
        }else if(position == 2){
            return sendFragment;
        }else if(position == 3){
            return receiveFragment;
        }else if(position == 4){
            return commentFragment;
        }else if(position == 5){
            return refundFragment;
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
