package com.mumu.meishijia.adapter.football;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseRecyclerAdapter;
import com.mumu.meishijia.model.football.FootballPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 77 on 2018/7/10 0010.
 */

public class PlayerAdapter extends BaseRecyclerAdapter<FootballPlayer, PlayerAdapter.Holder> {

    public PlayerAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_player_item;
    }

    @Override
    public Holder createHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        FootballPlayer item = datas.get(position);

    }


    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_number)
        TextView txtNumber;

        Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
