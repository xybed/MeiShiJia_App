package com.mumu.meishijia.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import lib.utils.ToastUtil;
import lib.widget.LoadingDialog;

public class BaseActivity extends AppCompatActivity implements BaseView{

    private SystemBarTintManager tintManager;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            setTranslucentStatus(true);

            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            //此处可以重新指定状态栏颜色
            tintManager.setTintColor(0xffe8866c);
//                tintManager.setTintResource(R.drawable.bg_bar_status);
        }
        ToastUtil.setContext(this);//统一设置toast的context
    }

    @Override
    public void transparencyStatusBar(boolean isTransparency){
        //版本大于4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(isTransparency)
                //设置为全透明
                tintManager.setTintColor(0x00000000);
            else{
                tintManager.setTintColor(0xffe8866c);
//                tintManager.setTintResource(R.drawable.bg_bar_status);
            }
        }
    }

    @Override
    public void transparencyStatusBar(){
        //版本大于4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            tintManager.setTintColor(0x00000000);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideSoftInput();
    }

    @Override
    public void showLoadingDialog(String message, boolean cancelable, boolean otoCancelable){
        loadingDialog = new LoadingDialog(this, message, cancelable, otoCancelable);
        loadingDialog.show();
    }

    @Override
    public void showLoadingDialog(String message){
        showLoadingDialog(message, true, true);
    }

    @Override
    public void dismissLoadingDialog(){
        if(loadingDialog != null)
            loadingDialog.dismiss();
    }

    /**
     * 隐藏软键盘
     */
    @Override
    public void hideSoftInput() {
        /** 隐藏软键盘 **/
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
