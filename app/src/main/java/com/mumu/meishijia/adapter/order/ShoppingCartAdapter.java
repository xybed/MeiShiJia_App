package com.mumu.meishijia.adapter.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseRecyclerAdapter;
import com.mumu.meishijia.constant.ProductStatus;
import com.mumu.meishijia.model.order.ShoppingCart;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiqi on 2018/11/3.
 * 购物车适配器
 */

public class ShoppingCartAdapter extends BaseRecyclerAdapter<ShoppingCart, ShoppingCartAdapter.Holder> {
    private boolean isShow;//是否显示加减按钮

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
        final ShoppingCart item = datas.get(position);
        holder.rbSelect.setChecked(item.isSelected());
        Glide.with(context).load(item.getImage()).placeholder(R.drawable.icon_no_image).into(holder.imgProduct);
        if(item.getStatus().intValue() == ProductStatus.SHELF.getCode()){
            //在不失效的情况下，比较库存
            if(item.getStock() < item.getNum()){
                holder.txtInvalid.setText(context.getString(R.string.order_stock_not_enough));
                holder.txtInvalid.setVisibility(View.VISIBLE);
            }else {
                holder.txtInvalid.setText(context.getString(R.string.order_invalid));
                holder.txtInvalid.setVisibility(View.GONE);
            }
        }else {
            holder.txtInvalid.setText(context.getString(R.string.order_invalid));
            holder.txtInvalid.setVisibility(View.VISIBLE);
        }
        holder.txtName.setText(context.getString(R.string.product_name_placeholder, item.getName()));
        holder.txtPrice.setText(context.getString(R.string.product_price_placeholder, item.getPrice().doubleValue()));
        holder.txtCount.setText(context.getString(R.string.com_placeholder, item.getNum()));

        //点击事件，在商品有效的情况下，点击才有意义
        if(isShow){
            holder.imgAdd.setVisibility(View.VISIBLE);
            holder.imgSub.setVisibility(View.VISIBLE);
        }else {
            holder.imgAdd.setVisibility(View.GONE);
            holder.imgSub.setVisibility(View.GONE);
        }
        if(item.getStatus().intValue() == ProductStatus.SHELF.getCode()){
            holder.imgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.getNum() < item.getStock()){
                        item.setNum(item.getNum()+1);
                        notifyDataSetChanged();
                        if(listener != null && item.isSelected())
                            listener.onAddClick(item.getPrice().doubleValue());
                    }else {
                        Toast.makeText(context.getApplicationContext(), context.getString(R.string.order_stock_not_enough), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.imgSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.getNum() > 0){
                        item.setNum(item.getNum()-1);
                        notifyDataSetChanged();
                        if(listener != null && item.isSelected())
                            listener.onSubClick(item.getPrice().doubleValue());
                    }
                }
            });
            holder.rbSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setSelected(!item.isSelected());
                    notifyDataSetChanged();
                    if(listener != null)
                        listener.onRbClick(item.getPrice().doubleValue(), item.getNum(), item.isSelected());
                }
            });
        }else {
            holder.imgAdd.setClickable(false);
            holder.imgSub.setClickable(false);
            holder.rbSelect.setClickable(false);
        }
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    private OnItemClickListener listener;
    public interface OnItemClickListener{
        void onRbClick(double price, int num, boolean isSelected);
        void onAddClick(double price);
        void onSubClick(double price);
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
