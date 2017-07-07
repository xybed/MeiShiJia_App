package com.mumu.meishijia.view.mine;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.hwangjr.rxbus.RxBus;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.constant.RxBusAction;
import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.presenter.mine.UserInfoPresenter;
import com.mumu.meishijia.view.BaseActivity;
import com.mumu.meishijia.view.SelectCityActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.cache.CacheJsonMgr;
import lib.glide.GlideCircleTransform;
import lib.utils.DatePickUtil;
import lib.utils.MyLogUtil;
import lib.utils.NumberUtil;
import lib.utils.PhotoUtil;
import lib.utils.StreamUtil;
import lib.utils.ToastUtil;
import lib.widget.ActionSheet;

public class UserInfoActivity extends BaseActivity implements UserInfoView {
    public static final int REQ_CITY = 1;
    public static final String RESULT_PROVINCE = "result_province";
    public static final String RESULT_CITY = "result_city";

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
    @BindView(R.id.txt_city)
    TextView txtCity;
    @BindView(R.id.edit_signature)
    EditText editSignature;

    private UserInfoPresenter presenter;

    private String sexCode;
    private String province;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ButterKnife.bind(this);
        initUI();
        presenter = new UserInfoPresenter(this);
        RxBus.get().register(this);
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
        editRealName.setText(user.getReal_name());
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
        txtBirthday.setText(user.getBirthday());
        editEmail.setText(user.getEmail());
        if(!TextUtils.isEmpty(user.getProvince()) && !TextUtils.isEmpty(user.getCity())){
            province = user.getProvince();
            city = user.getCity();
            txtCity.setText(province + "  " + city);
        }
        editSignature.setText(user.getSignature());
    }

    @OnClick({R.id.btn_left, R.id.txt_save, R.id.img_avatar, R.id.llay_sex, R.id.llay_birthday, R.id.llay_city})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.txt_save:
                modifyUserInfo();
                break;
            case R.id.img_avatar:
                if(permissionIsGet(REQ_CAMERA_PMS, Manifest.permission.CAMERA)){
                    changeAvatar();
                }
                break;
            case R.id.llay_sex:
                changeSex();
                break;
            case R.id.llay_birthday:
                changeBirthday();
                break;
            case R.id.llay_city:
                startActivityForResult(new Intent(this, SelectCityActivity.class), REQ_CITY);
                break;
        }
    }

    private void changeAvatar(){
        ActionSheet.createBuilder(this)
                .setCancelableOnTouchOutside(true)
                .setCancelButtonTitle(R.string.com_cancel)
                .setOtherButtonTitles(getString(R.string.user_take_photo), getString(R.string.user_select_photo))
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onOtherButtonClick(int index) {
                        switch (index){
                            case 0:
                                PhotoUtil.takePhoto(UserInfoActivity.this);
                                break;
                            case 1:
//                                PhotoUtil.selectPhoto(UserInfoActivity.this);
                                selectPhoto();
                                break;
                        }
                    }
                }).show();
    }

    private void selectPhoto(){
        Matisse.from(UserInfoActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(9)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.matisse_select_photo_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .theme(R.style.Matisse_Dracula)
                .forResult(21);
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
                editEmail.getText().toString(), province, city, editSignature.getText().toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case REQ_CAMERA_PMS:
                if (grantResults != null && grantResults.length != 0)
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        ToastUtil.show(getString(R.string.com_open_photograph_permission));
                    }else {
                        changeAvatar();
                    }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK)
            return;
        //选择城市回调的结果
        if(requestCode == REQ_CITY){
            province = data.getStringExtra(RESULT_PROVINCE);
            city = data.getStringExtra(RESULT_CITY);
            txtCity.setText(province + "  " + city);
            return;
        }
        //选择图片返回
        PhotoUtil.onActivityResult(this, requestCode, data, new PhotoUtil.PhotoResultListener() {
            @Override
            public void photoResultSuccess(String path) {
                Bitmap bitmap = PhotoUtil.file2Bitmap(path);
                InputStream is = StreamUtil.bitmap2InputStream(bitmap);
                Glide.with(UserInfoActivity.this).load(StreamUtil.inputStream2Byte(is))
                        .transform(new GlideCircleTransform(UserInfoActivity.this))
                        .into(imgAvatar);
                showLoadingDialog(getString(R.string.user_upload_avatar), false, false);
                presenter.modifyAvatar(path);
            }

            @Override
            public void photoResultFail(String errMsg) {
                ToastUtil.show(errMsg);
            }
        });
        if (requestCode == 21) {
            List<Uri> mSelected;
            mSelected = Matisse.obtainResult(data);
            for(Uri uri : mSelected){
                MyLogUtil.e("Matisse", "uri: " + uri.getPath());
            }
        }
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
        userModel.setProvince(province);
        userModel.setCity(city);
        userModel.setSignature(editSignature.getText().toString());
        String jsonStr = JSON.toJSONString(userModel);
        cacheJsonMgr.saveJson(jsonStr, UserModel.class.getSimpleName());
        RxBus.get().post(RxBusAction.MineUserData, "");
        finish();
    }

    @Override
    public void modifyFail(String errMsg) {
        dismissLoadingDialog();
        ToastUtil.show(errMsg);
    }

    @Override
    public void modifyAvatarSuccess(String result) {
        dismissLoadingDialog();
        //更改本地的userModel数据
        CacheJsonMgr cacheJsonMgr = CacheJsonMgr.getInstance(this);
        UserModel userModel = MyApplication.getInstance().getUser();
        userModel.setAvatar(result);
        String jsonStr = JSON.toJSONString(userModel);
        cacheJsonMgr.saveJson(jsonStr, UserModel.class.getSimpleName());
        RxBus.get().post(RxBusAction.MineUserData, "");
        ToastUtil.show(R.string.com_upload_success);
    }

    @Override
    public void modifyAvatarFail(String errMsg) {
        dismissLoadingDialog();
        ToastUtil.show(errMsg);
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
        super.onDestroy();
    }
}
