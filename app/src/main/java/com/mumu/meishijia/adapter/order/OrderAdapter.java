package com.mumu.meishijia.adapter.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseRecyclerAdapter;
import com.mumu.meishijia.model.product.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 77 on 2018/11/21 0021.
 * 订单中商品列表的adapter
 */

public class OrderAdapter extends BaseRecyclerAdapter<Product, OrderAdapter.Holder> {
    public OrderAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_order_item;
    }

    @Override
    public Holder createHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Product item = datas.get(position);
        Glide.with(context).load(item.getImage()).placeholder(R.drawable.icon_no_image).into(holder.imgProductImage);
        holder.txtName.setText(item.getName());
        holder.txtPrice.setText(context.getString(R.string.product_price_placeholder, item.getPrice().doubleValue()));
        holder.txtNum.setText(context.getString(R.string.product_num_placeholder, item.getNum()));
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

        Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
