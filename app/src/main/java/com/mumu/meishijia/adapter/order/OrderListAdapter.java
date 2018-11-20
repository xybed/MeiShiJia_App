package com.mumu.meishijia.adapter.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseRecyclerAdapter;
import com.mumu.meishijia.constant.OrderStatus;
import com.mumu.meishijia.model.order.Order;
import com.mumu.meishijia.model.product.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 77 on 2018/11/19 0019.
 * 订单adapter
 */

public class OrderListAdapter extends BaseRecyclerAdapter<Order, OrderListAdapter.Holder> {
    public OrderListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_order_list_item;
    }

    @Override
    public Holder createHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Order item = datas.get(position);
        if(item.getStatus().intValue() == OrderStatus.WAIT_PAY.getCode()){
            holder.txtOrderStatus.setText(context.getString(R.string.order_wait_buyer_pay));
        }else if(item.getStatus().intValue() == OrderStatus.WAIT_SEND.getCode()){
            holder.txtOrderStatus.setText(context.getString(R.string.order_buyer_pay));
        }else if(item.getStatus().intValue() == OrderStatus.WAIT_RECEIVE.getCode()){
            holder.txtOrderStatus.setText(context.getString(R.string.order_wait_seller_send));
        }else if(item.getStatus().intValue() == OrderStatus.WAIT_EVALUATE.getCode()){
            holder.txtOrderStatus.setText(context.getString(R.string.order_trade_success));
        }else if(item.getStatus().intValue() == OrderStatus.REFUND.getCode()){
            holder.txtOrderStatus.setText(context.getString(R.string.order_trade_close));
        }else {
            holder.txtOrderStatus.setText("");
        }
        //商品列表
        int count = 0;
        for(Product product : item.getProducts()){
            count += product.getNum();
        }
        holder.txtTotalCount.setText(context.getString(R.string.order_total_count_product_placeholder, count));
        holder.txtTotalAmount.setText(context.getString(R.string.order_total_amount_placeholder, item.getPayAmount().doubleValue()));
    }

    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_order_status)
        TextView txtOrderStatus;
        @BindView(R.id.recycler_view)
        RecyclerView recyclerView;
        @BindView(R.id.txt_total_count)
        TextView txtTotalCount;
        @BindView(R.id.txt_total_amount)
        TextView txtTotalAmount;

        Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
