package com.mumu.meishijia.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.mumu.meishijia.R;
import com.mumu.meishijia.presenter.BasePresenter;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import lib.baidu.MyLocation;
import lib.utils.MyLogUtil;
import lib.utils.ToastUtil;
import lib.widget.LoadingDialog;

public class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView{

    protected P presenter;

    private SystemBarTintManager tintManager;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createPresenter();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            setTranslucentStatus(true);

            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            //此处可以重新指定状态栏颜色
            tintManager.setTintColor(0xffe8866c);
//                tintManager.setTintResource(R.drawable.bg_bar_status);
        }

    }

    @SuppressWarnings("unchecked")
    private void createPresenter(){
        try {
            presenter = ((Class<P>) ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]).getConstructor(BaseView.class).newInstance(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassCastException e){
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
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
        presenter.unSubscribe();
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

    /**
     * Manifest.permission.READ_PHONE_STATE
     * Manifest.permission.WRITE_EXTERNAL_STORAGE
     * Manifest.permission.ACCESS_FINE_LOCATION
     * Manifest.permission.CAMERA
     * Manifest.permission.RECORD_AUDIO
     * Manifest.permission.READ_PHONE_STATE
     * Manifest.permission.WRITE_EXTERNAL_STORAGE
     * 权限检查
     */
    protected final static int REQ_CAMERA_PMS = 0x001;
    protected final static int REQ_RECORD_PMS = 0x002;
    protected final static int REQ_LOCATION_PMS = 0x003;
    protected final static int REQ_READ_PHONE_STATE_PMS = 0x004;
    protected final static int REQ_WRITE_EXTERNAL_STORAGE_PMS = 0x005;
    protected final static int REQ_PERMISSION_GROUP = 0x006;//在欢迎界面用到的
    protected final static int REQ_CAMERA_AND_STORAGE_PMS = 0x007;//拍照功能需要拍照权限和内存读取权限

    /**
     * 查看是否获取到了对应权限
     * ---如果没有，就去申请权限，并返回false
     * ---如果获取到了，就返回true
     * @param permission 权限
     * @param reqCode 请求code
     */
    public boolean permissionIsGet(String permission, int reqCode){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            //这一句需加在版本判断里，不然ide会飘红。。。
            requestPermissions(new String[]{permission}, reqCode);
            return false;
        }else {
            return true;
        }
    }

    /**
     * 申请权限后的回调
     * 按代码逻辑，check的时候传的是String，在request权限的时候也只有一个String，在这里的两个数组内的元素应该都是一个，注释更改下代码，之后再看看
     * @param requestCode requestCode
     * @param permissions permissions
     * @param grantResults grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MyLogUtil.e("permission", "权限回调");
        switch (requestCode) {
            case REQ_RECORD_PMS:
                if (grantResults != null && grantResults.length != 0)
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        ToastUtil.show(getString(R.string.com_open_voice_permission));
                    }
                break;
            case REQ_CAMERA_PMS:
                if (grantResults != null && grantResults.length != 0)
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        ToastUtil.show(getString(R.string.com_open_photograph_permission));
                    }
                break;
            case REQ_LOCATION_PMS:
                if (grantResults != null && grantResults.length != 0)
                    //&& grantResults[1] != PackageManager.PERMISSION_GRANTED
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        ToastUtil.show(getString(R.string.com_open_location_permission));
                    }else{
                        permissionLocGot();
                    }
                break;
            case REQ_READ_PHONE_STATE_PMS:
                if (grantResults != null && grantResults.length != 0)
                    //&& grantResults[1] != PackageManager.PERMISSION_GRANTED
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        ToastUtil.show(getString(R.string.com_open_read_phone_state_permission));
                    }else{
                    }
                break;
            case REQ_WRITE_EXTERNAL_STORAGE_PMS:
                if (grantResults != null && grantResults.length != 0)
                    //&& grantResults[1] != PackageManager.PERMISSION_GRANTED
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        ToastUtil.show(getString(R.string.com_open_write_external_storage_permission));
                    }else{
                    }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 定位权限获取到时候的处理
     */
    protected void permissionLocGot(){
        MyLogUtil.e("permission", "获取到定位权限");
        MyLocation.getInstance().requestLocation();
    }
}
