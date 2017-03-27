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

import com.mumu.meishijia.R;
import com.mumu.meishijia.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.StringUtil;

public class LoginActivity extends BaseActivity {

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
            setRegisterButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * 设置登录按钮状态
     */
    private void setRegisterButtonState(){
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
                break;
            case R.id.txt_register:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_forget_password:
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
}
