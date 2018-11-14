package com.mumu.meishijia.adapter.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseRecyclerAdapter;
import com.mumu.meishijia.constant.ShoppingCartStatus;
import com.mumu.meishijia.model.order.ShoppingCart;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiqi on 2018/11/3.
 * 购物车适配器
 */

public class ShoppingCartAdapter extends BaseRecyclerAdapter<ShoppingCart, ShoppingCartAdapter.Holder> {
    public ShoppingCartAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_shopping_cart_item;
    }

    @Override
    public Holder createHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ShoppingCart item = datas.get(position);
        holder.rbSelect.setChecked(item.isSelected());
        Glide.with(context).load(item.getImage()).placeholder(R.drawable.icon_no_image).into(holder.imgProduct);
        if(item.getStatus().intValue() == ShoppingCartStatus.EFFECTIVE.getCode()){
            holder.txtInvalid.setVisibility(View.GONE);
        }else {
            holder.txtInvalid.setVisibility(View.VISIBLE);
        }
        holder.txtName.setText(context.getString(R.string.product_name_placeholder, item.getName()));
        holder.txtPrice.setText(context.getString(R.string.product_price_placeholder, item.getPrice().doubleValue()));
        holder.txtCount.setText(context.getString(R.string.com_placeholder, item.getNum()));
    }

    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.rb_select)
        RadioButton rbSelect;
        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.txt_invalid)
        TextView txtInvalid;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_price)
        TextView txtPrice;
        @BindView(R.id.img_sub)
        ImageView imgSub;
        @BindView(R.id.txt_count)
        TextView txtCount;
        @BindView(R.id.img_add)
        ImageView imgAdd;

        Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
