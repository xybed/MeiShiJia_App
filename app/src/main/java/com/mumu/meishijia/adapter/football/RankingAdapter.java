package com.mumu.meishijia.adapter.football;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseMyAdapter;
import com.mumu.meishijia.model.football.RankingModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 排名的行adapter
 * Created by Administrator on 2017/6/7.
 */

public class RankingAdapter extends BaseMyAdapter<RankingModel> {
    public RankingAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_ranking_item;
    }

    @Override
    public Object initView(int position, View convertView) {
        Holder holder = new Holder(convertView);
        return holder;
    }

    @Override
    public void fillView(int position, View convertView, Object mHolder) {
        Holder holder = (Holder) mHolder;
        if(position == 0){
            holder.llayText.setVisibility(View.VISIBLE);
        }else {
            holder.llayText.setVisibility(View.GONE);
        }
        RankingModel data = datas.get(position);
        holder.txtRanking.setText(data.getRanking()+"");
        Glide.with(context).load(data.getLogo()).into(holder.imgLogo);
        holder.txtName.setText(data.getName());
        holder.txtPlays.setText(data.getPlays()+"");
        holder.txtPoint.setText(data.getPoint()+"");
        holder.txtWin.setText(data.getWin()+"");
        holder.txtDraw.setText(data.getDraw()+"");
        holder.txtLose.setText(data.getLose()+"");
        holder.txtGoal.setText(data.getGoal()+"");
        holder.txtConceded.setText(data.getConceded()+"");
        holder.txtGoalDifference.setText(data.getGoal_difference()+"");
    }

    class Holder {
        @BindView(R.id.llay_text)
        LinearLayout llayText;
        @BindView(R.id.txt_ranking)
        TextView txtRanking;
        @BindView(R.id.img_logo)
        ImageView imgLogo;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_plays)
        TextView txtPlays;
        @BindView(R.id.txt_point)
        TextView txtPoint;
        @BindView(R.id.txt_win)
        TextView txtWin;
        @BindView(R.id.txt_draw)
        TextView txtDraw;
        @BindView(R.id.txt_lose)
        TextView txtLose;
        @BindView(R.id.txt_goal)
        TextView txtGoal;
        @BindView(R.id.txt_conceded)
        TextView txtConceded;
        @BindView(R.id.txt_goal_difference)
        TextView txtGoalDifference;
        @BindView(R.id.llay_item)
        LinearLayout llayItem;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
