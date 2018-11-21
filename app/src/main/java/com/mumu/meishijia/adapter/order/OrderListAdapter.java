package com.mumu.meishijia.adapter.order;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
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
import lib.utils.DensityUtil;

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
            holder.txtPay.setVisibility(View.VISIBLE);
            holder.txtCancelOrder.setVisibility(View.VISIBLE);
            holder.txtConfirmOfReceive.setVisibility(View.GONE);
            holder.txtRefund.setVisibility(View.GONE);
            holder.txtComment.setVisibility(View.GONE);
            holder.txtDeleteOrder.setVisibility(View.GONE);
        }else if(item.getStatus().intValue() == OrderStatus.WAIT_SEND.getCode()){
            holder.txtOrderStatus.setText(context.getString(R.string.order_buyer_pay));
            holder.txtPay.setVisibility(View.GONE);
            holder.txtCancelOrder.setVisibility(View.GONE);
            holder.txtConfirmOfReceive.setVisibility(View.GONE);
            holder.txtRefund.setVisibility(View.VISIBLE);
            holder.txtComment.setVisibility(View.GONE);
            holder.txtDeleteOrder.setVisibility(View.GONE);
        }else if(item.getStatus().intValue() == OrderStatus.WAIT_DELIVERY.getCode()){
            holder.txtOrderStatus.setText(context.getString(R.string.order_seller_send));
            holder.txtPay.setVisibility(View.GONE);
            holder.txtCancelOrder.setVisibility(View.GONE);
            holder.txtConfirmOfReceive.setVisibility(View.VISIBLE);
            holder.txtRefund.setVisibility(View.VISIBLE);
            holder.txtComment.setVisibility(View.GONE);
            holder.txtDeleteOrder.setVisibility(View.GONE);
        }else if(item.getStatus().intValue() == OrderStatus.WAIT_COMMENT.getCode()){
            holder.txtOrderStatus.setText(context.getString(R.string.order_trade_success));
            holder.txtPay.setVisibility(View.GONE);
            holder.txtCancelOrder.setVisibility(View.GONE);
            holder.txtConfirmOfReceive.setVisibility(View.GONE);
            holder.txtRefund.setVisibility(View.GONE);
            holder.txtComment.setVisibility(View.VISIBLE);
            holder.txtDeleteOrder.setVisibility(View.VISIBLE);
        }else if(item.getStatus().intValue() == OrderStatus.REFUND.getCode()){
            holder.txtOrderStatus.setText(context.getString(R.string.order_trade_close));
            holder.txtPay.setVisibility(View.GONE);
            holder.txtCancelOrder.setVisibility(View.GONE);
            holder.txtConfirmOfReceive.setVisibility(View.GONE);
            holder.txtRefund.setVisibility(View.GONE);
            holder.txtComment.setVisibility(View.GONE);
            holder.txtDeleteOrder.setVisibility(View.VISIBLE);
        }else if(item.getStatus().intValue() == OrderStatus.SUCCESS.getCode()){
            holder.txtOrderStatus.setText(context.getString(R.string.order_trade_success));
            holder.txtPay.setVisibility(View.GONE);
            holder.txtCancelOrder.setVisibility(View.GONE);
            holder.txtConfirmOfReceive.setVisibility(View.GONE);
            holder.txtRefund.setVisibility(View.GONE);
            holder.txtComment.setVisibility(View.GONE);
            holder.txtDeleteOrder.setVisibility(View.VISIBLE);
        }else {
            holder.txtOrderStatus.setText("");
            holder.txtPay.setVisibility(View.GONE);
            holder.txtCancelOrder.setVisibility(View.GONE);
            holder.txtConfirmOfReceive.setVisibility(View.GONE);
            holder.txtRefund.setVisibility(View.GONE);
            holder.txtComment.setVisibility(View.GONE);
            holder.txtDeleteOrder.setVisibility(View.GONE);
        }
        //商品列表
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        OrderAdapter adapter = new OrderAdapter(context);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = DensityUtil.dip2px(context, 10);
            }
        });
        holder.recyclerView.setAdapter(adapter);
        adapter.setData(item.getProducts());

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
        @BindView(R.id.txt_pay)
        TextView txtPay;
        @BindView(R.id.txt_cancel_order)
        TextView txtCancelOrder;
        @BindView(R.id.txt_confirm_of_receive)
        TextView txtConfirmOfReceive;
        @BindView(R.id.txt_refund)
        TextView txtRefund;
        @BindView(R.id.txt_comment)
        TextView txtComment;
        @BindView(R.id.txt_delete_order)
        TextView txtDeleteOrder;

        Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
