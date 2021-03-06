package com.mumu.meishijia.view.mine;

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

import com.mumu.meishijia.R;
import com.mumu.meishijia.presenter.mine.ForgetPwdPresenter;
import com.mumu.meishijia.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.SMSSDK;
import lib.share.ShareSDKLogin;
import lib.utils.RegexUtil;
import lib.utils.StringUtil;
import lib.utils.ToastUtil;

public class ForgetPwdActivity extends BaseActivity<ForgetPwdPresenter> implements ForgetPwdView, ShareSDKLogin.SMSListener{

    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_verify_code)
    EditText editVerifyCode;
    @BindView(R.id.btn_get_verify)
    Button btnGetVerify;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.img_password_look)
    ImageView imgPasswordLook;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    private ShareSDKLogin shareSDKLogin;
    private boolean isLook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);

        ButterKnife.bind(this);
        addWatcher();
        shareSDKLogin = new ShareSDKLogin(this, this);
    }

    private void addWatcher(){
        btnCommit.setClickable(false);
        editUsername.addTextChangedListener(new ForgetPwdTextWatcher());
        editVerifyCode.addTextChangedListener(new ForgetPwdTextWatcher());
        editPassword.addTextChangedListener(new ForgetPwdTextWatcher());
    }

    private class ForgetPwdTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setCommitButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * 设置提交按钮状态
     */
    private void setCommitButtonState(){
        if(!StringUtil.isEmpty(editUsername.getText().toString())
                && !StringUtil.isEmpty(editVerifyCode.getText().toString())
                && !StringUtil.isEmpty(editPassword.getText().toString())){
            btnCommit.setBackgroundResource(R.drawable.selector_rect2_theme_color);
            btnCommit.setClickable(true);
        }else{
            btnCommit.setBackgroundResource(R.drawable.shape_rect_gray_b);
            btnCommit.setClickable(false);
        }
    }

    @OnClick({R.id.btn_get_verify, R.id.img_password_look, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_verify:
                getVerifyCode();
                break;
            case R.id.img_password_look:
                togglePassword();
                break;
            case R.id.btn_commit:
                modifyPwd();
                break;
        }
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

    private void modifyPwd(){
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
        //短信回调的地方去调修改密码的接口
        showLoadingDialog(getString(R.string.com_committing), false, false);
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
        btnGetVerify.setBackgroundResource(R.drawable.selector_rect2_theme_color);
        btnGetVerify.setClickable(true);
    }

    @Override
    public void submitSuccess() {
        //短信验证成功,去修改密码
        presenter.modifyPwd(editUsername.getText().toString(), editPassword.getText().toString());
    }

    @Override
    public void smsError() {
        dismissLoadingDialog();
        presenter.stopTimer();
        ToastUtil.show(getString(R.string.user_error_verify_code));
    }

    @Override
    public void modifySuccess(String result) {
        dismissLoadingDialog();
        ToastUtil.show(result);
        //关闭此界面，现只有登录界面跳转过来，关闭就回到登录界面去登录
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.stopTimer();
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}
