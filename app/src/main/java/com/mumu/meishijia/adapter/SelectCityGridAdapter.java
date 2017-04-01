package com.mumu.meishijia.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mumu.meishijia.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择城市界面热门城市的adapter
 * Created by Administrator on 2017/3/31.
 */

public class SelectCityGridAdapter extends BaseMyAdapter<String> {

    public SelectCityGridAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_hot_city_item;
    }

    @Override
    public Object initView(int position, View convertView) {
        return new Holder(convertView);
    }

    @Override
    public void fillView(int position, View convertView, Object mHolder) {
        Holder holder = (Holder) mHolder;
        String data = datas.get(position);
        holder.txtHotCity.setText(data);
    }

    class Holder {
        @BindView(R.id.txt_hot_city)
        TextView txtHotCity;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
