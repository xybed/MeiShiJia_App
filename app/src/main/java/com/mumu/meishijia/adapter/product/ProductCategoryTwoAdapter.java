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
 * 商品二级分类adapter
 * Created by 77 on 2018/7/30 0030.
 */

public class ProductCategoryTwoAdapter extends BaseRecyclerAdapter<ProductCategory, ProductCategoryTwoAdapter.Holder>{
    public ProductCategoryTwoAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_product_category_two_item;
    }

    @Override
    public Holder createHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final int pos = position;
        ProductCategory item = datas.get(position);
        holder.txtCategory.setText(item.getName());
        if(item.isSelected()){
            holder.txtCategory.setTextColor(context.getResources().getColor(R.color.theme_color));
        }else {
            holder.txtCategory.setTextColor(context.getResources().getColor(R.color.black_b));
        }
        holder.txtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(pos);
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
        void onItemClick(int position);
    }
}
