package com.mumu.meishijia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 适用于RecyclerView的基类适配器
 * Created by 77 on 2018/7/10 0010.
 */

public abstract class BaseRecyclerAdapter<T, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H>{

    protected Context context;
    protected List<T> datas;

    public BaseRecyclerAdapter(Context context){
        this.context = context;
        datas = new ArrayList<T>();
    }

    public abstract int getLayoutResourceId();

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(getLayoutResourceId(), parent, false);
        return createHolder(view);
    }

    public abstract H createHolder(View view);

    @Override
    public int getItemCount() {
        if (datas != null) {
            return datas.size();
        }
        return 0;
    }

    /**
     * 设置数据
     * @param datas
     */
    public void setData(List<T> datas) {
        this.datas.clear();
        if (datas == null){
            notifyDataSetChanged();
            return;
        }
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 增加数据
     * @param datas
     */
    public void addData(List<T> datas){
        if(datas == null)
            return;
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 获取数据
     * @return
     */
    public List<T> getData() {
        return this.datas;
    }

    /**
     * 清除数据
     */
    public void clearData(){
        datas.clear();
        notifyDataSetChanged();
    }
}
