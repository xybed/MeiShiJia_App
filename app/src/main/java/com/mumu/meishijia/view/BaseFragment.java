package com.mumu.meishijia.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.hwangjr.rxbus.RxBus;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.constant.RxBusAction;
import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.mine.LoginActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import lib.cache.CacheJsonMgr;
import lib.utils.DensityUtil;
import lib.utils.ToastUtil;
import lib.widget.LoadingDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView{

    protected P presenter;

    private LoadingDialog loadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ToastUtil.setContext(getActivity());
        super.onCreate(savedInstanceState);
        createPresenter();
    }

    @SuppressWarnings("unchecked")
    private void createPresenter(){
        try {
            presenter = ((Class<P>) ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]).getConstructor(BaseView.class).newInstance(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideSoftInput();
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        /** 隐藏软键盘 **/
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /***系统状态栏高度  start**/
    private int statusBarHeight = 0;

    public int getStatusBarHeight() {
        if(statusBarHeight == 0){
            //默认状态栏高度
            statusBarHeight = DensityUtil.dip2px(getActivity(),25);
        }
        return statusBarHeight;
    }

    public void setStatusBarHeight(int statusBarHeight) {
        this.statusBarHeight = statusBarHeight;
    }

    @Override
    public void toastErr(String msg) {
        dismissLoadingDialog();
        toast(msg);
    }

    protected void toast(String msg){
        if(!TextUtils.isEmpty(msg)){
            /*
            * 因为在主题里设置了fitsSystemWindows为true，所以toast的文字位置不居中
            * 把application当作context的值传入可解决这个问题*/
            Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void goLogin() {
        MyApplication.getInstance().setUser(null);
        MyApplication.getInstance().setLogin(false);
        MyApplication.getInstance().setIMLogin(false);
        //清除用户信息
        CacheJsonMgr.getInstance(getActivity()).deleteJson(UserModel.class.getSimpleName());

        //通知我的界面刷新数据
        RxBus.get().post(RxBusAction.MineUserData, "");

        //跳转到LoginActivity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /****end***/

    public void showLoadingDialog(String message, boolean cancelable, boolean otoCancelable){
        loadingDialog = new LoadingDialog(getActivity(), message, cancelable, otoCancelable);
        loadingDialog.show();
    }

    public void showLoadingDialog(String message){
        showLoadingDialog(message, true, true);
    }

    public void dismissLoadingDialog(){
        if(loadingDialog != null)
            loadingDialog.dismiss();
    }
}
