package com.mumu.meishijia.adapter.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseRecyclerAdapter;

/**
 * Created by qiqi on 2018/11/4.
 * 确认订单的商品条目adapter
 */

public class OrderConfirmAdapter extends BaseRecyclerAdapter<Object, RecyclerView.ViewHolder>{
    public OrderConfirmAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_order_product_item;
    }

    @Override
    public RecyclerView.ViewHolder createHolder(View view) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
