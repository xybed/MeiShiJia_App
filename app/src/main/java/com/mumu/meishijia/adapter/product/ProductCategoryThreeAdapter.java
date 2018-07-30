package com.mumu.meishijia.adapter.product;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseRecyclerAdapter;
import com.mumu.meishijia.model.product.ProductCategory;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品三级分类adapter
 * Created by 77 on 2018/7/30 0030.
 */

public class ProductCategoryThreeAdapter extends BaseRecyclerAdapter<ProductCategory, ProductCategoryThreeAdapter.Holder>{

    public ProductCategoryThreeAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_product_category_three_item;
    }

    @Override
    public Holder createHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final ProductCategory item = datas.get(position);
        holder.txtCategory.setText(item.getName());
        holder.txtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(item);
            }
        });
    }

    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_category)
        TextView txtCategory;

        public Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(ProductCategory category);
    }
}
