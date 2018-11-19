package com.mumu.meishijia.adapter.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseRecyclerAdapter;

/**
 * Created by 77 on 2018/11/19 0019.
 * 订单adapter
 */

public class OrderListAdapter extends BaseRecyclerAdapter<Object, RecyclerView.ViewHolder>{
    public OrderListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_order_list_item;
    }

    @Override
    public RecyclerView.ViewHolder createHolder(View view) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
