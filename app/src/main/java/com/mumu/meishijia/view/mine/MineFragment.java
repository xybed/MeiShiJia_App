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
import com.mumu.meishijia.constant.RxBusAction;
import com.mumu.meishijia.tencent.dbmodel.ConversationRealmModel;
import com.mumu.meishijia.test.TestActivity;
import com.mumu.meishijia.view.BaseFragment;
import com.mumu.meishijia.view.im.ContactsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import lib.glide.GlideCircleTransform;
import lib.realm.MyRealm;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {


    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.txt_user)
    TextView txtUser;
    @BindView(R.id.txt_msg_unread)
    TextView txtMsgUnread;

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
        checkLoginRefreshUI();
    }

    private void checkLoginRefreshUI(){
        if (MyApplication.getInstance().isLogin()) {
            Glide.with(this).load(MyApplication.getInstance().getUser().getAvatar())
                    .placeholder(R.drawable.icon_default_avatar)
                    .error(R.drawable.icon_default_avatar)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(imgAvatar);
            txtUser.setText(MyApplication.getInstance().getUser().getNickname());
            refreshUnreadMsg();
        } else {
            Glide.with(this).load(R.drawable.icon_default_avatar)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(imgAvatar);
            txtUser.setText(getString(R.string.user_login));
            txtMsgUnread.setText("");
            txtMsgUnread.setVisibility(View.GONE);
        }
    }

    private void refreshUnreadMsg(){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        RealmResults<ConversationRealmModel> conversationList = realm.where(ConversationRealmModel.class).findAll();
        int unread = 0;
        for(ConversationRealmModel conversation : conversationList){
            unread += conversation.getUnread_msg();
        }
        if(unread <= 0){
            txtMsgUnread.setVisibility(View.GONE);
        }else if(unread > 0 && unread <= 99){
            txtMsgUnread.setVisibility(View.VISIBLE);
            txtMsgUnread.setText(unread+"");
        }else {
            txtMsgUnread.setVisibility(View.VISIBLE);
            txtMsgUnread.setText("99");
        }
    }

    @OnClick({R.id.llay_top_img, R.id.llay_receiving_address, R.id.llay_conversation, R.id.llay_contacts, R.id.llay_setting})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.llay_top_img:
                if(MyApplication.getInstance().isLogin()){
                    intent = new Intent(getActivity(), UserInfoActivity.class);
                }else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.llay_receiving_address:
                if(MyApplication.getInstance().isLogin()){
                    intent = new Intent(getActivity(), ReceivingAddressActivity.class);
                }else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.llay_conversation:
                if(MyApplication.getInstance().isLogin()){
                    intent = new Intent(getActivity(), TestActivity.class);
                }else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.llay_contacts:
                if(MyApplication.getInstance().isLogin()){
                    intent = new Intent(getActivity(), ContactsActivity.class);
                }else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.llay_setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Subscribe(
        thread = EventThread.MAIN_THREAD,
        tags = {
                @Tag(RxBusAction.MineUserData)
        }
    )
    public void rbRefreshTop(String s){
        checkLoginRefreshUI();
    }

    @Subscribe(
        thread = EventThread.MAIN_THREAD,
        tags = {
                @Tag(RxBusAction.MineUnreadMsg)
        }
    )
    public void rbRefreshUnreadMsg(String s){
        refreshUnreadMsg();
    }

    @Override
    public void onDestroyView() {
        RxBus.get().unregister(this);
        super.onDestroyView();
    }
}
