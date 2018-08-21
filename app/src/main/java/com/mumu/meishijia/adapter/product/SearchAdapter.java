package com.mumu.meishijia.adapter.product;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseRecyclerAdapter;
import com.mumu.meishijia.model.product.SearchHistoryRealm;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 77 on 2018/8/20 0020.
 */

public class SearchAdapter extends BaseRecyclerAdapter<SearchHistoryRealm, SearchAdapter.Holder> {
    public SearchAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_search_history_item;
    }

    @Override
    public Holder createHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final SearchHistoryRealm item = datas.get(position);
        holder.txtName.setText(item.getName());
        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null)
                    listener.onItemClick(item.getName());
            }
        });
        holder.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null)
                    listener.onItemDelete(item);
            }
        });
    }

    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public interface OnItemClickListener{
        void onItemClick(String name);
        void onItemDelete(SearchHistoryRealm searchHistoryRealm);
    }

    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.img_close)
        ImageView imgClose;

        Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
