package com.mumu.meishijia.adapter.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseRecyclerAdapter;
import com.mumu.meishijia.model.order.ShoppingCart;
import com.mumu.meishijia.model.product.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.utils.NumberUtil;

/**
 * Created by qiqi on 2018/11/4.
 * 确认订单的商品条目adapter
 */

public class OrderConfirmAdapter extends BaseRecyclerAdapter<ShoppingCart, OrderConfirmAdapter.Holder> {
    public OrderConfirmAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_order_product_item;
    }

    @Override
    public Holder createHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ShoppingCart item = datas.get(position);
        Glide.with(context).load(item.getImage()).placeholder(R.drawable.icon_no_image).into(holder.imgProductImage);
        holder.txtName.setText(item.getName());
        holder.txtPrice.setText(context.getString(R.string.product_price_placeholder, item.getPrice().doubleValue()));
        holder.txtNum.setText(context.getString(R.string.product_num_placeholder, item.getNum()));
        holder.txtTotalCount.setText(context.getString(R.string.order_total_count_product_placeholder, item.getNum()));
        double totalAmount = NumberUtil.multiply(item.getNum(), item.getPrice().doubleValue());
        holder.txtTotalAmount.setText(context.getString(R.string.product_price_placeholder, totalAmount));
    }

    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_product_image)
        ImageView imgProductImage;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_price)
        TextView txtPrice;
        @BindView(R.id.txt_num)
        TextView txtNum;
        @BindView(R.id.edit_remark)
        EditText editRemark;
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
