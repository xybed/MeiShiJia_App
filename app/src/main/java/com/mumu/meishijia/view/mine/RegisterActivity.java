package com.mumu.meishijia.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hwangjr.rxbus.RxBus;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.constant.RxBusAction;
import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.presenter.mine.RegisterPresenter;
import com.mumu.meishijia.view.BaseActivity;
import com.mumu.meishijia.view.common.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.SMSSDK;
import lib.share.ShareSDKLogin;
import lib.utils.RegexUtil;
import lib.utils.StringUtil;
import lib.utils.ToastUtil;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterView, ShareSDKLogin.SMSListener{

    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.img_password_look)
    ImageView imgPasswordLook;
    @BindView(R.id.edit_verify_code)
    EditText editVerifyCode;
    @BindView(R.id.btn_get_verify)
    Button btnGetVerify;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private ShareSDKLogin shareSDKLogin;
    private boolean isLook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        addWatcher();
        RxBus.get().register(this);
        shareSDKLogin = new ShareSDKLogin(this, this);
    }

    private void addWatcher(){
        btnRegister.setClickable(false);
        editUsername.addTextChangedListener(new RegisterTextWatcher());
        editPassword.addTextChangedListener(new RegisterTextWatcher());
        editVerifyCode.addTextChangedListener(new RegisterTextWatcher());
    }

    private class RegisterTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setRegisterButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * 设置注册按钮状态
     */
    private void setRegisterButtonState(){
        if(!StringUtil.isEmpty(editUsername.getText().toString())
                && !StringUtil.isEmpty(editPassword.getText().toString())
                && !StringUtil.isEmpty(editVerifyCode.getText().toString())){
            btnRegister.setBackgroundResource(R.drawable.selector_rect_theme_color);
            btnRegister.setClickable(true);
        }else{
            btnRegister.setBackgroundResource(R.drawable.shape_rect_gray_b);
            btnRegister.setClickable(false);
        }
    }

    @OnClick({R.id.btn_left, R.id.img_password_look, R.id.btn_get_verify, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.img_password_look:
                togglePassword();
                break;
            case R.id.btn_get_verify:
                getVerifyCode();
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    /**
     * 切换密码可见
     */
    private void togglePassword() {
        if(!isLook){
            imgPasswordLook.setImageResource(R.drawable.icon_password_look);
            editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else{
            imgPasswordLook.setImageResource(R.drawable.icon_password_unlook);
            editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        isLook = !isLook;
        editPassword.setSelection(editPassword.getText().length());
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        /*
        1.如果手机号符合规则，则去获取验证码
        2.开启倒计时，设置按钮状态
         */
        if(RegexUtil.checkMobile(editUsername.getText().toString())){
            SMSSDK.getVerificationCode("86", editUsername.getText().toString());
            presenter.startTimer();
        }else{
            ToastUtil.show(getString(R.string.user_input_phone_number));
        }
    }

    private void register(){
        /*
        1.验证手机号
        2.验证密码
        3.调用短信sdk，验证验证码是否正确
         */
        if(!RegexUtil.checkMobile(editUsername.getText().toString())) {
            ToastUtil.show(getString(R.string.user_input_phone_number));
            return;
        }else if(!RegexUtil.checkPassword(editPassword.getText().toString())){
            ToastUtil.show(getString(R.string.user_input_password));
            return;
        }else if(TextUtils.isEmpty(editVerifyCode.getText().toString())){
            ToastUtil.show(getString(R.string.user_input_verify_code));
            return;
        }
        //回调正确的地方去调presenter的注册
        showLoadingDialog(getString(R.string.user_registering), false, false);
        SMSSDK.submitVerificationCode("86", editUsername.getText().toString(), editVerifyCode.getText().toString());
    }

    @Override
    public void onTimerRunning(int totalTime) {
        btnGetVerify.setText(getString(R.string.user_second_placeholder, totalTime));
        btnGetVerify.setBackgroundResource(R.drawable.shape_rect_gray_b);
        btnGetVerify.setClickable(false);
    }

    @Override
    public void onTimerEnd() {
        btnGetVerify.setText(getString(R.string.user_get_verify_code));
        btnGetVerify.setBackgroundResource(R.drawable.selector_rect_theme_color);
        btnGetVerify.setClickable(true);
    }

    @Override
    public void submitSuccess() {
        //短信验证成功,去注册
        presenter.register(editUsername.getText().toString(), editPassword.getText().toString(), editVerifyCode.getText().toString());
    }

    @Override
    public void smsError() {
        dismissLoadingDialog();
        presenter.stopTimer();
        ToastUtil.show(getString(R.string.user_error_verify_code));
    }

    @Override
    public void registerSuccess(String result) {
        dismissLoadingDialog();
        ToastUtil.show(result);
        //注册成功，自动去登录
        showLoadingDialog(getString(R.string.user_logging), false, false);
        presenter.login(editUsername.getText().toString(), editPassword.getText().toString());
    }

    @Override
    public void loginSuccess(UserModel result) {
        dismissLoadingDialog();
        //登录成功，跳转主界面
        MyApplication.getInstance().setUser(result);
        MyApplication.getInstance().setLogin(true);
        //通知我的界面刷新数据,不传任何数据
        RxBus.get().post(RxBusAction.MineUserData, "");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.stopTimer();
        SMSSDK.unregisterAllEventHandler();
        RxBus.get().unregister(this);
        super.onDestroy();
    }
}
