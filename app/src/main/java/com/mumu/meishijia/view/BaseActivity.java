package com.mumu.meishijia.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.hwangjr.rxbus.RxBus;
import com.jaeger.library.StatusBarUtil;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.constant.RxBusAction;
import com.mumu.meishijia.model.mine.User;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.mine.LoginActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import lib.baidu.MyLocation;
import lib.cache.CacheJsonMgr;
import lib.utils.MyLogUtil;
import lib.utils.ToastUtil;
import lib.widget.ActionTitleBar;
import lib.widget.FrameProgressLayout;
import lib.widget.LoadingDialog;

public class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView,
        SwipeRefreshLayout.OnRefreshListener, FrameProgressLayout.OnClickRefreshListener{

    protected P presenter;

    private LoadingDialog loadingDialog;
    private ActionTitleBar actionTitleBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FrameProgressLayout frameProgressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createPresenter();
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
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        setStatusBar();
        getTitleBar();
        if(actionTitleBar != null){
            actionTitleBar.getImgLeft().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onLeftButtonClick();
                }
            });
            actionTitleBar.getImgRight().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRightButtonClick();
                }
            });
            actionTitleBar.getTxtRight().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRightButtonClick();
                }
            });
        }
        getSwipeRefresh();
        getFrameProgress();
    }

    protected void setStatusBar(){
        StatusBarUtil.setColor(this, getResources().getColor(R.color.theme_color), 1);
    }

    protected void setStatusBarTransparent(){
        StatusBarUtil.setTransparent(this);
    }

    protected void setStatusBarTransparentInFragment(){
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 1, null);
    }

    private void getTitleBar(){
        if (actionTitleBar == null) {
            View root = getWindow().getDecorView();
            actionTitleBar = root.findViewWithTag(ActionTitleBar.TAG);
        }
    }

    protected void setTitle(String title){
        if(actionTitleBar == null)
            return;
        actionTitleBar.getTxtTitle().setText(title);
    }

    protected void onLeftButtonClick(){
        finish();
    }

    protected void onRightButtonClick(){}

    private void getSwipeRefresh(){
        if(swipeRefreshLayout == null)
            swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setColorSchemeResources(R.color.theme_color);
            swipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    protected void startRefresh(){
        if(swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(true);
    }

    protected void stopRefresh(){
        if(swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

    }

    private void getFrameProgress(){
        if(frameProgressLayout == null){
            View root = getWindow().getDecorView();
            frameProgressLayout = root.findViewWithTag(FrameProgressLayout.TAG);
        }
        if(frameProgressLayout != null)
            frameProgressLayout.setOnClickRefreshListener(this);
    }

    protected void showFrameProgress(){
        if(frameProgressLayout != null)
            frameProgressLayout.show();
    }

    protected void dismissFrameProgress(){
        if(frameProgressLayout != null)
            frameProgressLayout.dismiss();
    }

    protected void noData(String str){
        if(frameProgressLayout != null){
            if(str != null){
                frameProgressLayout.noData(str);
            }else {
                frameProgressLayout.noData();
            }
        }
    }

    protected void loadFail(String str){
        if(frameProgressLayout != null){
            if(str != null){
                frameProgressLayout.loadFail(str);
            }else {
                frameProgressLayout.loadFail();
            }
        }
    }

    @Override
    public void onClickRefresh() {

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
        if(presenter != null){
            presenter.unSubscribe();
            presenter = null;
        }
        hideSoftInput();
    }

    protected void toast(int resId){
        toast(getString(resId));
    }

    protected void toast(String msg){
        if(!TextUtils.isEmpty(msg)){
            /*
            * 因为在主题里设置了fitsSystemWindows为true，所以toast的文字位置不居中
            * 把application当作context的值传入可解决这个问题*/
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 针对请求错误的统一处理
     * @param msg 错误信息
     */
    @Override
    public void toastErr(String msg){
        dismissLoadingDialog();
        stopRefresh();
        if("暂无数据".equals(msg)){
            noData(null);
        }else {
            loadFail(null);
        }
        toast(msg);
    }

    @Override
    public void goLogin() {
        MyApplication.getInstance().setUser(null);
        MyApplication.getInstance().setLogin(false);
        MyApplication.getInstance().setIMLogin(false);
        //清除用户信息
        CacheJsonMgr.getInstance(this).deleteJson(User.class.getSimpleName());

        //通知我的界面刷新数据
        RxBus.get().post(RxBusAction.MineUserData, "");

        //跳转到LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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
