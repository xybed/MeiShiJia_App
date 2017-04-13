package com.mumu.meishijia.view.im;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hwangjr.rxbus.RxBus;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.constacts.RxBusAction;
import com.mumu.meishijia.model.im.ContactsDetailModel;
import com.mumu.meishijia.model.im.ContactsRealmModel;
import com.mumu.meishijia.presenter.im.ContactsDetailPresenter;
import com.mumu.meishijia.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import lib.glide.GlideCircleTransform;
import lib.realm.MyRealm;
import lib.utils.ToastUtil;
import lib.widget.FrameProgressLayout;

public class ContactsDetailActivity extends BaseActivity implements ContactsDetailView{
    public static final String FRIEND_ID = "friend_id";
    public static final int REQ_MODIFY_REMARK = 0;
    public static final String RESULT_REMARK = "result_remark";

    @BindView(R.id.frame_progress)
    FrameProgressLayout frameProgress;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.txt_remark)
    TextView txtRemark;
    @BindView(R.id.img_sex)
    ImageView imgSex;
    @BindView(R.id.txt_username)
    TextView txtUsername;
    @BindView(R.id.txt_nick_name)
    TextView txtNickName;
    @BindView(R.id.txt_region)
    TextView txtRegion;
    @BindView(R.id.txt_signature)
    TextView txtSignature;

    private ContactsDetailPresenter presenter;

    private int friendId;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_detail);

        presenter = new ContactsDetailPresenter(this);
        initUI();
        presenter.getContactsDetail(friendId);
        realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        RxBus.get().register(this);
    }

    private void initUI(){
        ButterKnife.bind(this);
        Intent intent = getIntent();
        friendId = intent.getIntExtra(FRIEND_ID, 0);
        frameProgress.show();
        frameProgress.setOnClickRefreshListener(new FrameProgressLayout.OnClickRefreshListener() {
            @Override
            public void onClickRefresh() {
                presenter.getContactsDetail(friendId);
            }
        });
    }

    /**
     * 刷新联系人界面的联系人列表
     */
    private void refreshContactsList(){
        RxBus.get().post(RxBusAction.ContactsList, "");
    }

    @OnClick({R.id.btn_left, R.id.txt_set_remark, R.id.btn_send_msg})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.txt_set_remark:
                intent = new Intent(this, ModifyRemarkActivity.class);
                intent.putExtra(ModifyRemarkActivity.FRIEND_ID, friendId);
                intent.putExtra(ModifyRemarkActivity.REMARK, txtRemark.getText().toString());
                startActivityForResult(intent, REQ_MODIFY_REMARK);
                break;
            case R.id.btn_send_msg:
                intent = new Intent(this, ChatActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getSuccess(ContactsDetailModel result) {
        if(result == null){
            frameProgress.noData(getString(R.string.im_no_contacts_detail));
            return;
        }
        frameProgress.dismiss();
        Glide.with(this).load(result.getAvatar())
                .placeholder(R.drawable.icon_default_avatar)
                .transform(new GlideCircleTransform(this))
                .into(imgAvatar);
        if(!TextUtils.isEmpty(result.getRemark())){
            txtRemark.setText(result.getRemark());
        }else {
            txtRemark.setVisibility(View.GONE);
        }
        if(result.getSex() == 1){
            imgSex.setImageResource(R.drawable.icon_male);
            imgSex.setVisibility(View.VISIBLE);
        }else if(result.getSex() == 2){
            imgSex.setImageResource(R.drawable.icon_female);
            imgSex.setVisibility(View.VISIBLE);
        }else {
            imgSex.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(result.getUsername())){
            txtUsername.setText(getString(R.string.im_username_placeholder, result.getUsername()));
        }else {
            txtUsername.setText(getString(R.string.im_username_placeholder, ""));
        }
        if(!TextUtils.isEmpty(result.getNickname())){
            txtNickName.setText(getString(R.string.im_nick_name_placeholder, result.getNickname()));
        }else {
            txtNickName.setText(getString(R.string.im_nick_name_placeholder, ""));
        }
        if(!TextUtils.isEmpty(result.getProvince()) && !TextUtils.isEmpty(result.getCity())){
            txtRegion.setText(result.getProvince() + "  " + result.getCity());
            txtRegion.setVisibility(View.VISIBLE);
        }else {
            txtRegion.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(result.getSignature())){
            txtSignature.setText(result.getSignature());
            txtSignature.setVisibility(View.VISIBLE);
        }else {
            txtSignature.setVisibility(View.GONE);
        }
        //修改本地数据库
        ContactsRealmModel contactsRealmModel = realm.where(ContactsRealmModel.class)
                .equalTo("user_id", MyApplication.getInstance().getUser().getId()).equalTo("friend_id", friendId).findFirst();
        realm.beginTransaction();
        //好友可能主动改的数据有头像、昵称、地区、个性签名，但影响联系人表的只有头像这个字段
        contactsRealmModel.setAvatar(result.getAvatar());
        realm.insertOrUpdate(contactsRealmModel);
        realm.commitTransaction();
        //刷新联系人列表
        refreshContactsList();
    }

    @Override
    public void getFail(String errMsg) {
        frameProgress.loadFail();
        ToastUtil.show(errMsg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK)
            return;
        if(requestCode == REQ_MODIFY_REMARK){
            //修改备注名后的返回
            String remark = data.getStringExtra(RESULT_REMARK);
            txtRemark.setText(remark);
            //修改本地数据库
            ContactsRealmModel contactsRealmModel = realm.where(ContactsRealmModel.class)
                    .equalTo("user_id", MyApplication.getInstance().getUser().getId()).equalTo("friend_id", friendId).findFirst();
            realm.beginTransaction();
            contactsRealmModel.setRemark(remark);
            realm.insertOrUpdate(contactsRealmModel);
            realm.commitTransaction();
            //刷新联系人列表
            refreshContactsList();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
        super.onDestroy();
    }
}