package com.mumu.meishijia.view.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.presenter.mine.RegisterPresenter;
import com.mumu.meishijia.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.SMSSDK;
import lib.share.ShareSDKLogin;
import lib.utils.RegexUtil;
import lib.utils.StringUtil;
import lib.utils.ToastUtil;

public class RegisterActivity extends BaseActivity implements RegisterView, ShareSDKLogin.SMSListener{

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

    private RegisterPresenter presenter;

    private ShareSDKLogin shareSDKLogin;
    private boolean isLook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        addWatcher();
        presenter = new RegisterPresenter(this);
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
//            SMSSDK.getVerificationCode("86", editUsername.getText().toString());
            presenter.startTimer();
        }else{
            ToastUtil.show(getString(R.string.user_input_phone_number));
        }
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
        showLoadingDialog(getString(R.string.com_wait));
    }

    @Override
    public void smsError() {
        presenter.stopTimer();
    }

    @Override
    protected void onDestroy() {
        presenter.stopTimer();
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}
