package com.mumu.meishijia.view.mine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.constacts.RxBusAction;
import com.mumu.meishijia.model.mine.UserModel;
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
    @BindView(R.id.txt_user)
    TextView txtUser;

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initUI(view);
        RxBus.get().register(this);
        return view;
    }

    private void initUI(View view){
        ButterKnife.bind(this, view);
        if (MyApplication.getInstance().isLogin()) {
            Glide.with(this).load(MyApplication.getInstance().getUser().getAvatar())
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(imgAvatar);
            txtUser.setText(MyApplication.getInstance().getUser().getNickname());
        } else {
            Glide.with(this).load(R.drawable.icon_default_avatar)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(imgAvatar);
            txtUser.setText(getString(R.string.user_login));
        }
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

    @Subscribe(
        thread = EventThread.MAIN_THREAD,
        tags = {
                @Tag(RxBusAction.Login)
        }
    )
    public void rbRefreshTop(UserModel userModel){
        Glide.with(this).load(userModel.getAvatar())
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .transform(new GlideCircleTransform(getActivity()))
                .into(imgAvatar);
        txtUser.setText(userModel.getNickname());
    }

    @Override
    public void onDestroyView() {
        RxBus.get().unregister(this);
        super.onDestroyView();
    }
}
