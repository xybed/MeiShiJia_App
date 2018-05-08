package com.mumu.meishijia.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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
import com.mumu.meishijia.presenter.mine.LoginPresenter;
import com.mumu.meishijia.tencent.IMUtil;
import com.mumu.meishijia.view.BaseActivity;
import com.mumu.meishijia.view.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.RegexUtil;
import lib.utils.StringUtil;
import lib.utils.ToastUtil;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView{

    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.img_password_look)
    ImageView imgPasswordLook;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private boolean isLook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        addWatcher();
    }

    private void addWatcher(){
        btnLogin.setClickable(false);
        editUsername.addTextChangedListener(new LoginTextWatcher());
        editPassword.addTextChangedListener(new LoginTextWatcher());
    }

    private class LoginTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setLoginButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * 设置登录按钮状态
     */
    private void setLoginButtonState(){
        if(!StringUtil.isEmpty(editUsername.getText().toString())
                && !StringUtil.isEmpty(editPassword.getText().toString())){
            btnLogin.setBackgroundResource(R.drawable.selector_rect_theme_color);
            btnLogin.setClickable(true);
        }else{
            btnLogin.setBackgroundResource(R.drawable.shape_rect_gray_b);
            btnLogin.setClickable(false);
        }
    }

    @OnClick({R.id.btn_left, R.id.img_password_look, R.id.btn_login, R.id.txt_register, R.id.txt_forget_password})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.img_password_look:
                togglePassword();
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.txt_register:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_forget_password:
                intent = new Intent(this, ForgetPwdActivity.class);
                startActivity(intent);
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

    private void login(){
        /*
        1.验证手机号
        2.验证密码规则
         */
        if(!RegexUtil.checkMobile(editUsername.getText().toString())) {
            ToastUtil.show(getString(R.string.user_input_phone_number));
            return;
        }else if(!RegexUtil.checkPassword(editPassword.getText().toString())){
            ToastUtil.show(getString(R.string.user_input_password));
            return;
        }
        showLoadingDialog(getString(R.string.user_logging), false, false);
        presenter.login(editUsername.getText().toString(), editPassword.getText().toString());
    }

    @Override
    public void loginSuccess(UserModel result) {
        dismissLoadingDialog();
        //登录成功，跳转主界面
        MyApplication.getInstance().setUser(result);
        MyApplication.getInstance().setLogin(true);
        //登录腾讯IM
        IMUtil.getInstance().loginIM();
        //通知我的界面刷新数据
        RxBus.get().post(RxBusAction.MineUserData, "");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
