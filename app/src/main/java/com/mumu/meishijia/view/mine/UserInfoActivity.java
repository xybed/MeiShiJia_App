package com.mumu.meishijia.view.mine;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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
import com.mumu.meishijia.model.mine.User;
import com.mumu.meishijia.presenter.mine.UserInfoPresenter;
import com.mumu.meishijia.view.BaseActivity;
import com.mumu.meishijia.view.common.SelectCityActivity;
import com.zhihu.matisse.Matisse;

import java.io.InputStream;
import java.util.ArrayList;
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

public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements UserInfoView {
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

    private String sexCode;
    private String province;
    private String city;
    private String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ButterKnife.bind(this);
        initUI();
        RxBus.get().register(this);
    }

    private void initUI(){
        if(!MyApplication.getInstance().isLogin()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        User user = MyApplication.getInstance().getUser();
        Glide.with(this).load(user.getAvatar())
                .placeholder(R.drawable.icon_default_avatar)
                .transform(new GlideCircleTransform(this))
                .into(imgAvatar);
        editNickname.setText(user.getNickname());
        editRealName.setText(user.getRealName());
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
        avatar = user.getAvatar();
    }

    @Override
    protected void onRightButtonClick() {
        modifyUserInfo();
    }

    @OnClick({R.id.img_avatar, R.id.llay_sex, R.id.llay_birthday, R.id.llay_city})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                changeAvatar();
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
                                //如果没获取到权限，到onRequestPermissionsResult中处理
                                //拍照还需要内存读写权限
                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                    List<String> permissionList = new ArrayList<String>();
                                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                    }
                                    if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                                        permissionList.add(Manifest.permission.CAMERA);
                                    }
                                    if(permissionList.size() > 0){
                                        requestPermissions(permissionList.toArray(new String[permissionList.size()]), REQ_CAMERA_AND_STORAGE_PMS);
                                    }else {
                                        PhotoUtil.takePhoto(UserInfoActivity.this);
                                    }
                                }else {
                                    PhotoUtil.takePhoto(UserInfoActivity.this);
                                }
                                break;
                            case 1:
                                if(permissionIsGet(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQ_WRITE_EXTERNAL_STORAGE_PMS)){
                                    PhotoUtil.selectPhoto(UserInfoActivity.this, 1);
                                }
                                break;
                        }
                    }
                }).show();
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
        presenter.modifyUserInfo(MyApplication.getInstance().getUser().getId()+"", avatar, editNickname.getText().toString(),
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
                        PhotoUtil.takePhoto(UserInfoActivity.this);
                    }
                break;
            case REQ_WRITE_EXTERNAL_STORAGE_PMS:
                if(grantResults != null && grantResults.length != 0)
                    if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                        ToastUtil.show(getString(R.string.com_open_write_external_storage_permission));
                    }else {
                        PhotoUtil.selectPhoto(UserInfoActivity.this, 1);
                    }
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
                showLoadingDialog(getString(R.string.com_upload_image), false, false);
                presenter.uploadImage(path);
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
        User user = MyApplication.getInstance().getUser();
        user.setAvatar(avatar);
        user.setNickname(editNickname.getText().toString());
        user.setRealName(editRealName.getText().toString());
        user.setSex(NumberUtil.parseInt(sexCode, 0));
        user.setBirthday(txtBirthday.getText().toString());
        user.setEmail(editEmail.getText().toString());
        user.setProvince(province);
        user.setCity(city);
        user.setSignature(editSignature.getText().toString());
        String jsonStr = JSON.toJSONString(user);
        cacheJsonMgr.saveJson(jsonStr, User.class.getSimpleName());
        RxBus.get().post(RxBusAction.MineUserData, "");
        finish();
    }

    @Override
    public void uploadSuccess(String result) {
        dismissLoadingDialog();
        ToastUtil.show(R.string.com_upload_success);
        avatar = result;
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
        super.onDestroy();
    }
}
