package com.mumu.meishijia.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import lib.utils.DensityUtil;
import lib.utils.ToastUtil;
import lib.widget.LoadingDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
    private LoadingDialog loadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ToastUtil.setContext(getActivity());
        super.onCreate(savedInstanceState);
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
