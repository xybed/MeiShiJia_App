package com.mumu.meishijia.adapter.product;

import android.content.Context;
import android.graphics.Paint;
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
 * Created by 77 on 2018/8/10 0010.
 */

public class ProductListAdapter extends BaseRecyclerAdapter<Product, ProductListAdapter.Holder> {
    public ProductListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_product_item;
    }

    @Override
    public Holder createHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.txtOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Product item = datas.get(position);
        Glide.with(context).load(item.getImage()).placeholder(R.drawable.icon_no_image).into(holder.imgProductImage);
        holder.txtName.setText(context.getString(R.string.product_name_placeholder, item.getName()));
        holder.txtDescription.setText(item.getDescription());
        holder.txtDiscountPrice.setText(context.getString(R.string.product_price_placeholder, item.getDiscountPrice()));
        if(item.getDiscountPrice() == item.getOriginalPrice()){
            holder.txtOriginalPrice.setVisibility(View.GONE);
        }else {
            holder.txtOriginalPrice.setVisibility(View.VISIBLE);
            holder.txtOriginalPrice.setText(context.getString(R.string.product_price_placeholder, item.getOriginalPrice()));
            holder.txtOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_product_image)
        ImageView imgProductImage;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_description)
        TextView txtDescription;
        @BindView(R.id.txt_discount_price)
        TextView txtDiscountPrice;
        @BindView(R.id.txt_original_price)
        TextView txtOriginalPrice;

        Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
