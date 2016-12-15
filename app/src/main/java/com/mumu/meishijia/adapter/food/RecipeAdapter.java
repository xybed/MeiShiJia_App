package com.mumu.meishijia.adapter.food;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseMyAdapter;
import com.mumu.meishijia.model.food.RecipeModel;
import com.mumu.meishijia.model.food.RecipeSubModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lib.utils.ToastUtil;
import lib.widget.FlowLayout;

/**
 * Created by Administrator on 2016/12/14.
 */

public class RecipeAdapter extends BaseMyAdapter<RecipeModel> {

    public RecipeAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_recipe_list_item;
    }

    @Override
    public Object initView(int position, View convertView) {
        Holder holder = new Holder(convertView);
        return holder;
    }

    @Override
    public void fillView(int position, View convertView, Object mHolder) {
        Holder holder = (Holder) mHolder;
        RecipeModel data = datas.get(position);
        holder.txtTwoLevel.setText(data.getValue());
        List<RecipeSubModel> recipeSubModels = data.getSub();
        for(final RecipeSubModel recipeSubModel : recipeSubModels){
            TextView textView = (TextView) mInflater.inflate(
                    R.layout.layout_recipe_flowlayout_item,
                    null);
            textView.setText(recipeSubModel.getValue());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = recipeSubModel.getId();
                    //TODO 跳转食物详情
                    ToastUtil.show(""+id);
                }
            });
            holder.flowLayout.addView(textView);
        }
    }

    class Holder {
        @Bind(R.id.txt_two_level)
        TextView txtTwoLevel;
        @Bind(R.id.flow_layout)
        FlowLayout flowLayout;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
