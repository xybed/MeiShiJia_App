package com.mumu.meishijia.view.mine;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.presenter.mine.UserInfoPresenter;
import com.mumu.meishijia.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.cache.CacheJsonMgr;
import lib.glide.GlideCircleTransform;
import lib.utils.DatePickUtil;
import lib.utils.NumberUtil;
import lib.utils.ToastUtil;
import lib.widget.ActionSheet;

public class UserInfoActivity extends BaseActivity implements UserInfoView {

    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.edit_nickname)
    EditText editNickname;
    @BindView(R.id.edit_real_name)
    EditText editRealName;
    @BindView(R.id.txt_sex)
    TextView txtSex;
    @BindView(R.id.txt_birthday)
    TextView txtBirthday;
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.edit_city)
    EditText editCity;

    private UserInfoPresenter presenter;

    private String sexCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ButterKnife.bind(this);
        initUI();
        presenter = new UserInfoPresenter(this);
    }

    private void initUI(){
        if(!MyApplication.getInstance().isLogin()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        UserModel user = MyApplication.getInstance().getUser();
        Glide.with(this).load(user.getAvatar())
                .transform(new GlideCircleTransform(this))
                .into(imgAvatar);
        editNickname.setText(user.getNickname());
        if(!TextUtils.isEmpty(user.getReal_name())){
            editRealName.setText(user.getReal_name());
        }
        switch (user.getSex()){
            case 0:
                txtSex.setText(getString(R.string.user_sex_unknown));
                sexCode = "0";
                break;
            case 1:
                txtSex.setText(getString(R.string.user_male));
                sexCode = "1";
                break;
            case 2:
                txtSex.setText(getString(R.string.user_female));
                sexCode = "2";
                break;
        }
        if(!TextUtils.isEmpty(user.getBirthday())){
            txtBirthday.setText(user.getBirthday());
        }
        if(!TextUtils.isEmpty(user.getEmail())){
            editEmail.setText(user.getEmail());
        }
        if(!TextUtils.isEmpty(user.getCity())){
            editCity.setText(user.getCity());
        }
    }

    @OnClick({R.id.btn_left, R.id.txt_save, R.id.llay_sex, R.id.llay_birthday})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.txt_save:
                modifyUserInfo();
                break;
            case R.id.llay_sex:
                changeSex();
                break;
            case R.id.llay_birthday:
                changeBirthday();
                break;
        }
    }

    private void changeSex(){
        ActionSheet.createBuilder(this)
                .setCancelableOnTouchOutside(true)
                .setCancelButtonTitle(R.string.com_cancel)
                .setOtherButtonTitles(getResources().getStringArray(R.array.user_sex_group))
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onOtherButtonClick(int index) {
                        switch (index){
                            case 0:
                                sexCode = "0";
                                txtSex.setText(getString(R.string.user_sex_unknown));
                                break;
                            case 1:
                                sexCode = "1";
                                txtSex.setText(getString(R.string.user_male));
                                break;
                            case 2:
                                sexCode = "2";
                                txtSex.setText(getString(R.string.user_female));
                                break;
                        }
                    }
                }).show();
    }

    private void changeBirthday(){
        DatePickUtil.datePicker(this, txtBirthday, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String month = monthOfYear + 1 + "";
                String day = dayOfMonth + "";
                if (monthOfYear + 1 < 10)
                    month = "0" + month;
                if (dayOfMonth < 10)
                    day = "0" + day;
                if (DatePickUtil.dateCompare(year, monthOfYear, dayOfMonth)) {
                    txtBirthday.setText(year + "-" + month + "-" + day);
                }else {
                    ToastUtil.show(getString(R.string.user_illegal_birthday));
                }
            }
        });
    }

    private void modifyUserInfo(){
        if(TextUtils.isEmpty(editNickname.getText().toString())){
            ToastUtil.show(getString(R.string.user_nickname_tip));
            return;
        }
        showLoadingDialog(getString(R.string.com_committing));
        presenter.modifyUserInfo(MyApplication.getInstance().getUser().getId()+"", editNickname.getText().toString(),
                editRealName.getText().toString(), sexCode, txtBirthday.getText().toString(),
                editEmail.getText().toString(), editCity.getText().toString());
    }

    @Override
    public void modifySuccess(String result) {
        dismissLoadingDialog();
        //更改本地的userModel数据
        CacheJsonMgr cacheJsonMgr = CacheJsonMgr.getInstance(this);
        UserModel userModel = MyApplication.getInstance().getUser();
        userModel.setNickname(editNickname.getText().toString());
        userModel.setReal_name(editRealName.getText().toString());
        userModel.setSex(NumberUtil.parseInt(sexCode, 0));
        userModel.setBirthday(txtBirthday.getText().toString());
        userModel.setEmail(editEmail.getText().toString());
        userModel.setCity(editCity.getText().toString());
        String jsonStr = JSON.toJSONString(userModel);
        cacheJsonMgr.saveJson(jsonStr, UserModel.class.getSimpleName());
        finish();
    }

    @Override
    public void modifyFail(String errMsg) {
        dismissLoadingDialog();
        ToastUtil.show(errMsg);
    }
}
