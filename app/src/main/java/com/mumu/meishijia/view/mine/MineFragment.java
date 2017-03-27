package com.mumu.meishijia.view.mine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mumu.meishijia.R;
import com.mumu.meishijia.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.glide.GlideCircleTransform;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {


    @BindView(R.id.img_avatar)
    ImageView imgAvatar;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        Glide.with(this).load(R.drawable.icon_default_avatar)
                .transform(new GlideCircleTransform(getActivity()))
                .into(imgAvatar);
        return view;
    }

    @OnClick({R.id.llay_top_img})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.llay_top_img:
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
